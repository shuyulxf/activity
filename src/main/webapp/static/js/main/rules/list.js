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
	
	/* Timer init */
	var Timer = require("timer");
	new Timer({
		"$el" : $(".timer"),
		"styleClass" : "fr"
	});
	
	require("header");
	require("bootstrapExtend");
	require("validatorExtend");
	
	var config = require("config");
	var ruleList = config.ruleList,
		ruleActionDel = config.ruleActionDel,
		createOrEditRule = config.createOrEditRule;
	
	var common = require("common");
	var getErrorHtml = common.getErrorHtml,
	    getSuccessAlertHtml = common.getSuccessAlertHtml;
	
	var $ruleListModel = $("#ruleListModel");
	
	var $ruleDetailModel = $("#ruleDetailModel");
	$(".rule-detail").click(function(){
		
		$ruleDetailModel.modal({
			modalBody: "<pre>" + $(this).text() + "</pre>",
			show: true,
			posMiddle: true
		});
	});

	
	
	var Dialog = require("Dialog");
	$(".icon-delete").on("click", function(){
		var $this = $(this);
		var dialog = new Dialog({
			html: "确定要删除规则吗？",
			hasCancel: true,
			cancelBtn: "取消",
	        cancelCallback: null,
	        hasSure: true,
	        sureBtn: "确定",
	        sureCallback: function(){
	        	$.ajax({
	    			type: "POST",
	    			url:  ruleActionDel,
	    			data: {
	    				ruleId : $this.data("ruleid"),
	    				isCommonGroup: $this.data("iscommongroup")
	    			},
	    			timeout: 600000,
	    			dataType: "json",
	    			success: function(data) {
	    				
	    				if (data.status == 0) {
	    					$ruleListModel.modal({
	    						modalBody: getSuccessAlertHtml(data.msg),
	    						show: true,
	    						posMiddle: true
	    					});
	    					setTimeout(function(){
	    						window.location.reload();
	    					}, 2000);
	    				} else {
	    					$ruleListModel.modal({
	    						modalBody: getErrorHtml(data.msg),
	    						show: true,
	    						posMiddle: true
	    					});
	    				}
	    			},
	    			error: function(xhr) {
	    				console.log(xhr);
	    				$ruleListModel.modal({
    						modalBody: getErrorHtml("更新失败，网络异常"),
    						show: true,
    						posMiddle: true
    					});
	    			}
	    		});
	        },
	        hasClose: true
		});
		
		dialog.showDialog();
		
	});
	

	$(".icon-bianji").on("click", function(){
		
		var $this = $(this);
		
		if ($this.hasClass("active")) return false;
		
	    location.href = createOrEditRule + "?ruleId=" + $this.data("ruleid");
		
	});
	
	
	
});