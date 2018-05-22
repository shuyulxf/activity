package indi.shuyu.action;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import indi.shuyu.actionresult.entity.ActionResult;
import indi.shuyu.common.base.Base;
import indi.shuyu.common.base.SpringContextHelper;
import indi.shuyu.common.validator.ValidItemPairResult;
import indi.shuyu.common.validator.ValidatorItemPair;
import indi.shuyu.controller.RuleManageController;
import indi.shuyu.initialization.GlobalStaticValues;
import indi.shuyu.model.dao.PrizeDao;
import indi.shuyu.model.entity.Prize;
import indi.shuyu.system.logger.LogDash;

@Controller
@RequestMapping("/prize/action")
public class PrizeManageAction {
	
	private final static Logger logger = LoggerFactory.getLogger(RuleManageController.class);
	LogDash ld;
	
	@Autowired  
	private PrizeDao pd;  
	
	@Autowired  
	private HttpSession session;  
	@RequestMapping(value="/create", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult createPrize(String prizeName) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		
		ActionResult ar = new ActionResult();
		ValidItemPairResult vpr = validPrizeName(prizeName, gv);
		ld = (LogDash)SpringContextHelper.getBean("logDash");
		
		if (!vpr.getValid()) {
			
			ar.setParamValidError();
			ar.setMsg(vpr.getMsg());
		} else {
			
			try {
				
				String userName = Base.getLoginUserName(session);

				int rlt = pd.insertPrize(prizeName, userName, Base.toTimeStamp());
				if (rlt == 1) {
					ar.setStatusSuccess();
					ar.setMsg("活动奖品添加成功"); 
				} else {
					ar.setStatusFail();
					ar.setCode("5009");
					ar.setMsg("活动奖品添加失败，未知原因"); 
				}
			} catch(Exception e) {
				
				ar.setStatusFail();
				String msg = e.getMessage();
				if(msg.contains("违反唯一约束条件 ")) {
					ar.setCode("5001");
					ar.setMsg("活动奖品添加失败，重复的活动奖品名称");
				} else {
					ar.setCode("5002");
					ar.setMsg(msg);
				}
				
				ld.addLog(ar.toString());
			}
		}
		ld.addLog(ar.toString());
		logger.info(ld.toString());
		
		return ar;
	}
	
	private ValidItemPairResult validPrizeId(String prizeId, GlobalStaticValues gv) throws JsonParseException, JsonMappingException, IOException {
		
		String[] prizeIdTypes   = {"required", "number"};
		
		List<ValidatorItemPair> pairs = new ArrayList<ValidatorItemPair>();
		pairs.add(gv.getValidatorItempair(prizeIdTypes, "prizeId", prizeId));
		
		return gv.validte(pairs);
	}
	
	private ValidItemPairResult validPrizeName(String prizeName, GlobalStaticValues gv) throws JsonParseException, JsonMappingException, IOException {


		String[] prizeNameTypes   = {"required", "text", "maxlength", "minlength"};
		
		HashMap<String, String> variables = new HashMap<String, String>();
		variables.put("maxlength", "10");
		variables.put("minlength", "2");
		
		List<ValidatorItemPair> pairs = new ArrayList<ValidatorItemPair>();
		pairs.add(gv.getValidatorItempair(prizeNameTypes, "prizeName", prizeName, variables));
		
		return gv.validte(pairs);
	}
	
	
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult deleteRuleGroup(String prizeId) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		
		ActionResult ar = new ActionResult();
		ValidItemPairResult vpr = validPrizeId(prizeId, gv);
		
		if (!vpr.getValid()) {
			
			ar.setParamValidError();
			ar.setMsg(vpr.getMsg());
			
		}  else {
			try {
				
				ld = (LogDash)SpringContextHelper.getBean("logDash");
				
				
				int rlt = pd.deletePrize(prizeId);

				
				if (rlt == 1) {
					ar.setStatusSuccess();
					ar.setMsg("删除活动奖品成功"); 
				} else {
					ar.setStatusFail();
					ar.setCode("5008");
					ar.setMsg("删除活动奖品失败，未知原因"); 
				}
			} catch(Exception e) {
				
				ar.setStatusFail();
				ar.setCode("5008");
				ar.setMsg(e.getMessage());
			}
		}
		
		ld.addLog(ar.toString());
		logger.info(ld.toString());
		
		return ar;
	}
	
	@RequestMapping(value="/edit/get", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult editRuleGroupGet(String prizeId) throws Exception {
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		
		ActionResult ar = new ActionResult();
		ValidItemPairResult vpr = validPrizeId(prizeId, gv);
		
		if (!vpr.getValid()) {
			
			ar.setParamValidError();
			ar.setMsg(vpr.getMsg());
			
		}  else {
			try {
				
				ld = (LogDash)SpringContextHelper.getBean("logDash");
				
				String userName = Base.getLoginUserName(session);
				
				
				Prize rg = pd.selectByPrizeId(prizeId);
			    if (rg == null) {
			    	ar.setStatusFail();
			    	ar.setCode("5002");
			    	ar.setMsg("按活动奖品ID查找奖品失败！");
			    } else {
			    	ar.setStatusSuccess();
					ar.setCode("5000");
					ar.setMsg("按活动奖品ID查找奖品成功！");
			    	ar.setData(rg.toString());
			    }

			} catch(Exception e) {
				
				ar.setStatusFail();
			    ar.setCode("5007");
				ar.setMsg(e.getMessage());
			}
		}
		
		ld.addLog(ar.toString());
		logger.info(ld.toString());
		
		return ar;
	}
	
	@RequestMapping(value="/edit/save", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult editRuleGroupSave(String prizeId, String prizeName) throws Exception {
		
		ActionResult ar = new ActionResult();
		
		GlobalStaticValues gv = (GlobalStaticValues)SpringContextHelper.getBean("initialization");
		
		ValidItemPairResult vpr = validPrizeId(prizeId, gv);
		ValidItemPairResult vpr1 = validPrizeName(prizeName, gv);
		
		if (!vpr.getValid() || !vpr1.getValid()) {
			
			ar.setParamValidError();
			if (vpr.getValid())	ar.setMsg(vpr1.getMsg());
			else 	ar.setMsg(vpr.getMsg());
		}  else {
			try {
				
				ld = (LogDash)SpringContextHelper.getBean("logDash");
				
				
				int rlt = pd.updatePrize(prizeId, prizeName);
			    if (rlt != 1) {
			    	ar.setStatusFail();
			    	ar.setCode("5003");
			    	ar.setMsg("更新规则组失败");
			    } else {
			    	ar.setStatusSuccess();
					ar.setCode("5000");
					ar.setMsg("更新活动奖品‘"+prizeName+"’成功！");
			    }

			} catch(Exception e) {
				
				ar.setStatusFail();
			    ar.setCode("5004");
				ar.setMsg(e.getMessage());
			}
		}
		
		ld.addLog(ar.toString());
		logger.info(ld.toString());
		
		return ar;
	}
	
}
