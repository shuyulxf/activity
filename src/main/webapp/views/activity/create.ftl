<#include "../components/header/header.ftl"/>  
<title>${TITLE}</title>
<link href="${BASEPATH}/css/pages/activity.css" rel="stylesheet" type="text/css">
<#include "../components/sidebar/nav.ftl"/>  
<div class="col-md-10 col-md-offset-2 main">
	<div class="clear direct">
	   <#include "../components/breadcrumb/breadcrumb.ftl"/>  
	   <#include "../components/timer/timer.ftl"/>  
	</div>
	<div class="panel panel-default activity">
	  <div class="panel-body">
	    <div class="page-header">
		  <h3>${subTitle}<small>为活动平台${subTitle}活动</small></h3>
		</div>
		<input name="activityId" type="hidden" value="<#if activity??>${activity.activityId}</#if>" />
		<form method="post" action="#nogo" id="commonForm">
			<div class="row step-header al-c">第一步  <span>活动通用属性配置</span></div>
		    <div class="row  form-item">
		    	<div class="col-md-3 col-lg-3 form-label">
				      <label>活动名称:</label>
			  	</div>
			  	<div class="col-md-5  col-lg-5 comp-group">
				      <input type="text" name="activityName" value="<#if activity??>${activity.activityName}</#if>" class="form-control" aria-label="活动名称不得重复" placeholder="活动名称不得重复,长度范围[2, 30]" required rangelength="2,30">
			  	</div>
		    </div>
		   
		    <#if activity?? &&activity.activityReplySetting??> 
		     	<#assign replyValues = activity.activityReplySetting?eval />
		    </#if>
		    <#if activity?? &&activity.activityCommonSetting??> 
		     	<#assign attrValues = activity.activityCommonSetting?eval />
		    </#if>
		    <#if acsFas??>
		        <#list acsFas as acsFa>
		        	<#if acsFa.formAttrNameLabel?index_of("敏感词") == -1>
		        		<#assign item=acsFa>
		        		<#assign template>/actFormCompForRule/${acsFa.formAttrNameFillType?trim}.ftl</#assign>
				        <#include template>
					</#if>
		        </#list>
		    </#if>
		    <div class="row">
		    
		    	<div class="col-md-4  col-lg-4 col-md-offset-3 col-lg-offset-3">
			    	<button type="submit" data-loading-text="提交中..." class="btn btn-primary next" autocomplete="off">
					  下一步
					</button>
				</div>
		    </div>
	    </form>
	    <form method="post" action="#nogo" id="generalForm" class="hide">
			<div class="row step-header al-c">第二步  <span>活动参与条件基本属性配置</span></div>
			<#if activity??&&activity.activityGeneralSetting??> 
		     	<#assign attrValues = activity.activityGeneralSetting?eval />
		    </#if>
		    <#if agsFas??>
		        <#list agsFas as agsFa>
		        	<#if agsFa.formAttrNameLabel?index_of("敏感词") == -1>
		        		<#assign item=agsFa>
		        		<#assign template>/actFormCompForRule/${item.formAttrNameFillType?trim}.ftl</#assign>
				        <#include template>
					</#if>
		        </#list>
		    </#if>
		    <div class="row">
		    	<div class="col-md-2  col-lg-2 col-md-offset-3 col-lg-offset-3">
			    	<button type="submit" data-loading-text="提交中..." class="btn btn-primary prev" autocomplete="off">
					  上一步
					</button>
				</div>
		    	<div class="col-md-2 col-lg-2">
			    	<button type="submit" data-loading-text="提交中..." class="btn btn-primary next" autocomplete="off">
					  下一步
					</button>
				</div>
		    </div>
	    </form>
	    <form method="post" action="#nogo" id="awardForm" class="hide">
			<div class="row step-header al-c">第三步  <span>活动获奖条件属性配置</span></div>
		    <#if activity??&&activity.activityAwardSetting??> 
		     	<#assign attrValues = activity.activityAwardSetting?eval />
		    </#if>
		    <#if aasFas??>
		        <#list aasFas as aasFa>
	        		<#assign item=aasFa>
	        		<#assign template>/actFormCompForRule/${item.formAttrNameFillType?trim}.ftl</#assign>
			        <#include template>
		        </#list>
		    </#if>
		    <div class="row">
		    	<div class="col-md-2  col-lg-2 col-md-offset-3 col-lg-offset-3">
			    	<button type="submit" data-loading-text="提交中..." class="btn btn-primary prev" autocomplete="off">
					  上一步
					</button>
				</div>
		    	<div class="col-md-2 col-lg-2">
			    	<button type="submit" data-type="<#if subTitle?contains("创建")>create</#if>" class="btn btn-primary next" autocomplete="off">
					  提交
					</button>
				</div>
		    </div>
	    </form>
	  </div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="activitySuccessInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm no-margin" role="document">
	    <div class="modal-content">
	      <div class="modal-body">
	      </div>
	    </div>
	  </div>
	</div>
</div>
<script src="${BASEPATH}/js/lib/bootstrap.min.js"></script>
<script src="${BASEPATH}/js/plugin/datetimepicker/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${BASEPATH}/js/plugin/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script src="${BASEPATH}/js/plugin/multiselect.js"></script>
<script>
    
    var formValidatorRules = [
    	 {
		   rules: {
		   	   activityId: {
		   	   		number: true
		   	   },
			   activityName: {
				   minlength: 2,
				   maxlength: 30
			   },
		   },
		   messages: {
			   activityName: {
				   required: "活动名称不能为空",
				   rangelength: "活动名称的最小长度不得少于2个字不得多于30个字"
			   },
			   activityType: {
			   	   required: "不能为空"
			   }
		   }
	    },
	    {
		   rules: {
			   activityName: {
				   required: true,
				   minlength: 2,
				   maxlength: 30
			   }
		   },
		   messages: {
			   activityName: {
				   required: "活动名称不能为空",
				   minlength: "活动名称的最小长度不得少于2个字",
				   maxlength: "活动名称的最小长度不得多于30个字"
			   } 
		   }
	    },
	    {
	    
	    },
	    {
	    
	    }
    ];
	// seajs 的简单配置
	seajs.config({
	  base: "${BASEPATH}/js",
	  alias: {
	  	 "globalError": "src/base/globalErrorHandle.js",
	  	 "sideBar"    : "src/components/SideBar.js",
	  	 "breadcrumb" : "src/components/Breadcrumb.js",
	  	 "timer"      : "src/components/Timer.js",
	  	 "Dialog"     : "src/components/Dialog.js",
	  	 "header"     : "src/components/Header.js",
	  	 "common"     : "src/base/common.js",
	  	 "config"     : "src/base/config.js",
	  	 "bootstrapExtend" : "src/base/bootstrapExtend.js",
	  	 "validatorExtend" : "src/base/validatorExtend.js",
	  	 "dataType" : "src/base/dataType.js",
	  }
	})
	
	// 加载入口模块
	seajs.use("${BASEPATH}/js/main/activity/main");
</script>
<#include "../components/footer/footer.ftl"/>  