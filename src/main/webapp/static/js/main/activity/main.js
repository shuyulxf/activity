define(function(require,exports,module) {
	
	"use strict";
	
	/* deal global error */
	require("globalError");
	
	/* SideBar init */
	var SideBar = require("sideBar");
	new SideBar({
		"$el" : $(".sidebar")
	});
	
	/* Breadcrumb init */
	var Breadcrumb = require("breadcrumb");
	new Breadcrumb({
		"$el" : $(".breadcrumb")
	});
	
	require("header");
	
	/* Timer init */
	var Timer = require("timer");
	new Timer({
		"$el" : $(".timer"),
		"styleClass" : "fr"
	});

	
	var isObject = require("dataType")["isObject"];
	
	require("bootstrapExtend");
	require("validatorExtend");
	
	
	var config = require("config"),
	    activityActionCreate = config.activityActionCreate,
	    activityActionDel    = config.activityActionDel,
	    activityActionEditSave = config.activityActionEditSave,
	    activityWillOnlineList = config.activityWillOnlineList;
	
	var common = require("common"),
		exacuteTextScript = common.exacuteTextScript,
		jsonFromSerialize = common.jsonFromSerialize,
		getSuccessAlertHtml = common.getSuccessAlertHtml,
		getErrorHtml = common.getErrorHtml,
		isEmpty = common.isEmpty,
		mapToStr= common.mapToStr;
	
	$('.dropdown-toggle').dropdown();
	
	var $activitySuccessInfo = $("#activitySuccessInfo");

	
    var $forms     = $("form"),
        $nexts     = $(".next"),
        $prevs     = $(".prev"),
        validators = [],
        curFormIdx = 0,
        lastFormIdx = $forms.length - 1;
    
    
    // 表单验证
    $forms.each(function(idx, el) {
    	
    	var $this = $(el);
    	validators.push($this.validate(formValidatorRules[idx]));
    });
    
    // 下一步或者提交表单
    $nexts.on("click", function(e) {
    	
    	var $this = $(this);
    	
    	if (curFormIdx != lastFormIdx) {

	        if ($($forms[curFormIdx]).valid()) {
	        	$($forms[curFormIdx++]).addClass("hide");
	        	$($forms[curFormIdx]).removeClass("hide");
	        }
	    } else {
	  
	    	//提交活动表单
	    	var valid = $($forms[curFormIdx]).valid(),
		    $this = $(this);
			if (valid && !$this.hasClass("active")) {
				$this.addClass("active")
				
				var url = activityActionEditSave;
				if ($this.data("type") == "create") url = activityActionCreate;
				$.ajax({
					type: "POST",
					url : url,
					data: collectParamsBeforeSubmit(),
					timeout: 600000,
					dataType: "json",
					success: function(data) {
					   
						if (data.status == 1) {
							
							$this.before(getErrorHtml(data.msg));
						} else {
							
							$activitySuccessInfo.modal({
								modalBody: getSuccessAlertHtml(data.msg + ", 请到相应的列表页查看!"),
								show: true
							});
							
							setTimeout(function(){
								window.location.href = activityWillOnlineList;
							}, 2000);
						}
						$this.removeClass("active");
					},
					error: function(xhr) {
						console.log(xhr);
						$this.removeClass("active");
						$this.before(getErrorHtml("更新失败，网络异常"));
					}
				})
			}
	    }
        
    	return false;
    });
    
    function collectParamsBeforeSubmit() {
    	
    	var paramLists = {},
    		ruleIdMap = {},
    	    activityId = $("[name=activityId]").val(),
    	    activityType =  $("[name=activityType]").val(),
    	    activityName =  $("[name=activityName]").val();
    	
    	// 配置activityId
    	if (activityId) paramLists.activityId = activityId;
    	
    	var formAttrTypes = ["activityCommonSetting", "activityGeneralSetting", "activityAwardSetting"];
    	
    	var len = formAttrTypes.length;
    	var replyParams = {},
    	    replyRgx = /[\w]+Reply(Suc|Fail)/g;
    	
    	for (var i = 0; i < len; i++) {
    		
    		var formAttType = formAttrTypes[i],
    		    $form = $($forms[i]),
    		    params = {};
    		
    		var $formItems = $form.find(".form-item"),
    		    formLen = $formItems.length;
    	
    		for(var j = 0; j < formLen; j++) {
    			
    			var $formItem = $($formItems[j]);
    		    
                var formValue;    	
                
                var $ipts = $formItem.find("[name]");
                var $v = $ipts.filter(function(idx) {
                	
                	var $ipt = $($ipts[idx]),
                	    name = $ipt.attr("name");
                	if (name) {
                		replyRgx.lastIndex = 0;
                		if (replyRgx.test(name)) {
                			var v = $ipt.val();
                			if (v) replyParams[name] = $ipt.val();
                		} else if(name.indexOf("ruleIds") == -1){
                			return true;
                		}
                	}
                });
                
                var name = $v.attr("name"),
                    value = $v.val();
                if (name == "activityAwardDeliveryType") debugger;
                if (value) {
                	var valForRadio = "";
                    if ($v.attr("type") == 'radio') {

                    	valForRadio = $v.filter(":checked").val();
        				params[name] = valForRadio;
        			}
        			else {
        				params[name] = value;
        			}
                    var ruleId = $formItem.find("[name=ruleIds]").val();
                    if (formAttType != "activityCommonSetting" && valForRadio != "false" && name != "numForjoinActivityToEarnSeriesAward" && name != "prizeIdsForSeriesAward") mergeRuleIds(ruleIdMap, ruleId);
                }
    		}
    		
    		
    		if(!isEmpty(params)) paramLists[formAttType] = JSON.stringify(params);
    	}
    	
    	paramLists["ruleIds"] = mapToStr(ruleIdMap);
    	if(!isEmpty(replyParams)) paramLists["activityReplySetting"] = JSON.stringify(replyParams);
		
    	return paramLists;
    }
    
    function mergeRuleIds(ruleIdMap, ruleIdStr) {
    	
    	if (ruleIdStr == null || ruleIdStr.length == 0 || !isObject(ruleIdMap)) return ruleIdMap;
    	
    	ruleIdStr = ruleIdStr.replace(/ /g, "");
    
    	if (ruleIdStr.length == 0) return ruleIdMap;
    	
    	var ruleIds = ruleIdStr.split(","),
    		len = ruleIds.length;
    	for(var i = 0; i < len; i++) {
    		
    		var ruleId = ruleIds[i];
    		if (ruleId && ruleIdMap[ruleId] == null) ruleIdMap[ruleId] = ruleId;
    	}
    	
    	return ruleIdMap;
    }
    
    
    
    $prevs.on("click", function(e) {
    	
    	var $this = $(this);
      
    	$($forms[curFormIdx--]).addClass("hide");
    	$($forms[curFormIdx]).removeClass("hide");

    	return false;
    });
    
  
   
    exacuteTextScript($(".activity").find(".plugin-init[type=text]"));

});