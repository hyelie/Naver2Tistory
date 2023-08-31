package convert.scrappers.naver.sectionParsers;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;
import utils.Utils;

/*
Image is special. Therefore encode image in base64 format and insert into ConvertedTreeNode.content after downloading the image.
 */
public class ImageParser extends SectionParser {
    @Override
    public ConvertedTreeNode parseToTreeNode(Element element) {
        ConvertedTreeNode imageNode = ConvertedTreeNode.builder().type(StyleType.IMAGE).build();

        // image
        Element imageModule = element.child(0).select("img").get(0);
        String imageSrc = imageModule.attr("src");
        if(imageModule.hasAttr("data-lazy-src")) imageSrc = imageModule.attr("data-lazy-src");

        String imageContent = "";
        try {
            byte[] imageByte = Utils.downloadByteImage(imageSrc);
            imageContent = new String(imageByte);
        } catch (Exception e) {
            System.out.println("[이미지 전환 중 오류] : " + e.getMessage());
        }
        ConvertedTreeNode imageByteNode = ConvertedTreeNode.builder().type(StyleType.IMAGEBYTE).content(imageContent).build();
        imageNode.appendChild(imageByteNode);

        // caption        
        if(element.childrenSize() >= 2){
            ConvertedTreeNode captionNode = ConvertedTreeNode.builder().type(StyleType.CAPTION).build();
            Element captionModule = element.child(1);
            captionNode.appendChild(parseTextModule(captionModule));
            imageNode.appendChild(captionNode);
        }

        return imageNode;
    }
    
    
}
