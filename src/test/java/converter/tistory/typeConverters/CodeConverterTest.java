package converter.tistory.typeConverters;

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
import convert.converters.tistory.typeConverters.CodeConverter;
import utils.NodeTestUtils;

public class CodeConverterTest {
    TypeConverter codeConverter = new CodeConverter();

    @Test
    public void testCodeConverter() {
        // given
        ConvertedTreeNode codeNode = ConvertedTreeNode.builder().type(StyleType.CODE).build();
        ConvertedTreeNode childNode1 = ConvertedTreeNode.builder().type(StyleType.NONE).build();
        codeNode.appendChild(childNode1);
        ConvertedTreeNode childNode2 = ConvertedTreeNode.builder().type(StyleType.ROW).build();
        codeNode.appendChild(childNode2);

        // when
        ConvertResultVO convertResultVO = codeConverter.convertAndReturnNextNodes(codeNode);

        // then
        String codeHtml =
        "<pre class=\"bash\" data-ke-language=\"bash\" data-ke-type=\"codeblock\">" + 
            "<code></code>" + 
        "</pre>";
        Document doc = Jsoup.parse(codeHtml);
        Element codeElement = doc.body().child(0);

        assertEquals(codeElement.outerHtml(), convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(new ArrayList<>(), convertResultVO.getNextNodes());
    }
}
