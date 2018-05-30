define(function(require,exports,module) {
	"use strict";
	
	
	/* data type*/
	var isVarType = function() {

	    var types = ["Array", "Boolean", "Date", "Number", "Object", "RegExp", "String", "Window", "HTMLDocument"];
	    
	    function addOneDataType(type, data) {
	    	 return !(!data) && Object.prototype.toString.call(data) === "[object " + type + "]";
	    }
	    for (var i in types) {
	        var type = types[i];
	        isVarType["is" + type] = addOneDataType.bind(undefined, type);
	    }
	};
	
	isVarType();
	
	module.exports = isVarType;
});