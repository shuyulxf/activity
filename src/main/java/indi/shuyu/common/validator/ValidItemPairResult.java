package indi.shuyu.common.validator;

public class ValidItemPairResult {
	Boolean valid;
	String msg;
	
	public Boolean getValid(){
		return valid;
	}
	
	public void setValid(Boolean v) {
		this.valid = v;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String m) {
		this.msg = m;
	}
}
