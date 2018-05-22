package indi.shuyu.action;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import indi.shuyu.actionresult.entity.ActionResult;
import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.JacksonUtils;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.common.base.StringUtils;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.ActivityAttrDao;
import indi.shuyu.model.dao.AwardDao;
import indi.shuyu.model.dao.RuleDao;
import indi.shuyu.model.dao.UserDao;
import indi.shuyu.model.entity.Activity;
import indi.shuyu.model.entity.Award;
import indi.shuyu.service.transactional.ActivityActionTransaction;
import indi.shuyu.system.logger.LogDash;

@Controller
@RequestMapping("/manage/action")
public class ActivityManageAction {
	private final static Logger logger = LoggerFactory.getLogger(ActivityManageAction.class);
	LogDash ld;
	

	@Autowired  
	private ActivityAttrDao aad; 
	
	@Autowired  
	private RuleDao rd; 
	
	@Autowired  
	private AwardDao ad; 
	
	@Autowired  
	private UserDao ud; 
	
	
	@Autowired  
	private ActivityActionTransaction aat;
	
	@Autowired  
	private HttpSession session;  
	
	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult createActivity(@RequestParam(value="activityGeneralSetting", required=false) String ags,
									   @RequestParam(value="activityAwardSetting", required=false) String aas,
									   @RequestParam(value="activityReplySetting", required=false) String ars,
									   @RequestParam(value="ruleIds", required=false) String ruleIds,
									   String activityCommonSetting) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult();
		
