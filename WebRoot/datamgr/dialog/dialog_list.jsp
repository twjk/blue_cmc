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
				<th style="width:10%">编号</th>
				<th style="width:50%">用户</th>
				<th style="width:15%">最新消息时间</th>
				<th style="width:15%">处理状态</th>
				<th style="width:10%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="detailData('${dialogid}')" >
				<td>${dialogid}</td>
				<td>${user.nick }（${userid }）</td>
				<td><s:date name="msgtime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><dict:text field="dialogDealStatus" initvalue="${dealstatus }"/></td>
				<td>
					<a href="javascript:detailData('${dialogid}');"><img src="<lt:contextPath/>/images/detail.gif" alt="详情" title="详情"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
