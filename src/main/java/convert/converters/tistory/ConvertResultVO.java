package convert.converters.tistory;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConvertResultVO {
    private Element result;
    private List<ConvertedTreeNode> nextNodes = new ArrayList<>(); // next traversal target nodes

    public ConvertResultVO(Element result){
        this.result = result;
    }
}
