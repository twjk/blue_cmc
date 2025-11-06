<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  	<base href="<%=basePath%>"></head>
    <title>用户功能树</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="css/style.css">
<style type="text/css">
<!--
*{margin:0;padding:0;border:0;}
#nav {
 width:100%;  line-height:25px; 
 text-align:left; list-style:none; border:#93AFD4 solid 1px;overflow:hidden
    /*定义整个ul菜单的行高和背景色*/
}
/*==================一级目录===================*/
#nav a {
 width:100%; 
 display: block;
 padding-left:20px;
 /*Width(一定要)，否则下面的Li会变形*/
}
#nav li { background-image:url(images/UL.jpg); width:100%; /*一级目录的背景色*/
 float:left; font-size:13px;

 /*float：left,本不应该设置，但由于在Firefox不能正常显示
 继承Nav的width,限制宽度，li自动向下延伸*/
}
#nav a:link  {
 color:#112C82; text-decoration:none;font-weight:bold;width:100%; 
}
#nav a:visited  {
 color:#112C82;text-decoration:none;font-weight:bold;width:100%; 
}
#nav a:hover  {
 color:#112C82;text-decoration:none;font-weight:bold;width:100%; 
}
/*==================二级目录===================*/
#nav li ul {
 list-style:none;
 text-align:left;width:100%; 
}
#nav li ul li{ 
 background: #ffffff;width:100%; /*二级目录的背景色*/
   border-bottom:#F4F4F4 1px solid;  /*下面的一条白边*/
}
#nav li ul a{
         padding-left:20px;
         width:100%;
 /* padding-left二级目录中文字向右移动，但Width必须重新设置=(总宽度-padding-left)*/
}
/*下面是二级目录的链接样式*/
#nav li ul a:link  {
 color:#112C82; text-decoration:none; font-weight:normal;
}
#nav li ul a:visited  {
 color:#112C82;text-decoration:none; font-weight:normal; 
}
#nav li ul a:hover {
 color:#002AB0; font-weight:bold;
 text-decoration:none; background-color:#F6F6F6;
 /* 二级onmouseover的字体颜色、背景色*/
}
/*==============================*/
#nav li:hover ul {
 left: auto;
}
#nav li.sfhover ul {
 left: auto;
}
#content {
 clear: left; 
}
#nav ul.collapsed {
 display: none;
}
-->
#PARENT{
 width:180px; margin:0 auto 0;
}
</style>
<body style="background-color:#B9D6F4;overflow:hidden">
<div style="height:10px"></div>
<div id="PARENT">
<ul id="nav">
<s:iterator value="#session.userbean.treeRights" id="right" status="st">
<li>
	<a href="javascript:void(0);"  onclick="DoMenu('ChildMenu${st.count }')">${right.rightname }</a>
	<ul id="ChildMenu${st.count }" class="collapsed">
		<s:iterator value="chdrights">
			<li><a href="<s:property value="url"/>" target="mainFrame">·<s:property value="rightname"/></a></li>
		</s:iterator>
	 </ul>
</li>
</s:iterator>
</ul>
</div>
</body>
<script type=text/javascript><!--
var LastLeftID = "";
function menuFix() {
 var obj = document.getElementById("nav").getElementsByTagName("li");
 
 for (var i=0; i<obj.length; i++) {
  obj[i].onmouseover=function() {
   this.className+=(this.className.length>0? " ": "") + "sfhover";
  }
  obj[i].onMouseDown=function() {
   this.className+=(this.className.length>0? " ": "") + "sfhover";
  }
  obj[i].onMouseUp=function() {
   this.className+=(this.className.length>0? " ": "") + "sfhover";
  }
  obj[i].onmouseout=function() {
   this.className=this.className.replace(new RegExp("( ?|^)sfhover\\b"), "");
  }
 }
}
function DoMenu(emid)
{
 var obj = document.getElementById(emid); 
 obj.className = (obj.className.toLowerCase() == "expanded"?"collapsed":"expanded");
 if((LastLeftID!="")&&(emid!=LastLeftID)) //关闭上一个Menu
 {
  document.getElementById(LastLeftID).className = "collapsed";
 }
 LastLeftID = emid;
}
function GetMenuID()
{
 var MenuID="";
 var _paramStr = new String(window.location.href);
 var _sharpPos = _paramStr.indexOf("#");
 
 if (_sharpPos >= 0 && _sharpPos < _paramStr.length - 1)
 {
  _paramStr = _paramStr.substring(_sharpPos + 1, _paramStr.length);
 }
 else
 {
  _paramStr = "";
 }
 
 if (_paramStr.length > 0)
 {
  var _paramArr = _paramStr.split("&");
  if (_paramArr.length>0)
  {
   var _paramKeyVal = _paramArr[0].split("=");
   if (_paramKeyVal.length>0)
   {
    MenuID = _paramKeyVal[1];
   }
  }
  /*
  if (_paramArr.length>0)
  {
   var _arr = new Array(_paramArr.length);
  }
  
  //取所有#后面的，菜单只需用到Menu
  //for (var i = 0; i < _paramArr.length; i++)
  {
   var _paramKeyVal = _paramArr[i].split('=');
   
   if (_paramKeyVal.length>0)
   {
    _arr[_paramKeyVal[0]] = _paramKeyVal[1];
   }  
  }
  */
 }
 
 if(MenuID!="")
 {
  DoMenu(MenuID)
 }else{
 //如果没有选中的menu，那么默认第一个打开
 var obj = document.getElementById("nav").getElementsByTagName("ul");
 DoMenu(obj[0].id)
 }
}
GetMenuID(); //*这两个function的顺序要注意一下，不然在Firefox里GetMenuID()不起效果
menuFix();
--></script> 
</html>
