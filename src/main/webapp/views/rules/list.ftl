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
		<table class="table">
	    	<thead>
                <tr>
                  <th>序号</th>
                  <th>规则名称(中文)</th>
                  <th>规则预定义函数</th>
                  <th>规则内容</th>
                  <th>规则权重</th>
                  <th>所属规则组</th>
                  <th>所属互斥规则</th>
                  <th>创建用户</th>
                  <th>创建时间</th>
                  <th>操作</th>
                </tr>
             </thead>
             <tbody>
             	<#if rules?? >
             	<#list rules as rule>
			            <tr>
							<td>${rule?index + 1}</td>
	             		    <td style="width: 150px;">${rule.getRuleNameCN()}</td>
	             		    <td  style="max-width: 200px; max-height: 40px; word-wrap: break-word;"><#if rule.functions??><pre class="rule-detail">${rule.functions?replace("#and#", "&&")?replace("#add#", "+")}</pre></#if></td>
	             		    <td  style="max-width: 200px; word-wrap: break-word;"><pre class="rule-detail">${rule.getRule()}</pre></td>
		             		<td>${rule.ruleWeight}</td>
		             		<td style="width: 120px;" >${rule.ruleGroupNameCN!""}</td>
		             		<td style="width: 120px;">${rule.exRuleGroupNameCN!""}</td>
		             		<td>${rule.getCreateUser()}</td>
		             		<td>${rule.getCreateTime()}</td>
		             		<td>
		             			<span class="iconfont icon-bianji" data-ruleid="${rule.ruleId}"></span>
								<span class="iconfont icon-delete" data-iscommongroup="${rule.isCommonGroup!""}" data-ruleid="${rule.ruleId}"></span>
							</td>
						</tr>
				 	</#list>
				<#else>
					<tr>
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
	<div class="modal fade" id="ruleListModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm no-margin" role="document">
	    <div class="modal-content">
	      <div class="modal-body">
	      </div>
	    </div>
	  </div>
	</div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="ruleDetailModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog no-margin" role="document">
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
	seajs.use("${BASEPATH}/js/main/rules/list");
</script>
<#include "../components/footer/footer.ftl"/>  