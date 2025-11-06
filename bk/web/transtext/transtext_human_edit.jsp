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
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;人工翻译</td>
		</tr>
		<tr>
			<td class="tdgray" width="10%" colspan="2">订单编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.orderid }</td>
			<td class="tdgray" width="10%">用户编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.userid }</td>
			<td class="tdgray" width="10%">翻译模式：</td>
			<td class="tdwhite" width="30%">&nbsp;<dict:text field="transModel" initvalue="${entity.cmcTtTrans.transmodel }"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">创建时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="entity.createtime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">提交时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="entity.waittime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">完成时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="entity.finishtime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">处理状态：</td>
			<td class="tdwhite">&nbsp;<dict:text field="orderDealStatus" initvalue="${entity.dealstatus }"/></td>
			<td class="tdgray">处理人：</td>
			<td class="tdwhite">&nbsp;${entity.opername }</td>
			<td class="tdgray">开始处理时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="entity.opertime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">翻译语言：</td>
			<td class="tdwhite" colspan="5">
				&nbsp;<dict:text field="textLang" initvalue="${entity.cmcTtTrans.fromlang}"/>
				&nbsp;->&nbsp;
				<dict:text field="textLang" initvalue="${entity.cmcTtTrans.tolang}"/>		
			</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">原文：</td>
			<td class="tdwhite" colspan="5">
				&nbsp;
				${entity.cmcTtTrans.src}
				&nbsp;&nbsp;
				<s:if test="entity.cmcTtTrans.voice!=null && entity.cmcTtTrans.voice!=''">
					<a href="javascript:playSound('${entity.cmcTtTrans.voice}');"><img id="playVoice" style="vertical-align:middle;" src="<lt:contextPath/>/images/play.png"></a>
				</s:if>
			</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">译文：</td>
			<td class="tdwhite" colspan="5">
				<textarea id="dst" style="width:99%;height:100px;margin: 3px;">${entity.cmcTtTrans.dst}</textarea>
				<div align="center" style="margin:5px;">
					<input type="hidden" id="orderId" value="${entity.orderid }">
					<form name="editForm" action="#" id="editForm">
						<s:if test="canStart">
							<input type="button" class="btn2" value="开始翻译" onClick="startTrans();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<s:if test="canTrans">
							<input type="button" id="btnSave" class="btn2" value="保存" onClick="saveData();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<s:if test="canFinish">
							<input type="button" id="btnFinish" class="btn2" value="保存&完成" onClick="finish();" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<s:if test="canCancel">
							<input type="button" class="btn2" value="取消&退款" onClick="showCancel();" />
							<div id="cancelDiv" style="position: absolute;width:500px;display: none;background-color: #ffffff">
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableHeader">
								<tr>
									<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">取消翻译并退款用户</td>
								</tr>
								<tr>
									<td width="30" align="right" class="textrse12">*</td>
									<td width="100">取消原因：</td>
									<td><input type="text" id="cancelReason" style="width: 300px;"></td>
								</tr>
								<tr>
									<td></td>
									<td>备选原因：</td>
									<td>
										<select id="selCancelReason" onchange="selectCancelReason();">
											<option value="">--请选择--</option>							
											<option value="感谢您使用出国翻译官。重复提交，请您重新确认，期待您的再次使用。">感谢您使用出国翻译官。重复提交，请您重新确认，期待您的再次使用。</option>
											<option value="感谢您使用出国翻译官。暂不支持该语种的中文翻译，期待您的再次使用。">感谢您使用出国翻译官。暂不支持该语种的中文翻译，期待您的再次使用。</option>
										</select>
									</td>
								</tr>
								<tr>
									<td colspan="3" align="center" height="40">
										<input type="button" class="btn2" value="确定取消" onClick="saveCancel();" />
										<input type="button" class="btn2" value="关闭" onClick="hideCancel();" />
									</td>
								</tr>
								</table>
							</div>
							&nbsp;&nbsp;&nbsp;&nbsp;
						</s:if>
						<input type="button" class="btn2" value="返回列表" onClick="backList();" />				
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
				<s:if test="!entity.cmcRMsgs.isEmpty()">
					<ul style="list-style:none;margin: 0;padding: 0">
					<s:iterator value="entity.cmcRMsgs" status="sts" id="l">
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
				<s:iterator value="entity.cmcRLogs" status="sts" id="l">
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
