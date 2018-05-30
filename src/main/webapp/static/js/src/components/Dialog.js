define(function(require,exports,module) {
	/* simple dialog */
	function SimpleDialog(opts) {
	
	    let that = this;
	
	    let options = {
	        $container: document.getElementsByTagName("body")[0],
	        title: "Dialog Title!",
	        hasTitle : false,
	        type : "plain",
	        html : "",
	        hasCancel: false,
	        cancelBtn: "cancel",
	        cancelCallback: null,
	        hasSure: false,
	        sureBtn: "sure",
	        sureCallback:null,
	        auto: false,
	        delay: 2000,
	        hasClose: false,
	        middle: true
	    };
	
	    this.options = $.extend(options, opts);
	    this.$container = options.$container;
	    this.possessions = [];
	    this.init();
	}
	SimpleDialog.prototype = {
	    init: function(){
	
	        let that = this,
	            $dialog = document.createElement("dialog");
	        $dialog.className += "dialog ";
	        
	        $dialog.addEventListener("click", function(e){
	  
	            let t = e.target,
	                cs = t.className;
	            if (cs.indexOf("close") !== -1 && cs.indexOf("sure") !== -1 && cs.indexOf("cancel") !== -1) return;
	            
	            let {hasCancel, cancelCallback, hasSure, sureCallback} = that.options;
	            if (cs.indexOf("cancel") !== -1 && hasCancel && cancelCallback) cancelCallback();
	            if (cs.indexOf("sure") !== -1 && hasSure && sureCallback) sureCallback();
	
	            that.hide();
	        });
	        this.$dialog = $dialog;        
	    },
	    show: function(){
	
	        this.$dialog.className += " show";
	    },
	    hide: function(){
	
	        let $dialog = this.$dialog;
	        $dialog.className = $dialog.className.replace("show", "");
	    },
	    showDialog: function() {
	
	        let {auto, delay, middle} = this.options,
	            that = this;
	        var $container = this.$container,
	            $dialog = this.$dialog;
	        
	        $container.append(this.getInnerDialogNode());
	        this.show();
	        if(middle) {
	        	$($dialog).css("top", (document.documentElement.clientHeight - $($dialog).height())/2)
	        }
	        
	        
	        let timer = 0;
	        if (auto) timer = setTimeout(function(){
	            that.hide();
	            clearTimeout(timer);
	        }, delay);
	    },
	    getInnerDialogNode: function() {
	
	        let {hasTitle, title, type, html, hasClose, hasCancel, cancelBtn, hasSure, sureBtn} = this.options;
	
	        let renderOpBtnHTML = function(){
	            let sureBtnHTML = `<button class='sure btn-primary'>${sureBtn}</button>`,
	                cancelBtnHTML = `<button class='cancel'>${cancelBtn}</button>`;
	            
	            return (hasCancel || hasSure) ? `<div class="op-btns"> ${sureBtnHTML}${cancelBtnHTML}</div>` : "";
	        }
	
	        let renderCloseHTML = function() {
	            return hasClose ? `<span class="close"><i>|</i><i>|</i></span>` : "";
	        }
	       
	        var t = "title modal-title";
	        let HTML = `<p class="${hasTitle ? t : ''}">${hasTitle ? title: ""}</p>
	                <div class="dialog-main">${html ? html : ""}</div>
	                ${renderOpBtnHTML()}
	                ${renderCloseHTML()}
	            `;
	        
	        let $dialog = this.$dialog;
	        $dialog.innerHTML = HTML;
	        $dialog.className += type;
	
	        return $dialog;
	    }, 
	    cancel: function(){
	        this.hide();
	    },
	    sure: function(){
	
	    }
	}
	
	module.exports = SimpleDialog;
});