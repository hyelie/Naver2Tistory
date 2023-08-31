package converter.tistory.typeConverters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;
import convert.converters.tistory.typeConverters.ColumnConverter;
import utils.NodeTestUtils;

public class ColumnConverterTest {
    TypeConverter columnConverter = new ColumnConverter();

    @Test
    public void testColumnConverter() {
        // given
        ConvertedTreeNode colNode = ConvertedTreeNode.builder().type(StyleType.COLUMN).build();
        ConvertedTreeNode childNode1 = ConvertedTreeNode.builder().type(StyleType.NONE).build();
        colNode.appendChild(childNode1);
        ConvertedTreeNode childNode2 = ConvertedTreeNode.builder().type(StyleType.COLUMN).build();
        colNode.appendChild(childNode2);

        // when
        ConvertResultVO convertResultVO = columnConverter.convertAndReturnNextNodes(colNode);

        // then
        String rowHtml = "<td></td>";
        
        assertEquals(rowHtml, convertResultVO.getResult().outerHtml());
        NodeTestUtils.assertNodeListEquals(colNode.getChilds(), convertResultVO.getNextNodes());
    }
}
