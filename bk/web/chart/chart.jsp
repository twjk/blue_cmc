<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
  <head>
    <title>图表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=request.getContextPath() %>/css/mobile.css" rel="stylesheet" type="text/css"/>
	
	<script type="text/javascript" src="<%=request.getContextPath() %>/javascript/jquery-1.3.2.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/javascript/chart/chart.js"></script>
  </head>
  
  <body>
  	<div class="blank"></div>
  	<div id="todayCount">正在加载ApiKey今日访问量...</div>
  	<div class="blank"></div>
  	<div id="dayCount">正在加载ApiKey每日访问量...</div>
  </body>
</html>
