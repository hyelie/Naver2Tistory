package convert.scrappers.naver.sectionParsers;

import org.jsoup.nodes.Element;

import convert.SupportType;
import convert.blogPost.ConvertedTreeNode;
import convert.scrappers.naver.SectionParser;

public class TableParser extends SectionParser {
    @Override
    public ConvertedTreeNode parseToTreeNode(Element element){
        ConvertedTreeNode tableNode = ConvertedTreeNode.builder().type(SupportType.TABLE).build();

        Element tableBody = element.child(0).child(0).child(0);
        for(Element row : tableBody.children()){
            ConvertedTreeNode rowNode = ConvertedTreeNode.builder().type(SupportType.ROW).build();
            tableNode.appendChild(rowNode);
            for(Element column : row.children()){
                ConvertedTreeNode colNode = ConvertedTreeNode.builder().type(SupportType.COLUMN).build();
                for(Element textModule : column.children()){
                    colNode.appendChild(parseTextModule(textModule));
                }
                rowNode.appendChild(colNode);
            }
        }

        return tableNode;
    }
}
