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
import convert.converters.tistory.typeConverters.paragraphConverters.DefaultParagraphConverter;
import utils.NodeTestUtils;

public class DefaultParagraphConverterTest {
    TypeConverter centerParagraphConverter = new DefaultParagraphConverter();

    @Test
    public void testCenterParagraphConverter() {
        // given
        ConvertedTreeNode defaultParagraphNode = ConvertedTreeNode.builder().type(StyleType.PARAGRAPH_DEFAULT).build();
        ConvertedTreeNode childNode1 = ConvertedTreeNode.builder().type(StyleType.TEXT).build();
        defaultParagraphNode.appendChild(childNode1);
        ConvertedTreeNode childNode2 = ConvertedTreeNode.builder().type(StyleType.TEXT).build();
        defaultParagraphNode.appendChild(childNode2);

        // when
        ConvertResultVO convertResultVO = centerParagraphConverter.convertAndReturnNextNodes(defaultParagraphNode);

        // then
        String defaultParagraphHtml = "<p></p>";
        Document doc = Jsoup.parse(defaultParagraphHtml);
        Element defaultParagraphElement = doc.body().child(0);

        assertEquals(defaultParagraphElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(defaultParagraphNode.getChilds(), convertResultVO.getNextNodes());
    }
}
