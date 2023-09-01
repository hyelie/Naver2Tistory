package convert.converters.tistory.typeConverters.paragraphConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class DefaultParagraphConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode paragraphNode) {
        return new ConvertResultVO(createParagraphElement(), paragraphNode.getChilds());
    }

    private Element createParagraphElement(){
        return new Element("p");
    }
}
