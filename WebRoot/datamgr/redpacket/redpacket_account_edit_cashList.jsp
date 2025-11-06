<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="qcmz" prefix="lt"%>
<%@ taglib uri="dictionary" prefix="dict"%>
<html>
    <head>
		<title></title>
	</head>
	<body>
	<div>
		<table class="rebateHeader" cellpadding="0" cellspacing="0"  align="center" style="width:100%">
			<tr>
				<th style="width:20%">提现金额(元)</th>
				<th style="width:20%">申请时间</th>
				<th style="width:20%">处理时间</th>
				<th style="width:20%">处理人姓名</th>
				<th style="width:20%">状态</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr>
				<td>${cashamount}</td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="createtime"/></td>	
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="dealtime"/></td>
				<td>${dealname}</td>
				<td><dict:text field="redPacketCashStatus" initvalue="${status }"/></td>			
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="cashQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
