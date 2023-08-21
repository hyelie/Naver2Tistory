package convert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;

import java.io.IOException;

public class Crawler {
    /**
     * Crawl given url and store it in the attribute
     * 
     * @param url - Given URL to crawl
     * @throws Exception when data is not received
     * because of page does no exist or an unexpected error while receiving data while crawling.
     */ 
    public Document crawl(String url) throws Exception{
        try{
            Connection con = Jsoup.connect(url).timeout(5000).ignoreHttpErrors(true);
            Response response = con.execute();
            if (response.statusCode() == 200) {
                return con.get();
            } else{ // page not found
                throw new Exception(ResponseMessage.NOT_FOUND.getLabel());
            }
        } catch(IOException e) { // 이외 connection 중 발생하는 오류
            throw new Exception(ResponseMessage.INTERNAL_ERROR.getLabel());
        }
    }
}
