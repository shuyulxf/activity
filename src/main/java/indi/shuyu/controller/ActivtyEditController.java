package indi.shuyu.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.JacksonUtils;
import indi.shuyu.core.rules.UpdateActivityRuleDRLFile;
import indi.shuyu.model.dao.ActivityAttrDao;
import indi.shuyu.model.dao.FormAttrDao;
import indi.shuyu.model.entity.Activity;
import indi.shuyu.model.entity.FormAttr;
import indi.shuyu.model.entity.Search;

/*
* @author shuyu
* @des 活动管理模块的controller定义，指定页面的访问地址
*/

@Controller
@RequestMapping("/")
public class ActivtyEditController {
	private final static Logger logger = LoggerFactory.getLogger(ActivtyEditController.class);
	
	@Autowired  
	private ActivityAttrDao ad;  
	
	@Autowired  
	private FormAttrDao fad;  
	
	@Autowired  
	private HttpSession session;  


	/*
    * @url /activity/create
    * @params 
    *  activityId 活动的ID
    * @des 活动创建或编辑，若activityId为空，为活动创建；否则，为活动编辑
    */
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public ModelAndView editActivity(ModelMap model, @RequestParam(value="activityId", required=false) String activityId) {

	   String  subTitle = "编辑",
			   activityIdError = "",
			   errorMsg = "",
			   role = "";
	   
       //保存活动模型的四种类型的属性列表
	   List<FormAttr> acsFas = null,
			          agsFas = null,
			          aasFas = null,
			          aesFas = null;

	   Activity activity = null;


       if (activityId != null && activityId.equals("")) {
			activityIdError = "yes";
			errorMsg = "activityId为空，请检查";
		} else {
			
            // 获取当前登录用户的角色
			role = Base.getLoginUserOrRole(session);
            
            // 获取活动模型的通用属性列表、活动参与属性列表、奖品发放属性列表
			acsFas = fad.selectFormAttrByType("activityCommonSetting");
			agsFas = fad.selectFormAttrByType("activityGeneralSetting");
			aasFas = fad.selectFormAttrByType("activityAwardSetting");
			
            //判断当前是创建还是编辑
			if (activityId == null) {
				subTitle = "创建";
			} else {
				subTitle = "编辑";
				// 编辑时根据activityId获取活动实体记录
				activity = ad.selectActivityById(activityId, role);
			} 
			
		}
	
       // 绑定该url所要展示的页面，路径为./views/activity/create
       // 将获取的数据输出到页面，以供页面使用
       ModelAndView mv = new ModelAndView();
       mv.setViewName("activity/create");  
       mv.addObject("subTitle", subTitle);
       mv.addObject("activity", activity);
       mv.addObject("acsFas", acsFas);
       mv.addObject("agsFas", agsFas);
       mv.addObject("aasFas", aasFas);
       mv.addObject("aesFas", aesFas);
       
     
      return mv;
    }
	
    // 枚举类，用于判断当前访问的活动列表，包括已上线、未上线、已下线等三个
	private enum ListType {
		ONLINE("online", 0), WILLONLINE("willonline", 1), OFFLINE("offline", 2);
	     
		private String type;
		private int index;
		private ListType( String type , int index ){
			this.type  = type;
			this.index = index;
	    }
		
		public String getType() {
			return type;
		}
		public void setType(String t) {
			this.type = t;
		}
		
		public int getIndex() {
			return index;
		}
		public void setType(int i) {
			this.index = i;
		}
		
		 // 根据value返回枚举类型,主要在switch中使用  
	    public static ListType getByValue(String type) {  
	    	
	        for (ListType listType : values()) {  
	        
	            if (listType.getType().equals(type)) {  
	                return listType;  
	            }  
	        }  
	        return null;  
	    }  
	}
	
	
	
    /*
    * @url /activity/manage/{type}
    *    type可以为 online、willonline、offline中任意一个
    * @params 
    *  paramStr 筛选条件，是Json String类型
    * @des 活动列表页
    */
    @RequestMapping(value = "manage/{type}", method = RequestMethod.GET)
    public ModelAndView activityList(@PathVariable String type, 
    								 @RequestParam(value="paramStr", required=false) String paramStr) throws IOException
    
    {
    	String subTitle = "",
    		   role = Base.getLoginUserOrRole(session);
    	
    	long total = 0;
    	int	 page  = 0;
    	List<Activity> lists = null;
    	
    	// 搜索条件参数解析，用于查找符合筛选条件的数据
    	Search params = new Search();
    	
    	if (paramStr != null && paramStr.length() > 0) {
    		params = JacksonUtils.toObject(paramStr, Search.class);
    		page = params.getPage();
    	}
    	params.setCreateUser(role);
    	Timestamp t = Base.toTimeStamp();
    	params.setSysTime(t);

        // 不同的type，返回不同的活动列表。
    	switch(ListType.getByValue(type)) {
    	
    		case ONLINE:
    			subTitle = "已上线活动列表";
                // 设置搜索参数中状态为已上线
    			params.setStatus(0);
                // 获取符合筛选条件的活动的总数
    			total = ad.selectActivityTotalNumber(params);
                // 获取活动列表
    			lists = ad.selectActivityList(params);
    			break;
    		case WILLONLINE:
    			subTitle = "未上线活动列表";
    			params.setStatus(1);
    			total = ad.selectActivityTotalNumber(params);
    			lists = ad.selectActivityList(params);
    			break;
    		case OFFLINE:
    			subTitle = "已下线活动列表";
    			params.setStatus(2);
    			total = ad.selectActivityTotalNumber(params);
    			lists = ad.selectActivityList(params);
    			break;
    		default:
    			break;
    	}


    	// 绑定该url所要展示的页面，路径为./views/activity/list
        // 将获取的数据输出到页面，以供页面使用
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("activity/list");  
    	mv.addObject("type", type);
        mv.addObject("subTitle", subTitle); 
        mv.addObject("paramStr", paramStr);
        mv.addObject("total", total);
        mv.addObject("page", page == 0 ? 1 : page);
        mv.addObject("lists", lists);
        
        return mv;
    }
    
    //设置url=/activity/index为系统的首页，并设置默认页面为已上线活动列表页
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public ModelAndView index() throws IOException
    
    {
    	String subTitle = "",
    		   userName = Base.getLoginUserName(session);
    	
    	
    	long total = 0,
    		 page  = 0;
    	List<Activity> lists = null;
    	
    	
    	Search params = new Search();
    	
    	params.setCreateUser(userName);
    	Timestamp t = Base.toTimeStamp();
    	params.setSysTime(t);
    	String type = "online";
    	subTitle = "已上线活动列表!";
		params.setStatus(0);
		total = ad.selectActivityTotalNumber(params);
		lists = ad.selectActivityList(params);
    	
    	ModelAndView mv = new ModelAndView();
    	mv.setViewName("activity/list");  
    	mv.addObject("type", type);
        mv.addObject("subTitle", subTitle); 
        mv.addObject("total", total);
        mv.addObject("page", page == 0 ? 1 : page);
        mv.addObject("lists", lists);
        
        return mv;
    }
}


