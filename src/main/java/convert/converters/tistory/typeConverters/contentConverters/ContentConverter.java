 package convert.converters.tistory.typeConverters.contentConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class ContentConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode contentNode) {
        Element contentElement = createContentElement();

        if(contentNode.isLeaf()){
            String content = TypeConverter.convertContent(contentNode.getContent());
            contentElement.text(content);
        }

        return new ConvertResultVO(contentElement, contentNode.getChilds());
    }

    private Element createContentElement(){
        return new Element("content");
    }
}
