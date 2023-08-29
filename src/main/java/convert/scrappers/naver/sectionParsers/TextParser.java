package convert.scrappers.naver.sectionParsers;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.scrappers.naver.SectionParser;

public class TextParser extends SectionParser {
    @Override
    public ConvertedTreeNode parseToTreeNode(Element element) {
        return parseTextModule(element.child(0)); // se-module-text
    }
    
}
