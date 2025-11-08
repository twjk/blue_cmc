<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
    <head>
		<title></title>
		<style type="text/css">
			html, body {
				margin: 0;
				padding: 0;
				height: 100%;
				overflow: hidden;
			}
			body {
				display: flex;
				flex-direction: column;
			}
			.list-container {
				flex: 1;
				display: flex;
				flex-direction: column;
				overflow: hidden;
				min-height: 0;
				height: 100%;
			}
			.table-wrapper {
				flex: 1;
				overflow-y: auto;
				min-height: 0;
				position: relative;
			}
			.resultListFence {
				width: 100%;
				border-collapse: collapse;
			}
			.resultListFence thead {
				position: sticky;
				top: 0;
				z-index: 10;
			}
			.pagination-wrapper {
				flex-shrink: 0;
				padding: 16px 0;
				text-align: right;
				border-top: 1px solid #E5E7EB;
				background-color: #FFFFFF;
			}
		</style>
	</head>
	<body>
	<div class="list-container">
		<div class="table-wrapper">
			<table class="resultListFence" cellpadding="0" cellspacing="0"  align="center" border="0">
				<thead>
					<tr>
						<th style="width:10%">编号</th>
						<th style="width:50%">用户</th>
						<th style="width:15%">最新消息时间</th>
						<th style="width:15%">处理状态</th>
						<th style="width:10%">操作</th>
					</tr>
				</thead>
				<tbody id="tab">
				<s:iterator value="pageBean.resultList" status="stut" id="bean">
				<tr onDblClick="detailData('${dialogid}')" >
					<td>${dialogid}</td>
					<td>${user.nick }（${userid }）</td>
					<td><s:date name="msgtime" format="yyyy-MM-dd HH:mm:ss"/></td>
					<td><dict:text field="dialogDealStatus" initvalue="${dealstatus }"/></td>
					<td>
						<a href="javascript:detailData('${dialogid}');"><img src="<lt:contextPath/>/images/detail.gif" alt="详情" title="详情"></a>
					</td>
				</tr>
				</s:iterator>
				</tbody>
			</table>
		</div>
		<div class="pagination-wrapper">
			<lt:pagination methodStr="dataQuery" sytleName="default" pageBean="${pageBean}" />
		</div>
	</div>
	</body>
</html>
