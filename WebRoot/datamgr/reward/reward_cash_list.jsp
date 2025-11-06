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
				<th style="width:8%">提现编号</th>
				<th style="width:10%">用户编号</th>
				<th style="width:10%">用户昵称</th>
				<th style="width:10%">帐户类型</th>
				<th style="width:10%">提现金额</th>
				<th style="width:15%">申请时间</th>
				<th style="width:15%">处理时间</th>
				<th style="width:10%">处理人</th>
				<th style="width:6%">状态</th>
				<th style="width:6%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${cashid}')" >
				<td>${cashid}</td>
				<td>${userid}</td>
				<td>${user.nick}</td>
				<td><dict:text field="payAccountType" initvalue="${accounttype}"/></td>
				<td>${cashamount}</td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="createtime"/></td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="dealtime"/></td>
				<td>${dealname}</td>
				<td><dict:text field="cashStatus" initvalue="${status}"/></td>
				<td>
					<a href="javascript:editData('${cashid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="处理" title="处理"></a>
				</td>				
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
