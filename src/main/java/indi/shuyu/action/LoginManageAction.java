package indi.shuyu.action;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import indi.shuyu.actionresult.entity.ActionResult;
import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.RuleDao;
import indi.shuyu.system.logger.LogDash;

@Controller
@RequestMapping("/")
public class LoginManageAction {
	
	private final static Logger logger = LoggerFactory.getLogger(SystemManageAction.class);
	LogDash ld;
	
	@Autowired  
	private RuleDao rd; 
	
	@Autowired  
	private HttpSession session;  
	
	
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult reloadSystem(String userName) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult();
		
		boolean rlt = Base.logoutByUserName(session, userName);
		if (rlt) {
			ar.setStatusSuccess();
			ar.setMsg("注销成功");
			JSONObject obj = new JSONObject();
			obj.put("location", gv.getThirdUrl("login"));
			ar.setData(JSON.toJSONString(obj));
		} else {
			ar.setStatusFail();
			ar.setMsg("要注销的用户未登录或者不一致，请检查");
		}

		return ar;
	}
	
	
}

