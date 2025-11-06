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
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:14%">订单编号</th>
				<th style="width:10%">订单类型</th>
				<th style="width:10%">语言</th>
				<th style="width:10%">处理状态</th>
				<th style="width:10%">提交时间</th>
				<th style="width:10%">翻译时间</th>
				<th style="width:10%">校对时间</th>
				<th style="width:10%">校对状态</th>
				<th style="width:8%">校对人</th>
				<th style="width:8%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${orderid}', ${ordertype})">
				<td>${orderid}</td>
				<td><dict:text field="orderType" initvalue="${ordertype }"/></td>
				<td>
					<dict:text field="textLang" initvalue="${fromlang }"/>
					->
					<dict:text field="textLang" initvalue="${tolang }"/>					
				</td>
				<td><dict:text field="orderDealStatus" initvalue="${dealstatus}"/></td>
				<td><s:date name="%{waittime}" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<s:date name="%{opertime}" format="yyyy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{finishtime}" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<s:date name="%{checkstarttime}" format="yyyy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{checkfinishtime}" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td><dict:text field="checkStatus" initvalue="${checkstatus}"/></td>
				<td>${checkname}</td>
				<td>
					<a href="javascript:editData('${orderid}', ${ordertype});"><img src="<lt:contextPath/>/images/edit.gif" alt="校对" title="校对"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
