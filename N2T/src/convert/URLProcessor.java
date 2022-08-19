package convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLProcessor {
    // blog.naver.com/{NAVER_ID}/{POST_NUMBER}
    Pattern rawPattern = Pattern.compile("http[s]?:\\/\\/blog.naver.com\\/([A-za-z0-9]*)\\/([0-9]*)");
    // blog.naver.com/PostView.naver?blogId={NAVER_ID}&logNo={POST_NUMBER} 형식
    Pattern processedPattern = Pattern.compile("http[s]?:\\/\\/blog.naver.com\\/PostView.naver\\?blogId=([A-za-z0-9]*)&logNo=([0-9]*)");

    /**
     * 입력 URL이 rawPattern의 형식이라면 가공이 필요하고,
     * processedPattern의 형식이라면 가공할 필요가 없음.
     * 그렇지 않다면 잘못된 URL임
     * 
     * @param : 입력으로 넣는 URL
     * @return 네이버 블로그 URL 형식에 맞지 않으면 0, 맞지만 가공할 필요가 있으면 1, 맞으면서 가공할 필요가 없으면 2
     */
    Integer getNaverBlogURLType(String inputURL){
        if(inputURL.matches(this.processedPattern.toString())) return 2;
        else if(inputURL.matches(this.rawPattern.toString())) return 1;
        else return 0;
    }

    /**
     * 네이버 블로그 URL을 processedPattern의 형식으로 바꿈
     * 
     * @param inputURL - 네이버 블로그 URL
     * @return processedPattern 형식 URL
     * @throws Exception 네이버 블로그 URL 형식이 아닌 경우 오류 메시지와 같이 throw
     */
    public String getOriginURL(String inputURL) throws Exception{
        Integer urlType = getNaverBlogURLType(inputURL);
        if(urlType == 2){ // already completed
            return inputURL;
        }
        else if(urlType == 1){ // need to be processed
            String result = "https://blog.naver.com/PostView.naver?blogId=";
            Matcher rawMatcher = this.rawPattern.matcher(inputURL);
            if(rawMatcher.find()){
                String NAVER_ID = rawMatcher.group(1);
                String POST_NUMBER = rawMatcher.group(2);
                result += NAVER_ID + "&logNo=" + POST_NUMBER;
            }
            return result;
        }
        else{
            throw new Exception("잘못된 URL입니다");
        }
    }
}
