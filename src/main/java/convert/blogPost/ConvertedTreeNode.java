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

image의 경우 byte[] - string - byte[]로 변환하면 문제가 발생하기 때문에 base64로 인코딩한 값을 넣는다.
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
