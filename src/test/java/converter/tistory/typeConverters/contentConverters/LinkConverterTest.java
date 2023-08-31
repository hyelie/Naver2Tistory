package converter.tistory.typeConverters.contentConverters;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;
import convert.converters.tistory.typeConverters.contentConverters.LinkConverter;
import utils.NodeTestUtils;

public class LinkConverterTest {
    TypeConverter linkConverter = new LinkConverter();

    @Test
    public void testCodeConverter() {
        // given
        ConvertedTreeNode linkNode = ConvertedTreeNode.builder().type(StyleType.LINK).content("https://hyelie.com").build();
        ConvertedTreeNode tiltNode = ConvertedTreeNode.builder().type(StyleType.TILT).build();
        linkNode.appendChild(tiltNode);
        ConvertedTreeNode strikeNode = ConvertedTreeNode.builder().type(StyleType.STRIKE).build();
        tiltNode.appendChild(strikeNode);

        // when
        ConvertResultVO convertResultVO = linkConverter.convertAndReturnNextNodes(linkNode);

        // then
        String linkHtml = "<a href=\"https://hyelie.com\" target=\"_blank\" rel=\"noopener\">https://hyelie.com</a>";
        Document doc = Jsoup.parse(linkHtml);
        Element linkElement = doc.body().child(0);

        assertEquals(linkElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(new ArrayList<>(), convertResultVO.getNextNodes());
    }
}
