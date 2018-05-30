<div class="row form-item" id="${item.formAttrName}-radio">
	<input name="ruleIds" value="${item.ruleIds}" type="hidden" />
	<div class="col-md-3 col-lg-3 form-label">
	      <label>${item.formAttrNameLabel}:</label>
  	</div>
  	    <#if activity?? && activity[item.formAttrName]??>
  			<#assign rvalue=activity[item.formAttrName]!"">
  		<#elseif activity?? && attrValues[item.formAttrName]??>
  			<#assign rvalue=attrValues[item.formAttrName]!"">
  		<#else>
  			<#assign rvalue=item.formAttrNameDefaultValue!"">
  		</#if>
	
  	<div class="col-md-5  col-lg-5 comp-group">
  		<div class="radio">
		  <label>
		    <input type="radio" name="${item.formAttrName}"  form-type="data" value="true" ${item.formAttrNameValidation!""} <#if rvalue?index_of("true") != -1>checked</#if>>
		    是
		  </label>
		</div>
		<div class="radio">
		  <label>
		    <input type="radio" name="${item.formAttrName}"  form-type="data"  value="false" <#if rvalue?index_of("false") != -1>checked</#if>>
		   不 是
		  </label>
		</div>
		<#if item.hasReplyForFormAttr == 1><#include "/actFormCompForRule/reply.ftl"></#if>
		<div = class="error">${item.formAttrIntroduce!""}</div>
	</div>
</div>

<script type="text" class="plugin-init">

	var initPlugins = {
		isActivityAllKeywordsTriggerType: function($radio) {
			
			var $check = $radio.find("[name=isActivityAllKeywordsTriggerType]:checked");
			if ($check.length == 0) return;
			var s = $check.val();
			
			var $activityTriggerKeywords = $("#activityTriggerKeywords-keyword");
			if (s && s == "true") $activityTriggerKeywords.hide();
			else {
				 $activityTriggerKeywords.find("[name=activityTriggerKeywords]").attr("required","true");
			}
		},
		isSuppliedSeriesAwardForJoinActivity: function($radio) {
		    
			var $check = $radio.find("[name=isSuppliedSeriesAwardForJoinActivity]:checked");
			if ($check.length == 0) return;
			var s = $check.val();
			
			var $numForSeriesAward = $("#numForjoinActivityToEarnSeriesAward-number");
			var $prizeIdsForSeriesAward = $("#prizeIdsForSeriesAward-multi");
			if (s == "false") {
				$numForSeriesAward.addClass("hide");
				$numForSeriesAward.removeAttr("number");
				$prizeIdsForSeriesAward.addClass("hide");
				$prizeIdsForSeriesAward.removeAttr("required");
			}
			else {
				$numForSeriesAward.removeClass("hide");
				$numForSeriesAward.attr("number", "number");
				$prizeIdsForSeriesAward.removeClass("hide");
				$prizeIdsForSeriesAward.attr("required", "required");
			}
		}
	}
	
	var initEvents = {
		isActivityAllKeywordsTriggerType: function($radio) {
		
			var $activityTriggerKeywords = $("#activityTriggerKeywords-keyword");
			if ($radio.find("[name=isActivityAllKeywordsTriggerType]").length == 0) return;
			
			$radio.on("change", function(){
				
				var $check = $radio.find("[name=isActivityAllKeywordsTriggerType]:checked");
				if($check.length == 0) return;
				
				var v = $check.val();
				if (v == "true") {
					$activityTriggerKeywords.hide();
					$activityTriggerKeywords.find("[name=activityTriggerKeywords]").removeAttr("required");
				} else {
					$activityTriggerKeywords.show();
					
				}

			});
		},
		isSuppliedSeriesAwardForJoinActivity: function($radio) {
		
			var $numForSeriesAward = $("#numForjoinActivityToEarnSeriesAward-number");
			var $prizeIdsForSeriesAward = $("#prizeIdsForSeriesAward-multi");
			
			$radio.on("change", function(){
				
				var $check = $radio.find("[name=isSuppliedSeriesAwardForJoinActivity]:checked");
			
				if ($check.length == 0) return;
				var s = $check.val();
	
				
				if (s == "false") {
					$numForSeriesAward.addClass("hide");
					$numForSeriesAward.removeAttr("number");
					$prizeIdsForSeriesAward.addClass("hide");
					$prizeIdsForSeriesAward.removeAttr("required");
				}
				else {
					$numForSeriesAward.removeClass("hide");
					$numForSeriesAward.attr("number", "number");
					$prizeIdsForSeriesAward.removeClass("hide");
					$prizeIdsForSeriesAward.attr("required", "required");
				}

			});
			
		}
	}
	
	function init() {
	
		var $radioWrap = $("#${item.formAttrName}-radio");
		var $radio = $radioWrap.find(".radio");

		
		var plugin = initPlugins["${item.formAttrName}"];
		if (plugin)	plugin($radio);
		
		var e = initEvents["${item.formAttrName}"];
		if(e) e($radio);
	}
	
	
	init();
</script>