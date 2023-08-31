package convert.converters.tistory;

import convert.blogPost.ConvertedTreeNode;
import utils.Utils;

// Implement this interface to convert other kind of StyleTypes.
public interface TypeConverter {
    abstract public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode node);

    public static String convertContent(String content){
        content = Utils.escapeSpecials(content);
        return content.isEmpty() ? "&nbsp;" : content;
    }
}