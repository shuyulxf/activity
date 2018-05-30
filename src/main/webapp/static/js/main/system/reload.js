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
	
	
	
	// 验证表单并提交数据
	var config = require("config");
	var systemActionReload = config.systemActionReload;
	
	var common = require("common");
	var getErrorHtml = common.getErrorHtml,
	    getSuccessAlertHtml = common.getSuccessAlertHtml;
	
	var $createRltInfo = $("#createRltInfo");
	
	$("#submit").click(function(e) {

		var $this = $(this);
		
		if ($this.hasClass("active")) return false;
		
		
		
		var isReloadActivity = $("[name=isReloadActivity]:checked").val(),
			isReloadRuleDrl  = $("[name=isReloadRuleDrl]:checked").val();
		if (isReloadActivity == '0' && isReloadRuleDrl == '0') {
			$this.removeClass("active");
			return false;
		}
		
		$this.addClass("active");
		$.ajax({
			type: "POST",
			url: systemActionReload,
			data: {
				isReloadActivity: isReloadActivity,
				isReloadRuleDrl : isReloadRuleDrl
			},
			timeout: 600000,
			dataType: "json",
			success: function(data) {
			    
				if (data.status == 1) {
					
					$this.before(getErrorHtml(data.msg));
				} else {
					
					$createRltInfo.modal({
						modalBody: getSuccessAlertHtml(data.msg+", 若再更新，请10分钟后再来更新！"),
						show: true
					});
				}
				
				setTimeout(function() {
					$this.removeClass("active");
				}, 600000);
				
			},
			error: function(xhr) {
				console.log(xhr);
				$this.removeClass("active");
				$this.before(getErrorHtml("更新失败，网络异常"));
			}
		})
	
		return false;
	});
});