<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<html>
  <head>
	<title></title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/usercrowdtask.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/audioplayer.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：任务管理  &gt;&gt; 用户任务查询</td>
		</tr>
		</table>
	</div>	
	<!-- 查询区域 -->
	<div id="searchArea" style="width:98%; margin:10px auto 0">
		<form name="qryForm" id="qryForm" action="#">
		<table align="center" cellpadding="0" cellspacing="4" width="100%" class="condition" >
		    <tr height="6"><td colspan="6"></td></tr>
			<tr>
				<td class="textRight" width="9%">任务编号/名称：</td>
				<td class="textLeft" width="25%"><input name="taskid" style="width: 40px;" /> / <input name="title" style="width: 100px;"></td>
				<td class="textRight" width="8%">任务来源：</td>
				<td class="textLeft" width="25%"><dict:select field="crowdTaskSource" name="tasksource" inithead="0"/></td>
				<td class="textRight" width="8%">状态：</td>
				<td class="textLeft" width="25%"><dict:select field="userCrowdTaskStatus" name="status" inithead="0"/></td>
			</tr>
			<tr>
				<td class="textRight">用户编号：</td>
				<td class="textLeft"><input name="userid" /></td>
				<td class="textRight">用户任务编号：</td>
				<td class="textLeft"><input name="utid" /></td>
				<td class="textRight">用户UUID：</td>
				<td class="textLeft"><input name="uuid" /></td>
			</tr>
			<tr>
				<td class="textRight">业务类型：</td>
				<td class="textLeft"><dict:select field="serviceType" name="servicetype" inithead="0"/></td>
				<td class="textRight">平台：</td>
				<td class="textLeft"><dict:select name="platform" field="platform" inithead="0"/></td>
				<td class="textRight"></td>
				<td class="textLeft">
					<input class="btn2" id="btnQuery" type="button" value="查 询" onClick="dataQuery();" />
					<input class="btn2" type="reset" value="重 置"/>
				</td>
			</tr>
			<tr height="6"><td colspan="6"></td></tr>
	  </table>
	  </form>
	</div>
	
	<!-- 数据编辑区域  -->
	<div id="editorArea" class="editDiv" style="display:none; margin:0 auto 0"></div>
	
	<!-- 查询结果区域  -->
	<div id="resultArea"></div>
  </body>
</html>