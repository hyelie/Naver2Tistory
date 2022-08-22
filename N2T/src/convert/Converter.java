package convert;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Converter {
    private Document doc;
    private Elements post;
    private enum ResponseMessage{
        NOT_FOUND("유효하지 않은 요청입니다. 해당 블로그가 없습니다. 블로그 아이디를 확인해 주세요."),      // Invalid blog ID
        NO_CONTENT("삭제되거나, 존재하지 않거나, 비공개 글이거나, 구버전 포스팅입니다."),                   // Deleted, private or written by old version of Naver Editor
        INTERNAL_ERROR("예상치 못한 에러가 발생했습니다.");                                                 // Unexpected error

        private final String label;
        ResponseMessage(String label){
            this.label = label;
        }
    }

    // Crawl naver blog post
    /**
     * Crawl given URL and store it in the attribute
     * 
     * @param URL - Given URL to crawl
     * @throws Exception when data is not received
     * because of deleted, private, old editor version, no blog ID,
     * or an unexpected error while receiving data while crawling.
     */
    public void crawl(String URL) throws Exception{
        try{
            Connection con = Jsoup.connect(URL).timeout(5000).ignoreHttpErrors(true);
            Response response = con.execute();
            if (response.statusCode() == 200) {
                this.doc = con.get();
                this.post = doc.select(".se-viewer");
                if(post.size() == 0){ // 삭제된/존재하지 않는/비공개 게시글 또는 구버전 네이버 에디터로 작성한 게시글
                    throw new Exception(ResponseMessage.NO_CONTENT.toString());
                }
            } else{ // blogId가 틀린 경우
                throw new Exception(ResponseMessage.NOT_FOUND.toString());
            }
        } catch(IOException e) { // 이외 connection 중 발생하는 오류
            throw new Exception(ResponseMessage.INTERNAL_ERROR.toString());
        }
    }

    // convert crawled naver blog HTML to tistory HTML
    public void stylize(){
        // stylize with HTML DOM DFS traversal
        // 각 container의 type에 맞게 고침
    }
}
