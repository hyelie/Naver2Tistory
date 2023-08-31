package scrapper.naver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;

public class NodeTestUtils {
    public static void assertNodeTypeAndContent(ConvertedTreeNode node, StyleType expectedType, String expectedContent){
        assertNotNull(node);
        assertEquals(expectedType, node.getType());
        assertEquals(expectedContent, node.getContent());
    }
}
