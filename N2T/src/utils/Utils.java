package utils;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Utils {
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
    public static List<String> readList(Path path) throws Exception{
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
     * Get absolute path of image directory
     */
    public static String getImageDirectory(){
        return Utils.getCurrentDirectory() + "/N2T/image/";
    }

    /**
     * Get absolute path of config file
     */
    public static String getConfigPath(){
        return Utils.getCurrentDirectory() + "/N2T/config.json";
    }

    /**
     * Get absolute path of URL list file
     */
    public static String getURLListPath(){
        return Utils.getCurrentDirectory() + "/N2T/list.txt";
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
     * Download image from paramter 'imageSrc' and save in image folder
     */
    public static void downloadImage(String imageSrc) throws Exception{
        String imageFolderPath = Utils.getImageDirectory();
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new URL(imageSrc).openStream();
            int numImages = getNumImages();
			out = new FileOutputStream(imageFolderPath + String.valueOf(numImages) + ".jpg"); // 저장경로
			while (true) {
				int data = in.read();
				if (data == -1) break;
				out.write(data);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			throw new Exception("이미지를 다운로드 하는 중 오류가 발생했습니다.");
		} finally {
			if (in != null) in.close();
			if (out != null) out.close();
		}
    }

    /**
     * Clear all image files in image folder.
     */
    public static void clearImageFolder(){
        // 폴더 내의 모든 하위 이미지 삭제
		String imageFolderPath = Utils.getImageDirectory();
		File imageFolder = new File(imageFolderPath);
		File[] images = imageFolder.listFiles();
		for(File image : images){
			if(!image.delete()){
				System.out.println("[다운로드한 이미지 파일 삭제 중 오류] : 파일을 삭제하지 않고 내버려 둡니다.");
			}
		}
    }

    /**
     * Get number of images in image folder.
     */
    public static int getNumImages(){
        String imageFolderPath = Utils.getImageDirectory();
        File imageFolder = new File(imageFolderPath);
        File[] images = imageFolder.listFiles();
        int numImages = 0;
        for(File image : images) numImages++;
        return numImages;
    }

    /**
     * Convert image on 'path' to bytes.
     */
    public static byte[] fileToBytes(String path) throws Exception{
        try{
            File file = new File(path);
            byte[] imageBytes = Files.readAllBytes(file.toPath());
            return imageBytes;
        }
        catch(Exception e){
            throw new Exception("파일 변환 중 오류가 발생했습니다.");
        }
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
}
