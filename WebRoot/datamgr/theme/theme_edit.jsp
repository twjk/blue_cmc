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
					<td width="10%">中文名称：</td>
					<td><s:textfield id="titlecn" name="entity.titlecn"/></td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>当地名称：</td>
					<td><s:textfield id="titlelc" name="entity.titlelc"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>代码：</td>
					<td><s:textfield id="themecode" name="entity.themecode"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>图片：</td>
					<td>
						<s:hidden id="pic" name="entity.pic"/>
						<img id="selPic" src="${entity.pic }"
							onclick="insertSinglePic(this.id);"
							alt="点击换图" title="点击换图"
							style="margin-top: 10px; margin-bottom: 10px;cursor: pointer;max-height: 120px">
						<script id="singleEditor" type="text/plain" style="display:none"/>
						<script type="text/javascript">
							var singleEditor = new InsertImage('singleEditor', 'theme', callback_insertSinglePic);
							function insertSinglePic(id){
								singleEditor.insertImage(id);
							}	
							function callback_insertSinglePic(t, result, actionData) {
								$("#"+actionData).attr("src", result[0].src);
								$("#"+actionData).prev("input").val(result[0].src);
							}
						</script>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>背景图片：</td>
					<td>
						<s:hidden id="bgpic" name="entity.bgpic"/>
						<img id="selBgpic" src="${entity.bgpic }"
							onclick="insertSinglePic(this.id);" 
							alt="点击更换背景图片" title="点击更换背景图片"
							style="margin-top: 10px; margin-bottom: 10px;cursor: pointer;max-height: 200px">
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>离线包：</td>
					<td>
						<s:textfield id="downloadurl" name="entity.downloadurl" cssClass="input2"/>
						<input type="button" class="btn2" value="浏览" onclick="selectFile('downloadurl')">
					</td>
				</tr>
				<tr>
				 	<td align="right"></td>
					<td>离线包大小：</td>
					<td><s:textfield id="filesize" name="entity.filesize"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>状态：</td>
					<td><dict:radio dataSource="xml" table="common" field="status" name="entity.status" initvalue="${entity==null?1:entity.status}" cssClass="raddefault"/></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse14">*</span></td>
					<td>排序：</td>
					<td>
						<input id="sortindex" name="entity.sortindex" value="${entity==null?99:entity.sortindex}"/>
						<span class="hint">（值越小排序越靠前）</span>
					</td>
				</tr>
				<tr height="30">
					<td colspan="3" align="center">
						<s:hidden id="themeid" name="entity.themeid"/>
						<input type="button" class="btn2" value="保存" onClick="saveData();" />
						<input type="button" class="btn2" value="取消" onClick="backList();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
