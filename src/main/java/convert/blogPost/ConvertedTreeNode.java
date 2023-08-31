package convert.blogPost;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/*
leaf node만 Node.content가 ""이 아니다.
일반 텍스트는 [TEXT - PARAGRAPH - CONTENT - [style]] 순서로 이루어진다.
만약 style이 있는 경우 CONTENT 아래에 style에 해당하는 node가 더 붙고,
style이 없는 경우 CONTENT에 바로 내용이 붙는다.
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
