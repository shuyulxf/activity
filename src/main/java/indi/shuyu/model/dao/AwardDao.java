package indi.shuyu.model.dao;

import java.util.HashMap;
import java.util.List;

import indi.shuyu.model.entity.Award;
import indi.shuyu.model.entity.Prize;

public interface AwardDao {
	int insertAward(Award a);
	List<Long> selectAwardByActivityId(Long activityId);
	int deleteAwardByActivityId(Long activityId);
	int updateAward(Award a);
	List<Prize> selectPrizeByActivityId(Long activityId);
	
	List<Award> selectLeftAwardNumByActivityName(List<Long> lists);
	
	List<Award> selectAwardInfoByActivityId(Long id);
	
	int updateAwardNum(Long awardId, int awardNum);
	
	int updateAA(int a, int b);
	
	List<Award>  selectAwardByPrizeIds(List<String> prizeList);
}
