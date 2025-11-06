<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>用户信息</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/style.css" />
	</head>
	<body topmargin="0" leftmargin="0">
	<table width="100%" cellpadding="0" cellspacing="0" border="0" style="width:100%;border: solid 1px #B0C9DD">
		<tr>
			<td width="80">用户编号：</td>
			<td>${user.uid }</td>
		</tr>
		<tr>
			<td>昵称：</td>
			<td>${user.nick }</td>
		</tr>
		<tr>
			<td>手机号：</td>
			<td>${user.intlPrefix }-${user.mobile }</td>
		</tr>
		<tr>
			<td>绑定第三方：</td>
			<td>${user.accountType }</td>
		</tr>
	</table>	 
	</body>
</html>