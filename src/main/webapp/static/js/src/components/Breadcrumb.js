define(function(require, exports, module) {
    
	"use strict";
	
	let configPath = require("../base/config");
	let base = configPath.base,
	    index = configPath.index,
	    isObject = require("../base/dataType").isObject;
	
	
	var config = {
		"index":"活动管理<li>已上线活动</li>",
		"create" : "活动创建",
		"manage": {
			"title" : "活动管理",
			"subs"  : {
				"online" : "已上线活动",
				"willonline" : "未上线活动",
				"offline" :  "已下线活动"
			}
		},
		"data" : {
			"title" : "详细数据管理",
			"subs"  :{
				"activity" : "活动数据管理",
				"prize"    : "奖品数据管理",
				"user"     : "用户数据管理"
			}
		},
		"statistic" : {
			"title" : "统计管理",
			"subs"  :{
				"activity" : "活动统计管理",
				"prize"    : "奖品统计管理",
				"user"     : "用户统计管理"
			}
		},
		"rulegroup"    : {
			"title"    : "规则组管理",
			"subs"     : {
				"create" : "规则组创建",
				"list"   : "规则组列表"
 			}
		},
		"rule"      : {
			"title" : "规则管理",
			"subs"  : {
				"createOrEdit" : "规则编辑",
				"list"         : "规则列表"
 			}
		},
		"form"  : {
			"title" : "活动表单组件管理",
			"subs"  : {
				"createOrEdit" : "活动表单组件编辑",
				"list"         : "活动表单组件列表"
 			}
		},
		"prize"    : {
			"title" : "奖品管理",
			"subs"  :{
				"create" : "奖品添加",
				"list"   : "奖品列表"
 			}
		},
		"system"  : {
			"title": "系统管理",
			"subs" : {
				"reload" : "系统重载",
				"test"   : "测试接口"
			}
		}
	};
	
	function Breadcrumb(opts){
	
		var defaultOptions = {
			"$el" : null
		};
		
		$.extend(defaultOptions, opts);
		
		this.options = defaultOptions;
		
		init.call(this);
	}
	
	var init = function() {
	
		var $el = this.options.$el;
		if ($el === null) throw new Error("Breadcrumb dom not defined!");
		
		var that = this,
		    options = this.options;
		
		$el.on("click", "li", function(){
			
			var $this = $(this),
				$a    = $this.find("a");
			
			if ($this.hasClass("active")) return false;
			
			window.location.href = $a.href;
		});
		
		initBreadPath(config, $el);
	};
	
	var initBreadPath = function(config, $el) {
		
		var href = location.pathname;
		if (!href) throw new Error("window location is null!");
		
		if (href.indexOf("index") != -1) {
			href = index;
		}
			
		var comps = href.split(base);
		if (comps.length > 1) comps = comps[1];
		else throw new Error("window location is invalid!");
		
		if (!comps) throw new Error("window location is invalid!");
		
		comps = comps.split("/");
		
		var len = comps.length,
		    url = base.slice(0, -1),
		    props = config;
		    
		for (var i = 0; i < len && isObject(props); i++) {
		
			var comp = comps[i],
			    active = false;
			    
			url += "/" + comps[i];
			
			props = props[comp];
			var title = "";
			if (isObject(props)) {
				title = props.title;
				props = props.subs;
			} else {
				title = props;
			}
			
			
			if (i == len - 1) active = true;
			
			let a = `<a href="${url}">${title}</a>`,
			    li = `<li class="${active ? "active" : ""}">` +
			         `${active ? title : a}` + 
			         `</li>`;
			         
			
		    $el.append(li);
		}
		
	}
	
	Breadcrumb.constructor = "Breadcrumb";
	
	module.exports = Breadcrumb;
	
});