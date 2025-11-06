<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.qcmz.framework.constant.DictConstants"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
    <head>
		<title></title>
	</head>
	<body>
	<div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:3%">活动</th>
				<th style="width:5%">编号</th>
				<th style="width:7%">分类</th>
				<th style="width:10%">来源</th>
				<th style="width:40%">标题</th>
				<th style="width:10%">城市</th>
				<th style="width:10%">发布时间</th>
				<th style="width:5%">排序</th>
				<th style="width:5%">状态</th>
				<th style="width:5%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${artid}')" >
				<td>
					<s:if test="activity!=null">
						<img src="<lt:contextPath/>/images/activity.png" alt="活动" title="${activity.title }">
					</s:if>
				</td>
				<td>${artid}</td>
				<td>
					<c:if test="${not empty fullcatid}">
						<c:forEach var="i" begin="0" step="4" end="${fn:length(fullcatid)-1}">
							> <dict:text field="<%=DictConstants.DICTNAME_CAT %>" initvalue="${fn:substring(fullcatid, i, i+4)}"/>
						</c:forEach>
					</c:if>
				</td>
				<td>${cmcLtSource.sourcename }</td>
				<td><a href="${link}" target="_blank">${title }</a></td>
				<td>${cityname }</td>
				<td><s:date name="%{publishtime}" format="yyyy-MM-dd HH:mm"/></td>
				<td>${sortindex }</td>
				<td>
					<a href="javascript:updateStatus('${artid }','${status==0?1:0 }');" title="点击${status==1?'停用':'启用' }"><dict:text table="common" field="status" initvalue="${status}" dataSource="xml"></dict:text></a>
				</td>
				<td>
					<a href="javascript:editData('${artid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="编辑" title="编辑"></a>
					<a href="javascript:deleteData('${artid }');"><img src="<lt:contextPath/>/images/delet.gif" alt="删除" title="删除"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
