package convert.converters.tistory.typeConverters;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class CodeConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode codeNode) {
        Element preElement = createPreElement();

        Element codeElement = createCodeElement();
        codeElement.text(codeNode.getContent());
        preElement.appendChild(codeElement);

        return new ConvertResultVO(preElement);
    }

    private Element createPreElement(){
        Element preElement = new Element("pre");
        preElement.attr("class", "bash");
        preElement.attr("data-ke-language", "bash");
        preElement.attr("data-ke-type", "codeblock");
        return preElement;
    }

    private Element createCodeElement(){
        return new Element("code");
    }
}
