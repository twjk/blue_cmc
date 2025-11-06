<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.qcmz.framework.constant.DictConstants"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
    <head>
		<title>列表</title>
	</head>
	<body>
	<div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:5%">编号</th>
				<th style="width:10%">主题名称</th>
				<th style="width:10%">分类</th>
				<th style="width:10%">语言</th>
				<th style="width:30%">原文</th>
				<th style="width:30%">译文</th>
				<th style="width:5%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${sentid}')" >
				<td>${sentid}</td>
				<td>${cmcPkgTheme.titlecn}</td>
				<td>
					<c:if test="${not empty cat}">
						<c:forEach var="i" begin="0" step="4" end="${fn:length(cat)-1}">
							> <dict:text field="<%=DictConstants.DICTNAME_CAT %>" initvalue="${fn:substring(cat, i, i+4)}"/>
						</c:forEach>
					</c:if>
				</td>
				<td>
					<dict:text field="speechLang" initvalue="${fromlang}"/>
					—>
					<dict:text field="speechLang" initvalue="${tolang}"/>
				</td>
				<td>${src}</td>
				<td>${dst}</td>
				<td>
					<a href="javascript:editData('${sentid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="编辑" title="编辑"></a>
					<a href="javascript:deleteData('${sentid }');"><img src="<lt:contextPath/>/images/delet.gif" alt="删除" title="删除"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
