package scrapper.naver.sectionParsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;
import convert.scrappers.naver.sectionParsers.QuotationParser;
import utils.NodeTestUtils;

public class QuotationParserTest {
    SectionParser quotationParser = new QuotationParser();

    @Test
    public void testQuotationParser() throws Exception {
        // given
        String quotationHtml =
        "<div class=\"se-section se-section-quotation se-l-default\">" + 
            "<blockquote class=\"se-quotation-container\">" + 
                "<div class=\"se-module se-module-text se-quote\"><!-- SE-TEXT { --><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-288f920d-f0df-4792-bbf2-d16fc7a06c27\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-6a978899-0b19-4f1e-8e5c-4ad166a32767\">인</span><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-b7f29c51-e2e3-4eb6-9893-dc08f1e2451a\"><b>용</b></span><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-ccf8d03f-0efe-46af-b0a4-16f3054f2a14\">구</span></p><!-- } SE-TEXT --></div>" + 
                "<div class=\"se-module se-module-text se-cite\"><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-ba213008-263b-4fa5-9d67-3a4ce0b21104\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-d016fe4f-16c3-4f12-a600-903fe713955c\">인용구 출처</span></p></div>" + 
            "</blockquote>" + 
        "</div>";
        
        Document doc = Jsoup.parse(quotationHtml);
        Element quotationElement = doc.body().child(0);

        // when
        ConvertedTreeNode quotationNode = quotationParser.parseToTreeNode(quotationElement);

        // then
        NodeTestUtils.assertNodeTypeAndContent(quotationNode, StyleType.QUOTATION, "");

        ConvertedTreeNode quoteNode = quotationNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(quoteNode, StyleType.QUOTE, "");

        ConvertedTreeNode quoteParagraphNode = quoteNode.getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(quoteParagraphNode.getChilds().get(0), StyleType.CONTENT, "인");
        NodeTestUtils.assertNodeTypeAndContent(quoteParagraphNode.getChilds().get(1), StyleType.CONTENT, "");
        NodeTestUtils.assertNodeTypeAndContent(quoteParagraphNode.getChilds().get(1).getChilds().get(0), StyleType.BOLD, "용");
        NodeTestUtils.assertNodeTypeAndContent(quoteParagraphNode.getChilds().get(2), StyleType.CONTENT, "구");

        ConvertedTreeNode citeNode = quotationNode.getChilds().get(1);
        NodeTestUtils.assertNodeTypeAndContent(citeNode, StyleType.CITE, "");

        ConvertedTreeNode citeParagraphNode = citeNode.getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(citeParagraphNode.getChilds().get(0), StyleType.CONTENT, "인용구 출처");
    }
}

                        