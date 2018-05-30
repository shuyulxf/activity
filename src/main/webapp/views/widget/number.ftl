<style type="text/css">
	.number .number-input{
        width: 60px;
        border: 1px solid rgb(204, 204, 204);
    }
    .number .btn-error{
        border: 1px solid #d9534f;
        z-index: 1;
    }
</style>
<div class="number btn-group" id="${numAttr.name!""}-num">
    <button type="button" class="btn btn-default sub-num">-</button>
    <input type="text" class="btn btn-default number-input" <#if numAttr.name??>name="${numAttr.name}</#if>" value="${numAttr.number!""}" number ${numAttr.vali!""}/>
    <button type="button" class="btn btn-default add-num">+</button>
</div>
<script type="text" class="${numAttr.className!""}">
	function NumberWidget(opts) {

        var defaultOpts = {
            $el: null,
            min: 0,
            max: null,
            dis: 1
		}

        var $el = opts.$el;
        if (!$el || $el.length == 0) return null;
        
		var rgx = /^\d+(\.\d+)?$/g;
		if (!opts.max || !rgx.test(opts.max) )  delete opts.max;
		if (!opts.min || !rgx.test(opts.min) )  opts.min = 0;

		var dis = opts && opts.dis ? opts.dis : defaultOpts.dis;
		var fixed = 0;
		
		function getBase(dis) {
		
			var base = 1;
			while(dis < 1) {
				dis *= 10;
				base *= 10;
				fixed++;
			}
			
			return base;
		}
		
        this.options = $.extend(defaultOpts, opts, {
            $subNum: $el.find(".sub-num"),
            $addNum: $el.find(".add-num"),
            $numIpt: $el.find(".number-input"),
            base : dis && getBase(dis),
            fixed: fixed
        });
        

        this.init();
    }
    NumberWidget.prototype = {
        toFixed: function(num, fixed) {
			var obj = new Number(num);
			return obj.toFixed(fixed);
        },
        init: function() {
            var opts = this.options,
                $subNum = opts.$subNum,
                $addNum = opts.$addNum,
                $numIpt = opts.$numIpt,
                min = opts.min,
                max = opts.max,
                dis = opts.dis,
                base = opts.base,
                fixed = opts.fixed,
                toFixed = this.toFixed;

            var v = $numIpt.val();
            //if (!v) $numIpt.val(min);

            var rgx = /^ *\d+(\.\d+)? *$/g;
            $subNum.click(function() {
                rgx.lastIndex = 0;
                var val = $numIpt.val();
                if (!val) val = '0';
                if (rgx.test(val) && +val > min) {
                    $numIpt.removeClass("btn-error");
                    
                    val = ((+val) * base - dis * base) / base;
                    
                    $numIpt.val(toFixed(val, fixed));
                }
                else $numIpt.addClass("btn-error");
            });

            $addNum.click(function(){
            	var val = $numIpt.val();
          		if (!val) val = '0';
                rgx.lastIndex = 0;
                if (rgx.test(val) && (max && +val < max || !max)) {
                    $numIpt.removeClass("btn-error");
                    val = ((+val) * base + dis * base) / base;
                    
                    $numIpt.val(toFixed(val, fixed));
                }
                else $numIpt.addClass("btn-error");
            })

            $numIpt.on("input", function() {
                rgx.lastIndex = 0;
                var val = $numIpt.val();
               
                if (!rgx.test(val)) $numIpt.addClass("btn-error");
                else $numIpt.removeClass("btn-error");
            })
        }
    }
	
	<#if numAttr.formAttrs?? && numAttr.formAttrs?length != 0>
		<#assign formAttrs = numAttr.formAttrs?eval>
		var nw = new NumberWidget({
			$el: $("#${numAttr.name}-num"),
			<#if formAttrs.formNumMin??>min: ${formAttrs.formNumMin},</#if>
	        <#if formAttrs.formNumMax??>max: ${formAttrs.formNumMax},</#if>
	        <#if formAttrs.formNumDistance??>dis: ${formAttrs.formNumDistance}</#if>
		});
	<#else>
		 var nw = new NumberWidget({
	        $el: $("#${numAttr.name}-num"),
	        <#if numAttr.min??>min: ${numAttr.min},</#if>
	        <#if numAttr.max??>max: ${numAttr.max},</#if>
	        <#if numAttr.dis??>dis: ${numAttr.dis}</#if>
	    })
	</#if>
   
</script>