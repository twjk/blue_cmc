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
				<th style="width:20%">发红包用户编号</th>
				<th style="width:20%">发红包用户昵称</th>
				<th style="width:20%">领取金额(元)</th>
				<th style="width:25%">领取时间</th>
				<th style="width:15%">语音</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr>
				<td>${receiverid}</td>
				<td>${bean.receiver.nick}</td>
				<td>${amount}</td>
				<td><s:date format="yyyy-MM-dd HH:mm:ss" name="receivetime"/></td>	
				<td>
					<s:if test="#bean.voice!=null && #bean.voice!=''">
						<img onclick="playAudio('${voice}', this);" src="<lt:contextPath/>/images/play.png" title="播放" style="vertical-align:middle;cursor: pointer;">
					</s:if>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="receiveQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
