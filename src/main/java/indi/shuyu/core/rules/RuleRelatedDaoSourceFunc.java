package indi.shuyu.core.rules;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.mapping.Map;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import indi.shuyu.actionresult.entity.RuleExResult;
import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.common.http.SpringHttpHelper;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.AwardDao;
import indi.shuyu.model.dao.UserDao;
import indi.shuyu.model.entity.Activity;
import indi.shuyu.model.entity.Award;
import indi.shuyu.model.entity.Cache;
import indi.shuyu.model.entity.User;
import indi.shuyu.service.transactional.DataConsistencyTransactional;

@Service
public class RuleRelatedDaoSourceFunc {

	private UserDao ud;
	private AwardDao ad;
	private DataConsistencyTransactional act;
	public ConcurrentHashMap<String, Cache> caches = null; 
	
	
	
	public RuleRelatedDaoSourceFunc(UserDao ud, AwardDao ad, DataConsistencyTransactional act) {
		this.ud = ud;
		this.ad = ad;
		this.act = act;
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		this.caches = gv.caches;
		
	}
	
	public String getKeywordMatch(String query, String keywords) throws UnsupportedEncodingException{

		String url = "http://222.186.101.212:8282/BranchInterface/BranchInterface?funcation=UnderstandByKeyword";
		
		String param = "&query=" + query + "&keyWord=" + keywords;
		url = url + "&query=" + URLEncoder.encode(query,"UTF-8") + "&keyWord=" + URLEncoder.encode(keywords,"UTF-8");
		
		url = url.replaceAll("%2C", ",");

		String rlt = SpringHttpHelper.sendMessageGetHttp(url);
		
		return rlt;
	}

	
	public void addNewCache(User u, Activity a, int status, int idx, int state, int flag) {
		
	    Cache c = new Cache();
	    String sessionId = u.getSessionId();
	    if(caches.get(sessionId) == null)	{
	    	c.setSessionId(sessionId);
		    c.setActivityId(a.getActivityId());
		    c.setUserName(u.getPhoneNumber());
		    c.setStatus(status);
		    c.setT(System.currentTimeMillis());
		    
		    JSONObject infos = new JSONObject();
		    infos.put("state", state);
		    if (flag == 1) infos.put("quesSeq", idx);
		    else if(flag == 2)  {
		    	infos.put("activityIndex", idx);
		    	infos.put("quesSeq", -1);
		    }
		   
		    c.setInfos(infos);
		    
		    this.caches.put(sessionId, c);
	    }
	}
	
	public Cache getCache(String sessionId) {
		
		Cache c = caches.get(sessionId);
		return c;
	}
	
	public void removeCache(String sessionId) {
		if(caches.get(sessionId) != null)	caches.remove(sessionId);
	}
	
	public boolean updateCache(String sessionId, int idx, int state, int flag) {
		
		Cache c = caches.get(sessionId);
		if (c != null) {
			JSONObject info = c.getInfos();
			info.put("state", state);
			if (flag == 1) info.put("quesSeq", idx);
			else if(flag == 2) info.put("activityIndex", idx);
			
			c.setInfos(info);
			return true;
		}
		
		return false;
	}
	
	public String getPrizeQuizQuestion(JSONObject ques) {
		
		String rlt = "";
		rlt += ques.getString("question");
		
		JSONArray options = ques.getJSONArray("options");
		int len = options.size();
		
		for (int i = 0; i < len; i++) {
			rlt += "<br>[" + (i + 1) + "] " + options.getString(i);
		}
		
		
		return rlt;
	}
	
	private HashMap<String, String> setIntegerMap() {
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("一", "1");
		map.put("二", "2");
		map.put("三", "3");
		map.put("四", "4");
		map.put("五", "5");
		map.put("六", "6");
		map.put("七", "7");
		map.put("八", "8");
		map.put("九", "9");
		
		return map;
		
	}
	
	public boolean judgeAnswerIsRight(String query, JSONArray options, String answer) {
		
		String opt = ChooseInteraction.compareChooseItemAndQuery(query, options);
		boolean rlt = false;
		
		if ("-1".equals(opt)) {
			
			if ("123456789".indexOf(query) != -1) {
				if (query.equals(answer)) rlt = true;
			} else {
				HashMap<String, String> map = setIntegerMap();
				for (Entry<String, String> entry : map.entrySet()) {  
					String key = entry.getKey(),
						   value = entry.getValue();
					if (query.indexOf(key) != -1 && value.equals(answer)) {
						rlt = true;
					}
				}  
			}			
		} else {
			if (answer.equals(opt)) rlt = true;
		}
		
		return rlt;
	}
	
	public String getAwardHistory(String phoneNumber, String activityName) {
		
		
		
		List<User> list = ud.selectAwardHistory(phoneNumber, activityName);
		if (list == null || list.size() < 1) return "";
			
		return getAwardStyle(list, activityName);
	}
	
