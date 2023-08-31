package convert.scrappers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import convert.blogPost.BlogPost;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;

import java.io.IOException;
import java.util.HashMap;

// Inherit this abstract class to scrap other types of blogs.
public abstract class Scrapper {
    private static final String defaultErrorMessage = "스크래핑 중 알 수 없는 오류가 발생했습니다.";

    protected Scrapper(){
        initializeErrorMessages();
    }

    public BlogPost scrap(String url) throws Exception{
        Document document = crawl(url);
        return parse(document);
    }
    
    protected Document crawl(String url) throws Exception {
        try{
            Connection con = Jsoup.connect(url).timeout(5000).ignoreHttpErrors(true);
            Response response = con.execute();
            if (response.statusCode() == 200) {
                return con.get();
            }
            else{ // page not found
                throw new Exception(getErrorMessage(response.statusCode()));
            }
        } catch(IOException e) { // 이외 connection 중 발생하는 오류
            throw new Exception(getErrorMessage(500));
        }
    }
    protected abstract BlogPost parse(Document document) throws Exception ;

    protected HashMap<Integer, String> errorMessages = new HashMap<Integer, String>();
    protected abstract void initializeErrorMessages();
    protected String getErrorMessage(int statusCode){
        if(errorMessages.containsKey(statusCode)){
            return errorMessages.get(statusCode);
        }
        return defaultErrorMessage;
    }
}