package convert.converter.tistory.typeConverters;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;
import convert.converters.tistory.typeConverters.HorizontalLineConverter;
import utils.NodeTestUtils;

public class HorizontalLineConverterTest {
    TypeConverter horizontalLineConverter = new HorizontalLineConverter();

    @Test
    public void testHoriaonztalLineConverter() {
        // given
        ConvertedTreeNode horizontalNode = ConvertedTreeNode.builder().type(StyleType.HORIZONTALLINE).build();
        ConvertedTreeNode childNode1 = ConvertedTreeNode.builder().type(StyleType.NONE).build();
        horizontalNode.appendChild(childNode1);
        ConvertedTreeNode childNode2 = ConvertedTreeNode.builder().type(StyleType.COLUMN).build();
        horizontalNode.appendChild(childNode2);

        // when
        ConvertResultVO convertResultVO = horizontalLineConverter.convertAndReturnNextNodes(horizontalNode);

        // then
        String horizontalLineHtml = "<hr contenteditable=\"false\" data-ke-type=\"horizontalRule\" data-ke-style=\"style5\"/>";
        Document doc = Jsoup.parse(horizontalLineHtml);
        Element horizontalLineElement = doc.body().child(0);

        NodeTestUtils.assertElementEquals(horizontalLineElement, convertResultVO.getResult());
        NodeTestUtils.assertNodeListEquals(new ArrayList<>(), convertResultVO.getNextNodes());
    }
}
