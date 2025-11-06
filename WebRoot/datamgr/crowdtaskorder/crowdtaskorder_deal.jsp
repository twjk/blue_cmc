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
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;众包任务订单处理</td>
		</tr>
		<tr>
			<td class="tdgray" width="10%" colspan="2">订单编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${order.orderid }</td>
			<td class="tdgray" width="10%">用户编号：</td>
			<td class="tdwhite" width="20%">&nbsp;${order.userid }</td>
			<td class="tdgray" width="10%">订单类型：</td>
			<td class="tdwhite" width="30%">&nbsp;<dict:text field="orderType" initvalue="${order.ordertype }"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">提交时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="order.waittime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">处理时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="order.opertime" format="yyyy-MM-dd HH:mm:ss" /></td>
			<td class="tdgray">完成时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="order.finishtime" format="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">处理状态：</td>
			<td class="tdwhite">&nbsp;<dict:text field="orderDealStatus" initvalue="${order.dealstatus }"/></td>
			<td class="tdgray">处理人：</td>
			<td id="opername" class="tdwhite<s:if test="other"> markR</s:if>">&nbsp;${order.opername }</td>
			<td class="tdgray">订单金额：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${order.amount}"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">任务单价（元）：</td>
			<td class="tdwhite">&nbsp;<fmt:formatNumber type="currency" pattern="0.00" value="${order.cmcRTask.price}"/></td>
			<td class="tdgray">参与人数：</td>
			<td class="tdwhite">&nbsp;${order.cmcRTask.peoplenum}</td>
			<td class="tdgray">任务天数：</td>
			<td class="tdwhite">&nbsp;${order.cmcRTask.days}</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">任务说明：</td>
			<td class="tdwhite" colspan="5" style="padding:3px;">${order.cmcRTask.content}</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">相关网址：</td>
			<td class="tdwhite" colspan="5" style="padding:3px;">${order.cmcRTask.url}</td>
		</tr>
		<s:if test="order.cmcRTask.picurl!=null && order.cmcRTask.picurl!=''">
		<tr>
			<td class="tdgray" colspan="2">图片：</td>
			<td class="tdwhite" colspan="5" style="padding:3px;">
				<img alt="图片" src="${order.cmcRTask.picurl}" style="max-width: 200px;max-height: 100px;">
				<br/>				
				${order.cmcRTask.picurl}
			</td>
		</tr>
		</s:if>
		<s:if test="order.cmcRTask.cmcCtTask!=null">
		<tr>
			<td class="tdgray" colspan="2">任务编号：</td>
			<td class="tdwhite">&nbsp;${order.cmcRTask.taskid }</td>
			<td class="tdgray">任务名称：</td>
			<td class="tdwhite">&nbsp;${order.cmcRTask.cmcCtTask.title }</td>
			<td class="tdgray">任务进展：</td>
			<td class="tdwhite">
				&nbsp;
				已完成【${order.cmcRTask.cmcCtTask.finishnum }】
				待完成【${order.cmcRTask.cmcCtTask.ingnum }】
				报名【${order.cmcRTask.cmcCtTask.applynum }】
				上限【${order.cmcRTask.cmcCtTask.maxnum }】
			<td>
		</tr>
		</s:if>
		</table>
		<table width="100%" cellspacing="0" border="0" cellpadding="0">
		<tr>
			<td>
				<div align="center" style="margin:8px;">
					<input type="hidden" id="orderId" value="${order.orderid }">
					<input type="hidden" id="orderType" value="${order.ordertype }">
					<s:if test="canStart">
						<input type="button" class="btn2" value="开始处理" onClick="acceptOrder();" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					</s:if>
					<s:if test="canAddTask">
						<input type="button" id="btnFinish" class="btn2" value="创建任务" onClick="addTask();" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					</s:if>
					<s:if test="canFinish">
						<input type="button" id="btnFinish" class="btn2" value="完成" onClick="finishOrder();" />
						&nbsp;&nbsp;&nbsp;&nbsp;
					</s:if>
					<s:if test="canCancel">
						<input type="button" class="btn2" value="拒单&退款" onClick="showCancel();" />
						<div id="cancelDiv" style="position: absolute;left: 25%;width:500px;display: none;background-color: #ffffff">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableHeader">
							<tr>
								<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">取消订单并退款</td>
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
										<option value="系统无法支撑此类逻辑会出现冒领情况，已退款。">系统无法支撑此类逻辑会出现冒领情况，已退款。</option>
										<option value="期待您的再次使用。">期待您的再次使用。</option>
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
					<input type="button" class="btn2" value="返回列表" onClick="backList();" />				
				</div>
			</td>
		</tr>
		</table>
		<form name="editForm" action="#" id="editForm" style="display: none">
			<div style="height:10px"></div>
			<table width="100%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">填写助力任务信息</th>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>任务名称：</td>
				<td><input id="taskTitle" name="bean.taskTitle" value="[新]多多助力<s:property value='order.orderid.substring(15)'/>"/></td>
			</tr>
			<tr>
				<td></td>
				<td>分组代码：</td>
				<td>
					<input id="groupCode" name="bean.groupCode"/>
					<span class="hint">（同一分组的任务，同一用户只能领取一个）</span>
				</td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>参与人数：</td>
				<td><input id="peopleNum" name="bean.peopleNum" value="57"/></td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>题目奖励金额：</td>
				<td>
					<input id="subjectReward" name="bean.subjectReward" value="0.14"/>
					<span class="hint">（元）</span>
				</td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>题目来源：</td>
				<td><dict:select id="selSubjectSource" field="crowdTaskVideoSource" name="bean.subjectSource" initvalue="7"/></td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>题目：</td>
				<td><input id="subjectContent" name="bean.subjectContent" class="input2"/></td>
			</tr>
			<tr>
				<td></td>
				<td>备选题目：</td>
				<td>
					<select id="selSubject" onchange="selOptionSubject();" style="width:400px;">
						<option value="">--请选择--</option>	
						<option value="点击红色按钮会打开“XXX”App，允许粘贴，在弹出的页面进行助力，然后助力之后的真实截图上传（上传真实图片）">点击红色按钮会打开“XXX”App，允许粘贴，在弹出的页面进行助力，然后助力之后的真实截图上传（上传真实图片）</option>
					</select>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>题目链接：</td>
				<td>
					<input id="subjectUrl" name="bean.subjectUrl" value="${order.cmcRTask.url}" class="input2"/>
					<span class="hint">（链接或口令，video）</span>
				</td>
			</tr>
			<s:if test="order.cmcRTask.picurl!=null && order.cmcRTask.picurl!=''">
			<tr>
				<td></td>
				<td>题目图片：</td>
				<td>
					<input id="subjectPicUrl" name="bean.subjectPicUrl" value="${order.cmcRTask.picurl}" class="input2"/>
				</td>
			</tr>
			</s:if>
			<tr>
			 	<td class="required">*</td>
				<td>结束时间：</td>
				<td>
					<input type="text" id="endTime" name="bean.endTime" value="2029-12-31 23:59:59" class="Wdate" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'%y-%M-%d 00:00:00', quickSel:['%y-%M-%d 23:59:59','%y-%M-{%d+1} 23:59:59','%y-{%M+1}-%d 23:59:59','%y-12-31 23:59:59','2029-12-31 23:59:59']})"/>
				</td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>任务描述：</td>
				<td>
					<textarea id="description" name="bean.description" class="textarea4">1、本任务包括1个助力任务，验收符合要求的发放奖励金。
2、按照规则完成助力任务。
3、若有作弊行为将会导致奖励失效，甚至导致帐号被封禁。</textarea>
				</td>
			</tr>
			<tr>
			 	<td align="right"><span class="textrse12">*</span></td>
				<td>排序：</td>
				<td>
					<input id="sortIndex" name="bean.sortIndex" value="1"/>
					<span class="hint">（值越小排序越靠前。天降红包8，助力9，每日一句/一问99）</span>
				</td>
			</tr>
			<tr>
				<td align="right"></td>
				<td colspan="2" align="center" style="padding-top:10px;">
					<input type="hidden" id="orderId" name="bean.orderId" value="${order.orderid }"/>
					<input type="button" class="btn2" value="保存任务" onClick="saveTask();" />
					<input type="button" class="btn2" value="取消" onClick="cancelAddTask();" />
				</td>
			</tr>
		</table>
		</form>
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
		<br/>
	</body>	
</html>
