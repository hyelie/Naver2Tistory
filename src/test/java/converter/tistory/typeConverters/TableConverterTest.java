package converter.tistory.typeConverters;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;
import convert.converters.tistory.typeConverters.TableConverter;
import utils.NodeTestUtils;

public class TableConverterTest {
    TypeConverter tableConverter = new TableConverter();

    @Test
    public void testTableConverter() {
        // given
        ConvertedTreeNode tableNode = ConvertedTreeNode.builder().type(StyleType.TABLE).build();
        ConvertedTreeNode childNode1 = ConvertedTreeNode.builder().type(StyleType.NONE).build();
        tableNode.appendChild(childNode1);
        ConvertedTreeNode childNode2 = ConvertedTreeNode.builder().type(StyleType.ROW).build();
        tableNode.appendChild(childNode2);

        // when
        ConvertResultVO convertResultVO = tableConverter.convertAndReturnNextNodes(tableNode);

        // then
        String tableHtml =
        "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">" + 
            "<tbody></tbody>" + 
        "</table>";
        Document doc = Jsoup.parse(tableHtml);
        Element tableElement = doc.body().child(0);

        NodeTestUtils.assertElementEquals(tableElement, convertResultVO.getResult());
        NodeTestUtils.assertNodeListEquals(tableNode.getChilds(), convertResultVO.getNextNodes());
    }
}
