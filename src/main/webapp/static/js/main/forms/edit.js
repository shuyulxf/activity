define(function(require,exports,module) {
	
	"use strict";
	
	/* deal global error */
	require("globalError");

	/* SideBar init */
	var SideBar = require("sideBar");
	new SideBar({
		"$el" : $(".sidebar")
	});
	
	/* Breadcrumb init */
	var Breadcrumb = require("breadcrumb");
	new Breadcrumb({
		"$el" : $(".breadcrumb")
	});
	
	/* Timer init */
	var Timer = require("timer");
	new Timer({
		"$el" : $(".timer"),
		"styleClass" : "fr"
	});
	
	require("header");
	
	require("bootstrapExtend");
	require("validatorExtend");
	
	var config = require("config");
	var formActionCreate = config.formActionCreate,
		formActionEditSave = config.formActionEditSave,
		formList = config.formList;
	
	var common = require("common");
	var getErrorHtml = common.getErrorHtml,
	    getSuccessAlertHtml = common.getSuccessAlertHtml,
	    exacuteTextScript   = common.exacuteTextScript,
	    jsonFromSerialize = common.jsonFromSerialize;

	
	$('.dropdown-toggle').dropdown();
	$(".icon-help").popover({
		trigger: "hover",
		placement: "right",
		html: true
	});
	
	$('.flexTextArea').flexText();
	
	// sucess info
	var $ruleSuccessInfo = $("#ruleSuccessInfo");

	
	var	$formAttrForm = $("#formAttrForm");
	
	var $formAttrValiList = $(".formAttrValiList"),
	    $formAttrNameValidation = $("[name=formAttrNameValidation]");
	var valiTypeNeedUserInput = "minlength,maxlength, rangelength, min, max, range";
	var $userInputForvalidType = $(".userInputForvalidType");
	var $userInputForvalidTypeWrap = $(".inputForvalidType");
	
	function updateSelVal() {
		var vals = $(".formAttrValiList").val();
		if(vals) {
			$formAttrNameValidation.val(vals.join(" "));
		} else {
			$formAttrNameValidation.val("");
		}
	}
	$formAttrValiList.multiselect({
		onChange: function(option, checked, select) {
			
			var sel = option.context.value;
			var idx = sel.indexOf("=");
			if(idx == -1) idx = sel.length;
			sel = sel.slice(0, idx);
	
			var rgxTxt = "(^|[^a-zA-Z])+" + sel +"($|[^a-zA-Z])+";
			var rgx = new RegExp(rgxTxt);
			if (checked) {
				if (rgx.test(valiTypeNeedUserInput)) {
					$userInputForvalidTypeWrap.removeClass("hide");
					$userInputForvalidType.off("input").on("input", function(e) {
						option.context.value = $.trim(option.text()) + "='" + $(this).val() + "'";
						updateSelVal();
					});
				}
				else {
					updateSelVal();
				}
				
			} else { 
				if (rgx.test(valiTypeNeedUserInput)) {
					option.context.value = $.trim(option.text());
				}
				
				$userInputForvalidTypeWrap.addClass("hide");
				$userInputForvalidType.val("");
				updateSelVal();
			}

		},
		onDropdownHide: function(){
			updateSelVal();
		}
	});
	
	var formAttrValidator = $formAttrForm.validate({
		   rules: {
			   formAttrName: {
				   required: true
			   },
			   formAttrNameLabel: {
				   required: true
			   },
			   formAttrNameType: {
				   required: true
			   },
			   formAttrNameFillType: {
				   required: true
			   },
			   hasReplyForFormAttr: {
				   required: true,
				   number: true
			   },
			   exRuleGroupId: {
				   required: true,
				   number: true
			   },

		   },
		   messages: {
			   formAttrName: {
				   required: "表单属性名称不能为空"
			   },
			   formAttrNameLabel: {
				   required: "表单属性Label不能为空"
			   },
			   formAttrNameType: {
				   required: "所属活动属性类别不能为空"
			   },
			   formAttrNameFillType: {
				   required: "表单字段填写类型不能为空"
			   },
			   hasReplyForFormAttr: {
				   required: "是否设置回复语字段不能为空"
			   },
			   exRuleGroupId: {
				   required: "表单属性相关的互斥规则组不能为空",
				   number: "所属的互斥规则组Id应该为数字类型"
			   }
		   }
	});
	
	
	$("#submit").click(function() {
		var valid = $formAttrForm.valid(),
		    $this = $(this);
		if (valid && !$this.hasClass("active")) {
			$this.addClass("active");
			
			var params = jsonFromSerialize($formAttrForm.serialize())
			
			var formType = params["formAttrNameFillType"];
			var $attrs = $(".form-attr-for-" + formTypeMap[formType] + " .attr"),
			    len = $attrs.length,
			    attr = {};
			for (var i = 0; i < len; i++) {
				var $attr = $($attrs.get(i));
				var val = $attr.val();
			    if (val && val.length > 0)	attr[$attr.attr("id")] = val.replace(/\"/g, "'");
			}
			
			$.ajax({
				type: "POST",
				url : formActionCreate,
				data: {
					form: JSON.stringify(params),
					attrs: JSON.stringify(attr)
				},
				timeout: 600000,
				dataType: "json",
				success: function(data) {
				   
					if (data.status == 1) {
						
						$this.before(getErrorHtml(data.msg));
					} else {
						
						$ruleSuccessInfo.modal({
							modalBody: getSuccessAlertHtml(data.msg),
							show: true
						});
						setTimeout(function(){
							window.location.href = formList;
						}, 2000);
					}
					$this.removeClass("active");
				},
				error: function(xhr) {
					$this.before(getErrorHtml("更新失败，网络异常"));
					console.log(xhr);
					$this.removeClass("active");
				}
			})
		}
		
		return false;
	});
	
	
	var formTypeMap = {
		"select": "selects",
		"multiselect": "selects",
		"number": "num"
	}
	$("#update").click(function() {
		
		var valid = $formAttrForm.valid(),
		    $this = $(this);
		
		if (valid && !$this.hasClass("active")) {
			
			$this.addClass("active");
			
			var params = jsonFromSerialize($formAttrForm.serialize())
			
			var formType = params["formAttrNameFillType"];
			var $attrs = $(".form-attr-for-" + formTypeMap[formType] + " .attr"),
			    len = $attrs.length,
			    attr = {};
			for (var i = 0; i < len; i++) {
				var $attr = $($attrs.get(i));
			
				var val = $attr.val();
			    if (val && val.length > 0)	attr[$attr.attr("id")] = val.replace(/\"/g, "'");
			}
		
			
		    
			$.ajax({
				type: "POST",
				url : formActionEditSave,
				data: {
					form: JSON.stringify(params),
					attrs: JSON.stringify(attr)
				},
				timeout: 600000,
				dataType: "json",
				success: function(data) {
				   
					if (data.status == 1) {
						
						$this.before(getErrorHtml(data.msg));
					} else {
						
						$ruleSuccessInfo.modal({
							modalBody: getSuccessAlertHtml(data.msg),
							show: true
						});
						setTimeout(function(){
							window.location.href = formList;
						}, 2000);
					}
					$this.removeClass("active");
				},
				error: function(xhr) {
					console.log(xhr);
					$this.removeClass("active");
				}
			})
		}
		
		return false;
	});
	
	exacuteTextScript($(".rule script[type=text]"))
	exacuteTextScript($(".form").find(".plugin-init[type=text]"));
	
});