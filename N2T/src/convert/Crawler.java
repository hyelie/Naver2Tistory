package convert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;

import java.io.IOException;

public class Crawler {
    private Document doc;

    /**
     * Crawl given URL and store it in the attribute
     * 
     * @param URL - Given URL to crawl
     * @throws Exception when data is not received
     * because of page does no exist or an unexpected error while receiving data while crawling.
     */ 
    public Document crawl(String URL) throws Exception{
        try{
            Connection con = Jsoup.connect(URL).timeout(5000).ignoreHttpErrors(true);
            Response response = con.execute();
            if (response.statusCode() == 200) {
                this.doc = con.get();
            } else{ // 페이지가 존재하지 않는 경우
                throw new Exception(ResponseMessage.NOT_FOUND.getLabel());
            }
        } catch(IOException e) { // 이외 connection 중 발생하는 오류
            throw new Exception(ResponseMessage.INTERNAL_ERROR.getLabel());
        }
        return this.doc;
    }
}
