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
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeader">
		<tr>
			<td width="2%">&nbsp;</td>
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;对话详情</td>
		</tr>
		<tr>
			<td class="tdgray" width="10%" colspan="2">对话编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.dialogid }</td>
			<td class="tdgray" width="10%">用户：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.user.nick }（${entity.userid}）</td>
			<td class="tdgray" width="10%">处理状态：</td>
			<td class="tdwhite" width="30%">&nbsp;<dict:text field="dialogDealStatus" initvalue="${entity.dealstatus }"/></td>
		</tr>
		</table>
		
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeader" style="margin-top: 10px;">
		<tr>
			<td width="2%">&nbsp;</td>
			<td width="98%" align="left" class="text13blackb">&nbsp;&nbsp;回复</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td style="text-align: center;padding:10px;">
				<textarea id="msg" style="width:100%;height:100px;"></textarea>
				<p>
					<input type="hidden" id="dialogId" value="${entity.dialogid }">
					<input type="button" class="btn2" value="回复" onClick="saveMsg();"/>
					<input type="button" class="btn2" value="返回列表" onClick="backList();" />
				</p>
			</td>
		</tr>
		</table>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0" style="width:100%">
			<tr>
				<th style="width:10%">消息编号</th>
				<th style="width:10%">消息方</th>
				<th style="width:50%">内容</th>
				<th style="width:15%">时间</th>
				<th style="width:15%">智能体</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="entity.msgs" status="stut" id="bean">
			<tr >
				<td>${msgid}</td>
				<td><dict:text field="dialogMsgSide" initvalue="${msgside }"/></td>
				<td>${msg }</td>
				<td><s:date name="createtime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<s:if test="@com.qcmz.cmc.constant.DictConstant@DICT_DIALOG_MSGSIDE_AGENT==msgside">
						${msgname }(${msgcd })
					</s:if>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	</body>
</html>
