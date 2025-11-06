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
				<th style="width:20%">译员用户名</th>
				<th style="width:20%">译员姓名</th>
				<th style="width:15%">佣金（元）</th>
				<th style="width:15%">订单总额（元）</th>
				<th style="width:15%">订单数</th>
				<th style="width:15%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="translatorCommissionList" status="stut" id="bean">
			<tr onDblClick="dataDetail('${operCd}')" >
				<td>${operCd}</td>
				<td>${operName }</td>
				<td><fmt:formatNumber type="currency" pattern="0.00" value="${commissionAmount}"/></td>
				<td><fmt:formatNumber type="currency" pattern="0.00" value="${orderAmount }"/></td>
				<td>${orderCount }</td>
				<td>
					<a href="javascript:dataDetail('${operCd}');"><img src="<lt:contextPath/>/images/detail.gif" alt="明细" title="明细"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
		<br/>	
	</div>
	</body>
</html>
