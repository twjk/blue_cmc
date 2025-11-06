<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
    <head>
		<title></title>
	</head>
	<body>
	<div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:3%">活动</th>
				<th style="width:7%">任务编号</th>
				<th style="width:30%">任务名称/题库</th>
				<th style="width:6%">题目数</th>
				<th style="width:6%">任务奖励</th>
				<th style="width:15%">有效期</th>
				<th style="width:15%">已完成/待完成/报名/上限</th>
				<th style="width:6%">排序</th>
				<th style="width:6%">状态</th>
				<th style="width:6%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${taskid}')" >
				<td>
					<s:if test="activity!=null">
						<img src="<lt:contextPath/>/images/activity.png" alt="活动" title="${activity.title }">
					</s:if>
				</td>
				<td>
					${taskid }
					<br/>
					<dict:text field="crowdTaskFreq" initvalue="${taskfreq }"/>
				</td>
				<td style="text-align: left">
					${title }<s:if test="groupcode!=null && groupcode!=''">&lt;${groupcode }&gt;</s:if><s:if test="tasktype==1">【<dict:text field="crowdTaskType" initvalue="${tasktype }"/>】</s:if>
					<br/>
					${cmcCtLib.libname }&lt;${cmcCtLib.libid }&gt;
				</td>
				<td>${subjectnum }</td>
				<td>${taskreward }</td>
				<td>
					<s:date name="%{starttime}" format="yyyy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{endtime}" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${finishnum } / ${ingnum } / ${applynum } / ${maxnum }</td>
				<td>${sortindex }</td>
				<%-- 
				<td>
					<dict:text field="auto" initvalue="${autoverify }" dataSource="xml" table="common"/> 
					<br/>
					${verifyreward }
				</td>
				<td>
					<dict:text field="auto" initvalue="${autoqc }" dataSource="xml" table="common"/> 
					<br/> 
					${qcreward }
				</td>
				--%>
				<td>
					<a href="javascript:updateStatus('${taskid }','${status==0?1:0 }','${taskfreq }','${parenttaskid }');" title="点击${status==1?'停用':'启用' }">
						<dict:text table="common" field="status" initvalue="${status}" dataSource="xml"/>
					</a>
				</td>
				<td>
					<a href="javascript:editData('${taskid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="编辑" title="编辑"></a>
					&nbsp;&nbsp;
					<s:if test="@com.qcmz.cmc.constant.DictConstant@DICT_CROWDTASK_TASKTYPE_PRETASK!=tasktype">
						<a href="javascript:showCancel('${taskid }','${title }');"><img src="<lt:contextPath/>/images/cancel.png" alt="取消未完成用户任务" title="取消未完成用户任务"></a>
					</s:if>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	<div id="cancelDiv" style="position: absolute;width:500px;display: none;background-color: #ffffff">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableHeader">
		<tr>
			<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">取消未完成的用户任务</td>
		</tr>
		<tr>
			<td width="30" align="right" class="textrse12">*</td>
			<td width="100">任务名称：</td>
			<td width="250"><span id="cancelTaskName"></span></td>
		</tr>
		<tr>
			<td></td>
			<td>用户报名时间：</td>
			<td>
				<select id="daysAgo" style="width:200px;">
					<option value="" selected="selected">所有</option>							
					<option value="1">1天前</option>
					<option value="3">3天前</option>
					<option value="10">10天前</option>
				</select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>取消原因：</td>
			<td><input type="text" id="cancelReason" value="超时未完成" style="width: 200px;"></td>
		</tr>
		<tr>
			<td colspan="3" align="center" height="40">
				<input id="cancelTaskId" type="hidden">
				<input id="btnCancel" type="button" class="btn2" value="确定取消" onClick="saveCancel();" />
				<input type="button" class="btn2" value="关闭" onClick="hideCancel();" />
			</td>
		</tr>
		</table>
	</div>
	</body>
</html>