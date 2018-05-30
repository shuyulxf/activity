<style type="text/css">
	.award{
	    margin-bottom: 15px;
	}
	.award .add{
		margin-left: 14px;
		margin-top: 5px;
	}
	.award .dropdown{
		display: inline-block;
	}
	.award .ipt{
		width: 120px;
		display: inline-block;
	}
	.award .award-item{
		margin-bottom: 10px;
	}
	.award .del{
		border-radius: 35px;
	}
	.award .error{
		margin-left: 14px;
		margin-top: 3px;
	}
	.award .comp-group{
		padding: 0;
	}
	.award .btn-group input{
		width: 75px;
    	font-size: 12px;
	}
</style>
<div class="row form-item" id="${item.formAttrName}-award">
	<input name="ruleIds" value="${item.ruleIds}" type="hidden" />
	<div class="col-md-3 col-lg-3 form-label">
	      <label>${item.formAttrNameLabel}:</label>
  	</div>
  	<div class="col-md-9  col-lg-9">
  		<div class="list">
  			<div class="row award">
  				<div class="col-md-12  col-lg-12 comp-group">
  					<input name="${item.formAttrName}"  form-type="data"  type="hidden" class="award-value" ${item.formAttrNameValidation!""} value=<#if attrValues?? && attrValues.activityAwardList??>${attrValues.activityAwardList!""}<#else>"${item.formAttrNameDefaultValue!""}"</#if>  />
	  			</div>
	  			<div class="col-md-12  col-lg-12">
  				 	<#if attrValues?? && attrValues.activityAwardList??>
  				 		<#assign awards=attrValues.activityAwardList?eval>
  				 		<#list awards as award>
  				 			<div class="award-item clear">
	  						<div class="col-md-1  col-lg-1">
			  					
			  				</div>
			  				
			  				<div class="col-md-3  col-lg-3 comp-group">
			  					奖品: 
			  					<div class="dropdown">
						 		   <button type="button" class="btn btn-default dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								     <span class="award-type btn-value">${award.typeName!""}</span>
								     <input value="${award.type!""}" class="btn-value-input" type="hidden" readonly/>
								     <span class="caret"></span>
								   </button>
								   <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
								   	 <li data-id="${award.type!""}">${award.typeName!""}</li>
								   </ul>
								</div>
						    </div>
			  				<div class="col-md-3  col-lg-3 comp-group">
			  					总量:  <input class="form-control ipt award-num" readonly value="${award.num!""}" placeholder="请填写数值"/>
			  				</div>
			  				<div class="col-md-4 col-lg-4 comp-group">
		  					单次发放量:  <div class="btn-group" role="group" aria-label="...">
										  <input type="text"  data-id="1" number="number" class="btn btn-default" value="${award.numStart!""}" placeholder="最低值">
										  <input type="text"  data-id="2" number="number" class="btn btn-default" value="${award.numEnd!""}" placeholder="最高值">
										  <input type="text"  data-id="3" number="number" class="btn btn-default" value="${award.distance!""}" placeholder="增长间隔">
										</div>
		  					</div>
		  				</div>
  				 		</#list>
  				 	</#if>
  				 	<button type="button" class="btn btn-primary add">添加</button>
  				 	<div = class="error">${item.formAttrIntroduce!""}</div>
  					<div class="hide award-item clear">
  						<div class="col-md-1  col-lg-1">
		  					<button type="button" class="btn btn-default del" autocomplete="off">X</button>
		  				</div>
		  				
		  				<div class="col-md-3  col-lg-3 comp-group">
		  					奖品: 
		  					<div class="dropdown">
					 		   <button type="button" class="btn btn-default dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							     <span class="award-type btn-value"></span>
							     <input value="" class="btn-value-input" type="hidden"/>
							     <span class="caret"></span>
							   </button>
							   <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
							   </ul>
							</div>
					    </div>
		  				<div class="col-md-3  col-lg-3 comp-group">
		  					总量:  <input class="form-control ipt award-num" placeholder="请填写奖品总量"/>
		  				</div>
		  				<div class="col-md-4 col-lg-4 comp-group">
		  					单次发放量:  <div class="btn-group" role="group" aria-label="...">
										  <input type="text"  data-id="1" number="number" class="btn btn-default" value="1" placeholder="最低值">
										  <input type="text"  data-id="2" number="number" class="btn btn-default" value="1" placeholder="最高值">
										  <input type="text"  data-id="3" number="number" class="btn btn-default" value="1" placeholder="增长间隔">
										</div>
		  				</div>
	  				</div>
  				</div>
  			</div>
		</div>
	</div>
	
