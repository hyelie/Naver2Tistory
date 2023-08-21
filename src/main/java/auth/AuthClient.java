package auth;

public interface AuthClient {
    /**
     * Upload post in each blog.
     * @param title : post title
     * @param content : post content
     */
    public void post(String title, String content) throws Exception;
}
