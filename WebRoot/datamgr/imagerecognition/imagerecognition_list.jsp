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
				<th style="width:5%">编号</th>
				<th style="width:10%">分类</th>
				<th style="width:15%">用户</th>
				<th style="width:20%">图像</th>
				<th style="width:35%">识别结果</th>
				<th style="width:15%">创建时间</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr>
				<td>${imageid}</td>
				<td><dict:text field="ircat" initvalue="${catid}"/></td>
				<td>${userid }<br/>${user.nick }</td>
				<td>
					<a href="${imageurl }" target="_blank">
						<img src="${thumburl }" style="max-width: 150px; max-height: 150px;padding:2px;"></td>
					</a>
				<td>
					<ul style="list-style: none;margin: 5 0 5 0;padding: 0">
						<s:iterator value="cmcIrRecognitions" var="recognition">
						<li>
							${content }
							(${score })
						</li>
						</s:iterator>
					</ul>
				</td>
				<td><s:date name="%{createtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
