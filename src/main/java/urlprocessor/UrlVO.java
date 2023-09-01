package urlprocessor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import migrator.BlogType;

@Getter
@AllArgsConstructor
public class UrlVO {
    BlogType urlType;
    String url;
}