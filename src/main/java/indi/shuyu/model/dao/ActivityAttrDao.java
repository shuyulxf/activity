package indi.shuyu.model.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import indi.shuyu.model.entity.Activity;
import indi.shuyu.model.entity.Search;

public interface ActivityAttrDao {
	
	// 插入一条活动
	int insertActivity(Activity a);
	
	// 选出一条活动
	Activity selectActivityById(String activityId, String createUser);
	
	// 更新活动
	int updateActivity(Activity a);
	
	int deleteActivity(String activityId, String createUser);
	
	long selectActivityTotalNumber(Search params);
	
	List<Activity> selectActivityList(Search params);
	
	List<Activity> selectActivityList1(Search params);
	
	List<Activity> selectNotOffLineActivityList(Timestamp sysTime);
	
	List<HashMap<Long, String>> selectAllActivityIdAndMapForUser(String createUser);
}
