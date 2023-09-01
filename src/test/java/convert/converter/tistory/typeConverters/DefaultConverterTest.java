package convert.converter.tistory.typeConverters;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;
import convert.converters.tistory.typeConverters.DefaultConverter;
import utils.NodeTestUtils;

public class DefaultConverterTest {
    TypeConverter defaulConverter = new DefaultConverter();

    @Test
    public void testDefaultConverter() {
        // given
        ConvertedTreeNode defaultNode = ConvertedTreeNode.builder().type(StyleType.NONE).build();
        ConvertedTreeNode childNode1 = ConvertedTreeNode.builder().type(StyleType.NONE).build();
        defaultNode.appendChild(childNode1);
        ConvertedTreeNode childNode2 = ConvertedTreeNode.builder().type(StyleType.COLUMN).build();
        defaultNode.appendChild(childNode2);

        // when
        ConvertResultVO convertResultVO = defaulConverter.convertAndReturnNextNodes(defaultNode);

        // then
        String defaultHtml = "<none></none>";
        Document doc = Jsoup.parse(defaultHtml);
        Element defaultElement = doc.body().child(0);

        NodeTestUtils.assertElementEquals(defaultElement, convertResultVO.getResult());
        NodeTestUtils.assertNodeListEquals(defaultNode.getChilds(), convertResultVO.getNextNodes());
    }
}