		try {
			if (activityCommonSetting == null || activityCommonSetting.length() == 0) {
				
				ar.setStatusFail();
				ar.setMsg("活动数据为空,　请检查!");
			} else {
				
				String userName = Base.getLoginUserName(session);
				
				if (userName == null || userName.length() == 0) {
					ar.setStatusFail();
					ar.setMsg("用户未登录");
				} else {
					
					Timestamp t = Base.toTimeStamp();
					Activity a = new Activity();
					a.setRuleIds(ruleIds);
					a.setCreateUser(userName);
					a.setCreateTime(t);
					
					JSONObject commonLeft = new JSONObject();
					Boolean flag = false;
					JSONObject common = JSON.parseObject(activityCommonSetting);  
					for (Map.Entry<String, Object> entry : common.entrySet()) {
						String key = entry.getKey();
						Object value = entry.getValue();
						Method tmp = null;
						try {
						    tmp = a.getClass().getDeclaredMethod("set" + StringUtils.upperFirstChar(key), String.class);
						} catch(Exception e) {
							
						}
						
						if (tmp != null) {    
						    if (value != null) {
							   Object[] obj = new Object[1];
							    obj[0] = value;
							    tmp.invoke(a, obj); 
						    }
						} else {
							commonLeft.put(key, value);
							flag = true;
						}
			        }
					
					if (flag) a.setActivityCommonSetting(JSON.toJSONString(commonLeft));
					if (ags != null) a.setActivityGeneralSetting(ags);
					if (ags != null) {
						a.setActivityGeneralSetting(ags);
						// 设置activityType
						JSONObject agso = JSON.parseObject(ags);  
						a.setActivityType(agso.getString("activityType"));
					}
					if (aas != null) a.setActivityAwardSetting(aas);
					
					JSONObject aasObj = JSON.parseObject(aas);
					JSONArray awards = null;
					try {
						awards = aasObj.getJSONArray("activityAwardList");
					}
					catch(Exception e)  {
						ar.setStatusFail();
						ar.setMsg("奖品列表json解析失败");
					}
					
					Boolean rlt = aat.createActivity(a, awards, ruleIds, aad, rd, ad, gv);
					
					if (!rlt) {
						ar.setStatusFail();
						ar.setMsg("添加失败，未知原因");
					} else {
						ar.setStatusSuccess();
						ar.setMsg("创建活动成功!");
					}
				}
				
				
			}
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
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult updateActivity(@RequestParam(value="activityGeneralSetting", required=false) String ags,
									   @RequestParam(value="activityAwardSetting", required=false) String aas,
									   @RequestParam(value="activityReplySetting", required=false) String ars,
									   @RequestParam(value="ruleIds", required=false) String ruleIds,
									   String activityId,
									   String activityCommonSetting) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult();
		
		try {
			if (activityId == null || activityId.length() == 0 || activityCommonSetting == null || activityCommonSetting.length() == 0) {
				
				ar.setStatusFail();
				ar.setMsg("活动数据为空,　请检查!");
			} else {
				
				String role = Base.getLoginUserOrRole(session);
				String userName = Base.getLoginUserName(session);
				
				if (userName == null || userName.length() == 0) {
					ar.setStatusFail();
					ar.setMsg("用户未登录");
				} else {
				
					Activity a = new Activity();
					a.setRuleIds(ruleIds);
					a.setCreateUser(role);
					a.setActivityId(Long.parseLong(activityId));
					
					JSONObject commonLeft = new JSONObject();
					Boolean flag = false;
					JSONObject common = JSON.parseObject(activityCommonSetting);  
					for (Map.Entry<String, Object> entry : common.entrySet()) {
						String key = entry.getKey();
						Object value = entry.getValue();
						Method tmp = null;
						try {
						    tmp = a.getClass().getDeclaredMethod("set" + StringUtils.upperFirstChar(key), String.class);
						} catch(Exception e) {
							
						}
						
						if (tmp != null) {    
						    if (value != null) {
							   Object[] obj = new Object[1];
							    obj[0] = value;
							    tmp.invoke(a, obj); 
						    }
						} else {
							commonLeft.put(key, value);
							flag = true;
						}
			        }
					
					if (flag) a.setActivityCommonSetting(JSON.toJSONString(commonLeft));
					
					if (ags != null) {
						a.setActivityGeneralSetting(ags);
						// 设置activityType
						JSONObject agso = JSON.parseObject(ags);  
						a.setActivityType(agso.getString("activityType"));
					}
					
					if (ars != null) a.setActivityReplySetting(ars);
					if (aas != null) a.setActivityAwardSetting(aas);
					
					JSONObject aasObj = JSON.parseObject(aas);
					JSONArray awards = aasObj.getJSONArray("activityAwardList");
					
					Boolean rlt = aat.updateActivity(a, awards, ruleIds, aad, rd, ad, gv);
					
					if (!rlt) {
						ar.setStatusFail();
						ar.setMsg("更新失败，未知原因");
					} else {
						ar.setStatusSuccess();
						ar.setMsg("更新活动成功!");
					}
				}
				
				
			}
		} catch(Exception e) {
			ld.addLog(e);
			e.printStackTrace();
			ar.setStatusFail();
			ar.setMsg(e.getMessage());
		}
		
		ld.addLog(ar.toString());
		logger.info(JacksonUtils.toJSONString(ar));
		
		return ar;
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult deleteActivity(String activityId) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult();
		
		try {
			if (activityId == null || activityId.length() == 0) {
				
				ar.setStatusFail();
				ar.setMsg("活动数据为空!");
			} else {
				String role = Base.getLoginUserOrRole(session);
				String userName = Base.getLoginUserName(session);
				
				if (userName == null || userName.length() == 0) {
					ar.setStatusFail();
					ar.setMsg("用户未登录！");
				} else {
					Boolean rlt = aat.deleteActivity(activityId, role, aad, rd, ad, gv);
					
					if (!rlt) {
						ar.setStatusFail();
						ar.setMsg("删除失败，未知原因!");
					} else {
						ar.setStatusSuccess();
						ar.setMsg("删除活动成功!");
					}
				}
					
				
			}
		} catch(Exception e) {
			ld.addLog(e);
			e.printStackTrace();
			ar.setMsg(e.getMessage());
			ar.setStatusFail();
		}
		
		ld.addLog(ar.toString());
		logger.info(JacksonUtils.toJSONString(ar));
		
		return ar;
	}
	
