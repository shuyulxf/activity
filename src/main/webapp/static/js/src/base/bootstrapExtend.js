define(function(require,exports,module) {
	"use strict";
	
	var getHeightFormHTMLText = require("common").getHeightFormHTMLText;
	
	var dropdown = $.fn.dropdown;
	$.fn.dropdown = function (options) {
		
		
		dropdown.call(this, options);
       
        var $this = $(this);
      

        
        $this.parents(".dropdown").find(".dropdown-menu").on("click", "li", function(e){
        	
        	var $target = $(this),
        	    $dropdown = $target.closest(".dropdown"),
        		$activeShow = $dropdown.find(".btn-value"),
        		$hidden = $dropdown.find(".btn-value-input"),
        	    title = $target.text(),
        	    value = $target.data("id") ? $target.data("id") : title;
        	    
        	    value = value == "NULL" ? null : value;
        	    
        	$activeShow.text(title);
        	$hidden.val(value);
        
        	var iscommongroup = $target.data("icg");
        	if (iscommongroup) $hidden.attr("data-iscommongroup", iscommongroup);
        	
        	// 点击时是否有回调方法
        	var callbackStr = $dropdown.data("callback");
        	if (callbackStr) {
        		var callback = new Function("type", callbackStr);
        		callback(value);
        	}
        });
    };
    
    var modal = $.fn.modal,
        height = document.documentElement.clientHeight;
	$.fn.modal = function (options) {

		var modalBody = options.modalBody,
			$this = $(this);
		
		$this.off("show.bs.modal").on('show.bs.modal', function (e) {
			
			  var $this = $(this),
			      $md   = $this.find(".modal-dialog");

			  $md.css("top", ( height - getHeightFormHTMLText(modalBody))/2);
		});
		
		modal.call(this, options);

		
		if (modalBody)	$this.find(".modal-body").html(modalBody);
		
    };
});