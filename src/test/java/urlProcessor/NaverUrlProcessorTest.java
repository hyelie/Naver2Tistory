package urlProcessor;
import org.junit.Test;

import urlprocessor.UrlType;
import urlprocessor.blogUrlProcessors.NaverUrlProcessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NaverUrlProcessorTest {
    NaverUrlProcessor naverUrlProcessor = new NaverUrlProcessor();

    String rawPattern1 = "https://blog.naver.com/jhi990823/222848946417";
    String rawPattern2 = "http://blog.naver.com/jhi990823/222848946417";
    String processedPattern1 = "https://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";
    String processedPattern2 = "http://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";
    String invalidPattern1 = "http://hyelie.com";
    String invalidPattern2 = "https://blog.naver.com/invalidUrl";

    @Test
    public void testUrlType(){
        assertEquals(UrlType.NAVER, naverUrlProcessor.getUrlType());
    }

    @Test
    public void testRawPatternMatches(){
        assertTrue(naverUrlProcessor.matches(rawPattern1));
        assertTrue(naverUrlProcessor.matches(rawPattern2));
    }

    @Test
    public void testProcessedPatternMatches(){
        assertTrue(naverUrlProcessor.matches(processedPattern1));
        assertTrue(naverUrlProcessor.matches(processedPattern2));
    }

    @Test
    public void testInvalidPatternMatches(){
        assertFalse(naverUrlProcessor.matches(invalidPattern1));
        assertFalse(naverUrlProcessor.matches(invalidPattern2));
    }

    @Test
    public void testProcessRawPattern() throws Exception {
        assertEquals(processedPattern1, naverUrlProcessor.process(rawPattern1));
        assertEquals(processedPattern1, naverUrlProcessor.process(rawPattern2));
    }

    @Test
    public void testProcessProcessedPattern() throws Exception {
        assertEquals(processedPattern1, naverUrlProcessor.process(processedPattern1));
        assertEquals(processedPattern2, naverUrlProcessor.process(processedPattern2));
    }    

    @Test(expected = Exception.class)
    public void testInvalidPattern1() throws Exception {
        naverUrlProcessor.process(invalidPattern1);
    }

    @Test(expected = Exception.class)
    public void testInvalidPattern2() throws Exception {
        naverUrlProcessor.process(invalidPattern2);
    }
}
