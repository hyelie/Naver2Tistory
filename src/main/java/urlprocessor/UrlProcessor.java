package urlprocessor;

import java.util.ArrayList;
import java.util.List;

import urlprocessor.blogUrlProcessors.NaverUrlProcessor;

public class UrlProcessor {
    private static final List<BlogUrlProcessor> blogUrlProcessors = new ArrayList<>();

    public UrlProcessor(){
        blogUrlProcessors.add(new NaverUrlProcessor());
        // Append other blog url processors here
    }

    public UrlVO process(String inputUrl) throws Exception {
        for(BlogUrlProcessor blogUrlProcessor : blogUrlProcessors){
            if(blogUrlProcessor.matches(inputUrl)){
                return new UrlVO(blogUrlProcessor.getUrlType(), blogUrlProcessor.process(inputUrl));
            }
        }
        throw new Exception("[Url 작업 중 오류] : 지원하지 않는 블로그의 Url입니다.");
    }
}