<style type="text/css">
    
    .activity-list .activity-item{
    	margin: 10px 0 10px 0;
    }
    
    .activity-list .del{
    	margin-left: 5px;
    }
    .activity-list .error{
    	padding-left: 5px;
    }
</style>
<div class="row form-item hide" id="${item.formAttrName}-activityList">
	<input name="ruleIds" value="${item.ruleIds}" type="hidden" />
	<div class="col-md-3 col-lg-3 form-label">
	      <label>${item.formAttrNameLabel}:</label>
  	</div>
  	<div class="col-md-6  col-lg-6 comp-group">
  		<div class="activity-list">
	  		<#if activity?? && activity[item.formAttrName]??>
	  			<#assign alvalue=activity[item.formAttrName]!"">
	  		<#elseif activity?? && attrValues[item.formAttrName]??>
	  			<#assign alvalue=attrValues[item.formAttrName]!"">
	  		<#else>
	  			<#assign alvalue=item.formAttrNameDefaultValue!"[]">
	  		</#if>
		    <input name="${item.formAttrName}"  form-type="data"   ${item.formAttrNameValidation!""} type="hidden" class="activity-list-values" value=<#if alvalue??>${alvalue}</#if> >
		   	<div class="hide activity-item">
		   		<span class="activity-type"></span>
		   		<button class="btn-ml btn btn-default del-type" type="button">删除</button>
		   	</div>
		   	
		   	<#if alvalue??>
		     	<#assign activityItems = alvalue?eval />
		     	<#list activityItems as activityItem>
		        	<div class="activity-item">
				   		<span class="activity-type">${activityItem.type}</span>
				   		<button class="btn-ml btn btn-default del-type" type="button">删除</button>
				   	</div>
		        </#list>
		    </#if>
		   	<#assign faDatas>
		   		{"formAttrData": "['签到', '搜索', '有奖问答']"}
			</#assign>
		    <#assign widget={"hasName": false, "name": item.formAttrName, "vali": item.formAttrNameValidation!"", "className": "plugin-init",  "fromAttrs": faDatas}>
  			<#include "/widget/select.ftl">
            <button class="btn-ml btn btn-default add-type"  type="button">新增活动</button>
            <#include "/widget/question.ftl">
		</div>
		<#if item.hasReplyForFormAttr == 1><#include "/actFormCompForRule/reply.ftl"></#if>
		<div = class="error">${item.formAttrIntroduce!""}</div>
	</div>
	
