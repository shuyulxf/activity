package indi.shuyu.initialization;

import java.util.HashMap;

public class ExportColumn {
	
	private HashMap<Integer, HashMap<String, String>> columns;
	
	private String[] headerArr = {"活动名称,发奖地区,发奖渠道,发奖时间,奖品名称,发奖类型,奖品数量,获奖用户",
								  "用户名,获奖活动,获奖地区,获奖渠道,获奖时间,奖品名称,奖品类型,奖品数量",
								  "活动名称,活动地区,活动渠道,参与人次,参与人数,获奖人数",
								  "活动名称,活动地区,活动渠道,中奖人次,中奖人数,已出奖品及数量",
								  "用户,获奖活动,地区,应用渠道,奖品名称,获奖总量"};
	
	private String[] columnArr = {"activityName,province,applyCode,earnTime,awardType,isSeriesAwardForJoinActivity,numberForAward,phoneNumber",
			  "phoneNumber,activityName,province,applyCode,earnTime,awardType,isSeriesAwardForJoinActivity,numberForAward",
			  "activityName,province,applyCode,joinUserNum,disJoinUserNum,awardUserNum",
			  "activityName,province,applyCode,awardUserNum,disAwardUserNum,awardSendDetail",
			  "phoneNumber,activityName,province,applyCode,awardType,numberForAward"};
	
	public ExportColumn() {
		
		columns = new HashMap<Integer, HashMap<String, String>>();
		
		int len = headerArr.length;
		for (int i = 0; i < len; i++) {
			
			String headerStr = headerArr[i];
			String header[] = headerStr.split(",");
			
			HashMap<String, String> map = new HashMap<String, String>();
			String columnStr = columnArr[i];
			String column[] = columnStr.split(",");
			
			int l = column.length;
			for(int j = 0; j < l; j++) {
				map.put(header[j], column[j]);
			}
			
			columns.put(i, map);
		}
		
	}
	
	public HashMap<Integer, HashMap<String, String>> getColumns() {
		return columns;
	}
	
}
