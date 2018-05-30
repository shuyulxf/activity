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
	var prizeActionDel = config.prizeActionDel,
		prizeActionEditGet = config.prizeActionEditGet,
		prizeActionEditSave = config.prizeActionEditSave;
	
	var common = require("common");
	var getSuccessAlertHtml = common.getSuccessAlertHtml,
	    getErrorHtml = common.getErrorHtml;
	
	var $prizeListModal = $("#prizeListModal");
	
	
	var Dialog = require("Dialog");
	$(".icon-delete").on("click", function(){
		var $this = $(this);
		var dialog = new Dialog({
			html: "确定要删除活动奖品吗？",
			hasCancel: true,
			cancelBtn: "取消",
	        cancelCallback: null,
	        hasSure: true,
	        sureBtn: "确定",
	        sureCallback: function(){
	        	$.ajax({
	    			type: "POST",
	    			url:  prizeActionDel,
	    			data: {
	    				prizeId : $this.data("prizeid")
	    			},
	    			timeout: 600000,
	    			dataType: "json",
	    			success: function(data) {
	    			    
	    				if (data.status == 0) {
	    					$prizeListModal.modal({
	    						modalBody: getSuccessAlertHtml(data.msg),
	    						show: true,
	    						posMiddle: true
	    					});
	    					setTimeout(function(){
	    						window.location.reload();
	    					}, 2000);
	    				} else {
	    					$prizeListModal.modal({
	    						modalBody: getErrorHtml(data.msg),
	    						show: true,
	    						posMiddle: true
	    					});
	    				}
	    			},
	    			error: function(xhr) {
	    				console.log(xhr);
	    				$prizeListModal.modal({
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
	
	var $editPrize = $("#editPrize");
	$(".icon-bianji").on("click", function(){
		
		var $this = $(this);
		
		if ($this.hasClass("active")) return false;
		
		$this.addClass("active");
		
		$.ajax({
			type: "POST",
			url:  prizeActionEditGet,
			data: {
				prizeId : $this.data("prizeid")
			},
			timeout: 600000,
			dataType: "json",
			success: function(data) {
			    
				if (data.status == 0) {
					popEditPrizeDialog(JSON.parse(data.data));
					
				} else {
					$prizeListModal.modal({
						modalBody: getErrorHtml(data.msg),
						show: true,
						posMiddle: true
					});
				}
				$this.removeClass("active");
			},
			error: function(xhr) {
				console.log(xhr);
				$this.removeClass("active");
				$this.before(getErrorHtml("更新失败，网络异常"));
			}
		});
	});
	
	function popEditPrizeDialog(data) {
		
		var html = template("prizeEdit", data);
		$editPrize.modal({
			modalBody: html,
			show: true
		});
		
		initEditPrizeFormFunc();
	}
	
	
	function initEditPrizeFormFunc() {
		var $form = $("#prizeForm");
		var validator = $form.validate({
		   rules: {
			   prizeName: {
				   required: true,
				   minlength: 2,
				   maxlength: 10
			   }
		   },
		   messages: {
			   prizeName: {
				   required: "活动奖品的名称不能为空",
				   minlength: "活动奖品的最小长度不得少于2个字",
				   maxlength: "活动奖品的最小长度不得多于10个字"
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
				url: prizeActionEditSave,
				data: $form.serialize(),
				timeout: 600000,
				dataType: "json",
				success: function(data) {
				    
					if (data.status == 1) {
						
						$this.before(getErrorHtml(data.msg));
					} else {
						
						$prizeListModal.modal({
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