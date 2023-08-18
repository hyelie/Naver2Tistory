import org.junit.Test;

import URLProcessor.NaverURLProcessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NaverURLProcessorTest {
    NaverURLProcessor naverURLProcessor = new NaverURLProcessor();

    String rawPattern1 = "https://blog.naver.com/jhi990823/222848946417";
    String rawPattern2 = "http://blog.naver.com/jhi990823/222848946417";
    String processedPattern1 = "https://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";
    String processedPattern2 = "http://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";
    String invalidPattern1 = "http://hyelie.com";
    String invalidPattern2 = "https://blog.naver.com/invalidURL";

    @Test
    public void testRawPatternMatches(){
        assertTrue(naverURLProcessor.matches(rawPattern1));
        assertTrue(naverURLProcessor.matches(rawPattern2));
    }

    @Test
    public void testProcessedPatternMatches(){
        assertTrue(naverURLProcessor.matches(processedPattern1));
        assertTrue(naverURLProcessor.matches(processedPattern2));
    }

    @Test
    public void testInvalidPatternMatches(){
        assertFalse(naverURLProcessor.matches(invalidPattern1));
        assertFalse(naverURLProcessor.matches(invalidPattern2));
    }

    @Test
    public void testProcessRawPattern() throws Exception {
        assertEquals(naverURLProcessor.process(rawPattern1), processedPattern1);
        assertEquals(naverURLProcessor.process(rawPattern2), processedPattern1);
    }

    @Test
    public void testProcessProcessedPattern() throws Exception {
        assertEquals(naverURLProcessor.process(processedPattern1), processedPattern1);
        assertEquals(naverURLProcessor.process(processedPattern2), processedPattern2);
    }    

    @Test(expected = Exception.class)
    public void testInvalidPattern1() throws Exception {
        naverURLProcessor.process(invalidPattern1);
    }

    @Test(expected = Exception.class)
    public void testInvalidPattern2() throws Exception {
        naverURLProcessor.process(invalidPattern2);
    }
}
