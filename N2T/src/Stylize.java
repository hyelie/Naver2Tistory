// crawling한 html을 tistory 양식에 맞게 고침
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Stylize {
    public static void main(String[] args) throws Exception {
		


		String url = "https://blog.naver.com/jhi990823/222783075683";
		Document doc = null;
        
        // url에 접속한다.
		try {
			doc = Jsoup.connect(url).get();
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		Elements url_refer = doc.select("#mainFrame");
		System.out.println("urll\n");
		for(int i=0; i<url_refer.size(); i++) {
			System.out.println(url_refer.eq(i).text());
		}
		System.out.println("srcsrc\n");
		Elements sss= doc.getElementsByTag("src");
		for(int i=0; i<sss.size(); i++) {
			System.out.println(sss.eq(i).text());
		}

		//doc = Jsoup.connect(url_refer.eq(0).text()).get();

		/*Elements Classes = doc.select("div.se-main-container");
        
        // select를 사용해서 doc에서 이 태그가 나오는 부분을 찾아준다. 그래서 이것을 bestsellers에 넣어준다. 
        //그래서 div detail 부분의 모든 element를 가지고 온다.
		Elements bestsellers = doc.select("div.detail");
        
        // bestseller로 가져온 것중에 div title 부분만 가져온다.
		Elements titles = bestsellers.select("div.title");
        
        // title 내부의 href 태그만 가져온다.
		
        
        // for을 이용해서 meta-data가 아닌 부분만 출력하게 한다.
		for(int i=0; i<Classes.size(); i++) {
			System.out.println(i+1 + "위: " + Classes.eq(i).text());
		}

		Elements booktitles = titles.select("a[href]");*/
	}
}
