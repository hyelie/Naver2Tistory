package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;

public class NodeTestUtils {
    public static void assertNodeTypeAndContent(ConvertedTreeNode actual, StyleType expectedType, String expectedContent){
        assertNotNull(actual);
        assertEquals(expectedType, actual.getType());
        assertEquals(expectedContent, actual.getContent());
    }

    public static void assertNodeListEquals(List<ConvertedTreeNode> expected, List<ConvertedTreeNode> actual){
        assertNotNull(expected);
        assertNotNull(actual);

        assertEquals(expected.size(), actual.size());

        int len = expected.size();
        for(int i = 0; i<len; i++){
            assertNodeEquals(expected.get(i), actual.get(i));
        }
    }

    public static void assertNodeEquals(ConvertedTreeNode expected, ConvertedTreeNode actual){
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getType(), actual.getType());
        assertEquals(expected.getContent(), actual.getContent());
        assertNodeListEquals(expected.getChilds(), actual.getChilds());
    }

    public static void assertElementEquals(Element expected, Element actual){
        assertEquals(expected.outerHtml(), actual.outerHtml());
    }
}
