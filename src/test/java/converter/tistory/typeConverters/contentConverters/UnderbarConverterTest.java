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
import convert.converters.tistory.typeConverters.contentConverters.UnderbarConverter;
import utils.NodeTestUtils;

public class UnderbarConverterTest {
    TypeConverter underbarConverter = new UnderbarConverter();

    @Test
    public void testCodeConverter() {
        // given
        ConvertedTreeNode underbarNode = ConvertedTreeNode.builder().type(StyleType.UNDERBAR).build();
        ConvertedTreeNode tiltNode = ConvertedTreeNode.builder().type(StyleType.TILT).build();
        underbarNode.appendChild(tiltNode);
        ConvertedTreeNode strikeNode = ConvertedTreeNode.builder().type(StyleType.STRIKE).build();
        tiltNode.appendChild(strikeNode);

        // when
        ConvertResultVO convertResultVO = underbarConverter.convertAndReturnNextNodes(underbarNode);

        // then
        String underbarHtml = "<u></u>";
        Document doc = Jsoup.parse(underbarHtml);
        Element underbarElement = doc.body().child(0);

        assertEquals(underbarElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(underbarNode.getChilds(), convertResultVO.getNextNodes());
    }
}
