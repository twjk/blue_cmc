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
	<!-- Font Awesome 图标库 -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
	<!-- Google Fonts -->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
	<!-- 现代化样式 -->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/modern.css?v=2.1">
	<script language="javascript" src="<%=request.getContextPath() %>/javascript/jquery-1.11.0.min.js"></script>
	<script language="javascript">var contextPath='<%=request.getContextPath() %>';</script>
	<script language="Javascript" src="<%=request.getContextPath() %>/javascript/main.js"></script>
	<style>
		html{height:100%}
		body{height:100%; margin:0px; padding:0px}
	</style>
  </head>
  
  <body style="background-color: #F9FAFB;">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">

		<tr>
  			<td id="ltd" height="100%" style="position: relative; z-index: 100;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%">
				    <tr><td height="2"  bgcolor="#007AFF"></td></tr>
					<tr>
						<td height="28" bgcolor="#FFFFFF" style="border-bottom: 1px solid #E5E7EB; padding: 12px 16px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
	                       <tr>
	                        <td align="left" style="font-weight: 600; font-size: 15px; color: #111827;"><i class="fa-regular fa-house" style="margin-right: 8px; color: #3B82F6;"></i><%=SrmClient.getAppName() %></td>
	                       </tr>
	                    </table>
						</td>
					</tr>
					<tr>
				        <td  valign="top" bgcolor="#F9FAFB" height="100%">
							<iframe name="leftFrame" id="leftFrame" src="<%=request.getContextPath() %>/frame/left.jsp" width="100%" height="100%" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto"></iframe>
						</td>
					</tr>
				</table>
				
			</td>
 			<td align="left" valign="top" height="100%" style="width: auto;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" height="100%"> 
				<tr>
		        	<td valign="top" bgcolor="#FFFFFF" height="100%">
						<iframe name="mainFrame" id="mainFrame" src="<%=request.getContextPath() %>/frame/right.jsp" width="100%" height="100%" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto"></iframe>
					</td>
				</tr>
			</table>
			</td>
  		</tr>
		<tr><td colspan="3" height="40" style="background-color: #FFFFFF; border-top: 1px solid #E5E7EB;">
		<div style="width: 100%; height: 100%; display: flex; align-items: center; justify-content: center; box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.05);">
			<span style="color: #6B7280; font-size: 14px; font-weight: 500; font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; letter-spacing: 0.5px;">CMC System</span>
		</div>
		</td></tr>
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
