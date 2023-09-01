package convert.converters.tistory.typeConverters.contentConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class UnderlineConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode UnderlineNode) {
        Element UnderlineElement = createUnderlineElement();

        if(UnderlineNode.isLeaf()){
            String content = TypeConverter.convertContent(UnderlineNode.getContent());
            UnderlineElement.text(content);
        }

        return new ConvertResultVO(UnderlineElement, UnderlineNode.getChilds());
    }

    private Element createUnderlineElement(){
        return new Element("u");
    }
}
