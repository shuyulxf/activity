<div class="row form-item" id="${item.formAttrName}-number">
	<input name="ruleIds" value="${item.ruleIds}" type="hidden" />
	<div class="col-md-3 col-lg-3 form-label">
	      <label>${item.formAttrNameLabel}:</label>
  	</div>
  	<div class="col-md-5  col-lg-5 comp-group">
  		<#if activity?? && activity[item.formAttrName]??>
  			<#assign nvalue=activity[item.formAttrName]!"">
  		<#elseif activity?? && attrValues[item.formAttrName]??>
  			<#assign nvalue=attrValues[item.formAttrName]!"">
  		<#else>
  			<#assign nvalue=item.formAttrNameDefaultValue!"">
  		</#if>
  		<#assign formAttrs=item.formNameTypeAttrs!"">
  	    <#assign numAttr={"name":item.formAttrName, "number": nvalue, "vali": item.formAttrNameValidation!"", "className": "plugin-init","formAttrs":formAttrs}>
  		<#include "/widget/number.ftl">
		<#if item.hasReplyForFormAttr == 1><#include "/actFormCompForRule/reply.ftl"></#if>
		<div = class="error">${item.formAttrIntroduce!""}</div>
	</div>
</div>

