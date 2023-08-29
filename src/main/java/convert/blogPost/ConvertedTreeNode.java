package convert.blogPost;

import convert.SupportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ConvertedTreeNode {
    @Builder.Default
    private SupportType type = SupportType.NONE;

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
