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
				<th style="width:10%">业务类型</th>
				<th style="width:10%">用户编号</th>
				<th style="width:15%">用户昵称</th>
				<th style="width:10%;cursor:pointer;" onclick="sortBalance();">
					可用余额
					<span id="sortBalance" class="<s:if test="orderby=='balance desc'">sortby</s:if><s:else>unsortby</s:else>">
						&nbsp;↓&nbsp;
					</span>
				</th>
				<th style="width:10%;cursor:pointer;" onclick="sortIncome();">
					收入总额
					<span id="sortIncome" class="<s:if test="orderby=='incomeamount desc'">sortby</s:if><s:else>unsortby</s:else>">
						&nbsp;↓&nbsp;
					</span>
				</th>
				<th style="width:10%;cursor:pointer;" onclick="sortExpenses();">
					支出总额
					<span id="sortExpenses" class="<s:if test="orderby=='expensesamount desc'">sortby</s:if><s:else>unsortby</s:else>">
						&nbsp;↓&nbsp;
					</span>
				</th>
				<th style="width:20%">最近交易</th>
				<th style="width:10%">创建时间</th>
				<th style="width:5%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="detailData('${userid}')">
				<td><dict:text field="serviceType" initvalue="${servicetype}"/></td>
				<td>${userid}</td>
				<td>${user.nick }</td>
				<td>${balance }</td>
				<td>${incomeamount }</td>
				<td>${expensesamount }</td>
				<td>
					<s:iterator value="bills" status="st" id="bill">
					${amount }（${billdesc }）
					</s:iterator>
				</td>
				<td><s:date name="%{createtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<a href="javascript:detailData('${userid}');"><img src="<lt:contextPath/>/images/detail.gif" alt="详情" title="详情"></a>						
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>