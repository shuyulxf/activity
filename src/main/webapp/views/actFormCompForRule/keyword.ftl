<style type="text/css">
	.keyword{
	    margin-bottom: 15px;
	}
	.keyword .form-control{
		width: auto;
		display: inline-block;
		vertical-align: middle;
	}
	.keyword .btn{
		vertical-align: midde;
	}
	.keyword .btn-info{
		margin-left: 25px;
    }
    .keyword .word{
        display: inline-block;
        vertical-align: middle;
        border: 1px solid rgba(255, 80, 48, 0.5);
        padding: 2px 5px;
        margin-top: 8px;
        margin: 8px 5px 4px;
        border-radius: 3px;
    }
    .keyword .word:hover{
        background: rgba(255, 80, 48, 0.5);
        color: #fff;
        cursor: pointer;
    }
    .keyword .word i{
        font-style: normal;
        padding-left: 5px;
        font-size: 12px;
    }
</style>
<div class="row form-item" id="${item.formAttrName}-keyword">
	<input name="ruleIds" value="${item.ruleIds}" type="hidden" />
	<div class="col-md-3 col-lg-3 form-label">
	      <label>${item.formAttrNameLabel}:</label>
  	</div>
  	<div class="col-md-5  col-lg-5 comp-group">
  		<div class="keyword">
	  		<#if activity?? && activity[item.formAttrName]??>
	  			<#assign kvalue=activity[item.formAttrName]!"">
	  		<#elseif activity?? && attrValues[item.formAttrName]??>
	  			<#assign kvalue=attrValues[item.formAttrName]!"">
	  		<#else>
	  			<#assign kvalue=item.formAttrNameDefaultValue!"">
	  		</#if>
		    <input name="${item.formAttrName}"  form-type="data"  value="${kvalue!""}"  ${item.formAttrNameValidation!""} type="hidden" class="keyword-value"/>
		    <input type="text" class="form-control keyword-input" placeholder="关键词" >
		    <button type="button" class="btn btn-default add">添加</button>
		    <button type="button" class="btn btn-info search">搜索</button>
		    <div class="word-list"></div>
		</div>
		<#if item.hasReplyForFormAttr == 1><#include "/actFormCompForRule/reply.ftl"></#if>
		<div = class="error">${item.formAttrIntroduce!""}</div>
	</div>
	
</div>
<script type="text" class="plugin-init">

		function Keyword(opts) {
		
		var defaultOpts = {
            $el: null,
            wordHtml: function(word){
                return "<li data-word='" + word + "' class='word'>" + word + "<i>X</i></li>";
            }
		}
        
        if (opts == null || opts.$el == null) return null; 
        this.options = $.extend(defaultOpts, opts);
        var $el = defaultOpts.$el,
            $keywordValues = $el.find(".keyword-value"),
            initValue      = $keywordValues.val(),
            $wordList      = $el.find(".word-list");
        
        this.options = $.extend(this.options, {
            $keywordValues: $keywordValues,
            initValue : initValue,
            $wordList : $wordList,
            $add      : $el.find(".add"),
            $search   : $el.find(".search"),
            $keywordInput : $el.find(".keyword-input")
        });
        
		this.init();
	}
	
	Keyword.prototype = {
		init: function() {
            var opts = this.options,
                $el  = opts.$el,
                $add = opts.$add,
                $search   = opts.$search,
                initValue = opts.initValue,
                $wordList = opts.$wordList,
                wordHtml  = opts.wordHtml,
                $keywordValues = opts.$keywordValues,
                $keywordInput  = opts.$keywordInput;
            var that = this;
            if (initValue != null) {
                var words = initValue.split(","),
                    len = words.length,
                    lists = [];
                for(var i = 0; i < len; i ++) {
                    var word = $.trim(words[i]);
                    if (word.length > 0) {
                        lists.push(wordHtml(word));
                    } 
                }
                $wordList.html(lists.join(""));
            }
            
            $el.on("click", ".word i", function(){
                var $this = $(this),
                    $word = $this.closest(".word"),
                    val   = $word.data("word"),
                    words = $keywordValues.val();
            
                $word.remove();
                var rgx = " *" + val + " *,? *";
                words = words.replace(new RegExp(rgx), "");
                $keywordValues.val(words.replace(/ *, *$/g, ""));
               
            });
            $keywordInput.on("input", function() {
                $wordList.find(".btn-danger").removeClass("btn-danger");
                $wordList.find(".btn-success").removeClass("btn-success");
            });
            $add.click(function(){
                var word = $keywordInput.val(),
                    words = $keywordValues.val();
                var rgxTxt = "(^|[^a-zA-Z])+" + word +"($|[^a-zA-Z])+";
                
                if (word) {
                    if (!(new RegExp(rgxTxt)).test(words)) {
                        $wordList.append(wordHtml(word));
                        if (words) words += ",";
                        words += word;
                        $keywordValues.val(words);
                    } else {
                        that.findWordLiByWord(word).addClass("btn-danger");
                    }
                    $keywordInput.val("");
                }
            });
            $search.click(function() {
                var word = $keywordInput.val();
                
                if (word) {
                    that.addSucInfoForSearch(word);
                }
            })
        },
        findWordLiByWord: function(word) {
            var opts = this.options,
                $lis = opts.$wordList.find("li");
            
            var len = $lis.length;
            for (var i = 0; i < len; i++) {
                var $li = $($lis[i]);
                if ($li.data("word") == word) return $li;
            }
            return null;
        },
        addSucInfoForSearch: function(word) {
            var opts = this.options,
                $lis = opts.$wordList.find("li");
            
            var len = $lis.length;
            for (var i = 0; i < len; i++) {
                var $li = $($lis[i]);
                if ($li.data("word").indexOf(word) != -1 ) $li.addClass("btn-success");
            }
        }
	}
    
    var keyword = new Keyword({
        $el: $("#${item.formAttrName}-keyword").find(".keyword")
    })
	
</script>