define(function(require, exports, module) {
    
	"use strict";
	
	var FormatDate = require("../base/formatDate");
	var date = new FormatDate({
		"hasweekday" : true,
		"format"     : "YYYY年MM月DD日  HH:MM"
	}).format();
	
	
	function Timer(opts){
		
		var defaultOptions = {
			"$el" : null,
			"styleClass" : ""
		};
		
		$.extend(defaultOptions, opts);
		this.options = defaultOptions;
		
		init.call(this);
	}
	
	var init = function() {
		
		var opts = this.options,
		    $el = opts.$el,
		    styleClass = opts.styleClass;
		
		if ($el === null) throw new Error("Side bar dom not defined!");

		$el.addClass(styleClass);
		var innerHTML = `<span class="date">${date.date}</span>
						 <span class="time">${date.time}</span>
						 <span class="weekday">${date.weekday}</span>`;
						 
		$el.html(innerHTML);
	};
	
	
	
	Timer.constructor = "Timer";
	
	module.exports = Timer;
	
});