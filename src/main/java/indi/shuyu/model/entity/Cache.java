package indi.shuyu.model.entity;



import com.alibaba.fastjson.JSONObject;

public class Cache {
	
	private String sessionId;
	private String userName;
	private long activityId;
    private int status = 0;
    private JSONObject infos;
    private long t;

    
    public void setSessionId(String sid) {
   		this.sessionId = sid;
    }
    public String getSessionId() {
    	return sessionId;
    }
    
    public String getUserName() {
		return userName;
	}
	
	public void setUserName(String u) {
		this.userName = u;
	}
	
	public long getActivityId() {
		return activityId;
	}
	
	public void setActivityId(long aid) {
		this.activityId = aid;
	}
    
	public int getStatus() {
		return status;
	}
	public void setStatus(int s) {
		this.status = s;
	}
	
	// status, quesSeq, activityIndex
	public JSONObject getInfos() {
		return infos;
	}
	public void setInfos(JSONObject infos) {
		this.infos = infos;
	}
	
	public void setT(long t) {
		this.t = t;
	}
	
	public long getT() {
		return t;
	}
}
