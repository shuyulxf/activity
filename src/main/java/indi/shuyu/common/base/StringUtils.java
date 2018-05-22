package indi.shuyu.common.base;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	public static String toString(long[] arrs, String deli) {
		
		StringBuffer sb = new StringBuffer();
		int len = arrs.length;
		sb.append(String.valueOf(arrs[0]));
		
		for (int i = 1; i < len; i++) {
			sb.append(deli + String.valueOf(arrs[i]));
		}
		
		return new String(sb);
	}
	
	public static String toString(String[] arrs, String deli) {
		
		StringBuffer sb = new StringBuffer();
		int len = arrs.length;
		sb.append(arrs[0]);
		
		for (int i = 1; i < len; i++) {
			sb.append(deli + arrs[i]);
		}
		
		return new String(sb);
	}
	
	public static String upperFirstChar(String str) {
		
		if (str == null || str.length() < 1) return "";
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	public boolean match(String rgx, String str) {
		
		Pattern pattern = Pattern.compile(rgx);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	public String firstCharToUpperCase(String str) {  
		
	    char[] ch = str.toCharArray();  
	    if (ch[0] >= 'a' && ch[0] <= 'z') {  
	        ch[0] = (char) (ch[0] - 32);  
	    }  
	    
	    return new String(ch);  
	} 
}
