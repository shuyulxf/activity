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
	
	var common = require("common"),
		exacuteTextScript = common.exacuteTextScript,
		jsonFromSerialize   = common.jsonFromSerialize,
		getSuccessAlertHtml = common.getSuccessAlertHtml,
		getErrorHtml = common.getErrorHtml;
	
	var config = require("config"),
	    systemInterfaceTest = config.systemInterfaceTest;
	
	var $form = $("#testForm");
	var validator = $form.validate({
	   rules: {
		   phoneNumber: {
			   required: true,
			   minlength: 11,
			   maxlength: 11
		   },
		   province: {
			   required: true,
			   province: true
		   },
		   query: {
			   required: true
		   }
	   },
	   messages: {
		   phoneNumber: {
			   required: "活动参与者的手机号码不能为空",
			   minlength: "手机号码不能少于11位",
			   maxlength: "手机号码不能多于11位"
		   },
		   province: {
			   required: "活动参与者所在省份不能为空",
			   province: "活动参与者所在省份为必填"
		   },
		   query: {
			   required: "输入词不能为空"
		   } 
	   }
	});
	
	var $createRltInfo = $("#createRltInfo");
	
	$("#submit").click(function(e) {
		var valid = $form.valid(),
		    $this = $(this);
		
		if ($this.hasClass("active")) return false;
		
		$this.addClass("active");
		
		if (valid) {
			$.ajax({
				type: "POST",
				url: systemInterfaceTest,
				data: {
					params: JSON.stringify(jsonFromSerialize($form.serialize()))
				},
				timeout: 600000,
				dataType: "json",
				success: function(data) {
				    
					if (data.status == 1) {
						
						$this.before(getErrorHtml(data.msg));
					} else {
						
						var rlts = data.result;
						var msg;
						
						if (rlts) {
							var rl = rlts.length,
							    html = ["接口返回结果解析：<br>",
							            "<table border='1' class='tbl'>",
							            "<thead>",
							            "<tr>",
							            "<th>活动名称</th>",
							            "<th>参与结果</th>",
							            "</tr>",
							            "</thead>",
							            "<tbody>"];
							for (var i = 0; i < rl; i++) {
								
								var rlt = rlts[i];
								var tr = ["<tr>", 
								          "<td>" + rlt.activityName + "</td>"];
								if (rlt.msg) tr.push("<td>" + rlt.msg + "</td>");
								else tr.push("<td>" + rlt.info + "</td>");
								
								tr.push("</tr>");
								html.push(tr.join(""));
							}
							if(rl == 0) html.push("<tr><td>没有找到可参与的活动</td></tr>");
							html.push("</tbody>");
							html.push("</table>");
							
							msg = html.join("");
							
						} else {
							msg = data.msg;
						}
						
						$createRltInfo.modal({
							modalBody: getSuccessAlertHtml(msg),
							show: true
						});
					}
					$this.removeClass("active");
				},
				error: function(xhr) {
					console.log(xhr);
					$this.removeClass("active");
					$this.before(getErrorHtml("更新失败，网络异常"));
				}
			})
		}
		return false;
	});

	exacuteTextScript($("#testForm").find(".plugin-init[type=text]"));
	
});