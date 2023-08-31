package convert.converters.tistory.typeConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class HorizontalLineConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode horizontalLineNode) {
        Element horizontalLineElement = createHorizontalLineElement();

        return new ConvertResultVO(horizontalLineElement);
    }

    private Element createHorizontalLineElement(){
        Element horizontalLineElement = new Element("hr");
        horizontalLineElement.attr("contenteditable", "false");
        horizontalLineElement.attr("data-ke-type", "horizontalRule");
        horizontalLineElement.attr("data-ke-style", "style5");

        return horizontalLineElement;
    }
}