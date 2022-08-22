import java.util.Scanner;

import convert.URLProcessor;

public class N2T {
    // 프로그램 실행 전에 config를 확인
    public void init(){
        // file 읽고, config 맞는지 확인
        System.out.println("[검증] : 기존에 입력한 사용자 정보를 확인 중입니다.");

        // 있으면 수정 물어보고, 없으면 바로 입력받음
        
        // 입력받은 후에 API가 정상작동하는지 테스트
    }

    // URL 가공 후 tistory 양식으로 stylize해서 upload
    public void step(String URL){
        // URL 가공
        try{
            URLProcessor urlProcessor = new URLProcessor();
            URL = urlProcessor.getOriginURL(URL);
        }
        catch(Exception e){
            System.out.println("[오류] : " + e.getMessage());
        }


        // stylize

        // upload
    }

    // 
    // 
    // 
    // 
    public void process(){
        // init();
        // url file input
        /*
        init() 실행 준비 후
        URL들 파일로 입력받고 
        while(N--){
            입력받은 URL들 프로세싱
        }
        프로세싱 이후 로그 txt 형식으로 출력
        티스토리 띄워줌
         */
    }
}
