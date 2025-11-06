<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>语音合成</title>
		<jsp:include page="/common/head.jsp" />
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：测试  &gt;&gt; 语音合成</td>
    	</tr>
	  </table>
	</div>
	<br/>
	<form id="editForm" name="editForm" method="post" action="/cmc/services-ssl/speech!tts.do">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th colspan="2">语音合成</th>
			</tr>
			<tr>
				<td></td>
				<td width="20%">authAccount：</td>
				<td width="78%"><input type="text" name="authAccount" value="trans"></td>
			</tr>
			<tr>
				<td></td>
				<td>authToken：</td>
				<td><input type="text" name="authToken"/></td>
			</tr>
			<tr>
				<td></td>
				<td>语言：</td>
				<td>
					<select name="langCode">
						<option value="zh-cn">普通话</option>
						<option value="en">英语</option>
						<option value="ms">马来语</option>
						<option value="vi">越南语</option>
					</select>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>代理：</td>
				<td>
					<select name="proxyId">
						<option value="10">Google</option>
					</select>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>发音人：</td>
				<td>
					<select name="gender">
						<option value="F">女</option>
						<option value="M">男</option>
					</select>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>语速：</td>
				<td>
					<input type="text" name="speed" value="100">
					<span class="hint">（正常语速为100，200为正常语速的2倍）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>文本：</td>
				<td><textarea name="text" rows="60" cols="10"></textarea></td>
			</tr>
			<tr height="30">
				<td colspan="3" align="center">
					<input type="submit" class="btn2" value="合成"/>
				</td>
			</tr>
		</table>	 
	</form>
	<br/>
	</body>
</html>