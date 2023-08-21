package urlprocessor.blog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import urlprocessor.BlogUrlProcessor;
import urlprocessor.UrlType;

public class NaverUrlProcessor implements BlogUrlProcessor {
    private static final UrlType TYPE = UrlType.NAVER;
    @Override
    public UrlType getUrlType(){
        return TYPE;
    }

    // Regular expression filtering [blog.naver.com/{NAVER_ID}/{POST_NUMBER}] format
    private static final Pattern RAW_PATTERN = Pattern.compile("http[s]?:\\/\\/blog.naver.com\\/([A-za-z0-9]*)\\/([0-9]*)");
    // Regular expression filtering [blog.naver.com/PostView.naver?blogId={NAVER_ID}&logNo={POST_NUMBER}] format
    private static final Pattern PROCESSED_PATTERN = Pattern.compile("http[s]?:\\/\\/blog.naver.com\\/PostView.naver\\?blogId=([A-za-z0-9]*)&logNo=([0-9]*)(.)*");

    @Override
    public Boolean matches(String inputURL){
        return matchesProcessedPattern(inputURL) || matchesRawPattern(inputURL);
    }

    @Override
    public String process(String inputURL) throws Exception{
        if(matchesProcessedPattern(inputURL)) return inputURL;
        if(matchesRawPattern(inputURL)) return transformRawPattern(inputURL);

        throw new Exception("[URL 작업 중 오류] : 잘못된 URL입니다");
    }

    private boolean matchesProcessedPattern(String inputURL){
        return inputURL.matches(PROCESSED_PATTERN.toString());
    }

    private boolean matchesRawPattern(String inputURL){
        return inputURL.matches(RAW_PATTERN.toString());
    }

    private String transformRawPattern(String inputURL){
        Matcher rawMatcher = this.RAW_PATTERN.matcher(inputURL);
        if(rawMatcher.find()){
            String NAVER_ID = rawMatcher.group(1);
            String POST_NUMBER = rawMatcher.group(2);
            return String.format("https://blog.naver.com/PostView.naver?blogId=%s&logNo=%s", NAVER_ID, POST_NUMBER);
        }
        return inputURL;
    }
}