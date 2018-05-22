package indi.shuyu.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import indi.shuyu.actionresult.entity.ActionResult;
import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.JacksonUtils;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.common.validator.ValidItemPairResult;
import indi.shuyu.common.validator.ValidatorHelper;
import indi.shuyu.controller.RuleManageController;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.FormAttrDao;
import indi.shuyu.model.dao.RuleDao;
import indi.shuyu.model.entity.Rule;
import indi.shuyu.service.transactional.RuleActionTransaction;
import indi.shuyu.system.logger.LogDash;

@Controller
@RequestMapping("/rule/action")
public class RuleManageAction {
	
	private final static Logger logger = LoggerFactory.getLogger(RuleManageController.class);
	LogDash ld;
	
	@Autowired  
	private RuleActionTransaction rat;
	
	@Autowired  
	private FormAttrDao fad;
	
	@Autowired  
	private RuleDao rd; 
	
	@Autowired  
	private HttpSession session;  
	
	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult createRuleAndFormAttr(String rule, String isCommonGroup) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult();
		
		try {
			if (rule == null || rule.length() == 0) {
				
				ar.setStatusFail();
				ar.setMsg("规则为空！");
			} else {
				
				Rule r = JacksonUtils.toObject(rule, Rule.class);
				
				String userName = Base.getLoginUserName(session);
				Timestamp t = Base.toTimeStamp();
				
				
				ValidItemPairResult vpr = ValidatorHelper.validParams(r, gv);
				
				if (vpr.getValid()) {
					
					r.setCreateUser(userName);
					r.setCreateTime(t);
					
					boolean rlt = rat.createRule(r, isCommonGroup, rd, fad, ld);
					if (!rlt) {
						ar.setStatusFail();
						ar.setMsg("规则添加失败!");
					} else {
						ar.setStatusSuccess();
						ar.setMsg("规则添加成功!");
					}
				} else {
					ar.setParamValidError();
					ar.setMsg(ar.toString());
				}
			}
		} catch(Exception e) {
			ld.addLog(e);
			e.printStackTrace();
			ar.setStatusFail();
			ar.setMsg(e.toString());
		}
		
		ld.addLog(ar.toString());
		logger.info(JacksonUtils.toJSONString(ar));
		
		return ar;
	}
	
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult updateRuleAndFormAttr(String rule, String isCommonGroup) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult();
		
		try {
			if (rule == null || rule.length() == 0) {
				
				ar.setStatusFail();
				ar.setMsg("规则为空！");
			} else {
				
				Rule r = JacksonUtils.toObject(rule, Rule.class);
				
				String userName = Base.getLoginUserName(session);
				Timestamp t = Base.toTimeStamp();
				
				ValidItemPairResult vpr = ValidatorHelper.validParams(r, gv);
				
				if (vpr.getValid()) {
					
					r.setCreateUser(userName);
					r.setCreateTime(t);
					
					if (r.getFunctions() == null) r.setFunctions("");
					int rlt = rd.updateRule(r);
					
					if (rlt < 1) {
						ar.setStatusFail();
						ar.setMsg("规则更新失败！");
						ar.setCode("6009");
					} else {
						ar.setStatusSuccess();
						ar.setCode("6000");
						ar.setMsg("规则更新成功！");
					}
				} else {
					ar.setParamValidError();
					ar.setMsg(vpr.getMsg());
				}
			}
		} catch(Exception e) {
			ld.addLog(e);
			ar.setStatusFail();
			String msg = e.getMessage();
			if(msg!= null && msg.contains("违反唯一约束条件 ")) {
				ar.setCode("6001");
				ar.setMsg(msg);
			} else {
				ar.setCode("6002");
				ar.setMsg(msg);
			}
		}
		
		ld.addLog(ar.toString());
		logger.info(JacksonUtils.toJSONString(ar));
		
		return ar;
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult deleteRule(String ruleId, String isCommonGroup) throws Exception 
	{
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ActionResult ar = new ActionResult();
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		
		Rule r = new Rule();
		r.setRuleId(Long.parseLong(ruleId));
		ValidItemPairResult vpr = ValidatorHelper.validParams(r, gv);
		
		if (!vpr.getValid()) {
			ar.setParamValidError();
			ar.setMsg(vpr.getMsg());
			
		} else {
			
			try {

				boolean rlt = rat.deleteRule(ruleId, isCommonGroup, rd, fad, ld);
		
				if (rlt) {
					ar.setStatusSuccess();
					ar.setMsg("删除规则成功！"); 
				} else {
					ar.setStatusFail();
					ar.setCode("6009");
					ar.setMsg("删除规则失败，请先查看是否有相关联的表单，请先删除"); 
				}
			} catch(Exception e) {
				
				ar.setStatusFail();
				String msg = e.getMessage();
				if(msg!= null && msg.contains("违反唯一约束条件")) {
					ar.setCode("6001");
					ar.setMsg(msg);
				} else {
					ar.setCode("6002");
					ar.setMsg(msg);
				}
				
				ld.addLog(ar.toString());
			}
		}
		
		ld.addLog(ar.toString());
		logger.info(ld.toString());
		
		return ar;
	}
	
}
