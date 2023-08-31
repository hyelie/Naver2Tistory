package convert.converters.tistory.typeConverters.contentConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class UnderbarConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode underbarNode) {
        Element underbarElement = createUnderbarElement();

        if(underbarNode.isLeaf()){
            String content = TypeConverter.convertContent(underbarNode.getContent());
            underbarElement.text(content);
        }

        return new ConvertResultVO(underbarElement, underbarNode.getChilds());
    }

    private Element createUnderbarElement(){
        return new Element("u");
    }
}
