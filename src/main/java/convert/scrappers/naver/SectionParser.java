package convert.scrappers.naver;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;

// Inherit this abstract class to parse other types of naver blog sections.
public abstract class SectionParser {
    abstract public ConvertedTreeNode parseToTreeNode(Element element);

    // Regular expression filtering [se-text-paragraph se-text-paragraph-{ALIGN-TYPE}] format
    private static final Pattern paragraphPattern = Pattern.compile("se-text-paragraph se-text-paragraph-align-([A-za-z]*)");
    private static Map<String, StyleType> styleMap = new HashMap<>();
    static{
        initializeStyleMap();
    }

    private static void initializeStyleMap(){
        styleMap.put("", StyleType.PARAGRAPH_DEFAULT);
        styleMap.put("right", StyleType.PARAGRAPH_RIGHT);
        styleMap.put("justify", StyleType.PARAGRAPH_LEFT);
        styleMap.put("center", StyleType.PARAGRAPH_CENTER);
        styleMap.put("a", StyleType.LINK);
        styleMap.put("b", StyleType.BOLD);
        styleMap.put("i", StyleType.TILT);
        styleMap.put("u", StyleType.UNDERLINE);
        styleMap.put("strike", StyleType.STRIKE);
        // Append other [naver blog style to StyleType mapping] here
    }

    protected ConvertedTreeNode parseTextModule(Element textModule) { // se-module-text
        ConvertedTreeNode textNode = ConvertedTreeNode.builder().type(StyleType.TEXT).build();

        for(Element textParagraph : textModule.children()){ // se-text-paragraph
            String align = getAlignFromElement(textParagraph);
            StyleType alignType = getAlignTypeFromMap(align);
            ConvertedTreeNode paragraphNode = parseTextParagraph(textParagraph, alignType);
            textNode.appendChild(paragraphNode);
        }

        return textNode;
    }

    private ConvertedTreeNode parseTextParagraph(Element textParagraph, StyleType alignType){
        ConvertedTreeNode paragraphNode = ConvertedTreeNode.builder().type(alignType).build();
        for(Element spanElement : textParagraph.children()){
            paragraphNode.appendChild(parseSpanElementToTreeNode(spanElement));
        }
        return paragraphNode;
    }

    // span 이하 element들에 대해 DFS 이후 tagname을 style로 지정한 tree node return
    private ConvertedTreeNode parseSpanElementToTreeNode(Element element){
        StyleType styleType = getTextStyleFromMap(element.tagName());
        
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

    private String getAlignFromElement(Element textParagraph){
        Matcher paragraphMatcher = paragraphPattern.matcher(textParagraph.className());

        if(paragraphMatcher.find()){
            return paragraphMatcher.group(1);
        }
        return "";
    }

    private StyleType getAlignTypeFromMap(String align){
        return styleMap.getOrDefault(align, StyleType.PARAGRAPH_DEFAULT);
    }
    
    private StyleType getTextStyleFromMap(String style){
        return styleMap.getOrDefault(style, StyleType.CONTENT);
    }
}
