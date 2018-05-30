<style type="text/css">
	.question .form-control{
		width: auto;
		display: inline-block;
        vertical-align: middle;
        margin-left: 10px;
    }
    .question .hide{
        display: none;
    }
    .question .pro-label{
        width: 60px;
    }
    .question .problem-stem, .question .problem-options, .problem-answer {
        margin: 10px 0;
    }
    .question .btn-ml{
        margin-left: 70px;
    }
    .question .btn-ml-5{
        margin-left: 5px;
    }
    .question .error, .question .tip{
        color: red;
        margin-bottom: 0;
    }
</style>
<div class="question" id="${widget.name!""}-question">
    <input <#if !(widget.hasName??)>name="${widget.name!""}"</#if> form-type="data"  ${widget.vali!""}   type="hidden" class="question-list" value=<#if widget.value??>${widget.value}<#else>"[]"</#if>   />
    <div class="question-item hide">
        <div class="problem-stem">
            <label class="pro-label">问题题干:</label><input type="text" class="form-control" placeholder="请输入有奖问答的问题题干" />
            <button class="btn btn-default delAll"  type="button">删除问题</button>
        </div>
        <div class="problem-options hide">
            <label class="pro-label">问题选项:</label><input type="text" class="form-control" placeholder="请输入有奖问答的问题选项" /><button class="btn-ml-5 btn btn-default"  type="button">删除</button>
        </div>
        <button class="btn-ml btn btn-default oadd"  type="button">新增问题选项</button>
        <div class="problem-answer">
            <label class="pro-label">问题答案:</label><input type="text" class="form-control" placeholder="问题答案，是问题选项的添加序号"  number/>
            <p class='btn-ml tip'>为问题题干的添加顺序，若题干有两项，则可填写的值为1和2</p>
        </div>
    </div>
    <button class="btn-ml btn btn-default add" type="button">新增问题</button>
</div>

