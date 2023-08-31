package convert.converters.tistory.typeConverters.contentConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class StrikeConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode strikeNode) {
        Element strikElement = createStrkieElement();

        if(strikeNode.isLeaf()){
            String content = TypeConverter.convertContent(strikeNode.getContent());
            strikElement.text(content);
        }

        return new ConvertResultVO(strikElement, strikeNode.getChilds());
    }

    private Element createStrkieElement(){
        return new Element("strike");
    }
}
