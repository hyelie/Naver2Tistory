package convert.scrappers.naver.sectionParsers;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;

public class QuotationParser extends SectionParser {
    @Override
    public ConvertedTreeNode parseToTreeNode(Element element) {
        ConvertedTreeNode quotationNode = ConvertedTreeNode.builder().type(StyleType.QUOTATION).build();

        Element quotationContainer = element.child(0);

        // quote
        ConvertedTreeNode quoteNode = ConvertedTreeNode.builder().type(StyleType.QUOTE).build();
        Element quoteModule = quotationContainer.child(0);
        quoteNode.appendChild(parseTextModule(quoteModule));
        quotationNode.appendChild(quoteNode);

        // cite
        ConvertedTreeNode citeNode = ConvertedTreeNode.builder().type(StyleType.CITE).build();
        Element citeModule = quotationContainer.child(1);
        citeNode.appendChild(parseTextModule(citeModule));
        quotationNode.appendChild(citeNode);

        return quotationNode;
    }
}
