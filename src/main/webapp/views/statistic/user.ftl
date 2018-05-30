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
		<#assign hasPhoneSearch="true">
		<#assign isUsePrizeSel="true">
		<#assign hasExportBtn="true">
		<#assign exportParams={"btnIds":"statisticForUser","type":4}>
		<#include "../components/search/search.ftl"/>  
		<table class="table">
	    	<thead>
                <tr>
                  <th>序号</th>
                  <th>用户</th>
                  <th>获奖活动</th>
                  <th>开始时间</th>
                  <th>结束时间</th>
                  <th>地区</th>
                  <th>应用渠道</th>
                  <th>奖品名称</th>
                  <th>获奖总量</th>
                </tr>
             </thead>
             <tbody>
				<#if lists??>
	             	<#list lists as list>
	             	  <tr>
	             		<td>${list?index + 1}</td>
	             		<td>${list.phoneNumber!""}</td>
	             		<td>${list.activityName!""}</td>
	             		<td>${params.startTime!""}</td>
	             		<td>${params.endTime!""}</td>
	             		<td>${list.province!""}</td>
	             		<td>${list.applyCode!""}</td>
	                    <td>${list.awardType!""}</td>
	                    <td>${list.numberForAward!""}</td>
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
	            </#if>
             </tbody>
	    </table>
	    <p id="pagination" class="pagi"></p>
	  </div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="activityStatisticModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
	seajs.use("${BASEPATH}/js/main/statistic/list");
</script>
<#include "../components/footer/footer.ftl"/>  