define(function(require, exports, module) {
    
	"use strict";
	
	var common = require("../base/common"),
	    exacuteTextScript = common.exacuteTextScript,
	    jsonFromSerialize = common.jsonFromSerialize,
	    isEmpty = common.isEmpty;
	 
	function Search(opts){
		
		var defaultOptions = {
			"$el" : null
		};
		
		$.extend(defaultOptions, opts);
		this.options = defaultOptions;
		
		init.call(this);
	}
	
	var init = function() {
		
		var opts = this.options,
		    $el = opts.$el;
		
		//init datePicker
		$el.find('.datetime').datetimepicker({
			language:  'zh-CN',
	        format: "yyyy-mm-dd hh:ii:ss",
	        autoclose: true,
	        todayBtn: true,
	        pickerPosition: "bottom-left"
	    });
		
		exacuteTextScript($el.find("script[type=text]"));
		
		$el.find("button[type=submit]").click(function() {
			
			var params = jsonFromSerialize($el.serialize());
			if (!isEmpty(params)) window.location.href = window.location.pathname + "?paramStr=" + encodeURI(JSON.stringify(params));
			else window.location.href = window.location.pathname;
			return false;
		});
	};
	
	
	Search.constructor = "Search";
	
	module.exports = Search;
	
});