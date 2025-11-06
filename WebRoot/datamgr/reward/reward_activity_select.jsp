<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<html>
  <head>
	<title></title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/rewardactivity.js"></script>
  </head>
	<body>
	<div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:90%">活动名称</th>
				<th style="width:10%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="list" id="bean">
			<tr onDblClick="selectedActivity('${actid}', '${title }')" >
				<td>${title}</td>
				<td><a href="javascript:selectedActivity('${actid}', '${title }');">选择</a></td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<script>
	function selectedActivity(actid, title){
		window.opener.selActivityCallback(actid, title);
		window.close();
	}
	</script>
	</body>
</html>