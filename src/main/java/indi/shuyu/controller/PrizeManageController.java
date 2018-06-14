package indi.shuyu.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import indi.shuyu.common.base.Base;
import indi.shuyu.model.dao.PrizeDao;
import indi.shuyu.model.entity.Prize;

/*
* @author shuyu
* @des 奖品自定义的controller定义，指定页面的访问地址
*/

@Controller
@RequestMapping("/prize")
public class PrizeManageController {

	private final static Logger logger = LoggerFactory.getLogger(FormAttrManageController.class);
	
	@Autowired  
	private PrizeDao pd;  
	
	@Autowired  
	private HttpSession session;  
	
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public ModelAndView createRuleGroup(ModelMap model) {

       ModelAndView mv = new ModelAndView();
       mv.setViewName("prize/create"); 
       mv.addObject("subTitle", "奖品添加"); 
       
       return mv;
    }
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ModelAndView ruleGroupList(ModelMap model) {
	   
	   List<Prize> prizes = pd.selectPrizeAll();
	   
       ModelAndView mv = new ModelAndView();
       mv.addObject("subTitle", "奖品列表"); 
       mv.addObject("prizes", prizes); 

       return mv;
    }
}
