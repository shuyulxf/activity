<style type="text/css">
	.multi .dropdown-menu {
		width: 373px;
	}
	.multi li{
		display: inline-block;
		width: 50%;
	}
	.multi .form-control{
		vertical-align: middle;
	}
	.multi .input-group-btn{
		vertical-align: middle;
		display: inline-block;
	}
	.multi .multiselect-filter {
		width: auto;
	}
</style>
<div class="multi btn-group " id="${widget.name!""}">
    <input type="hidden" class="btn btn-primary " name="${widget.name!""}" value="${widget.value!""}" ${widget.vali!""}/>
    <select class="items multiselect" multiple="multiple">
    	
	</select>
	<#if widget.fromAttrs?? && widget.fromAttrs?length != 0>
		<#assign formAttrs=widget.fromAttrs?eval>
	<#else>
		<#assign formAttrs={}>
	</#if>
</div>


<script type="text" class="${widget.className!""}">
	function MultiSelectWidget(opts) {

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
    MultiSelectWidget.prototype = {
        init: function() {
           
           var opts = this.options,
               $el = opts.$el,
               defaultValue = opts.defaultValue;
           
           var $hidden = $el.find("[type=hidden]"),
               $multi  = $el.find("select");
              
           var value = $hidden.val();
           if (value) defaultValue = defaultValue + "," + value;
           
           this.options.defaultValue = defaultValue;
           
           
           var changeValue = function(){
           
          		var valList = $multi.val();
          		
				if (valList && valList.length > 0) $hidden.val(valList.join(","));
				else $hidden.val("");
           }
           
           var isItemOf = function(str1, str2) {
           		var rgx = "(^|,)" + str2 + "(,|$)";
           		return (new RegExp(rgx)).test(str1);
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
			    	    	op = "<option value='" + id + "'" + (<#if widget.value??> isItemOf('${widget.value}', id) ? "selected='selected'":""<#else>""</#if>) + ">" + item.value + "</option>";
			    	    }
			    	    else {
			    	    
			    	    	op = "<option value='" + item + "'" + (<#if widget.value??> isItemOf('${widget.value}', item) ? "selected='selected'":""<#else>""</#if>) + ">" + item + "</option>";
			    	    }
			    	    items.push(op);
			    	}
			    	
			    	$multi.append(items.join(""));
			    	$multi.multiselect({
			    		includeSelectAllOption: true,
						enableFiltering: true,
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
           	render(${formAttrs.formAttrData});
           <#else>
		        $.ajax({
	    			type: "POST",
	    			url:  "${BASEPATH}/${formAttrs.formAttrDataUrl!""}",
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
        }
    }

    var nw = new MultiSelectWidget({
        $el: $("#${widget.name}")
    })
</script>