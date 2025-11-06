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
				<th width="1%"></th>
				<th style="width:12%">订单</th>
				<th style="width:8%">用户</th>
				<th style="width:8%">语言</th>
				<th style="width:15%">原文</th>
				<th style="width:15%">总额/优惠/抵扣/应付/已付</th>
				<th style="width:12%">创建/提交时间</th>
				<th style="width:8%">开始/完成时间</th>
				<th style="width:6%">处理状态/人</th>
				<th style="width:10%">下单平台</th>
				<th style="width:5%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="detailData('${orderid}', ${ordertype})" >
				<td>&nbsp;</td>
				<td style="text-align: left">
					${orderid}
					<br/>
					<dict:text field="orderType" initvalue="${ordertype }"/>
					<s:if test="appointflag==1"><img src="<lt:contextPath/>/images/appoint.png" alt="预约" title="预约" width="16" height="16" style="vertical-align: middle"></s:if>
					<s:if test="usertype==2"><img src="<lt:contextPath/>/images/ent.png" alt="企业用户" title="企业用户" width="16" height="16" style="vertical-align: middle"></s:if>
				</td>
				<td>${userid }<br/>${user.nick }</td>
				<td>
					<dict:text field="textLang" initvalue="${fromlang}"/>
					&nbsp;->&nbsp;
					<dict:text field="textLang" initvalue="${tolang}"/>
				</td>
				<td style="padding-right: 3px;padding-left: 3px;">
					<s:if test="cmcPtPic!=null">
						<img src="${cmcPtPic.thumburl}" style="max-height: 70px; max-width: 150px">
					</s:if>
					<s:elseif test="cmcTtTrans!=null">
						<s:property value="@com.qcmz.framework.util.StringUtil@abbreviate(cmcTtTrans.src, 50)" escapeHtml="true"/>
					</s:elseif>
				</td>
				<td>
				 	<s:if test="cmcUCombo!=null">
				 		${cmcUCombo.combotitle }
				 	</s:if>
				 	<s:else>
						<fmt:formatNumber type="currency" pattern="0.00" value="${amount}"/>
					 	/
					 	<fmt:formatNumber type="currency" pattern="0.00" value="${couponamount}"/>
					 	/
					 	<fmt:formatNumber type="currency" pattern="0.00" value="${walletamount}"/>
					 	/
					 	<fmt:formatNumber type="currency" pattern="0.00" value="${payableamount}"/>
					 	/
					 	<fmt:formatNumber type="currency" pattern="0.00" value="${payamount}"/>
					 	<br/>
					 	<dict:text field="tradeWay" initvalue="${tradeway}"/>
				 	</s:else>
				 </td>
				<td>
					<s:date name="%{createtime}" format="yy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{waittime}" format="yy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<s:date name="%{opertime}" format="MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{finishtime}" format="MM-dd HH:mm:ss"/>
				</td>
				<td>
					<dict:text field="orderDealStatus" initvalue="${dealstatus}"/>
					<br/>
					${opername}
				</td>
				<td>
					<dict:text field="serviceType" initvalue="${servicetype}"/>
					<br/>
					${platform}&nbsp;${version}
				</td>
				<td style="text-align: left">
					<a href="javascript:detailData('${orderid}', ${ordertype});"><img src="<lt:contextPath/>/images/detail.gif" alt="详情" title="详情"></a>
					<s:if test="@com.qcmz.cmc.constant.DictConstant@DICT_ORDER_DEALSTATUS_WAITPAY==dealstatus">
						&nbsp;&nbsp;
						<a href="javascript:synPay('${orderid}');"><img src="<lt:contextPath/>/images/refresh.png" alt="同步支付结果" title="同步支付结果" width="16" height="16"></a>
					</s:if>
					<s:elseif test="@com.qcmz.cmc.constant.DictConstant@DICT_ORDER_DEALSTATUS_CANCEL==dealstatus && cmcROrder.payamount>0">
						&nbsp;&nbsp;
						<a href="javascript:synRefund('${orderid}');"><img src="<lt:contextPath/>/images/refresh.png" alt="同步退款结果" title="同步退款结果" width="16" height="16"></a>
					</s:elseif>
					<s:elseif test="@com.qcmz.cmc.util.OrderUtil@canForceRefund(walletamount, payamount, tradeway)">
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
