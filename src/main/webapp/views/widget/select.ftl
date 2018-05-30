<div class="select btn-group " id="${widget.name!""}-select">
    <input type="hidden" class="btn btn-primary value" <#if !(widget.hasName??)>name="${widget.name!""}"</#if> value="${widget.value!""}" ${widget.vali!""}/>
    <select class="items multiselect">
    	
	</select>
	<#if widget.fromAttrs?? && widget.fromAttrs?length != 0>
		<#assign formAttrs=widget.fromAttrs?eval>
	<#else>
		<#assign formAttrs={}>
	</#if>
</div>
<script type="text" class="${widget.className!""}">
	function SelectWidget(opts) {
        var defaultOpts = {
            $el: null
		}

        var $el = opts.$el;
        if (!$el || $el.length == 0) return null;
		
        this.options = $.extend(defaultOpts, opts);
     
        var defaultValues = "${widget.value!""}",
            defaultMap = {};
        if (defaultValues) {
        	var list = defaultValues.split(","),
        	    len  = list.length;
        	    
        	for (var i = 0; i < len; i++) {
        		var v = list[i];
        		if (!defaultMap[v]) defaultMap[v] = 1;
        	}
        }
		this.options.defaultMap = defaultMap;
		
        this.init();
    }
    SelectWidget.prototype = {
        init: function() {

           var opts = this.options,
               $el = opts.$el,
               defaultMap = opts.defaultMap;
      
           var $hidden = $el.find(".value"),
               $multi  = $el.find(".multiselect");
           
           var clbStr = "<#if formAttrs.formAttrChangeCallback??>${formAttrs.formAttrChangeCallback?replace("\r\n", "")}</#if>";
           var callback;
           if (clbStr) callback = new Function(clbStr);
         
           
           var changeValue = function(){
           
          		var valList = $multi.val();
				if (valList && valList.length > 0) $hidden.val(valList);
				else $hidden.val("");
				
				$hidden.trigger('change');
				
				if (callback) callback();
           }
           
           function render(datas) {

           		var items = [];
           		if (datas && Array.isArray(datas)) {
		    	             
			    	var len = datas.length;
			    	for (var i = 0; i < len; i++) {
			    		var item = datas[i];
			    		
			    		var op;
			    	    if (typeof item == "object") {
			    
			    	    	var id = item.id + "";
			    	    	op = "<option value='" + id + "'" + (<#if widget.value??> '${widget.value}' == id ? "selected='selected'":""<#else>""</#if>) + ">" + item.value + "</option>";
			    	    }
			    	    else {
			    	    	op = "<option value='" + item + "'" + (<#if widget.value??> '${widget.value}' == item ? "selected='selected'":""<#else>""</#if>) + ">" + item + "</option>";
			    	    }
			    		items.push(op);
			    		
			    		var $hidden =  $("#${widget.name!""}-select").find(".value");
			    		if (i == 0 && !$hidden.val()) {
			    			if (typeof item == "object") {
			    				$hidden.val(item.id)
			    			} else {
			    				$hidden.val(item);
			    			}
			    		}
			    	}
			    	
			    	$multi.append(items.join(""));
			    	$multi.multiselect({
						onChange: function(option, checked, select) {
							changeValue();
						},
						onSelectAll: function() {
				           changeValue();
				        },
						onDropdownHide: function(){
							changeValue();
						}
						
				   });
			    }
           }
           
           <#if formAttrs.formAttrData?? && formAttrs.formAttrData?length != 0>
            var datas = ${formAttrs.formAttrData};
           	render(datas);
           <#elseif formAttrs.formAttrDataUrl?? && formAttrs.formAttrDataUrl?length != 0>
          		
		        $.ajax({
	    			type: "POST",
	    			url:  "${BASEPATH}/${formAttrs.formAttrDataUrl}",
	    			timeout: 600000,
	    			dataType: "json",
	    			success: function(data) {
	    			
	    			    if (data.status == 0) {
	    			        try {
		    			        var datas = JSON.parse(data.data);
			    			    render(datas);
			    			} catch(e) {
			    				console.log(e);
			    			}
	    			    }
	    			} 
		        });
	        </#if>
	        
            //init state
            if (callback) callback();
        }
    }

    var nw = new SelectWidget({
        $el: $("#${widget.name!""}-select")
    })
</script>