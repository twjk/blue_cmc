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
				<th style="width:5%">语言</th>
				<th style="width:17%">标题</th>
				<th style="width:26%">原文</th>
				<th style="width:26%">译文</th>
				<th style="width:10%">发布时间</th>
				<th style="width:5%">状态</th>
				<th style="width:6%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${sentid}')" >
				<td>${sentid}</td>
				<td>
					<dict:text field="speechLang" initvalue="${fromlang}"/>
					<br/>
					<dict:text field="speechLang" initvalue="${tolang}"/>
				</td>
				<td style="padding-left:2px;padding-right:2px;">
					<s:if test="#bean.htmlurl==null">${title}</s:if>
					<s:else><a href="${bean.htmlurl}" target="_blank">${title}</a></s:else>
				</td>
				<td style="padding-left:2px;padding-right:2px;">${src }</td>
				<td style="padding-left:2px;padding-right:2px;">${dst }</td>
				<td>
					<s:date name="releasetime" format="yyyy-MM-dd"/>
					<br/>
					<s:date name="releasetime" format="HH:mm:ss"/>
				</td>
				<td>
					<a href="javascript:updateStatus('${sentid }','${status==0?1:0 }');" title="点击${status==1?'停用':'启用' }"><dict:text table="common" field="status" initvalue="${status}" dataSource="xml"></dict:text></a>
				</td>
				<td>
					<a href="javascript:editData('${sentid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="编辑" title="编辑"></a>
					<a href="javascript:deleteData('${sentid }');"><img src="<lt:contextPath/>/images/delet.gif" alt="删除" title="删除"></a>
					<a href="javascript:createHtml('${sentid }');"><img src="<lt:contextPath/>/images/html.png" alt="生成静态页" title="生成静态页"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
