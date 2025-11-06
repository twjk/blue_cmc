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
						<s:if test="entity==null">
							${themeEntity.titlecn }
							<input type="hidden" id="themeid" name="entity.themeid" value="${themeid}"/>
						</s:if>
						<s:else>
							${entity.cmcPkgTheme.titlecn}
							<s:hidden id="themeid" name="entity.themeid"/>
						</s:else>
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>分类：</td>
					<td id="selCat"><s:hidden id="currCat" name="entity.cat" /></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>源语言：</td>
					<td><dict:select id="fromlang" name="entity.fromlang" field="speechLang" inithead="0" initvalue="${entity==null?'zh-cn':entity.fromlang}"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>原文：</td>
					<td><s:textfield id="src" name="entity.src" cssClass="input2"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>目标语言：</td>
					<td><dict:select id="tolang" name="entity.tolang" field="speechLang" inithead="0" initvalue="${entity.tolang}"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>译文：</td>
					<td><s:textfield id="dst" name="entity.dst" cssClass="input2"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>语音标识：</td>
					<td><s:textfield id="voice" name="entity.voice"/></td>
				</tr>
				<tr height="30">
					<td colspan="3" align="center">
						<s:hidden name="entity.sentid"/>
						<input type="button" class="btn2" value="保存" onClick="saveData();" />
						<input type="button" class="btn2" value="取消" onClick="backList();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
