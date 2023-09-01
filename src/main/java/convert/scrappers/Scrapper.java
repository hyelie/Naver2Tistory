package convert.scrappers;

import java.util.HashMap;
import java.util.Map;

import convert.blogPost.BlogPost;
import convert.scrappers.naver.NaverScrapper;
import migrator.BlogType;
import urlprocessor.UrlVO;

public class Scrapper {
    private static Map<BlogType, BlogScrapper> blogScrapperMap = new HashMap<>();

    public Scrapper(){
        blogScrapperMap.put(BlogType.NAVER, new NaverScrapper());
        // Append other blog scrappers here
    }

    public BlogPost scrap(UrlVO urlVO) throws Exception {
        BlogType blogType = urlVO.getUrlType();
        String url = urlVO.getUrl();

        if(blogScrapperMap.containsKey(blogType)){
            BlogScrapper blogScrapper = blogScrapperMap.get(blogType);
            return blogScrapper.scrap(url);
        }
        throw new Exception("[스크래핑 중 오류] : 지원하지 않는 블로그입니다.");
    }
}
