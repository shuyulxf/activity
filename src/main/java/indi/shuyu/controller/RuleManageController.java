package indi.shuyu.controller;

import java.io.IOException;
import java.util.HashMap;
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
import indi.shuyu.common.validator.ValidatorHelper;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.RuleDao;
import indi.shuyu.model.entity.FormAttr;
import indi.shuyu.model.entity.Rule;
import indi.shuyu.model.entity.RuleGroup;
import indi.shuyu.system.logger.LogDash;

@Controller
@RequestMapping("/rule")
public class RuleManageController{
	
	private final static Logger logger = LoggerFactory.getLogger(RuleManageController.class);
	LogDash ld;
	
	@Autowired  
	private RuleDao rd;  
	
	@Autowired  
	private HttpSession session;  
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "createOrEdit", method = RequestMethod.GET)
	public ModelAndView ruleCreate(ModelMap model, @RequestParam(value="ruleId", required=false) String ruleId) throws JsonParseException, JsonMappingException, BeansException, IOException {
		
		// title set
		String  subTitle = "",
				ruleIdError = "no",
				errorMsg = "";
		
		
		List<RuleGroup> ruleGroups = null,
				        exRuleGroups = null;
		
		RuleGroup defaultGroup = null,
				  exDefaultGroup = null;
		
		Rule rule = null;
		
		String userName = "";
		
		
		
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		
		try {
			if (ruleId != null && ruleId.equals("")) {
				subTitle = "编辑";
				ruleIdError = "yes";
				errorMsg = "ruleId为空，请检查!";
			} else {
				
				userName = Base.getLoginUserName(session);
				// 搜索出系统中的ruleGroups供选择 
				ruleGroups = rd.selectRuleGroupAll(0);
				exRuleGroups = rd.selectRuleGroupAll(1);
				
				
				if (ruleGroups == null || ruleGroups.size() == 0) {
					ruleIdError = "yes";
					errorMsg = "规则组列表为空，请先添加规则组!";
				} else if(exRuleGroups == null || exRuleGroups.size() == 0) {
					ruleIdError = "yes";
					errorMsg = "互斥规则组列表为空，请先添加互斥规则组!";
				} else {
					
					defaultGroup = ruleGroups.get(0);
					exDefaultGroup = exRuleGroups.get(0);
					
					if (ruleId == null) {
						
						subTitle = "创建";
					} else {
						Rule r = new Rule();
						r.setRuleId(Long.parseLong(ruleId));
						ValidItemPairResult vpr = ValidatorHelper.validParams(r, (GlobalStaticValues)SpringContextHelper.getBean("initialization"));
						
						if (vpr.getValid()) {
							subTitle = "编辑";
							
							rule = rd.selectRuleByRuleId(ruleId);
							if (rule != null) {
								long rgId = rule.getRuleGroupId();
								long exRgId = rule.getExRuleGroupId();
								
								int i = 0, j = 0;
								int rs = ruleGroups.size();
								for (i = 0; i < rs; i++) {
									RuleGroup rg = ruleGroups.get(i); 
									if (rg.getRuleGroupId() == rgId) {
										defaultGroup = rg;
										break;
									}
								}
								
								int exs = exRuleGroups.size();
								for (j = 0; j < exs; j++) {
									RuleGroup erg = exRuleGroups.get(j); 
									if (erg.getRuleGroupId() == exRgId) {
										exDefaultGroup = erg;
										break; 
									}
								}
								
								if (i == rs || j == exs) {
									ruleIdError = "yes";
									errorMsg = "要编辑的rule所属的规则或者互斥规则组不存在，请检查!";
								} 
							} else {
								ruleIdError = "yes";
								errorMsg = "要编辑的rule不存在，请检查!";
							}
						} else {
							ruleIdError = "yes";
							errorMsg = "要编辑的ruleId校验失败!";
						}
					}
					
					
					if (defaultGroup == null) {
						ruleIdError = "yes";
						errorMsg = "该规则所属的规则组不存在，请检查!";
					}
					
				}
				
			}
		} catch(Exception e) {
			ld.addLog(e);
		}
		
		logger.info(ld.toString());
		
        ModelAndView mv = new ModelAndView();
        mv.setViewName("rules/rule");  
        mv.addObject("subTitle", subTitle);
        
        mv.addObject("ruleIdError", ruleIdError);
        mv.addObject("errorMsg", errorMsg);
        
        mv.addObject("ruleGroups", ruleGroups);
        mv.addObject("exRuleGroups", exRuleGroups);
        mv.addObject("defaultGroup", defaultGroup);
        mv.addObject("exDefaultGroup", exDefaultGroup);
     
        mv.addObject("rule", rule);
      
       return mv;
    }
	
	
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView ruleList(ModelMap model) {
		
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		
		List<Rule> rules = null,
				   rulesNoExGroup = null;
		
		String subTitle = "规则列表 ";
		try {
			
			rules = rd.selectRuleAll();
			rulesNoExGroup = rd.selectRuleExGroupNullAll();
			rules.addAll(rulesNoExGroup);
		} catch(Exception e) {
			ld.addLog(e);
		}
		
		logger.info(ld.toString());
		
        ModelAndView mv = new ModelAndView();
        mv.setViewName("rules/list"); 
        mv.addObject("subTitle", subTitle);
        mv.addObject("rules", rules);
      
       return mv;
    }
}
