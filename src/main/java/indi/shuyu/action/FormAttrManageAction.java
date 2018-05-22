package indi.shuyu.action;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;

import javax.annotation.Resource;
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
import indi.shuyu.common.base.StringUtils;
import indi.shuyu.common.validator.ValidItemPairResult;
import indi.shuyu.common.validator.ValidatorHelper;
import indi.shuyu.controller.RuleManageController;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.FormAttrDao;
import indi.shuyu.model.dao.RuleDao;
import indi.shuyu.model.entity.FormAttr;
import indi.shuyu.system.logger.LogDash;

@Controller
@RequestMapping("/form/action")
public class FormAttrManageAction {
	
	private final static Logger logger = LoggerFactory.getLogger(FormAttrManageAction.class);
	LogDash ld;
	

	@Autowired  
	private FormAttrDao fad; 
	
	
	@Autowired  
	private RuleDao rd; 
	
	@Autowired  
	private HttpSession session;  
	
	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult createFormAttr(String form, String attrs) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult();
		
		try {
			if (form == null || form.length() == 0) {
				
				ar.setStatusFail();
				ar.setMsg("规则表单为空！");
			} else {
				
				FormAttr f = JacksonUtils.toObject(form, FormAttr.class);


				String userName = Base.getLoginUserName(session);
				Timestamp t = Base.toTimeStamp();
				
				
				
				ValidItemPairResult vpr = ValidatorHelper.validParams(f, gv);
				
				if (vpr.getValid()) {
					
					f.setCreateUser(userName);
					f.setCreateTime(t);
					
					long[] ruleIdArr = null;
					long ex = f.getExRuleGroupId();
					
					String ruleIds = "";
					Boolean ruleIdError = false;
					if (ex != 0) {
						
			    		ruleIdArr = rd.selectRuleIdsByGroupId(ex);
			    		if (ruleIdArr != null && ruleIdArr.length > 0) {
			    			ruleIds = StringUtils.toString(ruleIdArr, ",");;
			    		} 
			    	} else {
			    		ar.setStatusFail();
						ar.setMsg("表单字段绑定的互斥规则组中的规则不存在，请先添加不存在失败！");
						ruleIdError = true;
			    	}
					
				    if (!ruleIdError) {
				    	
				    	f.setRuleIds(ruleIds);
						int rlt = fad.insertFormAttr(f);
						
						if (rlt < 1) {
							ar.setStatusFail();
							ar.setMsg("表单添加失败");
						} else {
							ar.setStatusSuccess();
							ar.setMsg("表单添加成功");
						}
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
			ar.setMsg(e.getMessage());
		}
		
		ld.addLog(ar.toString());
		logger.info(JacksonUtils.toJSONString(ar));
		
		return ar;
	}
	
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult updateFormAttr(String form, String attrs) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult();
		
		try {
			if (form == null || form.length() == 0) {
				
				ar.setStatusFail();
				ar.setMsg("表单参数为空");
			} else {
				
				FormAttr f = JacksonUtils.toObject(form, FormAttr.class);
				if (attrs != null && attrs.length() > 0)	f.setFormNameTypeAttrs(attrs);
				String userName = Base.getLoginUserName(session);
				Timestamp t = Base.toTimeStamp();
				
				ValidItemPairResult vpr = ValidatorHelper.validParams(f, gv);
				
				if (vpr.getValid()) {
					
					f.setCreateUser(userName);
					f.setCreateTime(t);
					
					long[] ruleIdArr = null;
					long ex = f.getExRuleGroupId();
					
					String ruleIds = "";
					Boolean ruleIdError = false;
					if (ex != 0) {
						
			    		ruleIdArr = rd.selectRuleIdsByGroupId(ex);
			    		if (ruleIdArr != null && ruleIdArr.length > 0) {
			    			ruleIds = StringUtils.toString(ruleIdArr, ",");;
			    		} 
			    	} else {
			    		ar.setStatusFail();
						ar.setMsg("表单字段绑定的互斥规则组中的规则不存在，请先添加不存在失败！");
						ruleIdError = true;
			    	}
					
					if (!ruleIdError) {
						f.setCreateUser(userName);
					    f.setRuleIds(ruleIds);
						int rlt = fad.updateFormAttr(f);
						if (rlt < 1) {
							ar.setStatusFail();
							ar.setMsg("表单更新失败！");
							ar.setCode("6009");
						} else {
							ar.setStatusSuccess();
							ar.setCode("6000");
							ar.setMsg("表单更新成功");
						}
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
		logger.info(JacksonUtils.toJSONString(ld.toString()));
		
		return ar;
	}
	

	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult deleteFormAttr(String formAttrInfoId) throws Exception 
	{
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ActionResult ar = new ActionResult();
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		
		if (formAttrInfoId != null) {
			FormAttr f = new FormAttr();
			f.setFormAttrInfoId(Long.parseLong(formAttrInfoId));
			ValidItemPairResult vpr = ValidatorHelper.validParams(f, gv);
			
			if (!vpr.getValid()) {
				ar.setParamValidError();
				ar.setMsg(vpr.getMsg());
				
			} else {
				
				try {
					
					String userName = Base.getLoginUserName(session);
	
					int rlt = fad.deleteFormAttrById(formAttrInfoId);
			
					if (rlt == 1) {
						ar.setStatusSuccess();
						ar.setMsg("删除表单成功！"); 
					} else {
						ar.setStatusFail();
						ar.setCode("6009");
						ar.setMsg("删除表单失败，未知原因"); 
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
		} else {
			ar.setStatusFail();
			ar.setCode("6009");
			ar.setMsg("要删除的表单不存在"); 
		}
		
		ld.addLog(ar.toString());
		logger.info(ld.toString());
		
		return ar;
	}
	
	
}
