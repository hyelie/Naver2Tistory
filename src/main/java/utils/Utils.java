package utils;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

public class Utils {
    private static final Scanner scanner = new Scanner(System.in);
    /**
     * Print 'message', get input, and return.
     * @param message - showing message to user.
     * @return input string.
     */
    public static String getInput(String message){
        System.out.println(message);
        String inputString = scanner.nextLine();
        return inputString;
    }

    public static void printMessage(String message){
        System.out.println(message);
    }

    /**
     * @param path - given file path want to read.
     * @return String array in the file.
     * @throws Exception when file does not exist.
     */
    public static List<String> readList(Path path) throws Exception{
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            // lines.stream().forEach(System.out::println);
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
     * Get absolute path of config file
     */
    public static String getConfigPath(){
        return Utils.getCurrentDirectory() + "/config";
    }

    /**
     * Get absolute path of URL list file
     */
    public static String getUrlListPath(){
        return Utils.getCurrentDirectory() + "/list.txt";
    }

    /**
     * Opens a new Internet window corresponding to the URL.
     * @param url - given URL which want to open.
     */
    public static void openWindow(String url){
        try{
            //Desktop.getDesktop().browse(new URI(url)); // TODO: desktop에서 작동하는지 확인.
        }
        catch(Exception E){
            return;
        }
    }

    /**
     * Download image from parameter 'imageSrcUrl' and return as byte format.
     */
    public static byte[] downloadByteImage(String imageSrcUrl) throws Exception{
		InputStream in = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        
		try {
			in = new URL(imageSrcUrl).openStream();
			while (true) {
				int data = in.read();
				if (data == -1) break;
				out.write(data);
			}
			in.close();
			out.close();
            return out.toByteArray();
		} catch (Exception e) {
			throw new Exception("이미지를 다운로드 하는 중 오류가 발생했습니다.");
		} finally {
			if (in != null) in.close();
			if (out != null) out.close();
		}
    }

    /**
     * Encode byte[] data to base 64.
     */
    public static String encodeByteToBase64(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * Decode base64 string to byte[].
     */
    public static byte[] decodeBase64ToByte(String data){
        return Base64.getDecoder().decode(data);
    }

    /**
     * Insert 'appendString' into 'originString' on index 'index'.
     * @param originString - origin string.
     * @param index - index which want to be inserted.
     * @param appendString - string which want to insert.
     * @return result of inserting 'appendString' into 'originString' on 'index' position.
     */
    public static String insert(String originString, Integer index, String appendString){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(originString);
        stringBuffer.insert(index, appendString);
        return stringBuffer.toString();
    }

    /**
     * Find Nth position of 'target' in 'originString' 
     * @param originString - origin string.
     * @param target - string which want to find.
     * @return Nth position of 'target' in 'origniString'. If not, return -1.
     */
    public static Integer findNthString(String originString, String target, int n){
        int position = -1;
        for(int i = 0; i<n; i++){
            position = originString.indexOf(target, position + 1);
            if(position == -1) return -1;
        }
        return position;
    }

    /**
     * Encode all special chars in given string.
     */
    public static String escapeSpecials(String string) {
        string = string.replace(">", "&gt;");
        string = string.replace("<", "&lt;");
        string = string.replace("\"", "&quot;");
        string = string.replace(" ", "&nbsp;");

        return string;
    }

    /**
     * Replace all chars to UTF-8.
     */
    public static String encodeToUTF8(String origin) throws Exception{
        try{
            return URLEncoder.encode(origin, "UTF-8");
        }
        catch (Exception e){
            throw new Exception("인코딩 중 문제가 발생했습니다.");
        }
    }
}
