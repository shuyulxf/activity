<#include "../components/header/header.ftl"/>  
<title>${TITLE}-${subTitle}</title>
<link href="${BASEPATH}/css/pages/ruleGroup.css" rel="stylesheet" type="text/css">
<#include "../components/sidebar/nav.ftl"/>  
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div class="clear direct">
	  <#include "../components/breadcrumb/breadcrumb.ftl"/>  
	  <#include "../components/timer/timer.ftl"/>  
	</div>
	<div class="panel panel-default rule-group">
	  <div class="panel-body">
	    <div class="page-header">
		  <h3>${subTitle}<small>为活动平台添加规则组</small></h3>
		</div>
		<#if ruleGroupIdError == "yes">
			<div class="col-md-12  col-lg-12 ta-c" >
				${errorMsg}
			</div>
		<#else>
		
		<form method="post" action="#nogo" id="ruleGroupForm">
			<input type="hidden" value="<#if rg??>${rg.ruleGroupId!""}</#if>" name="ruleGroupId"/>
		    <div class="row">
		    	<div class="col-md-3 col-lg-3 form-label">
				      <label>活动规则组名称(中文):</label>
			  	</div>
			  	<div class="col-md-5  col-lg-5 comp-group">
				      <input type="text" value="<#if rg??> ${rg.ruleGroupNameCN!""}</#if>" name="ruleGroupNameCN" class="form-control" aria-label="活动规则组名称不得重复" placeholder="活动规则组名称不得重复,长度范围[6, 30]">
			  	</div>
		    </div>
		    <div class="row">
		    	<div class="col-md-3 col-lg-3 form-label">
				      <label>活动规则组名称(英文):</label>
			  	</div>
			  	<div class="col-md-5  col-lg-5 comp-group">
				      <input type="text" value="<#if rg??> ${rg.ruleGroupNameEN!""}</#if>" name="ruleGroupNameEN" class="form-control" aria-label="活动规则组英文名称不得重复" placeholder="活动规则组英文名称不得重复,长度范围[6, 50]">
			  	</div>
		    </div>
		    <div class="row">
		    	<div class="col-md-3 col-lg-3 form-label">
				      <label>是否为互斥规则组:</label>
			  	</div>
			  	<div class="col-md-5  col-lg-5 comp-group">
				    <div class="radio">
					  <label>
					    <input type="radio" value="1" name="isExclusive" <#if rg??&&rg.isExclusive??&& rg.isExclusive==1>checked</#if>>
					    	是
					  </label>
					</div>
					<div class="radio">
					  <label>
					    <input type="radio" value="0" name="isExclusive" <#if rg??&&rg.isExclusive??><#if rg.isExclusive==0>checked</#if><#else>checked</#if>>
					    否
					  </label>
					</div>
			  	</div>
		    </div>
		    <div class="row">
		    	<div class="col-md-3 col-lg-3 form-label">
				      <label>规则组执行阶段:</label>
			  	</div>
			  	<div class="col-md-5  col-lg-5 comp-group">
				    <div class="radio">
					  <label>
					    <input type="radio" value="0" name="isCommonGroup" <#if rg??&&rg.isCommonGroup??&& rg.isCommonGroup==0>checked</#if> />
					             活动参与者条件验证
					  </label>
					</div>
					<div class="radio">
					  <label>
					    <input type="radio" value="1" name="isCommonGroup" <#if rg??&&rg.isCommonGroup?? && rg.isCommonGroup==1>checked</#if> />
					    筛选可参与活动
					  </label>
					</div>
					<div class="radio">
					  <label>
					    <input type="radio" value="2" name="isCommonGroup" <#if rg??&&rg.isCommonGroup??><#if rg.isCommonGroup==2>checked</#if><#else>checked</#if> />
					    活动执行过程
					  </label>
					</div>
			  	</div>
			</div>
			
			<div class="row weight <#if rg??&&rg.isExclusive??&& rg.isExclusive==1>hide</#if>" >
		    	<div class="col-md-3 col-lg-3 form-label">
				   <label>活动规则组权重:</label>
			  	</div>
			  	<div class="col-md-4  col-lg-4 comp-group">
				<#if rg??>
					<#assign numAttr={"name":"ruleGroupWeight","number": rg.ruleGroupWeight!""}>
				<#else>
					<#assign numAttr={"name":"ruleGroupWeight","number": "0"}>
				</#if>
				<#include "/widget/number.ftl">
				</div>
		    </div>
		    <div class="row">
		    	<div class="col-md-4  col-lg-4 col-md-offset-3 col-lg-offset-3">
		    		<button type="submit" id="<#if subTitle?index_of("创建") != -1>submit<#else>update</#if>" data-loading-text="提交中..." class="btn btn-primary" autocomplete="off">
						  提交
					</button>
				</div>
		    </div>
	    </form>
	    </#if>
	  </div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="createRltInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm no-margin" role="document">
	    <div class="modal-content">
	      <div class="modal-body">
				ddd
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
	seajs.use("${BASEPATH}/js/main/ruleGroup/edit");
</script>
<#include "../components/footer/footer.ftl"/>  