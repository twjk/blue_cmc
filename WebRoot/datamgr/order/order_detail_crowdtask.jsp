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
		
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeader">
		<tr>
			<td width="2%">&nbsp;</td>
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;<dict:text field="orderType" initvalue="${order.ordertype }"/>订单详情</td>
		</tr>
		<tr>
			<td class="tdgray" width="10%" colspan="2">订单编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${order.orderid }</td>
			<td class="tdgray" width="10%">用户编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${order.userid }</td>
			<td class="tdgray" width="10%">处理状态：</td>
			<td class="tdwhite" width="30%">&nbsp;<dict:text field="orderDealStatus" initvalue="${order.dealstatus }"/></td>
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
		<tr>
			<td class="tdgray" colspan="2">任务单价（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${order.cmcRTask.price}"/></td>
			<td class="tdgray">参与人数：</td>
			<td class="tdwhite">&nbsp;${order.cmcRTask.peoplenum}</td>
			<td class="tdgray">任务天数：</td>
			<td class="tdwhite">&nbsp;${order.cmcRTask.days}</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">任务说明：</td>
			<td class="tdwhite" colspan="5" style="padding:3px;">${order.cmcRTask.content}</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">相关网址：</td>
			<td class="tdwhite" colspan="5" style="padding:3px;">${order.cmcRTask.url}</td>
		</tr>
		<s:if test="order.cmcRTask.picurl!=null && order.cmcRTask.picurl!=''">
		<tr>
			<td class="tdgray" colspan="2">任务图片：</td>
			<td class="tdwhite" colspan="5" style="padding:3px;">
				<img alt="图片" src="${order.cmcRTask.picurl}" style="max-width: 200px;max-height: 100px;">
				<br/>				
				${order.cmcRTask.picurl}
			</td>
		</tr>
		</s:if>
		<s:if test="order.cmcRTask.cmcCtTask!=null">
		<tr>
			<td class="tdgray" colspan="2">任务编号：</td>
			<td class="tdwhite">&nbsp;${order.cmcRTask.taskid }</td>
			<td class="tdgray">任务名称：</td>
			<td class="tdwhite">&nbsp;${order.cmcRTask.cmcCtTask.title }</td>
			<td class="tdgray">任务进展：</td>
			<td class="tdwhite">
				&nbsp;
				已完成【${order.cmcRTask.cmcCtTask.finishnum }】
				待完成【${order.cmcRTask.cmcCtTask.ingnum }】
				报名【${order.cmcRTask.cmcCtTask.applynum }】
				上限【${order.cmcRTask.cmcCtTask.maxnum }】
			<td>
		</tr>
		</s:if>
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
		<br/>
	</body>
</html>
