package convert.converters.tistory;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Element;

import auth.TistoryClient;
import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.converters.Converter;
import convert.converters.tistory.typeConverters.*;
import convert.converters.tistory.typeConverters.contentConverters.*;
import convert.converters.tistory.typeConverters.paragraphConverters.*;
import utils.Utils;

public class TistoryConverter implements Converter {
    private TistoryClient tistoryClient;
    private static Map<StyleType, TypeConverter> converterMap = new HashMap<>();

    public TistoryConverter(TistoryClient tistoryClient){
        this.tistoryClient = tistoryClient;
        initializeConverterMap();
    }

    private void initializeConverterMap(){
        converterMap.put(StyleType.TABLE, new TableConverter());
        converterMap.put(StyleType.ROW, new RowConverter());
        converterMap.put(StyleType.COLUMN, new ColumnConverter());

        converterMap.put(StyleType.QUOTATION, new QuotationConverter());

        converterMap.put(StyleType.PARAGRAPH_DEFAULT, new DefaultParagraphConverter());
        converterMap.put(StyleType.PARAGRAPH_LEFT, new LeftParagraphConverter());
        converterMap.put(StyleType.PARAGRAPH_RIGHT, new RightParagraphConverter());
        converterMap.put(StyleType.PARAGRAPH_CENTER, new CenterParagraphConverter());

        converterMap.put(StyleType.LINK, new LinkConverter());
        converterMap.put(StyleType.BOLD, new BoldConverter());
        converterMap.put(StyleType.TILT, new TiltConverter());
        converterMap.put(StyleType.UNDERBAR, new UnderbarConverter());
        converterMap.put(StyleType.STRIKE, new StrikeConverter());
        
        converterMap.put(StyleType.CODE, new CodeConverter());
        converterMap.put(StyleType.IMAGE, new ImageConverter(tistoryClient));
        converterMap.put(StyleType.HORIZONTALLINE, new HorizontalLineConverter());
        converterMap.put(StyleType.NONE, new DefaultConverter());
        // Append other TypeConverter implements here
    }

    @Override
    public String convert(ConvertedTreeNode root) {
        Element convertedElement = traverseAndConvert(root);
        
        return encodeToUTF8(convertedElement.outerHtml());
    }

    private Element traverseAndConvert(ConvertedTreeNode curNode){
        TypeConverter typeConverter = getTypeConverter(curNode.getType());

        ConvertResultVO convertResult = typeConverter.convertAndReturnNextNodes(curNode);
        Element curElement = convertResult.getResult();

        for(ConvertedTreeNode nextNode : convertResult.getNextNodes()){
            Element nextElement = traverseAndConvert(nextNode);
            curElement.appendChild(nextElement);
        }

        return curElement;
    }

    private TypeConverter getTypeConverter(StyleType supportType){
        return converterMap.getOrDefault(supportType, converterMap.get(StyleType.NONE));
    }

    private String encodeToUTF8(String origin){
        try{
            return Utils.encodeToUTF8(origin);
        }
        catch(Exception e) {
            System.out.println(e.getMessage() + " 인코딩을 취소합니다.");
        }
        return origin;
    }
}