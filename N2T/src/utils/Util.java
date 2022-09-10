package utils;

import java.awt.Desktop;
import java.net.URI;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

// 파일입출력 등 귀찮은 것들 모아둠
public class Util {
    /**
     * Print 'message', get input, and return.
     * @param message - showing message to user.
     * @return input string.
     */
    public static String getInput(String message){
        Scanner scan = new Scanner(System.in); 
        System.out.println(message);
        String inputString = scan.next();
        scan.close();
        return inputString;
    }

    /**
     * @param path - given file path want to read.
     * @return String array in the file.
     * @throws Exception when file does not exist.
     */
    public static List<String> readFile(Path path) throws Exception{
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            lines.stream().forEach(System.out::println);
            return lines;
        }
        catch (IOException e) {
            throw new Exception("[초기화 오류] : list.txt 파일이 없습니다.");
        }
    }

    public static void writeFile(){

    }

    /**
     * Get absolute path of current directory
     */
    public static String getCurrentDirectory(){
        Path path = FileSystems.getDefault().getPath("");
		return path.toAbsolutePath().toString();
    }    

    /**
     * Opens a new Internet window corresponding to the URL.
     * @param url - given URL which want to open.
     */
    public static void openWindow(String url){
        try{
            Desktop.getDesktop().browse(new URI(url)); // TODO: desktop에서 작동하는지 확인.
        }
        catch(Exception E){
            return;
        }
    }
}
