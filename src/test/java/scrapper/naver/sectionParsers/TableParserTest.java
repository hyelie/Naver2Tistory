package scrapper.naver.sectionParsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;
import convert.scrappers.naver.sectionParsers.TableParser;
import scrapper.naver.NodeTestUtils;

public class TableParserTest {
    SectionParser tableParser = new TableParser();

    @Test
    public void testTableParser() throws Exception {
        String tableHtml =
        "<div class=\"se-section se-section-table se-l-default se-section-align-\" style=\"width: 100%;\">" + 
            "<div class=\"se-table-container\">" + 
                "<table class=\"se-table-content\" style=\"\">" + 
                    "<tbody><tr class=\"se-tr\"><td class=\"se-cell\" colspan=\"1\" rowspan=\"1\" style=\"width: 33.33%; height: 43.0px;  \">" + 
                                "<div class=\"se-module se-module-text\"><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-cc827aef-b3b6-40ee-baec-0a2015aee081\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-d2c274ca-130f-40c7-89ab-eda4ba7ca1c5\">0, 0</span></p></div>" + 
                    "</td><td class=\"se-cell\" colspan=\"1\" rowspan=\"1\" style=\"width: 33.33%; height: 43.0px;  \">" + 
                                "<div class=\"se-module se-module-text\"><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-76687888-f414-4560-acee-1dc79526bde5\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-2552c00d-ae27-4043-be87-5cdf83c15d15\"><u>0, 1</u></span></p></div>" + 
                    "</td><td class=\"se-cell\" colspan=\"1\" rowspan=\"1\" style=\"width: 33.33%; height: 43.0px;  \">" + 
                                "<div class=\"se-module se-module-text\"><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-47196255-7f4d-49aa-b9c1-6bd3613c1a0a\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-de4c5fa6-1964-48c2-8cb2-9c0502396bb6\">0, 2</span></p></div>" + 
                    "</td></tr><tr class=\"se-tr\"><td class=\"se-cell\" colspan=\"1\" rowspan=\"1\" style=\"width: 33.33%; height: 43.0px;  \">" + 
                                "<div class=\"se-module se-module-text\"><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-3b94612b-f180-4373-bed9-1394f301955d\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-7fcdedb0-381c-4041-bfbe-06093c24ea3c\">1, 0</span></p></div>" + 
                    "</td><td class=\"se-cell\" colspan=\"1\" rowspan=\"1\" style=\"width: 33.33%; height: 43.0px;  \">" + 
                                "<div class=\"se-module se-module-text\"><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-9e1b035f-d703-41fc-974e-9938d7913d98\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-16dbe4ff-5c5b-4351-8a1c-a7850d462b7d\"><b>1, 1</b></span></p></div>" + 
                    "</td><td class=\"se-cell\" colspan=\"1\" rowspan=\"1\" style=\"width: 33.33%; height: 43.0px;  \">" + 
                                "<div class=\"se-module se-module-text\"><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-470c41bc-170a-44c5-a91b-bfc0e8b10af7\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-93f7ba5c-5421-46e9-abdc-f4bb8890844a\">1, 2</span></p></div>" + 
                    "</td></tr><tr class=\"se-tr\"><td class=\"se-cell\" colspan=\"1\" rowspan=\"1\" style=\"width: 33.33%; height: 43.0px;  \">" + 
                                "<div class=\"se-module se-module-text\"><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-55583b33-36cd-40dd-b13b-e7b41bec7a78\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-e0e7ded5-549c-4fd0-a45c-7ada838ce1da\">2, 0</span></p></div>" + 
                    "</td><td class=\"se-cell\" colspan=\"1\" rowspan=\"1\" style=\"width: 33.33%; height: 43.0px;  \">" + 
                                "<div class=\"se-module se-module-text\"><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-cd6fbc81-ff05-4316-b9a4-b9815d9af332\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-814adfc7-a095-46ab-b11f-38ed89a01a81\">2, 1</span></p></div><div class=\"se-module se-module-text\"><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-c39cb54b-10d8-4afa-93ea-c2369f8d69b6\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-5ad16eeb-3caf-44b0-9335-87b2e1e0fab3\">123123</span></p></div>" + 
                    "</td><td class=\"se-cell\" colspan=\"1\" rowspan=\"1\" style=\"width: 33.33%; height: 43.0px;  \">" + 
                                "<div class=\"se-module se-module-text\"><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-518f20c2-0aa7-4936-8a18-87c969dabb7c\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-37d0d3eb-3d52-46f5-a3a6-817963922991\">2, 2</span></p></div>" + 
                    "</td></tr></tbody>" + 
                "</table>" + 
            "</div>" + 
        "</div>";
        Document doc = Jsoup.parse(tableHtml);
        Element tableElement = doc.body().child(0);

        ConvertedTreeNode tableNode = tableParser.parseToTreeNode(tableElement);
        NodeTestUtils.assertNodeTypeAndContent(tableNode, StyleType.TABLE, "");

        // row 1
        ConvertedTreeNode rowNode1 = tableNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(rowNode1, StyleType.ROW, "");

        ConvertedTreeNode colNode1 = rowNode1.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(colNode1, StyleType.COLUMN, "");
        ConvertedTreeNode contentNode = colNode1.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(contentNode, StyleType.CONTENT, "0, 0");

        ConvertedTreeNode colNode2 = rowNode1.getChilds().get(1);
        NodeTestUtils.assertNodeTypeAndContent(colNode2, StyleType.COLUMN, "");
        contentNode = colNode2.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(contentNode, StyleType.CONTENT, "");
        ConvertedTreeNode underbarNode = contentNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(underbarNode, StyleType.UNDERBAR, "0, 1");

        ConvertedTreeNode colNode3 = rowNode1.getChilds().get(2);
        NodeTestUtils.assertNodeTypeAndContent(colNode3, StyleType.COLUMN, "");
        contentNode = colNode3.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(contentNode, StyleType.CONTENT, "0, 2");

        // row 3
        ConvertedTreeNode rowNode3 = tableNode.getChilds().get(2);
        NodeTestUtils.assertNodeTypeAndContent(rowNode3, StyleType.ROW, "");

        colNode1 = rowNode3.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(colNode1, StyleType.COLUMN, "");
        contentNode = colNode1.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(contentNode, StyleType.CONTENT, "2, 0");

        colNode2 = rowNode3.getChilds().get(1);
        NodeTestUtils.assertNodeTypeAndContent(colNode2, StyleType.COLUMN, "");
        contentNode = colNode2.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(contentNode, StyleType.CONTENT, "2, 1");
        contentNode = colNode2.getChilds().get(1).getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(contentNode, StyleType.CONTENT, "123123");

        colNode3 = rowNode3.getChilds().get(2);
        NodeTestUtils.assertNodeTypeAndContent(colNode3, StyleType.COLUMN, "");
        contentNode = colNode3.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(contentNode, StyleType.CONTENT, "2, 2");
    }
}
