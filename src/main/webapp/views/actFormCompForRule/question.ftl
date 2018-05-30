
<div class="row form-item hide" id="${item.formAttrName!""}-question">
	<input name="ruleIds" value="${item.ruleIds!""}" type="hidden" />
	<div class="col-md-3 col-lg-3 form-label">
	      <label>${item.formAttrNameLabel!""}:</label>
  	</div>
  	<div class="col-md-6  col-lg-6 comp-group">
  		<#if activity?? && activity[item.formAttrName]??>
  			<#assign qvalue=activity[item.formAttrName]!"">
  		<#elseif activity?? && attrValues[item.formAttrName]??>
  			<#assign qvalue=attrValues[item.formAttrName]!"">
  		<#else>
  			<#assign qvalue=item.formAttrNameDefaultValue!"[]">
  		</#if>
  		<#assign formAttrs=item.formNameTypeAttrs!"">
  		
  	    <#assign widget={"name":item.formAttrName, "value": qvalue, "vali": item.formAttrNameValidation!"", "className": "plugin-init","formAttrs":formAttrs}>
  		
		<#include "/widget/question.ftl">
		<#if item.hasReplyForFormAttr == 1><#include "/actFormCompForRule/reply.ftl"></#if>
		<div = class="error">${item.formAttrIntroduce!""}</div>
	</div>
	
</div>
