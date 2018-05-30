define(function(require,exports,module) {
	"use strict";
	
	var base = "/activity/";
	
	module.exports = {
		base: base,

		
		createRuleGroup        : base + "rulegroup/create",
		ruleGroupList          : base + "rulegroup/list",
		ruleGroupActionCreate  : base + "rulegroup/action/create",
		ruleGroupActionDel     : base + "rulegroup/action/delete",
		ruleGroupActionEditGet : base + "rulegroup/action/edit/get",
		ruleGroupActionEditSave: base + "rulegroup/action/edit/save",
		
		createPrize: base + "prize/create",
		prizeList  : base +"prize/list",
		prizeActionCreate  : base + "prize/action/create",
		prizeActionDel     : base + "prize/action/delete",
		prizeActionEditGet : base + "prize/action/edit/get",
		prizeActionEditSave: base + "prize/action/edit/save",
		
		createOrEditRule: base + "rule/createOrEdit",
		ruleList  : base +"rule/list",
		ruleActionCreate  : base + "rule/action/create",
		ruleActionDel     : base + "rule/action/delete",
		ruleActionEditSave: base + "rule/action/update",
		
		createOrEditForm: base + "form/createOrEdit",
		formList  : base +"form/list",
		formActionCreate  : base + "form/action/create",
		formActionDel     : base + "form/action/delete",
		formActionEditSave: base + "form/action/update",
		
		createOrEditActivity: base + "create",
		activityOnlineList  : base +"manage/online",
		activityOfflineList  : base +"manage/offline",
		activityWillOnlineList  : base +"manage/willonline",
		activityActionCreate  : base + "manage/action/create",
		activityActionDel     : base + "manage/action/delete",
		activityActionEditSave: base + "manage/action/update",
		
		
		index: base +"manage/online",
		
		homepage: base + "index",
		
		systemActionReload: base + "system/action/reload",
		
		logout: base + "logout",
		
		joinUserTotalNum: base + "manage/action/finish/user/number",
		awardUserTotalNum: base + "manage/action/award/user/number",
		awardLeftNum: base + "manage/action/award/left",
		
		systemInterfaceTest: base + "system/action/test"
	};
});