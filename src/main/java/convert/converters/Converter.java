package convert.converters;

import convert.blogPost.BlogPost;

// Implement this interface to convert ConvertedTreeNode to other types of blogs.
public interface Converter {
    public String convert(BlogPost blogPost);
}
