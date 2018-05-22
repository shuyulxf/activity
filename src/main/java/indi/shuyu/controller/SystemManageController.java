package indi.shuyu.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import indi.shuyu.model.dao.PrizeDao;
import indi.shuyu.model.entity.Prize;

@Controller
@RequestMapping("/system")
public class SystemManageController {

	private final static Logger logger = LoggerFactory.getLogger(FormAttrManageController.class);
	
	@Autowired  
	private PrizeDao pd;  
	
	@RequestMapping(value = "reload", method = RequestMethod.GET)
	public ModelAndView reloadSystem(ModelMap model) {

       ModelAndView mv = new ModelAndView();
       mv.setViewName("system/reload"); 
       mv.addObject("subTitle", "系统刷新"); 
       
       return mv;
    }
	
	@RequestMapping(value = "test", method = RequestMethod.GET)
	public ModelAndView testSystem(ModelMap model) {

       ModelAndView mv = new ModelAndView();
       mv.setViewName("system/test"); 
       mv.addObject("subTitle", "调用接口"); 
       
       return mv;
    }
}
