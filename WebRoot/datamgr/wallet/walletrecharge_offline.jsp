<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
  	<head>
		<title></title>
	</head>
	<body>
		<form name="editForm" action="#" id="editForm">
			<div style="height:10px"></div>
			<table width="100%" cellpadding="0" cellspacing="0" class="tableHeader" border="0" align="center">
				<tr>
				    <td width="2%">&nbsp;</td>
					<td colspan="2" align="left" class="text13blackb">手工充值</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td width="10%">用户编号：</td>
					<td>
						<input id="userId" name="offlineBean.userId" value="">
						<span class='hint'>（用户编号和手机号码不能同时为空）</span>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>手机号码：</td>
					<td>
						<input id="mobile" name="offlineBean.mobile" value=""/>
						<dict:select field="serviceType" name="offlineBean.serviceType"/>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>充值类型：</td>
					<td>
						<ul class="raddefault">
							<li><input type="radio" name="offlineBean.billType" value="1" checked="checked"/>充值</li>
							<li><input type="radio" name="offlineBean.billType" value="5"/>活动赠送</li>
						</ul>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>充值金额：</td>
					<td><input id="amount" name="offlineBean.amount"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>相关订单号：</td>
					<td><input name="offlineBean.orderId"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>充值说明：</td>
					<td><input name="offlineBean.description"/></td>
				</tr>
				<tr>
				 	<td align="right" colspan="3" height="10"></td>
				</tr>
				<tr height="30">
					<td colspan="3" align="center">
						<input type="button" class="btn2" value="保存" onClick="saveOfflineRecharge();" />
						<input type="button" class="btn2" value="取消" onClick="backList();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
