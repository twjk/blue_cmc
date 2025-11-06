<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.qcmz.cmc.config.SystemConfig"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
  <head>
	<title>众包任务数据维护</title>
	<jsp:include page="/common/head.jsp" />
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/crowdtaskdatamgr.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：系统管理  &gt;&gt; 众包任务数据维护</td>
		</tr>
		</table>
	</div>	
	<br/>
	<form id="editForm" name="editForm" action="#">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">导入题库</th>
			</tr>
			<tr>
			 	<td class="required">*</td>
				<td width="10%">目录：</td>
				<td>
					<input id="crowdtask_importlib_dirPath" value="/mnt/data/crowdtask/"/>
					<span class="hint">（/mnt/data/crowdtask/xxx，c:/test/import）</span>
				</td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>题库名称：</td>
				<td>
					<input id="crowdtask_importlib_libName" value="文本标注"/>
					<span class="hint">（libName = 题库名称_序列号）</span>
				</td>
			</tr>		
			<tr height="30">
				<td align="right"></td>
				<td colspan="2">
					<input id="btnSyn" type="button" class="btn2" value="导入题库" onClick="doImportLib();" />
				</td>
			</tr>
		</table>
		<br/>
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">删除用户任务数据</th>
			</tr>
			<tr>
				<td class="required">*</td>
				<td width="10%">日期：</td>
				<td>
					<input id="usercrowdtask_deletefile_endtime_start" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" value="2024-08-03"/>
					&nbsp;-&nbsp;
					<input id="usercrowdtask_deletefile_endtime_end" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" value="2024-08-03"/>
				</td>
			</tr>
			<tr height="30">
				<td align="right"></td>
				<td colspan="2">
					<input type="button" class="btn2" value="删除文件" onClick="doDeleteUserCrowdTaskFile();" />
					<span class="hint">（删除同时满足条件的用户任务文件：1.自动取消的公司任务 2.指定日期已取消/已完结的用户任务）</span>
				</td>
			</tr>
		</table>
		<br/>
	</form>
  </body>
</html>
