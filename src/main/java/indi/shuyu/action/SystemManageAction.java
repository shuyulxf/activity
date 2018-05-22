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
import indi.shuyu.actionresult.entity.RuleExResult;
import indi.shuyu.common.base.JacksonUtils;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.core.rules.UpdateActivityRuleDRLFile;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.RuleDao;
import indi.shuyu.model.entity.User;
import indi.shuyu.system.logger.LogDash;
import indi.shuyu.wsitf.impl.ActivityProcessImpl;

@Controller
@RequestMapping("/system/action")
public class SystemManageAction {
	
	private final static Logger logger = LoggerFactory.getLogger(SystemManageAction.class);
	LogDash ld;
	
	@Autowired  
	private RuleDao rd; 
	
	@Autowired  
	private HttpSession session;  
	
	@Autowired
    private  ActivityProcessImpl actProImpl;
	
	@RequestMapping(value="/reload", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult reloadSystem(Boolean isReloadActivity, Boolean isReloadRuleDrl) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult();
		
		UpdateActivityRuleDRLFile rardf = new UpdateActivityRuleDRLFile();
		
		try {
			
			if (isReloadActivity) gv.setActivityMap();
			// 更新规则
			if (isReloadRuleDrl) rardf.initSystemAllDrlFiles(rd,  gv.getActivityMap(), ld);
			
			ar.setStatusSuccess();
			ar.setMsg("系统更新成功！");
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
	
	@RequestMapping(value="/test", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject testInterface(String params) throws Exception {
		
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		
		User user = JacksonUtils.toObject(params, User.class);
		String id = session.getId();
	    user.setSessionId(id);
	    user.setToken("a181d7ee-c652-4d0c-910d-3fdfea274b7c");
	    JSONObject rer = null;
	    String rlt = "";
	    try {
		    rlt = actProImpl.joinActivity(JSON.toJSONString(user));
		    rer = JSON.parseObject(rlt);
	    } catch(Exception e) {
	    	ld.addLog(e);
			e.printStackTrace();
	    }
        
        
		ld.addLog(rlt);
		logger.info(rlt);
		
		return rer;
	}
}
