package urlProcessor;
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
        // when
        UrlType naverProcessedUrl1Type = urlProcessor.process(naverProcessedUrl1).getUrlType();
        UrlType naverProcessedUrl2Type = urlProcessor.process(naverProcessedUrl2).getUrlType();
        UrlType naverRawUrl1Type = urlProcessor.process(naverRawUrl1).getUrlType();
        UrlType naverRawUrl2Type = urlProcessor.process(naverRawUrl2).getUrlType();

        // then
        assertEquals(UrlType.NAVER, naverProcessedUrl1Type);
        assertEquals(UrlType.NAVER, naverProcessedUrl2Type);
        assertEquals(UrlType.NAVER, naverRawUrl1Type);
        assertEquals(UrlType.NAVER, naverRawUrl2Type);
    }
    
    @Test
    public void testNaverRawUrl() throws Exception {
        // when
        String processedNaverRawUrl1 = urlProcessor.process(naverRawUrl1).getUrl();
        String processedNaverRawUrl2 = urlProcessor.process(naverRawUrl2).getUrl();

        // then
        assertEquals(naverProcessedUrl1, processedNaverRawUrl1);
        assertEquals(naverProcessedUrl1, processedNaverRawUrl2);
    }

    @Test
    public void testNaverProcessedUrl() throws Exception {
        // when
        String processedNaverProcessedUrl1 = urlProcessor.process(naverProcessedUrl1).getUrl();
        String processedNaverProcessedUrl2 = urlProcessor.process(naverProcessedUrl2).getUrl();

        // then
        assertEquals(naverProcessedUrl1, processedNaverProcessedUrl1);
        assertEquals(naverProcessedUrl2, processedNaverProcessedUrl2);
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
