<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<!-- 就业精选中增加公司 -->
<html>
	<head>
		<title>填写信息</title>
		<jsp:include page="/common/head.jsp"></jsp:include>
		<script type="text/javascript" src="<lt:contextPath/>/ueditor/ueditor.config.js"></script>
		<script type="text/javascript" src="<lt:contextPath/>/ueditor/ueditor.all.min.js"></script>
		<script type="text/javascript" src="<lt:contextPath/>/ueditor/lang/zh-cn/zh-cn.js"></script>
		<script type="text/javascript" src="<lt:contextPath/>/ueditor/ueditor.plus.js"></script>
		<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/localCompany.js"></script>
	</head>
	<body style="padding: 5px;">
		<form name="editForm" action="#" id="editForm">
			<div style="height:5px"></div>
			<table width="100%" cellpadding="0" cellspacing="0" class="tableHeader" border="0" align="center">
				<tr>
				    <td width="2%">&nbsp;</td>
					<td colspan="2" align="left" class="text13blackb">填写公司信息</td>
				</tr>	
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td width="10%">名称：</td>
					<td><s:textfield id="companyname" name="entity.companyname" maxlength="100" cssClass="input2"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>图标：</td>
					<td valign="middle">
						<s:textfield id="logo" name="entity.logo" maxlength="100" cssClass="input2"/>
						<img id="logoImg" src=" "
							onclick="insertSinglePic(this.id);"  
							alt="点击换图" title="点击换图" 
							style="vertical-align:middle;cursor: pointer;max-height: 40px">
						<script id="singleEditor" type="text/plain" style="display:none"></script>
						<script type="text/javascript">
							var singleEditor = new InsertImage('singleEditor', 'localcompany', callback_insertSinglePic);
							function insertSinglePic(id){
								singleEditor.insertImage(id);
							}	
							function callback_insertSinglePic(t, result, actionData) {
								$("#"+actionData).attr("src", result[0].src);
								$("#"+actionData).prev("input").val(result[0].src);
							}
						</script>
					</td>
				</tr>
				<tr>
				 	<td></td>
					<td>介绍：</td>
					<td><s:textarea id="introduce" name="entity.introduce" cssClass="textarea2" /></td>
				</tr>
				<tr height="30">
					<td colspan="3" align="center">
						<s:hidden id="createway" name="entity.createway" value="1"/>
						<input type="button" class="btn2" value="保存" onClick="saveData4Pop();" />
						<input type="button" class="btn2" value="取消" onClick="window.close();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
