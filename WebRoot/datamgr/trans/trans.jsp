<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>翻译</title>
		<jsp:include page="/common/head.jsp" />
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：测试  &gt;&gt; 翻译</td>
    	</tr>
	  </table>
	</div>
	<br/>
	<form id="editForm" name="editForm" method="post" action="transAction!trans.do">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th colspan="2">翻译</th>
			</tr>
			<tr>
				<td></td>
				<td width="20%">语言：</td>
				<td width="78%">
					<dict:select name="from" field="speechLang" inithead="0"/>
					&nbsp;-->&nbsp;
					<dict:select name="to" field="speechLang" inithead="0"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>原文：</td>
				<td><input name="src"/></td>
			</tr>
			<tr>
				<td></td>
				<td>代理：</td>
				<td>
					<select name="proxyId">
						<option value="1">百度</option>
						<option value="2">微软</option>
						<option value="5">灵云</option>
						<option value="10">谷歌</option>
					</select>
				</td>
			</tr>			
			<tr height="30">
				<td colspan="3" align="center">
					<input type="submit" class="btn2" value="翻译"/>
				</td>
			</tr>
		</table>	 
	</form>
	<br/>
	</body>
</html>