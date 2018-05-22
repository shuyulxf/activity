package indi.shuyu.core.rules;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.RuleDao;
import indi.shuyu.model.entity.Activity;
import indi.shuyu.model.entity.Rule;
import indi.shuyu.system.logger.LogDash;

public class UpdateActivityRuleDRLFile {
	
	private final static Logger logger = LoggerFactory.getLogger(UpdateActivityRuleDRLFile.class);
	LogDash ld;
	
	private String[] commonInfoForDrl = {"package indi.shuyu.rules;\r\n",
										 "import java.util.*;",
			 							 "import indi.shuyu.model.entity.User;",
			 							 "import indi.shuyu.actionresult.entity.RuleExResult;",
			 							 "import indi.shuyu.actionresult.entity.ActivityExResult;",
			 							 "import indi.shuyu.core.rules.GlobalFuncForRules;",
			 							 "import indi.shuyu.core.rules.RuleRelatedDaoSourceFunc;",
			 							 "import indi.shuyu.model.entity.Activity;",
			 							 "import indi.shuyu.common.base.Base;",
			 							 "import indi.shuyu.common.base.JacksonUtils;",
			 							 "import java.sql.Timestamp;",
										 "import indi.shuyu.common.base.StringUtils;",
										 "import com.alibaba.fastjson.JSON;",
										 "import com.alibaba.fastjson.JSONArray;",
										 "import com.alibaba.fastjson.JSONObject;",
										 "import indi.shuyu.model.entity.Cache;"};
	
	

	public static String ICPAgendaGlobalGroup = "common-rule-group";
	public static String ICPAgendaActivityFilterGroup = "filter-activity-group";
	

	private String getPath(String name) {
		
		String fileName = "rules/" + name + ".drl";
    	 
    	 ClassLoader classLoader = getClass().getClassLoader();
         URL url = classLoader.getResource(fileName);
         
         String path = "";
         
         if (url == null) path = classLoader.getResource("").getFile() + fileName;
         else path = url.getFile();
         
         return path;
	}
	
    public Boolean update(String name, List<Rule> ruleList) throws IOException{
    	
    	 ld = (LogDash)SpringContextHelper.getBean("logDash");
    	 
    	 String path = getPath(name);
         
         File file = new File(path);
         
         Boolean rlt = false;
         FileChannel fcout = null;
         FileLock flout = null;
         RandomAccessFile out = null;
         try {
        	 
        	 if (file.exists()) file.delete();
         	 else file.createNewFile(); 
                 
             //对该文件加锁  
             out = new RandomAccessFile(file, "rw");  
             fcout = out.getChannel();  
             flout = null;  
             while(true){    
                 try {  
                     flout = fcout.tryLock();  
                     break;  
                 } catch (Exception e) {  
                	 ld.addLog("有其他线程正在操作" + name + "文件，当前线程休眠1000毫秒!");
                      System.out.println();   
                 }  
                   
             }  
             
             StringBuffer commonInfoDrlSb = new StringBuffer();  
             commonInfoDrlSb.append(String.join("\r\n", commonInfoForDrl) + "\r\n\r\n");  
             out.write(commonInfoDrlSb.toString().getBytes("UTF-8")); 
             
             if(ruleList != null && ruleList.size() > 0) {
	             int ruleLen = ruleList.size();
	             Set<String> funcList = new HashSet<String>();
	             for (int i = 0; i < ruleLen; i++) {
	            	 
	            	 StringBuffer sb = new StringBuffer();  
	                 sb.append(getOneFullRuleByRuleEntity(ruleList.get(i), name, funcList));  
	                 out.write(sb.toString().getBytes("utf-8"));  
	             }
             }
             
             ld.addLog(path + "规则文件新建或更新成功！");  
             rlt = true;
             flout.release();  
             fcout.close();  
             out.close();  
             out=null;  
         } catch (IOException e) {  
             e.printStackTrace();  
             ld.addLog(e);
             
             if (flout != null) flout.release();  
             if (fcout != null) fcout.close();  
             if (out != null) out.close();  
             out=null;  
             
         }
         
		 logger.info(ld.toString());
		 
		 return rlt;
    }
     
    private String dealSpecialChar(String r) {
    	
    	r = r.replace("#and#", "&&");
    	r = r.replace("#add#", "+");
    	r = r.replaceAll("'", "\"");
    	
    	return r;
    }
    
    private Matcher getFunctionNameMatcher(String functions, Set<String> funcList) {
    	
    	 if (functions == null || functions.length() == 0) return null;
    	 
    	 String rgx = "function +[^ ]+ +([^\\(]*)\\(";
    	 Pattern r = Pattern.compile(rgx);
 	     Matcher m = r.matcher(functions);
 	     
 	     while(m.find()){
     	   for(int i = 0;i <= m.groupCount();i++){

     		   if (i == 1) funcList.add(m.group(i));
            }
        
         }
 	     
 	     return m;
    }
    
    private String editFunctionNameWithFileName(Matcher m, String functions, String file) {
    	
    	if (functions == null || functions.length() == 0) return functions;
    	m.reset();
	    while(m.find()){
    	   for(int i = 0;i <= m.groupCount(); i++){
    		   String g = m.group(i);
    		   if (i == 1) functions = functions.replaceFirst(g, g + "_"+ file);
           }
       
        }

    	return functions;
    }
    
    private String replaceFuncNameForRule(Set set, String rule, String file) {
    	
    	Iterator<String> it = set.iterator();  
    	while (it.hasNext()) {  
    	  String str = it.next();  
    	  rule = rule.replaceFirst(str, str + "_" + file);
    	} 
    	
    	return rule;
    }
    
