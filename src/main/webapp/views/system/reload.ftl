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
	    <div class="page-header">
		  <h3>${subTitle}<small>更新系统缓存和规则</small></h3>
		</div>
		<form method="post" action="#nogo" id="prizeForm">
			<div class="row">
		    	<div class="col-md-3 col-lg-3 form-label">
				      <label>是否更新活动缓存:</label>
			  	</div>
			  	<div class="col-md-5  col-lg-5 comp-group">
				    <div class="radio">
					  <label>
					    <input type="radio" value="1" name="isReloadActivity" >
					    	是
					  </label>
					</div>
					<div class="radio">
					  <label>
					    <input type="radio" value="0" name="isReloadActivity" checked>
					    否
					  </label>
					</div>
			  	</div>
		    </div>
		    <div class="row">
		    	<div class="col-md-3 col-lg-3 form-label">
				      <label>是否更新规则:</label>
			  	</div>
			  	<div class="col-md-5  col-lg-5 comp-group">
				    <div class="radio">
					  <label>
					    <input type="radio" value="1" name="isReloadRuleDrl" >
					    	是
					  </label>
					</div>
					<div class="radio">
					  <label>
					    <input type="radio" value="0" name="isReloadRuleDrl" checked>
					    否
					  </label>
					</div>
			  	</div>
		    </div>
		    <div class="row">
		    	<div class="col-md-4  col-lg-4 col-md-offset-3 col-lg-offset-3">
			    	<button type="submit" id="submit" data-loading-text="提交中..." class="btn btn-primary" autocomplete="off">
					 刷新
					</button>
				</div>
		    </div>
	    </form>
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
	  	 "header" : "src/components/Header.js"
	  }
	})
	
	// 加载入口模块
	seajs.use("${BASEPATH}/js/main/system/reload");
</script>
<#include "../components/footer/footer.ftl"/>  