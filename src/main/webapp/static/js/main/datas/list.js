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