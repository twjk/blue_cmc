<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
    <head>
		<title></title>
	</head>
	<body>
	<div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:20%">编号</th>
				<th style="width:70%">代码</th>
				<th style="width:10%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${tmid}')" >
				<td>${tmid}</td>
				<td>${tmcode}</td>
				<td>
					<a href="javascript:editData('${tmid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="编辑" title="编辑"></a>
					<a href="javascript:deleteData('${tmid }');"><img src="<lt:contextPath/>/images/delet.gif" alt="删除" title="删除"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
