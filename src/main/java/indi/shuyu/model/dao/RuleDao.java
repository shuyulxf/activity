package indi.shuyu.model.dao;

import java.util.List;

import indi.shuyu.model.entity.FormAttr;
import indi.shuyu.model.entity.Rule;
import indi.shuyu.model.entity.RuleGroup;


public interface RuleDao {
	
	// GROUPS
	// select all rulegroups 
	List<RuleGroup> selectRuleGroupAll(int isExclusive);
	
	
	// insert into rule
	int insertRule(Rule rule);
	// update rule
	int updateRule(Rule rule);
	// delete rule
	int deleteRuleById(String ruleId);
	// select all rules
	List<Rule> selectRuleAll();
	// selet all rules no exgroup
	List<Rule> selectRuleExGroupNullAll();
	Rule selectRuleByRuleId(String ruleId);
	
	long[] selectRuleIdsByGroupId(long ex);
	List<Rule> selectRuleAndGroupForActivity(String[] ruleIds);
	
	List<Rule> selectRulesByExacuteProcess(int type);
	
	List<String> selectRulesByExGroupId(Long exRuleGroupId);
}
