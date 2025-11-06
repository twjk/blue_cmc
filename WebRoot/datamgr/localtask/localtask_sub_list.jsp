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
				<th style="width:10%">用户</th>
				<th style="width:5%">工作时间</th>
				<th style="width:15%">岗位</th>
				<th style="width:10%">薪酬</th>
				<th style="width:30%">订阅内容</th>
				<th style="width:5%">学历/工龄</th>
				<th style="width:15%">工作城市/地址</th>
				<th style="width:10%">订阅/通知</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr>
				<td>
					${user.nick }<br/>${userid }
				</td>
				<td><dict:text field="workTimeType" initvalue="${worktimetype }"/></td>
				<td>${title }</td>
				<td><dict:text field="rewardType" initvalue="${rewardtype }"/>${reward }</td>
				<td>${content }</td>
				<td>
					<dict:text field="localEdu" initvalue="${edu }"/>
					<br/>
					<s:if test="workyear!=null">${workyear}年</s:if>
				</td>
				<td>${cityname }<br/>${address }</td>
				<td>
					<s:date name="%{createtime}" format="yyyy-MM-dd HH:mm"/>
					<br/>
					<s:date name="%{notifytime}" format="yyyy-MM-dd HH:mm"/>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
