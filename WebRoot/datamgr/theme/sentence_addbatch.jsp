<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>填写信息</title>
	</head>
	<body>
		<form name="editForm" action="#" id="editForm">
			<div style="height:10px"></div>
			<table width="100%" cellpadding="0" cellspacing="0" class="tableHeader" border="0" align="center">
				<tr>
				    <td width="2%">&nbsp;</td>
					<td colspan="2" align="left" class="text13blackb">填写信息</td>
				</tr>	
				<tr>
				 	<td align="right"></td>
					<td width="10%">主题名称：</td>
					<td>
						${themeEntity.titlecn }
						<input type="hidden" id="themeid" name="themeid" value="${themeid}"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>分类：</td>
					<td id="selCat"><s:hidden id="currCat" name="cat" /></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>搜索：</td>
					<td>
						<dict:select id="lib_type" field="transLibType" initvalue="02" inithead="0" cssClass="select2" style="display:none"/>
						<dict:select id="lib_from" field="speechLang" initvalue="zh-cn" cssClass="select2"/>
						—>
						<dict:select id="lib_to" field="speechLang" initvalue="${themeEntity.themecode }" cssClass="select2"/>
						&nbsp;&nbsp;<input type="text" id="lib_key"/>
						&nbsp;&nbsp;<input type="button" class="btn5" value="查 询" onclick="queryLib();">
						<span class="hint">（支持查询原文）</span>					
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>语句：</td>
					<td height="60">
						<ul id="libList" onmouseup="checkBatch();"></ul>
					</td>
				</tr>
				<tr height="30">
					<td colspan="3" align="center">
						<input type="text" id="libids" style="width:0;height:0;margin: 0;padding: 0;border: 0;">
						<input type="button" class="btn2" value="保存" onClick="saveDataBatch();" />
						<input type="button" class="btn2" value="取消" onClick="backList();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
