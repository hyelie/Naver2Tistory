package convert.converters.tistory.typeConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class TableConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode tableNode) {
        Element tableElement = createTableElement();
        Element tbodyElement = createTbodyElement();
        tableElement.appendChild(tbodyElement);
        return new ConvertResultVO(tableElement, tableNode.getChilds());
    }

    private Element createTableElement(){
        Element tableElement = new Element("table");
        tableElement.attr("style", "border-collapse: collapse; width: 100%;");
        tableElement.attr("border", "1");

        return tableElement;
    }

    private Element createTbodyElement(){
        return new Element("tbody");
    }
}