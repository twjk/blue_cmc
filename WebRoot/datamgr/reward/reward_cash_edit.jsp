<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">   
<html>
	<head>
		<title>提现处理</title>
	</head>
	<body>
		<div style="height:10px"></div>
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeader">
		<tr>
			<td width="2%">&nbsp;</td>
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;提现详情</td>
		</tr>
		<tr>
			<td class="tdgray" width="10%" colspan="2">可用余额：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.cmcRewardAccount.balance }</td>
			<td class="tdgray" width="10%">用户：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.user.nick }（${entity.userid }）</td>
			<td class="tdgray" width="10%">用户手机号：</td>
			<td class="tdwhite" width="30%">&nbsp;${entity.user.mobile }</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">本次提现金额：</td>
			<td class="tdwhite markR">&nbsp;${entity.cashamount }</td>
			<td class="tdgray">帐户类型：</td>
			<td class="tdwhite">&nbsp;<dict:text field="payAccountType" initvalue="${entity.accounttype}"/></td>
			<td class="tdgray">申请时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="entity.createtime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">状态：</td>
			<td class="tdwhite">&nbsp;<dict:text field="cashStatus" initvalue="${entity.status }"/></td>
			<td class="tdgray">处理时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="entity.dealtime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">处理人：</td>
			<td class="tdwhite">&nbsp;${entity.dealname }</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">外部交易号：</td>
			<td class="tdwhite">&nbsp;${entity.outtradeid }</td>
			<td class="tdgray">交易结果：</td>
			<td class="tdwhite">&nbsp;${entity.traderesult }</td>
			<td class="tdgray">驳回原因：</td>
			<td class="tdwhite">&nbsp;${entity.dealresult }</td>
		</tr>
		</table>
		
		<s:if test="entity.status==1">
		<form name="editForm" action="#" id="editForm">
		<p align="center">
			<s:hidden id="cashid" name="entity.cashid"/>
			<input type="button" class="btn2" value="向用户付款" onClick="transfer();" />
			<input type="button" class="btn2" value="驳回申请" onClick="showReject();" />
			<input type="button" class="btn2" value="返 回" onClick="backList();"/>
			
			<div id="rejectDiv" style="position: absolute;width:500px;display: none;background-color: #ffffff">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableHeader">
			<tr>
				<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">驳回申请</td>
			</tr>
			<tr>
				<td width="30" align="right" class="textrse12">*</td>
				<td width="100">驳回原因：</td>
				<td><input type="text" id="rejectReason" style="width: 300px;"></td>
			</tr>
			<tr>
				<td></td>
				<td>备选原因：</td>
				<td>
					<select id="selRejectReason" onchange="selectRejectReason();">
						<option value="">--请选择--</option>
						<option value="帐户余额不足">帐户余额不足</option>
						<option value="请将微信支付做实名认证，再次申请提现">请将微信支付做实名认证，再次申请提现</option>
						<option value="交易被微信拦截，请更换提现微信账号，再次申请提现">交易被微信拦截，请更换提现微信账号，再次申请提现</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center" height="40">
					<input type="button" class="btn2" value="确定驳回" onClick="rejectCash();" />
					<input type="button" class="btn2" value="关闭" onClick="hideReject();" />
				</td>
			</tr>
			</table>
		</div>
		</p>
		</form>
		</s:if>
		<s:else>
			<p align="center"><input type="button" class="btn2" value="返 回" onClick="backList();"/></p>
		</s:else>
		
		<p style="margin-bottom: 0;padding-bottom: 0">提现记录：</p>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" style="width:100%">
		<tr>
			<th style="width:10%">帐户类型</th>
			<th style="width:10%">提现金额</th>
			<th style="width:20%">申请时间</th>
			<th style="width:20%">处理时间</th>
			<th style="width:10%">处理人</th>
			<th style="width:20%">处理结果</th>
			<th style="width:10%">状态</th>
		</tr> 
		<s:iterator value="cashList" status="stut" id="bean">
		<tr>
			<td><dict:text field="payAccountType" initvalue="${accounttype}"/></td>
			<td>${cashamount}</td>
			<td><s:date format="yyyy-MM-dd HH:mm:ss" name="createtime"/></td>
			<td><s:date format="yyyy-MM-dd HH:mm:ss" name="dealtime"/></td>
			<td>${dealname}</td>
			<td><s:if test="dealresult!=null">${dealresult }</s:if></td>
			<td><dict:text field="cashStatus" initvalue="${status}"/></td>
		</tr>
		</s:iterator>
		</table>
	</body>
</html>
