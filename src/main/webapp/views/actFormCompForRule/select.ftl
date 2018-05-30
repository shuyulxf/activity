<div class="row form-item" id="${item.formAttrName!""}">
	<input name="ruleIds" value="${item.ruleIds!""}" type="hidden" />
	<div class="col-md-3 col-lg-3 form-label">
	      <label>${item.formAttrNameLabel!""}:</label>
  	</div>
  	<div class="col-md-5  col-lg-5 comp-group">
  		<#if activity?? && activity[item.formAttrName]??>
  			<#assign wvalue=activity[item.formAttrName]!"">
  		<#elseif activity?? && attrValues[item.formAttrName]??>
  			<#assign wvalue=attrValues[item.formAttrName]!"">
  		<#else>
  			<#assign wvalue=item.formAttrNameDefaultValue!"">
  		</#if>

  	    <#assign widget={"name":item.formAttrName, "value": wvalue, "vali": item.formAttrNameValidation!"", "className": "plugin-init", "fromAttrs":item.formNameTypeAttrs!""}>
  		
  		<#include "/widget/select.ftl">
		<#if item.hasReplyForFormAttr?? && item.hasReplyForFormAttr == 1><#include "/actFormCompForRule/reply.ftl"></#if>
		<div = class="error">${item.formAttrIntroduce!""}</div>
	</div>
</div>