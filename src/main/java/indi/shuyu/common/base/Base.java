package indi.shuyu.common.base;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;


public class Base {
	 
	public static String toDate() {
		
		Date d = new Date();
		SimpleDateFormat sd=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String ss = sd.format(d);

		return "to_date('" + ss + "', 'yyyy-mm-dd hh24:mi:ss')";
	}
	
	public static Timestamp toTimeStamp() {
		
		Date date = new Date();       
		Timestamp nousedate = new Timestamp(date.getTime());
		return nousedate;
	}
	
	public static Timestamp strToTimeStamp(String d) {
		
		Timestamp t = Timestamp.valueOf(d);  
		return t;
	}
	
	public static String getLoginUserOrRole(HttpSession session) {
		
		Object userNameObj = session.getAttribute("UserName"),
			   userNameRoleObj = session.getAttribute("UserNameRole");
		
		String userName = "",
			   role = "_activity_admin";
		
		if (userNameRoleObj != null) {
			String userNameRole = userNameRoleObj.toString();
			if(userNameRole.indexOf(role) != -1) return null;
		}
		if (userNameObj != null) {
			userName = userNameObj.toString();
			return userName;
		}
		
		return "nologin";
	}
	
	public static String getLoginUserName(HttpSession session) {

		Object userNameObj = session.getAttribute("UserName");
		String userName = "";
		if (userNameObj != null) userName = userNameObj.toString();
		
		return userName;
	}
	
	public static boolean logoutByUserName(HttpSession session, String userName) {

		Object userNameObj = session.getAttribute("UserName");

		if (userNameObj != null) {
			session.removeAttribute(userName);
		    session.invalidate();
			return true;
		}
		
		return false;
	}

}
