import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import auth.TistoryClient;
import convert.Converter;
import convert.Crawler;
import convert.URLProcessor;
import utils.Util;

public class N2T {
    private List<String> URLs;
    private TistoryClient tistoryClient;
    private URLProcessor urlProcessor = new URLProcessor();
    private Crawler crawler = new Crawler();
    private Converter converter = new Converter();

    // 프로그램 실행 전에 config를 확인
    private void init(){
        try{
            // config 파일 읽어서 값 초기화
            System.out.println("[검증 중] : 기존에 입력한 사용자 정보를 확인 중입니다.");
            this.tistoryClient = new TistoryClient();
            

            // config 파일에 있는 값이 맞는지 확인 및 API가 정상작동하는지 테스트
            tistoryClient.authorize();
            System.out.println("[검증 완료] : 기존에 입력한 사용자 정보를 확인했습니다.");

            // 입력 파일에 있는 URL 입력받아 저장
            System.out.println("[파일 읽는 중] : list.txt 파일을 읽는 중입니다.");
            Path path = Paths.get(Util.getCurrentDirectory() + "/N2T/src/list.txt");
            URLs = Util.readFile(path);
            System.out.println("[파일 읽기 완료] : list.txt 파일을 읽었습니다.");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // URL 가공 후 crawling 이후, tistory 양식으로 stylize해서 upload
    private void step(String URL){
        try{
            // process URL
            String processedURL = urlProcessor.getOriginURL(URL);

            // crawl from processed URL
            Document doc = crawler.crawl(processedURL);

            // get post from crawled data
            converter.setPost(doc);

            // extract and get title from post
			converter.extractTitle();
			String title = converter.getTitle();
            System.out.println("[작업 중] : " + title);
            
			 // extract content from post
			 converter.extractContent();
            
			 // stylize content and get stylized result
			 converter.stylize();
			 String result = converter.getResult();

             // upload stylized result to tistory
             tistoryClient.post("test", "test");
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // 프로그램 실행
    public void process(){
        init();
        //URLs.stream().forEach(element -> step(element));
        /*
        프로세싱 이후 로그 txt 형식으로 출력
        티스토리 띄워줌
         */
    }
}
