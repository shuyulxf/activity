package indi.shuyu.service.transactional;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import indi.shuyu.core.rules.UpdateActivityRuleDRLFile;
import indi.shuyu.model.dao.FormAttrDao;
import indi.shuyu.model.dao.RuleDao;
import indi.shuyu.model.entity.Rule;
import indi.shuyu.system.logger.LogDash;

@Service
public class RuleActionTransaction {
	
	
	@Transactional(readOnly=true, rollbackFor = {Exception.class})	
	public boolean createRule(Rule r, String isCommonGroup, RuleDao rd, FormAttrDao fad, LogDash ld) {

		Boolean res = true;
		try {
			int rlt = rd.insertRule(r);
			
			if (rlt < 1) {
				res = false;
			} else {
				Long exRuleGroupId = r.getExRuleGroupId();
		
					
				int fRlt = fad.updateFormAttrRuleIds(r.getRuleId() + "", exRuleGroupId);
				
//				if (isCommonGroup != null && isCommonGroup.length() != 0) {
//					
//					int ICG = Integer.parseInt(isCommonGroup);
//					if (ICG == 1) {
//						UpdateActivityRuleDRLFile uardf = new UpdateActivityRuleDRLFile();
//						List<Rule> ruleList =rd.selectCommonGroupRule();
//						if (ruleList == null || ruleList.size() == 0) {
//							res = false;
//							throw new Error("规则创建失败");
//						} else {
//							Boolean updateRlt = uardf.update(UpdateActivityRuleDRLFile.ICPAgenDaGroup, ruleList);
//							if (!updateRlt) res = false;
//						}
//					} else {
//						
//					}
//				}
			}
			
		}
		catch(Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
			ld.addLog(e);
			throw e;
		}
		
		return res;
	}
	
	@Transactional(readOnly=true, rollbackFor = {Exception.class})	
	public boolean updateRule(Rule r, String isCommonGroup, RuleDao rd, LogDash ld) throws Exception {
		
		Boolean res = true;
		try {
			int rlt = rd.updateRule(r);
			if (rlt < 1) {
				res = false;
			} else {
				
				if (isCommonGroup != null && isCommonGroup.length() != 0) {
					
					int ICG = Integer.parseInt(isCommonGroup);
					if (ICG == 1) {
						UpdateActivityRuleDRLFile uardf = new UpdateActivityRuleDRLFile();
						List<Rule> ruleList = rd.selectRulesByExacuteProcess(0);
						if (ruleList == null || ruleList.size() == 0) {
							res = false;
							throw new Error("规则更新失败！");
						} else {
							Boolean updateRlt = uardf.update(UpdateActivityRuleDRLFile.ICPAgendaGlobalGroup, ruleList);
							if (!updateRlt) res = false;
						}
					}
				}
			}
			
		}
		catch(Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
			ld.addLog(e);
			throw e;
		}
		
		return res;
	}
	
	@Transactional(readOnly=true, rollbackFor = {Exception.class})	
	public boolean deleteRule(String ruleId, String isCommonGroup, RuleDao rd, FormAttrDao fad, LogDash ld) throws Exception {
		
		Boolean res = true;
		try {
			
				int rlt = rd.deleteRuleById(ruleId);
				if (rlt < 1) {
					res = false;
				} else {
					int ufids = fad.updateFormWhenDelRule(ruleId);
				}
				
			
//				else {
//					
//					if (isCommonGroup != null && isCommonGroup.length() != 0) {
//						
//						int ICG = Integer.parseInt(isCommonGroup);
//						if (ICG == 1) {
//							UpdateActivityRuleDRLFile uardf = new UpdateActivityRuleDRLFile();
//							List<Rule> ruleList = rd.selectRulesByExacuteProcess(0);
//							
//							Boolean updateRlt = uardf.update(UpdateActivityRuleDRLFile.ICPAgendaGlobalGroup, ruleList);
//							if (!updateRlt) res = false;
//						}
//					}
//				}
			
			
		}
		catch(Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
			ld.addLog(e);
			throw e;
		}
		
		return res;
	}
}