</div>
<script type="text" class="plugin-init">

	function ActivityList(opts) {
		
		var defaultOpts = {
            $el: null
		}
        
        if (opts == null || opts.$el == null) return null; 
		
		var $el = opts.$el,
		    $ipt, $add, $questions, $typeSel;
		if ($el) {
			$ipt = $el.find(".activity-list-values"),
		    $add = $el.find(".add-type");
		    $questions = $("#${item.formAttrName}-question");
		    $typeSel = $("#${item.formAttrName}-select");
		}
		
        this.options = $.extend(defaultOpts, opts, {
            $errorELe : $("<span class='btn-ml error'></span>") 
        }, {
        	"$ipt": $ipt,
        	"$add": $add,
        	"$questions": $questions,
        	"$typeSel": $typeSel,
        	"questionList": []
        });

		this.init();
	}
	
	ActivityList.prototype = {
		init: function() {
		
			var opts = this.options,
                $el  = opts.$el,
                $add = opts.$add,
                $ipt = opts.$ipt,
                $activityList = $el.find(".activity-list-values"),
                $type = $el.find(".value"),
                lastType = $type.val(),
                $questions = opts.$questions,
                $questionValues = $questions.find(".question-list"),
                lastQuestions = $questionValues.val(),
                $typeSel = opts.$typeSel,
                $item = $el.find(".hide.activity-item"),
                that = this,
                $errorELe = opts.$errorELe,
                questionList = opts.questionList;
			
			var defaultActivityList = ${alvalue},
			    len = defaultActivityList.length;
			for (var i = 0; i < len; i++) {
				var activity = defaultActivityList[i];
				if (activity.type == "有奖问答") {
					questionList.push(activity.attributes);
				}
			}

		   var defaultValue = $activityList.val(),
               activityList = defaultValue && JSON.parse(defaultValue);
           
           if (lastType != "有奖问答") $questions.addClass("hide");
           
           $type.change(function() {
           		var $this = $(this),
           		    type = $this.val();
           		
           		if (type != lastType) {
           			console.log(type);
           			lastType = type;
           			
           			if (type == "有奖问答") $questions.removeClass("hide");
           			else $questions.addClass("hide");
           		}
           });
           
           $questionValues.change(function() {
           		var $this = $(this),
           		    questions = $this.val(),
           		    $activityItem = $this.closest(".activity-item"),
                    $data = $activityItem.find(".activity-type"),
                    idx = $el.find(".activity-item").not(".hide").index($activityItem);
           		
           		if (questions != lastQuestions) {
           			
           			lastQuestions = questions;
           		}
           });
           
           
           $el.on("click", ".add-type", function() {
    	
           	   var type = $type.val(),
           	       questionValues = $questionValues.val();
           	  
           	   if (type == "有奖问答" && (!questionValues || questionValues == "[]")) {
           	 		
           	  	  	$el.find(".error").remove();
             		$add.after($errorELe.html("有奖问答至少要添加一个问题"));
             		return;
           	   } else {
           	  	 $el.find(".error").remove();
           	   }
           	   
           	   if (type == "有奖问答") {
           	   
           	     var $items = $el.find(".activity-item").not(".hide");
           	   	 var idx = $items.filter(function(i){
           	   	 	if ($($items[i]).find(".activity-type").html() == "有奖问答") return true;
           	   	 }).length;
           	   	 questionList.splice(idx, 0, lastQuestions);
           	   }

           	
           	   var $newItem = $item.clone();
           	  	   $newItem.find(".activity-type").html(type);
           	  	   $newItem.removeClass("hide");
           	  	   $typeSel.before($newItem);
           	  
           	   
           	  	   
           	   that.update($newItem);
       	    
               var $questionItems = $questions.find(".question-item").not(".hide");
				
               var len = $questionItems.length;
               for (var i = 0; i < len; i++) {
               	  var $ques = $($questionItems[i]);
               	 
               	 $ques.find(".problem-stem input").val("");
               	 
               	 var $options = $ques.find(".problem-options input"),
               	     len = $options.length;
               	 for (var i = 0; i < len; i++) {
               	 	var $option = $($options[i]);
               	 	$option.val("");
               	 }
               	 
               	 $ques.find(".problem-answer input").val("");
               }
                
           	   
           	});
           
            $el.on("click", ".del-type", function() {
            
           	  var type = $type.val(),
           	      $activityItems = $el.find(".activity-item").not(".hide"),
           	      len = $activityItems.length,
           	      $this = $(this),
           	      $activityItem = $this.closest(".activity-item");
           	  
           	  if (len <= 1) {
           	  	$activityItem.find(".error").remove();
                $this.after($errorELe.html("有奖问答的问题选项不得少于2个"));
           	  } else {
           	    $activityItem.remove();
           	    if (type == "有奖问答") {
	     
           	     	var idx = $activityItems.filter(function(i){
	           	   	 	if ($($activityItems[i]).find(".activity-type").html() == "有奖问答") return true;
	           	   	}).index($activityItem);
           	    	questionList.splice(idx, 1);
           	    }
           	  	that.update();
           	  }
           	  
           });
        },
        update: function($newItem) {
        
        	 var opts = this.options,
                 $el  = opts.$el,
                 $add = opts.$add,
                 $ipt = opts.$ipt,
                 $activityList = $el.find(".activity-list-values"),
                 $activityItems = $el.find(".activity-item").not(".hide"),
                 $questions = opts.$questions,
                 $questionValues = $questions.find(".question-list"),
                 activityList = [],
                 $errorELe = opts.$errorELe,
                 questionList = opts.questionList;
  			
             var ail = $activityItems.length;
             for (var i = 0; i < ail; i++) {
             	var $activityItem = $($activityItems[i]),
             	    obj = {},
             	    type = $activityItem.find(".activity-type").text();
        
             	obj.type = type;
             	
             	if (type == "有奖问答") {
             		var value = $questionValues.val();
             		if (value) {
	             		var idx = $activityItems.filter(function(i){
		           	   	 	if ($($activityItems[i]).find(".activity-type").html() == "有奖问答") return true;
		           	   	}).index($activityItem);
             			 obj.attributes = questionList[idx];
             		} else {
             			$el.find(".error").remove();
             		 	$add.after($errorELe.html("有奖问答至少要添加一个问题"));
             		 	return;
             		}
             	}
             	activityList.push(obj);
             }
             
             $activityList.val(JSON.stringify(activityList)); 
        }
	}
    
    var activityList = new ActivityList({
        $el: $("#${item.formAttrName}-activityList").find(".activity-list")
    })
	
</script>