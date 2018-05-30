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
	var createOrEditActivity = config.createOrEditActivity,
		activityActionDel = config.activityActionDel;

	var common = require("common");
	var getErrorHtml = common.getErrorHtml,
	    getSuccessAlertHtml = common.getSuccessAlertHtml,
	    jsonFromSerialize   = common.jsonFromSerialize;
	
   var $activityListModal = $("#activityListModal");

	
	// init search form
	var Search = require("Search");
	new Search({
		$el: $(".search")
	});
	
	
	var Dialog = require("Dialog");
	$(".icon-delete").on("click", function(){
		
		var $this = $(this);
		
		var dialog = new Dialog({
			html: "确定要删除表单吗？",
			hasCancel: true,
			cancelBtn: "取消",
	        cancelCallback: null,
	        hasSure: true,
	        sureBtn: "确定",
	        sureCallback: function(){
	        	
	        	if ($this.hasClass("active")) return;
	        	$this.addClass("active");
	        	$.ajax({
	    			type: "POST",
	    			url:  activityActionDel,
	    			data: {
	    				activityId : $this.data("activityid")
	    			},
	    			timeout: 600000,
	    			dataType: "json",
	    			success: function(data) {
	    				
	    				if (data.status == 0) {
	    					$activityListModal.modal({
	    						modalBody: getSuccessAlertHtml(data.msg),
	    						show: true,
	    						posMiddle: true
	    					});
	    					setTimeout(function(){
	    						window.location.reload();
	    					}, 2000);
	    				} else {
	    					$activityListModal.modal({
	    						modalBody: getErrorHtml(data.msg),
	    						show: true,
	    						posMiddle: true
	    					});
	    				}
	    				$this.addClass("active")
	    			},
	    			error: function(xhr) {
	    				console.log(xhr);
	    				$activityListModal.modal({
							modalBody: getErrorHtml("更新失败，网络异常"),
							show: true
						});
	    				$this.addClass("active")
	    			}
	    		});
	        },
	        hasClose: true
		});
		
		dialog.showDialog();
		
	});
	

	$(".icon-bianji").on("click", function(){
		
		var $this = $(this);
		
		if ($this.hasClass("active")) return false;
		
	    location.href = createOrEditActivity + "?activityId=" + $this.data("activityid");
		
	});
	
	var numForPerPage = 10;
	$('#pagination').bootpag({
	    total: Math.ceil(total/numForPerPage),          // total pages
	    page: page,            // default page
	    maxVisible: 5,     // visible pagination
	    leaps: true         // next/prev leaps through maxVisible
	}).on("page", function(event, num){
		
		var params = jsonFromSerialize($(".search").serialize());
		if (params) {
			params.numPerPage = numForPerPage;
			params.page = num;
			
			window.location.href = window.location.pathname + "?paramStr=" + encodeURI(JSON.stringify(params));
		}
		
	});
	
	
	var joinUserTotalNum = config.joinUserTotalNum,
		awardUserTotalNum = config.awardUserTotalNum,
		awardLeftNum = config.awardLeftNum;
	
	// info
	var collectActivityNames = function($list, len) {
		
		if ($list == null) return $list;
		var data = [];
		for (var i = 0; i < len; i++) {
			var $item = $($list[i]);
			var name = $item.data("name");
			var awardType = $item.data("awardtype");
			
			if (name && (!awardType || awardType == "直接发放奖品")) data.push(name);
		}
		
		return data;
	}
	var findUserNumData = function(datas, name) {
		if (!datas) return null;
		var len = datas.length;
		for(var i = 0; i < len; i++) {
			var d = datas[i];
			if (d["activityName"] == name) return d;
		}
		return null;
	}
	
	var $peopleJoinNumShow = $(".people-join-num"),
	    $peopleAwardNumShow = $(".people-award-num"),
	    $awardLeftNum = $(".award-show"),
	    joinItemsLen = $peopleJoinNumShow.length,
	    awardItemsLen = $peopleAwardNumShow.length,
	    awardLeftNumLen = $awardLeftNum.length;
	
	if ($peopleJoinNumShow.length != 0) {
		
		$.ajax({
			type: "POST",
			url : joinUserTotalNum,
			data: {
				listStr: JSON.stringify(collectActivityNames($peopleJoinNumShow, joinItemsLen))
			},
			timeout: 600000,
			dataType: "json",
			success: function(data) {

				if (data.status == 0 && data.data) {
					data = JSON.parse(data.data);
					$peopleJoinNumShow.each(function(idx, item) {
						var $item = $(item),
						    name = $item.data("name");
						var d = findUserNumData(data, name);
						if (d) $item.html(d["count"]);
						else $item.html(0);
					});
				} else {
					$peopleJoinNumShow.each(function(idx, item) {
						$(item).html(0);
					});
				}
				
			},
			error: function(xhr) {
				console.log(xhr);
			}
		});
	}
	
	if ($peopleAwardNumShow.length != 0) {
		
		$.ajax({
			type: "POST",
			url : awardUserTotalNum,
			data: {
				listStr: JSON.stringify(collectActivityNames($peopleAwardNumShow, awardItemsLen))
			},
			timeout: 600000,
			dataType: "json",
			success: function(data) {

				if (data.status == 0 && data.data) {
					data = JSON.parse(data.data);
					$peopleAwardNumShow.each(function(idx, item) {
						var $item = $(item),
						    name = $item.data("name");
						var d = findUserNumData(data, name);
						if (d) $item.html(d["count"]);
						else $item.html(0);
					});
				} else {
					$peopleAwardNumShow.each(function(idx, item) {
						$(item).html(0);
					});
				}
				
			},
			error: function(xhr) {
				console.log(xhr);
			}
		});
	}
	
	
	var findAwardData = function(datas, id) {
		if (!datas) return null;
		
		var len = datas.length;
		var rlt = [];
		for(var i = 0; i < len; i++) {
	
			var d = datas[i];
			if (d["activityId"] == id) rlt.push(d);
		}
		if (rlt.length > 0) return rlt;
		
		return null;
	}
	
	var findAward = function(awardList, prizeName) {
		
		if (!awardList || !prizeName) return null;
		var len = awardList.length;
		for (var i = 0; i < len; i++) {
			var award = awardList[i];
			if (award.typeName == prizeName) return award;
		}
		return null;
	}
	
	var getAwardHtml = function(data, total, type) {
		
		if (!data) return "0";
		var len = data.length,
		    html = [];
		for (var i = 0; i < len; i++) {
			var d = data[i],
			    pName = d["prizeName"],
			    pNum = d["awardNum"];
			
			if (type == "award-send-rate") {
				var award = findAward(total, pName);
				
				if (award && award.num) {
					var left = parseInt(pNum),
					    t = parseInt(award.num);
					if (t != 0) {
						html.push(pName +": " + ((t - left) / t * 100).toFixed(2) + "%");
					} else {
						html.push("0");
					}
				}
				
			} else {
				html.push(pName +": " + pNum);
			}
		}
		
		return html.join("<br>")
	}
	
	if ($awardLeftNum.length != 0) {
		
		$.ajax({
			type: "POST",
			url : awardLeftNum,
			data: {
				listStr: JSON.stringify(collectActivityNames($awardLeftNum, awardLeftNumLen))
			},
			timeout: 600000,
			dataType: "json",
			success: function(data) {

				if (data.status == 0 && data.data) {
					data = JSON.parse(data.data);
					$awardLeftNum.each(function(idx, item) {
						var $item = $(item),
						    name = $item.data("name");
						var d = findAwardData(data, name);
						var awardType = $item.data("awardtype"),
						    type = $item.data("type");
						if (d && awardType == "直接发放奖品") {
							
							var awardListStr = $item.data("awardlist");
							
							var html = getAwardHtml(d, awardListStr,type);
							$item.html(html);
							
						}
						else {
							$item.html(awardType != "直接发放奖品" ? awardType : 0);
						}
					});
				} else {
					$awardLeftNum.each(function(idx, item) {
						$(item).html(0);
					});
				}
				
			},
			error: function(xhr) {
				console.log(xhr);
			}
		});
	}
});