package converter.tistory.typeConverters;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import convert.blogPost.ConvertedTreeNode;
import convert.blogPost.StyleType;
import convert.converters.tistory.ConvertResultVO;
import convert.converters.tistory.TypeConverter;
import convert.converters.tistory.typeConverters.QuotationConverter;
import utils.NodeTestUtils;

public class QuotationConverterTest {
    TypeConverter quotationConverter = new QuotationConverter();

    @Test
    public void testQuotationConverter() {
        // given
        ConvertedTreeNode quotationNode = ConvertedTreeNode.builder().type(StyleType.QUOTATION).build();
        ConvertedTreeNode quoteNode = ConvertedTreeNode.builder().type(StyleType.QUOTE).build();
        quotationNode.appendChild(quoteNode);
        ConvertedTreeNode quoteTextNode = ConvertedTreeNode.builder().type(StyleType.TEXT).build();
        quoteNode.appendChild(quoteTextNode);

        ConvertedTreeNode citeNode = ConvertedTreeNode.builder().type(StyleType.CITE).build();
        quotationNode.appendChild(citeNode);
        ConvertedTreeNode quoteCiteNode = ConvertedTreeNode.builder().type(StyleType.TEXT).build();
        citeNode.appendChild(quoteCiteNode);

        // when
        ConvertResultVO convertResultVO = quotationConverter.convertAndReturnNextNodes(quotationNode);

        // then
        String quotationHtml = "<blockquote data-ke-style=\"style1\"></blockquote>";
        Document doc = Jsoup.parse(quotationHtml);
        Element quotationElement = doc.body().child(0);
        List<ConvertedTreeNode> expectedList = new ArrayList<>();
        expectedList.add(quoteTextNode);
        expectedList.add(ConvertedTreeNode.builder().type(StyleType.CONTENT).content("").build());
        expectedList.add(quoteCiteNode);

        NodeTestUtils.assertElementEquals(quotationElement, convertResultVO.getResult());
        NodeTestUtils.assertNodeListEquals(expectedList, convertResultVO.getNextNodes());
    }
}
