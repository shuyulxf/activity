package indi.shuyu.model.entity;

import java.sql.Timestamp;

public class Prize extends BaseEntity{
	long prizeId;
	String prizeName;
	String createUser;
	Timestamp createTime;
	
	public long getPrizeId(){
		return prizeId;
	}
	
	public void setPrizeId(long id){
		this.prizeId = id;
	}
	
	public String getPrizeName(){
		return prizeName;
	}
	
	public void setPrizeName(String name){
		this.prizeName = name;
	}
	
	public String getCreateUser(){
		return createUser;
	}
	
	public void setCreateUser(String u){
		this.createUser = u;
	}
	
	public Timestamp getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Timestamp d){
		this.createTime = d;
	}
}
