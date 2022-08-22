package convert;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Converter {
    private Document doc;
    private Elements post;

    // Crawl naver blog post
    /**
     * Crawl given URL and store it in the attribute
     * 
     * @param URL - Given URL to crawl
     * @throws 
     */
    public void crawl(String URL){
        // url에 접속한다.
		// 없는 경우 처리해야 함
		try {
			this.doc = Jsoup.connect(URL)
					.timeout(5000)
					.get();
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}

        // TODO : 삭제된/존재하지 않는 게시글(logNo 틀린), 유효하지 않은 요청(blogId 틀린)인 경우

		this.post = doc.select(".se-viewer"); // 본문 저장

        
    }

    // convert crawled naver blog HTML to tistory HTML
    public void stylize(){
        // HTML DOM DFS traversal.
        // 각 container의 type에 맞게 고침.
    }
}
