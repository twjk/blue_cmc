<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
				<th style="width:10%">编号</th>
				<th style="width:60%">公司名称</th>
				<th style="width:10%">认证</th>
				<th style="width:10%">创建途径</th>
				<th style="width:10%">操作</th>
			</tr> 
			<tbody id="tab">
			<s:iterator value="pageBean.resultList" status="stut" id="bean">
			<tr onDblClick="editData('${companyid}')" >
				<td>${companyid}</td>
				<td>${companyname }</td>
				<td><dict:text field="localCompanyCertifyStatus" initvalue="${certifystatus}"/></td>
				<td><dict:text field="localCompanyCreateWay" initvalue="${createway}"/></td>
				<td>
					<a href="javascript:editData('${companyid}');"><img src="<lt:contextPath/>/images/edit.gif" alt="编辑" title="编辑" ></a>
					<a href="javascript:deleteData('${companyid }');"><img src="<lt:contextPath/>/images/delet.gif" alt="删除" title="删除"></a>
				</td>
			</tr>
			</s:iterator>
			</tbody>
		</table>
	</div>
	<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
	</body>
</html>
