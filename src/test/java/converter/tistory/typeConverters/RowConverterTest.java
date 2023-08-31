package converter.tistory.typeConverters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;
import convert.converters.tistory.typeConverters.RowConverter;
import utils.NodeTestUtils;

public class RowConverterTest {
    TypeConverter rowConverter = new RowConverter();

    @Test
    public void testTableConverter() {
        // given
        ConvertedTreeNode rowNode = ConvertedTreeNode.builder().type(StyleType.ROW).build();
        ConvertedTreeNode childNode1 = ConvertedTreeNode.builder().type(StyleType.NONE).build();
        rowNode.appendChild(childNode1);
        ConvertedTreeNode childNode2 = ConvertedTreeNode.builder().type(StyleType.COLUMN).build();
        rowNode.appendChild(childNode2);

        // when
        ConvertResultVO convertResultVO = rowConverter.convertAndReturnNextNodes(rowNode);

        // then
        String rowHtml = "<tr></tr>";

        assertEquals(rowHtml, convertResultVO.getResult().outerHtml());
        //NodeTestUtils.assertElementEquals(rowElement, convertResultVO.getResult());
        NodeTestUtils.assertNodeListEquals(rowNode.getChilds(), convertResultVO.getNextNodes());
    }
}
