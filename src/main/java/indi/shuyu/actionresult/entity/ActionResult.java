package indi.shuyu.actionresult.entity;

import indi.shuyu.common.base.JacksonUtils;

public class ActionResult {
	int status;
	String code;
	String msg;
	String data;
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int s) {
		this.status = s;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String m) {
		this.msg = m;
	}
	
	public String geCode() {
		return code;
	}
	
	public void setCode(String c) {
		this.code = c;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String d) {
		this.data = d;
	}
	
	public void setStatusFail() {
		this.status = 1;
	}
	
	public void setStatusSuccess() {
		this.status = 0;
	}
	
	public void setParamValidError() {
		setStatusFail();
		setCode("3001");
	}
	
	public String toString() {
		return JacksonUtils.toJSONString(this);
	}
}
