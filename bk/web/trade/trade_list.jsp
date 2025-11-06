<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
    <head>
		<title></title>
		<script type="text/javascript" src="<lt:contextPath/>/javascript/listgry.js"></script>
	</head>
	<body>
	<div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:8%">交易编号</th>
				<th style="width:8%">用户编号</th>
				<th style="width:8%">交易类型</th>
				<th style="width:5%">点数</th>
				<th style="width:5%">金额</th>
				<th style="width:10%">项目/语言</th>
				<th style="width:5%">产品数量</th>
				<th style="width:15%">第三方/流水号</th>
				<th style="width:26%">描述</th>
				<th style="width:10%">时间</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr>
				<td>${tradeid}</td>
				<td>${userid}</td>
				<td><dict:text field="tradeType" initvalue="${tradetype}"/></td>
				<td>${point}</td>
				<td>${amount}</td>
				<td>
					<dict:text field="tradeItem" initvalue="${item}"/>
					<br/>
					<dict:text field="speechLang" initvalue="${langcode}"/>
				</td>
				<td>${productnum}</td>
				<td>
					<s:if test="cmcProxy!=null">
						${cmcProxy.proxyname}
					</s:if>
					<br/>
					${serial}
				</td>
				<td>${description}</td>
				<td><s:date name="%{tradetime}" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
