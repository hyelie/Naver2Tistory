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
import convert.converters.tistory.typeConverters.paragraphConverters.CenterParagraphConverter;
import utils.NodeTestUtils;

public class CenterParagraphConverterTest {
    TypeConverter centerParagraphConverter = new CenterParagraphConverter();

    @Test
    public void testCenterParagraphConverter() {
        // given
        ConvertedTreeNode centerParagraphNode = ConvertedTreeNode.builder().type(StyleType.PARAGRAPH_CENTER).build();
        ConvertedTreeNode childNode1 = ConvertedTreeNode.builder().type(StyleType.TEXT).build();
        centerParagraphNode.appendChild(childNode1);
        ConvertedTreeNode childNode2 = ConvertedTreeNode.builder().type(StyleType.TEXT).build();
        centerParagraphNode.appendChild(childNode2);

        // when
        ConvertResultVO convertResultVO = centerParagraphConverter.convertAndReturnNextNodes(centerParagraphNode);

        // then
        String centerParagraphHtml = "<p style=\"text-align: center;\"></p>";
        Document doc = Jsoup.parse(centerParagraphHtml);
        Element centerParagraphElement = doc.body().child(0);

        assertEquals(centerParagraphElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(centerParagraphNode.getChilds(), convertResultVO.getNextNodes());
    }
}
