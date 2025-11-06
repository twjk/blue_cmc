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
				 	<td align="right"></td>
					<td>分类：</td>
					<td id="selCat"><s:hidden id="currCat" name="entity.fullcatid" /></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td width="10%">名称：</td>
					<td><s:textfield id="title" name="entity.title" maxlength="50"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>姓名：</td>
					<td><s:textfield id="fullname" name="entity.fullname" maxlength="50"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>性别：</td>
					<td><dict:select name="entity.gender" field="sex" inithead="0" initvalue="${entity.gender}"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>头像：</td>
					<td>
						<s:textfield id="icon" name="entity.icon" cssClass="input2" onchange="changeSrc(this);"/>
						<img id="selIcon" src="${entity.icon } "
							onclick="insertSinglePic(this.id);"  
							alt="点击换图" title="点击换图"
							style="vertical-align:middle;cursor: pointer;max-height: 40px">
						<script id="singleEditor" type="text/plain" style="display:none"/>
						<script type="text/javascript">
							var singleEditor = new InsertImage('singleEditor', 'dubber', callback_insertSinglePic);
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
				 	<td align="right"></td>
					<td>特长：</td>
					<td><s:textarea id="specialty" name="entity.specialty" cssClass="textarea2"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>风格：</td>
					<td><s:textarea id="style" name="entity.style" cssClass="textarea2"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>试听语音：</td>
					<td>
						<s:textfield id="audition" name="entity.audition" cssClass="input2"/>
						<a href="javascript:uploadFile('audition');">上传语音</a>
						<script id="uploadEditor" type="text/plain" style="display:none"></script>
						<script type="text/javascript">	
							var uploadEditor = new UploadFile('uploadEditor', 'dubber', callback_uploadFile);
							function uploadFile(id){
								uploadEditor.uploadFile(id);
							}	
							function callback_uploadFile(t, result, actionData) {
								$("#"+actionData).val(result[0].url);
							}
						</script>
						<img onclick="playAudioById('audition', this);" src="<lt:contextPath/>/images/play.png" title="播放" style="vertical-align:middle;cursor: pointer;">
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>排序：</td>
					<td>
						<input id="sortindex" name="entity.sortindex" value="${entity==null?99:entity.sortindex}"/>
						<span class="hint">（值越小排序越靠前）</span>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>状态：</td>
					<td><dict:radio dataSource="xml" table="common" field="status" name="entity.status" initvalue="${entity==null?1:entity.status}" cssClass="raddefault"/></td>
				</tr>
				<tr height="30">
					<td colspan="3" align="center">
						<s:hidden id="dubberid" name="entity.dubberid"/>
						<input type="button" class="btn2" value="保存" onClick="saveData();" />
						<input type="button" class="btn2" value="取消" onClick="backList();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
