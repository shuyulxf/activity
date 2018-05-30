<div class="row form-item" id="${item.formAttrName}-timePicker">
	<input name="ruleIds" value="${item.ruleIds}" type="hidden" />
	<div class="col-md-3 col-lg-3 form-label">
	      <label class="control-label">${item.formAttrNameLabel}:</label>
  	</div>
  	<#if activity?? && activity[item.formAttrName]??>
		<#assign tvalue=activity[item.formAttrName]!"">
	<#elseif activity?? && attrValues[item.formAttrName]??>
		<#assign tvalue=attrValues[item.formAttrName]!"">
	<#else>
		<#assign tvalue=item.formAttrNameDefaultValue!"">
	</#if>
  	<div class="col-md-5  col-lg-5 comp-group">
        <div class="datetime-wrap col-md-4 col-lg-4">
	        <div class="input-group date form_date datetime" data-date="" data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="${item.formAttrName}" data-link-format="yyyy-mm-dd hh:ii:ss">
	            <input class="form-control" size="16" type="text"  value="${tvalue!""}" readonly>
	            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
			 </div>
        </div>
		<input type="hidden" id="${item.formAttrName}" name="${item.formAttrName}" ${item.formAttrNameValidation!""} value="${tvalue!""}"/><br/>
		<#if item.hasReplyForFormAttr == 1><#include "/actFormCompForRule/reply.ftl"></#if>
		<div = class="error">${item.formAttrIntroduce!""}</div>
	</div>
</div>

<script type="text" class="plugin-init">
		$("#${item.formAttrName}-timePicker").find('.datetime').datetimepicker({
			language:  'zh-CN',
	        format: "yyyy-mm-dd hh:ii:ss",
	        autoclose: true,
	        todayBtn: true,
	        pickerPosition: "bottom-left"
	    });
</script>