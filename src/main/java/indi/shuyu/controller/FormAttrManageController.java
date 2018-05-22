package indi.shuyu.controller;

import java.io.IOException;
import java.sql.Timestamp;
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
import indi.shuyu.common.base.JacksonUtils;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.common.validator.ValidItemPairResult;
import indi.shuyu.common.validator.ValidatorHelper;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.FormAttrDao;
import indi.shuyu.model.dao.RuleDao;
import indi.shuyu.model.entity.FormAttr;
import indi.shuyu.model.entity.Rule;
import indi.shuyu.model.entity.RuleGroup;
import indi.shuyu.system.logger.LogDash;

@Controller
@RequestMapping("/form")
public class FormAttrManageController {
	
	private final static Logger logger = LoggerFactory.getLogger(FormAttrManageController.class);
	LogDash ld;
	
	@Autowired  
	private FormAttrDao fad;  
	
	@Autowired  
	private RuleDao rd;  
	
	@Autowired  
	private HttpSession session;  
	
	@RequestMapping(value = "createOrEdit", method = RequestMethod.GET)
	public ModelAndView FormCreate(ModelMap model, @RequestParam(value="formAttrInfoId", required=false) String formAttrInfoId) throws JsonParseException, JsonMappingException, BeansException, IOException {

		// title set
		String  subTitle = "",
				formAttrInfoIdError = "no",
				errorMsg = "";
		
		FormAttr fa = null;
		
		String userName = "";
		
		List<RuleGroup> exRuleGroups = null;
		
		RuleGroup exDefaultGroup = null;
		
		HashMap<String, String> vdMap = null;
		
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		
		try {
			if (formAttrInfoId != null && formAttrInfoId.equals("")) {
				subTitle = "编辑";
				formAttrInfoIdError = "yes";
				errorMsg = "formAttrInfoId为空，请检查";
			} else {
				
				userName = Base.getLoginUserName(session);
				Timestamp t = Base.toTimeStamp();
				
				exRuleGroups   = rd.selectRuleGroupAll(1);
				exDefaultGroup = exRuleGroups.get(0);
				
				if (formAttrInfoId == null) {
					
					subTitle = "创建";
				} else {
					
					FormAttr f = new FormAttr();
					f.setFormAttrInfoId(Long.parseLong(formAttrInfoId));
					ValidItemPairResult vpr = ValidatorHelper.validParams(f, (GlobalStaticValues)SpringContextHelper.getBean("initialization"));
					
					if (vpr.getValid()) {
						subTitle = "编辑";
						f.setCreateUser(userName);
						f.setCreateTime(t);
						fa = fad.selectFormAttrByFormId(f);
						if (fa != null) {
							long faId = fa.getExRuleGroupId();
							
							int exs = exRuleGroups.size(), i;
							for (i = 0; i < exs; i++) {
								
								RuleGroup erg = exRuleGroups.get(i); 
								if (erg.getRuleGroupId() == faId) {
									exDefaultGroup = erg;
									break; 
								}
							}
							
							vdMap = getVMap(fa.getFormAttrNameValidation());
						} else {
							formAttrInfoIdError = "yes";
							errorMsg = "表单差查找失败!";
						}
					} else {
						formAttrInfoIdError = "yes";
						errorMsg = "要编辑的formAttrInfoId校验失败";
					}
				}
			}
		} catch(Exception e) {
			ld.addLog(e);
		}
		
		ld.addLog(errorMsg);
		logger.info(ld.toString());
		ModelAndView mv = new ModelAndView();
        mv.setViewName("forms/formAttr");  
        mv.addObject("subTitle", subTitle);
        mv.addObject("formAttrInfoIdError", formAttrInfoIdError);
        mv.addObject("errorMsg", errorMsg);
        mv.addObject("form", fa);
        mv.addObject("exDefaultGroup", exDefaultGroup);
        mv.addObject("exRuleGroups", exRuleGroups);
        mv.addObject("vdMap", vdMap);
        
       return mv;
	}
	
	private HashMap<String, String> getVMap(String s) {
		
		HashMap<String, String> rlt = new HashMap<String, String>();
		
		if (s == null || s.length() < 1) return rlt;
		
		String[] vs = s.split(" ");
		int len = vs.length;
		for (int i = 0; i < len; i++) {
			String v = vs[i];
			
			if (v != null && v.length() > 0) {
				if (v.indexOf("=") != -1) {
					rlt.put(v.split("=")[0], v);
				} else {
					rlt.put(v, v);
				}
			}
		}
		
		return rlt;
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView ruleList(ModelMap model) {
		
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		
		String userName = Base.getLoginUserName(session);
		
		List<FormAttr> fas = null; 
		
		String subTitle = "规则列表 ";
		try {
			
			fas = fad.selectFormsAll();
			
		} catch(Exception e) {
			ld.addLog(e);
		}
		
		logger.info(ld.toString());
		
        ModelAndView mv = new ModelAndView();
        mv.setViewName("forms/list"); 
        mv.addObject("subTitle", subTitle);
        mv.addObject("forms", fas);
      
       return mv;
    }
}
