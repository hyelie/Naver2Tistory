package auth;

import utils.HttpConnection;
import utils.HttpConnectionVO;
import utils.Utils;

import java.net.URISyntaxException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class TistoryClient implements AuthClient {
    private String appId;
    private String secretKey;
    private String blogName;
    private String code;
    private String accessToken;
    private void setAccessToken(String accessToken){ this.accessToken = accessToken; };

    public TistoryClient() throws Exception{
        initializeFromConfig(); 
        authorize();
    }

    private void initializeFromConfig() throws Exception{
        Utils.printMessage("[검증 시작] : /config/tistory.json 파일에 입력한 사용자 정보를 확인합니다.");

        try{
            JSONParser parser = new JSONParser();
            Reader reader = new FileReader(Utils.getConfigPath() + "/tistory.json");
            JSONObject config = (JSONObject) parser.parse(reader);
            this.appId = (String) config.getOrDefault("APP_ID", null);
            this.secretKey = (String) config.getOrDefault("SECRET_KEY", null);
            this.blogName = (String) config.getOrDefault("BLOG_NAME", null);
            this.accessToken = (String) config.getOrDefault("ACCESS_TOKEN", null);
            if(this.appId == null || this.secretKey == null || this.blogName == null) throw new NullPointerException("[검증 실패] : /config/tistory.json 파일에 필수값이 없습니다.");
        }
        catch(Exception e){
            if(e instanceof FileNotFoundException){
                throw new Exception("[초기화 오류] : /config/tistory.json 파일이 없습니다.");
            }
            else if(e instanceof NullPointerException){
                throw new Exception("[초기화 오류] : /config/tistory.json 파일에 값이 없습니다.");
            }
            else{
                throw new Exception("[초기화 오류] : 초기화 중 알 수 없는 오류가 발생했습니다.");
            }
        }
    }

    public void authorize() throws Exception {
        Utils.printMessage("[검증 중] : /config/tistory.json 파일에 입력한 사용자 정보를 검증합니다.");
        if (!isAccessTokenValid()) {
            openIssueCodeWindow();
            String accessToken = issueAccessToken();
            setAccessToken(accessToken);
        }
        Utils.printMessage("[검증 완료] : /config/tistory.json 파일에 입력한 사용자 정보를 확인했습니다.");
    }

    /**
     * @see https://tistory.github.io/document-tistory-apis/apis/v1/blog/list.html
     */
    private Boolean isAccessTokenValid() throws Exception {
        String checkAccessTokenUrl = "https://www.tistory.com/apis/blog/info?"
                                   + "access_token=" + this.accessToken + "&"
                                   + "output=xml";
        try{
            HttpConnectionVO result = HttpConnection.request(checkAccessTokenUrl, "GET", null);
            return result.getCode() == 200;
        }
        catch(Exception e){
            throw new Exception("[티스토리 acess token 검증 중 오류] : " + e.getMessage());
        }
    }

    /**
     * @see https://tistory.github.io/document-tistory-apis/auth/authorization_code.html
     */
    private void openIssueCodeWindow() throws Exception{     
        String issueCodeUrl = "https://www.tistory.com/oauth/authorize?"
                            + "client_id=" + this.appId + "&"
                            + "redirect_uri=http://" + this.blogName + ".tistory.com/" + "&"
                            + "response_type=code";

        try{
            Utils.printMessage("            다음 링크를 인터넷 창에 입력해 주세요.");
            Utils.printMessage("            " + issueCodeUrl);
            Utils.openWindow(issueCodeUrl);
            this.code = Utils.getInput("            발급받은 Code를 입력해 주세요.");
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
     * @see https://tistory.github.io/document-tistory-apis/auth/authorization_code.html
     */
    private String issueAccessToken() throws Exception{
        String isCodeValidUrl = "https://www.tistory.com/oauth/access_token?"
                              + "client_id=" + this.appId + "&"
                              + "client_secret=" + this.secretKey + "&"
                              + "redirect_uri=http://" + this.blogName + ".tistory.com/" + "&"
                              + "code=" + this.code + "&"
                              + "grant_type=authorization_code";

        try{
            HttpConnectionVO result = HttpConnection.request(isCodeValidUrl, "GET", null);
            if(result.getCode() == 200){
                String[] responseArray = result.getBody().toString().split("=");
                return responseArray[1];
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
     * Upload post in Tistory.
     * @see https://tistory.github.io/document-tistory-apis/apis/v1/post/write.html
     * @throws Exception when exception occur while checking or issuing token.
     */
    public void post(String title, String content) throws Exception {
        String postUrl = "https://www.tistory.com/apis/post/write";
        String param = "access_token=" + this.accessToken + "&"
                        + "blogName=" + this.blogName + "&"
                        + "title=" + title + "&"
                        + "content=" + content + "&"
                        + "visibility=0";
        try{
            HttpConnectionVO result = HttpConnection.request(postUrl, "POST", param);
            if(result.getCode() >= 300){
                throw new Exception("[티스토리 포스팅 실패] : " + result.getBody());
            }
        }
        catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Upload image to Tistory server and get 'replacer' list.
     * @see https://tistory.github.io/document-tistory-apis/apis/v1/post/attach.html
     */
    public String uploadImageAndGetReplacer(byte[] imageByte) throws Exception{
        String postUrl = "https://www.tistory.com/apis/post/attach";
        String param = "access_token=" + this.accessToken + "&"
                     + "blogName=" + this.blogName + "&"
                     + "output=json";
        try{
            HttpConnectionVO result = HttpConnection.request(postUrl, "POST", param, imageByte);

            if(result.getCode() == 200){
                JSONParser jsonParser = new JSONParser();
                JSONObject responseBody = (JSONObject) jsonParser.parse(result.getBody());
                return ((JSONObject) responseBody.get("tistory")).get("replacer").toString();
            }
            return "";
        }
        catch(Exception e){
            throw new Exception("[티스토리 이미지 업로드 중 오류] : " + e.getMessage());
        }
    }
}