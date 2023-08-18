package URLProcessor;

import java.util.ArrayList;
import java.util.List;

public class URLProcessor {
    private final List<BlogURLProcessor> blogURLProcessors = new ArrayList<>();

    public URLProcessor(){
        blogURLProcessors.add(new NaverURLProcessor());
        // Append other blog url processors here
    }

    public String process(String inputURL) throws Exception {
        for(BlogURLProcessor blogURLProcessor : blogURLProcessors){
            if(blogURLProcessor.matches(inputURL)) return blogURLProcessor.process(inputURL);
        }
        throw new Exception("[URL 작업 중 오류] : 지원하지 않는 블로그의 URL입니다.");
    }
}