</div>
<script type="text" class="plugin-init">

	function Award(opts) {
		
		var defaultOpts = {
            $el: null
		}
        
        if (opts == null || opts.$el == null) return null; 
        this.options = $.extend(defaultOpts, opts);

        var $el = opts.$el;
        
        this.options = $.extend(this.options, {
        	$add: $el.find(".add"),
        	$hide: $el.find(".award-item.hide"),
        	$ipt : $el.find(".award-value")
        });
        
		this.init();
	}
	
	Award.prototype = {
		init: function() {
            var opts = this.options,
                $el  = opts.$el,
                $add = opts.$add,
                $hide = opts.$hide,
                $ipt = opts.$ipt;
                
            var that = this;
            
            
	    	var $groups = $el.find(".btn-group input"),
	    	    $start = $($groups[0]),
	    	    $end = $($groups[1]),
	    	    $distance = $($groups[2]);
	    	    
	    	$groups.on("input", function() {
	    		that.updateWidgetValue();
	    	});
	    	
            
            $add.on("click", function(e){
            	
            	var $item = $hide.clone().removeClass("hide");
            	$add.before($item);
            	
            	var $awardNum = $item.find(".award-num");
            	$awardNum.attr("number", "number");
            	
            	var $groups = $item.find(".btn-group input");
            	$groups.on("input", function() {
            		that.updateWidgetValue();
            	});
            	
		    	$awardNum.on("input", function() {
		    		that.updateWidgetValue();
		    	});
		    	
		    	$item.find(".dropdown-menu li").on("click", function() {
		    
		    		that.updateWidgetValue();
		    	});
		    	
            	$item.find(".dropdown-toggle").dropdown();
            });
            
            //bind event for item del
            $el.on("click", ".del", function() {
            
            	var $this = $(this);
            	$this.parents(".award-item").remove();
            	that.updateWidgetValue();
            });
            
            this.initAwardType();
        },
        isNum: function(str) {
        	var rgx = /\d+/g;
        	rgx.lastIndex = 0;
        	return rgx.test(str);
        },
        updateWidgetValue: function() {
       
        	  var opts = this.options,
	              $el  = opts.$el,
	              $ipt = opts.$ipt;
	              
	          var $awardItems = $el.find(".award-item:not('.hide')"),
	              len = $awardItems.length;
	     
	          var value = [],
	              map = {};
	              
	          for(var i = 0; i < len; i++) {
	          
	          	  var $item = $($awardItems[i]),
	          	      $awardType = $item.find(".btn-value-input"),
	          	      $awardTypeName = $item.find(".btn-value"),
	          	      $awardNum  = $item.find(".award-num");
	          	      
	          	  var $groups = $item.find(".btn-group input"),
	          	      $start = $($groups[0]),
	          	      $end = $($groups[1]),
	          	      $distance = $($groups[2]);
	          	  
	          	  var type = $awardType.val(),
	          	      num = $.trim($awardNum.val()),
	          	      typeName =   $.trim($awardTypeName.text()),
	          	      isNum = this.isNum,
	          	      start = $.trim($start.val()),
	          	      end = $.trim($end.val()),
	          	      distance = $.trim($distance.val());
	          	   
	          	  
	          	  if (type && isNum(type) && num && isNum(num) && start && isNum(start) && end && isNum(end) && distance && isNum(distance) && !map[type]) { 
	          	  	  var obj = {};
	          	  	  obj.type = type;
	          	  	  obj.typeName = typeName;
	          	  	  obj.num = num;
	          	  	  obj.numStart = start;
	          	  	  obj.numEnd = end;
	          	  	  obj.distance = distance;
	          	  	  value.push(obj);
	          	  	  map[type] = 1;
	          	  }
	          }
	       	
	          $ipt.val(JSON.stringify(value));
        },
        initAwardType: function() {
        
        	var opts = this.options,
	            $hide  = opts.$hide;
	            
        	$.ajax({
    			type: "POST",
    			url:  "${BASEPATH}/formdata/action/prizelist",
    			timeout: 600000,
    			dataType: "json",
    			success: function(data) {
    			
    			    if (data.status == 0) {
    			        try {
	    			        var datas = JSON.parse(data.data);
	    			        var list = [];
		    			    var len = datas.length;
		    			    for (var i = 0; i < len; i++) {
		    			    	var obj = datas[i];
		    			    	list.push("<li data-id='" + obj.id + "'>" + obj.value + "</li>");
		    			    	if (i == 0) {
		    			    		$hide.find(".btn-value").text(obj.value);
		    			    		$hide.find(".btn-value-input").val(obj.id);
		    			    	}
		    			    }
		    			    $hide.find(".dropdown-menu").html(list.join(""));
		    			    $hide.find(".dropdown-toggle").dropdown();

		    			} catch(e) {
		    				console.log(e);
		    			}
    			    }
    			} 
	        });
        }
	}
    
    var Award = new Award({
        $el: $("#${item.formAttrName}-award").find(".award")
    })
	
</script>