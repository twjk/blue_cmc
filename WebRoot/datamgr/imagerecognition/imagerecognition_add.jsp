<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>测试</title>
		<jsp:include page="/common/head.jsp" />
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：图像识别管理  &gt;&gt; 添加图像识别</td>
    	</tr>
	  </table>
	</div>
	<br/>
	<form id="editForm" name="editForm" enctype="multipart/form-data" method="post" action="/cmc/services-ssl/image!recognize.do">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th colspan="2">添加图像识别</th>
			</tr>
			<tr>
				<td></td>
				<td width="20%">version：</td>
				<td width="78%"><input type="text" name="appVer" value="1.0.0"></td>
			</tr>
			<tr>
				<td></td>
				<td>authAccount：</td>
				<td><input type="text" name="authAccount" value="app_image_ios"></td>
			</tr>
			<tr>
				<td></td>
				<td>authToken：</td>
				<td><input type="text" name="authToken"/></td>
			</tr>
			<tr>
				<td></td>
				<td>用户编号：</td>
				<td><input type="text" name="uid" value="12832438"/></td>
			</tr>
			<tr>
				<td></td>
				<td>图像分类：</td>
				<td><dict:select field="ircat" name="catId" initvalue="1001"/></td>
			</tr>
			<tr>
				<td></td>
				<td>图像：</td>
				<td><input type="file" name="file" accept="image/bmp,image/jpeg,image/png"></td>
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