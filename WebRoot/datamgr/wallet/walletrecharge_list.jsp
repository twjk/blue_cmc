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
				<th style="width:10%">充值单号</th>
				<th style="width:10%">业务类型</th>
				<th style="width:10%">用户编号</th>
				<th style="width:10%">用户昵称</th>
				<th style="width:8%">充值金额</th>
				<th style="width:10%">下单时间</th>
				<th style="width:10%">下单平台</th>
				<th style="width:10%">支付时间</th>
				<th style="width:8%">支付途径</th>
				<th style="width:7%">状态</th>
				<th style="width:7%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr>
				<td>${orderid}</td>
				<td><dict:text field="serviceType" initvalue="${cmcWalletAccount.servicetype}"/></td>
				<td>${userid}</td>
				<td>${user.nick }</td>				
				<td>${amount }</td>
				<td><s:date name="%{createtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${platform}&nbsp;${version}</td>
				<td><s:date name="%{paytime}" format="yyyy-MM-dd HH:mm:ss"/></td>				
				<td><dict:text field="tradeWay" initvalue="${tradeway}"/></td>
				<td><dict:text field="walletRechargeStatus" initvalue="${status}"/></td>
				<td>
					<s:if test="@com.qcmz.cmc.constant.DictConstant@DICT_WALLET_RECHARGESTATUS_WAITPAY==status">
						&nbsp;&nbsp;
						<a href="javascript:synPay('${orderid}');"><img src="<lt:contextPath/>/images/refresh.png" alt="同步支付结果" title="同步支付结果" width="16" height="16"></a>
					</s:if>
					<s:elseif test="@com.qcmz.cmc.util.WalletUtil@canForceRefund(payamount, tradeway)">			
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