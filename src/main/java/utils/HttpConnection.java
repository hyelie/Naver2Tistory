package utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class HttpConnection {
	/**
	 * @return response of HttpURLConnectoin 'con' to HttpConnectionVO.
	 * @throws Exception to caller while running this function.
	 */
	private static HttpConnectionVO getResponse(HttpURLConnection con) throws Exception{
		// response code에 따라 input stream 지정
		Integer responseCode = con.getResponseCode();
		InputStream responseStream;
		if(responseCode < HttpURLConnection.HTTP_BAD_REQUEST){
			responseStream = con.getInputStream();
		}
		else{
			responseStream = con.getErrorStream();
		}			

		// 결과 출력
		BufferedReader in = new BufferedReader(new InputStreamReader(responseStream));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		return new HttpConnectionVO(responseCode, response.toString());
	}

	/**
	 * Connect to parameter 'targetUrl' with type 'type', paramter 'parameter'.
	 * 
	 * @param targetUrl - connecting url
	 * @param type - HTTP type. (GET, POST, ...)
	 * @param parameter - HTTP connection paramter
	 * @return HTTPConnectionVO
	 * @see HttpConnectionVO
	 * @see HttpConnection#getResponse(HttpURLConnection)
	 * @throws Exception when connection timeout, or refused, otherwise.
	 */
	public static HttpConnectionVO request(String targetUrl, String type, String parameter) throws Exception {
		HttpURLConnection con = null;
		try {
			URL url = new URL(targetUrl);
			con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod(type);
			if (type != "GET") {
				con.setDoOutput(true);
				con.getOutputStream().write(parameter.getBytes());		
			}

			return HttpConnection.getResponse(con);
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
		}
	}

	/**
	 * Connect to parameter 'targetURL' with type 'type', paramter 'parameter' with file in parameter 'filePath'.
	 * 
	 * @param targetURL - connecting url
	 * @param type - HTTP type. (GET, POST, ...)
	 * @param parameter - HTTP connection paramter
	 * @param fileByte - file byte send to targetURL.
	 * @return HTTPConnectionVO
	 * @see HttpConnectionVO
	 * @see HttpConnection#getResponse(HttpURLConnection)
	 * @throws Exception when connection timeout, or refused, otherwise.
	 */
	public static HttpConnectionVO request(String targetUrl, String type, String parameter, byte[] fileByte) throws Exception {
		HttpURLConnection con = null;
		try {
			URL url = new URL(targetUrl + "?" + parameter);
			con = (HttpURLConnection) url.openConnection();

			con.setRequestMethod(type);

			con.setDoInput(true);
			con.setDoOutput(true);

			// input file
			String BOUNDARY = "ibubyftcy000999------nitn67";
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Content-Type", "multipart/form-data;boundary="+BOUNDARY);
			DataOutputStream dos = new DataOutputStream(con.getOutputStream());
			
			dos.writeBytes("--" + BOUNDARY + "\r\n");
			dos.writeBytes("Content-Disposition: form-data;name=\"uploadedfile\";filename=\"1.jpg\"\r\n");
			dos.writeBytes("\r\n");
			dos.write(fileByte);
			dos.writeBytes("\r\n");
			dos.writeBytes("--" + BOUNDARY + "--\r\n");
			dos.flush();

			return HttpConnection.getResponse(con);
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
		}
	}
}