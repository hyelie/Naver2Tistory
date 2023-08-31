package scrapper.naver.sectionParsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;
import convert.scrappers.naver.sectionParsers.CodeParser;
import utils.NodeTestUtils;

public class CodeParserTest {
    SectionParser codeParser = new CodeParser();

    @Test
    public void testCodeParser() throws Exception {
        String codeHtml =
        "<div class=\"se-section se-section-code se-l-code_black\">" +
            "<div class=\"se-module se-module-code se-fs-fs13\">" + 
                "<div class=\"se-code-source\">" + 
                    "<div class=\"__se_code_view language-javascript\">" + 
                        "source code... 1 source code... 2 source code... 3 source code... 4" +
                    "</div>" + 
                "</div>" + 
            "</div>" +
        "</div>";
        Document doc = Jsoup.parse(codeHtml);
        Element codeElement = doc.body().child(0);

        ConvertedTreeNode codeNode = codeParser.parseToTreeNode(codeElement);

        NodeTestUtils.assertNodeTypeAndContent(codeNode, StyleType.CODE, "source code... 1 source code... 2 source code... 3 source code... 4");
    }
}
