package convert.converters.tistory.typeConverters.contentConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class BoldConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode boldNode) {
        Element boldElement = createBoldElement();

        if(boldNode.isLeaf()){
            String content = TypeConverter.convertContent(boldNode.getContent());
            boldElement.text(content);
        }

        return new ConvertResultVO(boldElement, boldNode.getChilds());
    }

    private Element createBoldElement(){
        return new Element("b");
    }
}
