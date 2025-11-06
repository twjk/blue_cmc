<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
  	<head>
		<title></title>
	</head>
	<body>
		<div style="height:10px"></div>
		
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeaderNoBottom">
		<tr>
			<td width="2%">&nbsp;</td>
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;用户套餐信息</td>
		</tr>
		<tr>
			<td class="tdgray" width="10%" colspan="2">用户套餐编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.ucid }</td>
			<td class="tdgray" width="10%">用户：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.user.nick }（${entity.userid}）</td>
			<td class="tdgray" width="10%">翻译类型：</td>
			<td class="tdwhite" width="30%">&nbsp;<dict:text field="transType" initvalue="${entity.transtype }"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">套餐名称：</td>
			<td class="tdwhite">&nbsp;${entity.combotitle }</td>
			<td class="tdgray">付款方：</td>
			<td class="tdwhite">&nbsp;<dict:text field="transComboPaySide" initvalue="${entity.payside }"/></td>
			<td class="tdgray">创建时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="%{entity.createtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">总量/余量：</td>
			<td class="tdwhite">&nbsp;${entity.num }/${entity.usableNumReal } ${entity.cmcCombo.unitname }</td>
			<td class="tdgray">已用次数：</td>
			<td class="tdwhite">&nbsp;${entity.usedtimes }</td>
			<td class="tdgray">有效期：</td>
			<td class="tdwhite">&nbsp;<s:date name="%{entity.starttime}" format="yyyy-MM-dd HH:mm:ss"/> ~ <s:date name="%{entity.endtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">兑换码：</td>
			<td class="tdwhite">&nbsp;${entity.exchangecode }</td>
			<td class="tdgray">兑换时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="%{entity.exchangetime}" format="yyyy-MM-dd HH:mm:ss"/></td>
			<td class="tdgray">兑换状态：</td>
			<td class="tdwhite">&nbsp; <dict:text field="exchangestatus" initvalue="${entity.exchangestatus }" table="common" dataSource="xml"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">服务途径：</td>
			<td class="tdwhite">&nbsp;<dict:text field="transComboServiceWay" initvalue="${entity.cmcCombo.serviceway }"/></td>
			<td class="tdgray"></td>
			<td class="tdwhite">&nbsp;</td>
			<td class="tdgray"></td>
			<td class="tdwhite"></td>
		</tr>
		</table>

		<div style="width:100%;text-align: center;padding-top:10px;">
			<input type="button" class="btn2" value="返回列表" onClick="backList();" />
			<input type="button" class="btn2" value="增加使用" onClick="addUseUserCombo();" />
		</div>
		
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0" style="width: 100%">
		<tr>
			<th style="width:30%">使用时间</th>
			<th style="width:30%">使用订单号</th>
			<th style="width:40%">备注</th>
		</tr> 
		<tbody>
		<s:iterator value="entity.hises" id="his">
		<tr>
			<td><s:date name="%{usedtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${usedorderid}</td>
			<td>${remark}</td>
		</tr>
		</s:iterator>
		</tbody>
		</table>
		
		<div id="useUserComboDiv" style="position: absolute;width:400px;display: none;background-color: #ffffff">
			<form name="editForm" action="#" id="editForm">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableHeader">
			<tr>
				<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">填写用户套餐使用信息</td>
			</tr>
			<tr>
				<td width="30" align="right" class="textrse12">*</td>
				<td width="100">使用时间：</td>
				<td><input type="text" id="usedtime" name="usedTime" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
			</tr>
			<tr>
				<td></td>
				<td>使用订单号：</td>
				<td><input type="text" id="usedorderid" name="orderId"></td>
			</tr>
			<tr>
				<td></td>
				<td>备注：</td>
				<td><input type="text" id="remark" name="remark"></td>
			</tr>
			<tr>
				<td colspan="3" align="center" height="40">
					<input type="hidden" id="ucid" name="ucid" value="${entity.ucid }">
					<input type="button" class="btn2" value="保 存" onClick="saveUseUserCombo();" />
					<input type="button" class="btn2" value="取 消" onClick="hideUseUserCombo();" />
				</td>
			</tr>
			</form>
			</table>
		</div>
	</body>
</html>
