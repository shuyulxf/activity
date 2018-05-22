package indi.shuyu.common.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;


import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import indi.shuyu.action.ActivityManageAction;
import indi.shuyu.system.logger.LogDash;

public class JavaWebServiceHelper {
	
	private final static Logger logger = LoggerFactory.getLogger(ActivityManageAction.class);
	static LogDash ld;
	
	private static Throwable findTimeoutCause(Throwable fault) {
		ld = (LogDash)SpringContextHelper.getBean("logDash");
        Throwable ex = fault;
		try {
			while (ex != null) {
			    ex = ex.getCause();
			    if (ex instanceof SocketTimeoutException) {
			    	ld.addLog("接口调用超时" + ex);
			    	Thread.sleep(60000);
			        break;
			    }
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        return ex;
    }
	
	//执行http的URL调用
	public  String InvokeHttpService(String url)
			throws Exception {
		
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		
		StringBuffer strBuf = new StringBuffer();
		String utf8Str = "";
		BufferedReader reader = null;
		InputStreamReader isr = null;
		InputStream is = null;
		try {
			ld.addLog("執行Http請求其IP为" + URLDecoder.decode(url, "UTF-8"));
		
			utf8Str = java.net.URLDecoder.decode(url, "UTF-8");
			URL Url = new URL(url);
			URLConnection conn = Url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.connect();
			
			// io流读取接口内容
			is = conn.getInputStream();
			isr = new InputStreamReader(is, "UTF-8");
			reader = new BufferedReader(isr);
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line);
			}
		} catch (IOException e) {
			ld.addLog("执行HTTP Get请求" + utf8Str + "时，发生异常！");
		}
		ld.addLog("InvokeWebService(String url)"+strBuf.toString());

		return strBuf.toString();
	}
//执行http基础上webService的调用	
	public static  String InvokeWebService(String url, String nameSpace,
			String methodName, Object[] paras) {	
		
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		
		RPCServiceClient serviceClient = null;		
		try {
			// 客户端控件
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTimeOutInMilliSeconds(600000L);
			EndpointReference targetEPR = new EndpointReference(url);
			options.setTo(targetEPR);
			// 命名空间
			QName qName = new QName(nameSpace, methodName);
			// 返回值的类型，基本类型为
			Class<?>[] returnType = new Class[] { String.class };
			Object[] result = serviceClient.invokeBlocking(qName, paras,returnType);
			
			if (result != null && result.length > 0 && result[0] != null){
				return result[0].toString();
			}
			return "";
			//return result;
		} catch (Exception e) {
			//Log myLog = Log.getLoger();
			Throwable ex = findTimeoutCause(e);
            //GlobalValue.myLog.info(ex instanceof SocketTimeoutException ? "接口调用超时" + ex : "接口调用异常" + e);
            if(ex instanceof SocketTimeoutException) {
            	ld.addLog("webService调用超时：" + ex);
            } else {
            	ld.addLog("webService调用异常：" + e.toString());
            }
            return "";
		} finally {
			try {
				serviceClient.cleanupTransport();
				//serviceClient.cleanup();
			} catch (Exception e) {
				ld.addLog(e.toString());
			}
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String[] arg={"xjdxgswx","abcd1234","18064097728"};
//		String res211=JavaWebServiceHelper.InvokeWebService(
//				"http://xiaozhi.189.cn:8082/SingleLoginInInterface/SingleLoginWSPort?wsdl",
//				"http://SingleLoginAtIM/", "userChecker",arg);
//		System.out.println(res211);
		
		String[] arg = {"132615277220", "a181d7ee-c652-4d0c-910d-3fdfea274b7c", "sdfsd"};
		String res211=JavaWebServiceHelper.InvokeWebService(
				"http://xiaozhi.189.cn:8082/SingleLoginInInterface/SingleLoginWSPort?wsdl",
				"http://SingleLoginAtIM/", "tokenCheck", arg);
		System.out.println(res211);
	}

}

