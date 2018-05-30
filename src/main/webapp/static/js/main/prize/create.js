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
			   required: "活动奖品名称不能为空",
			   minlength: "活动奖品名称的最小长度不得少于2个字",
			   maxlength: "活动奖品名称的最小长度不得多于10个字"
		   } 
	   }
	});
	
	// 验证表单并提交数据
	var config = require("config");
	var prizeActionCreate = config.prizeActionCreate,
		prizeList         = config.prizeList;
	
	var common = require("common");
	var getErrorHtml = common.getErrorHtml,
	    getSuccessAlertHtml = common.getSuccessAlertHtml;
	
	var $createRltInfo = $("#createRltInfo");
	
	$("#submit").click(function(e) {
		
		var valid = $form.valid(),
		    $this = $(this);
		
		if ($this.hasClass("active")) return false;
		
		$this.addClass("active");
		
		if (valid) {
			$.ajax({
				type: "POST",
				url: prizeActionCreate,
				data: $form.serialize(),
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
							window.location.href = prizeList;
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
	

	$(".btn").popover({})
});