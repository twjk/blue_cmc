<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>填写信息</title>
	</head>
	<body>
		<form name="editForm" action="#" id="editForm">
			<div style="height:10px"></div>
			<table width="100%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">填写任务信息(目前只支持助力/添加剂采集任务)</th>
			</tr>
			<tr>
				<td class="required">*</td>
				<td width="10%">任务名称：</td>
				<td><s:textfield id="taskTitle" name="editBean.taskTitle"/></td>
			</tr>
			<s:if test="editBean.orderId!=null && editBean.orderId!=''">
			<tr>
				<td></td>
				<td>订单号：</td>
				<td>${editBean.orderId }</td>
			</tr>
			</s:if>
			<tr>
				<td></td>
				<td>分组代码：</td>
				<td>
					<s:textfield id="groupCode" name="editBean.groupCode"/>
					<span class="hint">（同一分组的任务，同一用户只能领取一个）</span>
				</td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>参与人数：</td>
				<td>
					<s:textfield id="peopleNum" name="editBean.peopleNum"/>
					<span class="hint">（不要小于已报名人数${editBean.applyNum }）</span>
				</td>
			</tr>
			<s:if test="editBean.subjectId!=null">
			<tr>
				<td class="required">*</td>
				<td>题目奖励金额：</td>
				<td>
					<s:textfield id="subjectReward" name="editBean.subjectReward"/>
					<span class="hint">（元）</span>
				</td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>题目：</td>
				<td>
					<s:textfield id="subjectContent" name="editBean.subjectContent" class="input2"/>
					
				</td>
			</tr>
			<tr>
				<td></td>
				<td>备选题目：</td>
				<td>
					<select id="selSubject" onchange="selOptionSubject();" style="width:400px;">
						<option value="">--请选择--</option>	
						<option value="点击红色按钮会打开“XXX”App，允许粘贴，在弹出的页面进行助力，然后助力之后的真实截图上传（上传真实图片）">点击红色按钮会打开“XXX”App，允许粘贴，在弹出的页面进行助力，然后助力之后的真实截图上传（上传真实图片）</option>
					</select>
					<span class="hint">（选择后会覆盖题目输入框里的内容）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>题目来源：</td>
				<td>
					<dict:select id="selSubjectSource" field="crowdTaskVideoSource" name="editBean.subjectSource" initvalue="${editBean.subjectSource }" inithead="0"/>
					<span class="hint">（videoSource）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>题目链接：</td>
				<td>
					<s:textfield id="subjectUrl" name="editBean.subjectUrl" class="input2"/>
					<a href="javascript:pasteSubjectUrl();">从粘贴板替换</a>
					&nbsp;
					<a href="javascript:clearSubjectUrl();">清除</a>
					<span class="hint">（链接或口令，video）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>题目图片：</td>
				<td>
					<s:textfield id="subjectPicUrl" name="editBean.subjectPicUrl" cssClass="input2"/>
					<img id="selSubjectPic" src="${editBean.subjectPicUrl }"
							onclick="insertSinglePic(this.id);"  
							alt="点击换图" title="点击换图"
							style="vertical-align:middle;cursor: pointer;max-height: 40px">
					<script id="singleEditor" type="text/plain" style="display:none"/>
					<script type="text/javascript">
						var singleEditor = new InsertImage('singleEditor', 'crowdtasksubject', callback_insertSinglePic);
						function insertSinglePic(id){
							singleEditor.insertImage(id);
						}	
						function callback_insertSinglePic(t, result, actionData) {
							$("#"+actionData).attr("src", result[0].src);
							$("#"+actionData).prev("input").val(result[0].src);
						}
						function changeSrc(obj){
							$(obj).next().attr("src", $(obj).val());
						}
					</script>
				</td>
			</tr>
			</s:if>
			<s:else>
			<tr>
				<td></td>
				<td>题目：</td>
				<td>该任务暂不支持修改题目相关信息</td>
			</tr>
			</s:else>
			<tr>
				<td></td>
				<td>限制用户：</td>
				<td>
					<s:textfield id="limitUserId" name="editBean.limitUserId" class="input2"/>
					<span class="hint">（多个用,分隔，常用：11000027,11000033）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>限制业务：</td>
				<td>
					<dict:check field="serviceType" cssClass="chkdefault" name="editBean.limitServiceType" initvalue="${editBean.limitServiceTypeStr }"/>
					<span class="hint">（查添加剂APP只获取"查添加剂"业务的任务）</span>
				</td>
			</tr>
			<tr>
			 	<td class="required">*</td>
				<td>有效期：</td>
				<td>
					<input type="text" id="startTime" name="editBean.startTime" class="Wdate"
						value="<s:date name='editBean.startTime' format='yyyy-MM-dd HH:mm:ss'/>" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\'endTime\')}', quickSel:['%y-%M-%d 00:00:00','%y-%M-{%d+1} 00:00:00']})"/>
					&nbsp;至&nbsp;
					<input type="text" id="endTime" name="editBean.endTime" class="Wdate"
						value="<s:date name='editBean.endTime' format='yyyy-MM-dd HH:mm:ss'/>" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'startTime\')}', quickSel:['%y-%M-%d 23:59:59','%y-%M-{%d+1} 23:59:59','%y-{%M+1}-%d 23:59:59','%y-12-31 23:59:59','2029-12-31 23:59:59']})"/>
				</td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>任务描述：</td>
				<td>
					<s:textarea id="description" name="editBean.description" cssClass="textarea5"></s:textarea>
				</td>
			</tr>
			<tr>
			 	<td align="right"><span class="textrse12">*</span></td>
				<td>排序：</td>
				<td>
					<input id="sortIndex" name="editBean.sortIndex" value="${editBean.sortIndex}"/>
					<span class="hint">（值越小排序越靠前，目前最小值为${minSortIndex }。天降红包8，助力9，每日一句/一问99）</span>
				</td>
			</tr>
			<tr>
			 	<td></td>
				<td>特定奖励活动：</td>
				<td>
					<s:hidden id="actId" name="editBean.actId"/>
					<span id="actTitle">
						<s:if test="editBean.activity!=null">
							<s:if test="editBean.activity.status==0">
							（<dict:text dataSource="xml" table="common" field="status" initvalue="${editBean.activity.status }"/>）
							</s:if>
							${editBean.activity.title }
						</s:if>
						<s:else>无</s:else>
					</span>
					&nbsp;&nbsp;
					<a href="javascript:selActivity();">选择活动</a>
					&nbsp;&nbsp;
					<a href="javascript:cancelActivity();">取消活动</a>
				</td>
			</tr>
			<tr>
				<td align="right"></td>
				<td colspan="2" align="center" style="padding-top:10px;">
					<s:hidden id="taskId" name="editBean.taskId"/>
					<s:hidden id="subjectId" name="editBean.subjectId"/>
					<input type="button" class="btn2" value="保存" onClick="updateData();" />
					<input type="button" class="btn2" value="取消" onClick="backList();" />
				</td>
			</tr>
		</table>
		</form>
	</body>
</html>
