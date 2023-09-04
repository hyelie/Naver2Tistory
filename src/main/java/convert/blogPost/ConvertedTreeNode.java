package convert.blogPost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/*
Only the leaf node's content is not "".
Plain text in this tree consists of the following order: [TEXT - PARAGRAPH - CONTENT - [style]].
If there is a style, an additional node corresponding to the style is added under the CONTENT. example) [TEXT - PARAGRAPH - CONTENT - BOLD - UNDERLINE - CONTENT]
If there is no style, the content is attached directly to CONTENT. example) [TEXT - PARAGRAPH - CONTENT]

In the case of image, problems occur when converting byte[] - string - byte[], so enter a base64 encoded value into node.content.
*/
@Getter
@Builder
@AllArgsConstructor
public class ConvertedTreeNode {
    @Builder.Default
    private StyleType type = StyleType.NONE;

    @Builder.Default
    private String content = "";

    @Builder.Default
    private List<ConvertedTreeNode> childs = new ArrayList<>();

    public void appendChild(ConvertedTreeNode convertedTreeElement){
        childs.add(convertedTreeElement);
    }

    public Boolean isLeaf(){
        if(childs.size() == 0) return true;
        return false;
    }
}
