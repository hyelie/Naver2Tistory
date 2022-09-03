package utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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

    public List<String> readFile(Path path){
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            lines.stream().forEach(System.out::println);
            return lines;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    public void writeFile(){

    }

    public String getCurrentDirectory(){
        Path path = FileSystems.getDefault().getPath("");
		return path.toAbsolutePath().toString();
    }
    
}
