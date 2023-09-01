package convert.scrapper.naver;
import org.junit.Test;

import convert.blogPost.BlogPost;
import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.BlogScrapper;
import convert.scrappers.naver.NaverScrapper;
import utils.NodeTestUtils;
import utils.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;

public class NaverScrapperTest {
    BlogScrapper naverScrapper = new NaverScrapper();
    BlogPost validPost, privatePost, nonExistPost;

    @Before
    public void setup() throws Exception {
        // given
        String testUrl = "https://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";

        // when
        validPost = naverScrapper.scrap(testUrl);
    }

    @Test(expected = Exception.class)
    public void testPrivateScrap() throws Exception {
        String privateUrl = "https://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222566159910";
        privatePost = naverScrapper.scrap(privateUrl);
        assertEquals(privatePost.getTitle(), "테스트");
    }

    @Test(expected = Exception.class)
    public void testNonExistentScrap() throws Exception {
        String nonExistentUrl = "https://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=22256615991";
        nonExistPost = naverScrapper.scrap(nonExistentUrl);
        assertEquals(nonExistPost.getTitle(), "테스트");
    }

    @Test
    public void testTitle() throws Exception {
        String title = validPost.getTitle();
        assertNotNull(title);
        assertEquals(title, "테스트");
    }

    @Test
    public void testContentRoot() throws Exception {
        ConvertedTreeNode root = validPost.getRoot();
        assertNotNull(root);
        assertEquals(root.getType(), StyleType.NONE);
        assertEquals(root.getContent(), "");
        assertEquals(root.getChilds().size(), 19);
    }

