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
import convert.converters.tistory.typeConverters.contentConverters.StrikeConverter;
import utils.NodeTestUtils;

public class StrikeConverterTest {
    TypeConverter strikeConverter = new StrikeConverter();

    @Test
    public void testStrikeConverter() {
        // given
        ConvertedTreeNode strikeNode = ConvertedTreeNode.builder().type(StyleType.STRIKE).build();
        ConvertedTreeNode tiltNode = ConvertedTreeNode.builder().type(StyleType.TILT).build();
        strikeNode.appendChild(tiltNode);
        ConvertedTreeNode boldNode = ConvertedTreeNode.builder().type(StyleType.BOLD).build();
        tiltNode.appendChild(boldNode);

        // when
        ConvertResultVO convertResultVO = strikeConverter.convertAndReturnNextNodes(strikeNode);

        // then
        String strikeHtml = "<strike></strike>";
        Document doc = Jsoup.parse(strikeHtml);
        Element strikeElement = doc.body().child(0);

        assertEquals(strikeElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(strikeNode.getChilds(), convertResultVO.getNextNodes());
    }

    @Test
    public void testLeafStrikeConverter() {
        // given
        ConvertedTreeNode strikeLeafNode = ConvertedTreeNode.builder().type(StyleType.STRIKE).content("strike leaf 테스트").build();

        // when
        ConvertResultVO convertResultVO = strikeConverter.convertAndReturnNextNodes(strikeLeafNode);

        // then
        String strikeHtml = "<strike>strike leaf 테스트</strike>";
        Document doc = Jsoup.parse(strikeHtml);
        Element strikeElement = doc.body().child(0);

        assertEquals(strikeElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(strikeLeafNode.getChilds(), convertResultVO.getNextNodes());
    }
}
