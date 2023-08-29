package convert.blogPost;

// Commonly used VO in this project
public class BlogPost {
    private String title;
    private ConvertedTreeNode root;

    public BlogPost(String title, ConvertedTreeNode root){
        this.title = title;
        this.root = root;
    }

    public String getTitle(){
        return title;
    }

    public ConvertedTreeNode getRoot(){
        return root;
    }
}
