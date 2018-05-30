define(function(require,exports,module) {
	"use strict";
	
	$.validator.setDefaults({
		 ignore: ".ignore",
		 errorElement: "span",
	     errorPlacement: function ( error, element ) {
	       error.addClass("iconfont icon-tanhao");
		   element.parents( ".comp-group" ).append(error);
	     }
	});
	
	$.validator.addMethod("weight", function(value, element) {
		
	   return this.optional( element ) || /^[\d]+$/g.test(value);
	}, "请输入正确的权重值，范围为[0, 9]");
	
	$.validator.addMethod("phoneNumber", function(value, element) {
	    var length = value.length;
	    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
	    return this.optional(element) || (length == 11 && mobile.test(value));
	}, "请正确填写您的手机号码");
	
	$.validator.addMethod("province", function(value, element) {
		var valids = "北京,天津,河北,山西,内蒙古,辽宁,吉林,黑龙江,上海,江苏,浙江,安徽,福建,江西,山东,河南,湖北,湖南,广东,广西,海南,重庆,四川,贵州,云南,西藏,陕西,甘肃,青海,宁夏,新疆,台湾,香港,澳门"

	    return this.optional(element) || valids.indexOf(value) == -1 ? false:true;
	}, "请填入正确的省份");
	
});