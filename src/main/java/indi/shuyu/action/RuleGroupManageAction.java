package indi.shuyu.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import indi.shuyu.actionresult.entity.ActionResult;
import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.common.validator.ValidItemPairResult;
import indi.shuyu.common.validator.ValidatorItemPair;
import indi.shuyu.controller.RuleManageController;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.RuleGroupDao;
import indi.shuyu.model.entity.RuleGroup;
import indi.shuyu.system.logger.LogDash;

@Controller
@RequestMapping("/rulegroup/action")
public class RuleGroupManageAction {
	
	private final static Logger logger = LoggerFactory.getLogger(RuleManageController.class);
	LogDash ld;
	
	@Autowired  
	private RuleGroupDao rgd;  
	
	@Autowired  
	private HttpSession session; 
	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult createRuleGroup(String ruleGroupNameCN, String ruleGroupNameEN,String ruleGroupWeight, String isExclusive, String isCommonGroup) throws Exception {
		
		ActionResult ar = new ActionResult();
		ValidItemPairResult vpr = validAddRuleGroupParams(ruleGroupNameCN, ruleGroupWeight, isExclusive);
		
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		
		if (!vpr.getValid()) {
			
			ar.setParamValidError();
			ar.setMsg(vpr.getMsg());

		} else {
			
			try {
				
				String userName = Base.getLoginUserName(session);
				
				RuleGroup rg = new RuleGroup();
				rg.setRuleGroupNameCN(ruleGroupNameCN);
				rg.setRuleGroupNameEN(ruleGroupNameEN);
				rg.setRuleGroupWeight(Integer.parseInt(ruleGroupWeight));
				rg.setIsExclusive(Integer.parseInt(isExclusive));
				rg.setIsCommonGroup(Integer.parseInt(isCommonGroup));
				rg.setCreateUser(userName);
				rg.setCreateTime(Base.toTimeStamp());

				int rlt = rgd.insertRuleGroup(rg);
				if (rlt == 1) {
					ar.setStatusSuccess();
					ar.setMsg("添加规则组成功"); 
				} else {
					ar.setStatusFail();
					ar.setCode("4009");
					ar.setMsg("添加规则组失败，未知原因"); 
				}
			} catch(Exception e) {
				
				ar.setStatusFail();
				String msg = e.getMessage();
				if(msg.contains("违反唯一约束条件 ")) {
					ar.setCode("4001");
					ar.setMsg("规则组添加失败，重复的规则组名称!");
				} else {
					ar.setCode("4002");
					ar.setMsg(msg);
				}
				
				ld.addLog(ar.toString());
			}
		}
		
		ld.addLog(ar.toString());
		logger.info(ld.toString());
		
		return ar;
	}
	
	private ValidItemPairResult validAddRuleGroupParams(String ruleGroupName, String ruleGroupWeight, String isExclusive) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");

		String[] ruleGroupNameTypes   = {"required", "text", "maxlength", "minlength"},
				 ruleGroupWeightTypes = {"required", "weight"},
			     isExclusiveTypes = {"required", "radio"};
		
		HashMap<String, String> variables = new HashMap<String, String>();
		variables.put("maxlength", "30");
		variables.put("minlength", "6");
		
		List<ValidatorItemPair> pairs = new ArrayList<ValidatorItemPair>();
		pairs.add(gv.getValidatorItempair(ruleGroupNameTypes, "ruleGroupName", ruleGroupName, variables));
		pairs.add(gv.getValidatorItempair(ruleGroupWeightTypes, "ruleGroupWeight", ruleGroupWeight));
		pairs.add(gv.getValidatorItempair(isExclusiveTypes, "isExclusive", isExclusive));
		
		return gv.validte(pairs);
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult deleteRuleGroup(String ruleGroupId) throws Exception {
		
		ActionResult ar = new ActionResult();
		ValidItemPairResult vpr = validRuleGroupIdParams(ruleGroupId);
		
		if (!vpr.getValid()) {
			
			ar.setParamValidError();
			ar.setMsg(vpr.getMsg());
			
		}  else {
			try {
				
				ld = (LogDash)SpringContextHelper.getBean("logDash");
				
				String userName = Base.getLoginUserName(session);
				
				
				int rlt = rgd.deleteRuleGroup(ruleGroupId);

				
				if (rlt == 1) {
					ar.setStatusSuccess();
					ar.setMsg("删除规则组成功"); 
				} else {
					ar.setStatusFail();
					ar.setCode("4008");
					ar.setMsg("删除规则组失败，未知原因"); 
				}
			} catch(Exception e) {
				
				ar.setStatusFail();
				ar.setCode("4008");
				ar.setMsg(e.getMessage());
			}
		}
		
		ld.addLog(ar.toString());
		logger.info(ld.toString());
		
		return ar;
	}
	
