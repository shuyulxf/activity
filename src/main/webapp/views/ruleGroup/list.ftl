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
		<table class="table">
	    	<thead>
                <tr>
                  <th>序号</th>
                  <th>规则组名称(中文)</th>
                  <th>规则组名称(英文)</th>
                  <th>规则组权重</th>
                  <th>是否为互斥</th>
                  <th>规则组执行阶段</th>
                  <th>创建者</th>
                  <th>创建时间</th>
                  <th>操作</th>
                </tr>
             </thead>
             <tbody>
             	<#if groups?size <= 0 >
             		<tr>
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
             		</tr>
				<#else>
					<#list groups as group>
			            <tr>
							<td>${group?index + 1}</td>
							<td>${group.ruleGroupNameCN}</td>
							<td>${group.ruleGroupNameEN!""}</td>
							<td>${group.ruleGroupWeight}</td>
							<td><#if group.isExclusive == 1>是<#else>否</#if></td>
							<td>
								<#if group.isCommonGroup == 0>
									活动用户
								<#elseif group.isCommonGroup == 1>
									筛选可参与活动
								<#else>
									活动执行
								</#if>
							</td>
							<td>${group.createUser}</td>
							<td>${group.createTime}</td>
							<td>
								<span class="iconfont icon-bianji" data-rulegroupid="${group.ruleGroupId}"></span>
								<span class="iconfont icon-delete" data-rulegroupid="${group.ruleGroupId}"></span>
							</td>
						</tr>
				 	</#list>
				</#if>
             </tbody>
	    </table>
	  </div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="ruleGroupListModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm no-margin" role="document">
	    <div class="modal-content">
	      <div class="modal-body">
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="editRuleGroup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog no-margin" role="document" style="width: 550px;">
	    <div class="modal-content">
		  <div class="modal-header">
		  	<h4 class="modal-title">编辑规则组</h4>
	      </div>
	      <div class="modal-body">
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
	seajs.use("${BASEPATH}/js/main/ruleGroup/list");
</script>
<#include "../components/footer/footer.ftl"/>  