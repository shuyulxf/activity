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
	var createOrEditActivity = config.createOrEditActivity,
		activityActionDel = config.activityActionDel;

	var common = require("common");
	var getErrorHtml = common.getErrorHtml,
	    getSuccessAlertHtml = common.getSuccessAlertHtml,
	    jsonFromSerialize   = common.jsonFromSerialize;
	
   var $activityListModal = $("#activityListModal");

	
	// init search form
	var Search = require("Search");
	new Search({
		$el: $(".search")
	});
	
	
	var Dialog = require("Dialog");
	$(".icon-delete").on("click", function(){
		
		var $this = $(this);
		
		var dialog = new Dialog({
			html: "确定要删除表单吗？",
			hasCancel: true,
			cancelBtn: "取消",
	        cancelCallback: null,
	        hasSure: true,
	        sureBtn: "确定",
	        sureCallback: function(){
	        	
	        	if ($this.hasClass("active")) return;
	        	$this.addClass("active");
	        	$.ajax({
	    			type: "POST",
	    			url:  activityActionDel,
	    			data: {
	    				activityId : $this.data("activityid")
	    			},
	    			timeout: 600000,
	    			dataType: "json",
	    			success: function(data) {
	    				
	    				if (data.status == 0) {
	    					$activityListModal.modal({
	    						modalBody: getSuccessAlertHtml(data.msg),
	    						show: true,
	    						posMiddle: true
	    					});
	    					setTimeout(function(){
	    						window.location.reload();
	    					}, 2000);
	    				} else {
	    					$activityListModal.modal({
	    						modalBody: getErrorHtml(data.msg),
	    						show: true,
	    						posMiddle: true
	    					});
	    				}
	    				$this.addClass("active")
	    			},
	    			error: function(xhr) {
	    				console.log(xhr);
	    				$activityListModal.modal({
							modalBody: getErrorHtml("更新失败，网络异常"),
							show: true
						});
	    				$this.addClass("active")
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
		
	    location.href = createOrEditActivity + "?activityId=" + $this.data("activityid");
		
	});
	
	var numForPerPage = 10;
	$('#pagination').bootpag({
	    total: Math.ceil(total/numForPerPage),          // total pages
	    page: page,            // default page
	    maxVisible: 5,     // visible pagination
	    leaps: true         // next/prev leaps through maxVisible
	}).on("page", function(event, num){
		
		var params = jsonFromSerialize($(".search").serialize());
		if (params) {
			params.numPerPage = numForPerPage;
			params.page = num;
			
			window.location.href = window.location.pathname + "?paramStr=" + encodeURI(JSON.stringify(params));
		}
		
	});
});