package indi.shuyu.model.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import indi.shuyu.model.entity.Prize;

public interface PrizeDao {
	/**
	 * 在数据库中添加一个规则组
	 * @author SHUYU
	 * @param prizeName
	 * @param createUser
	 * @param createTime
	 * @return
	 */
	int insertPrize(String prizeName, String createUser, Timestamp createTime);
	List<Prize> selectPrizeAll();
	int deletePrize(String prizeId);
	Prize selectByPrizeId(String prizeId);
	int updatePrize(String prizeId, String prizeName);
	List<Prize> selectPrizeListForUser();
}
