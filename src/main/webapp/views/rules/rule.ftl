<#include "../components/header/header.ftl"/>  
<title>${TITLE}</title>
<link href="${BASEPATH}/css/pages/rule.css" rel="stylesheet" type="text/css">
<#include "../components/sidebar/nav.ftl"/>  
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div class="clear direct">
	  <#include "../components/breadcrumb/breadcrumb.ftl"/>  
	  <#include "../components/timer/timer.ftl"/>  
	</div>
	<div class="panel panel-default rule">
	  <div class="panel-body">
	    <div class="page-header">
		  <h3>规则${subTitle}<small>活动平台规则</small></h3>
		</div>
		<#if ruleIdError == "yes">
			<div class="col-md-12  col-lg-12 ta-c" >
				${errorMsg}
			</div>
		<#else>
			<form method="post" action="#nogo" id="ruleForm">
			    <input name="ruleId" type="hidden" value="<#if rule??>${rule.getRuleId()}</#if>" />
			    <div class="row ruleNameCNIpt">
			    	<div class="col-md-3 col-lg-3 form-label">
				      <label>规则名称(中文):</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
				  	    <input value="<#if rule??> ${rule.getRuleNameCN()} </#if>" name="ruleNameCN" class="form-control"/>
				  	</div>
				  	<div  class="col-md-1  col-lg-1 help">
				  		<span tabindex="0" class="iconfont icon-help" role="button" data-toggle="popover" data-trigger="focus" title="规则编辑Help" data-content="规则格式见设计文档"></span>
				  	</div>
			    </div>
			    <div class="row ruleNameENIpt">
			    	<div class="col-md-3 col-lg-3 form-label">
				      <label>规则名称(英文):</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
				  	    <input value="<#if rule??> ${rule.getRuleNameEN()} </#if>" name="ruleNameEN" class="form-control"/>
				  	</div>
				  	<div  class="col-md-1  col-lg-1 help">
				  		<span tabindex="0" class="iconfont icon-help" role="button" data-toggle="popover" data-trigger="focus" title="规则编辑Help" data-content="规则格式见设计文档"></span>
				  	</div>
			    </div>
			    <div class="row groupInput">
			    	<div class="col-md-3 col-lg-3 form-label">
					      <label>规则组名称:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
					    <div class="btn-group dropdown">
						  <button type="button" class="btn btn-default dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						   <span class="btn-value"><#if defaultGroup??>${defaultGroup.ruleGroupNameCN}<#else>无规则组数据</#if></span>
						   <input data-isCommonGroup="<#if defaultGroup??>${defaultGroup.isCommonGroup}</#if>" value=<#if defaultGroup??>${defaultGroup.ruleGroupId}<#else>无规则组数据</#if> name="ruleGroupId" class="btn-value-input" type="hidden"/>
						   <span class="caret"></span>
						  </button>
						  <ul class="dropdown-menu">
						    <#if ruleGroups??>
						    	<#list ruleGroups as group>
						    	   <li data-icg="<#if group??>${group.isCommonGroup}</#if>" data-id="${group.ruleGroupId}">${group.ruleGroupNameCN}</li>
							    </#list>
						    <#else>
							            暂无规则组，请先添加规则组
							</#if>
						  </ul>
					    </div>
				  	</div>
			    </div>
			    <div class="row groupInput">
			    	<div class="col-md-3 col-lg-3 form-label">
					      <label>互斥的规则子组名称:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
					    <div class="btn-group dropdown">
						  <button type="button" class="btn btn-default dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						   <span class="btn-value"><#if exDefaultGroup??>${exDefaultGroup.ruleGroupNameCN}<#else>请选择互斥规则组</#if></span><input value="<#if exDefaultGroup??>${exDefaultGroup.ruleGroupId}<#else></#if>" name="exRuleGroupId" class="btn-value-input" type="hidden"/><span class="caret"></span>
						  </button>
						  <ul class="dropdown-menu">
						    <#if exRuleGroups??>
						    	<#list exRuleGroups as group>
							    	<li data-id="${group.ruleGroupId}">${group.ruleGroupNameCN}</li>
							    </#list>
						    <#else>
							            暂无互斥规则组，请先添加互斥规则组
							</#if>
						  </ul>
					    </div>
				  	</div>
			    </div>
			    <div class="row">
			    	<div class="col-md-3 col-lg-3 form-label">
				      <label>规则预定义函数:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
				  		<textarea name="functions" class="flexTextArea" placeholder="规则预定义函数, 属于同一互斥规则组的规则公用的函数，在其中一条规则中编辑即可"><#if rule??> ${rule.functions!""} </#if></textarea>
				  		
				  	</div>
				  	<div  class="col-md-1  col-lg-1 help">
				  		<span tabindex="0" class="iconfont icon-help" role="button" data-toggle="popover" data-trigger="focus" title="规则预定义函数编辑Help" data-content="规则预定义函数格式见设计文档"></span>
				  	</div>
			    </div>
			    <div class="row ruleInput">
			    	<div class="col-md-3 col-lg-3 form-label">
				      <label>规则:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
				  		<textarea name="rule" class="flexTextArea" placeholder="规则定义"><#if rule??> ${rule.rule!""} </#if></textarea>
				  	</div>
				  	<div  class="col-md-1  col-lg-1 help">
				  		<span tabindex="0" class="iconfont icon-help" role="button" data-toggle="popover" data-trigger="focus" title="规则编辑Help" data-content="规则格式见设计文档"></span>
				  	</div>
			    </div>
			    <div class="row ruleWeightSel">
			    	<div class="col-md-3 col-lg-3 form-label">
					   <label>规则权重:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
					  	<#if rule??>
							<#assign numAttr={"name":"ruleWeight","number": rule.ruleWeight!""}>
						<#else>
							<#assign numAttr={"name":"ruleWeight","number": "0"}>
						</#if>
						<#include "/widget/number.ftl">
					</div>
			    </div>
			    <div class="row">
			    	<div class="col-md-2  col-lg-2 col-md-offset-3 col-lg-offset-3">
				    	<button type="submit" id="<#if subTitle?contains("创建")>submit<#else>update</#if>" data-loading-text="提交中..." class="btn btn-primary" autocomplete="off">
						  提交
						</button>
					</div>
			    </div>
		    </form>
	    </#if>
	  </div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="ruleSuccessInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm no-margin" role="document">
	    <div class="modal-content">
	      <div class="modal-body">
	      </div>
	    </div>
	  </div>
	</div>
</div>
<script src="${BASEPATH}/js/lib/bootstrap.min.js"></script>
<script src="${BASEPATH}/js/plugin/multiselect.js"></script>
<script src="${BASEPATH}/js/plugin/jquery.flexText.min.js"></script>
<script>
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
	});
	
	// 加载入口模块
	seajs.use("${BASEPATH}/js/main/rules/edit");
</script>
<#include "../components/footer/footer.ftl"/> 