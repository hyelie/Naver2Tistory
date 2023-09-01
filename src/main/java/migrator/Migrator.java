package migrator;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import auth.AuthClient;
import convert.blogPost.BlogPost;
import convert.converters.Converter;
import convert.scrappers.Scrapper;
import migrator.TargetBlogConfigFactories.TistoryConfigFactory;
import urlprocessor.UrlProcessor;
import utils.Utils;

public class Migrator {
    private final UrlProcessor urlProcessor = new UrlProcessor();
    private final Scrapper scrapper = new Scrapper();
    private AuthClient authClient;
    private Converter converter;

    private final List<BlogType> targetBlogTypes = new ArrayList<>();
    private final Map<BlogType, TargetBlogConfigFactory> targetBlogConfigFactoryMap = new HashMap<>();
    

    public Migrator(){
        targetBlogTypes.add(BlogType.NONE);
        targetBlogTypes.add(BlogType.TISTORY);
        // append other target blog types here

        targetBlogConfigFactoryMap.put(BlogType.TISTORY, new TistoryConfigFactory());
        // append other target blog factories here
    }

    public void migrate(){
        BlogType targetBlogType = getTargetBlogType();
        configureTargetBlog(targetBlogType);

        List<String> urls = loadUrlList();

        Utils.printMessage("[작업 시작] 작업을 시작합니다.");
        for(String url : urls){
            Utils.printMessage("[작업 중] : " + url);
            migratePost(url);
        }
        Utils.printMessage("[작업 완료] 작업을 완료했습니다. 프로그램을 종료합니다.");
    }

    private BlogType getTargetBlogType(){
        Utils.printMessage("==============================");
        printTargetBlogTypes();
        Utils.printMessage("==============================");

        String input = Utils.getInput("업로드할 블로그를 선택하세요. 종료를 원하면 0을 입력하세요.");
        BlogType targetBlogType = parseInput(input);

        if(targetBlogType == null){
            Utils.printMessage("지원하지 않는 블로그 유형입니다. 프로그램을 종료합니다.");
            System.exit(1);
        }

        return targetBlogType;
    }

    private void configureTargetBlog(BlogType targetBlogType){
        TargetBlogConfigFactory targetBlogConfigFactory = getTargetBlogConfigureFactory(targetBlogType);
        if(targetBlogConfigFactory == null){
            Utils.printMessage("지원하지 않는 블로그 유형입니다. 프로그램을 종료합니다.");
            System.exit(1);
        }

        this.authClient = targetBlogConfigFactory.createAuthClient();
        this.converter = targetBlogConfigFactory.createConverter(this.authClient);
    }

    private void printTargetBlogTypes(){
        for(int i = 1; i<targetBlogTypes.size(); i++){
            Utils.printMessage(i + ". " + targetBlogTypes.get(i));
        }
    }

    private BlogType parseInput(String input){
        try{
            return BlogType.valueOf(input.toUpperCase());
        }
        catch (IllegalArgumentException illegalArgumentException){
            try{
                int index = Integer.parseInt(input);
                if(0 <= index && index < targetBlogTypes.size()){
                    return targetBlogTypes.get(index);
                }
                return null;
            }
            catch (NumberFormatException numberFormatException){
            }
        }
        return null;
    }

    private TargetBlogConfigFactory getTargetBlogConfigureFactory(BlogType targetBlogType){
        if(targetBlogConfigFactoryMap.containsKey(targetBlogType)){
            return targetBlogConfigFactoryMap.get(targetBlogType);
        }
        return null;
    }

    private List<String> loadUrlList(){
        Utils.printMessage("[파일 읽는 중] : list.txt 파일을 읽는 중입니다.");

        try{
            Path path = Paths.get(Utils.getUrlListPath());
            List<String> urls = Utils.readList(path);
            Utils.printMessage("[파일 읽기 완료] : list.txt 파일을 읽었습니다.");
            return urls;
        }
        catch(Exception e) {
            Utils.printMessage("[파일 오류] : list.txt 파일이 없습니다.");
        }

        return new ArrayList<>();
    }

    private void migratePost(String url){
        try{
            BlogPost blogPost = scrapper.scrap(urlProcessor.process(url));
            String title = blogPost.getTitle();
            String convertedContent = converter.convert(blogPost);
            authClient.post(title, convertedContent);
        }
        catch(Exception e){
            Utils.printMessage(e.getMessage() + " " + url);
        }
    }

}
