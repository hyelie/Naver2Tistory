package converter.tistory.typeConverters.contentConverters;

import static org.junit.Assert.assertEquals;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;
import convert.converters.tistory.typeConverters.contentConverters.ContentConverter;
import utils.NodeTestUtils;

public class ContentConverterTest {
    TypeConverter contentConverter = new ContentConverter();

    @Test
    public void testContentConverter() {
        // given
        ConvertedTreeNode contentNode = ConvertedTreeNode.builder().type(StyleType.CONTENT).build();
        ConvertedTreeNode tiltNode = ConvertedTreeNode.builder().type(StyleType.TILT).build();
        contentNode.appendChild(tiltNode);
        ConvertedTreeNode strikeNode = ConvertedTreeNode.builder().type(StyleType.STRIKE).build();
        tiltNode.appendChild(strikeNode);

        // when
        ConvertResultVO convertResultVO = contentConverter.convertAndReturnNextNodes(contentNode);

        // then
        String contentHtml = "<span></span>";
        Document doc = Jsoup.parse(contentHtml);
        Element contentElement = doc.body().child(0);

        assertEquals(contentElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(contentNode.getChilds(), convertResultVO.getNextNodes());
    }

    @Test
    public void testLeafContentConverter() {
        // given
        ConvertedTreeNode contentLeafNode = ConvertedTreeNode.builder().type(StyleType.CONTENT).content("content leaf 테스트").build();

        // when
        ConvertResultVO convertResultVO = contentConverter.convertAndReturnNextNodes(contentLeafNode);

        // then
        String contentHtml = "<content>content leaf 테스트</content>";
        Document doc = Jsoup.parse(contentHtml);
        Element contentElement = doc.body().child(0);

        assertEquals(contentElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(contentLeafNode.getChilds(), convertResultVO.getNextNodes());
    }
}
