package utils;

/**
 * consists of http status code 'code', http response body 'body'.
 */
public class HttpConnectionVO {
    private Integer code;
    private String body;

    public HttpConnectionVO(Integer code, String body){
        this.code = code;
        this.body = body;
    }

    public void setCode(Integer code){
        this.code = code;
    }
    public Integer getCode(){
        return code;
    }
    public void setBody(String body){
        this.body = body;
    }
    public String getBody(){
        return body;
    }
}
