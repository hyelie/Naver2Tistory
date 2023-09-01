package convert.converter.tistory.typeConverters.paragraphConverters;

import static org.junit.Assert.assertEquals;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;
import convert.converters.tistory.typeConverters.paragraphConverters.LeftParagraphConverter;
import utils.NodeTestUtils;

public class LeftParagraphConverterTest {
    TypeConverter leftParagraphConverter = new LeftParagraphConverter();

    @Test
    public void testLeftParagraphConverter() {
        // given
        ConvertedTreeNode leftParagraphNode = ConvertedTreeNode.builder().type(StyleType.PARAGRAPH_LEFT).build();
        ConvertedTreeNode childNode1 = ConvertedTreeNode.builder().type(StyleType.TEXT).build();
        leftParagraphNode.appendChild(childNode1);
        ConvertedTreeNode childNode2 = ConvertedTreeNode.builder().type(StyleType.TEXT).build();
        leftParagraphNode.appendChild(childNode2);

        // when
        ConvertResultVO convertResultVO = leftParagraphConverter.convertAndReturnNextNodes(leftParagraphNode);

        // then
        String leftParagraphHtml = "<p style=\"text-align: left;\"></p>";
        Document doc = Jsoup.parse(leftParagraphHtml);
        Element leftParagraphElement = doc.body().child(0);

        assertEquals(leftParagraphElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(leftParagraphNode.getChilds(), convertResultVO.getNextNodes());
    }
}
