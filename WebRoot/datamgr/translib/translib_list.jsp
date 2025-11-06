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
				<th style="width:5%">类型</th>
				<th style="width:5%">源语言</th>
				<th style="width:20%">原文</th>
				<th style="width:5%">目标语言</th>
				<th style="width:20%">译文</th>
				<th style="width:10%">翻译</th>
				<th style="width:5%;" onclick="sortHitCount();">
					<a href="javascript:void(0)">命中次数</a>
					<span id="sortHitCount" class="<s:if test="orderby!=null&&orderby!=''">sortby</s:if><s:else>unsortby</s:else>">
						&nbsp;↓&nbsp;
					</span>
				</th>
				<th style="width:6%">来源</th>
				<th style="width:6%">核对</th>
				<th style="width:5%">状态</th>
				<th style="width:8%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${libClass}', '${libid}')" >
				<td>${libid}</td>
				<td><dict:text field="transLibType" initvalue="${libtype}"/></td>
				<td><dict:text field="speechLang" initvalue="${fromlang}"/></td>
				<td>${src }</td>
				<td><dict:text field="speechLang" initvalue="${tolang}"/></td>
				<td>${dst }</td>
				<td><dict:text dataSource="xml" table="common" field="twoway" initvalue="${twoway}"/></td>
				<td>${hitcount }</td>
				<td><dict:text field="transLibSource" initvalue="${sourceid}" /></td>
				<td>
					<a href="javascript:updateCheckStatus('${libClass}', '${libid }','${checkstatus==0?1:0 }');" title="点击${checkstatus==1?'待核对':'已核对' }">
						<dict:text table="common" field="checkstatus" initvalue="${checkstatus}" dataSource="xml"/>
					</a>
				</td>
				<td>
					<a href="javascript:updateStatus('${libClass}', '${libid }','${status==0?1:0 }');" title="点击${status==1?'停用':'启用' }">
						<dict:text table="common" field="status" initvalue="${status}" dataSource="xml"/>
					</a>
				</td>
				<td>
					<a href="javascript:editData('${libClass}', '${libid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="编辑" title="编辑"></a>
					<a href="javascript:deleteData('${libClass}', '${libid}');"><img src="<lt:contextPath/>/images/delet.gif" alt="删除" title="删除"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}"/>
	</body>
</html>
