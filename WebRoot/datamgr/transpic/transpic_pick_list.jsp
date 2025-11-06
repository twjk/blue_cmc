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
				<th style="width:10%">订单编号</th>
				<th style="width:10%">用户</th>
				<th style="width:10%">图片</th>
				<th style="width:10%">语言</th>
				<th style="width:15%">总额/优惠/抵扣/应付/已付（元）</th>
				<th style="width:10%">创建时间</th>
				<th style="width:10%">开始处理时间</th>
				<th style="width:10%">完成时间</th>
				<th style="width:5%">处理人</th>
				<th style="width:5%">处理状态</th>
				<th style="width:5%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${picid}')" >
				<td>${picid}</td>
				<td>${userid }<br/>${user.nick }</td>
				<td style="padding: 1px"><img src="${thumburl}" style="max-height: 50px; max-width: 100px"></td>
				<td>
					<dict:text field="textLang" initvalue="${fromlang}"/>
					&nbsp;->&nbsp;
					<dict:text field="textLang" initvalue="${tolang}"/>
				</td>
				<td>
					<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.amount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.couponamount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.walletamount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.payableamount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.payamount}"/>
				 	<br/>
				 	<dict:text field="tradeWay" initvalue="${cmcROrder.tradeway}"/>
				</td>
				<td><s:date name="%{cmcROrder.createtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="%{cmcROrder.opertime}" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="%{cmcROrder.finishtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${cmcROrder.opername}</td>
				<td><dict:text field="orderDealStatus" initvalue="${cmcROrder.dealstatus}"/></td>
				<td>
					<a href="javascript:editData('${picid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="翻译" title="翻译"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
