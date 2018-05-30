<#include "../components/header/header.ftl"/>  
<title>${TITLE}</title>
<link href="${BASEPATH}/css/pages/rule.css" rel="stylesheet" type="text/css">
<#include "../components/sidebar/nav.ftl"/>  
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div class="clear direct">
	  <#include "../components/breadcrumb/breadcrumb.ftl"/>  
	  <#include "../components/timer/timer.ftl"/>  
	</div>
	<div class="panel panel-default form">
	  <div class="panel-body">
		<table class="table">
	    	<thead>
                <tr>
                  <th>序号</th>
                  <th>name属性</th>
                  <th>所属互斥规则组</th>
                  <th>所属活动属性类别</th>
                  <th>表单组件类型</th>
                  <th>Label</th>
                  <th>默认值</th>
                  <th>校验类型</th>
                  <th>表单组件填写类型所需相关属性</th>
                  <th>是否设置回复语</th>
                  <th>展示顺序</th>
                  <th>创建时间</th>
                  <th>创建者</th>
                  <th>操作</th>
                </tr>
             </thead>
             <tbody>
             	<#if forms?? >
             	<#list forms as form>
			            <tr>
							<td>${form?index + 1}</td>
	             		    <td  style="max-width: 120px; word-wrap: break-word;">${form.formAttrName}</td>
	             		    <td  style="width: 120px; word-wrap: break-word;">${form.exRuleGroupName}</td>
	             		    <td  style="width: 80px; word-wrap: break-word;">${form.formAttrNameType}</td>
		             		<td>${form.formAttrNameFillType}</td>
	             		    <td  style="width: 100px; word-wrap: break-word;">${form.formAttrNameLabel}</td>
		             		<td>${form.formAttrNameDefaultValue!""}</td>
		             		<td>${form.formAttrNameValidation!""}</td>
		             		<td style="max-width: 100px; word-wrap: break-word;">
		             			<#if form.formNameTypeAttrs??>
		             				<pre class="form-detail">${form.formNameTypeAttrs!""}</pre>
		             			</#if>
		             		</td>
		             		<td style="width: 80px; word-wrap: break-word;"><#if form.hasReplyForFormAttr?contains("1")>是<#else>否</#if></td>
		             		<td>${form.orderForForm!""}</td>
		             		<td>${form.getCreateTime()}</td>
		             		<td>${form.getCreateUser()}</td>
		             		<td>
		             			<span class="iconfont icon-bianji" data-formAttrInfoId="${form.formAttrInfoId}"></span>
								<span class="iconfont icon-delete" data-formAttrInfoId="${form.formAttrInfoId}"></span>
							</td>
						</tr>
				 	</#list>
				<#else>
					<tr>
             		    <td></td>
             		    <td></td>
             		    <td></td>
             		    <td></td>
             		    <td></td>
	             		<td style="text-align: right;">
	             			暂时无数据
	             		</td>
	             		<td></td>
	             		<td></td>
	             		<td></td>
	             		<td></td>
	             		<td></td>
             		</tr>
				</#if>
             </tbody>
	    </table>
	  </div>
	  
	 <!-- Modal -->
	<div class="modal fade" id="formListModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm no-margin" role="document">
	    <div class="modal-content">
	      <div class="modal-body">
	      </div>
	    </div>
	  </div>
	</div>
	</div>
</div>
<script src="${BASEPATH}/js/lib/bootstrap.min.js"></script>
<script>
	// seajs 的简单配置
	seajs.config({
	  base: "${BASEPATH}/js",
	  alias: {
	  	 "globalError": "src/base/globalErrorHandle.js",
	  	 "sideBar"    : "src/components/SideBar.js",
	  	 "breadcrumb" : "src/components/Breadcrumb.js",
	  	 "timer"      : "src/components/Timer.js",
	  	 "Dialog"     : "src/components/Dialog.js",
	  	 "common"     : "src/base/common.js",
	  	 "config"     : "src/base/config.js",
	  	 "bootstrapExtend" : "src/base/bootstrapExtend.js",
	  	 "validatorExtend" : "src/base/validatorExtend.js",
	  	 "dataType":  "src/base/dataType.js",
	  	 "header"     : "src/components/Header.js"
	  }
	})
	
	// 加载入口模块
	seajs.use("${BASEPATH}/js/main/forms/list");
</script>
<#include "../components/footer/footer.ftl"/>  