import org.junit.Test;

import urlprocessor.UrlProcessor;
import urlprocessor.UrlType;

import static org.junit.Assert.assertEquals;

public class UrlProcessorTest {
    UrlProcessor urlProcessor = new UrlProcessor();
    String naverRawUrl1 = "https://blog.naver.com/jhi990823/222848946417";
    String naverRawUrl2 = "http://blog.naver.com/jhi990823/222848946417";
    String naverProcessedUrl1 = "https://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";
    String naverProcessedUrl2 = "http://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";
    String naverInvalidUrl = "https://blog.naver.com/invalidUrl";
    String unsupportedUrl = "http://hyelie.com";

    @Test
    public void testNaverUrlType() throws Exception{
        assertEquals(UrlType.NAVER, urlProcessor.process(naverProcessedUrl1).getUrlType());
        assertEquals(UrlType.NAVER, urlProcessor.process(naverProcessedUrl2).getUrlType());
        assertEquals(UrlType.NAVER, urlProcessor.process(naverRawUrl1).getUrlType());
        assertEquals(UrlType.NAVER, urlProcessor.process(naverRawUrl2).getUrlType());
    }
    
    @Test
    public void testNaverRawUrl() throws Exception {
        assertEquals(urlProcessor.process(naverRawUrl1).getUrl(), naverProcessedUrl1);
        assertEquals(urlProcessor.process(naverRawUrl2).getUrl(), naverProcessedUrl1);
    }

    @Test
    public void testNaverProcessedUrl() throws Exception {
        assertEquals(urlProcessor.process(naverProcessedUrl1).getUrl(), naverProcessedUrl1);
        assertEquals(urlProcessor.process(naverProcessedUrl2).getUrl(), naverProcessedUrl2);
    }

    @Test(expected = Exception.class)
    public void testInvalidUrl() throws Exception {
        urlProcessor.process(naverInvalidUrl);
    }

    @Test(expected = Exception.class)
    public void testUnsupportedUrl() throws Exception {
        urlProcessor.process(unsupportedUrl);
    }
}
