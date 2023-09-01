package auth;

// Implement this interface to post other types of blogs.
public interface AuthClient {
    /**
     * Authorize to upload post
     */
    public void authorize() throws Exception;

    /**
     * Upload post in each blog.
     * @param title : post title
     * @param content : post content
     */
    public void post(String title, String content) throws Exception;
}
