<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="qcmz" prefix="lt"%>
<%@ taglib uri="dictionary" prefix="dict"%>
<html>
    <head>
		<title></title>
	</head>
	<body>
	<div>
		<table class="rebateHeader" cellpadding="0" cellspacing="0"  align="center" style="width:100%">
			<tr>
				<th style="width:31%">红包暗语</th>
				<th style="width:7%">红包金额(元)</th>
				<th style="width:7%">服务费(元)</th>
				<th style="width:7%">余额抵扣(元)</th>
				<th style="width:7%">应付金额(元)</th>
				<th style="width:7%">红包个数</th>
				<th style="width:7%">已领个数</th>				
				<th style="width:10%">发红包时间</th>
				<th style="width:10%">领取日期止</th>				
				<th style="width:7%">状态</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr>
				<td>${title}</td>
				<td>${packetamount}</td>
				<td>${serviceamount}</td>
				<td>${deductamount}</td>
				<td>${payableamount}</td>
				<td>${packetnum}</td>
				<td>
					<s:if test="#bean.receivednum!=null && #bean.receivednum>0">
						<a href="javascript:packetReceived('${packetid}');">${receivednum }</a>
					</s:if>
					<s:else>
						${receivednum }
					</s:else>
				</td>
				<td><s:date name="%{createtime}" format="yyyy-MM-dd HH:mm"/></td>
				<td><s:date name="%{endtime}" format="yyyy-MM-dd HH:mm"/></td>
				<td><dict:text field="redPacketStatus" initvalue="${status }"/></td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="packetQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
