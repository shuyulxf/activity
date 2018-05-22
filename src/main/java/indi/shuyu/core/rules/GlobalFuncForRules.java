package indi.shuyu.core.rules;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import indi.shuyu.model.entity.Activity;
import indi.shuyu.model.entity.Award;
import indi.shuyu.model.entity.User;

public class GlobalFuncForRules {
	
	public static Boolean switchGate(User u) {
		
		if (u.getStatus() == 0) return true;
		
		return false;
	}
	
	private static String[] REPLYTYPES = {"ReplySuc", "ReplyFail"};
	public static String getReplyForRule(Activity a, String[] formAttrList, int type) {
		
		if (a == null || formAttrList == null || formAttrList.length < 1) return "";
		
		int len = formAttrList.length;
		JSONObject replies = a.getARS();
		if (replies == null) return "";
		String rlt = "";
		for (int i = 0; i < len; i++) {
			
			String r = replies.getString(formAttrList[i] + REPLYTYPES[type]);
			if (r != null && r.length() > 0) rlt += r;
			
		}
		
		return rlt;
	}
	
	public static double getRandomRate() {
		return Math.random();
	}
	
	public static int getNumForSendAwardOnce(int min, int max, int dis) {
		
		if (min == max) return dis;
		return ((new Random()).nextInt((max - min) / dis + 1)) * dis + min;
	}
	
	public static JSONObject getSendAwardIdAndNumForUser(List<Award> awards, double rate) {
		
		Long awardId = (long) 0;
		int awardIdx = 0;
		Award desAward = null;
		
		int len = awards.size();
		int total = 0;
		for(int i = 0; i < len; i++) {
			total += awards.get(i).getAwardNum();
		}
		
		HashMap<Long, Double> rateMap = new HashMap<Long, Double>();
		Award first = awards.get(0);
		double preNum = first.getAwardNum();
		
		if (rate <= preNum*1.0/total) {
			awardIdx = 0;
			awardId = first.getAwardId();
		} else {
			
			for(int i = 1; i < len; i++) {
				Award award = awards.get(i);
				preNum += award.getAwardNum();
				if (rate <= preNum*1.0/total) {
					awardIdx = i;
					awardId = award.getAwardId();
				}
			}
		}
		
		desAward = awards.get(awardIdx);
		
		int num = GlobalFuncForRules.getNumForSendAwardOnce(desAward.getNumStart(), desAward.getNumEnd(), desAward.getDistance());
		
		JSONObject rlt = new JSONObject();
		rlt.put("awardId", awardId);
		rlt.put("num", num);
		rlt.put("awardType", awards.get(awardIdx).getPrizeName());
		
		return rlt;
	}
}
