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
			 	<td></td>
				<td width="10%">订单号：</td>
				<td>
					<input id="orderId" name="bean.orderId"/>
					<span class="hint">（先到“众包任务订单处理”中开始处理）</span>
				</td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>任务名称：</td>
				<td>
					<input id="taskTitle" name="bean.taskTitle" value=""/>
					<span class="hint">（XX助力）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>题库名称：</td>
				<td>
					<input id="libName" name="bean.libName"/>
					<span class="hint">（默认为"任务名称-订单号"）</span>
				</td>
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
				<td>题目分类：</td>
				<td>
					<select id="subjectCat" name="bean.subjectCat">
						<option value="8" selected="selected">助力</option>
						<option value="9">添加剂采集</option>
					</select>
				</td>
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
					<span class="hint">（选择后会覆盖题目输入框里的内容）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>题目来源：</td>
				<td>
					<dict:select id="selSubjectSource" field="crowdTaskVideoSource" name="bean.subjectSource" inithead="0"/>
					<span class="hint">（videoSource，助力必选）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>题目链接：</td>
				<td>
					<input id="subjectUrl" name="bean.subjectUrl" class="input2"/>
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
					<input id="subjectPicUrl" name="bean.subjectPicUrl" class="input2"/>
					<img id="selSubjectPic" src=""
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
			<tr>
				<td></td>
				<td>限制用户：</td>
				<td>
					<input id="limitUserId" name="bean.limitUserId" class="input2"/>
					<span class="hint">（多个用,分隔，常用：11000027,11000033）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>限制业务：</td>
				<td>
					<dict:check field="serviceType" cssClass="chkdefault" name="bean.limitServiceType"/>
					<span class="hint">（查添加剂APP只获取"查添加剂"业务的任务）</span>
				</td>
			</tr>
			<tr>
			 	<td class="required">*</td>
				<td>有效期：</td>
				<td>
					<input type="text" id="startTime" name="bean.startTime" class="Wdate"
						value="<s:date name='bean.startTime' format='yyyy-MM-dd HH:mm:ss'/>" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate:'#F{$dp.$D(\'endTime\')}', quickSel:['%y-%M-%d 00:00:00','%y-%M-{%d+1} 00:00:00']})"/>
					&nbsp;至&nbsp;
					<input type="text" id="endTime" name="bean.endTime" value="2029-12-31 23:59:59" class="Wdate" 
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'startTime\')}', quickSel:['%y-%M-%d 23:59:59','%y-%M-{%d+1} 23:59:59','%y-{%M+1}-%d 23:59:59','%y-12-31 23:59:59','2029-12-31 23:59:59']})"/>
				</td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>任务描述：</td>
				<td>
					<textarea id="description" name="bean.description" class="textarea5">1、本任务包括1个助力任务，验收符合要求的发放积分。
2、按照规则完成助力任务。
3、若有作弊行为将会导致奖励失效，甚至导致帐号被封禁。</textarea>
				</td>
			</tr>
			<tr>
			 	<td align="right"><span class="textrse12">*</span></td>
				<td>排序：</td>
				<td>
					<input id="sortIndex" name="bean.sortIndex" value="9"/>
					<span class="hint">（值越小排序越靠前，目前最小值为${minSortIndex }。天降红包8，助力9，每日一句/一问99）</span>
				</td>
			</tr>
			<tr>
			 	<td></td>
				<td>特定奖励活动：</td>
				<td>
					<s:hidden id="actid" name="bean.actId"/>
					<span id="actTitle">无</span>
					&nbsp;&nbsp;
					<a href="javascript:selActivity();">选择活动</a>
					&nbsp;&nbsp;
					<a href="javascript:cancelActivity();">取消活动</a>
				</td>
			</tr>			
			<tr>
				<td align="right"></td>
				<td colspan="2" align="center" style="padding-top:10px;">
					<input type="button" class="btn2" value="保存" onClick="saveData();" />
					<input type="button" class="btn2" value="取消" onClick="backList();" />
					<ul class="hintUL" style="text-align: left;" >
						<li>拼多多助力：30人/0.3元</li>
						<li>应用推荐助力：1000人/0.1元/通用网址</li>
					</ul>
				</td>
			</tr>
		</table>
		</form>
	</body>
</html>
