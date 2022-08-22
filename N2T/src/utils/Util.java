package utils;

import java.util.Scanner;

// 파일입출력 등 귀찮은 것들 모아둠
public class Util {
    


    // 입력 메시지 출력해주고, 입력받고 리턴
    public String getInput(String message){
        Scanner scan = new Scanner(System.in); 
        System.out.println(message);
        String inputString = scan.next();
        scan.close();
        return inputString;
    }

    public void getConfig(){

    }
    // config가 정상작동 하는지
    public void testConfig(){

    }

    public void setConfig(){
        
    }

    public void readFile(){

    }

    public void writeFile(){

    }
}
