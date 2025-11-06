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
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" >
			<tr>
				<th style="width:5%">用户编号</th>
				<th style="width:10%">用户昵称</th>
				<th style="width:12%">红包编号</th>
				<th style="width:26%">红包暗语</th>
				<th style="width:5%">红包金额(元)</th>
				<th style="width:5%">红包个数</th>
				<th style="width:5%">已领个数</th>
				<th style="width:10%">发红包时间</th>
				<th style="width:10%">领取日期止</th>
				<th style="width:5%">状态</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr>
				<td>${userid}</td>
				<td>${bean.user.nick}</td>
				<td>${packetid}</td>
				<td>${title}</td>
				<td>${packetamount}</td>
				<td>${packetnum}</td>
				<td>
					<s:if test="#bean.receivednum!=null && #bean.receivednum>0">
						<a href="javascript:packetReceived('${packetid}');">${receivednum}</a>
					</s:if>
					<s:else>
						${receivednum}
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
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
