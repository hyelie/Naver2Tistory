package convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Converter {
    private Elements post;
    private String title;
    private Elements content;
    private String result;
    
    // Regular expression filtering [se-section se-section-{TYPE}] format
    private Pattern sectionForm = Pattern.compile("se-section se-section-([A-za-z]*)");
    
    /**
     * Content Type in Naver Blog Post
     * 
     * @method findByLabel(label) : return ContentType which contain parameter 'label'. If label doesn't exist, then return NOTHING. 
     */
    private enum ContentType{
        TABLE("table"),
        QUOTATION("quotation"),
            QUOTE("quote"),
            CITE("cite"),
        TEXT("text"),
            PARAGRAPH("paragraph"),
        CODE("code"),
        IMAGE("image"),
            CAPTION("caption"),
        HORIZONTALLINE("horizontalLine"),
        LINK("oglink"),
        NOTHING("nothing");

        private final String label;
        ContentType(String label){
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
        public static ContentType findByLabel(String label){
            for(ContentType contentType : values()){
                if(label == contentType.label) return contentType;
            }
            return NOTHING;
        }
    }

    /**
     * Getter of title
     * @return title of current post
     */
    public String getTitle(){
        return this.title;
    }
    
    /**
     * Getter of result
     * @return stylized result
     */
    public String getResult(){
        return this.result;
    }

    /**
     * Extract post from HTML document and store in the attribute
     * 
     * @param doc - given document go get post
     * @throws Exception when data does not received because of
     * deleted/private post, or written by old version editor.
     */ 
    public void setPost(Document doc) throws Exception{
        this.post = doc.select(".se-viewer");
        if(post.size() == 0){ // 삭제된/존재하지 않는/비공개 게시글 또는 구버전 네이버 에디터로 작성한 게시글
            throw new Exception(ResponseMessage.NO_CONTENT.getLabel());
        }
    }

    /**
     * Extract title from post and store in the attribute 'title'
     * 
     * @throws Exception when data does not exist because of
     * deleted/private post, or written by old version editor.
     */
    public void extractTitle() throws Exception{
        Elements head = post.select(".pcol1"); // Naver blog 제목이 있는 class
		if(head.size() == 0){
			throw new Exception(ResponseMessage.NO_CONTENT.getLabel());
		}
        this.title = head.text();
    }

    /**
     * Extract content from post and store in the attribute 'content'
     * 
     * @throws Exception when data does not exist because of
     * deleted/private post, or written by old version editor.
     */
    public void extractContent() throws Exception{
        Elements content = post.select(".se-main-container"); // Naver blog 제목이 있는 class
		if(content.size() == 0){
			throw new Exception(ResponseMessage.NO_CONTENT.getLabel());
		}
        this.content = content;
    }

    /**
     * @return content type of given parameter 'element'. If nothing, then return ContentType.NOTHING
     */
    private ContentType getContentType(Element element){
		Matcher sectionMatcher = sectionForm.matcher(element.className());
        if(sectionMatcher.find()){
            String type = sectionMatcher.group(1);
            return ContentType.findByLabel(type);
        }
        return ContentType.NOTHING;
    }

    /**
     * convert ContentType.TABLE 'element' to Tistory format
     * @param element should be converted to table.
     */
    void convertTable(Element element){
        // table이면 tistory 형식에 맞게
    }

    /**
     * convert ContentType.QUOTATION 'element' to Tistory format
     * @param element should be converted to quotation.
     */
    void convertQUOTATION(Element element){
        // quotation이면 se-quote module과 se-cite module을 print
    }

    /**
     * convert ContentType.TEXT 'element' to Tistory format
     * @param element should be converted to text.
     */
    void convertTEXT(Element element){
        // text면 module의 child를 전부 print
    }

    /**
     * convert ContentType.CODE 'element' to Tistory format
     * @param element should be converted to code.
     */
    void convertCODE(Element element){
        // code면 module의 text를 print
    }

    /**
     * convert ContentType.IMAGE 'element' to Tistory format
     * @param element should be converted to image.
     */
    void convertIMAGE(Element element){
        // image면 image module, caption module의 text를 찾아 print
    }

    /**
     * convert ContentType.HORIZONTALLINE 'element' to Tistory format
     * @param element should be converted to horizontalLine.
     */
    void convertHORIZONTALLINE(Element element){
        // horizontalLine이면 tistory 형식에 맞게 print
    }

    /**
     * convert ContentType.LINK 'element' to Tistory format
     * @param element should be converted to link.
     */
    void convertLINK(Element element){
        // oglink면 link를 print
    }

    /**
     * Traverse childs of given parameter 'elements'.
     * While traversal, reform it's HTML element to Tistory style.
     * 
     * @param elements which want to traverse.
     * @see Converter#convertTable(Element)
     * @see Converter#convertQUOTATION(Element)
     * @see Converter#convertTEXT(Element)
     * @see Converter#convertCODE(Element)
     * @see Converter#convertIMAGE(Element)
     * @see Converter#convertHORIZONTALLINE(Element)
     * @see Converter#convertLINK(Element)
     */
    private void dfsDOM(Elements elements){
        for(Element element : elements){
            ContentType elementContentType = getContentType(element);

            // If section type exists, then stylize
            if(elementContentType == ContentType.TABLE){
                this.convertTable(element);
            }
            else if(elementContentType == ContentType.QUOTATION){
                this.convertQUOTATION(element);
            }
            else if(elementContentType == ContentType.TEXT){
                this.convertTEXT(element);
            }
            
            else if(elementContentType == ContentType.CODE){
                this.convertCODE(element);
            }
            
            else if(elementContentType == ContentType.IMAGE){
                this.convertIMAGE(element);
            }
            
            else if(elementContentType == ContentType.HORIZONTALLINE){
                this.convertHORIZONTALLINE(element);
            }
            
            else if(elementContentType == ContentType.LINK){
                this.convertLINK(element);
            }

            // else then traverse deeper.
            else{ // elementContentType == ContentType.NOTHING
                dfsDOM(element.children());
            }
		}
    }

    
    /**
     * Stylize naver blog content to tistory format
     * and store result in the attribute 'result'.
     * 
     * @see Converter#dfsDOM(Elements)
     */
    public void stylize(){
        this.result = "";
        // stylize with HTML DOM DFS traversal
        // 각 container의 type에 맞게 고침
        dfsDOM(this.content);
    }
}
