import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import auth.TistoryClient;
import convert.Converter;
import convert.Crawler;
import urlprocessor.UrlProcessor;
import utils.Utils;

public class N2T {
    private List<String> urls;
    private TistoryClient tistoryClient;
    private UrlProcessor urlProcessor = new UrlProcessor();
    private Crawler crawler = new Crawler();
    private Converter converter = new Converter();

    // 프로그램 실행 전에 config를 확인
    private void initConfig() throws Exception{
        try{
            // config 파일 읽어서 값 초기화
            System.out.println("[검증 중] : 기존에 입력한 사용자 정보를 확인 중입니다.");
            this.tistoryClient = new TistoryClient();
            
            // config 파일에 있는 값이 맞는지 확인 및 API가 정상작동하는지 테스트
            System.out.println("[검증 완료] : 기존에 입력한 사용자 정보를 확인했습니다.");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new Exception();
        }
    }

    // 프로그램 실행 전에 list.txt에 있는 url들을 읽어 옴.
    private void initUrls() throws Exception{
        try{
            // 입력 파일에 있는 url 입력받아 저장
            System.out.println("[파일 읽는 중] : list.txt 파일을 읽는 중입니다.");
            Path path = Paths.get(Utils.getUrlListPath());
            urls = Utils.readList(path);
            System.out.println("[파일 읽기 완료] : list.txt 파일을 읽었습니다.");
        }
        catch(Exception e){
            throw new Exception("[초기화 오류] : list.txt 파일이 없습니다.");
        }
    }

    // 이미지 폴더 clear, url 가공, crawling 및 이미지 다운로드, 이미지 업로드, crawling 결과 tistory 양식으로 stylize, upload
    private void step(String url) throws Exception{
        try{
            // clear image folder 
            Utils.clearImageFolder();

            // process url
            String processedUrl = urlProcessor.process(url).getUrl();

            // crawl from processed url
            Document doc = crawler.crawl(processedUrl);

            // get post from crawled data
            converter.setPost(doc);

            // extract and get title from post
			converter.extractTitle();
			String title = converter.getTitle();
            System.out.println("[작업 중] : " + title);
            
			// extract content from post           
			converter.extractContent();
            
			// stylize content
			converter.stylize();

            // upload and attach image
            List<String> replacers = new ArrayList<String>();
            // for(int i = 0; i < Utils.getNumImages(); i++){
            //     replacers.add(tistoryClient.attach(i));
            // }
            converter.attachIMAGE(replacers);

            converter.encode2UTF8();
            
            // get result
			String result = converter.getResult();

            // upload stylized result to tistory
            tistoryClient.post(title, result);
            System.out.println("[작업 완료] : " + title);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    // 프로그램 실행
   public void process(){
        // 초기화
        try{
            initConfig();
            initUrls();
        } catch(Exception e){
            System.out.println("[종료] : 초기화 중 오류 발생으로 프로그램을 종료합니다.");
            return;
        }        
        System.out.println("[시작] : Naver to Tistory 작업을 시작합니다.");

        // 모든 url에 대해 작업 진행
        for(String url : urls){
            // 정상적으로 끝났다면 해당 작업 종료, 그렇지 않으면 오류 메시지 출력 후 최대 1회 재시도.
            int num_tries = 0, max_tries = 2;
            while(true){
                try{
                    step(url);
                }
                catch(Exception e){
                    if(num_tries++ == max_tries){
                        System.out.println("[검증 오류] : 작업 중 반복적으로 실패해 프로그램을 종료합니다.");
                        return;
                    } 
                    System.out.println("[작업 중 오류 발생] : " + e.getMessage());
                    try{
                        initConfig();
                    } catch(Exception initConfigE){
                        System.out.println("[종료] : 작업 중 오류 발생으로 프로그램을 종료합니다.");
                        return;
                    }               
                }
                break;
            }
        }

        System.out.println("[종료] : Naver to Tistory 프로그램을 종료합니다.");
        
        //String userBlogUrl = "https://" + tistoryClient.getBlogName() + ".tistory.com/manage/posts";
        //Utils.openWindow(userBlogUrl);
        // TODO : exe 파일로 빼고 실행 잘 되는지 보기.
    }
}
