package scrapper.naver.sectionParsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;
import convert.scrappers.naver.sectionParsers.TextParser;
import utils.NodeTestUtils;

public class TextParserTest {
    SectionParser textParser = new TextParser();

    @Test
    public void testNestedText() throws Exception {
        // given
        String nestedTextHtml =
        "<div class=\"se-section se-section-text se-l-default\">" + 
            "<div class=\"se-module se-module-text\">" + 
                    "<p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-4de09800-c74a-457a-bd29-f116eb3c8e72\"><span style=\"color:#000000;\" class=\"se-fs-fs24 se-ff-   \" id=\"SE-a01e8aac-8e05-4500-8881-982fa4aae220\"><strike><u><i><b>대제목1 제목1</b></i></u></strike></span></p>" + 
            "</div>" + 
        "</div>";
        Document doc = Jsoup.parse(nestedTextHtml);
        Element nestedTextElement = doc.body().child(0);

        // when
        ConvertedTreeNode textNode = textParser.parseToTreeNode(nestedTextElement);

        // then
        NodeTestUtils.assertNodeTypeAndContent(textNode, StyleType.TEXT, "");

        ConvertedTreeNode paragraphNode = textNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(paragraphNode, StyleType.PARAGRAPH_DEFAULT, "");

        ConvertedTreeNode contentNode = paragraphNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(contentNode, StyleType.CONTENT, "");

        ConvertedTreeNode strikeNode = contentNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(strikeNode, StyleType.STRIKE, "");

        ConvertedTreeNode UnderlineNode = strikeNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(UnderlineNode, StyleType.UNDERLINE, "");

        ConvertedTreeNode tiltNode = UnderlineNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(tiltNode, StyleType.TILT, "");

        ConvertedTreeNode boldNode = tiltNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(boldNode, StyleType.BOLD, "대제목1 제목1");
    }

    @Test
    public void testSeparatedText() throws Exception {
        // given
        String nestedTextHtml =
        "<div class=\"se-section se-section-text se-l-default\">" + 
            "<div class=\"se-module se-module-text\">" + 
                "<p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-185b6da6-aaa2-4ff5-870a-421fefc6ffda\"><span style=\"color:#000000;\" class=\"se-fs- se-ff- se-weight-unset  \" id=\"SE-e289c0d3-904d-4341-a107-f241718f055f\">대제목 제목1 설명대제목 제</span><span style=\"color:#000000;\" class=\"se-fs- se-ff-   \" id=\"SE-ae27d4f7-2277-4c4c-b5bd-2c39dce6b32e\"><b>목1 설명대제</b></span><span style=\"color:#000000;\" class=\"se-fs- se-ff- se-weight-unset  \" id=\"SE-f57f5b0f-c988-4f5f-91cc-19e72ce68be1\">목 제목1 설명</span><span style=\"color:#000000;\" class=\"se-fs- se-ff- se-weight-unset  \" id=\"SE-8c3cdfb8-e49d-4ddd-9371-a583abf4cc7f\"><u>대제목 제목</u></span><span style=\"color:#000000;\" class=\"se-fs- se-ff- se-weight-unset  \" id=\"SE-03578c98-f91d-4776-93a8-7aa6d34ad255\">1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목</span><span style=\"color:#000000;\" class=\"se-fs- se-ff- se-weight-unset  \" id=\"SE-82f15892-64b5-414c-9dc0-a25735d30e3d\"><i>1 설명대제</i></span><span style=\"color:#000000;\" class=\"se-fs- se-ff- se-weight-unset  \" id=\"SE-7badd5f4-84f2-4423-a35e-1fabcb7aa038\">목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명</span></p>" +
            "</div>" + 
        "</div>";
        Document doc = Jsoup.parse(nestedTextHtml);
        Element separatedTextElement = doc.body().child(0);

        // when
        ConvertedTreeNode textNode = textParser.parseToTreeNode(separatedTextElement);

        // then
        NodeTestUtils.assertNodeTypeAndContent(textNode, StyleType.TEXT, "");

        ConvertedTreeNode paragraphNode = textNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(paragraphNode, StyleType.PARAGRAPH_DEFAULT, "");

        ConvertedTreeNode childNode0 = paragraphNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(childNode0, StyleType.CONTENT, "대제목 제목1 설명대제목 제");

        ConvertedTreeNode childNode1 = paragraphNode.getChilds().get(1);
        NodeTestUtils.assertNodeTypeAndContent(childNode1, StyleType.CONTENT, "");
        ConvertedTreeNode boldNode1 = childNode1.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(boldNode1, StyleType.BOLD, "목1 설명대제");

        ConvertedTreeNode childNode2 = paragraphNode.getChilds().get(2);
        NodeTestUtils.assertNodeTypeAndContent(childNode2, StyleType.CONTENT, "목 제목1 설명");

        ConvertedTreeNode childNode3 = paragraphNode.getChilds().get(3);
        NodeTestUtils.assertNodeTypeAndContent(childNode3, StyleType.CONTENT, "");
        ConvertedTreeNode UnderlineNode3 = childNode3.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(UnderlineNode3, StyleType.UNDERLINE, "대제목 제목");

        ConvertedTreeNode childNode4 = paragraphNode.getChilds().get(4);
        NodeTestUtils.assertNodeTypeAndContent(childNode4, StyleType.CONTENT, "1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목");

        ConvertedTreeNode childNode5 = paragraphNode.getChilds().get(5);
        NodeTestUtils.assertNodeTypeAndContent(childNode5, StyleType.CONTENT, "");
        ConvertedTreeNode tiltNode5 = childNode5.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(tiltNode5, StyleType.TILT, "1 설명대제");

        ConvertedTreeNode childNode6 = paragraphNode.getChilds().get(6);
        NodeTestUtils.assertNodeTypeAndContent(childNode6, StyleType.CONTENT, "목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명");
    }

