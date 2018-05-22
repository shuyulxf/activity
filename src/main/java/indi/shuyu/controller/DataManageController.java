package indi.shuyu.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.JacksonUtils;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.model.dao.UserDao;
import indi.shuyu.model.entity.Data;
import indi.shuyu.model.entity.Search;
import indi.shuyu.model.entity.User;
import indi.shuyu.system.logger.LogDash;


@Controller
@RequestMapping("")
public class DataManageController {
	
	private final static Logger logger = LoggerFactory.getLogger(DataManageController.class);
	LogDash ld;
	
	@Autowired  
	private UserDao ud;  
	
	@Autowired  
	private HttpSession session; 
	
	@RequestMapping(value = "data/activity", method = RequestMethod.GET)
	public ModelAndView dataActivity(ModelMap model, @RequestParam(value="paramStr", required=false) String paramStr) {
		
	   ld = (LogDash)SpringContextHelper.getBean("logDash");
	   
	   String  subTitle = "活动发奖明细",
			   role = Base.getLoginUserOrRole(session);
	   
	   long total = 0;
	   int page = 1;
	   
	   Search params = new Search();
   	
	   if (paramStr != null && paramStr.length() > 0) {
	   	   params = JacksonUtils.toObject(paramStr, Search.class);
	   	   page = params.getPage();
	   }
	   params.setCreateUser(role);
	   Timestamp t = Base.toTimeStamp();
	   params.setSysTime(t);
	   
	   List<Data> lists = null;
	   try {
		   lists = ud.selectUserEarnAwardList(params);
		   total = ud.selectTotalNumUserEarnAward(params);
	   } catch(Exception ex) {
			ld.addLog(ex);
	   }
	   
       ModelAndView mv = new ModelAndView();
       mv.setViewName("datas/activity");  
       mv.addObject("subTitle", subTitle);
       mv.addObject("total", total);
       mv.addObject("page", page);
       mv.addObject("lists", lists);
       mv.addObject("paramStr", paramStr);
       
       logger.info(ld.toString());
       
       return mv;
    }
	
	@RequestMapping(value = "data/user", method = RequestMethod.GET)
	public ModelAndView dataUser(ModelMap model, @RequestParam(value="paramStr", required=false) String paramStr) {

	   ld = (LogDash)SpringContextHelper.getBean("logDash");
	   
	   String  subTitle = "单用户获奖明细",
			   role = Base.getLoginUserOrRole(session);
	   
	   long total = 0;
	   int page = 1;
	   
	   Search params = new Search();
   	
	   if (paramStr != null && paramStr.length() > 0) {
	   	   params = JacksonUtils.toObject(paramStr, Search.class);
	   	   page = params.getPage();
	   }
	   params.setCreateUser(role);
	   Timestamp t = Base.toTimeStamp();
	   params.setSysTime(t);
	   
	   List<Data> lists = null;
	   try {
		   lists = ud.selectUserEarnAwardList(params);
		   total = ud.selectTotalNumUserEarnAward(params);
	   } catch(Exception ex) {
			ld.addLog(ex);
	   }
	   
       ModelAndView mv = new ModelAndView();
       mv.setViewName("datas/user");  
       mv.addObject("subTitle", subTitle);
       mv.addObject("total", total);
       mv.addObject("page", page);
       mv.addObject("lists", lists);
       mv.addObject("paramStr", paramStr);
       
       logger.info(ld.toString());
       
       return mv;
    }
	
	@RequestMapping(value = "statistic/activity", method = RequestMethod.GET)
	public ModelAndView statisticActivity(ModelMap model, @RequestParam(value="paramStr", required=false) String paramStr) {
		
	   ld = (LogDash)SpringContextHelper.getBean("logDash");
	   String  subTitle = "活动数据统计！",
			   role = Base.getLoginUserOrRole(session);
	   
	   long total = 0;
	   int page = 1;
	   
	   Search params = new Search();
	   List<Data> lists = null;
	   
	   if (paramStr != null && paramStr.length() > 0) {
	   	   params = JacksonUtils.toObject(paramStr, Search.class);
	   	   page = params.getPage();
	   	   params.setCreateUser(role);
		   Timestamp t = Base.toTimeStamp();
		   params.setSysTime(t);
		   try {
			  lists = ud.selectStatisticInActivityDimension(params);
			  total = ud.selectTotalNumForStatisticInActivityDimension(params);
		   } catch(Exception ex) {
				ld.addLog(ex);
		   }
	   }
	  

       ModelAndView mv = new ModelAndView();
       mv.setViewName("statistic/activity");  
       mv.addObject("subTitle", subTitle);
       mv.addObject("total", total);
       mv.addObject("page", page);
       mv.addObject("lists", lists);
       mv.addObject("paramStr", paramStr);
       
       logger.info(ld.toString());
       
       return mv;
    }
	
	@RequestMapping(value = "statistic/prize", method = RequestMethod.GET)
	public ModelAndView statisticPrize(ModelMap model, @RequestParam(value="paramStr", required=false) String paramStr) {
	
	   ld = (LogDash)SpringContextHelper.getBean("logDash");
	   String  subTitle = "奖品数据统计",
			   role = Base.getLoginUserOrRole(session);
	   
	   
	   long total = 0;
	   int page = 1;
	   
	   Search params = new Search();
	   List<Data> lists = null;
   	
	   if (paramStr != null && paramStr.length() > 0) {
		   
	   	   params = JacksonUtils.toObject(paramStr, Search.class);
	   	   page = params.getPage();
	   	   params.setCreateUser(role);
	   	   
		   Timestamp t = Base.toTimeStamp();
		   params.setSysTime(t);
		   
		   
		   try {
			  total = ud.selectTotalNumForStatisticInPrizeDimension(params);
			  lists = ud.selectStatisticInPrizeDimension(params);
		   } catch(Exception ex) {
				ld.addLog(ex);
		   }
	   }
	   

       ModelAndView mv = new ModelAndView();
       mv.setViewName("statistic/prize");  
       mv.addObject("subTitle", subTitle);
       mv.addObject("total", total);
       mv.addObject("lists", lists);
       mv.addObject("page", page);
       mv.addObject("paramStr", paramStr);
       
       return mv;
    }
	
	@RequestMapping(value = "statistic/user", method = RequestMethod.GET)
	public ModelAndView statisticUser(ModelMap model, @RequestParam(value="paramStr", required=false) String paramStr) {

	   ld = (LogDash)SpringContextHelper.getBean("logDash");
	   String  subTitle = "用户数据统计！",
			   role = Base.getLoginUserOrRole(session);
	   
	   
	   long total = 0;
	   int page = 1;
	   
	   Search params = new Search();
	   List<Data> lists = null;
   	
	   if (paramStr != null && paramStr.length() > 0) {
		   
	   	   params = JacksonUtils.toObject(paramStr, Search.class);
	   	   page = params.getPage();
	   	   
	   	   params.setCreateUser(role);
		   Timestamp t = Base.toTimeStamp();
		   params.setSysTime(t);
		  
		   try {
			   lists = ud.selectStatisticInUserDimension(params);
			   total = ud.selectTotalNumForStatisticInUserDimension(params);
		   } catch(Exception ex) {
				ld.addLog(ex);
		   }
	   }
	   
	   
       ModelAndView mv = new ModelAndView();
       mv.setViewName("statistic/user");  
       mv.addObject("subTitle", subTitle);
       mv.addObject("total", total);
       mv.addObject("lists", lists);
       mv.addObject("page", page);
       mv.addObject("paramStr", paramStr);
       
       return mv;
    }
}
