<#include "../components/header/header.ftl"/>  
<title>${TITLE}-${subTitle}</title>
<link href="${BASEPATH}/css/pages/prize.css" rel="stylesheet" type="text/css">
<#include "../components/sidebar/nav.ftl"/>  
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div class="clear direct">
	  <#include "../components/breadcrumb/breadcrumb.ftl"/>  
	  <#include "../components/timer/timer.ftl"/>  
	</div>
	<div class="panel panel-default prize">
	  <div class="panel-body">
		<table class="table">
	    	<thead>
                <tr>
                  <th>序号</th>
                  <th>奖品名称</th>
                  <th>创建者</th>
                  <th>创建时间</th>
                  <th>操作</th>
                </tr>
             </thead>
             <tbody>
             	<#if prizes?size <= 0 >
             		<tr>
             		    <td></td>
             		    <td></td>
	             		<td style="text-align: right;">
	             			暂时无数据
	             		</td>
	             		<td></td>
	             		<td></td>
             		</tr>
				<#else>
					<#list prizes as prize>
			            <tr>
							<td>${prize?index + 1}</td>
							<td>${prize.prizeName}</td>
							<td>${prize.createUser}</td>
							<td>${prize.createTime}</td>
							<td>
								<span class="iconfont icon-bianji" data-prizeid="${prize.prizeId}"></span>
								<span class="iconfont icon-delete" data-prizeid="${prize.prizeId}"></span>
							</td>
						</tr>
				 	</#list>
				</#if>
             </tbody>
	    </table>
	  </div>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="prizeListModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm no-margin" role="document">
	    <div class="modal-content">
	      <div class="modal-body">
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="editPrize" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog no-margin" role="document" style="width: 550px;">
	    <div class="modal-content">
		  <div class="modal-header">
		  	<h4 class="modal-title">编辑活动奖品</h4>
	      </div>
	      <div class="modal-body">
	      </div>
	    </div>
	  </div>
	</div>
</div>
<script src="${BASEPATH}/js/lib/bootstrap.min.js"></script>
<script src="${BASEPATH}/js/lib/art-template.min.js"></script>

<script id="prizeEdit" type="text/html">
	<form method="post" action="#nogo" id="prizeForm" class="prize">
	    <input type="hidden" name="prizeId" value={{prizeId}}>
	    <div class="row">
	    	<div class="col-md-3 col-lg-3 form-label">
			      <label>活动奖品名称:</label>
		  	</div>
		  	<div class="col-md-6  col-lg-6   comp-group">
			      <input type="text" name="prizeName" value={{prizeName}} class="form-control" aria-label="活动奖品名称不得重复" placeholder="活动奖品名称不得重复,长度范围[2, 10]">
		  	</div>
	    </div>
	    <div class="row">
	    	<div class="col-md-4  col-lg-4 col-md-offset-3 col-lg-offset-3">
		    	<button type="submit" id="submit" data-loading-text="提交中..." class="btn btn-primary" autocomplete="off">
				  提交
				</button>
			</div>
	    </div>
    </form>
</script>

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
	seajs.use("${BASEPATH}/js/main/prize/list");
</script>
<#include "../components/footer/footer.ftl"/>  