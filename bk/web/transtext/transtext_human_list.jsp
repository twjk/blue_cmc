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
				<th style="width:10%">订单编号</th>
				<th style="width:8%">用户</th>
				<th style="width:8%">语言</th>
				<th style="width:25%">原文</th>
				<th style="width:25%">译文</th>
				<th style="width:10%">提交/完成时间</th>
				<th style="width:8%">处理状态/人员</th>
				<th style="width:6%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${orderid}')">
				<td>
					${orderid}
					<s:if test="cmcROrder.dealprogress=='03'">
						<br/>￥${cmcROrder.amount }
					</s:if>
				</td>
				<td>${userid }<br/>${user.nick }</td>
				<td>
					<dict:text field="textLang" initvalue="${fromlang}"/>
					&nbsp;->&nbsp;
					<dict:text field="textLang" initvalue="${tolang}"/>
				</td>
				<td style="padding-right: 3px;">${src }</td>
				<td style="padding-left: 3px;padding-right: 3px">${dst }</td>			
				<td>
					<s:date name="%{cmcROrder.waittime}" format="yyyy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{cmcROrder.finishtime}" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<dict:text field="orderDealStatus" initvalue="${cmcROrder.dealstatus}"/>
					<br/>
					${cmcROrder.opername}
				</td>				
				<td>
					<a href="javascript:editData('${orderid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="翻译" title="翻译"></a>			
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>