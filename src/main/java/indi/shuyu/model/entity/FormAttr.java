package indi.shuyu.model.entity;

import java.sql.Timestamp;
import java.util.HashMap;

public class FormAttr  extends BaseEntity{
	long formAttrInfoId;
	String formAttrName;
	String formAttrNameLabel;
	String formAttrNameDefaultValue;
	String formAttrNameValidation;
	HashMap<String, String> formAttrNameValidationList;
	String formAttrNameType;
	String formAttrNameFillType;
	long ruleIdNoEx;
	String ruleIds;
	String exRuleGroupNameCN;
	long exRuleGroupId;
	int hasReplyForFormAttr;
	int orderForForm;
	String formAttrIntroduce;
	String formNameTypeAttrs;
	String createUser;
	Timestamp createTime;
	
	public void setFormAttrInfoId(long id) {
		this.formAttrInfoId = id;
	}
	public long getFormAttrInfoId() {
		return formAttrInfoId;
	}
	
	public void setFormAttrName(String s) {
		this.formAttrName = s;
	}
	public String getFormAttrName() {
		return formAttrName;
	}
	
	public void setFormAttrNameLabel(String s) {
		this.formAttrNameLabel = s;
	}
	public String getFormAttrNameLabel() {
		return formAttrNameLabel;
	}
	
	public void setFormAttrNameDefaultValue(String s) {
		this.formAttrNameDefaultValue = s;
	}
	public String getFormAttrNameDefaultValue() {
		return formAttrNameDefaultValue;
	}
	
	public void setFormAttrNameValidation(String s) {
		this.formAttrNameValidation = s;
	}
	public String getFormAttrNameValidation() {
		return formAttrNameValidation;
	}
	public void setFormAttrNameValidationList() {
		if (formAttrNameValidation != null && formAttrNameValidation.length() != 0) {
			String[] temp = formAttrNameValidation.split(" +");
			int size = temp.length;
			formAttrNameValidationList = new HashMap<String, String>();
			for (int i = 0; i < size; i++) {
				String t = temp[i];
				if (t.indexOf("=") != -1) formAttrNameValidationList.put(t.split("=")[0], t);
				else  formAttrNameValidationList.put(t, t);
			}
		}
		
	}
	public HashMap<String, String> getFormAttrNameValidationList() {
		return formAttrNameValidationList;
	}
	
	public void setFormAttrNameType(String s) {
		this.formAttrNameType = s;
	}
	public String getFormAttrNameType() {
		return formAttrNameType;
	}
	
	public void setFormAttrNameFillType(String s) {
		this.formAttrNameFillType = s;
	}
	public String getFormAttrNameFillType() {
		return formAttrNameFillType;
	}
	
	public void setRuleIdNoEx(long id) {
		this.ruleIdNoEx = id;
	}
	
	public long getRuleIdNoEx() {
		return ruleIdNoEx;
	}
	
	public void setRuleIds(String ids) {
		this.ruleIds = ids;
	}
	
	public String getRuleIds() {
		return ruleIds;
	}
	
	public void setExRuleGroupNameCN(String n) {
		this.exRuleGroupNameCN = n;
	}
	
	public int getOrderForForm() {
		return orderForForm;
	}
	
	public void setOrderForForm(int r) {
		this.orderForForm = r;
	}
	

	
	public String getFormAttrIntroduce() {
		return formAttrIntroduce;
	}
	
	public void setFormAttrIntroduce(String data) {
		this.formAttrIntroduce = data;
	}
	public String getFormNameTypeAttrs() {
		return formNameTypeAttrs;
	}
	
	public void setFormNameTypeAttrs(String data) {
		this.formNameTypeAttrs = data;
	}
	public String getExRuleGroupName() {
		return exRuleGroupNameCN;
	}
	
	public void setExRuleGroupId(long id) {
		this.exRuleGroupId = id;
	}
	
	public long getExRuleGroupId() {
		return exRuleGroupId;
	}
	
	public void setHasReplyForFormAttr(int id) {
		this.hasReplyForFormAttr = id;
	}
	public int getHasReplyForFormAttr() {
		return hasReplyForFormAttr;
	}
	
	public void setCreateUser(String u) {
		this.createUser = u;
	}
	public String getCreateUser() {
		return createUser;
	}
	
	public void setCreateTime(Timestamp t) {
		this.createTime = t;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
}
