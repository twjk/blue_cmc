<%@ page language="java"  pageEncoding="UTF-8"%>
<%@page import="com.qcmz.srm.client.util.SrmClient"%>
<html>
  <head>
    <title><%=SrmClient.getAppName() %></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css">
	<script language="javascript" src="<%=request.getContextPath() %>/javascript/jquery-1.11.0.min.js"></script>
	<script language="javascript">var contextPath='<%=request.getContextPath() %>';</script>
	<script language="Javascript" src="<%=request.getContextPath() %>/javascript/main.js"></script>
	<style>
		html{height:100%}
		body{height:100%; margin:0px; padding:0px}
	</style>
  </head>
  
  <body  style="background-color: #014FA2;">
 	<div><img id="slink" src="<%=request.getContextPath() %>/images/main/arrow_l.gif" onclick="switchBar();" alt="收起" style="cursor:pointer;position: absolute;top: 25px;left: 0px;"></div>
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
		    <td colspan="3" height="25" style="text-align: right">
		    	<span class="Textcolor3">${sessionScope.userbean.name }（${sessionScope.userbean.username }）</span>
		    	<a href="javascript:logout();" target="_top"><span class="Textcolor1" style="cursor: pointer;">安全退出</span></a>
		    	<!-- <a href="javascript:modpwd();"><span class="Textcolor1" style="cursor: pointer;">修改密码</span></a> -->
			</td>
		</tr>
		<tr>
  			<td id="ltd" height="100%" width="15%">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%">
				    <tr><td height="4"  bgcolor="#1C6FDC"></td></tr>
					<tr>
						<td height="28" bgcolor="#9AC1EF">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
	                       <tr>
	                        <td align="center"><img src="<%=request.getContextPath() %>/images/home.jpg" >&nbsp;<span class="textgray14"><%=SrmClient.getAppName() %></span></td>
	                       </tr>
	                    </table>
						</td>
					</tr>
					<tr>
				        <td  valign="top" bgcolor="#B9D6F4" height="100%">
							<iframe name="leftFrame" id="leftFrame" src="<%=request.getContextPath() %>/frame/left.jsp" width="100%" height="100%" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto"></iframe>
						</td>
					</tr>
				</table>
			</td>
 			<td align="left" valign="top" height="100%" width="85%">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%"> 
				<tr>
		        	<td valign="top" bgcolor="#FFFFFF" height="100%">
						<iframe name="mainFrame" id="mainFrame" src="<%=request.getContextPath() %>/frame/right.jsp" width="100%" height="100%" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto"></iframe>
					</td>
				</tr>
			</table>
			</td>
  		</tr>
		<tr><td colspan="3">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
			  <td class="bottom">北京蓝色链接科技有限公司 版权所有 </td>
			</tr>
		</table>
		</td><tr>
	</table>
	
	<div id="modpwd" style="width:380px;height:150px;display:none" >
         <table cellspacing="12"  class="pnr">
         	<tr><td style="text-align: right">原密码：<input type="password" id="oldpwd"  maxlength="30" style="width: 153px" /></td></tr>
        	<tr><td style="text-align: right">新密码：<input type="password" id="newpwd"  maxlength="30" style="width: 153px" /></td></tr>
        	<tr><td style="text-align: right">重复新密码：<input type="password" id="renewpwd"  maxlength="30" style="width: 153px" /></td></tr>
        	<tr><td style="text-align: center;"><input type="button"  class="btn5" value="返 回" onclick="undomodpwd();" />
        				<input type="button"  class="btn5" value="确 定" onclick="domodpwd();" /></td></tr>
       	</table>
	</div>
</body>
</html>
