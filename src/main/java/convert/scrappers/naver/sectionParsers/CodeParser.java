package convert.scrappers.naver.sectionParsers;

import org.jsoup.nodes.Element;

import convert.SupportType;
import convert.blogPost.ConvertedTreeNode;
import convert.scrappers.naver.SectionParser;

public class CodeParser extends SectionParser {
    @Override
    public ConvertedTreeNode parseToTreeNode(Element element) {
        String content = element.text();
        return ConvertedTreeNode.builder().type(SupportType.CODE).content(content).build();
    }
}
