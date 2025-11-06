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
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;短文快译</td>
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
			<td id="opername" class="tdwhite<s:if test="other"> markR</s:if>">&nbsp;${order.opername }</td>
			<td class="tdgray">开始处理时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="order.opertime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">翻译语言：</td>
			<td class="tdwhite">
				<s:if test="canTrans||canFinish">
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
			<td class="tdgray">预计翻译时长：</td>
			<td class="tdwhite">&nbsp;
				<s:if test="canFinish">
					<dict:select id="needTime" field="orderNeedTime" initvalue="${order.needtime }" inithead="0"/>
				</s:if>
				<s:else>${order.needTimeStr}
				</s:else>
			</td>
			<td class="tdgray">实际翻译时长：</td>
			<td class="tdwhite">&nbsp;${order.dealTimeStr }</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">原文：</td>
			<td class="tdwhite" colspan="5" style="padding: 3px;">
				${order.cmcTtTrans.src}
				&nbsp;&nbsp;
				<s:if test="order.cmcTtTrans.voice!=null && order.cmcTtTrans.voice!=''">
					<img onclick="playAudio('${order.cmcTtTrans.voice}', this);" src="<lt:contextPath/>/images/play.png" title="播放" style="vertical-align:middle;cursor: pointer;">
				</s:if>
			</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">译文：</td>
			<td class="tdwhite" colspan="5" style="padding: 3px;">
				<s:if test="canTrans||canFinish">
					<textarea id="dst" style="width:99%;height:100px;margin: 3px;">${order.cmcTtTrans.dst}</textarea>
				</s:if>
				<s:else>
					${order.cmcTtTrans.dst}
				</s:else>
				<div align="center" style="margin:5px;">
					<input type="hidden" id="orderId" value="${order.orderid }">
					<input type="hidden" id="orderType" value="${order.ordertype }">
					<s:if test="canStart">
						<input type="button" class="btn2" value="开始翻译" onClick="startTrans();" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					</s:if>
					<s:if test="canTrans">
						<input type="button" id="btnSave" class="btn2" value="保存" onClick="saveText();" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					</s:if>
					<s:if test="canFinish">
						<input type="button" id="btnFinish" class="btn2" value="保存&完成" onClick="finishText();" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					</s:if>
					<s:if test="canCancel">
						<input type="button" class="btn2" value="拒单&退款" onClick="showCancel();" />
						<div id="cancelDiv" style="position: absolute;width:500px;display: none;background-color: #ffffff">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableHeader">
							<tr>
								<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">取消翻译并退款用户</td>
							</tr>
							<tr>
								<td width="30" align="right" class="textrse12">*</td>
								<td width="100">拒单原因：</td>
								<td width="350"><input type="text" id="cancelReason" style="width: 340px;"></td>
							</tr>
							<tr>
								<td></td>
								<td>备选原因：</td>
								<td>
									<select id="selCancelReason" onchange="selectCancelReason();" style="width:340px;">
										<option value="">--请选择--</option>							
										<option value="感谢您使用出国翻译官。重复提交，请您重新确认，期待您的再次使用。">感谢您使用出国翻译官。重复提交，请您重新确认，期待您的再次使用。</option>
										<option value="感谢您使用出国翻译官。暂不支持该语种的中文翻译，期待您的再次使用。">感谢您使用出国翻译官。暂不支持该语种的中文翻译，期待您的再次使用。</option>
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="3" align="center" height="40">
									<input type="button" class="btn2" value="确定拒单" onClick="saveCancel();" />
									<input type="button" class="btn2" value="关闭" onClick="hideCancel();" />
								</td>
							</tr>
							</table>
						</div>
						&nbsp;&nbsp;&nbsp;&nbsp;
					</s:if>
					<s:if test="canHandover">
						<input type="button" id="btnHandover" class="btn2" value="转单" onClick="handover();" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					</s:if>
					<input type="button" class="btn2" value="返回列表" onClick="backList();" />				
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
			<td width="28%" style="text-align: center;padding:10px;" valign="top">
				<textarea id="msg" style="width:100%;height:100px;"></textarea>
				<p><input type="button" class="btn8" value="给客户留言" onClick="saveMsg();"/></p>
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
