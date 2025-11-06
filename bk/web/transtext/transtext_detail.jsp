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
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;订单详情</td>
		</tr>
		<tr>
			<td class="tdgray" width="10%" colspan="2">订单编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.orderid }</td>
			<td class="tdgray" width="10%">用户编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.userid }</td>
			<td class="tdgray" width="10%">翻译模式：</td>
			<td class="tdwhite" width="30%">&nbsp;<dict:text field="transModel" initvalue="${entity.cmcTtTrans.transmodel }"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">创建时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="entity.createtime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">提交时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="entity.waittime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">完成时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="entity.finishtime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">总额（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${entity.amount}"/></td>
			<td class="tdgray">优惠/抵扣（元）：</td>
			<td class="tdwhite">
				&nbsp;
				<fmt:formatNumber type="currency" pattern="0.00" value="${entity.couponamount}"/>
				/
				<fmt:formatNumber type="currency" pattern="0.00" value="${entity.walletamount}"/>
			</td>
			<td class="tdgray">应付（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${entity.payableamount}"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">支付途径：</td>
			<td class="tdwhite">&nbsp;<dict:text field="tradeWay" initvalue="${entity.tradeway}"/></td>
			<td class="tdgray">支付时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="entity.paytime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">已付（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${entity.payamount}"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">处理状态：</td>
			<td class="tdwhite">&nbsp;<dict:text field="orderDealStatus" initvalue="${entity.dealstatus }"/></td>
			<td class="tdgray">处理人：</td>
			<td class="tdwhite">&nbsp;${entity.opername }</td>
			<td class="tdgray">开始处理时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="entity.opertime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">校对状态：</td>
			<td class="tdwhite">&nbsp;<dict:text field="checkStatus" initvalue="${entity.checkstatus }"/></td>
			<td class="tdgray">校对人：</td>
			<td class="tdwhite">&nbsp;${entity.checkname }</td>
			<td class="tdgray">校对时间：</td>
			<td class="tdwhite">&nbsp;
				<s:date name="entity.checkstarttime" format="yyyy-MM-dd HH:mm:ss" />
				~
				<s:date name="entity.checkfinishtime" format="HH:mm:ss" />
			</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">翻译语言：</td>
			<td class="tdwhite" colspan="5">
				&nbsp;<dict:text field="textLang" initvalue="${entity.cmcTtTrans.fromlang}"/>
				&nbsp;->&nbsp;
				<dict:text field="textLang" initvalue="${entity.cmcTtTrans.tolang}"/>		
			</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">原文：</td>
			<td class="tdwhite" colspan="5" style="padding:3px;">${entity.cmcTtTrans.src}</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">译文：</td>
			<td class="tdwhite" colspan="5" style="padding:3px;">${entity.cmcTtTrans.dst}</td>
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
				<p><input type="button" class="btn3" value="给客户留言" onClick="saveMsg();"/></p>
			</td>
			<td width="70%" valign="top" style="padding-left: 30px;">
				<s:if test="!entity.cmcRMsgs.isEmpty()">
					<ul style="list-style:none;margin: 0;padding: 0">
					<s:iterator value="entity.cmcRMsgs" status="sts" id="l">
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
				<s:iterator value="entity.cmcRLogs" status="sts" id="l">
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
		<input type="hidden" id="orderId" value="${entity.orderid }">
		<p align="center"><input type="button" class="btn2" value="返回列表" onClick="backList();" /></p>
	</body>
</html>