	@RequestMapping(value="/finish/user/number", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult getTotalFinishUserNumForActivity(String listStr) throws Exception {
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult();
		
		boolean flag = true;
		JSONArray ids = null;
		try {
			ids = JSON.parseArray(listStr);
		} catch(Exception ex) {
			ar.setMsg("参数解析失败");
			ar.setStatusFail();
			ld.addLog(ex);
			flag = false;
			
		}
		
		if (flag && ids != null) {
			List<String> lists = new ArrayList<String>();
		
			int len = ids.size();
			List<HashMap<String, Integer>> datas = new ArrayList<HashMap<String, Integer>>();
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					lists.add(ids.getString(i));
				}
				
				try {
					datas = ud.selectTotalFinishJoinUserNumByActivityName(lists);
					ar.setData(JSONObject.toJSONString(datas));
				} catch(Exception ex) {
					ar.setMsg("查找失败");
					ar.setStatusFail();
					ld.addLog(ex);
				}
			} else {
				ar.setMsg("查找成功");
				ar.setStatusSuccess();
				ar.setData(JSON.toJSONString(datas));
			}
			
		} else {
			ar.setMsg("查找失败");
			ar.setStatusFail();
		}
		
		ld.addLog(JSONObject.toJSONString(ar));
		logger.info(ld.toString());
		
		return ar;
	}
	
	@RequestMapping(value="/award/user/number", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult getTotalAwarUserNumForActivity(String listStr) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult();
		
		boolean flag = true;
		JSONArray ids = null;
		try {
			ids = JSON.parseArray(listStr);
		} catch(Exception ex) {
			ar.setMsg("参数解析失败");
			ar.setStatusFail();
			ld.addLog(ex);
			flag = false;
			
		}
		
		if (flag && ids != null) {
			List<String> lists = new ArrayList<String>();
		
			int len = ids.size();
			List<HashMap<String, Integer>> datas = new ArrayList<HashMap<String, Integer>>();
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					lists.add(ids.getString(i));
				}
				
				try {
					datas = ud.selectTotalAwardUserNumByActivityName(lists);
					ar.setData(JSONObject.toJSONString(datas));
				} catch(Exception ex) {
					ar.setMsg("查找失败");
					ar.setStatusFail();
					ld.addLog(ex);
				}
			} else {
				ar.setMsg("查找成功");
				ar.setStatusSuccess();
				ar.setData(JSON.toJSONString(datas));
			}
			
		} else {
			ar.setMsg("查找失败");
			ar.setStatusFail();
		}
		
		ld.addLog(JSONObject.toJSONString(ar));
		logger.info(ld.toString());
		
		return ar;
	}
	
	@RequestMapping(value="/award/left", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult getTotalAwarLeftNumForActivity(String listStr) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		ActionResult ar = new ActionResult();
		
		boolean flag = true;
		JSONArray ids = null;
		try {
			ids = JSON.parseArray(listStr);
		} catch(Exception ex) {
			ar.setMsg("参数解析失败！");
			ar.setStatusFail();
			ld.addLog(ex);
			flag = false;
			
		}
		
		if (flag && ids != null) {
			List<Long> lists = new ArrayList<Long>();
			
			int len = ids.size();
			List<Award>  datas = new ArrayList<Award>();
			if (len > 0) {
				for (int i = 0; i < len; i++) {
					lists.add(ids.getLong(i));
				}
				
				try {
					datas = ad.selectLeftAwardNumByActivityName(lists);
					ar.setData(JSONObject.toJSONString(datas));
				} catch(Exception ex) {
					ar.setMsg("查找失败!");
					ar.setStatusFail();
					ld.addLog(ex);
				}
			} else {
				ar.setMsg("查找成功");
				ar.setStatusSuccess();
				ar.setData(JSON.toJSONString(datas));
			}
			
		} else {
			ar.setMsg("查找失败!");
			ar.setStatusFail();
		}
		
		ld.addLog(JSONObject.toJSONString(ar));
		logger.info(ld.toString());
		
		return ar;
	}
	
}
