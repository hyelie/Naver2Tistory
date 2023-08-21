package urlprocessor;

public class UrlVO {
    UrlType urlType;
    String url;

    public UrlVO(UrlType urlType, String url){
        this.urlType = urlType;
        this.url = url;
    }

    public UrlType getUrlType(){
        return this.urlType;
    }

    public String getUrl(){
        return this.url;
    }
}
