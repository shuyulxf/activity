package indi.shuyu.service.transactional;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import indi.shuyu.common.base.Base;
import indi.shuyu.core.rules.UpdateActivityRuleDRLFile;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.ActivityAttrDao;
import indi.shuyu.model.dao.AwardDao;
import indi.shuyu.model.dao.RuleDao;
import indi.shuyu.model.entity.Activity;
import indi.shuyu.model.entity.Award;

@Service
public class ActivityActionTransaction {

	@Transactional(readOnly=true, rollbackFor = {Exception.class})	
	public boolean createActivity(Activity activity, JSONArray awards, String ruleIds, 
								  ActivityAttrDao aad, RuleDao rd, AwardDao ad, GlobalStaticValues gv) throws Exception {
		
		try {
			
			int aRlt = aad.insertActivity(activity);
			
			long activityId = activity.getActivityId();
			if (aRlt < 1 || activityId == 0) throw new Error("活动插入失败");
			
			
			if (awards != null) {
				
			    int len = awards.size();
			    Timestamp t = Base.toTimeStamp();
			    for (int i = 0; i < len; i++) {
			    	JSONObject award = awards.getJSONObject(i);
			    	
			    	Boolean rlt = insertAward(activityId, award, t, ad);
			    	if (!rlt) throw new Exception("插入奖品失败，事务回滚, 请检查单次发奖量是否合法");
			    }
			}
			UpdateActivityRuleDRLFile uardf = new UpdateActivityRuleDRLFile();
			uardf.updateActivtyDrlFile(rd, ruleIds, activityId, activity);
			
			gv.addActivityMap(activityId, activity);
		}
		catch(Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
			throw e;
		}
		
		return true;
	}
	
	private boolean insertAward(Long activityId, JSONObject award, Timestamp t, AwardDao ad) {
		
		Award a = new Award();
    	a.setActivityId(activityId);
    	a.setPrizeId(award.getLong("type"));
    	int num = award.getInteger("num"),
    		start = award.getInteger("numStart"),
    		end = award.getInteger("numEnd"),
    		distance = award.getInteger("distance");
    	
    	if (start > num || end < start || distance > num) return false;
    	a.setAwardNum(num);
    	a.setNumStart(start);
    	a.setNumEnd(end);
    	a.setDistance(distance);
    	a.setCreateTime(t);
    	int rlt = ad.insertAward(a);
    	
    	if (rlt == 1) return true;
    	
    	return false;
	}
	
	private boolean updateAward(Long activityId, JSONObject award, Timestamp t, AwardDao ad) {
		
		Award a = new Award();
		a.setPrizeId(award.getLong("type"));
		a.setActivityId(activityId);
    	a.setNumStart(award.getInteger("numStart"));
    	a.setNumEnd(award.getInteger("numEnd"));
    	a.setDistance(award.getInteger("distance"));
    	int rlt = ad.updateAward(a);
    	
    	if (rlt == 1) return true;
    	
    	return false;
	}
	
	private boolean findPrizeId(List<Long> ids, Long id, int len) {
		
		for (int i = 0; i < len; i++) {
			if (ids.get(i) == id) return true;
		}
		
		return false;
	}
	
	@Transactional(readOnly=true, rollbackFor = {Exception.class})	
	public boolean updateActivity(Activity activity, JSONArray awards, String ruleIds, 
								  ActivityAttrDao aad, RuleDao rd, AwardDao ad, GlobalStaticValues gv) throws Exception {
		
		try {
			
			int aRlt = aad.updateActivity(activity);
			
			long activityId = activity.getActivityId();
			if (aRlt < 1 || activityId == 0) throw new Error("活动更新失败!");
			
			UpdateActivityRuleDRLFile uardf = new UpdateActivityRuleDRLFile();
			uardf.updateActivtyDrlFile(rd, ruleIds, activityId, activity);
			
			if (awards != null) {
			    int len = awards.size();
			    if (len > 0) {
			    	List<Long> ids = ad.selectAwardByActivityId(activityId);
			    	int size = ids.size();
			    	Timestamp t = Base.toTimeStamp();
				    for (int i = 0; i < len; i++) {
				    	JSONObject award = awards.getJSONObject(i);
				    	
				    	if (findPrizeId(ids, award.getLong("type"), size)) {
				    		Boolean rlt = updateAward(activityId, award, t, ad);
				    		if (!rlt) throw new Exception("更新奖品失败，事务回滚, 请检查单次发奖量是否合法");
				    	} else {
				    		Boolean rlt = insertAward(activityId, award, t, ad);
					    	if (!rlt) throw new Exception("更新奖品失败，事务回滚, 请检查单次发奖量是否合法");
				    	}
				    }
				}
			}
			
			gv.updateActivityMap(activityId, activity);
		}
		catch(Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
			throw e;
		}
		
		return true;
	}
	
	@Transactional(readOnly=true, rollbackFor = {Exception.class})	
	public boolean deleteActivity(String activityId, String createUser, ActivityAttrDao aad, RuleDao rd, AwardDao ad, GlobalStaticValues gv) {
		
		try {
			
			ad.deleteAwardByActivityId(Long.parseLong(activityId));
			
			int aRlt = aad.deleteActivity(activityId, createUser);
			
			if (aRlt < 1) throw new Error("活动删除失败");
			
			
			
			gv.removeActivityMap(Long.parseLong(activityId));
		}
		catch(Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
			throw e;
		}
		
		return true;
	}
}
