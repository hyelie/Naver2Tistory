package scrapper.naver.sectionParsers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;
import convert.scrappers.naver.sectionParsers.ImageParser;
import utils.NodeTestUtils;

public class ImageParserTest {
    SectionParser imageParser = new ImageParser();

    @Test
    public void testImageParserWithCaption() throws Exception {
        // given
        String imageWithCaptionHtml =
        "<div class=\"se-section se-section-image se-l-default se-section-align-\">" + 
                "<div class=\"se-module se-module-image\" style=\"\">" + 
                    "<a class=\"se-module-image-link __se_image_link __se_link\" style=\"\" onclick=\"return false;\" data-linktype=\"img\" data-linkdata=\"{&quot;id&quot; : &quot;SE-553d8212-fe88-41d2-a469-7f54c162240d&quot;, &quot;src&quot; : &quot;https://blogpfthumb-phinf.pstatic.net/20120504_73/jhi990823_1336138420833_S02En4_jpg/wallpaper-33614.jpg&quot;, &quot;originalWidth&quot; : &quot;1280&quot;, &quot;originalHeight&quot; : &quot;960&quot;, &quot;linkUse&quot; : &quot;false&quot;, &quot;link&quot; : &quot;&quot;}\" area-hidden=\"true\">" + 
                        "<img src=\"https://blogpfthumb-phinf.pstatic.net/20120504_73/jhi990823_1336138420833_S02En4_jpg/wallpaper-33614.jpg\" alt=\"\" class=\"se-image-resource egjs-visible\">" + 
                    "</a>" + 
                "</div>" + 
            "<div class=\"se-module se-module-text se-caption\"><p class=\"se-text-paragraph se-text-paragraph-align- \" style=\"\" id=\"SE-1d446049-4f57-4b0a-870d-47dc70e28a95\"><span style=\"\" class=\"se-fs- se-ff-   \" id=\"SE-789e0226-7cd8-48c1-ad35-fd7df407f27d\">고화질 이미지 캡션</span></p></div>" + 
        "</div>";
        Document doc = Jsoup.parse(imageWithCaptionHtml);
        Element imageWithCaptionElement = doc.body().child(0);

        //when
        ConvertedTreeNode imageNode = imageParser.parseToTreeNode(imageWithCaptionElement);

        // then
        NodeTestUtils.assertNodeTypeAndContent(imageNode, StyleType.IMAGE, "");

        ConvertedTreeNode imageByteNode = imageNode.getChilds().get(0);
        assertNotNull(imageByteNode);
        assertEquals(StyleType.IMAGEBYTE, imageByteNode.getType());

        ConvertedTreeNode captionNode = imageNode.getChilds().get(1);
        NodeTestUtils.assertNodeTypeAndContent(captionNode, StyleType.CAPTION, "");

        ConvertedTreeNode contentNode = captionNode.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(contentNode, StyleType.CONTENT, "고화질 이미지 캡션");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testImageParserWithoutCaption() throws Exception {
        // given
        String imageWithCaptionHtml =
        "<div class=\"se-section se-section-image se-l-default se-section-align-\">" + 
                "<div class=\"se-module se-module-image\" style=\"\">" + 
                    "<a class=\"se-module-image-link __se_image_link __se_link\" style=\"\" onclick=\"return false;\" data-linktype=\"img\" data-linkdata=\"{&quot;id&quot; : &quot;SE-553d8212-fe88-41d2-a469-7f54c162240d&quot;, &quot;src&quot; : &quot;https://blogpfthumb-phinf.pstatic.net/20120504_73/jhi990823_1336138420833_S02En4_jpg/wallpaper-33614.jpg&quot;, &quot;originalWidth&quot; : &quot;1280&quot;, &quot;originalHeight&quot; : &quot;960&quot;, &quot;linkUse&quot; : &quot;false&quot;, &quot;link&quot; : &quot;&quot;}\" area-hidden=\"true\">" + 
                        "<img src=\"https://blogpfthumb-phinf.pstatic.net/20120504_73/jhi990823_1336138420833_S02En4_jpg/wallpaper-33614.jpg\" alt=\"\" class=\"se-image-resource egjs-visible\">" + 
                    "</a>" + 
                "</div>" + 
        "</div>";
        Document doc = Jsoup.parse(imageWithCaptionHtml);
        Element imageWithCaptionElement = doc.body().child(0);

        // when
        ConvertedTreeNode result = imageParser.parseToTreeNode(imageWithCaptionElement);

        // then
        NodeTestUtils.assertNodeTypeAndContent(result, StyleType.IMAGE, "");

        ConvertedTreeNode imageByteNode = result.getChilds().get(0);
        assertNotNull(imageByteNode);
        assertEquals(StyleType.IMAGEBYTE, imageByteNode.getType());

        ConvertedTreeNode captionNode = result.getChilds().get(1);
        NodeTestUtils.assertNodeTypeAndContent(captionNode, StyleType.CAPTION, "");
    }
}
