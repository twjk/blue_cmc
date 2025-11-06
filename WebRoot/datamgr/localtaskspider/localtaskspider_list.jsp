<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
    <head>
		<title></title>
	</head>
	<body>
	<div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:5%">编号</th>
				<th style="width:7%">城市</th>
				<th style="width:13%">来源</th>
				<th style="width:55%">标题</th>
				<th style="width:10%">发布时间</th>
				<th style="width:10%">处理</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${spdid}')" >
				<td>${spdid}</td>
				<td>${cmcLtSource.cityname }</td>
				<td>${cmcLtSource.sourcename }</td>
				<td><a href="${link}" target="_blank">${title }</a></td>
				<td><s:date name="%{publishtime}" format="yyyy-MM-dd HH:mm"/></td>
				<td>
					<s:if test="@com.qcmz.framework.constant.DictConstants@DICT_DEALPROGRESS_WAITING==dealprogress">
						<a href="javascript:startDeal('${spdid }');" title="开始处理"><dict:text field="dealProgress" initvalue="${dealprogress}"/></a>
					</s:if>
					<s:elseif test="@com.qcmz.framework.constant.DictConstants@DICT_DEALPROGRESS_PROCESSING==dealprogress">
						<dict:text field="dealProgress" initvalue="${dealprogress}"/>
						(
						<a href="javascript:showToArticle('${spdid }', '${title }');" tile="完成处理">就业信息</a>
						&nbsp;
						<a href="javascript:finishDeal('${spdid }');" tile="完成处理">完成处理</a>
						)
					</s:elseif>
					<s:else>
						<dict:text field="dealProgress" initvalue="${dealprogress}"/>
					</s:else>
					<s:if test="opername!=null && opername!=''">
						<br/>${opername }(${opercd })
					</s:if>
					
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	<div id="toArticlelDiv" style="position: absolute;width:500px;display: none;background-color: #ffffff">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tableHeader">
		<tr>
			<td align="left" class="text13blackb" colspan="3" style="padding-left: 30px">转就业信息并完成处理</td>
		</tr>
		<tr>
			<td width="10"></td>
			<td width="50">标题：</td>
			<td width="300"><span id="spdTitle"></span></td>
		</tr>
		<tr>
			<td align="right" class="textrse12">*</td>
			<td>分类：</td>
			<td id="toArticle_selCat">
				<s:hidden id="toArticle_currCat" value="1301"/>
				<script type="text/javascript">
					initCat("toArticle_");
				</script>
			</td>
			</td>
		</tr>
		<tr>
			<td colspan="3" align="center" height="40">
				<input id="spdId" type="hidden">
				<input id="btnSaveToArtile" type="button" class="btn2" value="确定" onClick="saveToArticle();" />
				<input id="btnHideToArticle" type="button" class="btn2" value="关闭" onClick="hideToArticle();" />
			</td>
		</tr>
		</table>
	</div>
	</body>
</html>
