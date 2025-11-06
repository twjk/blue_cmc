<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>文件上传</title>
		<jsp:include page="/common/head.jsp" />
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：系统管理  &gt;&gt; 文件上传测试</td>
    	</tr>
	  </table>
	</div>
	<br/>
	<form id="editForm" name="editForm" enctype="multipart/form-data" method="post" action="/cmc/services-ssl/userCrowdTask!uploadTask.do">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th colspan="2">用户众包任务上传文件(userCrowdTask!uploadTask)</th>
			</tr>
			<tr>
				<td></td>
				<td width="20%">version：</td>
				<td width="78%"><input type="text" name="appVer" value="1.0.0"></td>
			</tr>
			<tr>
				<td></td>
				<td>authAccount：</td>
				<td><input type="text" name="authAccount" value="app_zc_ios"></td>
			</tr>
			<tr>
				<td></td>
				<td>authToken：</td>
				<td><input type="text" name="authToken"/></td>
			</tr>
			<tr>
				<td></td>
				<td>用户编号：</td>
				<td><input type="text" name="uid" value="11000001"/></td>
			</tr>
			<tr>
				<td></td>
				<td>任务编号：</td>
				<td><input type="text" name="taskId" value=""/></td>
			</tr>
			<tr>
				<td></td>
				<td>用户任务编号：</td>
				<td><input type="text" name="utId" value=""/></td>
			</tr>
			<tr>
				<td></td>
				<td>题目编号：</td>
				<td><input type="text" name="subjectId" value=""/></td>
			</tr>
			<tr>
				<td></td>
				<td>文件：</td>
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
	<form id="editForm_UploadBusinessLicense" name="editForm_UploadBusinessLicense"  class="editForm" enctype="multipart/form-data" method="post" 
		action="/cmc/services-ssl/company!uploadBusinessLicense.do">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th colspan="2">上传公司营业执照(hr!uploadBusinessLicense)</th>
			</tr>
			<tr>
				<td></td>
				<td width="20%">version：</td>
				<td width="78%"><input type="text" name="appVer" value="1.0.0"></td>
			</tr>
			<tr>
				<td></td>
				<td>authAccount：</td>
				<td><input type="text" name="authAccount" value="app_bc_ios"></td>
			</tr>
			<tr>
				<td></td>
				<td>authToken：</td>
				<td><input type="text" name="authToken"/></td>
			</tr>
			<tr>
				<td></td>
				<td>用户编号：</td>
				<td><input type="text" name="uid" value="11034038"/></td>
			</tr>
			<tr>
				<td></td>
				<td>文件：</td>
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