package indi.shuyu.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class SpringHttpHelper {
	
	public boolean isAjax(HttpServletRequest request) {
		
		if (request.getHeader("accept") != null && 
			request.getHeader("accept").indexOf("application/jsfon") != -1 ||
		    request.getHeader("X-Requested-With") != null && 
		    request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") != -1)
			return true;
		
		return false;
	}
	
	// 发送http get请求
	public static String sendMessageGetHttp(String url) {
		String responseMsg = "";
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("utf-8");
		GetMethod getMethod = new GetMethod(url);
		try {
			httpClient.executeMethod(getMethod);// 200
			//responseMsg = postMethod.getResponseBodyAsString().trim();
			InputStream is = getMethod.getResponseBodyAsStream();

		    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		    
	        String line;
	        StringBuffer buffer = new StringBuffer();
	        while ((line = reader.readLine()) != null) {
	            buffer.append(line);
	        }
	        reader.close();
	        return buffer.toString();
	        
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return responseMsg;
	}
	
	// 发送http请求
	public static String sendMessagePostHttp(String url, String json) {
		String responseMsg = "";
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("utf-8");
		PostMethod postMethod = new PostMethod(url);
		postMethod.addParameter("json", json);
		try {
			httpClient.executeMethod(postMethod);// 200
			//responseMsg = postMethod.getResponseBodyAsString().trim();
			InputStream is = postMethod.getResponseBodyAsStream();

		    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		    
	        String line;
	        StringBuffer buffer = new StringBuffer();
	        while ((line = reader.readLine()) != null) {
	            buffer.append(line);
	        }
	        reader.close();
	        return buffer.toString();
	        
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return responseMsg;
	}
	
	
}
