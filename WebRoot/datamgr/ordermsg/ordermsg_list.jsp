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
				<th style="width:12%">订单</th>
				<th style="width:10%">用户</th>
				<th style="width:6%">订单状态</th>
				<th style="width:6%">处理人</th>
				<th style="width:12%">留言人/时间</th>
				<th style="width:48%">留言内容</th>
				<th style="width:6%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="detailData('${orderid}', ${cmcROrder.ordertype})">
				<td>
					${orderid}
					<br/>
					<dict:text field="orderType" initvalue="${cmcROrder.ordertype }"/>					
				</td>
				<td>${cmcROrder.userid } <br/> ${cmcROrder.user.nick }</td>
				<td><dict:text field="orderDealStatus" initvalue="${cmcROrder.dealstatus}"/></td>
				<td>${cmcROrder.opername }</td>
				<td>
					<s:if test=""></s:if>
					${msgname }
					<br/>
					<s:date name="%{createtime}" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${msg}</td>
				<td>
					<a href="javascript:detailData('${orderid}', ${cmcROrder.ordertype});"><img src="<lt:contextPath/>/images/detail.gif" alt="详情" title="详情"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />	
	</body>
</html>
