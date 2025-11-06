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
				<th style="width:10%">订单</th>
				<th style="width:10%">用户</th>
				<th style="width:10%">配音员</th>
				<th style="width:20%">文本</th>
				<th style="width:10%">提交/完成时间</th>
				<th style="width:10%">处理进度/状态</th>
				<th style="width:10%">处理人</th>
				<th style="width:10%">订单金额</th>
				<th style="width:10%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${orderid}', ${cmcROrder.ordertype})" >
				<td>
					${orderid}
					<br/>
					<dict:text field="orderType" initvalue="${cmcROrder.ordertype }"/>
				</td>
				<td>${userid }<br/>${user.nick }</td>
				<td>${cmcDubber.title }<br/>${cmcDubber.fullname }</td>				
				<td style="padding-left: 3px;padding-right: 3px">
					<s:property value="@com.qcmz.framework.util.StringUtil@abbreviate(txt, 80)" escapeHtml="true"/>
				</td>
				<td>
					<s:date name="%{cmcROrder.waittime}" format="yyyy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{cmcROrder.finishtime}" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<dict:text field="dealProgress" initvalue="${cmcROrder.dealprogress}"/>
					<br/>
					<dict:text field="orderDealStatus" initvalue="${cmcROrder.dealstatus}"/>				
				</td>
				<td>${cmcROrder.opername}</td>
				<td>
					<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.amount}"/>
				</td>
				<td>
					<a href="javascript:editData('${orderid}', ${cmcROrder.ordertype} );"><img src="<lt:contextPath/>/images/edit.gif" alt="配音" title="配音"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
		<div id="evalTagDiv" style="position: absolute; width:200px;height:50px; text-align:center;font-size:12px; padding:2px; background-color: white; border: 1px solid #cccccc;display: none;"></div>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
