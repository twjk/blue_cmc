<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  	<head>
		<title></title>
		<script type="text/javascript">
			var other = ${other};
		</script>
	</head>
	<body>
		<div style="height:10px"></div>
		
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeaderNoBottom">
		<tr>
			<td width="2%">&nbsp;</td>
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;短文快译校对</td>
		</tr>
		<tr>
			<td class="tdgray" width="10%" colspan="2">订单编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${order.orderid }</td>
			<td class="tdgray" width="10%">用户编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${order.userid }</td>
			<td class="tdgray" width="10%">翻译模式：</td>
			<td class="tdwhite" width="30%">&nbsp;<dict:text field="transModel" initvalue="${order.cmcTtTrans.transmodel }"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">创建时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="order.createtime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">提交时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="order.waittime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">完成时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="order.finishtime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">处理状态：</td>
			<td class="tdwhite">&nbsp;<dict:text field="orderDealStatus" initvalue="${order.dealstatus }"/></td>
			<td class="tdgray">处理人：</td>
			<td class="tdwhite">&nbsp;${order.opername }</td>
			<td class="tdgray">开始处理时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="order.opertime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">校对状态：</td>
			<td class="tdwhite">&nbsp;<dict:text field="checkStatus" initvalue="${order.checkstatus }"/></td>
			<td class="tdgray">校对人：</td>
			<td class="tdwhite" id="checkname" <s:if test="other">class="markR"</s:if>>&nbsp;${order.checkname }</td>
			<td class="tdgray">校对时间：</td>
			<td class="tdwhite">
				&nbsp;<s:date name="order.checkstarttime" format="yyyy-MM-dd HH:mm:ss" />
				~
				<s:date name="order.checkfinishtime" format="HH:mm:ss" />
			</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">翻译语言：</td>
			<td class="tdwhite" colspan="5">
				<s:if test="canFinishCheck">
					&nbsp;<dict:select id="from" field="textLang" initvalue="${order.fromlang}"/>
					&nbsp;<img onclick="exchangeLang();" style="vertical-align:middle;max-height: 16px;cursor: pointer;" src="<lt:contextPath/>/images/exchange.png">&nbsp;
					<dict:select id="to" field="textLang" initvalue="${order.tolang}"/>
				</s:if>
				<s:else>
					&nbsp;<dict:text field="textLang" initvalue="${order.fromlang}"/>
					&nbsp;->&nbsp;
					<dict:text field="textLang" initvalue="${order.tolang}"/>
				</s:else>
			</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">原文：</td>
			<td class="tdwhite" colspan="5">				
				&nbsp;${order.cmcTtTrans.src}
				&nbsp;&nbsp;
				<s:if test="order.cmcTtTrans.voice!=null && order.cmcTtTrans.voice!=''">
					<img onclick="playAudio('${order.cmcTtTrans.voice}', this);" src="<lt:contextPath/>/images/play.png" title="播放" style="vertical-align:middle;cursor: pointer;">
				</s:if>
			</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">译文：</td>
			<td class="tdwhite" colspan="5" style="">
				<s:if test="canFinishCheck">
					<textarea id="dst" style="width:99%;height:100px;margin: 3px;">${order.cmcTtTrans.dst}</textarea>
				</s:if>
				<s:else>
					<div style="padding-left:5px;">${order.cmcTtTrans.dst}</div>
				</s:else>
				<div align="center" style="margin:5px;">
					<input type="hidden" id="orderId" value="${order.orderid }">
					<input type="hidden" id="orderType" value="${order.ordertype}" />
					<form name="editForm" action="#" id="editForm">
						<s:if test="canStartCheck">
							<input type="button" class="btn2" value="开始校对" onClick="startCheck();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<s:if test="canFinishCheck">
							<input type="button" id="btnSave" class="btn2" value="保存校对" onClick="saveCheckText();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" id="btnFinish" class="btn3" value="保存&完成校对" onClick="finishCheckText();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<input type="button" class="btn2" value="返回列表" onClick="refreshList();" />				
					</form>
				</div>
			</td>
		</tr>
		</table>
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeader" style="margin-top: 10px;">
		<tr>
			<td width="2%">&nbsp;</td>
			<td colspan="2" align="left" class="text13blackb">&nbsp;&nbsp;留言</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td width="28%" style="text-align: center;padding:10px;">
				<textarea id="msg" style="width:100%;height:100px;"></textarea>
				<p><input type="button" class="btn3" value="给客户留言" onClick="saveMsg();"/></p>
			</td>
			<td width="70%" valign="top" style="padding-left: 30px;">
				<s:if test="!order.cmcRMsgs.isEmpty()">
					<ul style="list-style:none;margin: 0;padding: 0">
					<s:iterator value="order.cmcRMsgs" status="sts" id="l">
						<li>
						<s:date name="createtime" format="yyyy-MM-dd HH:mm:ss" />
						-- ${msgname }
						-- ${msg }
						</li>
					</s:iterator>
					</ul>
				</s:if>
			</td>
		</tr>
		</table>
		<table width="100%" cellspacing="0" border="0" cellpadding="0" class="tableHeader" style="margin-top: 10px;">
		<tr>
			<td width="2%">&nbsp;</td>
			<td align="left" class="text13blackb">&nbsp;&nbsp;操作历史</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>
				<ul style="list-style:none;margin: 0;padding: 0">
				<s:iterator value="order.cmcRLogs" status="sts" id="l">
					<li>
					<s:date name="opertime" format="yyyy-MM-dd HH:mm:ss" />
					 -- ${opername }
					 -- <dict:text field="orderDealStatus" initvalue="${dealstatus}"/>
					 <s:if test="log!=null">
					 -- ${log }
					 </s:if>
					 </li>
				</s:iterator>
				</ul>
			</td>
		</tr>
		</table>
	</body>
</html>
