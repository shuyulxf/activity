<#include "../components/header/header.ftl"/>  
<title>${TITLE}</title>
<link href="${BASEPATH}/css/pages/form.css" rel="stylesheet" type="text/css">
<#include "../components/sidebar/nav.ftl"/>  
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<div class="clear direct">
	  <#include "../components/breadcrumb/breadcrumb.ftl"/>  
	  <#include "../components/timer/timer.ftl"/>  
	</div>
	<div class="panel panel-default form">
	  <div class="panel-body">
	    <div class="page-header">
		  <h3>表单组件${subTitle}<small>活动平台规则</small></h3>
		</div>
		<#if formAttrInfoIdError == "yes">
			<div class="col-md-12  col-lg-12 ta-c" >
				${errorMsg}
			</div>
		<#else>
		  	<form method="post" action="#nogo" id="formAttrForm">
		  		<input name="formAttrInfoId" type="hidden" value="<#if form??> ${form.getFormAttrInfoId()}</#if>" />
			  	<div class="row formAttrNameIpt">
			    	<div class="col-md-3 col-lg-3 form-label">
					   <label>表单组件name属性:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
				  	    <input value="<#if form??>${form.getFormAttrName()}</#if>" name="formAttrName" class="form-control" placeholder="字段参见文档中活动属性表中属性名字"/>
					</div>
					<div class="col-md-1  col-lg-1 help">
					    <span tabindex="0" class="iconfont icon-help" role="button" data-toggle="popover" data-trigger="focus" title="该规则相关表单字段Help" data-content="每条规则都对应一个表单，如输入框、下拉菜单等，此处为该字段内容存储到数据库中时的字段名称，和数据库中活动的属性想对应"></span>
					</div>
			    </div>
			    <div class="row groupInput">
			    	<div class="col-md-3 col-lg-3 form-label">
					      <label>所属互斥规则组:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
					    <div class="btn-group dropdown">
						  <button type="button" class="btn btn-default dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						   <span class="btn-value"><#if exDefaultGroup??>${exDefaultGroup.ruleGroupNameCN}<#else>请选择互斥规则组</#if></span><input value="<#if exDefaultGroup??>${exDefaultGroup.ruleGroupId}<#else></#if>" name="exRuleGroupId" class="btn-value-input" type="hidden"/><span class="caret"></span>
						  </button>
						  <ul class="dropdown-menu">
						  	<li data-id="null">请选择</li>
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
			    <div class="row formAttrNameTypeIpt">
			    	<div class="col-md-3 col-lg-3 form-label">
					   <label>name属性所属活动属性类别:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group formAttrNameType">
					   <div class="btn-group dropdown">
						  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						   <span class="btn-value"><#if form??>${form.getFormAttrNameType()}<#else>activityGeneralSetting</#if></span><input value="<#if form??>${form.getFormAttrNameType()}<#else>activityGeneralSetting</#if>" name="formAttrNameType" class="btn-value-input" type="hidden"/><span class="caret"></span>
						  </button>
						  <#assign types = ["activityCommonSetting","activityGeneralSetting", "activityAwardSetting"]>
						  <ul class="dropdown-menu">
						    <#list types as type>
						    	<li>${type}</li>
						    </#list>
						  </ul>
						</div>
						<span tabindex="0" class="iconfont icon-help" role="button" data-toggle="popover" data-trigger="focus" title="该字段所属的活动属性类别Help" data-content="规则所属的活动属性类别，总共有四种类别，参照文档中"></span>
				   </div>
			    </div>
			    <div class="row formAttrNameFillTypeIpt ">
			    	<div class="col-md-3 col-lg-3 form-label">
					   <label>表单组件类型:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group formAttrNameFillType">
				  	   <#assign formCallback>
				  	   
				  	   		var $itemSel = $('.form-attr-for-selects'),
				  	   		    $itemNum = $('.form-attr-for-num');
				  	   		var type = $("[name=formAttrNameFillType]").val();
				  	   		
				  	   		if(type=='select' || type=='multiselect') {
				  	   			$itemSel.removeClass('hide');
				  	   			$itemNum.addClass("hide");
				  	   		} else if (type == 'number') {
				  	   			$itemNum.removeClass('hide');
				  	   			$itemSel.addClass("hide");
				  	   		} else {
				  	   			$itemSel.addClass('hide');
				  	   			$itemNum.addClass("hide");
				  	   		}
				  	   </#assign>
	
					   <div class="btn-group dropdown" data-callback="${formCallback?replace("\n","")?replace("\"","'")}">
						  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						   <span class="btn-value"><#if form??>${form.getFormAttrNameFillType()}<#else>input</#if></span><input value="<#if form??>${form.getFormAttrNameFillType()}<#else>input</#if>" name="formAttrNameFillType" class="btn-value-input" type="hidden"/><span class="caret"></span>
						  </button>
						  <#assign types = ["award", "hidden", "input", "select", "multiselect", "radio", "number", "timePicker","keyword", "question","activityList"]>
						  <ul class="dropdown-menu">
						    <#list types as type>
						    	<li>${type}</li>
						    </#list>
						  </ul>
						</div>
						<span tabindex="0" class="iconfont icon-help" role="button" data-toggle="popover" data-trigger="focus" title="该字段填写类别Help" data-content="规则字段填写的方式，如直接填写或者下拉菜单，参照文档中"></span>
				  		<#if form?? && form.formNameTypeAttrs??>
				  			<#assign formTypeAttr = form.formNameTypeAttrs?eval>
				  		<#else>
				  			<#assign formTypeAttr = {}>
				  		</#if>
				  		<div class="form-attr-for-selects <#if form ?? && (form.getFormAttrNameFillType()?contains("select") || form.getFormAttrNameFillType()?contains("multiselect"))><#else>hide</#if>" >
					  		<div class="row formAttrDataForFormTypeIpt">
						    	<div class="col-md-4 col-lg-4 form-label">
								   <label>下拉菜单选项数据来源RUL:</label>
							  	</div>
							  	<div class="col-md-8  col-lg-8 comp-group formAttrDataForFormType">
								   <input id="formAttrDataUrl"  value="${formTypeAttr.formAttrDataUrl!""}" class="attr form-control" placeholder="下拉菜单选项数据来源RUL" />
							   </div>
						    </div>
						    <div class="row formAttrDataForFormTypeIpt">
						    	<div class="col-md-4 col-lg-4 form-label">
								   <label>下拉菜单选项数据来源RUL参数列表:</label>
							  	</div>
							  	<div class="col-md-8  col-lg-8 comp-group formAttrDataForFormType">
								   <input id="formAttrDataUrlParams" value="${formTypeAttr.formAttrDataUrlParams!""}" class="attr form-control" placeholder="下拉菜单选项数据来源RUL参数列表，为对象类型，{'key':'value'}" />
							   </div>
						    </div>
						    <div class="row formAttrDataForFormTypeIpt">
						    	<div class="col-md-4 col-lg-4 form-label">
								   <label>下拉菜单选项数据:</label>
							  	</div>
							  	<div class="col-md-8  col-lg-8 comp-group formAttrDataForFormType">
								   <input id="formAttrData" value="<#if formTypeAttr.formAttrData??>${formTypeAttr.formAttrData?replace("\"","'")}</#if>" class="attr form-control" placeholder="下拉菜单选项数据，为Array数据格式" />
							   </div>
						    </div>
						    <div class="row formAttrDataForFormTypeIpt">
						    	<div class="col-md-4 col-lg-4 form-label">
								   <label>下拉菜单值修改时回调函数:</label>
							  	</div>
							  	<div class="col-md-8  col-lg-8 comp-group formAttrDataForFormType">
							  		<textarea id="formAttrChangeCallback" class="attr" value="${formTypeAttr.formAttrChangeCallback!""}" class="attr flexTextArea" placeholder="下拉菜单数据修改时，要执行的回调函数,为立即执行函数，如： (function(){  /** your code **/})()" >${formTypeAttr.formAttrChangeCallback!""}</textarea>
							   </div>
						    </div>
				  		</div>
				  		<div style="margin-top: 10px;" class="form-attr-for-num <#if form ?? && form.getFormAttrNameFillType()?contains("number")><#else>hide</#if>">
				  		
				  		 	<div class="row formAttrDataForFormTypeIpt">
						    	<div class="col-md-2 col-lg-2 form-label">
								   <label>最小值:</label>
							  	</div>
							  	<div class="col-md-3 col-lg-3  formAttrDataForFormType">
							  		<input id="formNumMin" number="number" value="${formTypeAttr.formNumMin!""}" class="attr form-control" placeholder="数值表单的最小值" />
							   </div>
						    </div>
				  			<div class="row formAttrDataForFormTypeIpt">
						    	<div class="col-md-2 col-lg-2 form-label">
								   <label>最大值:</label>
							  	</div>
							  	<div class="col-md-3  col-lg-3  formAttrDataForFormType">
							  		<input id="formNumMax" number="number" value="${formTypeAttr.formNumMax!""}" class="attr form-control" placeholder="数值表单的最大值" />
							   </div>
						    </div>
						    <div class="row formAttrDataForFormTypeIpt">
						    	<div class="col-md-2 col-lg-2 form-label">
								   <label>增长间隔:</label>
							  	</div>
							  	<div class="col-md-3  col-lg-3  formAttrDataForFormType">
							  		<input id="formNumDistance" number="number" value="${formTypeAttr.formNumDistance!""}" class="attr form-control" placeholder="数值表单的增长间隔" />
							   </div>
						    </div>
				  		</div>
				    </div>
			    </div>
			    <div class="row formAttrLabelIpt">
			    	<div class="col-md-3 col-lg-3 form-label">
					   <label>Label标签值:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
					    <input value="<#if form??>${form.getFormAttrNameLabel()}</#if>" name="formAttrNameLabel" class="form-control" placeholder="该表单字段的Label"/>
					</div>
					<div class="col-md-1  col-lg-1 help">
					    <span tabindex="0" class="iconfont icon-help" role="button" data-toggle="popover" data-trigger="focus" title="该规则相关表单字段Label" data-content="表单字段的Label，即用户编辑活动规则时，表单左侧的提示语"></span>
					</div>
			    </div>
			    <div class="row formAttrNameDefaultValueIpt">
			    	<div class="col-md-3 col-lg-3 form-label">
					   <label>表单组件value属性默认值:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
					    <input value="<#if form?? && form.formAttrNameDefaultValue?? >${form.formAttrNameDefaultValue}</#if>" name="formAttrNameDefaultValue" class="form-control" placeholder="该表单字段的默认值"/>
					</div>
					<div class="col-md-1  col-lg-1 help">
					    <span tabindex="0" class="iconfont icon-help" role="button" data-toggle="popover" data-trigger="focus" title="表单字段默认值Help" data-content="该规则相关表单字段默认值"></span>
					</div>
			    </div>
			    <div class="row formAttrNameValidationIpt">
			    	<div class="col-md-3 col-lg-3 form-label">
					   <label>表单组件value值校验类型:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
				  	    <#assign valis = ["phoneNumber", "required", "email", "url", "date", "dateISO", "number", "digits", "creditcard", "accept", "minlength","maxlength", "rangelength", "min", "max", "range"]>
				  	    <input name="formAttrNameValidation" value="<#if form?? && form.getFormAttrNameValidation()??>${form.getFormAttrNameValidation()}</#if>" hidden/>
					    <select class="formAttrValiList" multiple="multiple">
					    	<#list valis as vali>
						    	<option value="<#if vdMap??&&vdMap[vali]??>${vdMap[vali]}<#else>${vali}</#if>" <#if vdMap??&&vdMap[vali]??>selected="selected"</#if> >${vali}</option>
						    </#list>
						</select>
						
						<span tabindex="0" class="iconfont icon-help" role="button" data-toggle="popover" data-trigger="focus" title="表单字段校验类型Help" 
							data-content="
								该规则相关表单组件value属性的填入值校验方式，可多选.<br>
								每个字段验证意思，请翻看https://jqueryvalidation.org/<br>
							"></span>
						
						<div class="inputForvalidType hide">
					  	   <input  class="form-control userInputForvalidType" placeholder="验证类型的额外信息"/>
					  	   <span tabindex="0" class="iconfont icon-help" role="button" data-toggle="popover" data-trigger="focus" title="表单字段校验类型Help" 
								data-content="
									['minlength','maxlength',  'min', 'max', 'range']填写多余字段时，请输入数字如： 1或者2
									['rangelength','range']填写多余字段时，请输入数字如：'2,30'或者'10, 20'
								"></span>
						</div>
					</div>
					
			    </div>
			    <div class="row">
			    	<div class="col-md-3 col-lg-3 form-label">
					   <label>表单组件填写类型填写说明:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
				  		<textarea name="formAttrIntroduce"  maxlength="500" value="<#if form?? && form.formAttrIntroduce??>${form.formAttrIntroduce!""}</#if>" class="flexTextArea" placeholder="该表单的填写说明，不按说明，提交时会失败"><#if form?? && form.formAttrIntroduce??>${form.formAttrIntroduce!""}</#if></textarea>
				   </div>
			    </div>
			    <div class="row">
				  	<div class="col-md-3 col-lg-3 form-label">
				      <label>是否设置回复语:</label>
				  	</div>
				  	<div class="col-md-5  col-lg-5 comp-group">
					    <div class="radio">
						  <label>
						    <input type="radio" value="1" name="hasReplyForFormAttr" <#if form??> <#if form.hasReplyForFormAttr?index_of("1") != -1>checked</#if></#if> >
						    	是
						  </label>
						</div>
						<div class="radio">
						  <label>
						    <input type="radio" value="0" name="hasReplyForFormAttr" <#if form??><#if form.hasReplyForFormAttr?index_of("0") != -1>checked</#if><#else>checked</#if>>
						    否
						  </label>
						</div>
				  	</div>
			    </div>
			   	<div class="row"> 
				   <div class="col-md-3 col-lg-3 form-label">
					   <label>表单组件的展示顺序:</label>
				  	</div>
				  	<div class="col-md-6  col-lg-6 comp-group">
				  		<#if form?? && form.orderForForm??>
				  			<#assign nvalue=form.orderForForm!"">
				  		<#else>
				  			<#assign nvalue=1>
				  		</#if>
				  		<#assign numAttr={"name":"orderForForm", "number": nvalue, "vali": "number", "className": "plugin-init"}>
				  	 	<#include "/widget/number.ftl">
				  	</div>
			  	</div>
			    <div class="row">
			    	<div class="col-md-2  col-lg-2 col-md-offset-3 col-lg-offset-3">
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
	seajs.use("${BASEPATH}/js/main/forms/edit");
</script>
<#include "../components/footer/footer.ftl"/> 