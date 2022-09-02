package convert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Converter {
    private Elements post;
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
     * Get naver blog post and store in the attribute
     * 
     * @param doc - given document go get post
     * @throws Exception when data does not received because of
     * deleted/private post, or written by old version editor.
     */ 
    void setPost(Document doc) throws Exception{
        this.post = doc.select(".se-viewer");
        if(post.size() == 0){ // 삭제된/존재하지 않는/비공개 게시글 또는 구버전 네이버 에디터로 작성한 게시글
            throw new Exception(ResponseMessage.NO_CONTENT.getLabel());
        }
    }

    /**
     * @return title from naver blog post
     * @throws Exception when data does not exist because of
     * deleted/private post, or written by old version editor.
     */
    public String getPostTitle() throws Exception{
        Elements head = post.select(".pcol1"); // Naver blog 제목이 있는 class
		if(head.size() == 0){
			throw new Exception(ResponseMessage.NO_CONTENT.getLabel());
		}
        return head.text();
    }

    /**
     * @return content from naver blog post
     * @throws Exception when data does not exist because of
     * deleted/private post, or written by old version editor.
     */
    public Elements getPostContent() throws Exception{
        Elements content = post.select(".se-main-container"); // Naver blog 제목이 있는 class
		if(content.size() == 0){
			throw new Exception(ResponseMessage.NO_CONTENT.getLabel());
		}
        return content;
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
     * Traverse childs of given parameter 'elements'.
     * While traversal, reform it's HTML element to Tistory style.
     * 
     * @param elements which want to traverse.
     */
    private void dfsDOM(Elements elements){
        for(Element element : elements){
            ContentType elementContentType = getContentType(element);
            // If type exists, then stylize
            // table이면 tistory 형식에 맞게
            if(elementContentType == ContentType.TABLE){
                
            }
            // quotation이면 se-quote module과 se-cite module을 print
            else if(elementContentType == ContentType.QUOTATION){
                
            }
            else if(elementContentType == ContentType.TEXT){

            }
            // text면 module의 child를 전부 print
            // code면 module의 text를 print
            else if(elementContentType == ContentType.CODE){

            }
            // image면 image module, caption module의 text를 찾아 print
            else if(elementContentType == ContentType.IMAGE){

            }
            // horizontalLine이면 tistory 형식에 맞게 print
            else if(elementContentType == ContentType.HORIZONTALLINE){

            }
            // oglink면 link를 print
            else if(elementContentType == ContentType.LINK){

            }
            // else then traverse deeper.
            else{ // elementContentType == ContentType.NOTHING
                dfsDOM(element.children());
            }
		}
    }

    // convert crawled naver blog HTML to tistory HTML
    public void stylize(Elements content){
        // stylize with HTML DOM DFS traversal
        // 각 container의 type에 맞게 고침

    }
}
