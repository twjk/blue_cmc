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
		<br/>
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeaderNoBottom">
		<tr>
			<td width="2%">&nbsp;</td>
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;译员佣金小计</td>
		</tr>
		<tr>
			<td class="tdgray" width="10%" colspan="2">译员：</td>
			<td class="tdwhite" width="20%">&nbsp;${translatorCommissionBean.operName }（${translatorCommissionBean.operCd }）</td>
			<td class="tdgray" width="10%">开始时间：</td>
			<td class="tdwhite" width="20%">&nbsp;<fmt:formatDate value="${start }" pattern='yyyy-MM-dd'/></td>
			<td class="tdgray" width="10%">结束时间：</td>
			<td class="tdwhite" width="30%">&nbsp;<fmt:formatDate value="${end }" pattern='yyyy-MM-dd'/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">佣金（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${translatorCommissionBean.commissionAmount}"/></td>
			<td class="tdgray">订单总额（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${translatorCommissionBean.orderAmount}"/></td>
			<td class="tdgray">订单数：</td>
			<td class="tdwhite">&nbsp;${translatorCommissionBean.orderCount }</td>
		</tr>
		</table>
		<div style="width:99%;text-align: center;padding:10px 0 10px 10;">
			<input type="button" class="btn2" value="返回列表" onClick="backList();" />
		</div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0" style="width:100%">
			<tr>
				<th style="width:3%">序号</th>
				<th style="width:12%">订单编号</th>
				<th style="width:8%">订单类型</th>
				<th style="width:15%">用户</th>
				<th style="width:8%">语言</th>
				<th style="width:20%">总额/优惠/抵扣/已付（元）</th>
				<th style="width:8%">佣金（元）</th>
				<th style="width:8%">处理状态</th>
				<th style="width:10%">开始处理时间</th>
				<th style="width:8%">处理时长</th>
			</tr> 
			<tbody id="tab2">
			<s:iterator value="translatorCommissionBean.orderList" status="stut" id="bean">
			<tr>
				<td>${stut.index+1 }</td>
				<td>${orderid}</td>
				<td><dict:text field="orderType" initvalue="${ordertype }"/></td>
				<td>${user.nick }</td>
				<td><dict:text field="textLang" initvalue="${fromlang}"/>
					&nbsp;->&nbsp;
					<dict:text field="textLang" initvalue="${tolang}"/>
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
					 	<fmt:formatNumber type="currency" pattern="0.00" value="${payamount}"/>
				 	</s:else>
				</td>
				<td>
					<s:if test="commissionamount==null">
						<span class="mark"><dict:text initvalue="${commissionstatus }" field="commissionstatus" dataSource="xml" table="common"/></span>
					</s:if>
					<s:else>
						<fmt:formatNumber type="currency" pattern="0.00" value="${commissionamount}"/>
					</s:else>
				</td>
				<td><dict:text field="orderDealStatus" initvalue="${dealstatus}"/></td>
				<td><s:date name="%{opertime}" format="MM-dd HH:mm:ss"/></td>
				<td>${dealTimeStr }</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
		<div style="width:99%;text-align: center;padding:10px 0 10px 0;">
			<input type="button" class="btn2" value="返回列表" onClick="backList();" />
		</div>
	</div>
	</body>
</html>
