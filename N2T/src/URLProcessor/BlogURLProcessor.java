package URLProcessor;

// Implement this interface to preprocess other types of blog URLs.
public interface BlogURLProcessor {
    /**
     * Return true if inputURL matches type of blog URL format, otherwise false.
     */
    Boolean matches(String inputURL);

    /**
     * Process inputURL to crawlable URL and return. 
     * @throws Exception when error occurs while processing URL.
     */
    String process(String inputURL) throws Exception;
}