    @Test
    public void testAlignedText() throws Exception {
        String alignedText =
        "<div class=\"se-section se-section-text se-l-default\">" + 
            "<div class=\"se-module se-module-text\">" + 
                "<p class=\" se-text-paragraph se-text-paragraph-align-right \"  style=\" \"  id=\" SE-1aaf8bf2-3d90-406f-8db6-b3a3a2dad854\" ><span style=\" color:#000000;\"  class=\" se-fs- se-ff-   \"  id=\" SE-497a59df-6c4a-4154-a794-361362b6e5af\" >본문 내용 2 (우측 정렬)</span></p>" + 
                "<p class=\" se-text-paragraph se-text-paragraph-align-center \"  style=\" \"  id=\" SE-af715d87-6af7-4027-be68-c809069eb89a\" ><span style=\" color:#000000;\"  class=\" se-fs- se-ff-   \"  id=\" SE-67ece3fd-ac2a-4401-8484-cca0ca238603\" >본문 내용 2 (가운데 정렬)</span></p>" + 
                "<p class=\" se-text-paragraph se-text-paragraph-align-justify \"  style=\" \"  id=\" SE-dc4bbc8b-f9a8-44c6-9b97-d6553eaf1ba5\" ><span style=\" color:#000000;\"  class=\" se-fs- se-ff-   \"  id=\" SE-43291a93-c6cc-4094-96f9-456b51b5f7d0\" >이미지 중첩</span></p>" + 
            "</div>" + 
        "</div>";
        Document doc = Jsoup.parse(alignedText);
        Element alignedTextElement = doc.body().child(0);

        ConvertedTreeNode textNode = textParser.parseToTreeNode(alignedTextElement);
        NodeTestUtils.assertNodeTypeAndContent(textNode, StyleType.TEXT, "");

        ConvertedTreeNode rightAlignNode = textNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(rightAlignNode, StyleType.PARAGRAPH_RIGHT, "");
        ConvertedTreeNode rightAlignTextNode = rightAlignNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(rightAlignTextNode, StyleType.CONTENT, "본문 내용 2 (우측 정렬)");

        ConvertedTreeNode centerAlignNode = textNode.getChilds().get(1);
        NodeTestUtils.assertNodeTypeAndContent(centerAlignNode, StyleType.PARAGRAPH_CENTER, "");
        ConvertedTreeNode centerAlignTextNode = centerAlignNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(centerAlignTextNode, StyleType.CONTENT, "본문 내용 2 (가운데 정렬)");

        ConvertedTreeNode leftAlignNode = textNode.getChilds().get(2);
        NodeTestUtils.assertNodeTypeAndContent(leftAlignNode, StyleType.PARAGRAPH_LEFT, "");
        ConvertedTreeNode leftAlignTextNode = leftAlignNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(leftAlignTextNode, StyleType.CONTENT, "이미지 중첩");


    }
}

