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
				<th style="width:10%">用户</th>
				<th style="width:35%">内容</th>
				<th style="width:15%">时间</th>
				<th style="width:10%">消息方</th>
				<th style="width:10%">智能体</th>
				<th style="width:10%">对话处理状态</th>
				<th style="width:5%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="detailData('${dialogid}')" >
				<td>${msgid}</td>
				<td>${user.nick }<br/>${userid }</td>
				<td>${msg}</td>
				<td><s:date name="createtime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><dict:text field="dialogMsgSide" initvalue="${msgside }"/></td>
				<td>
					<s:if test="@com.qcmz.cmc.constant.DictConstant@DICT_DIALOG_MSGSIDE_AGENT==msgside">
						${msgname }(${msgcd })
					</s:if>
				</td>
				<td><dict:text field="dialogDealStatus" initvalue="${cmcDialog.dealstatus }"/></td>
				<td>
					<a href="javascript:detailData('${dialogid}');"><img src="<lt:contextPath/>/images/detail.gif" alt="详情" title="详情"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
