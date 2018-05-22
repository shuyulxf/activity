package indi.shuyu.common.validator;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import indi.shuyu.common.base.StringUtils;
import indi.shuyu.controller.RuleManageController;

public class Validator {
	
	private final static Logger logger = LoggerFactory.getLogger(RuleManageController.class);
	
	HashMap<String, Integer> map;
	
	public Validator() {
		
		map = new HashMap<String, Integer>();
		String[] validRuleNames = {"required", "text", "maxlength", "minlength", "weight", "number","radio","enText", "phoneNumber", "province"};
		
		int l = validRuleNames.length;
		for (int i = 0; i < l; i++) {
			map.put(validRuleNames[i], i);
		}
	}
	public boolean match(String rgx, String str) {
		
		Pattern pattern = Pattern.compile(rgx);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	private int getType(String typeName) {
		return map.get(typeName);
	}
	
	
	private Boolean isNull(String value) {
		return value == null || value.length() == 0;
	}
	
	private Boolean isRequired(String value) {
		return value != null && value.length() != 0;
	}
	
	private Boolean isText(String value) {
		return isNull(value) || match("^(\\w|\\-|[\\u4e00-\\u9fa5])+$", value.trim());
	}
	
	private Boolean isEnText(String value) {
		return isNull(value) || match("^(\\w|\\_|\\d)+$", value.trim());
	}
	
	private Boolean isWeight(String value) {
		return isNull(value) || match("^\\d+$", value);
	}
	
	private Boolean isNumber(String value) {
		return isNull(value) || match("^\\d+$", value);
	}
	
	private Boolean isMinLength(String value, int min) {
		return isNull(value) || value.length() >= min;
	}
	
	private Boolean isMaxLength(String value, int max) {
		return isNull(value) || value.length() <= max;
	}
	
	private Boolean isRadio(String value) {
		return isNull(value) || match("^1|0$", value);
	}
	
	private Boolean isPhoneNumber(String value) {
		return isNull(value) ||  match("^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$", value);
	}
	
	private Boolean isProvince(String value) {
		String valids = "北京,天津,河北,山西,内蒙古,辽宁,吉林,黑龙江,上海,江苏,浙江,安徽,福建,江西,山东,河南,湖北,湖南,广东,广西,海南,重庆,四川,贵州,云南,西藏,陕西,甘肃,青海,宁夏,新疆,台湾,香港,澳门";
		return isNull(value) || valids.contains(value);
	}
	
	
	private ValidItemPairResult itemValid(ValidatorItemPair pair) {
		
		String value = pair.value;
		String formName = pair.formName;
		List<RuleItemPair> rules = pair.rules;
		ValidItemPairResult rlt = new ValidItemPairResult();
		
		try {
		    int size = rules.size();
		    
		    for (int i = 0; i < size; i++) {
		    	
		    	Boolean valid = false;
		    	RuleItemPair rule = rules.get(i);
		    	int type = getType(rule.type);
			    switch(type) {
			    
			    	case 0: 
			    		valid = isRequired(value);
			    		if (!valid) rlt.setMsg(formName + "字段为必填字段");
			    		break;
			    	case 1:
			    		valid = isText(value);
			    		if (!valid) rlt.setMsg(formName + "字段必须为单词字符");
			    		break;
			    	case 2:
			    		int max = rule.getMaxlength();
			    		valid = isMaxLength(value, max);
			    		if (!valid) rlt.setMsg(formName + "字段长度不得超过" + max);
			    		break;
			    	case 3:
			    		int min = rule.getMinlength();
			    		valid = isMinLength(value, min);
			    		if (!valid) rlt.setMsg(formName + "字段长度不得少于" + min);
			    		break;
			    	case 4:
			    		valid = isWeight(value);
			    		if (!valid) rlt.setMsg(formName + "字段不符合权重的值范围");
			    		break;
			    	case 5:
			    		valid = isNumber(value);
			    		if (!valid) rlt.setMsg(formName + "不是符合整数的格式");
			    		break;
			    	case 6:
			    		valid = isRadio(value);
			    		if (!valid) rlt.setMsg(formName + "不是符合开关数据的格式");
			    		break;
			    	case 7:
			    		valid = isEnText(value);
			    		if (!valid) rlt.setMsg(formName + "不是符合英文文本的格式");
			    		break;
			    	case 8:
			    		valid = isPhoneNumber(value);
			    		if (!valid) rlt.setMsg(formName + "不符合手机号码的格式");
			    		break;
			    	case 9:
			    		valid = isProvince(value);
			    		if (!valid) rlt.setMsg(formName + "不符合省份的格式");
			    		break;
			    	default:
			    		rlt.setMsg(rule.type + "校验函数未定义");
			    		break;
			    }
			    
			    if (!valid) {
			    	
			    	rlt.setValid(false);
			    	return rlt;
			    }
			 }
		    
		    rlt.setValid(true);
		    rlt.setMsg(formName + "字段验证成功");
	    } catch(Exception e) {
	    	logger.error(e.getMessage());
	    }
	    
		return rlt;
	}
	
	public ValidItemPairResult validate(List<ValidatorItemPair> itemPairs) {
		
		int size = itemPairs.size();
		for (int i = 0; i < size; i++) {
			ValidatorItemPair pair = itemPairs.get(i);
			ValidItemPairResult pairValidRlt = itemValid(pair);
			if (!pairValidRlt.getValid()) return pairValidRlt;
		}
		
		ValidItemPairResult rlt = new ValidItemPairResult();
		rlt.setValid(true);
		rlt.setMsg("字段验证成功");
		
		return rlt;
	}
}
