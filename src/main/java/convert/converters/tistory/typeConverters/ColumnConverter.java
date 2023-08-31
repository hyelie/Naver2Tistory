package convert.converters.tistory.typeConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class ColumnConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode columnNode) {
        return new ConvertResultVO(new Element("td"), columnNode.getChilds());
    }
}
