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
import convert.converters.tistory.typeConverters.paragraphConverters.RightParagraphConverter;
import utils.NodeTestUtils;

public class RightParagraphConverterTest {
    TypeConverter rightParagraphConverter = new RightParagraphConverter();

    @Test
    public void testRightParagraphConverter() {
        // given
        ConvertedTreeNode rightParagraphNode = ConvertedTreeNode.builder().type(StyleType.PARAGRAPH_RIGHT).build();
        ConvertedTreeNode childNode1 = ConvertedTreeNode.builder().type(StyleType.TEXT).build();
        rightParagraphNode.appendChild(childNode1);
        ConvertedTreeNode childNode2 = ConvertedTreeNode.builder().type(StyleType.TEXT).build();
        rightParagraphNode.appendChild(childNode2);

        // when
        ConvertResultVO convertResultVO = rightParagraphConverter.convertAndReturnNextNodes(rightParagraphNode);

        // then
        String rightParagraphHtml = "<p style=\"text-align: right;\"></p>";
        Document doc = Jsoup.parse(rightParagraphHtml);
        Element rightParagraphElement = doc.body().child(0);

        assertEquals(rightParagraphElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(rightParagraphNode.getChilds(), convertResultVO.getNextNodes());
    }
}
