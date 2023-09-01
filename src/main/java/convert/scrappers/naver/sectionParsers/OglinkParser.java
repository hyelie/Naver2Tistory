package convert.scrappers.naver.sectionParsers;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;

public class OglinkParser extends SectionParser {
    @Override
    public ConvertedTreeNode parseToTreeNode(Element element) {
        return ConvertedTreeNode.builder().type(StyleType.NONE).build();
    }
}
