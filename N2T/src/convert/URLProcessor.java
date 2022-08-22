package convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLProcessor {
    // Regular expression filtering [blog.naver.com/{NAVER_ID}/{POST_NUMBER}] format
    private Pattern rawPattern = Pattern.compile("http[s]?:\\/\\/blog.naver.com\\/([A-za-z0-9]*)\\/([0-9]*)");
    // Regular expression filtering [blog.naver.com/PostView.naver?blogId={NAVER_ID}&logNo={POST_NUMBER}] format
    private Pattern processedPattern = Pattern.compile("http[s]?:\\/\\/blog.naver.com\\/PostView.naver\\?blogId=([A-za-z0-9]*)&logNo=([0-9]*)");
    private enum URLType {
        INVALID,
        RAW,                    // Need to be processed
        PROCESSED;              // Already processed
    }

    /**
     * If the inputURL is in the format of rawPattern, processing is required.
     * If the inputURL is in the format of processedPattern, processing is not necessary.
     * Otherwise, inputURL is invalid URL
     * 
     * @param inputURL : Given URL
     * @return enum of URLType. INVALID if invalid, RAW if inputURL need to be processed, PROCESSED if inputURL is already processed
     */
    URLType getNaverBlogURLType(String inputURL){
        if(inputURL.matches(this.processedPattern.toString())) return URLType.PROCESSED;
        else if(inputURL.matches(this.rawPattern.toString())) return URLType.RAW;
        else return URLType.INVALID;
    }

    /**
     * Change Naver Blog URL to processedPattern format
     * 
     * @param inputURL - Naver Blog URL
     * @return processedPattern format URL
     * @throws Exception If inputURL is not Naver Blog URL format, then throw with error message
     */
    public String getOriginURL(String inputURL) throws Exception{
        URLType urlType = getNaverBlogURLType(inputURL);
        if(urlType == URLType.PROCESSED){
            return inputURL;
        }
        else if(urlType == URLType.RAW){
            String result = "";
            Matcher rawMatcher = this.rawPattern.matcher(inputURL);
            if(rawMatcher.find()){
                String NAVER_ID = rawMatcher.group(1);
                String POST_NUMBER = rawMatcher.group(2);
                result = String.format("https://blog.naver.com/PostView.naver?blogId=%s&logNo=%s", NAVER_ID, POST_NUMBER);
            }
            return result;
        }
        else { // urlType == URLType.INVALID
            throw new Exception("잘못된 URL입니다");
        }
    }
}
