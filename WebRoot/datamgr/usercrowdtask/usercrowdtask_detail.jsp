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
			<td colspan="6" align="left" class="text13blackb">&nbsp;&nbsp;用户任务信息</td>
		</tr>	
		<tr>
			<td class="tdgray" width="10%" colspan="2">任务名称：</td>
			<td class="tdwhite" width="20%">&nbsp;${entity.cmcCtTask.title }</td>
			<td class="tdgray" width="10%">截止日期：</td>
			<td class="tdwhite" width="20%">&nbsp;<s:date name="%{entity.cmcCtTask.endtime}" format="yyyy-MM-dd"/></td>
			<td class="tdgray" width="10%">任务奖励：</td>
			<td class="tdwhite" width="30%">&nbsp;${entity.cmcCtTask.taskreward }${entity.cmcCtTask.unitname }</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">用户：</td>
			<td class="tdwhite">&nbsp;${entity.user.nick }（${entity.userid}）</td>
			<td class="tdgray">用户任务编号：</td>
			<td class="tdwhite">&nbsp;${entity.utid }</td>
			<td class="tdgray">用户任务状态：</td>
			<td class="tdwhite">&nbsp;<dict:text field="userCrowdTaskStatus" initvalue="${entity.status }"/></td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">地点：</td>
			<td class="tdwhite">&nbsp;${entity.countryname }${entity.cityname }</td>
			<td class="tdgray">平台：</td>
			<td class="tdwhite">&nbsp;${entity.platform } ${entity.version }</td>
			<td class="tdgray">用户设备标识：</td>
			<td class="tdwhite">&nbsp;${entity.uuid }</td>
		</tr>
		<tr>
			<td class="tdgray" colspan="2">报名时间：</td>
			<td class="tdwhite">
				&nbsp;<s:date name="%{entity.applytime}" format="yyyy-MM-dd HH:mm:ss"/>
				
			</td>
			<td class="tdgray">提交时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="%{entity.finishtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
			<td class="tdgray">用户获得奖励：</td>
			<td class="tdwhite">&nbsp;
				<span class="markR">${entity.utreward }</span>
				（<s:date name="%{entity.utrewardtime}" format="yyyy-MM-dd HH:mm:ss"/>）
			</td>
		</tr>
		<s:if test="entity.verifystatus!=null">
		<tr>
			<td class="tdgray" colspan="2">审核人：</td>
			<td class="tdwhite">&nbsp;${entity.verifyUser.nick }（${entity.verifyuserid}）</td>
			<td class="tdgray">审核状态/时间：</td>
			<td class="tdwhite">
				&nbsp;<dict:text field="userCrowdTaskVerifyStatus" initvalue="${entity.verifystatus }"/>
				/
				<s:date name="%{entity.verifyfinishtime}" format="yyyy-MM-dd HH:mm:ss"/>
				<s:if test="entity.refusereason!=null && entity.refusereason!=''">
					/ ${entity.refusereason }
				</s:if>
			</td>
			<td class="tdgray">审核奖励/时间：</td>
			<td class="tdwhite">
				&nbsp;${entity.verifyreward} / <s:date name="%{entity.verifyrewardtime}" format="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
		</s:if>
		<s:if test="entity.qcstatus!=null">
		<tr>
			<td class="tdgray" colspan="2">审校人：</td>
			<td class="tdwhite">&nbsp;${entity.qcUser.nick }（${entity.qcuserid}）</td>
			<td class="tdgray">审校状态/完成时间：</td>
			<td class="tdwhite">
				&nbsp;<dict:text field="userCrowdTaskQcStatus" initvalue="${entity.qcstatus }"/>
				/
				&nbsp;<s:date name="%{entity.qcfinishtime}" format="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td class="tdgray">审校奖励/到账时间：</td>
			<td class="tdwhite">
				&nbsp;${entity.qcreward} / <s:date name="%{entity.qcrewardtime}" format="yyyy-MM-dd HH:mm:ss"/>				
			</td>
		</tr>
		</s:if>
		<s:if test="@com.qcmz.cmc.constant.DictConstant@DICT_USERCROWDTASK_STATUS_CANCEL==entity.status">
		<tr>
			<td class="tdgray" colspan="2">取消人：</td>
			<td class="tdwhite">&nbsp;${entity.cancelname}（${entity.cancelcd}）</td>
			<td class="tdgray">取消时间：</td>
			<td class="tdwhite">&nbsp;<s:date name="%{entity.canceltime}" format="yyyy-MM-dd HH:mm:ss"/></td>
			<td class="tdgray">取消原因：</td>
			<td class="tdwhite">&nbsp;${entity.cancelreason}</td>
		</tr>
		</s:if>
		</table>

		<div style="width:100%;text-align: center;padding-top:10px;padding-bottom: 10px;">
			<input type="button" class="btn2" value="返回列表" onClick="backList();" />
		</div>
		
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0" style="width: 100%">
		<tr>
			<th style="width:6%">序号</th>
			<th style="width:28%">题目</th>
			<th style="width:12%">答题时间</th>
			<th style="width:26%">答案（得分,编号）</th>
			<th style="width:8%">综合得分</th>
			<th style="width:8%">题目奖励</th>
			<th style="width:12%">到账时间</th>
		</tr> 
		<s:iterator value="entity.userSubjects" id="userSubject" status="st">
		<tr>
			<td>${st.index+1 }</td>
			<td>${userSubject.cmcCtSubject.content }</td>
			<td><s:date name="%{answertime}" format="yyyy-MM-dd HH:mm:ss"/></td>
			<td>
				<ul style="margin: 5;padding: 0;line-height: 20px;list-style: none;">
					<s:iterator value="userAnswers" id="userAnswer" status="st">
					<li>
						${userAnswer.optionvalue}
						<s:if test="voice!=null&&voice!=''">
							<img onclick="playAudio('${voice}', this);" src="<lt:contextPath/>/images/play.png" title="播放" style="vertical-align:middle;cursor: pointer;">
						</s:if>
						<s:if test="pic!=null&&pic!=''">
							<a href="${pic }" target="_blank"><img src="${pic }" style="width:50px;vertical-align:middle;cursor: pointer;margin:3px;"></a>
						</s:if>
						（${userAnswer.score }分，${userAnswer.uaid }）
					</li>
					</s:iterator>
				</ul>
			</td>
			<td>${userSubject.compositescore}</td>
			<td>
				<s:if test="usreward>0">
					${userSubject.usreward}
				</s:if>
				<s:else>
					<span class="markR">${userSubject.usreward}</span>
				</s:else>
			</td>
			<td><s:date name="%{usrewardtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		</s:iterator>
		</table>
		<br/>
	</body>
</html>
