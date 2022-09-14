package convert;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import utils.Utils;

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
                ALIGN_DEFAULT(""),
                ALIGN_RIGHT("right"),
                ALIGN_LEFT("justify"),
                ALIGN_CENTER("center"),
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
                if(label.equals(contentType.label)) return contentType;
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
     * Convert ContentType.TABLE 'element' to Tistory format
     * @param element should be converted to table.
     */
    private void convertTable(Element element){
        // table이면 tistory 형식에 맞게
        Element tableBody = element.child(0).child(0).child(0);
        String table = "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\" data-ke-align=\"alignLeft\">";
        table += "<tbody>";
        for(Element rows : tableBody.children()){
            table += "<tr>";
            for(Element columns : rows.children()){
                table += "<td>" + columns.text() + "</td>";
            }
            table += "</tr>";
        }
        table += "</tbody>";
        table += "</table>";

        this.result += table;
    }

    /**
     * Convert ContentType.QUOTATION 'element' to Tistory format
     * @param element should be converted to quotation.
     */
    private void convertQUOTATION(Element element){
        // quotation이면 se-quote module과 se-cite module을 print
        Element quotationContatiner = element.child(0);
        Element quote = quotationContatiner.child(0);
        Element cite = quotationContatiner.child(1);

        String quotation = "<blockquote data-ke-style=\"style1\">";
        quotation += quote.text();
        quotation += "<br/>";
        quotation += "- " + cite.text() + " -";
        quotation += "</blockquote>";

        this.result += quotation;
    }

    
    // Regular expression filtering [se-text-paragraph se-text-paragraph-{ALIGN-TYPE}] format
    private Pattern paragraphForm = Pattern.compile("se-text-paragraph se-text-paragraph-align-([A-za-z]*)");
    /**
     * Convert ContentType.TEXT 'element' to Tistory format
     * @param element should be converted to text.
     */
    private void convertTEXT(Element element){
        // text면 module의 child를 전부 print
        Element textModule = element.child(0);
        for(Element child : textModule.children()){
            // 정렬
            Matcher paragraphMatcher = paragraphForm.matcher(child.className());
            String paragraph = "<p";
            if(paragraphMatcher.find()){
                ContentType alignType = ContentType.findByLabel(paragraphMatcher.group(1));
                if(alignType.equals(ContentType.ALIGN_LEFT)){
                    paragraph += " style=\"text-align: left;\"";
                }
                else if(alignType.equals( ContentType.ALIGN_CENTER)){
                    paragraph += " style=\"text-align: center;\"";
                }
                else if(alignType.equals( ContentType.ALIGN_RIGHT)){
                    paragraph += " style=\"text-align: right;\"";
                }
                // alignType == ContentType.ALIGN_DEFAULT;
            }
            paragraph += ">";

            // link
            Elements links = child.getElementsByTag("a");
            Boolean isLink = (links.size() != 0);
            if(isLink){
                paragraph += "<a href=\"" + links.attr("href") + "\" target=\"_blank\" rel=\"noopener\">";
            }

                // text
                if(child.text().equals("&ZeroWidthSpace;")) paragraph += "&nbsp;";
                else paragraph += child.text();

            if(isLink) paragraph += "</a>";
            paragraph += "</p>";

            this.result += paragraph;
        }
    }

    /**
     * Convert ContentType.CODE 'element' to Tistory format
     * @param element should be converted to code.
     */
    private void convertCODE(Element element){
        // code면 module의 text를 print
        String code = "<pre class=\"bash\" data-ke-language=\"bash\" data-ke-type=\"codeblock\"><code>";
        code += element.text();
        code += "</code></pre>";

        this.result += code;
    }

    /**
     * Convert ContentType.IMAGE 'element' to Tistory format
     * @param element should be converted to image.
     */
    private void convertIMAGE(Element element){
        // image면 image module, caption module의 text를 찾아 print
        // img
        Elements imageModule = element.child(0).select("img");
        String imageSrc = imageModule.attr("src");
        if(imageModule.hasAttr("data-lazy-src")) imageSrc = imageModule.attr("data-lazy-src");

        try {
            Utils.downloadImage(imageSrc);
        } catch (Exception e) {
            System.out.println("[이미지 전환 중 오류] : " + e.getMessage());
        }

        // caption
        String caption = "";
        if(element.childrenSize() >= 2){
            Element captionModule = element.child(1);
            caption = captionModule.text();
        }
        String image = "<p><img src=\"\" caption=\"" + caption + "\" /></p>";

        this.result += image;
    }

    /**
     * Convert ContentType.HORIZONTALLINE 'element' to Tistory format
     * @param element should be converted to horizontalLine.
     */
    private void convertHORIZONTALLINE(Element element){
        // horizontalLine이면 tistory 형식에 맞게 print
        String horizontalLine = "<hr contenteditable=\"false\" data-ke-type=\"horizontalRule\" data-ke-style=\"style5\" />";
        this.result += horizontalLine;
    }

    /**
     * Convert ContentType.LINK 'element' to Tistory format
     * @param element should be converted to link.
     */
    private void convertLINK(Element element){
        // oglink면 link를 print
    }

    /**
     * Traverse childs of given parameter 'elements'.
     * While traversal, reform it's HTML element to Tistory style.
     * 
     * @param elements is which want to traverse.
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

    /**
     * Replace all img tag in attribute 'result' to replacer with caption.
     * @param replacers - list of replacers of images.
     */
    public void attachIMAGE(List<String> replacers){
        Document doc = Jsoup.parse(this.result);
        Elements images = doc.select("img");
        List<String> captions = images.eachAttr("caption");

        int idx = 0;
        String delimeter = "|";
        for(Element image : images){
            String replacer = replacers.get(idx);
            String caption = captions.get(idx);

            Integer captionIndex = Utils.findNthString(replacer, delimeter, 4); // caption index
            replacer = Utils.insert(replacer, captionIndex+1, caption); // caption 위치에 insert

            Element parent = image.parent();
            image.remove();
            parent.appendText(replacer);

            idx++;
        }    
        this.result = doc.toString();
    }
}
