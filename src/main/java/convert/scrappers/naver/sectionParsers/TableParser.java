package convert.scrappers.naver.sectionParsers;

import org.jsoup.nodes.Element;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.scrappers.naver.SectionParser;

public class TableParser extends SectionParser {
    @Override
    public ConvertedTreeNode parseToTreeNode(Element element){
        ConvertedTreeNode tableNode = ConvertedTreeNode.builder().type(StyleType.TABLE).build();

        Element tableBody = element.child(0).child(0).child(0);
        for(Element row : tableBody.children()){
            ConvertedTreeNode rowNode = ConvertedTreeNode.builder().type(StyleType.ROW).build();
            tableNode.appendChild(rowNode);
            for(Element column : row.children()){
                ConvertedTreeNode colNode = ConvertedTreeNode.builder().type(StyleType.COLUMN).build();
                for(Element textModule : column.children()){
                    colNode.appendChild(parseTextModule(textModule));
                }
                rowNode.appendChild(colNode);
            }
        }

        return tableNode;
    }
}
