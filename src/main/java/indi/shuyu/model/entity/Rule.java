package indi.shuyu.model.entity;

import java.sql.Timestamp;

public class Rule extends BaseEntity{
	long ruleId;
	String ruleNameCN;
	String ruleNameEN;
	long ruleGroupId;
	long exRuleGroupId;
	String ruleGroupNameCN;
	String ruleGroupNameEN;
	String exRuleGroupNameCN;
	String exRuleGroupNameEN;
	int isCommonGroup;
	String rule;  
	String functions;
	int ruleWeight;
	int ruleGroupWeight;
	String createUser;
	Timestamp createTime;
	
	
	public long getRuleId(){
		return ruleId;
	}
	
	public void setRuleId(long id){
		this.ruleId = id;
	}
	
	public String getRuleNameCN(){
		return ruleNameCN;
	}
	
	public void setRuleNameCN(String name){
		this.ruleNameCN = name;
	}
	
	public String getRuleNameEN(){
		return ruleNameEN;
	}
	
	public void setRuleNameEN(String name){
		this.ruleNameEN = name;
	}
	
	public long getRuleGroupId(){
		return ruleGroupId;
	}
	
	public void setRuleGroupId(long id){
		this.ruleGroupId = id;
	}
	
	public long getExRuleGroupId(){
		return exRuleGroupId;
	}
	
	public void setExRuleGroupId(long id){
		this.exRuleGroupId = id;
	}
	
	public String getExRuleGroupNameCN(){
		return exRuleGroupNameCN;
	}
	
	public void setExRuleGroupNameCN(String n){
		this.exRuleGroupNameCN = n;
	}
	
	public String getRuleGroupNameCN(){
		return ruleGroupNameCN;
	}
	
	public void setRuleGroupNameCN(String n){
		this.ruleGroupNameCN = n;
	}
	
	public String getExRuleGroupNameEN(){
		return exRuleGroupNameEN;
	}
	
	public void setExRuleGroupNameEN(String n){
		this.exRuleGroupNameEN = n;
	}
	
	public String getRuleGroupNameEN(){
		return ruleGroupNameEN;
	}
	
	public void setRuleGroupNameEN(String n){
		this.ruleGroupNameEN = n;
	}
	
	public int getIsCommonGroup(){
		return isCommonGroup;
	}
	
	public void setIsCommonGroup(int icp){
		this.isCommonGroup = icp;
	}
	
	public String getRule(){
		return rule;
	}
	
	public void setRule(String r){
		this.rule = r;
	}
	
	public String getFunctions(){
		return functions;
	}
	
	public void setFunctions(String f){
		this.functions = f;
	}
	
	public int getRuleWeight(){
		return ruleWeight;
	}
	
	public void setRuleWeight(int w){
		this.ruleWeight = w;
	}
	
	public int getRuleGroupWeight(){
		return ruleGroupWeight;
	}
	
	public void setRuleGroupWeight(int w){
		this.ruleGroupWeight = w;
	}
	
	
	
	public String getCreateUser(){
		return createUser;
	}
	
	public void setCreateUser(String cu){
		this.createUser = cu;
	}
	
	public Timestamp getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Timestamp t){
		this.createTime = t;
	}
}
