<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
    <head>
		<title></title>
	</head>
	<body>
	<div>
		<table class="resultListFence" style="width:100%" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:20%">日期</th>
				<th style="width:20%">类型</th>
				<th style="width:20%">金额</th>
				<th style="width:20%">描述</th>
				<th style="width:20%">单号</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr>
				<td><s:date name="%{createtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><dict:text field="rewardBillType" initvalue="${billtype}"/></td>
				<td>${amount }</td>
				<td>${billdesc }</td>
				<td>${orderid }</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="queryBill" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>