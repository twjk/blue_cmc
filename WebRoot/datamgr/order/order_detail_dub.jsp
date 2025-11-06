<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  	<head>
	<title></title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	</head>
	<body>
		<div style="height:10px"></div>
		
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeaderNoBottom">
		<tr>
			<td width="2%">&nbsp;</td>
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;配音订单详情</td>
		</tr>
		<tr>
			<td class="tdgray" width="10%" colspan="2">订单编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${order.orderid }</td>
			<td class="tdgray" width="10%">用户：</td>
			<td class="tdwhite" width="20%">
				&nbsp;${order.user.nick}（${order.userid }）
				<s:if test="order.usertype==2"><img src="<lt:contextPath/>/images/ent.png" alt="企业用户" title="企业用户" width="16" height="16" style="vertical-align: middle"></s:if>
			</td>
			<td class="tdgray" width="10%">企业员工：</td>
			<td class="tdwhite" width="30%">
				<s:if test="order.usertype==2">
					&nbsp;${order.employeename}（${order.employeeid }）
				</s:if>
			</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">创建时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="order.createtime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">提交时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="order.waittime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">完成时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="order.finishtime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">总额（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${order.amount}"/></td>
			<td class="tdgray">优惠/抵扣（元）：</td>
			<td class="tdwhite">
				&nbsp;
				<fmt:formatNumber type="currency" pattern="0.00" value="${order.couponamount}"/>
				/
				<fmt:formatNumber type="currency" pattern="0.00" value="${order.walletamount}"/>
			</td>
			<td class="tdgray">应付（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${order.payableamount}"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">支付途径：</td>
			<td class="tdwhite">&nbsp;<dict:text field="tradeWay" initvalue="${order.tradeway}"/></td>
			<td class="tdgray">支付时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="order.paytime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">已付（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${order.payamount}"/></td>
		</tr>
		<s:if test="@com.qcmz.cmc.constant.DictConstant@DICT_ORDERTYPE_DUB==order.ordertype">
		<tr>
			<td class="tdgray" colspan="2">作品名称：</td>
			<td class="tdwhite" colspan="3">&nbsp;${order.cmcRDub.title }</td>
			<td class="tdgray">处理状态：</td>
			<td class="tdwhite">&nbsp;<dict:text field="orderDealStatus" initvalue="${order.dealstatus }"/></td>
		</tr>
		</s:if>
		<s:elseif test="@com.qcmz.cmc.constant.DictConstant@DICT_ORDERTYPE_HUMANDUB==order.ordertype">
		<tr>
			<td class="tdgray" colspan="2">配音员：</td>
			<td class="tdwhite">&nbsp;${order.cmcRDub.cmcDubber.title }（${order.cmcRDub.cmcDubber.fullname }）</td>
			<td class="tdgray">用户手机号：</td>
			<td class="tdwhite">&nbsp;${order.mobile }</td>
			<td class="tdgray">处理状态：</td>
			<td class="tdwhite">&nbsp;<dict:text field="orderDealStatus" initvalue="${order.dealstatus }"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">用户需求：</td>
			<td class="tdwhite" colspan="5">&nbsp;${order.requirement }</td>
		</tr>
		</s:elseif>
		<tr>
			<td class="tdgray" colspan="2">配音文本：</td>
			<td class="tdwhite" colspan="5">&nbsp;<textarea id="txt" style="width:95%;height:100px;" readonly="readonly">${order.cmcRDub.txt }</textarea></td>
		</tr>
		</table>
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeader" style="margin-top: 10px;">
		<tr>
			<td width="2%">&nbsp;</td>
			<td colspan="2" align="left" class="text13blackb">&nbsp;&nbsp;留言</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td width="28%" style="text-align: center;padding:10px;">
				<textarea id="msg" style="width:100%;height:100px;"></textarea>
				<p><input type="button" class="btn8" value="给客户留言" onClick="saveMsg();"/></p>
			</td>
			<td width="70%" valign="top" style="padding-left: 30px;">
				<s:if test="!order.cmcRMsgs.isEmpty()">
					<ul style="list-style:none;margin: 0;padding: 0">
					<s:iterator value="order.cmcRMsgs" status="sts" id="l">
						<li>
						<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
						-- ${msgname }
						-- ${msg }
						</li>
					</s:iterator>
					</ul>
				</s:if>
			</td>
		</tr>
		</table>
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeader" style="margin-top: 10px;">
		<tr>
			<td width="2%">&nbsp;</td>
			<td align="left" class="text13blackb">&nbsp;&nbsp;操作历史</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<ul style="list-style:none;margin: 0;padding: 0">
				<s:iterator value="order.cmcRLogs" status="sts" id="l">
					<li>
					<s:date name="opertime" format="yyyy-MM-dd HH:mm:ss" />
					 -- ${opername }
					 -- <dict:text field="orderDealStatus" initvalue="${dealstatus}"/>
					 <s:if test="log!=null">
					 -- ${log }
					 </s:if>
					 </li>
				</s:iterator>
				</ul>
			</td>
		</tr>
		</table>
		<input type="hidden" id="orderId" value="${order.orderid }">
		<input type="hidden" id="orderType" value="${order.ordertype}" />
		<p align="center"><input type="button" class="btn2" value="返回列表" onClick="backList();" /></p>
	</body>
</html>
