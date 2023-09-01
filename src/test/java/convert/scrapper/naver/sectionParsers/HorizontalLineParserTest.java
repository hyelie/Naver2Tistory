package convert.scrapper.naver.sectionParsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;
import convert.scrappers.naver.sectionParsers.HorizontalLineParser;
import utils.NodeTestUtils;

public class HorizontalLineParserTest {
    SectionParser horizontalLineParser = new HorizontalLineParser();

    @Test
    public void testHorizontalLineParser() throws Exception {
        // given
        String horizontalLineHtml =
        "<div class=\"se-section se-section-horizontalLine se-l-default se-section-align-\">" + 
            "<div class=\"se-module se-module-horizontalLine\">" + 
                "<hr class=\"se-hr\">" + 
            "</div>" + 
        "</div>";
        Document doc = Jsoup.parse(horizontalLineHtml);
        Element horizontalLineElement = doc.body().child(0);

        // when
        ConvertedTreeNode horizontalNode = horizontalLineParser.parseToTreeNode(horizontalLineElement);

        NodeTestUtils.assertNodeTypeAndContent(horizontalNode, StyleType.HORIZONTALLINE, "");
    }
}
