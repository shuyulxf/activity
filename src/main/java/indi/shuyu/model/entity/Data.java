package indi.shuyu.model.entity;

import java.sql.Timestamp;

public class Data {
	 private String activityName;
	 private Timestamp createTime;
	 private Timestamp earnTime;
	 private String awardType;
	 private int numberForAward;
	 private int isSeriesAwardForJoinActivity;
	 private int count;
	 private String phoneNumber;
	 private String province;
	 private String city;
	 private String applyCode;
     private long joinUserNum = 0;
     private long disJoinUserNum = 0;
     private long disAwardUserNum = 0;
     private long awardUserNum = 0;
     private String awardSendDetail;
     
	 
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
	
	 
	 public void setCount(int c) {
		 this.count = c;
	 }
	 public int getCount() {
		 return count;
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


    public long getJoinUserNum() {
		return joinUserNum;
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
	public void setJoinUserNum(long jun) {
		this.joinUserNum = jun;
	}
	 
    public long getDisJoinUserNum() {
		return disJoinUserNum;
	}
	
	public void setDisJoinUserNum(long djun) {
		this.disJoinUserNum = djun;
	}
	 
	public long getAwardUserNum() {
		return awardUserNum;
	}
	
	public void setAwardUserNum(long aun) {
		this.awardUserNum = aun;
	}
	
	public long getDisAwardUserNum() {
		return disAwardUserNum;
	}
	
	public void setDisAwardUserNum(long aun) {
		this.disAwardUserNum = aun;
	}
	
	public String getAwardSendDetail() {
		return awardSendDetail;
	}
	
	public void setAwardSendDetail(String str) {
		this.awardSendDetail = str;
	}
}
