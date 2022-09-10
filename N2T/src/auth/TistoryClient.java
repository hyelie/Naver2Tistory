package auth;

import utils.HttpConnection;
import utils.HttpConnectionVO;
import utils.Util;

import java.net.URISyntaxException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class TistoryClient {
    private String appId;
    private String secretKey;
    private String blogName;
    public String getBlogName(){ return this.blogName; }
    private String code;
    private String accessToken;

    /**
     * Construct TistoryClient object using config.json file.
     * Read and store configuration datas from config.json.
     * @throws Exception when config.json file does not exist, or unexpected error occurs.
     */
    public TistoryClient() throws Exception{
        try{
            JSONParser parser = new JSONParser();
            Reader reader = new FileReader(Util.getCurrentDirectory() + "/N2T/src/auth/config.json");
            JSONObject config = (JSONObject) parser.parse(reader);
            this.appId = (String) config.getOrDefault("APP_ID", null);
            this.secretKey = (String) config.getOrDefault("SECRET_KEY", null);
            this.blogName = (String) config.getOrDefault("BLOG_NAME", null);
            if(this.appId == null || this.secretKey == null || this.blogName == null) throw new NullPointerException();
        }
        catch(Exception e){
            if(e instanceof FileNotFoundException){
                throw new Exception("[초기화 오류] : config.json 파일이 없습니다.");
            }
            else if(e instanceof NullPointerException){
                throw new Exception("[초기화 오류] : config.json 파일에 값이 없습니다.");
            }
            else{
                throw new Exception("[초기화 오류] : 초기화 중 알 수 없는 오류가 발생했습니다.");
            }
        }
    }

    /**
     * Issue new 'code' from Tistory API and store in the attribute.
     * @throws Exception when error occurs while issuing code from Tistory API.
     */
    private void issueCode() throws Exception{
        String issueCodeURL = String.format("https://www.tistory.com/oauth/authorize?client_id=%s&redirect_uri=http://%s.tistory.com&response_type=code", this.appId, this.blogName);
        try{
            System.out.println("            다음 링크를 인터넷 창에 입력해 주세요.");
            System.out.println("            " + issueCodeURL);
            Util.openWindow(issueCodeURL);
            this.code = Util.getInput("            발급받은 Code를 입력해 주세요.");
        }
        catch (Exception e){
            this.code = null;
            if(e instanceof IOException){
                throw new Exception("[티스토리 code 발급 중 오류] : 입출력 오류가 발생했습니다.");
            }
            else if(e instanceof URISyntaxException){
                throw new Exception("[티스토리 code 발급 중 오류] : 사용자 ID가 잘못되었습니다.");    
            }
        }
    }

    /**
     * Determine whether 'accessToken' attribute is valid or not.
     * @return true if accessToken is valid, false otherwise.
     * @throws Exception when error occurs while connecting to URL.
     */
    private Boolean isAccessTokenValid() throws Exception {
        String checkAccessTokenURL = "https://www.tistory.com/apis/blog/info?"
                                   + "access_token=" + this.accessToken + "&"
                                   + "output=xml";
        HttpConnectionVO result;
        try{
            result = HttpConnection.connect(checkAccessTokenURL, "GET", null);
            if(result.getCode() != 200) return false;
            else return true;
        }
        catch(Exception e){
            throw new Exception("[티스토리 acess token 검증 중 오류] : " + e.getMessage());
        }
    }

    /**
     * Issue new 'accessToken' and store in the attribute.
     * @throws Exception when error occurs while connecting to URL.
     */
    private void issueAccessToken() throws Exception{
        String isCodeValidURL = "https://www.tistory.com/oauth/access_token?"
                              + "client_id=" + this.appId + "&"
                              + "client_secret=" + this.secretKey + "&"
                              + "redirect_uri=http://" + this.blogName + ".tistory.com" + "&"
                              + "code=" + this.code + "&"
                              + "grant_type=authorization_code";
        
        HttpConnectionVO result;
        try{
            result = HttpConnection.connect(isCodeValidURL, "GET", null);
            if(result.getCode() == 200){
                String[] responseArray = result.getBody().toString().split("=");
                this.accessToken = responseArray[1];
            }
            else{
                throw new Exception("CODE, APP_ID, 또는 SECRET_KEY가 잘못되었습니다. 다시 한 번 확인해 주세요.");
            }
        }
        catch(Exception e){
            throw new Exception("[티스토리 acess token 발급 중 오류] : " + e.getMessage());
        }
    }

    /**
     * Authorize in tistory. If current config data is wrong,
     * then issue new config data. Finally store config data in the attribute.
     * 
     * @throws Exception when exception occur while checking or issuing token.
     */
    public void authorize() throws Exception {
        try{
            if(!isAccessTokenValid()){
                issueCode();
                issueAccessToken();
            }
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Upload post in Tistory. If error occurs while posting,
     * then throws Exception.
     * 
     * @param title - posting title
     * @param content - posting content
     * @throws Exception when exception occur while checking or issuing token.
     */
    public void post(String title, String content) throws Exception {
        try{
            String postURL = "https://www.tistory.com/apis/post/write";
            String param = "access_token=" + this.accessToken + "&"
                         + "blogName=" + this.blogName + "&"
                         + "title=" + title + "&"
                         + "content=" + content;
            HttpConnection.connect(postURL, "POST", param);
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}