	private ValidItemPairResult validRuleGroupIdParams(String ruleGroupId) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");

		String[] ruleGroupIdTypes   = {"number"};
		
		List<ValidatorItemPair> pairs = new ArrayList<ValidatorItemPair>();
		pairs.add(gv.getValidatorItempair(ruleGroupIdTypes, "ruleGroupId", ruleGroupId));
		
		return gv.validte(pairs);
	}
	
	@RequestMapping(value="/edit/get", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult editRuleGroupGet(String ruleGroupId) throws Exception {
		
		ActionResult ar = new ActionResult();
		ValidItemPairResult vpr = validRuleGroupIdParams(ruleGroupId);
		
		if (!vpr.getValid()) {
			
			ar.setParamValidError();
			ar.setMsg(vpr.getMsg());
			
		}  else {
			try {
				
				ld = (LogDash)SpringContextHelper.getBean("logDash");
				
				String userName = Base.getLoginUserName(session);
				
				
				RuleGroup rg = rgd.selectByGroupId(ruleGroupId);
			    if (rg == null) {
			    	ar.setStatusFail();
			    	ar.setCode("4002");
			    	ar.setMsg("按规则组ID查找规则组失败！");
			    } else {
			    	ar.setStatusSuccess();
					ar.setCode("4000");
					ar.setMsg("按规则组ID查找规则组成功！");
			    	ar.setData(rg.toString());
			    }

			} catch(Exception e) {
				
				ar.setStatusFail();
			    ar.setCode("4007");
				ar.setMsg(e.getMessage());
			}
		}
		
		ld.addLog(ar.toString());
		logger.info(ld.toString());
		
		return ar;
	}
	
	@RequestMapping(value="/edit/save", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult editRuleGroupSave(String ruleGroupId, String ruleGroupNameCN, String ruleGroupNameEN, String ruleGroupWeight, String isExclusive, String isCommonGroup) throws Exception {
		
		ActionResult ar = new ActionResult();
		ValidItemPairResult vpr = validAddRuleGroupParams(ruleGroupNameCN, ruleGroupWeight, isExclusive);
		ValidItemPairResult vpr1 = validRuleGroupIdParams(ruleGroupId);
		
		if (!vpr.getValid() || !vpr1.getValid()) {
			
			ar.setParamValidError();
			if (vpr.getValid())	ar.setMsg(vpr1.getMsg());
			else 	ar.setMsg(vpr.getMsg());
			
		}  else {
			try {
				
				ld = (LogDash)SpringContextHelper.getBean("logDash");
				
				String userName = Base.getLoginUserName(session);
				
				RuleGroup rg = new RuleGroup();
				rg.setRuleGroupNameCN(ruleGroupNameCN);
				rg.setRuleGroupNameEN(ruleGroupNameEN);
				rg.setRuleGroupWeight(Integer.parseInt(ruleGroupWeight));
				rg.setIsExclusive(Integer.parseInt(isExclusive));
				rg.setIsCommonGroup(Integer.parseInt(isCommonGroup));
				rg.setCreateUser(userName);
				rg.setCreateTime(Base.toTimeStamp());
				rg.setRuleGroupId(Long.parseLong(ruleGroupId));
				int rlt = rgd.updateRuleGroup(rg);
			    if (rlt != 1) {
			    	ar.setStatusFail();
			    	ar.setCode("4003");
			    	ar.setMsg("更新规则组失败");
			    } else {
			    	ar.setStatusSuccess();
					ar.setCode("4000");
					ar.setMsg("更新规则组‘"+ruleGroupNameCN+"’成功");
			    }

			} catch(Exception e) {
				
				ar.setStatusFail();
			    ar.setCode("4004");
				ar.setMsg(e.getMessage());
			}
		}
		
		ld.addLog(ar.toString());
		logger.info(ld.toString());
		
		return ar;
	}
}
