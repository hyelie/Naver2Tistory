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
import convert.converters.tistory.typeConverters.contentConverters.BoldConverter;
import utils.NodeTestUtils;

public class BoldConverterTest {
    TypeConverter boldConverter = new BoldConverter();

    @Test
    public void testCodeConverter() {
        // given
        ConvertedTreeNode boldNode = ConvertedTreeNode.builder().type(StyleType.BOLD).build();
        ConvertedTreeNode tiltNode = ConvertedTreeNode.builder().type(StyleType.TILT).build();
        boldNode.appendChild(tiltNode);
        ConvertedTreeNode strikeNode = ConvertedTreeNode.builder().type(StyleType.STRIKE).build();
        tiltNode.appendChild(strikeNode);

        // when
        ConvertResultVO convertResultVO = boldConverter.convertAndReturnNextNodes(boldNode);

        // then
        String codeHtml =
        "<b></b>";
        Document doc = Jsoup.parse(codeHtml);
        Element boldElement = doc.body().child(0);

        assertEquals(boldElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(boldNode.getChilds(), convertResultVO.getNextNodes());
    }
}
