package convert;

public enum ResponseMessage{
    NOT_FOUND("[네이버 블로그 오류] : 유효하지 않은 요청입니다. 해당 블로그가 없습니다. 블로그 아이디를 확인해 주세요."),      // Invalid blog ID
    NO_CONTENT("[네이버 블로그 오류] : 삭제되거나, 존재하지 않거나, 비공개 글이거나, 구버전 포스팅입니다."),                  // Deleted, private or written by old version of Naver Editor
    INTERNAL_ERROR("[네이버 블로그 오류] : 예상치 못한 에러가 발생했습니다.");                                              // Unexpected error

    private final String label;
    ResponseMessage(String label){
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}