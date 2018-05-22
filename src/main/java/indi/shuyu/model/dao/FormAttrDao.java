package indi.shuyu.model.dao;

import java.util.List;

import indi.shuyu.model.entity.FormAttr;

public interface FormAttrDao {
	
	FormAttr selectFormAttrByFormId(FormAttr fa);

	int insertFormAttr(FormAttr fa);

	int updateFormAttr(FormAttr fa);

	int deleteFormAttrById(String formAttrInfoId);

	List<FormAttr> selectFormsAll();

	List<FormAttr> selectFormExGroupNullAll();
	
	List<FormAttr> selectFormAttrByType(String formAttrNameType);
	
	int selectFormCountByRuleId(String ruleId);
	
	int updateFormAttrRuleIds(String ruleId, Long exRuleGroupId);
	
	int updateFormWhenDelRule(String ruleId);
}
