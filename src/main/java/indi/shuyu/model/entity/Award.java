package indi.shuyu.model.entity;

import java.sql.Timestamp;

public class Award {
	Long awardId;
	Long prizeId;
	Long activityId;
	int awardNum;
	int numStart;
	int numEnd;
	int distance;
	String prizeName;
	Timestamp createTime;
	
	public void setAwardId(Long id) {
		this.awardId = id;
	}
	public Long getAwardId() {
		return awardId;
	}
	
	public void setPrizeId(Long id) {
		this.prizeId = id;
	}
	public Long getPrizeId() {
		return prizeId;
	}
	
	public void setPrizeName(String n) {
		this.prizeName = n;
	}
	public String getPrizeName() {
		return prizeName;
	}
	
	public void setActivityId(Long id) {
		this.activityId = id;
	}
	public Long getActivityId() {
		return activityId;
	}
	
	public void setAwardNum(int n) {
		this.awardNum = n;
	}
	public int getAwardNum() {
		return awardNum;
	}
	
	public void setNumStart(int s) {
		this.numStart = s;
	}
	public int getNumStart() {
		return numStart;
	}
	
	public void setNumEnd(int e) {
		this.numEnd = e;
	}
	public int getNumEnd() {
		return numEnd;
	}
	
	public void setDistance(int d) {
		this.distance = d;
	}
	public int getDistance() {
		return distance;
	}
	
	public void setCreateTime(Timestamp t) {
		this.createTime = t;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
}
