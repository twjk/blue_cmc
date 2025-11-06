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
				<th style="width:10%">编号</th>
				<th style="width:10%">代码</th>
				<th style="width:25%">中文名称</th>
				<th style="width:25%">当地名称</th>
				<th style="width:5%">文件大小</th>
				<th style="width:10%">状态</th>
				<th style="width:5%">排序</th>
				<th style="width:15%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${themeid}')" >
				<td>${themeid}</td>
				<td>${themecode}</td>
				<td>${titlecn}</td>
				<td>${titlelc}</td>
				<td>${filesize }</td>
				<td><dict:text dataSource="xml" table="common" field="status" initvalue="${status}"/></td>
				<td>${sortindex }</td>
				<td>
					<a href="javascript:editData('${themeid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="编辑" title="编辑"></a>
					<a href="javascript:deleteData('${themeid }');"><img src="<lt:contextPath/>/images/delet.gif" alt="删除" title="删除"></a>
					<a href="javascript:queryDetailData('${themeid}');"><img src="<lt:contextPath/>/images/detail.gif" alt="明细" title="明细"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
