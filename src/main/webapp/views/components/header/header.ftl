<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<meta name="description" content="" />
	<meta name="keywords" content="activity platform" />
	<link rel="shortcut icon" href="${BASEPATH}/img/favicon.ico" type="image/x-icon">
	<link href="${BASEPATH}/css/lib/bootstrap.min.css" rel="stylesheet" type="text/css">
	<link href="${BASEPATH}/css/common/base.css" rel="stylesheet" type="text/css">
	<script src="${BASEPATH}/js/lib/jquery-1.12.4.min.js"></script>
	<script src="${BASEPATH}/js/lib/jquery.validate.min.js"></script>
	<script src="${BASEPATH}/js/lib/sea.js"></script>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top header">
	  <div class="container-fluid">
	    <div class="navbar-header">
	     	<img alt="活动logo" src="${BASEPATH}/img/icon.png"/>
	     	${TITLE}
	    </div>
	    <ul class="func-menus fr">
     		<li class="menu">
     			<span class="iconfont icon-user" aria-hidden="true"></span><br/>
     			<#assign UserName=Session.UserName!"">
     			<span class="info"><#if UserName??>${UserName!""} <input type="hidden" name="userName" value="${UserName!""}"><#else>未登录</#if></span>
     		</li>
     		<li class="menu">
     			<span class="iconfont icon-main" aria-hidden="true"></span><br/>
     			<span class="info"><a href="${HOMEPAGE}">系统首页</a></span>
     		</li>
     		<li class="menu" id="logout">
     			<span class="iconfont icon-exit" aria-hidden="true"></span><br/>
     			<span class="info">退出</span>
     		</li>
     	</ul>
	  </div>	  
	</nav>
	<div class="container-fluid">
	   <div class="modal fade" id="loginInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog modal-sm no-margin" role="document">
		    <div class="modal-content">
		      <div class="modal-body">
		      </div>
		    </div>
		  </div>
	   </div>
       <div class="row">
    
      