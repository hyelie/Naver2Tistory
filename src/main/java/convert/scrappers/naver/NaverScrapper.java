package convert.scrappers.naver;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import convert.blogPost.BlogPost;
import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.Scrapper;
import convert.scrappers.naver.sectionParsers.CodeParser;
import convert.scrappers.naver.sectionParsers.DefaultParser;
import convert.scrappers.naver.sectionParsers.HorizontalLineParser;
import convert.scrappers.naver.sectionParsers.ImageParser;
import convert.scrappers.naver.sectionParsers.OglinkParser;
import convert.scrappers.naver.sectionParsers.QuotationParser;
import convert.scrappers.naver.sectionParsers.TableParser;
import convert.scrappers.naver.sectionParsers.TextParser;

/*
Naver blog HTML consists of the following hierarchy.

se-viewer                                                               # entire posting
- se-component se-documentTitle                                         # title
    - se-component-content
        - se-section se-section-documentTitle
            - blog2_series                                              # category (not needed)
                - ...
            - pcol1                                                     # title text
                - se-module se-module-text
                    - se-text-paragraph
            - blog2_container                                           # writer name (not needed)
                - ...
            - blog2_post_function                                       # copy url button, add neighbor button (not needed)
                - ...
- se-main-container                                                     # main text
    - se-component se-[TYPE]
        - se-component-content
            - se-section se-section-[TYPE]
                - se-module se-module-text                              # text
                  - se-text-paragraph                                       # paragraph
                - se-module se-module-code                              # source code
                  - se-code-source                                  
                      - __se_code_view
                - se-module se-module-image                             # image
                  se-module se-module-text se-caption                   # image caption
                - se-module se-module-horizontalLine                    # horizontal line
                - se-module se-module-oglink                            # auto generated shortcut 
                - se-quotation-container                                # quotation container
                    - se-module se-module-text se-quote                     # quote
                    - se-module se-module-text se-cite                      # cite
                - se-table-container                                    # table container
                    - se-table-content                                      # table body

The hierarchy consists of component - component-content - section - module/container.
The component-content layer is common. View type is determined by the name of section layer below component-content layer.

All sections are the form of "se-section se-section-[TYPE]"
Types are : text, quotation, text, code ,image, horizontalLine, oglink
The text part of every layer consists of se-module-text. For example, the text of one cell of a table section is se-module-text.

*** Therefore, all child classes SectionParser must call parseTextModule().
*/
public class NaverScrapper extends Scrapper {
    private static Map<String, SectionParser> parserMap = new HashMap<>();
    private static final String DEFAULT = "";

    static{
        initializeParserMap();
    }

    private static void initializeParserMap(){
        parserMap.put("table", new TableParser());
        parserMap.put("quotation", new QuotationParser());
        parserMap.put("text", new TextParser());
        parserMap.put("code", new CodeParser());
        parserMap.put("image", new ImageParser());
        parserMap.put("horizontalLine", new HorizontalLineParser());
        parserMap.put("oglink", new OglinkParser());
        parserMap.put(DEFAULT, new DefaultParser());
        // Append other [naver blog section name to StyleType mapping] here
    }

    @Override
    protected void initializeErrorMessages() {
        errorMessages.put(204, "[네이버 블로그 오류] : 삭제되거나, 존재하지 않거나, 비공개 글이거나, 구버전 포스팅입니다.");
        errorMessages.put(404, "[네이버 블로그 오류] : 유효하지 않은 요청입니다. 해당 블로그가 없습니다. 블로그 아이디를 확인해 주세요.");
        errorMessages.put(500, "[네이버 블로그 오류] : 예상치 못한 에러가 발생했습니다.");
        // Append other blog error code and message mapping here
    }

    @Override
    protected BlogPost parse(Document document) throws Exception {
        Elements post = extractPost(document);
        String title = extractTitle(post);
        Element content = extractContent(post);
        ConvertedTreeNode root = parseToTree(content);
        
        return new BlogPost(title, root);
    }

    private Elements extractPost(Document document) throws Exception {
        Elements post = document.select(".se-viewer"); // naver blog 포스트 부분
        if(post.size() == 0){
            throw new Exception(getErrorMessage(204));
        }
        return post;
    }

    private String extractTitle(Elements post) throws Exception {
        Elements head = post.select(".pcol1"); // naver blog 제목
		if(head.size() == 0){
			throw new Exception(getErrorMessage(204));
		}
        return head.text();
    }

    private Element extractContent(Elements post) throws Exception {
        Elements mainContainer = post.select(".se-main-container"); // naver blog 본문
		if(mainContainer.size() == 0){
			throw new Exception(getErrorMessage(204));
		} 
        return mainContainer.first();
    }

    private ConvertedTreeNode parseToTree(Element curElement){
        ConvertedTreeNode rootNode = ConvertedTreeNode.builder().type(StyleType.NONE).build();

        for(Element child : curElement.children()){
            Element sectionElement = child.child(0).child(0);
            if(isSection(sectionElement)){
                SectionParser sectionParser = getSectionParser(getSection(sectionElement));
                ConvertedTreeNode sectionNode = sectionParser.parseToTreeNode(sectionElement);
                rootNode.appendChild(sectionNode);
            }
        }

        return rootNode;
    }

    private static final Pattern sectionPattern = Pattern.compile("se-section se-section-([A-za-z]*)");
    private Boolean isSection(Element element){
        Matcher sectionMatcher = sectionPattern.matcher(element.className());
        if(sectionMatcher.find()) return true;
        return false;
    }

    private String getSection(Element element){
        Matcher sectionMatcher = sectionPattern.matcher(element.className());
        if(sectionMatcher.find()){
            return sectionMatcher.group(1);
        }
        return DEFAULT;
    }

    private SectionParser getSectionParser(String section){
        return parserMap.getOrDefault(section, parserMap.get(DEFAULT));
    }
}