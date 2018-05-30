define(function(require,exports,module) {
	"use strict";
	
	let isString = require("../base/dataType").isString;
	
	function FormatDate(opts) {
		var defaultOptions = {
			"date"   : new Date(),
			"format" : "YYYY-MM-DD HH:MM:SS",
			"hasweekday" : false,
			"dates"  :　{
				"year" : null,
				"month": null,
				"day"  : null
			},
			"times"  : {
				"hour"  : null,
				"minute": null,
				"second": null
			},
			"weekday": null
		};
		
		var format = opts && opts.format;
		if (format && !(/y{4}[-|\/|年](m{2}[-|\/|月])?(d{2}日?)? +h{2}[:|时](m{2}[:|分])?(s{2}秒?)?/gi.test(format))){
			throw new Error("Format param is not correct!");
		}
		format = $.trim(format);
		if(format) opts.format = format.replace(/ +/g, " ");
		
		$.extend(defaultOptions, opts);
		
		this.options = defaultOptions;
		
		init.call(this);
	}
	
	function padWithZero(s) {
		if ((s + '').length < 2) return "0" + s;
		return s;
	}
	FormatDate.prototype.format = function() {
		
		var opts   = this.options,
		    format = opts.format,
		    hasWeekday = opts.hasweekday,
		    dates  = opts.dates,
		    times  = opts.times,
		    weekday = opts.weekday,
		    result = {};
		
		var holders = format.split(" ");
		
		/* dates */
		var dateHolder = holders[0],
		    date = "";
		date = dates.year + 
		       (!dateHolder.substr(4, 1) ? "" : dateHolder.substr(4, 1) + padWithZero(dates.month) + 
		       (!dateHolder.substr(7, 1) ? "" : dateHolder.substr(7, 1) + padWithZero(dates.day) + dateHolder.substr(10, 1)));
		result.date = date;
		
		/* times */
		var timeHolder = holders[1],
		    time = "";
		time = padWithZero(times.hour) + 
			   (!timeHolder.substr(2, 1) ? "" : timeHolder.substr(2, 1) + padWithZero(times.minute) +
			   (!timeHolder.substr(5, 1) ? "" : timeHolder.substr(5, 1) + padWithZero(times.second) + timeHolder.substr(9, 1)));
		result.time = time;
		
		if (hasWeekday) result.weekday = "星期" + (function(){
			var toCN = ["日", "一", "二", "三", "四", "五", "六"];
			return toCN[weekday];
		})();
		
		return result;
		
	}
	
	FormatDate.constructor = "FormatDate";
	
	function init() {
		 var opts  = this.options,
		     date  = opts.date,
		     dates = opts.dates,
		     times = opts.times,
		     hasWeekday = opts.hasWeekday,
		     weekday = opts.weekday,
		     dObj = date;
		 
		 if (isString(date)) {
	        date = date.replace("-", "/");
	        date = date.replace("T", " ");
	        dObj = new Date(date);
	     } 
	    
		
		 dates.year = dObj.getFullYear();
		 dates.month = dObj.getMonth() + 1;
		 dates.day = dObj.getDate();
	
	     times.hour = dObj.getHours();
	     times.minute = dObj.getMinutes();
	     times.second = dObj.getSeconds();
	     
	     opts.weekday = dObj.getDay();
	}
	
	module.exports = FormatDate;
});