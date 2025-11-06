<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
    <head>
		<title></title>
	</head>
	<body>
	<div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:10%">JPushRegid/用户编号</th>
				<th style="width:10%">语言</th>
				<th style="width:30%">原文</th>
				<th style="width:30%">译文</th>
				<th style="width:10%">修正时间</th>
				<th style="width:10%">来源</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr>
				<td>${pushregid}<br/>${userid}</td>
				<td>
					<dict:text field="textLang" initvalue="${fromlang}"/>
					->
					<dict:text field="textLang" initvalue="${tolang}"/>
				</td>
				<td>${src }</td>
				<td>
					${dst }
					<br/>
					<span class="hint">${mtdst }<span>
				</td>
				<td><s:date name="%{opertime}" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><dict:text field="transCorrectSource" initvalue="${correctsource }"/></td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}"/>
	</body>
</html>
