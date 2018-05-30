<div class="row reply" style="margin-top: 10px;">
	<div class="row">	
		<div class="col-md-3  col-lg-3  form-label">
			<label>成功回复语:</label>
		</div>
		<div class="col-md-7  col-lg-7">
		    <#assign attrName="${item.formAttrName}ReplySuc">
		    
			<input type="text" name="${item.formAttrName}ReplySuc"  value="<#if replyValues??>${replyValues[attrName]!""}</#if>" class="form-control">
		</div>
	</div>
	<div class="row">	
		<div class="col-md-3  col-lg-3 form-label">
			<label>失败回复语:</label>
		</div>
		<div class="col-md-7  col-lg-7">
			<#assign attrName="${item.formAttrName}ReplyFail">
			<input type="text" name="${item.formAttrName}ReplyFail"  value="<#if replyValues??>${replyValues[attrName]!""}</#if>" class="form-control">
		</div>
	</div>
</div>