<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">   
<html>
	<head>
		<title>红包提现处理</title>
		<script type="text/javascript">
			$(function() {
				$("#selector").organicTabs();
			});
		</script>
	</head>
	<body>
		<div style="height:10px"></div>
		<table width="100%" cellpadding="0" cellspacing="0" class="tableHeader" border="0" align="center">
			<tr>
			    <th width="2%">&nbsp;</th>
				<th colspan="6" align="left" class="text13blackb">红包账户信息</th>
			</tr>	
			<tr>
				<td class="tdgray" colspan="2" width="10%">用户昵称：</td>
				<td class="tdwhite" width="20%">${entity.user.nick }</td>
				<td class="tdgray" width="10%">用户编号：</td>
				<td class="tdwhite" width="20%">${entity.userid }</td>
				<td class="tdgray" width="10%">可用余额(元)：</td>
				<td class="tdwhite" width="30%">${entity.balance }</td>
			</tr>
			<tr>
				<td class="tdgray" colspan="2" width="10%">领取总额(元)：</td>
				<td class="tdwhite">${entity.receiveamount }</td>
				<td class="tdgray" width="10%">发放总额(元)：</td>
				<td class="tdwhite">${entity.sendamount }</td>
				<td class="tdgray" width="10%">申请提现金额(元)：</td>
				<td class="tdwhite" width="30%">${entity.applyamount }</td>
			</tr>
			<tr>
				<td class="tdgray" colspan="2" width="10%">领取红包个数：</td>
				<td class="tdwhite" width="20%">${entity.receivenum }</td>
				<td class="tdgray" width="10%">发放红包个数：</td>
				<td class="tdwhite" width="20%">${entity.sendnum }</td>
				<td class="tdgray" width="10%">已提现金额(元)：</td>
				<td class="tdwhite" width="30%">${entity.cashamount }</td>
			</tr>
		</table>
		<div style="width:100%;text-align: center;">
			<br/>
			<input type="button" class="btn2" value="返回列表" onClick="backList();" />
		</div>
		<div id="selector">
			<ul class="nav">
				<li class="nav-one"><a id="selectRpPacket" href="#rppacketnav" class="current">发红包列表</a></li>
				<li class="nav-two"><a id="selectRpReceive" href="#rpreceivenav">领红包列表</a></li>
				<li class="nav-three"><a id="selectRpCash" href="#rpcashnav">提现列表</a></li>
			</ul>
			<div class="contentDiv">
				<div id="rppacketnav" class="pane"></div>
				<div id="rpreceivenav" class="hide"></div>
				<div id="rpcashnav" class="hide"></div>
			</div>
		</div>
	</body>
</html>