    @Test
    public void testNestedTextStyle() throws Exception {     
        /*
        idx 0
        --TEXT, 
        ----PARAGRAPH_DEFAULT, 
        ------CONTENT, 
        --------STRIKE, 
        ----------UNDERLINE, 
        ------------TILT, 
        --------------BOLD, 대제목1 제목1
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode textNode = root.getChilds().get(0);
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
    public void testSeparatedTextStyle() throws Exception {     
        /*
        --TEXT, 
        ----PARAGRAPH_DEFAULT, 
        ----PARAGRAPH_DEFAULT, 
        ------CONTENT, 대제목 제목1 설명대제목 제
        ------CONTENT, 
        --------BOLD, 목1 설명대제
        ------CONTENT, 목 제목1 설명
        ------CONTENT, 
        --------UNDERLINE, 대제목 제목
        ------CONTENT, 1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목
        ------CONTENT, 
        --------TILT, 1 설명대제
        ------CONTENT, 목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode textNode = root.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(textNode, StyleType.TEXT, "");

        ConvertedTreeNode paragraphNode = textNode.getChilds().get(1);
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
    public void testTextAlign() throws Exception {     
        /*
        root child idx 2
        --TEXT, 
        ----PARAGRAPH_DEFAULT, 
        ----PARAGRAPH_DEFAULT, 
        ----PARAGRAPH_DEFAULT, 
        ----PARAGRAPH_RIGHT, 
        ------CONTENT, 본문 내용 2 (우측 정렬)
        ----PARAGRAPH_CENTER, 
        ------CONTENT, 본문 내용 2 (가운데 정렬)
        ----PARAGRAPH_LEFT, 
        ------CONTENT, 이미지 중첩
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode textNode = root.getChilds().get(2);
        NodeTestUtils.assertNodeTypeAndContent(textNode, StyleType.TEXT, "");

        ConvertedTreeNode rightAlignNode = textNode.getChilds().get(3);
        NodeTestUtils.assertNodeTypeAndContent(rightAlignNode, StyleType.PARAGRAPH_RIGHT, "");
        ConvertedTreeNode rightAlignTextNode = rightAlignNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(rightAlignTextNode, StyleType.CONTENT, "본문 내용 2 (우측 정렬)");

        ConvertedTreeNode centerAlignNode = textNode.getChilds().get(4);
        NodeTestUtils.assertNodeTypeAndContent(centerAlignNode, StyleType.PARAGRAPH_CENTER, "");
        ConvertedTreeNode centerAlignTextNode = centerAlignNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(centerAlignTextNode, StyleType.CONTENT, "본문 내용 2 (가운데 정렬)");

        ConvertedTreeNode leftAlignNode = textNode.getChilds().get(5);
        NodeTestUtils.assertNodeTypeAndContent(leftAlignNode, StyleType.PARAGRAPH_LEFT, "");
        ConvertedTreeNode leftAlignTextNode = leftAlignNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(leftAlignTextNode, StyleType.CONTENT, "이미지 중첩");
    }

    @Test
    public void testCodeBlock() throws Exception {     
        /*
        root child idx 1
        --CODE, source code... 1 source code... 2 source code... 3 source code... 4
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode codeNode = root.getChilds().get(1);
        NodeTestUtils.assertNodeTypeAndContent(codeNode, StyleType.CODE, "source code... 1 source code... 2 source code... 3 source code... 4");
    }

    @Test
    public void testQuotation() throws Exception {    
        /*
        --QUOTATION, 
        ----QUOTE, 
        ------TEXT, 
        --------PARAGRAPH_DEFAULT, 
        ----------CONTENT, 인
        ----------CONTENT, 
        ------------BOLD, 용
        ----------CONTENT, 구
        ----CITE, 
        ------TEXT, 
        --------PARAGRAPH_DEFAULT, 
        ----------CONTENT, 인용구 출처
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode quotationNode = root.getChilds().get(3);
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

    @Test
    public void testHorizontalLine() throws Exception {     
        /*
        root child idx 5
        --HORIZONTALLINE, 
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode codeNode = root.getChilds().get(5);
        NodeTestUtils.assertNodeTypeAndContent(codeNode, StyleType.HORIZONTALLINE, "");
    }

    @Test
    public void testHyperlink() throws Exception {     
        /*
        root child idx 8
        --TEXT, 
        ----PARAGRAPH_DEFAULT, 
        ----PARAGRAPH_DEFAULT, 
        ----PARAGRAPH_DEFAULT, 
        ------CONTENT, 
        --------LINK, https://hyelie.tistory.com
        ------CONTENT, 본문 내용 1
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode textNode = root.getChilds().get(8);
        ConvertedTreeNode contentNode = textNode.getChilds().get(2).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(contentNode, StyleType.CONTENT, "");

        ConvertedTreeNode linkNode = contentNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(linkNode, StyleType.LINK, "https://hyelie.tistory.com");
    }

    @Test
    public void testTable() throws Exception {     
        /*
        root child idx 9
        --TABLE, 
        ----ROW, 
        ------COLUMN, 
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------CONTENT, 0, 0
        ------COLUMN, 
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------CONTENT, 
        --------------UNDERLINE, 0, 1
        ------COLUMN, 
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------CONTENT, 0, 2
        ----ROW,
        ----ROW, 
        ------COLUMN, 
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------CONTENT, 2, 0
        ------COLUMN, 
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------CONTENT, 2, 1
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------CONTENT, 123123
        ------COLUMN, 
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------CONTENT, 2, 2
        */
        ConvertedTreeNode contentNode;
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode tableNode = root.getChilds().get(9);
        NodeTestUtils.assertNodeTypeAndContent(tableNode, StyleType.TABLE, "");

        // row 1
        ConvertedTreeNode rowNode1 = tableNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(rowNode1, StyleType.ROW, "");

        ConvertedTreeNode colNode1 = rowNode1.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(colNode1, StyleType.COLUMN, "");
        contentNode = colNode1.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(contentNode, StyleType.CONTENT, "0, 0");

        ConvertedTreeNode colNode2 = rowNode1.getChilds().get(1);
        NodeTestUtils.assertNodeTypeAndContent(colNode2, StyleType.COLUMN, "");
        contentNode = colNode2.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(contentNode, StyleType.CONTENT, "");
        ConvertedTreeNode UnderlineNode = contentNode.getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(UnderlineNode, StyleType.UNDERLINE, "0, 1");

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

    @Test
    public void testImageWithCaption() throws Exception {     
        /*
        root child idx 13
        --IMAGE, 
        ----IMAGEBASE64
        ----CAPTION, 
        ------TEXT, 
        --------PARAGRAPH_DEFAULT, 
        ----------CONTENT, 고화질 이미지 캡션
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode imageNode = root.getChilds().get(13);

        ConvertedTreeNode imageBase64Node = imageNode.getChilds().get(0);
        String imageUrl = "https://blogpfthumb-phinf.pstatic.net/20120504_73/jhi990823_1336138420833_S02En4_jpg/wallpaper-33614.jpg";
        String imageBase64 = Utils.encodeByteToBase64(Utils.downloadByteImage(imageUrl));
        NodeTestUtils.assertNodeTypeAndContent(imageBase64Node, StyleType.IMAGEBASE64, imageBase64);

        ConvertedTreeNode captionNode = imageNode.getChilds().get(1);
        NodeTestUtils.assertNodeTypeAndContent(captionNode, StyleType.CAPTION, "");

        ConvertedTreeNode captionContentNode = captionNode.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        NodeTestUtils.assertNodeTypeAndContent(captionContentNode, StyleType.CONTENT, "고화질 이미지 캡션");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testImageWithoutCaption() throws Exception {     
        /*
        root child idx 14
        --IMAGE, 
        ----IMAGEBASE64
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode imageNode = root.getChilds().get(14);

        ConvertedTreeNode imageBase64Node = imageNode.getChilds().get(0);
        String imageUrl = "https://blogpfthumb-phinf.pstatic.net/20120504_73/jhi990823_1336138420833_S02En4_jpg/wallpaper-33614.jpg";
        String imageBase64 = Utils.encodeByteToBase64(Utils.downloadByteImage(imageUrl));
        NodeTestUtils.assertNodeTypeAndContent(imageBase64Node, StyleType.IMAGEBASE64, imageBase64);

        ConvertedTreeNode captionNode = imageNode.getChilds().get(1);
    }

    @Deprecated
    private static void print(ConvertedTreeNode node, int depth){
        String result = "";
        for(int i = 0; i<depth; i++) result += "--";
        if(node.getType() == StyleType.IMAGEBASE64) result += node.getType();
        else result += node.getType() + ", " + node.getContent();
        Utils.printMessage(result);

        if(node.isLeaf()) return;
        for(ConvertedTreeNode child : node.getChilds()){
            print(child, depth+1);
        }
    }
}
