package convert.converters;

import convert.blogPost.ConvertedTreeNode;

// Implement this interface to convert ConvertedTreeNode to other types of blogs.
public interface Converter {
    public String convert(ConvertedTreeNode root);
}
