define(function(require, exports, module) {
    
	"use strict";
	
	let configPath = require("config"),
		common = require("common"),
		getErrorHtml = common.getErrorHtml,
		getSuccessAlertHtml = common.getSuccessAlertHtml,
	    logout = configPath.logout;
	
	function Header(opts){
		
		var defaultOptions = {
			"$el" : null,
			"$hidden" : null,
			"$tooltip" : null
		};
		
		$.extend(defaultOptions, opts);
		this.options = defaultOptions;
		
		init.call(this);
	}
	
	var init = function() {
		
		var opts = this.options,
		    $el = opts.$el,
		    $hidden = opts.$hidden,
		    $tooltip = opts.$tooltip;
		
		$el.on("click", function(e) {
			$.ajax({
				type: "POST",
				url: logout,
				data: {
					userName: $hidden.val()
				},
				timeout: 600000,
				dataType: "json",
				success: function(data) {
				    
					var status = data.status;
					if (status == 0) {
						var rlt = JSON.parse(data.data);
						
						$tooltip.modal({
							modalBody: getSuccessAlertHtml(data.msg),
							show: true
						});
						
						setTimeout(function(){
    						location.href = rlt.location;
    					}, 2000);
					} else {
						$tooltip.modal({
							modalBody: getErrorHtml(data.msg),
							show: true
						});
					}
					
					$this.removeClass("active");
				},
				error: function(xhr) {
					console.log(xhr);
					$this.removeClass("active");
				}
			});
		});
	};
	
	
	
	Header.constructor = "Header";
	
	new Header({
		$el: $("#logout"),
		$hidden: $("[name=userName]"),
		$tooltip: $("#loginInfo")
	});
	
	module.exports = Header;
	
});