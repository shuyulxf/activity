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
	
	$('.dropdown-toggle').dropdown();
	
	var $form = $("#ruleGroupForm");
	var validator = $form.validate({
	   rules: {
		   ruleGroupNameCN: {
			   required: true,
			   minlength: 6,
			   maxlength: 30
		   },
		   ruleGroupNameEN: {
			   required: true,
			   minlength: 6,
			   maxlength: 50
		   },
		   ruleGroupWeight: {
			   required: true,
			   weight: true
		   }
	   },
	   messages: {
		   ruleGroupNameCN: {
			   required: "活动规则组的名称不能为空",
			   minlength: "活动规则组的最小长度不得少于6个字",
			   maxlength: "活动规则组的最小长度不得多于30个字"
		   },
		   ruleGroupNameCN: {
			   required: "活动规则组的英文名称不能为空",
			   minlength: "活动规则组英文名称的最小长度不得少于6个字",
			   maxlength: "活动规则组英文名称的最小长度不得多于30个字"
		   },
		   ruleGroupWeight: {
			   required: "活动规则组的权重不能为空"
		   } 
	   }
	});
	
	var $exclusiveRadio = $("[name=isExclusive]"),
	    $weight = $(".weight");
	$exclusiveRadio.on("change", function(e) {
		
		var $this = $(this),
		    val = $this.val();
		if (val == '1') {
			$weight.addClass("hide");
		} else {
			$weight.removeClass("hide");
		}
	})
//	
//	$commonRadio.on("change", function(e) {
//		
//		var $this = $(this),
//		    val = $this.val();
//		if (val == '1') {
//			$exclusiveRadio.get(0).checked = false;  
//			$exclusiveRadio.get(1).checked = true;  
//		}
//	})
//	
	// 验证表单并提交数据
	var config = require("config");
	var ruleGroupActionCreate = config.ruleGroupActionCreate,
		ruleGroupActionUpdate = config.ruleGroupActionEditSave,
		ruleGroupList         = config.ruleGroupList;
	
	var common = require("common");
	var getErrorHtml = common.getErrorHtml,
	    getSuccessAlertHtml = common.getSuccessAlertHtml,
	    formSerializeTrim   = common.formSerializeTrim,
	    exacuteTextScript   = common.exacuteTextScript;
	
	var $createRltInfo = $("#createRltInfo");
	    
	
	$("#submit").click(function(e) {
		
		var valid = $form.valid(),
		    $this = $(this);
		
		if ($this.hasClass("active")) return false;
		
		$this.addClass("active");
		
		if (valid) {
			$.ajax({
				type: "POST",
				url: ruleGroupActionCreate,
				data: formSerializeTrim($form.serialize()),
				timeout: 600000,
				dataType: "json",
				success: function(data) {
				    
					if (data.status == 1) {
						
						$this.before(getErrorHtml(data.msg));
					} else {
						
						$createRltInfo.modal({
							modalBody: getSuccessAlertHtml(data.msg),
							show: true
						});
						setTimeout(function(){
							window.location.href = ruleGroupList;
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
	
	$("#update").click(function(e) {
		
		var valid = $form.valid(),
		    $this = $(this);
		
		if ($this.hasClass("active")) return false;
		
		$this.addClass("active");
		
		if (valid) {
			$.ajax({
				type: "POST",
				url: ruleGroupActionUpdate,
				data: formSerializeTrim($form.serialize()),
				timeout: 600000,
				dataType: "json",
				success: function(data) {
				    
					if (data.status == 1) {
						
						$this.before(getErrorHtml(data.msg));
					} else {
						
						$createRltInfo.modal({
							modalBody: getSuccessAlertHtml(data.msg),
							show: true
						});
						setTimeout(function(){
							window.location.href = ruleGroupList;
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
	
	
	// 展示表单项的其他信息
	$('.icon-help').popover({
		trigger: "hover"
	})
	$(".btn").popover({})
	
	
	exacuteTextScript($(".rule-group script[type=text]"));
});