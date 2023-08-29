package convert.scrappers.naver.sectionParsers;

import org.jsoup.nodes.Element;

import convert.SupportType;
import convert.blogPost.ConvertedTreeNode;
import convert.scrappers.naver.SectionParser;

public class QuotationParser extends SectionParser {
    @Override
    public ConvertedTreeNode parseToTreeNode(Element element) {
        ConvertedTreeNode quotationNode = ConvertedTreeNode.builder().type(SupportType.QUOTATION).build();

        Element quotationContainer = element.child(0);

        // quote
        ConvertedTreeNode quoteNode = ConvertedTreeNode.builder().type(SupportType.QUOTE).build();
        Element quoteModule = quotationContainer.child(0);
        quoteNode.appendChild(parseTextModule(quoteModule));
        quotationNode.appendChild(quoteNode);

        // cite
        ConvertedTreeNode citeNode = ConvertedTreeNode.builder().type(SupportType.CITE).build();
        Element citeModule = quotationContainer.child(1);
        citeNode.appendChild(parseTextModule(citeModule));
        quotationNode.appendChild(citeNode);

        return quotationNode;
    }
}
