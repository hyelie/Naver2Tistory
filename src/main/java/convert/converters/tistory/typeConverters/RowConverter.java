package convert.converters.tistory.typeConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class RowConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode rowNode) {
        return new ConvertResultVO(new Element("tr"), rowNode.getChilds());
    }
}
