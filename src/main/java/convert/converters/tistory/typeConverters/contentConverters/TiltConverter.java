package convert.converters.tistory.typeConverters.contentConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class TiltConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode tiltNode) {
        Element tiltElement = createTiltElement();

        if(tiltNode.isLeaf()){
            String content = TypeConverter.convertContent(tiltNode.getContent());
            tiltElement.text(content);
        }

        return new ConvertResultVO(tiltElement, tiltNode.getChilds());
    }

    private Element createTiltElement(){
        return new Element("i");
    }
}
