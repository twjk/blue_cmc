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
					<td width="10%">标题：</td>
					<td>
						<s:textfield id="title" name="entity.title" maxlength="100" cssClass="input2"/>
						&nbsp;
						<a href="${entity.link }" target="_blank">访问</a>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>分类：</td>
					<td id="selCat"><s:hidden id="currCat" name="entity.fullcatid" /></td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>排序：</td>
					<td>
						<input id="sortindex" name="entity.sortindex" value="${entity==null?99:entity.sortindex}" maxlength="5"/>
						<span class="hint">（ 0至99999，默认999，值越小排序越靠前）</span>
					</td>
				</tr>
				<tr>
				 	<td align="right"><span class="textrse12">*</span></td>
					<td>状态：</td>
					<td><dict:radio dataSource="xml" table="common" field="status" name="entity.status" initvalue="${entity==null?1:entity.status}" cssClass="raddefault"/></td>
				</tr>
				<tr>
				 	<td></td>
					<td>特定奖励：</td>
					<td>
						<s:hidden id="actid" name="entity.actid"/>
						<span id="actTitle">
							<s:if test="entity.activity!=null">
								<s:if test="entity.activity.status==0">
								（<dict:text dataSource="xml" table="common" field="status" initvalue="${entity.activity.status }"/>）
								</s:if>
								${entity.activity.title }
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
				 	<td></td>
					<td>来源：</td>
					<td>${entity.cmcLtSource.sourcename }</td>
				</tr>
				<tr>
				 	<td></td>
					<td>城市：</td>
					<td>${entity.cityname }</td>
				</tr>
				<tr>
				 	<td></td>
					<td>发布时间：</td>
					<td><s:date name="%{entity.	publishtime}" format="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<%-- 
				<tr>
				 	<td></td>
					<td>图片：</td>
					<td>
						<s:iterator value="entity.pics">
							<img src="${picurl }" style="max-width: 200px;">&nbsp;&nbsp;
						</s:iterator>
					</td>
				</tr>
				--%>
				<tr height="30">
					<td colspan="3" align="center">
						<s:hidden id="dubberid" name="entity.artid"/>
						<input type="button" class="btn2" value="保存" onClick="saveData();" />
						<input type="button" class="btn2" value="取消" onClick="backList();" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
