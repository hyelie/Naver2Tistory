package convert.scrappers.naver.sectionParsers;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;

public class CodeParser extends SectionParser {
    @Override
    public ConvertedTreeNode parseToTreeNode(Element element) {
        String content = element.text();
        return ConvertedTreeNode.builder().type(StyleType.CODE).content(content).build();
    }
}