	private String getAwardStyle(List<User> list, String activityName) {
		
		List<String> rlt = new ArrayList<String>();
		
		rlt.add("<style type=\"text/css\">.award-list{border-collapse: collapse;}.award-list td{border: 1px solid #cdcdcd;padding: 2px 5px;text-align: center;font-size: 12px;}</style>");
		rlt.add("<table class=\"award-list\" style=\"border-collapse: collapse;\"><thead><tr><td colspan=\"3\">");
		rlt.add(activityName);
		rlt.add("</td></tr></thead><tbody><tr><td>奖品名称</td><td>数量</td><td>时间</td></tr>");
		
		int len = list.size();
		for(int i = 0; i < len; i++) {
			User u = list.get(i);
			rlt.add("<tr><td>" + u.getAwardType() + "</td>");
			rlt.add("<td>" + u.getNumberForAward() + "</td>");
			rlt.add("<td>" + u.getEarnTime() + "</td></td>");
		}
		
		rlt.add("</tbody></table>");
		
		return String.join("", rlt);
	}
	
	public boolean insertJoinInfoForUser(User $u, int isFinished, String replyInfo) {
		
		$u.setIsFinished(isFinished);
		$u.setReplyInfo(replyInfo);
		int rlt = ud.insertUserJoinInfo($u);
		
		return rlt == 1 ? true : false;
	}
	
	public boolean isNotInProcess(RuleExResult rer) {
		
		int status = rer.getStatus();
		if (status == 3 || status == 4) return false;
		return true;
	}  
	
	public int getFinishNumForUser(String phoneNumber, String activityName) {
		return ud.selectFinishNumForUser(phoneNumber, activityName);
	}
	
	public int getFinishNumPerDayForUser(String phoneNumber, String activityName) {
		return ud.selectFinishNumPerDayForUser(phoneNumber, activityName);
	}
	
	public int getFinishNumPerWeekForUser(String phoneNumber, String activityName) {
		return ud.selectFinishNumPerWeekForUser(phoneNumber, activityName);
	}
	
	public int getFinishNumPerMonthForUser(String phoneNumber, String activityName) {
		return ud.selectFinishNumPerMonthForUser(phoneNumber, activityName);
	}
	
	public boolean insertAwardInfoForUser(User u, Activity a, boolean isSeries) {
		
		List<Award>  awards = null;
		if (isSeries) {
			String prizeListStr = a.getAAS().getString("prizeIdsForSeriesAward");
			if (prizeListStr != null && prizeListStr.length() > 0) {
				String[] prizeArr = prizeListStr.split(",");
				int len = prizeArr.length;
				if(len > 0) {
					List<String> prizeList = new ArrayList<String>();
					for (int i = 0; i < len; i++) {
						prizeList.add(prizeArr[i]);
					}
					awards = ad.selectAwardByPrizeIds(prizeList);
				}
			}
		}
		else awards = ad.selectAwardInfoByActivityId(a.getActivityId());
		if (awards == null || awards.size() == 0) return false;
		
		JSONObject res = GlobalFuncForRules.getSendAwardIdAndNumForUser(awards, u.getRate());
		long awardId = res.getInteger("awardId");
		int awardNum = res.getInteger("num");
		
		u.setEarnTime(Base.toTimeStamp());
		u.setIsSeriesAwardForJoinActivity(0);
		u.setAwardType(res.getString("awardType"));
		u.setNumberForAward(awardNum);
		
		if (isSeries) u.setIsSeriesAwardForJoinActivity(1);
		
		boolean rlt = act.sendAwardDetail(awardId, awardNum, u, ad, ud);
		
		return rlt;
	}

	public int getEarnAwardNumPerDayForUser(String phoneNumber, String activityName) {
		return ud.selectEarnAwardNumPerDayForUser(phoneNumber, activityName);
	}
	
	public int getEarnAwardNumPerWeekForUser(String phoneNumber, String activityName) {
		return ud.selectEarnAwardNumPerWeekForUser(phoneNumber, activityName);
	}
	
	public int getEarnAwardNumPerMonthForUser(String phoneNumber, String activityName) {
		return ud.selectEarnAwardNumPerMonthForUser(phoneNumber, activityName);
	}
	
	public int getEarnAwardNumForUser(String phoneNumber, String activityName) {
		return ud.selectEarnAwardNumTotalForUser(phoneNumber, activityName);
	}

	public int getTotalSeriesAwardNumForUser(String phoneNumber, String activityName) {
		return ud.selectTotalSeriesAwardNumForUser(phoneNumber, activityName);
	}
	public boolean ifExistCurSeriesAward(String phoneNumber, String activityName) {
		return ud.ifExistCurSeriesAward(phoneNumber, activityName) > 0 ? true : false;
	}
}
