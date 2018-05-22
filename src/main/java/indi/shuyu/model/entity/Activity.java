package indi.shuyu.model.entity;

import java.sql.Timestamp;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class Activity extends BaseEntity{
	long activityId;
	String activityName;
	String activityType;
	String activityProvince;
	String activityApplyCode;
	Timestamp activityStartTime;
	Timestamp activityEndTime;
	String activityCommonSetting;
	String activityGeneralSetting;
	String activityAwardSetting;
	String activityReplySetting;
	JSONObject ACS = null;
	JSONObject AGS = null;
	JSONObject AAS = null;
	JSONObject ARS = null;
	String ruleIds;
	String createUser;
	Timestamp createTime;
	
	public long getActivityId() {
		return activityId;
	}
	
	public void setActivityId(long id) {
		this.activityId = id;
	}
	
	public String getActivityName() {
		return activityName;
	}
	
	public void setActivityName(String n) {
		this.activityName = n;
	}
	
	public String getActivityType() {
		return activityType;
	}
	
	public void setActivityType(String t) {
		this.activityType = t;
	}
	
	public String getActivityProvince() {
		return activityProvince;
	}
	
	public void setActivityProvince(String p) {
		this.activityProvince = p;
	}
	
	public String getActivityApplyCode() {
		return activityApplyCode;
	}
	
	public void setActivityApplyCode(String a) {
		this.activityApplyCode = a;
	}
	
	public Timestamp getActivityStartTime() {
		return activityStartTime;
	}
	
	public void setActivityStartTime(Timestamp t) {
		this.activityStartTime = t;
	}
	
	public void setActivityStartTime(String t) {
		this.activityStartTime = Timestamp.valueOf(t);     
	}
	
	public Timestamp getActivityEndTime() {
		return activityEndTime;
	}
	
	public void setActivityEndTime(Timestamp t) {
		this.activityEndTime = t;
	}
	
	public void setActivityEndTime(String t) {
		this.activityEndTime = Timestamp.valueOf(t);     
	}
	
	public String getActivityCommonSetting() {
		return activityCommonSetting;
	}
	
	public void setActivityCommonSetting(String t) {
		if (t != null && t.length() > 0) ACS = JSON.parseObject(t);
		else ACS = new JSONObject();
		
		this.activityCommonSetting = t;
	}
	
	public String getActivityGeneralSetting() {
		return activityGeneralSetting;
	}
	
	public void setActivityGeneralSetting(String t) {
		
		if (t != null && t.length() > 0) AGS = JSON.parseObject(t);
		else AGS = new JSONObject();
		
		this.activityGeneralSetting = t;
	}
	
	public String getActivityAwardSetting() {
		return activityAwardSetting;
	}
	
	public void setActivityAwardSetting(String s) {
		
		if (s != null && s.length() > 0) AAS = JSON.parseObject(s);
		else AAS = new JSONObject();
		this.activityAwardSetting = s;
	}
	
	public String getActivityReplySetting() {
		return activityReplySetting;
	}
	
	public void setActivityReplySetting(String s) {
		
		if (s != null && s.length() > 0) ARS = JSON.parseObject(s);
		else ARS = new JSONObject();
		this.activityReplySetting = s;
	}
	
	public String getRuleIds() {
		return ruleIds;
	}
	
	public void setRuleIds(String ids) {
		this.ruleIds = ids;
	}
	
	
	public String getCreateUser() {
		return createUser;
	}
	
	
	public void setCreateUser(String user) {
		this.createUser = user;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Timestamp t) {
		this.createTime = t;
	}
	
	public JSONObject getACS() {
		return ACS;
	}
	public JSONObject getAGS() {
		return AGS;
	}
	public JSONObject getAAS() {
		return AAS;
	}
	public JSONObject getARS() {
		return ARS;
	}
}
