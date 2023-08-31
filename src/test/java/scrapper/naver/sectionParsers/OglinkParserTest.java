package scrapper.naver.sectionParsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;
import convert.scrappers.naver.sectionParsers.OglinkParser;
import scrapper.naver.NodeTestUtils;

public class OglinkParserTest {
    SectionParser oglinkParser = new OglinkParser();

    @Test
    public void testOglinkParser() throws Exception {
        String oglinkHtml =
        "<div class=\"se-section se-section-oglink se-l-large_image se-section-align-\">" + 
            "<div class=\"se-module se-module-oglink\">" + 
                
                "<a href=\"https://hyelie.tistory.com\" class=\"se-oglink-info\" target=\"_blank\">" + 
                    "<div class=\"se-oglink-info-container\">" + 
                        "<strong class=\"se-oglink-title\">Note</strong>" + 
                        "<p class=\"se-oglink-summary\">소개소개</p>" + 
                        "<p class=\"se-oglink-url\">hyelie.tistory.com</p>" + 
                    "</div>" + 
                "</a>" + 
            "</div>" + 
        "</div>";
        Document doc = Jsoup.parse(oglinkHtml);
        Element oglinkElement = doc.body().child(0);

        ConvertedTreeNode oglinkNode = oglinkParser.parseToTreeNode(oglinkElement);

        NodeTestUtils.assertNodeTypeAndContent(oglinkNode, StyleType.NONE, "");
    }
}

                        