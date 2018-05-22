package indi.shuyu.model.entity;


import java.sql.Timestamp;


public class User {
	 private long recordId;
	 private long awardInfoId;
	 private int  isFinished;
	 private String activityName;
	 private String replyInfo;
	 private Timestamp createTime;
	 private Timestamp earnTime;
	 private String awardType;
	 private String replyInfoForAward;
	 private double rate;
	 private int numberForAward;
	 private int isSeriesAwardForJoinActivity;
	 private int count;
	 private String replyInfForAward;
	 private long searchId;
	 private String query;
	 private String phoneNumber;
	 private String province;
	 private String city;
	 private String applyCode;
	 private String token;
     private int status;
     private String sessionId;
     
     public void setSessionId(String sid) {
    		this.sessionId = sid;
     }
     public String getSessionId() {
     	return sessionId;
     }
	
	 public void setRecordId(long id) {
		 this.recordId = id;
	 }
	 public long getRecordId() {
		 return recordId;
	 }
	 
	 public void setAwardInfoId(long id) {
		 this.awardInfoId = id;
	 }
	 public long getAwardInfoId() {
		 return awardInfoId;
	 }
	 
	 public void setIsFinished(int i) {
		 this.isFinished = i;
	 }
	 public int getIsFinished() {
		 return isFinished;
	 }
	 
	 public void setReplyInfo(String r) {
		 this.replyInfo = r;
	 }
	 public String getReplyInfo() {
		 return replyInfo;
	 }
	 
	 public void setCreateTime(Timestamp t) {
		 this.createTime = t;
	 }
	 public Timestamp getCreateTime() {
		 return createTime;
	 }
	 
	 public void setEarnTime(Timestamp e) {
		 this.earnTime = e;
	 }
	 public Timestamp getEarnTime() {
		 return earnTime;
	 }
	 public void setAwardType(String at) {
		 this.awardType = at;
	 }
	 public String getAwardType() {
		 return awardType;
	 }
	 public void setNumberForAward(int at) {
		 this.numberForAward = at;
	 }
	 public int getNumberForAward() {
		 return numberForAward;
	 }
	 public void setIsSeriesAwardForJoinActivity(int is) {
		 this.isSeriesAwardForJoinActivity = is;
	 }
	 public int getIsSeriesAwardForJoinActivity() {
		 return isSeriesAwardForJoinActivity;
	 }
	 public void setActivityName(String an) {
		 this.activityName = an;
	 }
	 public String getActivityName() {
		 return activityName;
	 }
	 public void setReplyInfoForAward(String r) {
		 this.replyInfoForAward = r;
	 }
	 public String getReplyInfoForAward() {
		 return replyInfoForAward;
	 }
	 
	 public void setRate(double r) {
		 this.rate = r;
	 }
	 
	 public double getRate() {
		 return rate;
	 }
	 
	 public void setCount(int c) {
		 this.count = c;
	 }
	 public int getCount() {
		 return count;
	 }
	 
	 public void setReplyInfForAward(String r) {
		 this.replyInfForAward = r;
	 }
	 public String getReplyInfForAward() {
		 return replyInfForAward;
	 }
	 public long getSearchId() {
	    	return searchId;
     }
    
     public void setSearchId(long id) {
     	this.searchId = id;
     }
    
	 public String getQuery() {
	 	return query;
	 }
	
	 public void setQuery(String q) {
		this.query = q;
	 }
	
	 public String getPhoneNumber() {
	 	return phoneNumber;
	 }
	
	 public void setPhoneNumber(String p) {
		this.phoneNumber = p;
	 }
	
	 public String getProvince() {
	 	return province;
	 }
	
	 public void setProvince(String p) {
		this.province = p;
	 }
	
	 public String getCity() {
		return city;
	 }
	
	 public void setCity(String c) {
		this.city = c;
	 }
	
	 public String getApplyCode() {
		return applyCode;
	 }
	
	 public void setApplyCode(String a) {
	 	this.applyCode = a;
	 }
	
	 public String getToken() {
		return token;
	 }
	
	 public void setToken(String t) {
		this.token = t;
	 }
	
	 public int getStatus() {
		return status;
	 }
	
	 public void setStatus(int s) {
		this.status = s;
	 }
	
 }