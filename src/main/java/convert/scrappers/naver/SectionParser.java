package convert.scrappers.naver;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import convert.SupportType;
import convert.blogPost.ConvertedTreeNode;

// Inherit this abstract class to parse other types of naver blog sections.
public abstract class SectionParser {
    abstract public ConvertedTreeNode parseToTreeNode(Element element);

    // Regular expression filtering [se-text-paragraph se-text-paragraph-{ALIGN-TYPE}] format
    private static final Pattern paragraphPattern = Pattern.compile("se-text-paragraph se-text-paragraph-align-([A-za-z]*)");
    private static Map<String, SupportType> styleMap = new HashMap<>();
    static{
        initializeStyleMap();
    }

    private static void initializeStyleMap(){
        styleMap.put("", SupportType.PARAGRAPH_DEFAULT);
        styleMap.put("right", SupportType.PARAGRAPH_RIGHT);
        styleMap.put("justify", SupportType.PARAGRAPH_LEFT);
        styleMap.put("center", SupportType.PARAGRAPH_CENTER);
        styleMap.put("a", SupportType.LINK);
        styleMap.put("b", SupportType.BOLD);
        styleMap.put("i", SupportType.TILT);
        styleMap.put("u", SupportType.UNDERBAR);
        styleMap.put("strike", SupportType.STRIKE);
        // Append other [naver blog style to SupportType mapping] here
    }

    protected ConvertedTreeNode parseTextModule(Element textModule) { // se-module-text
        ConvertedTreeNode textNode = ConvertedTreeNode.builder().type(SupportType.TEXT).build();

        for(Element textParagraph : textModule.children()){ // se-text-paragraph
            String align = getAlignFromElement(textParagraph);
            SupportType alignType = getAlignTypeFromMap(align);
            ConvertedTreeNode paragraphNode = parseTextParagraph(textParagraph, alignType);
            textNode.appendChild(paragraphNode);
        }

        return textNode;
    }

    private ConvertedTreeNode parseTextParagraph(Element textParagraph, SupportType alignType){
        ConvertedTreeNode paragraphNode = ConvertedTreeNode.builder().type(alignType).build();
        for(Element spanElement : textParagraph.children()){
            paragraphNode.appendChild(parseSpanElementToTreeNode(spanElement));
        }
        return paragraphNode;
    }

    // span 이하 element들에 대해 DFS 이후 tagname을 style로 지정한 tree node return
    private ConvertedTreeNode parseSpanElementToTreeNode(Element element){
        if(isSpanAndParent(element)) return parseSpanElementToTreeNode(element.child(0));

        SupportType styleType = getStyleTypeFromMap(element.tagName());
        
        if(element.childrenSize() == 0){
            String content = element.text();
            return ConvertedTreeNode.builder().type(styleType).content(content).build();
        }
        
        ConvertedTreeNode curNode = ConvertedTreeNode.builder().type(styleType).build();
        for(Element child : element.children()){
            curNode.appendChild(parseSpanElementToTreeNode(child));
        }
        return curNode;
    }

    private Boolean isSpanAndParent(Element element){
        if(element.tagName() == "span" && element.childrenSize() != 0) return true;
        return false;
    }

    private String getAlignFromElement(Element textParagraph){
        Matcher paragraphMatcher = paragraphPattern.matcher(textParagraph.className());

        if(paragraphMatcher.find()){
            return paragraphMatcher.group(1);
        }
        return "";
    }

    private SupportType getAlignTypeFromMap(String align){
        return styleMap.getOrDefault(align, SupportType.PARAGRAPH_DEFAULT);
    }
    
    private SupportType getStyleTypeFromMap(String style){
        return styleMap.getOrDefault(style, SupportType.NONE);
    }
}
