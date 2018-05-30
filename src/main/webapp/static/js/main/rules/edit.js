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
		ruleActionCreate = config.ruleActionCreate,
		ruleActionEditSave = config.ruleActionEditSave;
	
	var common = require("common");
	var getErrorHtml = common.getErrorHtml,
	    getSuccessAlertHtml = common.getSuccessAlertHtml,
	    exacuteTextScript   = common.exacuteTextScript,
	    jsonFromSerialize   = common.jsonFromSerialize;

	
	$('.dropdown-toggle').dropdown();
	$(".icon-help").popover({
		trigger: "hover",
		placement: "right",
		html: true
	});
	

	// sucess info, 为弹窗绑定事件，在弹窗时，修改弹窗的位置垂直居中
	var $ruleSuccessInfo = $("#ruleSuccessInfo");
	
	
	var $ruleForm = $("#ruleForm");
	var ruleValidator = $ruleForm.validate({
		   rules: {
			   ruleGroupId: {
				   required: true,
				   number: true
			   },
			   rule: {
				   required: true
			   },
			   ruleWeight: {
				   required: true,
				   number: true
			   },
			   ruleNameCN: {
				   required: true
			   },
			   ruleNameEN: {
				   required: true
			   },
			   exRuleGroupId: {
				   required: true,
				   number: true
			   }
		   },
		   messages: {
			   ruleGroupId: {
				   required: "活动规则所属的规则组不能为空",
		           number: "规则组的id不符合数字要求"
			   },
			   rule: {
				   required: "活动规则不能为空"
			   },
			   ruleWeight: {
				   required: "规则权重不能为空",
				   number: "活动规则权重不符合权重的要求"
			   },
			   ruleNameCN: {
				   required: "规则名称(中文)不能为空"
			   },
			   ruleNameEN: {
				   required: "规则名称(英文)权重不能为空"
			   },
			   exRuleGroupId: {
				   required: "互斥规则组不能为空",
				   number: "活动互斥规则权重不符合权重的要求"
			   }
		   }
	});
	
	// 规则
	var $ruleIpt = $(".ruleInput"),
		$ruleHiddenIpt = $ruleIpt.find("input");
	$('.flexTextArea').flexText();

	$("#submit").on("click", function() {
		
		var valid = $ruleForm.valid(),
	    $this = $(this);
		if (valid && !$this.hasClass("active")) {
			$this.addClass("active")
			$.ajax({
				type: "POST",
				url : ruleActionCreate,
				data: {
					rule: JSON.stringify(jsonFromSerialize($ruleForm.serialize())),
					isCommonGroup: $("[data-iscommongroup]").data("iscommongroup")
				},
				timeout: 600000,
				dataType: "json",
				success: function(data) {
				   
					if (data.status == 1) {
						
						$this.before(getErrorHtml(data.msg));
					} else {
						
						$ruleSuccessInfo.modal({
							modalBody: getSuccessAlertHtml(data.msg),
							show: true
						});
						setTimeout(function(){
							window.location.href = ruleList;
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
		
		return false;
	});
	
	$("#update").on("click", function() {
		
		var valid = $ruleForm.valid(),
	    $this = $(this);
	
		if (valid && !$this.hasClass("active")) {
			$this.addClass("active")
			$.ajax({
				type: "POST",
				url : ruleActionEditSave,
				data: {
					rule: JSON.stringify(jsonFromSerialize($ruleForm.serialize())),
					isCommonGroup: $("[data-iscommongroup]").data("iscommongroup")
				},
				timeout: 600000,
				dataType: "json",
				success: function(data) {
				   
					if (data.status == 1) {
						
						$this.before(getErrorHtml(data.msg));
					} else {
						
						$ruleSuccessInfo.modal({
							modalBody: getSuccessAlertHtml(data.msg),
							show: true
						});
						setTimeout(function(){
							window.location.href = ruleList;
						}, 2000);
					}
					$this.removeClass("active");
				},
				error: function(xhr) {
					console.log(xhr);
					$this.removeClass("active");
				}
			})
		}
		
		return false;
	});
	
	exacuteTextScript($(".rule script[type=text]"))
	
});