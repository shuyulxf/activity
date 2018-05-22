package indi.shuyu.actionresult.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RuleExResult {
	private int status; //活动参与者执行规则的返回状态
	private String userName; //活动参与者用户名
	private String msg; //活动管理者预先设置的回复语, 给用户
	private String info; //回复语, 给开发者
	private List<ActivityExResult> result;
	
	public int getStatus() {
		return status;
	} 
	
	public void setStatus(int t) {
		this.status = t;
	}
	
	public String getUserName() {
		return userName;
	} 
	
	public void setUserName(String u) {
		this.userName = u;
	}
	
	public String getMsg() {
		return msg;
	} 
	
	public void setMsg(String m) {
		this.msg = m;
	}
	
	public String getInfo() {
		return info;
	} 
	
	public void setInfo(String i) {
		this.info = i;
	}
	
	public List<ActivityExResult> getResult() {
		return result;
	} 
	
	public void setResult(ActivityExResult aer) {
		if (result == null) result = new ArrayList<ActivityExResult>();
		result.add(aer);
	}
	
	
}
