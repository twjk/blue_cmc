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
			<table width="100%" cellpadding="0" cellspacing="0" class="tableHeader" border="0" align="center">
				<tr>
				    <td width="2%">&nbsp;</td>
					<td colspan="2" align="left" class="text13blackb">填写信息</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>标题：</td>
					<td><s:textfield id="title" name="entity.title" maxlength="200"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td width="10%">源语言：</td>
					<td><dict:select name="entity.fromlang" field="speechLang" inithead="0" initvalue="${entity==null?'zh-cn':entity.fromlang}"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>原文：</td>
					<td><s:textarea id="src" name="entity.src" cssClass="textarea2"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>目标语言：</td>
					<td><dict:select name="entity.tolang" field="speechLang" inithead="0" initvalue="${entity.tolang}"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>译文：</td>
					<td><s:textarea id="dst" name="entity.dst" cssClass="textarea2"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>小图（180*140）：</td>
					<td>
						<s:textfield id="smallpic" name="entity.smallpic" cssClass="input2" onchange="changeSrc(this);"/>
						<img id="selSmallPic" src="${entity.smallpic } "
							onclick="insertSinglePic(this.id);"  
							alt="点击换图" title="点击换图"
							style="vertical-align:middle;cursor: pointer;max-height: 40px">
						<script id="singleEditor" type="text/plain" style="display:none"/>
						<script type="text/javascript">
							var singleEditor = new InsertImage('singleEditor', 'daysentence', callback_insertSinglePic);
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
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>大图（690*640）：<br/></td>
					<td style="padding-top:2px;">
						<s:textfield id="pic" name="entity.pic" cssClass="input2" onchange="changeSrc(this);"/>
						<img id="selPic" src="${entity.pic } "
							onclick="insertSinglePic(this.id);"  
							alt="点击更换图片" title="点击更换图片"
							style="vertical-align:middle;cursor: pointer;max-height:100px;">
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>来源：</td>
					<td><s:textfield id="source" name="entity.source"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>发布时间：</td>
					<td>
						<input type="text" id="releasetime" name="entity.releasetime" value="<s:date name='entity.releasetime' format='yyyy-MM-dd HH:mm:ss'/>" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
						<span class="hint">（每日每个源语言一条）</span>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>状态：</td>
					<td><dict:radio dataSource="xml" table="common" field="status" name="entity.status" initvalue="${entity==null?0:entity.status}" cssClass="raddefault"/></td>
				</tr>
				<tr>
				 	<td></td>
					<td>静态页面：</td>
					<td class="chkdefault">
						<s:if test="entity.htmlurl==null">暂无</s:if>
						<s:else><a href="${entity.htmlurl }" target="_blank">查看</a></s:else>
					</td>
				</tr>
				<tr height="30">
					<td colspan="3" align="center">
						<s:hidden id="sentid" name="entity.sentid"/>
						<input type="button" class="btn2" value="保存" onClick="saveData();" />
						<input type="button" class="btn2" value="取消" onClick="backList();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
