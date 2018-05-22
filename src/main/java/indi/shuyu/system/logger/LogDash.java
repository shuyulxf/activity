package indi.shuyu.system.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import indi.shuyu.actionresult.entity.ActivityExResult;

public class LogDash {
	
	private List<String> logs;
	
	public LogDash() {
		logs = new ArrayList<String>();
	}
	
	public void addLog(String log) {
		logs.add(log);
	}
	
	public void addLog(Exception e) {
		 StringWriter sw = new StringWriter();   
         PrintWriter pw = new PrintWriter(sw, true);   
         e.printStackTrace(pw);   
         pw.flush();   
         sw.flush();   
         logs.add(sw.toString());
	}
	
	public void addLog(ActivityExResult aer) {
		logs.add(JSON.toJSONString(aer));
	}
	
	
	public String toString() {
		return String.join("\n", logs);
	}
}
