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
import convert.converters.tistory.typeConverters.contentConverters.TiltConverter;
import utils.NodeTestUtils;

public class TiltConverterTest {
    TypeConverter tiltConverter = new TiltConverter();

    @Test
    public void testTiltConverter() {
        // given
        ConvertedTreeNode tiltNode = ConvertedTreeNode.builder().type(StyleType.TILT).build();
        ConvertedTreeNode boldNode = ConvertedTreeNode.builder().type(StyleType.BOLD).build();
        tiltNode.appendChild(boldNode);
        ConvertedTreeNode strikeNode = ConvertedTreeNode.builder().type(StyleType.STRIKE).build();
        tiltNode.appendChild(strikeNode);

        // when
        ConvertResultVO convertResultVO = tiltConverter.convertAndReturnNextNodes(tiltNode);

        // then
        String tiltHtml = "<i></i>";
        Document doc = Jsoup.parse(tiltHtml);
        Element tiltElement = doc.body().child(0);

        assertEquals(tiltElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(tiltNode.getChilds(), convertResultVO.getNextNodes());
    }

    @Test
    public void testLeafTiltConverter() {
        // given
        ConvertedTreeNode tiltLeafNode = ConvertedTreeNode.builder().type(StyleType.TILT).content("tilt leaf 테스트").build();

        // when
        ConvertResultVO convertResultVO = tiltConverter.convertAndReturnNextNodes(tiltLeafNode);

        // then
        String tiltHtml = "<i>tilt leaf 테스트</i>";
        Document doc = Jsoup.parse(tiltHtml);
        Element tiltElement = doc.body().child(0);

        assertEquals(tiltElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(tiltLeafNode.getChilds(), convertResultVO.getNextNodes());
    }
}
