package convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLProcessor {
    // regular expression filtering [blog.naver.com/{NAVER_ID}/{POST_NUMBER}] format
    private Pattern rawPattern = Pattern.compile("http[s]?:\\/\\/blog.naver.com\\/([A-za-z0-9]*)\\/([0-9]*)");
    // regular expression filtering [blog.naver.com/PostView.naver?blogId={NAVER_ID}&logNo={POST_NUMBER}] format
    private Pattern processedPattern = Pattern.compile("http[s]?:\\/\\/blog.naver.com\\/PostView.naver\\?blogId=([A-za-z0-9]*)&logNo=([0-9]*)");

    /**
     * If the inputURL is in the format of rawPattern, processing is required.
     * If the inputURL is in the format of processedPattern, processing is not necessary.
     * Otherwise, inputURL is invalid URL
     * 
     * @param inputURL : Given URL
     * @return 0 if invalid, 1 if inputURL need to be processed, 2 if inputURL is already processed
     */
    Integer getNaverBlogURLType(String inputURL){
        if(inputURL.matches(this.processedPattern.toString())) return 2;
        else if(inputURL.matches(this.rawPattern.toString())) return 1;
        else return 0;
    }

    /**
     * Change Naver Blog URL to processedPattern format
     * 
     * @param inputURL - Naver Blog URL
     * @return processedPattern format URL
     * @throws Exception If inputURL is not Naver Blog URL format, then throw with error message
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
