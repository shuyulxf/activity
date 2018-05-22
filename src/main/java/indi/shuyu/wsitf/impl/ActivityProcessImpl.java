package indi.shuyu.wsitf.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jws.WebService;

import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import indi.shuyu.actionresult.entity.ActivityExResult;
import indi.shuyu.actionresult.entity.RuleExResult;
import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.JacksonUtils;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.common.validator.ValidItemPairResult;
import indi.shuyu.common.validator.ValidatorHelper;
import indi.shuyu.core.rules.KieUtils;
import indi.shuyu.core.rules.RuleRelatedDaoSourceFunc;
import indi.shuyu.core.rules.UpdateActivityRuleDRLFile;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.AwardDao;
import indi.shuyu.model.dao.UserDao;
import indi.shuyu.model.entity.Activity;
import indi.shuyu.model.entity.Cache;
import indi.shuyu.model.entity.User;
import indi.shuyu.service.transactional.DataConsistencyTransactional;
import indi.shuyu.system.logger.LogDash;

@WebService 
public class ActivityProcessImpl{
	
	private final static Logger logger = LoggerFactory.getLogger(ActivityProcessImpl.class);
	LogDash ld;
	
	@Autowired  
	private UserDao ud;
	
	@Autowired
	private AwardDao ad;
	
	@Autowired
	private DataConsistencyTransactional act;
	
	
	private String[] OnceActivityTypes = {"有奖问答", "闯关游戏"};
	
	private boolean isOnceActivity(String type) {
		
		int len = OnceActivityTypes.length;
		for (int i = 0; i < len; i++) {
			if (type.equals(OnceActivityTypes[i])) return true;
		}
		return false;
	}
	public String joinActivity(String paramStr) {
		
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		RuleExResult rer = new RuleExResult();
		
		try {
			GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
			
			ld.addLog(paramStr);
			
			if (paramStr == null || paramStr.length() == 0) {
				rer.setInfo("参数解析异常，请检查");
				rer.setMsg("系统异常");
				rer.setStatus(1);
			} else {
				User user = JacksonUtils.toObject(paramStr, User.class);
				user.setCreateTime(Base.toTimeStamp());
				
		    	ValidItemPairResult vpr = ValidatorHelper.validParams(user, gv);
		    	if (vpr.getValid()) {
					rer.setUserName(user.getPhoneNumber());
					
					String ICPAgenDaGroup = UpdateActivityRuleDRLFile.ICPAgendaGlobalGroup;
					String ICPAgendaActivityFilterGroup = UpdateActivityRuleDRLFile.ICPAgendaActivityFilterGroup;
					ConcurrentHashMap<Long, Activity> activityMap = gv.getActivityMap();
					KieHelper kh = new KieHelper();
					RuleRelatedDaoSourceFunc rrds = new RuleRelatedDaoSourceFunc(ud, ad, act);
					KieSession kSession = null;
					
					// 活动参与者基本条件验证
					KieUtils.addResource(ICPAgenDaGroup);
					kSession = KieUtils.getKS();
					kSession.getAgenda().getAgendaGroup( ICPAgenDaGroup ).setFocus();
					
				    kSession.insert(user);
				    kSession.insert(rer);
				    kSession.insert(rrds);
				    kSession.fireAllRules();
				    kSession.dispose();
	
				    // 非在闯关活动中就插入一条搜索记录
				    if (user.getStatus() == 0 || user.getStatus() == 1) ud.insertSearchUser(user);
				    
				  
				    if (rer.getStatus() != 1 ) {
					     
				    	 // 筛选可参与的活动，并依次执行
					     Iterator<Map.Entry<Long, Activity>> entries = activityMap.entrySet().iterator();  
					     
					     Cache c = rrds.getCache(user.getSessionId());
					     if (c != null) {
					    	 long key = c.getActivityId();
					    	 String keyStr = key + "";
					    	 Activity a = activityMap.get(key);
					    	 ActivityExResult aer = new ActivityExResult();
					    	 String activityName = a.getActivityName();
				             aer.setActivityName(activityName);
				             user.setActivityName(activityName);
			                
			                 kSession = KieUtils.getKS(keyStr);
			    
			                 kSession.insert(a);
			                 kSession.insert(aer);
			                 kSession.insert(rrds);
			                 kSession.insert(user);
			                 kSession.insert(rer);
			                 kSession.getAgenda().getAgendaGroup(keyStr).setFocus();
			                 kSession.fireAllRules();
			                 rer.setResult(aer);
			                 kSession.dispose();
			            
					     } else {
					    	 KieUtils.addResource(ICPAgendaActivityFilterGroup);
					         
						     while (entries.hasNext()) {   
						        
						        kSession = KieUtils.getKS();
						        kSession.getAgenda().getAgendaGroup( ICPAgendaActivityFilterGroup ).setFocus();
						         
						        Map.Entry<Long, Activity> entry = entries.next();
						        Long key = entry.getKey();
						        String keyStr = key + "";
						        Activity a = entry.getValue();
						        ActivityExResult aer = new ActivityExResult();
						        
						       
						        kSession.insert(a);
						        kSession.insert(aer);
						        kSession.insert(rrds);
						        kSession.insert(user);
						        kSession.insert(rer);
						        kSession.fireAllRules();
						        kSession.dispose();
						        
						        int status = aer.getStatus();
						         
					            if (status != 1) {
					            	String activityName = a.getActivityName();
					                aer.setActivityName(activityName);
					                user.setActivityName(activityName);
					                //当前用户查询中奖记录
					                if (status == 2) { 
					                   rer.setResult(aer);   
					                } else {
					                
					                   kSession = KieUtils.getKS(keyStr);
					    
					                   kSession.insert(a);
					                   kSession.insert(aer);
					                   kSession.insert(rrds);
					                   kSession.insert(user);
					                   kSession.insert(rer);
					                   kSession.getAgenda().getAgendaGroup( keyStr ).setFocus();
					                   kSession.fireAllRules();
					                   rer.setResult(aer);
					                   kSession.dispose();
					                 }
					             } 
					             ld.addLog(aer);
					             Cache cache = rrds.getCache(user.getSessionId());
					             //为保证当前系统只有一个有奖问答或闯关游戏
					             if (cache != null) break;
						     }
					     }
					   }
				   } else {
					   rer.setInfo("参数解析异常，请检查");
				       rer.setMsg("用户输入字段验证失败，请查看您的信息是否正确");
					   rer.setStatus(1);
				   }
			}
		}catch(Exception e) {
			ld.addLog(e);
			rer.setInfo("参数解析异常，请检查");
			rer.setMsg("系统异常");
			rer.setStatus(1);
		}
		
		ld.addLog(JSON.toJSONString(rer));
		logger.info(ld.toString());
		
		return JSON.toJSONString(rer);
	}
	
}