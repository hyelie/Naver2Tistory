import java.io.File;

import convert.SupportType;
import convert.blogPost.BlogPost;
import convert.blogPost.ConvertedTreeNode;
import convert.scrappers.naver.NaverScrapper;
import urlprocessor.blogUrlProcessors.NaverUrlProcessor;
import utils.Utils;

public class Main {
    public static void main(String[] args) throws Exception {
        // N2T n2t = new N2T();
        // n2t.process();

        NaverScrapper naverScrapper = new NaverScrapper();
        String testUrl = "https://blog.naver.com/PostView.naver?blogId=jhi990823&logNo=222848946417";
        BlogPost blogPost = naverScrapper.scrap(testUrl);
        print(blogPost.getRoot(), 0);
    }

    private static void print(ConvertedTreeNode node, int depth){
        String result = "";
        for(int i = 0; i<depth; i++) result += "--";
        if(node.getType() == SupportType.IMAGEBYTE) result += node.getType();
        else result += node.getType() + ", " + node.getContent();
        System.out.println(result);

        if(node.isLeaf()) return;
        for(ConvertedTreeNode child : node.getChilds()){
            print(child, depth+1);
        }
    }
}
