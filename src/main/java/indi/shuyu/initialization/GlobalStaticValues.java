package indi.shuyu.initialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.common.validator.ValidItemPairResult;
import indi.shuyu.common.validator.Validator;
import indi.shuyu.common.validator.ValidatorItemPair;
import indi.shuyu.controller.RuleManageController;
import indi.shuyu.core.rules.UpdateActivityRuleDRLFile;
import indi.shuyu.model.dao.ActivityAttrDao;
import indi.shuyu.model.dao.RuleDao;
import indi.shuyu.model.dao.RuleGroupDao;
import indi.shuyu.model.entity.Activity;
import indi.shuyu.model.entity.Cache;
import indi.shuyu.system.logger.LogDash;


@Component
public class GlobalStaticValues implements InitializingBean  {
	
	private HashMap<String, String> responseStatusAndErrorResourceMap = null;
	private HashMap<String, String> validateTypeSetting = null;
	private HashMap<String, String> adminUserTransfer = null;
	private Validator validator = null;
	private HashMap<String, String[]> validFormItems = null;
	private ConcurrentHashMap<Long, Activity> activityMap = new ConcurrentHashMap<Long, Activity>();
	private HashMap<String, String> thirdUrls= null;
	private HashMap<Integer, HashMap<String, String>> exportColumns = null;
	public ConcurrentHashMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>(); 

	
	private String resourceType = "";
	
	private List<String> groupNameList = new ArrayList<String>();
	
	private final static Logger logger = LoggerFactory.getLogger(RuleManageController.class);
	
	@Autowired  
	private ActivityAttrDao aad;
	
	@Autowired  
	private RuleDao rd;
	
	@Autowired  
	private RuleGroupDao rgd;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		/* http response error status to error pages*/
		ResponseStatusAndErrorResourceName rs = new ResponseStatusAndErrorResourceName();
		responseStatusAndErrorResourceMap = rs.getRSRN();
	
		ValidatorTypeSetting vts = new ValidatorTypeSetting();
		validateTypeSetting = vts.getVTS();

		validator = new Validator();
		
		ValidInfoForFormItem VIFFI = new ValidInfoForFormItem();
		validFormItems = VIFFI.getVFI();
		
		AdminUserTransferSetting AUTS = new AdminUserTransferSetting();
		adminUserTransfer = AUTS.getAUTS();
		
		ThirdReferredUrlsSetting TRUS = new ThirdReferredUrlsSetting();
		thirdUrls = TRUS.getTRUS();
		
		resourceType = "appmediasetting";
		
		setActivityMap();

		setGroupNameList();
		//initRuleDrlFiles();
		
		
		ExportColumn exc = new ExportColumn();
		exportColumns = exc.getColumns();
		
	}

	 
	public String getResponseStatusAndErrorResource(String key) {
		
		String defaultKey = "others";
		if (key == null || key.length() <= 0)  key = defaultKey;
		
		String path = responseStatusAndErrorResourceMap.get(key);
		if (path == null) path =  responseStatusAndErrorResourceMap.get(defaultKey);
		
		return path;
	}
	
	public String getResponseStatusAndErrorResource() {
		
		return responseStatusAndErrorResourceMap.get("others");
	}
	
	private Boolean isNotNull(String rule) {
		return rule != null && rule.length() > 0;
	}
    private List<String> getValidatorTypeSetting(String[] types) {
    	
    	LogDash ld = (LogDash)SpringContextHelper.getBean("logDash");
    	
		List<String> rules = new ArrayList<String>();
		int len = types.length;
		try {
			
			for (int i = 0; i < len; i++) {
				String type = types[i],
					   rule = validateTypeSetting.get(type);
				if (isNotNull(rule)) rules.add(rule);
				else  throw new Exception(type + "is not defined in file!");
			}
		} catch(Exception e) {
			ld.addLog(e);
		}
		return rules;
	}
    
    private List<String> getValidatorTypeSetting(String[] types, HashMap<String, String> variables) {
		
    	List<String> rules = new ArrayList<String>();
		int len = types.length;
		LogDash ld = (LogDash)SpringContextHelper.getBean("logDash");
    	
		try{
			
			for (int i = 0; i < len; i++) {
				String type = types[i];
				String rule = validateTypeSetting.get(type);
				
				for (Map.Entry<String, String> entry : variables.entrySet()) {  
					String key   = entry.getKey(),
						   value = entry.getValue();
				    if (key.equals(type)) {
				    	rule = rule.replace("$" + key, value);
				    }
				} 
				rules.add(rule);
			}
    	} catch(Exception e) {
    		ld.addLog(e);
    	}
    	
		return rules;
	}
	public ValidatorItemPair getValidatorItempair(String[] types, String paranName, String param) throws JsonParseException, JsonMappingException, IOException {
		
		List<String> rules = getValidatorTypeSetting(types);
		ValidatorItemPair pair = new ValidatorItemPair(paranName, param, rules);
		return pair;
	}
	
	public ValidatorItemPair getValidatorItempair(String[] types, String paranName, String param, HashMap<String, String> variables) throws JsonParseException, JsonMappingException, IOException {
		
		List<String> rules = getValidatorTypeSetting(types, variables);
		ValidatorItemPair pair = new ValidatorItemPair(paranName, param, rules);
		return pair;
	}
	
	
	public ValidItemPairResult validte(List<ValidatorItemPair> pairs) {
		
		return validator.validate(pairs);
	}
	
	public HashMap<String, String[]> getVIFFI() {
		return validFormItems;
	}
	
	public String getAUTbyUser(String user) {
		String aut = adminUserTransfer.get(user);
		
		return (aut == null || aut.length() == 0 ) ? user : aut;
	}
	
	public String getThirdUrl(String name) {
		if (name == null || name.length() == 0) return "";
		return thirdUrls.get(name);
	}
	
	
	public String getResourceType() {
		
		return resourceType;
	}
	
	public void setActivityMap() {
		List<Activity> list = aad.selectNotOffLineActivityList(Base.toTimeStamp());
		
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Activity a = list.get(i);
			activityMap.put(a.getActivityId(), a);
		}
	}
	
	public void updateActivityMap() {
		List<Activity> list = aad.selectNotOffLineActivityList(Base.toTimeStamp());
		
		int size = list.size();
		for (int i = 0; i < size; i++) {
			Activity a = list.get(i);
			activityMap.replace(a.getActivityId(), a);
		}
	}
	
	public void addActivityMap(Long key, Activity a) {
		activityMap.put(key, a);
	}
	
	public void updateActivityMap(Long key, Activity a) {
		activityMap.replace(key, a);
	}
	
	public void removeActivityMap(Long key) {
		activityMap.remove(key);
	}
	
	
	public ConcurrentHashMap<Long, Activity> getActivityMap() {
		
		return activityMap;
	}
	
	
	public void setGroupNameList() {
		this.groupNameList = rgd.selectGroupNameList();
	}
	
	public List<String> getGroupNameList() {
		return groupNameList;
	}
	
	private void initRuleDrlFiles() {
		UpdateActivityRuleDRLFile uardf = new UpdateActivityRuleDRLFile();
		uardf.initSystemAllDrlFiles(rd, activityMap);
	}
	
	public HashMap<String, String> getExportColumn(int type) {
		return exportColumns.get(type);
	}
}
