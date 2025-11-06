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
				<s:if test="entity==null">
					<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td width="10%">原文：</td>
					<td>
						<dict:select id="fromlang" name="entity.fromlang" field="speechLang" initvalue="${lastFrom }" cssClass="select2"/>
						&nbsp;&nbsp;
						<s:textfield id="src" name="entity.src" cssClass="input2"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>译文：</td>
					<td>
						<dict:select id="tolang" name="entity.tolang" field="speechLang" initvalue="${lastTo }" cssClass="select2"/>
						&nbsp;&nbsp;
						<s:textfield id="dst" name="entity.dst" cssClass="input2"/>
					</td>
				</tr>
				</s:if>
				<s:else>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td width="10%">原文：</td>
					<td>
						<input type="text" value='<dict:text field="speechLang" initvalue="${entity.fromlang}"/>' style="width:70px;border: 0px;" readonly="readonly">
						<s:hidden name="entity.fromlang"/>
						&nbsp;&nbsp;
						<s:textfield id="src" name="entity.src" cssClass="input2"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>译文：</td>
					<td>
						<input type="text" value='<dict:text field="speechLang" initvalue="${entity.tolang}"/>' style="width:70px;border: 0px;" readonly="readonly">
						<s:hidden name="entity.tolang"/>
						&nbsp;&nbsp;
						<s:textfield id="dst" name="entity.dst" cssClass="input2"/>
					</td>
				</tr>
				</s:else>				
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>类型：</td>
					<td><dict:radio field="transLibType" name="entity.libtype" initvalue="${entity==null?'01':entity.libtype}" cssClass="raddefault"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>翻译：</td>
					<td>
						<dict:radio dataSource="xml" table="common" field="twoway" name="entity.twoway" initvalue="${entity==null?0:entity.twoway}" cssClass="raddefault"/>
						<span class="hint">（双向互译会自动多添加一条反向翻译记录）</span>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td valign="top">合成方式：</td>
					<td>
						<dict:radio field="ttsMethod" name="entity.ttssrc" initvalue="${entity==null?0:entity.ttssrc}" event="onclick='changeTtsMethod()'" cssClass="raddefault"/>
						&nbsp;
						<s:textfield id="ttstext" name="entity.ttstext" cssClass="input4"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>核对状态：</td>
					<td><dict:radio dataSource="xml" table="common" field="checkstatus" name="entity.checkstatus" initvalue="${entity==null?1:entity.checkstatus}" cssClass="raddefault"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>状态：</td>
					<td><dict:radio dataSource="xml" table="common" field="status" name="entity.status" initvalue="${entity==null?1:entity.status}" cssClass="raddefault"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>分类：</td>
					<td id="selCat"><s:hidden id="currCat" name="entity.cat" /></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>来源：</td>
					<td>
						<dict:text field="transLibSource" initvalue="${entity==null?'02':entity.sourceid}" />
						<input type="hidden" name="entity.sourceid" value="${entity==null?'02':entity.sourceid}"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>命中次数：</td>
					<td>
						${entity==null?0:entity.hitcount}
						<s:hidden name="entity.hitcount" value="%{entity==null?0:entity.hitcount}"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td valign="top">备注：</td>
					<td><s:textarea name="entity.remark" cssClass="textarea3"/></td>
				</tr>
				<tr height="30">
					<td colspan="3" align="center">
						<s:hidden id="libid" name="entity.libid"/>
						<s:hidden name="libClass"/>
						<input type="button" class="btn2" value="保存" onClick="saveData();" />
						<input type="button" class="btn2" value="取消" onClick="backList();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
