<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
    <head>
		<title></title>
		<script type="text/javascript" src="<lt:contextPath/>/javascript/listgry.js"></script>
	</head>
	<body>
	<div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:25%">用户编号</th>
				<th style="width:25%">用户名</th>
				<th style="width:25%">点数余额</th>
				<th style="width:25%">广告状态</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr>
				<td>${userid}</td>
				<td>${bean.umcUUser.username }</td>
				<td>${point}</td>
				<td><dict:text field="switch" table="common" initvalue="${adstatus}" dataSource="xml"/></td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
