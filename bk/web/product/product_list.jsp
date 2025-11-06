<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
    <head>
		<title></title>
		<script type="text/javascript" src="<lt:contextPath/>/javascript/listgry.js"></script>
	</head>
	<body>
	<div>
		<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
			<tr>
				<th style="width:5%">编号</th>
				<th style="width:20%">名称</th>
				<th style="width:15%">代码</th>
				<th style="width:10%">点数</th>
				<th style="width:10%">价格</th>
				<th style="width:10%">类型</th>
				<th style="width:10%">适用范围</th>
				<th style="width:10%">状态</th>
				<th style="width:10%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${productid}')" >
				<td>${productid}</td>
				<td>${productname}</td>
				<td>${productcode}</td>
				<td>${point }</td>
				<td>${price }</td>
				<td><dict:text field="productType4Trans" initvalue="${producttype}"/></td>
				<td><dict:text field="productRange" initvalue="${productrange}"/></td>
				<td><a href="javascript:updateStatus('${productid }','${status==0?1:0 }');" title="点击${status==1?'停用':'启用' }"><dict:text table="common" field="status" initvalue="${status}" dataSource="xml"></dict:text></a></td>
				<td>
					<a href="javascript:editData('${productid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="编辑" title="编辑"></a>
					<a href="javascript:deleteData('${productid }');"><img src="<lt:contextPath/>/images/delet.gif" alt="删除" title="删除"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
