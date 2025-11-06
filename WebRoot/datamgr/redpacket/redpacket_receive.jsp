<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>红包管理</title>
		<jsp:include page="/common/head.jsp" />
		<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/config.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：红包管理  &gt;&gt; 领红包</td>
    	</tr>
	  </table>
	</div>
	<br/>
	<form id="editForm" name="editForm" enctype="multipart/form-data" method="post" action="/cmc/services-ssl/rp!receive.do">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th colspan="2">领红包</th>
			</tr>
			<tr>
				<td></td>
				<td width="20%">version：</td>
				<td width="78%"><input type="text" name="appVer" value=""></td>
			</tr>
			<tr>
				<td></td>
				<td>authAccount：</td>
				<td><input type="text" name="authAccount" value="mp_jjhb"></td>
			</tr>
			<tr>
				<td></td>
				<td>authToken：</td>
				<td><input type="text" name="authToken"/>（带红包编号）</td>
			</tr>
			<tr>
				<td></td>
				<td>用户编号：</td>
				<td><input type="text" name="receiverId" value="10792511"/></td>
			</tr>
			<tr>
				<td></td>
				<td>红包编号：</td>
				<td><input type="text" name="packetId" value=""/></td>
			</tr>
			<tr>
				<td></td>
				<td>语音：</td>
				<td><input type="file" name="file"></td>
			</tr>
			<tr height="30">
				<td colspan="3" align="center">
					<input type="submit" class="btn2" value="保 存"/>
				</td>
			</tr>
		</table>	 
	</form>
	<br/>
	</body>
</html>