package convert.converters.tistory.typeConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class DefaultConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode defaultNode) {
        Element defaultElement = new Element("none");
        return new ConvertResultVO(defaultElement, defaultNode.getChilds());
    }
}