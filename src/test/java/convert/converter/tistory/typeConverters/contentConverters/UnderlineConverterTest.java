package convert.converter.tistory.typeConverters.contentConverters;

import static org.junit.Assert.assertEquals;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;
import convert.converters.tistory.typeConverters.contentConverters.UnderlineConverter;
import utils.NodeTestUtils;

public class UnderlineConverterTest {
    TypeConverter UnderlineConverter = new UnderlineConverter();

    @Test
    public void testUnderlineConverter() {
        // given
        ConvertedTreeNode UnderlineNode = ConvertedTreeNode.builder().type(StyleType.UNDERLINE).build();
        ConvertedTreeNode tiltNode = ConvertedTreeNode.builder().type(StyleType.TILT).build();
        UnderlineNode.appendChild(tiltNode);
        ConvertedTreeNode strikeNode = ConvertedTreeNode.builder().type(StyleType.STRIKE).build();
        tiltNode.appendChild(strikeNode);

        // when
        ConvertResultVO convertResultVO = UnderlineConverter.convertAndReturnNextNodes(UnderlineNode);

        // then
        String UnderlineHtml = "<u></u>";
        Document doc = Jsoup.parse(UnderlineHtml);
        Element UnderlineElement = doc.body().child(0);

        assertEquals(UnderlineElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(UnderlineNode.getChilds(), convertResultVO.getNextNodes());
    }

    @Test
    public void testLeafUnderlineConverter() {
        // given
        ConvertedTreeNode UnderlineLeafNode = ConvertedTreeNode.builder().type(StyleType.TILT).content("Underline leaf 테스트").build();

        // when
        ConvertResultVO convertResultVO = UnderlineConverter.convertAndReturnNextNodes(UnderlineLeafNode);

        // then
        String UnderlineHtml = "<u>Underline leaf 테스트</u>";
        Document doc = Jsoup.parse(UnderlineHtml);
        Element UnderlineElement = doc.body().child(0);

        assertEquals(UnderlineElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(UnderlineLeafNode.getChilds(), convertResultVO.getNextNodes());
    }
}
