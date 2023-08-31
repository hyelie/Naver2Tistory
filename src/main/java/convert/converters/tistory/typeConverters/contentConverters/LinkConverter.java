package convert.converters.tistory.typeConverters.contentConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class LinkConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode linkNode) {
        return new ConvertResultVO(createLinkElement(linkNode));
    }

    private Element createLinkElement(ConvertedTreeNode linkNode){
        Element linkElement = new Element("a");
        String link = TypeConverter.convertContent(linkNode.getContent());
        linkElement.attr("href", link);
        linkElement.attr("target", "_blank");
        linkElement.attr("rel", "noopener");
        linkElement.text(link);
        
        return linkElement;
    }
}
