import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jsoup.Jsoup;
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
    private void initConfig() throws Exception{
        try{
            // config 파일 읽어서 값 초기화
            System.out.println("[검증 중] : 기존에 입력한 사용자 정보를 확인 중입니다.");
            this.tistoryClient = new TistoryClient();
            
            // config 파일에 있는 값이 맞는지 확인 및 API가 정상작동하는지 테스트
            tistoryClient.authorize();
            System.out.println("[검증 완료] : 기존에 입력한 사용자 정보를 확인했습니다.");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new Exception();
        }
    }

    // 프로그램 실행 전에 list.txt에 있는 url들을 읽어 옴.
    private void initURLs(){
        try{
            // 입력 파일에 있는 URL 입력받아 저장
            System.out.println("[파일 읽는 중] : list.txt 파일을 읽는 중입니다.");
            Path path = Paths.get(Util.getCurrentDirectory() + "/N2T/src/list.txt");
            URLs = Util.readList(path);
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

            // image 파일에 있는 src 변경
            Jsoup.parse(result);
            //tistoryClient.attach();

            // upload stylized result to tistory
            tistoryClient.post(title, result);
            System.out.println("[작업 완료] : " + title);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    // 프로그램 실행
    public void process(){
        // 초기화
        try{
            initConfig();
            initURLs();
        } catch(Exception e){
            System.out.println("[종료] : 초기화 중 오류 발생으로 프로그램을 종료합니다.");
            return;
        }
        
        System.out.println("[작업 시작] : Naver to Tistory 작업을 시작합니다.");
        // 모든 URL에 대해 작업 진행
        for(String URL : URLs){
            // 정상적으로 끝났다면 해당 작업 종료, 그렇지 않으면 오류 메시지 출력 후 최대 1회 재시도.
            int num_tries = 0, max_tries = 2;
            while(true){
                try{
                    step(URL);
                }
                catch(Exception e){
                    if(num_tries++ == max_tries){
                        System.out.println("[검증 오류] : 사용자 정보 초기화가 반복적으로 실패해 프로그램을 종료합니다.");
                        return;
                    } 
                    System.out.println("[작업 중 오류 발생] : 로그인을 재시도합니다.");
                    try{
                        initConfig();
                    } catch(Exception initConfigE){
                        System.out.println("[종료] : 초기화 중 오류 발생으로 프로그램을 종료합니다.");
                        return;
                    }               
                }
                break;
            }
        }

        System.out.println("[성공] : Naver to Tistory 작업이 성공적으로 끝났습니다.");
        
        String userBlogURL = "https://" + tistoryClient.getBlogName() + ".tistory.com/manage/posts";
        Util.openWindow(userBlogURL);
        // TODO : 프로세싱 이후 로그 txt 형식으로 출력
        // TODO : exe 파일로 빼고 실행 잘 되는지 보기.
    }
}
