<div class="row form-item" id="${item.formAttrName}">
	<input name="ruleIds" value="${item.ruleIds!""}" type="hidden" />
	<div class="col-md-3 col-lg-3 form-label">
	      <label>${item.formAttrNameLabel}:</label>
  	</div>
  	<div class="col-md-5  col-lg-5 comp-group">
  		<#if activity?? && activity[item.formAttrName]??>
  			<#assign ivalue=activity[item.formAttrName]!"">
  		<#elseif activity?? && attrValues[item.formAttrName]??>
  			<#assign ivalue=attrValues[item.formAttrName]!"">
  		<#else>
  			<#assign ivalue=item.formAttrNameDefaultValue!"">
  		</#if>
  	
		<input type="text" name="${item.formAttrName}"  value="${ivalue}" class="form-control">
		<div = class="error">${item.formAttrIntroduce!""}</div>
		<#if item.hasReplyForFormAttr == 1><#include "/actFormCompForRule/reply.ftl"></#if>
	</div>
</div>