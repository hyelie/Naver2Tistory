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
    public Integer getCode(){
        return code;
    }
    public String getBody(){
        return body;
    }
}
