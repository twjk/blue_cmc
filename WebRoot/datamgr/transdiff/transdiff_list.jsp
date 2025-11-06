<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
		<title></title>
	</head>
	<body>
	<div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:10%">JPushRegid</th>
				<th style="width:5%">用户编号</th>
				<th style="width:10%">语言</th>
				<th style="width:30%">原文</th>
				<th style="width:35%">译文</th>
				<th style="width:5%">翻译时间</th>
				<th style="width:5%">状态</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr>
				<td>${pushregid}</td>
				<td>${userid}</td>
				<td>
					<dict:text field="textLang" initvalue="${fromlang}"/>
					->
					<dict:text field="textLang" initvalue="${tolang}"/>
				</td>
				<td>${src }</td>
				<td id="td_dst_${diffid }">
					<s:if test="status==0">
						<div style="width:100%;">
							<div style="float:right;width:60px">
								<a href="javascript:correctDst(${diffid},false);"><img src="<lt:contextPath/>/images/save.png" title="修正"></a>
								&nbsp;
								<a href="javascript:correctDst(${diffid},true);"><img src="<lt:contextPath/>/images/tolib.png" title="修正并存入译库"></a>
							</div>
							<div style="margin-right: 60px;"><input id="dst_${diffid }" value="<c:out value='${dst}'/>" style="width:100%;margin-top:3px"></div>
						</div>
					</s:if>
					<s:else>${dst }</s:else>
				</td>
				<td><s:date name="%{transtime}" format="HH:mm:ss"/></td>
				<td id="td_status_${diffid }"><dict:text field="dealstatus" initvalue="${status}" table="common" dataSource="xml"/></td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}"/>
	</body>
</html>
