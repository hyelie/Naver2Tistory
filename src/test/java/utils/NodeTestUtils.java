package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;

public class NodeTestUtils {
    public static void assertNodeTypeAndContent(ConvertedTreeNode origin, StyleType expectedType, String expectedContent){
        assertNotNull(origin);
        assertEquals(expectedType, origin.getType());
        assertEquals(expectedContent, origin.getContent());
    }

    public static void assertNodeListEquals(List<ConvertedTreeNode> expected, List<ConvertedTreeNode> origin){
        assertNotNull(expected);
        assertNotNull(origin);

        assertEquals(expected.size(), origin.size());

        int len = expected.size();
        for(int i = 0; i<len; i++){
            assertEquals(expected.get(i), origin.get(i));
        }
    }

    public static void assertNodeEquals(ConvertedTreeNode expected, ConvertedTreeNode origin){
        assertNotNull(expected);
        assertNotNull(origin);
        assertEquals(expected.getType(), origin.getType());
        assertEquals(expected.getContent(), origin.getContent());
        assertNodeListEquals(expected.getChilds(), origin.getChilds());
    }
}
