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
				<th style="width:6%">用户</th>
				<th style="width:10%">名称</th>
				<th style="width:12%">总额/抵扣/应付/已付</th>
				<th style="width:8%">兑换码</th>
				<th style="width:10%">兑换状态/时间</th>
				<th style="width:6%">兑换人</th>
				<th style="width:12%">创建/提交时间</th>
				<th style="width:10%">完成时间</th>
				<th style="width:5%">处理状态</th>
				<th style="width:6%">下单平台</th>
				<th style="width:5%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="detailData('${orderid}', ${cmcROrder.ordertype })">
				<td>${orderid}</td>
				<td>${userid }<br/>${user.nick }</td>
				<td>${acttitle }</td>				
				<td>
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.amount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.walletamount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.payableamount}"/>
				 	/
				 	<fmt:formatNumber type="currency" pattern="0.00" value="${cmcROrder.payamount}"/>
				 	
				 	<br/>
				 	<dict:text field="tradeWay" initvalue="${cmcROrder.tradeway}"/>
				</td>
				<td>${exchangecode}</td>
				<td>
					<dict:text field="exchangestatus" initvalue="${exchangestatus}" dataSource="xml" table="common"/>
					<br/>
					<s:date name="exchangetime" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${exchangeuserid }<br/>${exchangeUser.nick }</td>
				<td>
					<s:date name="cmcROrder.createtime" format="yyyy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="cmcROrder.waittime" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td><s:date name="cmcROrder.finishtime" format="MM-dd HH:mm:ss"/></td>
				<td><dict:text field="orderDealStatus" initvalue="${cmcROrder.dealstatus}"/></td>
				<td>
					<dict:text field="platform"  initvalue="${cmcROrder.platform}"/>
					<br/>
					${cmcROrder.version}
				</td>
				<td style="text-align: left">
					<a href="javascript:detailData('${orderid}', ${cmcROrder.ordertype });"><img src="<lt:contextPath/>/images/detail.gif" alt="详情" title="详情"></a>
					<s:if test="@com.qcmz.cmc.constant.DictConstant@DICT_ORDER_DEALSTATUS_WAITPAY==cmcROrder.dealstatus">
						&nbsp;&nbsp;
						<a href="javascript:synPay('${orderid}');"><img src="<lt:contextPath/>/images/refresh.png" alt="同步支付结果" title="同步支付结果" width="16" height="16"></a>
					</s:if>
					<s:elseif test="@com.qcmz.cmc.util.OrderUtil@canForceRefund(cmcROrder.walletamount, cmcROrder.payamount, cmcROrder.tradeway)">
						&nbsp;&nbsp;
						<a href="javascript:refund('${orderid}');"><img src="<lt:contextPath/>/images/refund.png" alt="退款" title="退款" width="16" height="16"></a>
					</s:elseif>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>