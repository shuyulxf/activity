package indi.shuyu.common.validator;

public class RuleItemPair {
	String type;
	int mexlength;
	int minlength;
	
	private Boolean isNotNull(String v) {
		if (v != null && v.length() > 0) return true;
		return false;
	}
	public String getType() {
		return type;
	}
	
	public void setType(String t) {
		this.type = t;
	}
	
	
	public int getMinlength() {
		return minlength;
	}
	
	public void setMinlength(String ml) {
		if (isNotNull(ml))	this.minlength = Integer.parseInt(ml);
	}
	
	public int getMaxlength() {
		return mexlength;
	}
	
	public void setMaxlength(String max) {
		if (isNotNull(max))	this.mexlength = Integer.parseInt(max);
	}
}
