package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpConnection {
	/**
	 * Connect to parameter 'targetURL' with type 'type', paramter 'parameter'.
	 * 
	 * @param targetURL - connecting url
	 * @param type - HTTP type. (GET, POST, ...)
	 * @param parameter - HTTP connection paramter
	 * @return HTTPConnectionVO. Http status body formed by XML.
	 * @see HttpConnectionVO
	 * @throws Exception when connection timeout, or refused, otherwise.
	 */
	public static HttpConnectionVO connect(String targetURL, String type, String parameter) throws Exception {
		HttpURLConnection con = null;
		BufferedReader in = null;
		try {
			URL url = new URL(targetURL);
			con = (HttpURLConnection) url.openConnection();

			// 연결 type 지정
			con.setRequestMethod(type);
			if (type != "GET") {
				con.setDoOutput(true);
				con.getOutputStream().write(parameter.getBytes());		
			}

			// response code에 따라 input stream 지정.
			Integer responseCode = con.getResponseCode();
			InputStream responseStream;
			if(responseCode < HttpURLConnection.HTTP_BAD_REQUEST){
				responseStream = con.getInputStream();
			}
			else{
				responseStream = con.getErrorStream();
			}			
			
			// 결과 출력
			in = new BufferedReader(new InputStreamReader(responseStream));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			return new HttpConnectionVO(responseCode, response.toString());
		}
		catch (Exception e) {
			if (e instanceof SocketTimeoutException) {
				throw new Exception("연결 시간 초과입니다.");
			} else if (e instanceof ConnectException) {
				throw new Exception("연결이 거부되었습니다.");
			} else {
				throw new Exception("연결 중 알 수 없는 오류가 발생했습니다.");
			}
		}
		finally{
			if(con != null) con.disconnect();
			if(in != null) in.close();
		}
	}
}