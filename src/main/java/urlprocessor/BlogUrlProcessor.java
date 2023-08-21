package urlprocessor;

// Implement this interface to preprocess other types of blog URLs.
public interface BlogUrlProcessor {
    /**
     * @return blog type of each url processor
     */
    public UrlType getUrlType();

    /**
     * Return true if inputUrl matches type of blog URL format, otherwise false.
     */
    Boolean matches(String inputUrl);

    /**
     * Process inputUrl to crawlable URL and return. 
     * @throws Exception when error occurs while processing URL.
     */
    String process(String inputUrl) throws Exception;
}