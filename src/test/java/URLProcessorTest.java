import org.junit.Test;

import urlprocessor.UrlProcessor;
import urlprocessor.UrlType;

import static org.junit.Assert.assertEquals;

public class URLProcessorTest {
    UrlProcessor urlProcessor = new UrlProcessor();
    String naverRawURL1 = "https://blog.naver.com/jhi990823/222848946417";
    String naverRawURL2 = "http://blog.naver.com/jhi990823/222848946417";
    String naverProcessedURL1 = "https://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";
    String naverProcessedURL2 = "http://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";
    String naverInvalidURL = "https://blog.naver.com/invalidURL";
    String unsupportedURL = "http://hyelie.com";

    @Test
    public void testNaverURLType() throws Exception{
        assertEquals(UrlType.NAVER, urlProcessor.process(naverProcessedURL1).getUrlType());
        assertEquals(UrlType.NAVER, urlProcessor.process(naverProcessedURL2).getUrlType());
        assertEquals(UrlType.NAVER, urlProcessor.process(naverRawURL1).getUrlType());
        assertEquals(UrlType.NAVER, urlProcessor.process(naverRawURL2).getUrlType());
    }
    
    @Test
    public void testNaverRawURL() throws Exception {
        assertEquals(urlProcessor.process(naverRawURL1).getURL(), naverProcessedURL1);
        assertEquals(urlProcessor.process(naverRawURL2).getURL(), naverProcessedURL1);
    }

    @Test
    public void testNaverProcessedURL() throws Exception {
        assertEquals(urlProcessor.process(naverProcessedURL1).getURL(), naverProcessedURL1);
        assertEquals(urlProcessor.process(naverProcessedURL2).getURL(), naverProcessedURL2);
    }

    @Test(expected = Exception.class)
    public void testInvalidURL() throws Exception {
        urlProcessor.process(naverInvalidURL);
    }

    @Test(expected = Exception.class)
    public void testUnsupportedURL() throws Exception {
        urlProcessor.process(unsupportedURL);
    }
}
