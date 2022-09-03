import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import convert.Converter;
import convert.Crawler;
import convert.URLProcessor;
import utils.Util;

public class N2T {
    private List<String> URLs;
    private Util util = new Util();
    private URLProcessor urlProcessor = new URLProcessor();
    private Crawler crawler = new Crawler();
    private Converter converter = new Converter();

    // 프로그램 실행 전에 config를 확인
    private void init(){
        // file 읽고, config 맞는지 확인
        System.out.println("[검증] : 기존에 입력한 사용자 정보를 확인 중입니다.");

        // 있으면 수정 물어보고, 없으면 바로 입력받음

        // 입력받은 후에 API가 정상작동하는지 테스트

        // 입력 파일에 있는 URL 입력받아 저장
		Path path = Paths.get(util.getCurrentDirectory() + "/N2T/src/list.txt");
		URLs = util.readFile(path);
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
            
        }
        catch(Exception e){
            System.out.println("[오류] : " + e.getMessage());
        }
    }

    // 프로그램 실행
    public void process(){
        init();
        URLs.stream().forEach(element -> step(element));
        /*
        프로세싱 이후 로그 txt 형식으로 출력
        티스토리 띄워줌
         */
    }
}
