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
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" >
			<tr>
				<th style="width:10%">用户编号</th>
				<th style="width:10%">用户昵称</th>
				<th style="width:10%">可用余额(元)</th>
				<th style="width:10%">申请提现金额(元)</th>
				<th style="width:10%">已提现金额(元)</th>
				<th style="width:10%">领取总额(元)</th>
				<th style="width:10%">领取红包个数</th>
				<th style="width:10%">发放总额(元)</th>
				<th style="width:10%">发放红包个数</th>
				<th style="width:10%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${userid}')" >
				<td>${userid}</td>
				<td>${bean.user.nick}</td>
				<td>${balance}</td>
				<td>${applyamount}</td>
				<td>${cashamount}</td>
				<td>${receiveamount}</td>
				<td>${receivenum}</td>
				<td>${sendamount}</td>
				<td>${sendnum}</td>
				<td><a href="javascript:editData('${userid}');"><img src="<lt:contextPath/>/images/detail.gif" alt="详情" title="详情"></a></td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
