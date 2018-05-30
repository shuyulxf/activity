define(function(require,exports,module) {
	"use strict";
	
	var isObject = require("dataType")["isObject"];
	
	var getErrorHtml = function(msg) {
		return `<span class="error iconfont icon-tanhao">${msg}</span><br>`;
	}
	var getSuccessAlertHtml = function(msg){
		return `<span class="iconfont icon-sucess alert-success">${msg}</span>`;
	}
	
	var formSerializeTrim = function(s) {
		s = $.trim(s);
		s = s.replace(/=(\+)+|(\+)+[=|$]/g,"="); 
		s = s.replace(/(\+)*\&(\+)*/g,"&");
		return  decodeURIComponent(s);  
	}
	
	var jsonFromSerialize = function(s) {
		
		if (s == null || s.length == 0) return {};
		
		var params = (formSerializeTrim(s)).split("&"),
		    len = params.length,
		    json = {};
		for(var i = 0; i < len; i++) {
			var param = params[i];
			if (param && param.length > 0) {
				var idx = param.indexOf("="),
				    value = param.substr(idx + 1);
				if (value)  {
					
					value = value.replace(/\"/g, "'");
					json[param.substr(0, idx)] = value ? value.replace(/\+/g, " ") : value;
				}
			}
		}
		
		return json;
	}
	
	var exacuteTextScript = function($pluginInits) {
		
		if (!$pluginInits || $pluginInits.length <= 0) return;
		
		// 组件脚本初始化
	    var len = $pluginInits.length;
	    
	    for(var i = 0; i < len; i++) {
		    	var func = new Function($($pluginInits[i]).text());
		    	func();
		   
	    }
	}

	var isEmpty = function(obj) {
		
		if (isObject(obj)) {
			
		    for(var key in obj) {
		        if(obj.hasOwnProperty(key))
		            return false;
		    }
		    return true;
		}
	}
	
	var mapToStr = function(obj) {
		
		var rlt = [];
		if (isObject(obj)) {
			
		    for(var key in obj) {
		       rlt.push(key)
		    }
		    return rlt.join(",");
		    
		} else return "";
	}
	
	var getHeightFormHTMLText = function(text) {
		
		var $temp = $(document.createElement("div")),
		 	$body = $("body");
		
		$temp.css("display", "hidden").html(text);
		$body.append($temp);
		
		var height = $temp.height();
		
		$temp.remove();
		
		return height;
	}
	
	module.exports = {
		getErrorHtml: getErrorHtml,
		getSuccessAlertHtml: getSuccessAlertHtml,
		formSerializeTrim: formSerializeTrim,
		exacuteTextScript: exacuteTextScript,
		jsonFromSerialize: jsonFromSerialize,
		isEmpty: isEmpty,
		mapToStr: mapToStr,
		getHeightFormHTMLText:getHeightFormHTMLText
	};
});