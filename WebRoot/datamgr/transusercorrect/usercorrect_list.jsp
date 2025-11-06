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
				<th style="width:10%">JPushRegid/用户编号</th>
				<th style="width:10%">语言</th>
				<th style="width:30%">原文</th>
				<th style="width:35%">修正译文/机器译文</th>
				<th style="width:5%">翻译时间</th>
				<th style="width:5%">状态</th>
				<th style="width:5%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr id="tr_${correctid }">
				<td>${pushregid}<br/>${userid}</td>
				<td>
					<dict:text field="textLang" initvalue="${fromlang}"/>
					->
					<dict:text field="textLang" initvalue="${tolang}"/>
				</td>
				<td>${src }</td>
				<td id="td_dst_${correctid }">
					<s:if test="status==0">
						<div style="width:100%;">
							<div style="float:right;width:60px">
								<a href="javascript:correctDst(${correctid},false);"><img src="<lt:contextPath/>/images/save.png" title="保存"></a>
								&nbsp;
								<a href="javascript:correctDst(${correctid},true);"><img src="<lt:contextPath/>/images/tolib.png" title="保存并存入译库"></a>
							</div>
							<div style="margin-right: 60px;"><input id="dst_${correctid }" value="<c:out value='${dst}'/>" style="width:100%;margin-top:3px"></div>
						</div>
					</s:if>
					<s:else>${dst }<br/></s:else>
					<span class="hint">${mtdst }<span>
				</td>
				<td><s:date name="%{createtime}" format="HH:mm:ss"/></td>
				<td id="td_status_${correctid }">
					<dict:text field="dealstatus" initvalue="${status}" dataSource="xml" table="common"/>
				</td>
				<td id="td_oper_${correctid }">
					<s:if test="status==0">
						<a href="javascript:deleteData('${correctid }');"><img src="<lt:contextPath/>/images/delet.gif" alt="删除" title="删除"></a>
					</s:if>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}"/>
	</body>
</html>
