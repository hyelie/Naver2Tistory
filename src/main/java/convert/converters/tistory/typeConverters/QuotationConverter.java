package convert.converters.tistory.typeConverters;

import java.util.List;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;

public class QuotationConverter implements TypeConverter {
    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode quotationNode) {
        /*
        structure of quotation
        --QUOTATION, 
        ----QUOTE, 
        ------TEXT, 
        --------PARAGRAPH_DEFAULT
        ----CITE, 
        ------TEXT, 
        --------PARAGRAPH_DEFAULT,
        */
        ConvertedTreeNode quoteNode = quotationNode.getChilds().get(0);
        List<ConvertedTreeNode> nextNodes = quoteNode.getChilds();

        if(hasCite(quotationNode)){
            ConvertedTreeNode citeNode = quotationNode.getChilds().get(1);
            ConvertedTreeNode newlineNode = ConvertedTreeNode.builder().type(StyleType.CONTENT).content("").build(); // Insert newline to distinguish quotation from cite
            nextNodes.add(newlineNode);
            nextNodes.addAll(citeNode.getChilds());
        }

        Element quotationElement = createQuotationElement();

        return new ConvertResultVO(quotationElement, nextNodes);
    }

    private Boolean hasCite(ConvertedTreeNode quotationNode){
        return quotationNode.getChilds().size() >= 2;
    }

    private Element createQuotationElement(){
        Element quotationElement = new Element("blockquote");
        quotationElement.attr("data-ke-style", "style1");

        return quotationElement;
    }
}