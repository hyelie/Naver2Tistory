package convert.converters.tistory;

import convert.blogPost.ConvertedTreeNode;

// Implement this interface to convert other kind of StyleTypes.
public interface TypeConverter {
    public ConvertResultVO convertAndReturnNextNodes(ConvertedTreeNode node);

    public static String convertContent(String content){
        return content.isEmpty() ? System.lineSeparator() : content;
    }
}