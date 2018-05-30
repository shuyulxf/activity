<#include "../components/header/header.ftl"/>  
<title>${TITLE}-${subTitle}</title>
<link href="${BASEPATH}/css/pages/activityList.css" rel="stylesheet" type="text/css">
<#include "../components/sidebar/nav.ftl"/>  
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div class="clear direct">
	  <#include "../components/breadcrumb/breadcrumb.ftl"/>  
	  <#include "../components/timer/timer.ftl"/>  
	</div>
	<div class="panel panel-default list">
	  <div class="panel-body">

		<#include "../components/search/search.ftl"/>  
		<table class="table">
	    	<thead>
                <tr>
                  <th>序号</th>
                  <th>活动名称</th>
                  <th>活动类型</th>
                  <th>上线时间</th>
                  <th>下线时间</th>
                  <th>活动地区</th>
                  <th>活动应用渠道</th>
                  <#if type=="willonline">
                  <th>奖品总量</th>
                  <#else>
                  <th>参与人次</th>
                  <th>获奖人次</th>
                  </#if>
                  <#if type=="online">
                  <th>奖品余量</th>
                  </#if>
                  <#if type=="offline">
                  <th>奖品发放率</th>
                  </#if>
                  <th>活动创建时间</th>
                  
                  <th>操作</th>
                 
                  
                </tr>
             </thead>
             <tbody>
				<#if lists??>
	             	<#list lists as list>
	             	  <tr>
	             		<td>${list?index + 1}</td>
	             		<td>${list.activityName!""}</td>
	             		<td>${list.activityType!""}</td>
	             		<td>${list.activityStartTime!""}</td>
	             		<td>${list.activityEndTime!""}</td>
	             		<td>${list.activityProvince!""}</td>
	             		<td>${list.activityApplyCode!""}</td>
	             		<#assign activityId=list.activityId!"">
	             		<#assign activityName=list.activityName!"">
	             		 <#if list.activityAwardSetting??>
	                     	<#assign awardSetting=list.activityAwardSetting?eval>
	                     	<#assign awardList=awardSetting.activityAwardList!"">
	                     <#else>
	                     	<#assign awardList="">
	                     	<#assign awardSetting={}>
	                     </#if>
	             		<#if type=="willonline">
	             			<td style="max-width: 120px;">
	             			<#if list.activityAwardSetting??>
	             				<#assign aas=list.activityAwardSetting?eval>
	             				<#assign aadt = aas.activityAwardDeliveryType!"">
	             				<#if aadt?? >
	             					<#if aadt?contains("直接发放奖品")>
	             						<#assign aal=aas.activityAwardList>
	             						<#if aal??>
	             							<#assign aalt=aal?eval>
	             							
	             							<#list aalt as l>
	             								${l.typeName}:${l.num}<br>
	             							<#else>
	             								未设置奖品
	             							</#list>
	             					
	             						<#else>
	             							未设置奖品
	             						</#if>
	                     			<#elseif aadt?contains("第三方链接抽奖")>
	                     				${aadt} ${aas.urlForThirdDeliverAward!""}
	                     			<#else>
	                     				${aadt}
	                     			</#if>
	                     		<#else>
	                     			奖品设置类型异常
	                     		</#if>	
	                     	<#else>
	                     		奖品数据设置异常，请检查
	                     	</#if>
	                     	</td>
	                    <#else>
	                     <td data-name="${activityName}" class="people-join-num"></td>
	                     <td data-name="${activityName}" data-awardType="${awardSetting.activityAwardDeliveryType!""}" class="people-award-num"></td>
	                    </#if>
	                    <#if type=="online">
		                  <td data-name="${activityId}" class="award-show" data-awardType="${awardSetting.activityAwardDeliveryType!""}" data-type="award-left-num" data-awardList=${awardList!""}></td>
		                </#if>
		                <#if type=="offline">  
		                  <td data-name="${activityId}" class="award-show" data-awardType="${awardSetting.activityAwardDeliveryType!""}" data-type="award-send-rate" data-awardList=${awardList!""}></td>
		                </#if>
		                <td>${list.createTime!""}</td>
		                <#if type !="offline">
			                <td>
				                <span class="iconfont icon-bianji" data-activityid="${activityId}"></span>
			                	<#if type=="willonline">
									<span class="iconfont icon-delete" data-activityid="${activityId}"></span>
								</#if>
			                </td>
			            </#if>
		              </tr>
	             	<#else>
	             		<tr>
	             	    	<td></td>
	             	    	<td></td>
	             	    	<td></td>
	             	    	<td></td>
	             	    	<td>暂无数据</td>
	             	    	<td></td>
	             	    	<td></td>
	             	    	<td></td>
	             	    	<td></td>
	             	    </tr>
	             	</#list>
	            <#else>
	            	
	            </#if>
             </tbody>
	    </table>
	    <p id="pagination" class="pagi"></p>
	  </div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="activityListModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm no-margin" role="document">
	    <div class="modal-content">
	      <div class="modal-body">
	      </div>
	    </div>
	  </div>
	</div>
</div>
<script src="${BASEPATH}/js/lib/bootstrap.min.js"></script>
<script src="${BASEPATH}/js/plugin/jquery.bootpag.min.js"></script>
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
	  	 "Search"   : "src/components/Search.js",
	  	 "common"     : "src/base/common.js",
	  	 "config"     : "src/base/config.js",
	  	 "bootstrapExtend" : "src/base/bootstrapExtend.js",
	  	 "validatorExtend" : "src/base/validatorExtend.js",
	  	 "dataType":  "src/base/dataType.js",
	  	  "header"     : "src/components/Header.js"
	  }
	});
	
	var total = ${total},
	    page  = ${page};
	
	// 加载入口模块
	seajs.use("${BASEPATH}/js/main/activity/list");
</script>
<#include "../components/footer/footer.ftl"/>  