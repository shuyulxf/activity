package indi.shuyu.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import indi.shuyu.actionresult.entity.ActionResult;
import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.JacksonUtils;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.common.http.SpringHttpHelper;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.ActivityAttrDao;
import indi.shuyu.model.dao.AwardDao;
import indi.shuyu.model.dao.FormDataDao;
import indi.shuyu.model.dao.PrizeDao;
import indi.shuyu.model.entity.Prize;
import indi.shuyu.system.logger.LogDash;

@Controller
@RequestMapping("/formdata/action")
public class FormAttrDataForRule {
	
	private final static Logger logger = LoggerFactory.getLogger(FormAttrDataForRule.class);
	LogDash ld;
	
	@Autowired  
	private FormDataDao fdd; 
	
	@Autowired  
	private PrizeDao pd; 
	
	@Autowired  
	private AwardDao ad; 
	
	@Autowired  
	private ActivityAttrDao aad; 
	
	
	@Autowired  
	private HttpSession session;  
	
	@RequestMapping(value="/applycode", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult getApplyCodeList() {
		
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult(); 
		List<String> list = null;
		try {
			list = fdd.selectApplyCode();
			if (list != null) {
				ar.setData(JacksonUtils.toJSONString(list));
				ar.setStatusSuccess();
				ar.setMsg("查询成功！");
			} else {
				ar.setStatusFail();
				ar.setMsg("查询失败！");
			}
		} catch(Exception e) {
			ar.setMsg(e.toString());
			ar.setStatusFail();
			ld.addLog(e);
		}
	    ld.addLog(JacksonUtils.toJSONString(ar));
		logger.info(ld.toString());
		return ar;
	}
	
	public static String getPermition(String url, String useId, boolean isAllCity,String resourceType,int level){
		
		JSONObject json = new JSONObject();
		
		json.put("useId", useId);
		json.put("type", resourceType);
		json.put("isAllCity", isAllCity);
		json.put("level", level);
		String permition = SpringHttpHelper.sendMessagePostHttp(url, JSON.toJSONString(json));
		return permition;
	}
	
	public static String getPermition(String useId, boolean isAllCity,String resourceType,int level){

		String url = "http://180.153.69.0:8082/GetPermitionWS/GetPermition";
		
		String textJson="{\"useId\":\""+useId+"\"" +
				",type:\""+resourceType+"\"" +
				",isAllCity:\""+isAllCity+"\"" +
				",level:"+level+
						"}";
		String permition = SpringHttpHelper.sendMessagePostHttp(url, textJson);
		return permition;
	}
	
	
	@RequestMapping(value="/province", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult getProvince(String form) {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ActionResult ar = new ActionResult(); 
		int LEVEL = 2;
		String resourceType = gv.getResourceType();
		boolean isAllCity = false;
		
		List<String> list = new ArrayList<String>();
		
		
		String url = gv.getThirdUrl("province");
		
		String userId = Base.getLoginUserName(session);
		JSONArray cityArray = null;
		try {
			String permit = getPermition(userId, isAllCity, resourceType, LEVEL);
			cityArray = JSON.parseArray(permit);
			int len = cityArray.size();
			if (len > 0) {
				JSONObject cityObj = cityArray.getJSONObject(0);
				if (cityObj != null) {
					JSONArray children = cityObj.getJSONArray("children");
					if (children != null) {
						int clen = children.size();
						for (int i = 0; i < clen; i++) {
							JSONObject child = children.getJSONObject(i);
							if (child != null && child.getString("text") != null) {
								list.add(child.getString("text"));
							}
						}
					}
				}
			}
		} catch(Exception ex) {
			
			ar.setStatusFail();
			ar.setMsg("查找该用户所管理的地市失败");
			ld.addLog(ex);
		}
	
		
	    ar.setData(JacksonUtils.toJSONString(list));
	    ar.setStatusSuccess();
	    ld.addLog(JacksonUtils.toJSONString(ar));
		logger.info(ld.toString());
		
		return ar;
	}
	
	@RequestMapping(value="/prizelist", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult getPrizeList() {
		
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult(); 
		List<Prize> list = null;
		
		try {
			list = pd.selectPrizeListForUser();
			
			JSONArray ja = new JSONArray();
			
			int len = list.size();
			for (int i = 0; i < len; i++) {
				JSONObject obj = new JSONObject();
				Prize p = list.get(i);
				obj.put("id",    p.getPrizeId());
				obj.put("value", p.getPrizeName());
				ja.add(obj);
			}
			if (list != null) {
				ar.setData(JacksonUtils.toJSONString(ja));
				ar.setStatusSuccess();
				ar.setMsg("查询成功！");
			} else {
				ar.setStatusFail();
				ar.setMsg("查询失败！");
			}
		} catch(Exception e) {
			ar.setMsg(e.toString());
			ar.setStatusFail();
			ld.addLog(e);
		}
		
		ld.addLog(JacksonUtils.toJSONString(ar));
		logger.info(ld.toString());
		return ar;
	}
	
	@RequestMapping(value="/activitymap", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult getActivityMap() {
		
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult(); 
		List<HashMap<Long, String>> list = null;
		String role = Base.getLoginUserOrRole(session);
		
		try {
			list = aad.selectAllActivityIdAndMapForUser(role);
			
			ar.setData(JacksonUtils.toJSONString(list));
			
		} catch(Exception e) {
			ar.setMsg(e.toString());
			ar.setStatusFail();
			ld.addLog(e);
		}
		
		ld.addLog(JacksonUtils.toJSONString(ar));
		logger.info(ld.toString());
		return ar;
	}
}
