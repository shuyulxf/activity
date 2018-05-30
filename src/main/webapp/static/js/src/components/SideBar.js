define(function(require, exports, module) {
    
	"use strict";
	
	let configPath = require("../base/config");
	let index = configPath.index,
	    homepage = configPath.homepage;
	
	function SideBar(opts){
		
		var defaultOptions = {
			"$el" : null,
			"active": "sidebar-active",
			"speed": 500
		};
		
		$.extend(defaultOptions, opts);
		this.options = defaultOptions;
		
		init.call(this);
	}
	
	var init = function() {
		
		var $el = this.options.$el;
		if ($el === null) throw new Error("Side bar dom not defined!");
		
		var that = this,
		    options = this.options,
		    speed = options.speed,
		    active = options.active;
		
		/* bind open and close click event */
		$el.on("click", ".nav-class", function() {
			
			var $this = $(this),
				$nav = $this.parents(".nav-list-item");
				
			var opened = isOpen.call(that, $nav);
			
			if (opened){
				
				$nav.find(".nav-items").hide(speed, function(){
					close($nav, active);
				});
			}
			else {
				close($nav.siblings(), active);
				$nav.find(".nav-items").show(speed, function(){
					open($nav, active);
				});
			}
		});
		
		
		
		//default opened
		
		var href = location.href,
		    $as = $el.find("a"),
		    len = $as.length;
		
		if (href.indexOf(homepage) != -1) href = href.replace(homepage, index);
		
		for (var i = 0; i < len; i++) {
			
			var a = $as[i],
			    ahref = a.href;
			
			if(ahref && ahref !== "#" && href.indexOf(ahref) !== -1) {
				var $a = $(a),
				    $navItem = $a.parents(".nav-items"),
				    $active = $navItem.length !== 0 ? $navItem : $a.parents("li");
				open($active, active);
				activeItem($a);
			}
		}

	};
	
	var isOpen = function($nav) {
		
		if ($nav === null) throw new Error("Side bar dom not defined!");
		
		var  active = this.options.active;
		
		if ($nav.hasClass(active)) return true;
		else return false;
	};
	
	var close = function($item, active) {
		$item.removeClass(active);
		$item.find(".nav-items").hide();
		$item.find(".glyphicon").removeClass("glyphicon-triangle-top").addClass("glyphicon-triangle-down");
	};
	
	var open = function($item, active) {
		$item.addClass(active);
		$item.find(".glyphicon").removeClass("glyphicon-triangle-down").addClass("glyphicon-triangle-top");
	};
	
	var activeItem = function($item) {
		$item.addClass("active");
	};
	
	var nonActiveItem = function($item) {
		$item.removeClass("active");
	};
	
	SideBar.constructor = "SideBar";
	
	module.exports = SideBar;
	
});