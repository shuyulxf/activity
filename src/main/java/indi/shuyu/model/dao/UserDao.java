package indi.shuyu.model.dao;

import java.util.HashMap;
import java.util.List;

import indi.shuyu.model.entity.Data;
import indi.shuyu.model.entity.Search;
import indi.shuyu.model.entity.User;

public interface UserDao {
	
	int insertSearchUser(User u);
	int insertUserJoinInfo(User u);
	int insertUserAwardInfo(User u);
	
	List<User> selectAwardHistory(String phoneNumber, String activityName);
	
	int selectFinishNumForUser(String phoneNumber, String activityName);
	
	int selectFinishNumPerDayForUser(String phoneNumber, String activityName);
	
	int selectFinishNumPerWeekForUser(String phoneNumber, String activityName);
	
	int selectFinishNumPerMonthForUser(String phoneNumber, String activityName);
	
	int selectEarnAwardNumTotalForUser(String phoneNumber, String activityName);
	
	int selectEarnAwardNumPerDayForUser(String phoneNumber, String activityName);
	
	int selectEarnAwardNumPerWeekForUser(String phoneNumber, String activityName);
	
	int selectEarnAwardNumPerMonthForUser(String phoneNumber, String activityName);
	
	List<HashMap<String, Integer>> selectTotalFinishJoinUserNumByActivityName(List<String> lists);
	
	List<HashMap<String, Integer>> selectTotalAwardUserNumByActivityName(List<String> lists);
	
	int selectTotalSeriesAwardNumForUser(String phoneNumber, String activityName);
	
	int ifExistCurSeriesAward(String phoneNumber, String activityName);
	
	List<Data> selectUserEarnAwardList(Search s);
	
	int selectTotalNumUserEarnAward(Search s);
	
	List<Data> selectStatisticInActivityDimension(Search s);
	
	long selectTotalNumForStatisticInActivityDimension(Search s);
	
	long selectTotalNumForStatisticInPrizeDimension(Search s);
	
	List<Data> selectStatisticInPrizeDimension(Search s);
	
	List<Data> selectStatisticInUserDimension(Search s);
	
	long selectTotalNumForStatisticInUserDimension(Search s);
	
	List<Data> selectUserEarnAwardListForExport(Search s);
	
	List<Data> selectStatisticInActivityDimensionForExport(Search s);
	
	List<Data> selectStatisticInPrizeDimensionForExport(Search s);
	
	List<Data> selectStatisticInUserDimensionForExport(Search s);
}