    private String getOneFullRuleByRuleEntity(Rule r, String agendaGroup, Set<String> funcList) {
    	
    	if (r == null) return "";
    	
    	List<String> rule = new ArrayList<String>();
    	
    	int ICP = r.getIsCommonGroup();
    	
    	
    	
    	String functions = r.getFunctions();
    	Matcher funNameMatcher = getFunctionNameMatcher(functions, funcList);
    	if (ICP == 2 && funNameMatcher != null)  {
    		functions = editFunctionNameWithFileName(funNameMatcher, functions, agendaGroup);
    	}
    	
	    if (functions != null && functions.length() > 0) {
	    	rule.add( dealSpecialChar(functions) + "\r\n");
	    }
    	
    	String ruleName = r.getRuleNameEN();
    	if (ICP == 2) {
    		ruleName += agendaGroup;
    	}
    	rule.add("rule \"" + ruleName + "\"");
    	
    	
    	if (ICP == 0) rule.add("   agenda-group \"" + ICPAgendaGlobalGroup + "\"");
    	else if (ICP == 1) rule.add("   agenda-group \"" + ICPAgendaActivityFilterGroup + "\"");
    	else rule.add("   agenda-group \"" + agendaGroup + "\"");
    	
    	rule.add("   lock-on-active true");
    	
    	String activationGroupName = r.getExRuleGroupNameEN();
    	if (activationGroupName != null && activationGroupName.length() > 0) rule.add("   activation-group \"" + activationGroupName + "\"");
    	
    	int rgw = r.getRuleGroupWeight();
    	if (rgw == 0) rule.add("   salience " + r.getRuleWeight());
    	else rule.add("   salience " + rgw + r.getRuleWeight());
    
        
        
        String ruleTxt = r.getRule();
        if (ruleTxt != null && ruleTxt.length() > 0) {
        	if (ICP == 2) {
        		//ruleTxt = editFunctionNameWithFileName(funNameMatcher, ruleTxt, agendaGroup);
        		ruleTxt = replaceFuncNameForRule(funcList, ruleTxt, agendaGroup);
        	}
        	rule.add(dealSpecialChar(ruleTxt));
        }
    	
        rule.add("end\r\n\r\n");
        
    	return String.join("\r\n", rule);
    }
    
    public Boolean initSystemAllDrlFiles(RuleDao rd, ConcurrentHashMap<Long, Activity> activityMap) {
    	
    	Boolean res = true;
    	
    	try {
	    	// 生成common 规则文件
	    	List<Rule> ruleCommonList = rd.selectRulesByExacuteProcess(0);
	    	update(UpdateActivityRuleDRLFile.ICPAgendaGlobalGroup, ruleCommonList);
	    	
	    	// 生成common 规则文件
	    	List<Rule> filterActivityList = rd.selectRulesByExacuteProcess(1);
	    	update(UpdateActivityRuleDRLFile.ICPAgendaActivityFilterGroup, filterActivityList);
			
	    	// 创建活动规则文件
	    	for (ConcurrentHashMap.Entry<Long, Activity> entry : activityMap.entrySet()) {
	    		 
	    		Long activityId = entry.getKey();
	    		Activity a = entry.getValue();
	    		String ruleIds = a.getRuleIds();
	    		
	    		if (ruleIds != null && ruleIds.length() > 0) {
					
	    			updateActivtyDrlFile(rd, ruleIds, activityId, a);
				}
	    		
	    	}
	    } catch(Exception ex) {
	    	res = false;
	    	
	    }
    	
    	return res;
    }
    
    public Boolean initSystemAllDrlFiles(RuleDao rd, ConcurrentHashMap<Long, Activity> activityMap, LogDash ld) {
    	
    	Boolean res = true;
    	
    	try {
	    	// 生成common 规则文件
	    	List<Rule> ruleCommonList = rd.selectRulesByExacuteProcess(0);
	    	update(UpdateActivityRuleDRLFile.ICPAgendaGlobalGroup, ruleCommonList);
	    	
	    	// 生成common 规则文件
	    	List<Rule> filterActivityList = rd.selectRulesByExacuteProcess(1);
	    	update(UpdateActivityRuleDRLFile.ICPAgendaActivityFilterGroup, filterActivityList);
			
	    	// 创建活动规则文件
	    	for (ConcurrentHashMap.Entry<Long, Activity> entry : activityMap.entrySet()) {
	    		 
	    		Long activityId = entry.getKey();
	    		Activity a = entry.getValue();
	    		String ruleIds = a.getRuleIds();
	    		
	    		if (ruleIds != null && ruleIds.length() > 0) {
					
	    			updateActivtyDrlFile(rd, ruleIds, activityId, a);
				}
	    		
	    	}
	    } catch(Exception ex) {
	    	res = false;
	    	ld.addLog(ex);
	    }
    	
    	return res;
    }
    
    public void updateActivtyDrlFile(RuleDao rd, String ruleIds, Long activityId, Activity a) throws IOException {
    	
    	ruleIds = ruleIds.replaceAll(" ", "");
		if (ruleIds.length() > 0) {
			String[] ruleIdList = ruleIds.split(",");
			
			List<Rule> rules = rd.selectRuleAndGroupForActivity(ruleIdList);
			
			if (rules != null && rules.size() > 0) {
				Boolean r = update(activityId + "", rules);
				if (!r) throw new Error("为活动" + a.getActivityName() + "创建规则文件失败！");
			} 
		}
    	
    }
    
}