package indi.shuyu.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.common.validator.ValidItemPairResult;
import indi.shuyu.common.validator.ValidatorItemPair;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.RuleGroupDao;
import indi.shuyu.model.entity.RuleGroup;

/*
* @author shuyu
* @des 规则组管理的controller定义，指定页面的访问地址
*/

@Controller
@RequestMapping("/rulegroup")
public class RuleGroupManageController {
	
	private final static Logger logger = LoggerFactory.getLogger(RuleGroupManageController.class);
	
	@Autowired  
	private RuleGroupDao rgd;  
	
	@Autowired  
	private HttpSession session;  
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public ModelAndView createRuleGroup(ModelMap model, @RequestParam(value="ruleGroupId", required=false) String ruleGroupId) throws JsonParseException, JsonMappingException, BeansException, IOException {

	   //GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		String subTitle = "规则组创建",
				ruleGroupIdError = "no",
				errorMsg = "",
				userName = "";
		
		RuleGroup rg = null;
		
		if (ruleGroupId != null && ruleGroupId.equals("")) {
			subTitle = "规则组编辑";
			ruleGroupIdError = "yes";
			errorMsg = "ruleGroupId为空，请检查";
		} else {
			userName = Base.getLoginUserName(session);
			if (ruleGroupId != null) {
				ValidItemPairResult vpr = validId(ruleGroupId, (GlobalStaticValues)SpringContextHelper.getBean("initialization"));
				if (vpr.getValid()) {
					subTitle = "规则组编辑";
					rg = rgd.selectByGroupId(ruleGroupId);
					if (rg == null) {
						ruleGroupIdError = "yes";
						errorMsg = "要编辑的规则不存在!";
					}
				}
				else {
					ruleGroupIdError = "yes";
					errorMsg = "要编辑的ruleGroupId校验失败";
				}
			}
	   }
		
       ModelAndView mv = new ModelAndView();
       mv.setViewName("ruleGroup/create"); 
       mv.addObject("subTitle", subTitle); 
       mv.addObject("ruleGroupIdError", ruleGroupIdError); 
       mv.addObject("errorMsg", errorMsg); 
       mv.addObject("rg", rg); 
       
       return mv;
    }
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView ruleGroupList(ModelMap model) {
	   
	   List<RuleGroup> groups = rgd.selectRuleGroupAll();
	   
       ModelAndView mv = new ModelAndView();
       mv.addObject("subTitle", "规则组列表"); 
       mv.addObject("groups", groups); 

       return mv;
    }
	
	private ValidItemPairResult validId(String ruleGroupId, GlobalStaticValues gv) throws JsonParseException, JsonMappingException, IOException {
		
		String[] ruleGroupIdTypes   = {"required", "number"};
		
		List<ValidatorItemPair> pairs = new ArrayList<ValidatorItemPair>();
		pairs.add(gv.getValidatorItempair(ruleGroupIdTypes, "ruleGroupId", ruleGroupId));
		
		return gv.validte(pairs);
	}
}
