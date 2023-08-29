package scrapper;
import org.junit.Test;

import convert.Scrapper;
import convert.SupportType;
import convert.blogPost.BlogPost;
import convert.blogPost.ConvertedTreeNode;
import convert.scrappers.naver.NaverScrapper;
import utils.Utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;

public class NaverScrapperTest {
    Scrapper naverScrapper = new NaverScrapper();
    BlogPost validPost, privatePost, nonExistPost;

    @Before
    public void setup() throws Exception {
        String testUrl = "https://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";
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
        assertEquals(root.getType(), SupportType.NONE);
        assertEquals(root.getContent(), "");
        assertEquals(root.getChilds().size(), 19);
    }

    /*
    The comments below means the execution result of print(root, 0).
    It shows the HTML hierarchy in the test URL.
    -- means one depth.
    */

    @Test
    public void testNestedTextStyle() throws Exception {     
        /*
        idx 0
        --TEXT, 
        ----PARAGRAPH_DEFAULT, 
        ------STRIKE, 
        --------UNDERBAR, 
        ----------TILT, 
        ------------BOLD, 대제목1 제목1
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode textNode = root.getChilds().get(0);
        assertNodeTypeAndContent(textNode, SupportType.TEXT, "");

        ConvertedTreeNode paragraphNode = textNode.getChilds().get(0);
        assertNodeTypeAndContent(paragraphNode, SupportType.PARAGRAPH_DEFAULT, "");

        ConvertedTreeNode strikeNode = paragraphNode.getChilds().get(0);
        assertNodeTypeAndContent(strikeNode, SupportType.STRIKE, "");

        ConvertedTreeNode underbarNode = strikeNode.getChilds().get(0);
        assertNodeTypeAndContent(underbarNode, SupportType.UNDERBAR, "");

        ConvertedTreeNode tiltNode = underbarNode.getChilds().get(0);
        assertNodeTypeAndContent(tiltNode, SupportType.TILT, "");

        ConvertedTreeNode boldNode = tiltNode.getChilds().get(0);
        assertNodeTypeAndContent(boldNode, SupportType.BOLD, "대제목1 제목1");
    }

    @Test
    public void testSeparatedTextStyle() throws Exception {     
        /*
        root child idx 0
        --TEXT, 
        ----PARAGRAPH_DEFAULT, 
        ----PARAGRAPH_DEFAULT, 
        ------NONE, 대제목 제목1 설명대제목 제목1 설명대제목 제
        ------BOLD, 목1 설명대제
        ------NONE, 목 제목1 설명
        ------UNDERBAR, 대제목 제목
        ------NONE, 1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목
        ------TILT, 1 설명대제
        ------NONE, 목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode textNode = root.getChilds().get(0);
        assertNodeTypeAndContent(textNode, SupportType.TEXT, "");

        ConvertedTreeNode paragraphNode = textNode.getChilds().get(1);
        assertNodeTypeAndContent(paragraphNode, SupportType.PARAGRAPH_DEFAULT, "");

        ConvertedTreeNode childNode0 = paragraphNode.getChilds().get(0);
        assertNodeTypeAndContent(childNode0, SupportType.NONE, "대제목 제목1 설명대제목 제");

        ConvertedTreeNode childNode1 = paragraphNode.getChilds().get(1);
        assertNodeTypeAndContent(childNode1, SupportType.BOLD, "목1 설명대제");

        ConvertedTreeNode childNode2 = paragraphNode.getChilds().get(2);
        assertNodeTypeAndContent(childNode2, SupportType.NONE, "목 제목1 설명");

        ConvertedTreeNode childNode3 = paragraphNode.getChilds().get(3);
        assertNodeTypeAndContent(childNode3, SupportType.UNDERBAR, "대제목 제목");

        ConvertedTreeNode childNode4 = paragraphNode.getChilds().get(4);
        assertNodeTypeAndContent(childNode4, SupportType.NONE, "1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목");

        ConvertedTreeNode childNode5 = paragraphNode.getChilds().get(5);
        assertNodeTypeAndContent(childNode5, SupportType.TILT, "1 설명대제");

        ConvertedTreeNode childNode6 = paragraphNode.getChilds().get(6);
        assertNodeTypeAndContent(childNode6, SupportType.NONE, "목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명대제목 제목1 설명");
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
        ------NONE, 본문 내용 2 (우측 정렬)
        ----PARAGRAPH_CENTER, 
        ------NONE, 본문 내용 2 (가운데 정렬)
        ----PARAGRAPH_LEFT, 
        ------NONE, 이미지 중첩
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode textNode = root.getChilds().get(2);
        assertNodeTypeAndContent(textNode, SupportType.TEXT, "");

        ConvertedTreeNode rightAlignNode = textNode.getChilds().get(3);
        assertNodeTypeAndContent(rightAlignNode, SupportType.PARAGRAPH_RIGHT, "");
        ConvertedTreeNode rightAlignTextNode = rightAlignNode.getChilds().get(0);
        assertNodeTypeAndContent(rightAlignTextNode, SupportType.NONE, "본문 내용 2 (우측 정렬)");

        ConvertedTreeNode centerAlignNode = textNode.getChilds().get(4);
        assertNodeTypeAndContent(centerAlignNode, SupportType.PARAGRAPH_CENTER, "");
        ConvertedTreeNode centerAlignTextNode = centerAlignNode.getChilds().get(0);
        assertNodeTypeAndContent(centerAlignTextNode, SupportType.NONE, "본문 내용 2 (가운데 정렬)");

        ConvertedTreeNode leftAlignNode = textNode.getChilds().get(5);
        assertNodeTypeAndContent(leftAlignNode, SupportType.PARAGRAPH_LEFT, "");
        ConvertedTreeNode leftAlignTextNode = leftAlignNode.getChilds().get(0);
        assertNodeTypeAndContent(leftAlignTextNode, SupportType.NONE, "이미지 중첩");
    }

    @Test
    public void testCodeBlock() throws Exception {     
        /*
        root child idx 1
        --CODE, source code... 1 source code... 2 source code... 3 source code... 4
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode codeNode = root.getChilds().get(1);
        assertNodeTypeAndContent(codeNode, SupportType.CODE, "source code... 1 source code... 2 source code... 3 source code... 4");
    }

    @Test
    public void testQuotation() throws Exception {    
        /*
        root child idx 3
        --QUOTATION, 
        ----QUOTE, 
        ------TEXT, 
        --------PARAGRAPH_DEFAULT, 
        ----------NONE, 인
        ----------BOLD, 용
        ----------NONE, 구
        ----CITE, 
        ------TEXT, 
        --------PARAGRAPH_DEFAULT, 
        ----------NONE, 인용구 출처
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode quotationNode = root.getChilds().get(3);
        assertNodeTypeAndContent(quotationNode, SupportType.QUOTATION, "");

        ConvertedTreeNode quoteNode = quotationNode.getChilds().get(0);
        assertNodeTypeAndContent(quoteNode, SupportType.QUOTE, "");

        ConvertedTreeNode quoteParagraphNode = quoteNode.getChilds().get(0).getChilds().get(0);
        assertNodeTypeAndContent(quoteParagraphNode.getChilds().get(0), SupportType.NONE, "인");
        assertNodeTypeAndContent(quoteParagraphNode.getChilds().get(1), SupportType.BOLD, "용");
        assertNodeTypeAndContent(quoteParagraphNode.getChilds().get(2), SupportType.NONE, "구");

        ConvertedTreeNode citeNode = quotationNode.getChilds().get(1);
        assertNodeTypeAndContent(citeNode, SupportType.CITE, "");

        ConvertedTreeNode citeParagraphNode = citeNode.getChilds().get(0).getChilds().get(0);
        assertNodeTypeAndContent(citeParagraphNode.getChilds().get(0), SupportType.NONE, "인용구 출처");
    }

    @Test
    public void testHorizontalLine() throws Exception {     
        /*
        root child idx 5
        --HORIZONTALLINE, 
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode codeNode = root.getChilds().get(5);
        assertNodeTypeAndContent(codeNode, SupportType.HORIZONTALLINE, "");
    }

    @Test
    public void testHyperlink() throws Exception {     
        /*
        root child idx 8
        --TEXT, 
        ----PARAGRAPH_DEFAULT, 
        ----PARAGRAPH_DEFAULT, 
        ----PARAGRAPH_DEFAULT, 
        ------LINK, https://hyelie.tistory.com
        ------NONE, 본문 내용 1
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode textNode = root.getChilds().get(8);
        ConvertedTreeNode linkNode = textNode.getChilds().get(2).getChilds().get(0);
        assertNodeTypeAndContent(linkNode, SupportType.LINK, "https://hyelie.tistory.com");
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
        ------------NONE, 0, 0
        ------COLUMN, 
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------UNDERBAR, 0, 1
        ------COLUMN, 
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------NONE, 0, 2
        ----ROW, 
        ----ROW, 
        ------COLUMN, 
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------NONE, 2, 0
        ------COLUMN, 
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------NONE, 2, 1
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------NONE, 123123
        ------COLUMN, 
        --------TEXT, 
        ----------PARAGRAPH_DEFAULT, 
        ------------NONE, 2, 2
        */
        ConvertedTreeNode contentNode;
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode tableNode = root.getChilds().get(9);
        assertNodeTypeAndContent(tableNode, SupportType.TABLE, "");

        // row 1
        ConvertedTreeNode rowNode1 = tableNode.getChilds().get(0);
        assertNodeTypeAndContent(rowNode1, SupportType.ROW, "");

        ConvertedTreeNode colNode1 = rowNode1.getChilds().get(0);
        assertNodeTypeAndContent(colNode1, SupportType.COLUMN, "");
        contentNode = colNode1.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        assertNodeTypeAndContent(contentNode, SupportType.NONE, "0, 0");

        ConvertedTreeNode colNode2 = rowNode1.getChilds().get(1);
        assertNodeTypeAndContent(colNode2, SupportType.COLUMN, "");
        contentNode = colNode2.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        assertNodeTypeAndContent(contentNode, SupportType.UNDERBAR, "0, 1");

        ConvertedTreeNode colNode3 = rowNode1.getChilds().get(2);
        assertNodeTypeAndContent(colNode3, SupportType.COLUMN, "");
        contentNode = colNode3.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        assertNodeTypeAndContent(contentNode, SupportType.NONE, "0, 2");


        // row 3
        ConvertedTreeNode rowNode3 = tableNode.getChilds().get(2);
        assertNodeTypeAndContent(rowNode3, SupportType.ROW, "");

        colNode1 = rowNode3.getChilds().get(0);
        assertNodeTypeAndContent(colNode1, SupportType.COLUMN, "");
        contentNode = colNode1.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        assertNodeTypeAndContent(contentNode, SupportType.NONE, "2, 0");

        colNode2 = rowNode3.getChilds().get(1);
        assertNodeTypeAndContent(colNode2, SupportType.COLUMN, "");
        contentNode = colNode2.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        assertNodeTypeAndContent(contentNode, SupportType.NONE, "2, 1");
        contentNode = colNode2.getChilds().get(1).getChilds().get(0).getChilds().get(0);
        assertNodeTypeAndContent(contentNode, SupportType.NONE, "123123");

        colNode3 = rowNode3.getChilds().get(2);
        assertNodeTypeAndContent(colNode3, SupportType.COLUMN, "");
        contentNode = colNode3.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        assertNodeTypeAndContent(contentNode, SupportType.NONE, "2, 2");
    }

