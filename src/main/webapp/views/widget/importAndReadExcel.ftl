<style>
.fileupload{
	display: inline-block;
    margin-left: 5px;
}
.inputfile{
    width: 0.1px;
    height: 0.1px;
    opacity: 0;
    overflow: hidden;
    position: absolute;
    z-index: -1;
}
.fileupload .popover{
 	width: 150px;
}
.fileupload label{
	cursor: pointer;
}
</style>
<div class="fileupload" data-phoneNumberIpt="${widget.phoneNumberIpt!""}">
	<input type="file" name="phoneNumber-upload" class="phoneNumber-upload inputfile">
	<label for="phoneNumber-upload" class="phoneNumber-upload-label">
		<span class="tip">${widget.defaultLabel!""}</span>
		<span tabindex="0" class="iconfont icon-help" role="button" data-toggle="popover" data-trigger="focus" title=""  data-content="文件中需包含一列，列名为'手机号码'" data-original-title="excel文件格式说明"></span>
	</label>
	<div class="modal fade uploadfile-model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm no-margin" role="document">
	    <div class="modal-content">
	      <div class="modal-body">
	      </div>
	    </div>
	  </div>
	</div>
</div>
<script src="${BASEPATH}/js/plugin/js-xlsx/shim.js"></script>
<script src="${BASEPATH}/js/plugin/js-xlsx/xlsx.full.min.js"></script>
<script type="text" class="plugin-init">

 	var columnNameForPhoneNumber = "手机号码",
 	    maxPhoneNumberCount = 100;
 	    
	$(".icon-help").popover({
		trigger: "hover",
		placement: "right",
		html: true
	});
	$(".phoneNumber-upload-label").on("click", function() {

		var $this = $(this),
		    $upload = $($this.siblings(".phoneNumber-upload")[0]);
		
		$upload.trigger("click");
	});
	
	$(".phoneNumber-upload").on("change", function(e){
		
		var $this = $(this),
		    $label = $this.siblings(".phoneNumber-upload-label"),
		    $modal = $this.siblings(".uploadfile-model");
		
		var files = e.target.files;
		if (files.length > 0) {
			$label.find(".tip").text(files[0].name);
		
			var fileReader = new FileReader();
			fileReader.onload = function(ev) {
			
                try {
                    var data = ev.target.result,
                        workbook = XLSX.read(data, {
                            type: 'binary'
                        }); // 以二进制流方式读取得到整份excel表格对象
                } catch (e) {
                	$modal.modal({
						modalBody: "<span class='error iconfont icon-tanhao'>文件类型不正确</span>",
						show: true
					});
                    return;
                }
                
				var result = [];
                // 遍历每张表读取
                workbook.SheetNames.forEach(function(sheetName) {
					var csv = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName]);
					if (csv != null && csv.length > 0) {
					
						var len = csv.length;
						for (var i = 0; i < len; i++) {
						
							var item = csv[i];
							var phoneNumber = item[columnNameForPhoneNumber];
							if (phoneNumber) {
							
								if (/^1[3|4|5|7|8][0-9]{9}$/.test(phoneNumber)) {
									result.push(phoneNumber);
								} else {
									$modal.modal({
										modalBody: "<span class='error iconfont icon-tanhao'>" + phoneNumber + "不符合手机号码格式</span>",
										show: true
									});
									return;
								}
								
							} 
						}
					}
				});
         
               	if (result.length > maxPhoneNumberCount) {
               		$modal.modal({
						modalBody: "<span class='error iconfont icon-tanhao'>可上传的手机号码不得超过" + maxPhoneNumberCount + "个</span>",
						show: true
					});
					
					return;
               	}
               	
               	var $ipt = ${widget.phoneNumberIpt};
               	$ipt.val(($ipt.val() ? $ipt.val() + "," : "") + result.join())
             }
             fileReader.readAsBinaryString(files[0]);
		}
	});
</script>