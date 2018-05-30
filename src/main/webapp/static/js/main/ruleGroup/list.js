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
	
	
	// URLs
	var config = require("config");
	var ruleGroupActionDel = config.ruleGroupActionDel,
		createRuleGroup    = config.createRuleGroup,
		ruleGroupActionEditSave = config.ruleGroupActionEditSave;
	
	var common = require("common");
	var getSuccessAlertHtml = common.getSuccessAlertHtml,
	    getErrorHtml = common.getErrorHtml;
	
	var $ruleGroupListModal = $("#ruleGroupListModal");
	
	var Dialog = require("Dialog");
	$(".icon-delete").on("click", function(){
		var $this = $(this);
		var dialog = new Dialog({
			html: "确定要删除工作组吗？",
			hasCancel: true,
			cancelBtn: "取消",
	        cancelCallback: null,
	        hasSure: true,
	        sureBtn: "确定",
	        sureCallback: function(){
	        	$.ajax({
	    			type: "POST",
	    			url:  ruleGroupActionDel,
	    			data: {
	    				ruleGroupId : $this.data("rulegroupid")
	    			},
	    			timeout: 600000,
	    			dataType: "json",
	    			success: function(data) {
	    			    
	    				if (data.status == 0) {
	    					$ruleGroupListModal.modal({
	    						modalBody: getSuccessAlertHtml(data.msg),
	    						show: true,
	    						posMiddle: true
	    					});
	    					setTimeout(function(){
	    						window.location.reload();
	    					}, 2000);
	    				} else {
	    					$ruleGroupListModal.modal({
	    						modalBody: getErrorHtml(data.msg),
	    						show: true,
	    						posMiddle: true
	    					});
	    				}
	    			},
	    			error: function(xhr) {
	    				console.log(xhr);
	    				$ruleGroupListModal.modal({
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
	
	var $editRuleGroup = $("#editRuleGroup");
	$(".icon-bianji").on("click", function(){
		
		var $this = $(this);
		
		if ($this.hasClass("active")) return false;
		
	    location.href = createRuleGroup + "?ruleGroupId=" + $this.data("rulegroupid");
		
	});
	
	function popEditRuleGroupDialog(data) {
		
		var html = template("ruleGroupEdit", data);
		$editRuleGroup.modal({
			modalBody: html,
			show: true
		});
		// 编辑规则组
		// 展示表单项的其他信息
		$('.icon-help').popover({
			trigger: "hover"
		});
		
		$('.dropdown-toggle').dropdown();
		initEditRuleGroupFormFunc();
	}
	
	
	function initEditRuleGroupFormFunc() {
		var $form = $("#ruleGroupForm");
		var validator = $form.validate({
		   rules: {
			   ruleGroupName: {
				   required: true,
				   minlength: 6,
				   maxlength: 30
			   },
			   ruleGroupWeight: {
				   required: true,
				   weight: true
			   }
		   },
		   messages: {
			   ruleGroupName: {
				   required: "活动规则组的名称不能为空",
				   minlength: "活动规则组的最小长度不得少于6个字",
				   maxlength: "活动规则组的最小长度不得多于30个字"
			   },
			   ruleGroupWeight: {
				   required: "活动规则组的权重不能为空"
			   } 
		   },
		   submitHandler: function(form) {
			   return false;
		   }
		});
	}
	
	$("body").on("click", "#submit", function(e){
		var $form = $(this).parents("form"),
		    valid = $form.valid(),
	        $this = $(this);

		if ($this.hasClass("active")) return false;
		
		$this.addClass("active");
		
		if (valid) {
			$.ajax({
				type: "POST",
				url: ruleGroupActionEditSave,
				data: $form.serialize(),
				timeout: 600000,
				dataType: "json",
				success: function(data) {
				    
					if (data.status == 1) {
						
						$this.before(getErrorHtml(data.msg));
					} else {
						
						$ruleGroupListModal.modal({
							modalBody: getSuccessAlertHtml(data.msg),
							show: true
						});
						setTimeout(function(){
							window.location.reload();
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
	})
	
});