<script type="text" class="plugin-init">

	function Question(opts) {

		var defaultOpts = {
            $el: null
		}
        
        if (opts == null || opts.$el == null) return null; 

        this.options = $.extend(defaultOpts, opts, {
            $errorELe : $("<p class='btn-ml error'></p>") 
        });
        
		this.init();
	}
	
	Question.prototype = {
		init: function() {

            var that = this,
                opts = this.options,
                $el  = opts.$el,
                $questionList = $el.find(".question-list"),
                $questionItem = $el.find(".question-item.hide"),
                $qAdd = $el.find(".add"),
                $errorELe = opts.$errorELe;

            var defaultValue = $questionList.val(),
                questionList = defaultValue && JSON.parse(defaultValue);
            if (questionList && questionList.length > 0) {
                var ql = questionList.length;
                for (var i = 0; i < ql; i++) {
                    var question = questionList[i];
                    var $qItem = $questionItem.clone(),
                        $qStem = $qItem.find(".problem-stem"),
                        $qOpt  = $qItem.find(".problem-options:first"),
                        $qoAdd = $qItem.find(".oadd"),
                        $qAnsw = $qItem.find(".problem-answer");
                    
                    $qStem.find("input").val(question["question"]);
                    
                    var options = question.options,
                        ol = options.length;
                    for (var j = 0; j < ol; j++) {
                        var $opt = $qOpt.clone();
                        if (j != 0) $opt.find("label").html("");
                        $opt.find("input").val(options[j]);
                        $opt.removeClass("hide");
                        $qoAdd.before($opt);
                    }
                    $qAnsw.find("input").val(question["answer"]);
                    $qItem.removeClass("hide");
                    $qAdd.before($qItem);
                }
            } else {

                var $newQItem = $questionItem.clone();
                $newQItem.removeClass("hide");
                var $newOpt = $newQItem.find(".problem-options").removeClass("hide").clone(),
                    $oadd = $newQItem.find(".oadd");
                $newOpt.find("label").html("");
                $oadd.before($newOpt);
                $qAdd.before($newQItem);
            } 
			
			$el.on("click", ".delAll", function(){
			  var $this = $(this),
                  $ques = $this.closest(".question-item"),
                  $queses = $el.find(".question-item").not(".hide");
               
               var len = $queses.length;
               
               if (len <= 1) {
              		$ques.find(".error").remove();
                    $qAdd.before($errorELe.html("有奖问答的问题选项不得少于2个"));
                    
               } else {
               		$ques.remove();
               		that.update();
               }
				return false;
			});
			
            $el.on("click", ".problem-options .btn", function() {
            
                var $this = $(this),
                    $opt = $this.closest(".problem-options"),
                    $ques = $this.closest(".question-item"),
                    $opts = $ques.find(".problem-options").not(".hide"),
                    $oAdd = $ques.find(".oadd");

                if ($opts.length <= 2) {
                    $ques.find(".error").remove();
                    $oAdd.before($errorELe.html("有奖问答的问题选项不得少于2个"));
                   
                } else {
                    $opt.remove();
                    $el.find(".problem-options").not(".hide").filter(":first").find("label").html("问题题干");
                    that.update();
                }

                
                
                return false;
            })

            $el.on("click", ".oadd", function() {

                var $this = $(this),
                    $ques = $this.closest(".question-item"),
                    $opt  = $ques.find(".problem-options:first").clone();
                
                $ques.find(".error").remove();
                $opt.find("label").html("");
                $opt.find("input").val("");
                $opt.removeClass("hide");
                $this.before($opt);
                
                return false;
            })

            $el.on("click", ".add", function() {
            
            	var $questionItems = $el.find(".question-item").not(".hide"),
            	    l = $questionItems.length;

            	for (var i = 0; i < l; i++) {
            	
            		var $qi = $($questionItems[i]),
            		    $ipts = $qi.find(".problem-options, .problem-answer").not(".hide").find("input"),
            		    ol = $ipts.length;
            		    
            		for (var j = 0; j < ol; j++) {
            		
            			var $ipt = $($ipts[j]),
            			    v = $ipt.val();
            			if (!v || !$.trim(v)) {
            				$qAdd.before($errorELe.addClass("global").html("请先填完整当前问题"));
            				return false;
            			}
            		}
            		
            	}
            	
                var $newQItem = $questionItem.clone();
                $newQItem.removeClass("hide");
                var $newOpt = $newQItem.find(".problem-options").removeClass("hide").clone(),
                    $oadd = $newQItem.find(".oadd");
                $newOpt.find("label").html("");
                $oadd.before($newOpt);
                function reset (){
                
	                var  $ipts = $newQItem.find("input"),
	        		    ol = $ipts.length;
	        		    
	        		for (var j = 0; j < ol; j++) {
	        			$($ipts[j]).val("");
	        		}
                }
                
        		reset();

                $qAdd.before($newQItem);
                
                return false;
            })
            
            var isUpdate = false;
            $el.on("input", ".problem-answer input", function() {
                var $this = $(this),
                    val = $this.val(),
                    $ques = $this.closest(".question-item"),
                    $opts = $ques.find(".problem-options").not(".hide");
                
                isUpdate = false;

                var l = $opts.length;
                $el.find(".global.error").remove();
                
                if (val && $.trim(val)) {
                    v = new Number($.trim(val))
                    if (isNaN(v) || v > l) {
                        $qAdd.before($errorELe.addClass("global").html("输入的答案无效，请输入1-" + l + "的有效数字"));
                        isUpdate = true;
                    }
                } else {
                    $qAdd.before($errorELe.addClass("global").html("请输入答案，请输入1-" + l + "的有效数字"));
                    isUpdate = true;
                }
            })

            $el.on("input", "input", function() {
                if (isUpdate) return false;
                that.update();
            })
        },
        update: function() {
            var that = this,
                opts = this.options,
                $el  = opts.$el,
                $questionList = $el.find(".question-list"),
                $questions = $el.find(".question-item").not(".hide"),
                l = $questions.length,
                $qAdd = $el.find(".add"),
                $errorELe = opts.$errorELe;

            $el.find(".global.error").remove();
            
            var questions = [];
            for (var i = 0; i < l; i++) {
                var $question = $($questions[i]),
                    question = {};
                
                var $quesStem = $question.find(".problem-stem").find("input"),
                    $quesOpts = $question.find(".problem-options").not(".hide").find("input"),
                    $quesAnsw = $question.find(".problem-answer").find("input");

                var quesStem = $quesStem.val();
                if (quesStem && $.trim(quesStem)) {
                    question["question"] = $.trim(quesStem)
                } else {
                    $qAdd.before($errorELe.addClass("global").html("请输入有奖问答的问题题干"));
                    return false;
                }

                var qol = $quesOpts.length,
                    opts = [];
                for (var j = 0; j < qol; j++) {
                    var opt = $($quesOpts[j]).val();
                    if (opt && $.trim(opt)) {
                        opts.push($.trim(opt));
                    } else {
                        $qAdd.before($errorELe.addClass("global").html("请输入有奖问答的问题选项"));
                        return false;
                    }
                }
                question["options"] = opts;

                var quesAnsw = $quesAnsw.val()
                if (quesAnsw && $.trim(quesAnsw)) {
                    question["answer"] = $.trim(quesAnsw)
                } else {
                    $qAdd.before($errorELe.addClass("global").html("请输入有奖问答的问题答案"));
                    return false;
                }

                questions.push(question);
            }

            $questionList.val(JSON.stringify(questions));
            
            $questionList.trigger('change');
        }
	}
    
    var question = new Question({
        $el: $("#${widget.name!""}-question")
    })
	
</script>