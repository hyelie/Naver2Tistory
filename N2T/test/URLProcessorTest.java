import org.junit.Test;

import URLProcessor.URLProcessor;

import static org.junit.Assert.assertEquals;

public class URLProcessorTest {
    URLProcessor urlProcessor = new URLProcessor();
    String naverRawURL1 = "https://blog.naver.com/jhi990823/222848946417";
    String naverRawURL2 = "http://blog.naver.com/jhi990823/222848946417";
    String naverProcessedURL1 = "https://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";
    String naverProcessedURL2 = "http://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";
    String naverInvalidURL = "https://blog.naver.com/invalidURL";
    String unsupportedURL = "http://hyelie.com";
    
    @Test
    public void testNaverRawURL() throws Exception {
        assertEquals(urlProcessor.process(naverRawURL1), naverProcessedURL1);
        assertEquals(urlProcessor.process(naverRawURL2), naverProcessedURL1);
    }

    @Test
    public void testNaverProcessedURL() throws Exception {
        assertEquals(urlProcessor.process(naverProcessedURL1), naverProcessedURL1);
        assertEquals(urlProcessor.process(naverProcessedURL2), naverProcessedURL2);
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
