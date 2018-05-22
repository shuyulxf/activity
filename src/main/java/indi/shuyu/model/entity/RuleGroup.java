package indi.shuyu.model.entity;

import java.sql.Timestamp;

public class RuleGroup extends BaseEntity{
	long ruleGroupId;
	String ruleGroupNameCN;
	String ruleGroupNameEN;
	int ruleGroupWeight;
	int isExclusive;
	int isCommonGroup;
	String createUser;
	Timestamp createTime;
	
	public long getRuleGroupId(){
		return ruleGroupId;
	}
	
	public void setRuleGroupId(long id){
		this.ruleGroupId = id;
	}
	
	public String getRuleGroupNameCN(){
		return ruleGroupNameCN;
	}
	
	public void setRuleGroupNameCN(String name){
		this.ruleGroupNameCN = name;
	}
	
	public String getRuleGroupNameEN(){
		return ruleGroupNameEN;
	}
	
	public void setRuleGroupNameEN(String name){
		this.ruleGroupNameEN = name;
	}
	
	public int getRuleGroupWeight(){
		return ruleGroupWeight;
	}
	
	public void setRuleGroupWeight(int weight){
		this.ruleGroupWeight = weight;
	}
	
	public int getIsCommonGroup(){
		return isCommonGroup;
	}
	
	public void setIsCommonGroup(int is){
		this.isCommonGroup = is;
	}
	public int getIsExclusive(){
		return isExclusive;
	}
	
	public void setIsExclusive(int is){
		this.isExclusive = is;
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
