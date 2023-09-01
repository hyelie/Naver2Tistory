package convert.converters.tistory.typeConverters;

import org.jsoup.nodes.Element;

import auth.TistoryClient;
import convert.blogPost.ConvertedTreeNode;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;
import utils.Utils;

public class ImageConverter implements TypeConverter {
    TistoryClient tistoryClient;
    public ImageConverter(TistoryClient tistoryClient){
        this.tistoryClient = tistoryClient;
    }

    @Override
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode imageNode) {
        /*
        imageNode의 구조
        --IMAGE, 
        ----IMAGEBASE64
        [----CAPTION, 
        ------TEXT, 
        --------PARAGRAPH_DEFAULT, 
        ----------CONTENT,]
         */
        ConvertedTreeNode imageByteNode = imageNode.getChilds().get(0);
        String replacer = uploadImageAndGetReplacer(imageByteNode.getContent());
        
        if(hasCaption(imageNode)){
            ConvertedTreeNode captionNode = imageNode.getChilds().get(1);
            String caption = getCaption(captionNode);
            replacer = insertCaptionIntoReplacer(replacer, caption);
        }

        Element imageElement = createImageElement(replacer);
        
        return new ConvertResultVO(imageElement);
    }

    private String uploadImageAndGetReplacer(String imageContent){
        byte[] imageByte = Utils.decodeBase64ToByte(imageContent);
        try{
            return tistoryClient.uploadImageAndGetReplacer(imageByte);
        }
        catch (Exception e){
            Utils.printMessage("[티스토리 이미지 업로드 중 오류] : 해당 이미지를 올리지 않고 다음 작업을 진행합니다.");
        }
        return "";
    }

    private Boolean hasCaption(ConvertedTreeNode imageNode){
        return imageNode.getChilds().size() >= 2;
    }

    private String getCaption(ConvertedTreeNode node){
        String result = node.getContent();
        for(ConvertedTreeNode child : node.getChilds()){
            result += getCaption(child);
        }
        return result;
    }

    private String insertCaptionIntoReplacer(String replacer, String caption){
        Integer captionIndex = Utils.findNthString(replacer, "|", 4);
        if(isInsertable(captionIndex)){
            replacer = Utils.insert(replacer, captionIndex+1, caption);
        }
        return replacer;
    }

    private Boolean isInsertable(Integer nthIndex){
        return nthIndex != -1;
    }

    private Element createImageElement(String replacer){
        Element imageElement = new Element("p");
        imageElement.text(replacer);
        
        return imageElement;
    }
    
}
