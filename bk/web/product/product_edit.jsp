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
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>名称：</td>
					<td><s:textfield id="productname" name="entity.productname" maxlength="200"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>代码：</td>
					<td><s:textfield id="productcode" name="entity.productcode" maxlength="200"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td width="10%">类型：</td>
					<td><dict:radio name="entity.producttype" field="productType4Trans" initvalue="${entity==null?'01':entity.producttype}" cssClass="raddefault"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>点数：</td>
					<td><s:textfield id="point" name="entity.point"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>价格：</td>
					<td>
						<s:textfield id="price" name="entity.price"/>
						<span class="hint">（元）</span>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td width="10%">适用范围：</td>
					<td><dict:radio name="entity.productrange" field="productRange" initvalue="${entity==null?'01':entity.productrange}" cssClass="raddefault"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>排序：</td>
					<td>
						<s:textfield id="sortindex" name="entity.sortindex"/>
						<span class="hint">（值越小排序越靠前）</span>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>状态：</td>
					<td><dict:radio dataSource="xml" table="common" field="status" name="entity.status" initvalue="${entity==null?1:entity.status}" cssClass="raddefault"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>描述：</td>
					<td><s:textarea id="description" name="entity.description" cssClass="textarea2"/></td>
				</tr>
				<tr height="30">
					<td colspan="3" align="center">
						<s:hidden id="productid" name="entity.productid"/>
						<input type="button" class="btn2" value="保存" onClick="saveData();" />
						<input type="button" class="btn2" value="取消" onClick="backList();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
