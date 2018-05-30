<div class="col-sm-3 col-md-2 sidebar">
  <ul class="nav nav-sidebar">
    <li class="">
      	<a class="nav-plain" href="${BASEPATH}/create">
      		<span class="iconfont icon-edit" aria-hidden="true"></span>
      		活动创建
      	</a>
    </li>
    <li class="nav-list-item">
      	<p class="nav-class">
      		<span class="iconfont icon-guanli" aria-hidden="true"></span>
      		活动管理
      		<span class="glyphicon glyphicon-triangle-bottom fr" aria-hidden="true"></span>
      	</p>
    	<ul class="nav nav-items">
    		<li>
    		  <a href="${BASEPATH}/manage/online">
    		             已上线活动
    		  </a>
    		</li>
    	    <li>
    	       <a href="${BASEPATH}/manage/willonline">
    	    	未上线活动
    	       </a>
    	    </li>
    	     <li>
    	       <a href="${BASEPATH}/manage/offline">
    	    	已下线活动
    	       </a>
    	    </li>
    	</ul>
    </li>
    <li class="nav-list-item">
      	<p class="nav-class">
      		<span class="iconfont icon-shujuku" aria-hidden="true"></span>
      		详细数据管理
      		<span class="glyphicon glyphicon-triangle-bottom fr" aria-hidden="true"></span>
      	</p>
    	<ul class="nav nav-items">
    		<li>
    		  <a href="${BASEPATH}/data/activity">
    		     活动数据管理
    		  </a>
    		</li>
    	     <li>
    	       <a href="${BASEPATH}/data/user">
    	    	用户数据管理
    	       </a>
    	    </li>
    	</ul>
    </li>
    <li class="nav-list-item">
      	<p class="nav-class">
      		<span class="iconfont icon-tongji" aria-hidden="true"></span>
      		统计数据管理
      		<span class="glyphicon glyphicon-triangle-bottom fr" aria-hidden="true"></span>
      	</p>
    	<ul class="nav nav-items">
    		<li>
    		  <a href="${BASEPATH}/statistic/activity">
    		     活动统计管理
    		  </a>
    		</li>
    	    <li>
    	       <a href="${BASEPATH}/statistic/prize">
    	    	奖品统计管理
    	       </a>
    	    </li>
    	     <li>
    	       <a href="${BASEPATH}/statistic/user">
    	    	用户统计管理
    	       </a>
    	    </li>
    	</ul>
    </li>

    <#assign UserNameRole=Session.UserNameRole!"">
    <#if UserNameRole?contains("_activity_admin")>
    <li class="nav-list-item">
      	<p class="nav-class">
      		<span class="iconfont icon-guizezujian" aria-hidden="true"></span>
      		规则组管理
      		<span class="glyphicon glyphicon-triangle-bottom fr" aria-hidden="true"></span>
      	</p>
    	<ul class="nav nav-items">
    		<li>
    		  <a href="${BASEPATH}/rulegroup/create">
    		               规则组创建
    		  </a>
    		</li>
    	    <li>
    	       <a href="${BASEPATH}/rulegroup/list">
    	                                规则组列表
    	       </a>
    	    </li>
    	</ul>
    </li>
    <li class="nav-list-item">
      	<p class="nav-class">
      		<span class="iconfont icon-guizeguanli" aria-hidden="true"></span>
      		规则管理
      		<span class="glyphicon glyphicon-triangle-bottom fr" aria-hidden="true"></span>
      	</p>
    	<ul class="nav nav-items">
    	    <li>
    	       <a href="${BASEPATH}/rule/createOrEdit">
    	                                规则编辑
    	       </a>
    	    </li>
    	    <li>
    	       <a href="${BASEPATH}/rule/list">
    	                                规则列表
    	       </a>
    	    </li>
    	</ul>
    </li>
    <li class="nav-list-item">
      	<p class="nav-class">
      		<span class="iconfont icon-form" aria-hidden="true"></span>
      		活动表单组件管理
      		<span class="glyphicon glyphicon-triangle-bottom fr" aria-hidden="true"></span>
      	</p>
    	<ul class="nav nav-items">
    	    <li>
    	       <a href="${BASEPATH}/form/createOrEdit">
    	                                活动表单组件编辑
    	       </a>
    	    </li>
    	    <li>
    	       <a href="${BASEPATH}/form/list">
    	                                活动表单组件列表
    	       </a>
    	    </li>
    	</ul>
    </li>
    <li class="nav-list-item">
      	<p class="nav-class">
      		<span class="iconfont icon-yingxiaohuodong_jiangpinfahuoguanli" aria-hidden="true"></span>
      		奖品管理
      		<span class="glyphicon glyphicon-triangle-bottom fr" aria-hidden="true"></span>
      	</p>
    	<ul class="nav nav-items">
    		<li>
    		  <a href="${BASEPATH}/prize/create">
    		     奖品添加
    		  </a>
    		</li>
    		<li>
    		  <a href="${BASEPATH}/prize/list">
    		     奖品列表
    		  </a>
    		</li>
    	</ul>
    </li>
    <li class="nav-list-item">
      	<p class="nav-class">
      		<span class="iconfont icon-setting" aria-hidden="true"></span>
      		系统管理
      		<span class="glyphicon glyphicon-triangle-bottom fr" aria-hidden="true"></span>
      	</p>
    	<ul class="nav nav-items">
    		<li>
    		  <a href="${BASEPATH}/system/reload">
    		   系统重载
    		  </a>
    		</li>
    		<li>
    		  <a href="${BASEPATH}/system/test">
    		  测试接口
    		  </a>
    		</li>
    	</ul>
    </li>
    </#if>
  </ul>
</div>