    @Test
    public void testLowImageWithCaption() throws Exception {     
        /*
        root child idx 11
        --IMAGE, 
        ----IMAGEBYTE
        ----CAPTION, 
        ------TEXT, 
        --------PARAGRAPH_DEFAULT, 
        ----------BOLD, 저화질 이미지 캡션
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode imageNode = root.getChilds().get(11);

        ConvertedTreeNode imagebyteNode = imageNode.getChilds().get(0);
        String imageUrl = "https://blog.kakaocdn.net/dn/coeHPP/btrLFhIH047/MwPwCO3Mvpc1zDcW1E02B0/img.png";
        String imageByte = new String(Utils.downloadByteImage(imageUrl));
        assertNodeTypeAndContent(imagebyteNode, SupportType.IMAGEBYTE, imageByte);

        ConvertedTreeNode captionNode = imageNode.getChilds().get(1);
        assertNodeTypeAndContent(captionNode, SupportType.CAPTION, "");

        ConvertedTreeNode captionContentNode = captionNode.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        assertNodeTypeAndContent(captionContentNode, SupportType.BOLD, "저화질 이미지 캡션");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testLowImageWithoutCaption() throws Exception {     
        /*
        root child idx 15
        --IMAGE, 
        ----IMAGEBYTE
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode imageNode = root.getChilds().get(15);

        ConvertedTreeNode imagebyteNode = imageNode.getChilds().get(0);
        String imageUrl = "https://blog.kakaocdn.net/dn/coeHPP/btrLFhIH047/MwPwCO3Mvpc1zDcW1E02B0/img.png";
        String imageByte = new String(Utils.downloadByteImage(imageUrl));
        assertNodeTypeAndContent(imagebyteNode, SupportType.IMAGEBYTE, imageByte);

        ConvertedTreeNode captionNode = imageNode.getChilds().get(1);
    }

    @Test
    public void testHighImageWithCaption() throws Exception {     
        /*
        root child idx 13
        --IMAGE, 
        ----IMAGEBYTE
        ----CAPTION, 
        ------TEXT, 
        --------PARAGRAPH_DEFAULT, 
        ----------NONE, 고화질 이미지 캡션
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode imageNode = root.getChilds().get(13);

        ConvertedTreeNode imagebyteNode = imageNode.getChilds().get(0);
        String imageUrl = "https://blogpfthumb-phinf.pstatic.net/20120504_73/jhi990823_1336138420833_S02En4_jpg/wallpaper-33614.jpg";
        String imageByte = new String(Utils.downloadByteImage(imageUrl));
        assertNodeTypeAndContent(imagebyteNode, SupportType.IMAGEBYTE, imageByte);

        ConvertedTreeNode captionNode = imageNode.getChilds().get(1);
        assertNodeTypeAndContent(captionNode, SupportType.CAPTION, "");

        ConvertedTreeNode captionContentNode = captionNode.getChilds().get(0).getChilds().get(0).getChilds().get(0);
        assertNodeTypeAndContent(captionContentNode, SupportType.NONE, "고화질 이미지 캡션");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testHighImageWithoutCaption() throws Exception {     
        /*
        root child idx 14
        --IMAGE, 
        ----IMAGEBYTE
        */
        ConvertedTreeNode root = validPost.getRoot();
        ConvertedTreeNode imageNode = root.getChilds().get(14);

        ConvertedTreeNode imagebyteNode = imageNode.getChilds().get(0);
        String imageUrl = "https://blogpfthumb-phinf.pstatic.net/20120504_73/jhi990823_1336138420833_S02En4_jpg/wallpaper-33614.jpg";
        String imageByte = new String(Utils.downloadByteImage(imageUrl));
        assertNodeTypeAndContent(imagebyteNode, SupportType.IMAGEBYTE, imageByte);

        ConvertedTreeNode captionNode = imageNode.getChilds().get(1);
    }

    private void assertNodeTypeAndContent(ConvertedTreeNode node, SupportType expectedType, String expectedContent){
        assertNotNull(node);
        assertEquals(expectedType, node.getType());
        assertEquals(expectedContent, node.getContent());
    }

    @Deprecated
    private static void print(ConvertedTreeNode node, int depth){
        String result = "";
        for(int i = 0; i<depth; i++) result += "--";
        if(node.getType() == SupportType.IMAGEBYTE) result += node.getType();
        else result += node.getType() + ", " + node.getContent();
        System.out.println(result);

        if(node.isLeaf()) return;
        for(ConvertedTreeNode child : node.getChilds()){
            print(child, depth+1);
        }
    }
}
