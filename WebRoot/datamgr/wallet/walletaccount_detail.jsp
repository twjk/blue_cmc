<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  	<head>
		<title></title>
		<script type="text/javascript">
			$(function() {
				$("#selector").organicTabs();
			});
		</script>	
	</head>
	<body>
		<div style="height:10px"></div>
		
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeaderNoBottom">
		<tr>
			<td width="2%">&nbsp;</td>
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;帐户信息</td>
		</tr>
		<tr>
			<td class="tdgray" width="10%" colspan="2">用户编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.userid }</td>
			<td class="tdgray" width="10%">用户昵称：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.user.nick }</td>
			<td class="tdgray" width="10%">业务类型：</td>
			<td class="tdwhite" width="30%">&nbsp;<dict:text field="serviceType" initvalue="${entity.servicetype }"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">可用余额（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${entity.balance}"/></td>
			<td class="tdgray">收入总额（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${entity.incomeamount}"/></td>
			<td class="tdgray">支出总额（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${entity.expensesamount}"/></td>
		</tr>
		</table>
		
		<div style="width:100%;text-align: center;padding-top:10px;">
			<input type="hidden" id="userId" value="${entity.userid}"/>
			<input type="button" class="btn2" value="返回列表" onClick="backList();" />
		</div>
		<div id="selector">
			<ul class="nav">
				<li class="nav-one"><a id="incomeNav" href="#incomeList" onclick="queryIncome();" class="current">收入列表</a></li>
				<li class="nav-two"><a id="expensesNav" href="#expensesList" onclick="queryExpenses();">支出列表</a></li>
			</ul>
			<div class="contentDiv">
				<div id="incomeList" class="pane"></div>
				<div id="expensesList" class="hide"></div>
			</div>
			<input type="hidden" id="incexp" value="1">
		</div>
	</body>
</html>
