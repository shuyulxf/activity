package indi.shuyu.actionresult.entity;

public class ActivityExResult {

	private int status;// 0-成功 1-失败
	private String activityName;
	private String msg;
	private String info;
	
	public int getStatus() {
		return status;
	} 
	
	public void setStatus(int t) {
		this.status = t;
	}
	
	public String getActivityName() {
		return activityName; 
	} 
	
	public void setActivityName(String an) {
		this.activityName = an;
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
}
