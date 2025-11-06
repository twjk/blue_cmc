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
				<th style="width:5%">工作时间</th>
				<th style="width:20%">公司</th>
				<th style="width:22%">岗位</th>
				<th style="width:15%">报酬</th>
				<th style="width:5%">城市</th>
				<th style="width:10%">创建日期</th>
				<th style="width:5%">排序</th>
				<th style="width:5%">状态</th>
				<th style="width:7%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${taskid}')" >
				<td>
					<s:if test="activity!=null">
						<img src="<lt:contextPath/>/images/activity.png" alt="活动" title="${activity.title }">
					</s:if>
				</td>
				<td>${taskid}</td>
				<td><dict:text field="workTimeType" initvalue="${worktimetype }"/></td>
				<td>${cmcLtCompany.companyname }</td>
				<td>${title }</td>
				<td>${reward }</td>
				<td>${cityname }</td>
				<td><s:date name="%{createtime}" format="yyyy-MM-dd"/></td>
				<td>${sortindex }</td>
				<td>
					<s:if test="userid>0">
						<s:if test="status==1">
							<a href="javascript:updateStatus('${taskid }','0');" title="点击下架"><dict:text field="localTaskStatus" initvalue="${status}"/></a>
						</s:if>
						<s:else>
							<dict:text field="localTaskStatus" initvalue="${status}"/>
						</s:else>
					</s:if>
					<s:else>
						<a href="javascript:updateStatus('${taskid }','${status==0?1:0 }');" title="点击${status==1?'下架':'发布' }"><dict:text field="localTaskStatus" initvalue="${status}"/></a>
					</s:else>
				</td>
				<td>
					<a href="javascript:editData('${taskid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="编辑" title="编辑"></a>
					<a href="javascript:deleteData('${taskid }');"><img src="<lt:contextPath/>/images/delet.gif" alt="删除" title="删除"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
