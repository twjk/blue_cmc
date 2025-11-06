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
				<th style="width:30px"></th>
				<th style="width:15%">用户</th>
				<th style="width:19%">用户任务</th>
				<th style="width:10%">题目/完成数</th>
				<th style="width:10%">任务/到账奖励</th>
				<th style="width:12%">报名/提交/到账时间</th>
				<th style="width:8%">地点</th>
				<th style="width:8%">平台</th>
				<th style="width:8%">任务状态</th>
				<th style="width:10%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="detailData('${utid}')">
				<td>
					<s:if test="%{inBlacklist}">
						<img src="<lt:contextPath/>/images/blacklist.png" title="黑名单用户" style="padding:5px;">
					</s:if>
				</td>
				<td>
					${user.nick }
					<br/>
					${userid}
					<br/>
					<dict:text field="serviceType" initvalue="${servicetype }"/>
				</td>
				<td>${cmcCtTask.title }&lt;${taskid }&gt;<br/>${utid }</td>
				<td>${subjectnum } / ${finishsubjectnum }</td>
				<td>${taskreward } / ${utreward }</td>
				<td>
					<s:date name="%{applytime}" format="yyyy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{finishtime}" format="yyyy-MM-dd HH:mm:ss"/>
					<br/>
					<s:date name="%{utrewardtime}" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${countryname }${cityname }</td>
				<td>${platform }<br/>${version }</td>
				<td><dict:text field="userCrowdTaskStatus" initvalue="${status }"/></td>
				<td>
					<a href="javascript:detailData('${utid}');"><img src="<lt:contextPath/>/images/detail.gif" alt="详情" title="详情"></a>
					<s:if test="@com.qcmz.cmc.util.CrowdTaskUtil@canCancel(status)">
						&nbsp;&nbsp;
						<s:if test="cmcCtTask.taskrewardsettle==2 && finishsubjectnum>0">
							<a href="javascript:finishTask('${utid }');"><img src="<lt:contextPath/>/images/finish.png" alt="强制完结" title="强制完结"></a>
						</s:if>
						<s:else>
							<a href="javascript:showCancel('${utid }','${user.nick }','${cmcCtTask.title }');"><img src="<lt:contextPath/>/images/cancel.png" alt="强制取消" title="强制取消"></a>
						</s:else>
					</s:if>					
					<s:if test="%{inBlacklist}">
						&nbsp;&nbsp;
						<a href="javascript:deleteBlacklist('${userid }');"><img src="<lt:contextPath/>/images/blacklist_delete.png" title="移出黑名单"></a>
					</s:if>
					<s:else>
						&nbsp;&nbsp;
						<a href="javascript:addBlacklist('${userid }','${user.nick }');"><img src="<lt:contextPath/>/images/blacklist_add.png" title="加入黑名单"></a>
					</s:else>
					&nbsp;&nbsp;
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
			<td width="30"></td>
			<td width="100">用户：</td>
			<td width="250"><span id="cancelUser"></span></td>
		</tr>
		<tr>
			<td></td>
			<td>任务：</td>
			<td><span id="cancelTaskName"></span></td>
		</tr>
		<tr>
			<td align="right" class="textrse12">*</td>
			<td>取消原因：</td>
			<td><input type="text" id="cancelReason" value="超时未完成" style="width: 200px;"></td>
		</tr>
		<tr>
			<td colspan="3" align="center" height="40">
				<input id="cancelUtId" type="hidden">
				<input type="button" class="btn2" value="确定取消" onClick="saveCancel();" />
				<input type="button" class="btn2" value="关闭" onClick="hideCancel();" />
			</td>
		</tr>
		</table>
	</div>
	</body>
</html>