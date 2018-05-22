package indi.shuyu.model.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import indi.shuyu.model.entity.RuleGroup;

public interface RuleGroupDao {
	
	/**
	 * 在数据库中添加一个规则组
	 * @author SHUYU
	 * @param ruleGroupId
	 * @param ruleGroupName
	 * @param ruleGroupWeight
	 * @param createUser
	 * @param createTime
	 * @param string 
	 * @return
	 */
	int insertRuleGroup(RuleGroup rg);
	List<RuleGroup> selectRuleGroupAll();
	int deleteRuleGroup(String ruleGroupId);
	RuleGroup selectByGroupId(String ruleGroupId);
	int updateRuleGroup(RuleGroup rg);

	String selectCommonGroupName();
	List<String> selectGroupNameList();
}
