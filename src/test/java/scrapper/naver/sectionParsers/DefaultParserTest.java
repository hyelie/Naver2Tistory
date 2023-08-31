package scrapper.naver.sectionParsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;
import convert.scrappers.naver.sectionParsers.DefaultParser;
import utils.NodeTestUtils;

public class DefaultParserTest {
    SectionParser defaultParser = new DefaultParser();

    @Test
    public void testDefaultParser() throws Exception {
        // given
        String defaultHtml =
        "<div class=\"se-section se-section-text se-l-default\">" + 
            "<div class=\"se-module se-module-text\">" + 
                "<p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-5f380014-ea0b-4511-9c67-9d53a7802343\">" +
                    "<span style=\"color:#000000;\" class=\"se-fs- se-ff-   \" id=\"SE-e83bf50e-542e-4330-835c-fa7e374d5635\">저화질 이미지</span>" +
                "</p>" +
            "</div>" + 
        "</div>";
        Document doc = Jsoup.parse(defaultHtml);
        Element defaultElement = doc.body().child(0);

        // when
        ConvertedTreeNode defaultNode = defaultParser.parseToTreeNode(defaultElement);

        NodeTestUtils.assertNodeTypeAndContent(defaultNode, StyleType.NONE, "저화질 이미지");
    }
}
