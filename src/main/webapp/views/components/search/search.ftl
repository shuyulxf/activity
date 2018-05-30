<style>
	.col-lg-6 [name=phoneNumbers] {
		width: 47%!important;
	}
	.ml-30{
		margin-left: 30px;
	}
</style>
<#if paramStr??>
<#assign params = paramStr?eval>
<#else>
<#assign params={}>
</#if>
<form class="search clear">
  <#if hasPhoneSearch??>
  <div class="<#if isUsePrizeSel??>col-md-6  col-lg-6<#else>col-md-4 col-lg-4</#if> comp-group">
    <label for="activityName">手机号码:</label>
	<input type="text" name="phoneNumbers"  value="<#if params??>${params.phoneNumbers!""}</#if>" placeholder="填写格式如下：13211,18917612233" class="form-control">
  	
  	<#if isUsePrizeSel??>
  	<#assign widget={"defaultLabel":"请选择用于手机号码excel","phoneNumberIpt":"$('[name=phoneNumbers]')"}>
  	<#include "../../widget/importAndReadExcel.ftl"/>  
  	</#if>
  </div>
  </#if>
  <div class="form-group col-md-3 col-lg-3">
    <label for="activityName">活动名称:</label>
    <#assign fromAttrs="{\"formAttrDataUrl\":\"/formdata/action/activitymap\"}">
    <#if params??>
    <#assign widget={"name":"activityIds","value":params.activityIds!"","fromAttrs":fromAttrs}>
    <#else>
    <#assign widget={"name":"activityIds","fromAttrs":fromAttrs}>
    </#if>
    <#include "../../widget/multiselect.ftl"/>  
  </div>
  <div class="form-group col-md-3 col-lg-3">
    <label for="activityProvince">活动地区:</label>
    <#assign fromAttrs="{\"formAttrDataUrl\":\"/formdata/action/province\"}">
    <#if params??>
    <#assign widget={"name":"activityProvince","value":params.activityProvince!"","fromAttrs":fromAttrs}>
    <#else>
    <#assign widget={"name":"activityProvince","fromAttrs":fromAttrs}>
    </#if>
    <#include "../../widget/multiselect.ftl"/>  
  </div>
  <div class="form-group col-md-4 col-lg-4">
    <label for="activityApplyCode">活动应用渠道:</label>
    <#assign fromAttrs="{\"formAttrDataUrl\":\"/formdata/action/applycode\"}">
    <#if params??>	
   	<#assign widget={"name":"activityApplyCode","value":params.activityApplyCode!"","fromAttrs":fromAttrs}>
   	<#else>
   	<#assign widget={"name":"activityApplyCode","fromAttrs":fromAttrs}>
   	</#if>
    <#include "../../widget/multiselect.ftl"/>  
  </div>
  <#if isUsePrizeSel??>
  <div class="form-group col-md-3 col-lg-3">
    <label for="activityProvince">奖品类型:</label>
    <#assign fromAttrs="{\"formAttrDataUrl\":\"/formdata/action/prizelist\"}">
    <#if params??>
    <#assign widget={"name":"awardTypeIds","value":params.awardTypeIds!"","fromAttrs":fromAttrs}>
    <#else>
    <#assign widget={"name":"awardTypeIds","fromAttrs":fromAttrs}>
    </#if>
    <#include "../../widget/multiselect.ftl"/>  
  </div>
  </#if>
  <div class="form-group col-md-4 col-lg-4">
    <div class="form-group">
        <label for="startTime" class="control-label">开始时间:</label>
        <div class="datetime-wrap col-md-4 col-lg-4">
	        <div class="input-group date form_date datetime" data-date="" data-link-field="startTime" data-link-format="yyyy-mm-dd hh:ii:ss">
	            <input class="form-control" size="16" type="text"  value="<#if params??>${params.startTime!""}</#if>" readonly>
	            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
			 </div>
        </div>
		<input type="hidden" id="startTime" name="startTime" value="<#if params??>${params.startTime!""}</#if>"/><br/>
    </div>
  </div>
  <div class="form-group col-md-4 col-lg-4">
    <div class="form-group">
        <label for="endTime" class="control-label">结束时间:</label>
        <div class="datetime-wrap col-md-4 col-lg-4">
	        <div class="input-group date form_date datetime" data-date=""  data-link-field="endTime" data-link-format="yyyy-mm-dd hh:ii:ss">
	            <input class="form-control" size="16" type="text" value="<#if params??>${params.endTime!""}</#if>" readonly>
	            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
			 </div>
        </div>
		<input type="hidden" id="endTime" value="<#if params??>${params.endTime!""}</#if>" name="endTime"/><br/>
    </div>
  </div>
  <div class="form-group col-md-4 col-lg-4">
  <button type="submit" class="btn btn-primary">搜索</button>
  <#if hasExportBtn??>
  <button type="button" class="btn btn-primary ml-30" id="${exportParams.btnIds}">导出</button>
  <script>
   var formSerializeTrim = function(s) {
		s = $.trim(s);
		s = s.replace(/=(\+)+|(\+)+[=|$]/g,"="); 
		s = s.replace(/(\+)*\&(\+)*/g,"&");
		return  decodeURIComponent(s);  
   }
   var jsonFromSerialize = function(s) {
		
		if (s == null || s.length == 0) return {};
		
		var params = (formSerializeTrim(s)).split("&"),
		    len = params.length,
		    json = {};
		for(var i = 0; i < len; i++) {
			var param = params[i];
			if (param && param.length > 0) {
				var idx = param.indexOf("="),
				    value = param.substr(idx + 1);
				if (value)  {
					
					value = value.replace(/\"/g, "'");
					json[param.substr(0, idx)] = value ? value.replace(/\+/g, " ") : value;
				}
			}
		}

		return json;
	}
	
  	function createHidenForm() {
    	var form = $("<form>");
        form.attr('style', 'display:none');
        form.attr('target', '');
        form.attr('method', 'post');
        form.attr('action', '/activity/data/export');
        
        // create params
        var params = jsonFromSerialize($(".search").serialize());
        var $input = $('<input>');
	   	 $input.attr('type', 'hidden');
	   	 $input.attr('name', 'paramStr');
	   	 $input.attr('value', JSON.stringify(params));
	   	 $input.attr('name', 'type');
	   	 $input.attr('value', ${exportParams.type});
	   	 form.append($input);
   	 
        $('body').append(form);
        
        return form;
    }
    
    function submitHiddenForm(form) {
    	form.submit();
    }
    function removeHiddenForm(form) {
    	form.remove();
    }
    
  	$("#${exportParams.btnIds}").on("click", function() {
  		 //creat form 
    	 var form = createHidenForm();
    	 submitHiddenForm(form);
    	
    	 removeHiddenForm(form);
  	});
  </script>
  </#if>
  </div>
</form>
<script src="${BASEPATH}/js/plugin/datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${BASEPATH}/js/plugin/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${BASEPATH}/js/plugin/multiselect.js"></script>
