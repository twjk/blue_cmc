<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.qcmz.cmc.config.SystemConfig"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<html>
  <head>
	<title></title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=SystemConfig.BDC_SERVER %>/ws/geog.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/localtasksub.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：同城管理  &gt;&gt; 用户订阅</td>
		</tr>
		</table>
	</div>	
	<!-- 查询区域 -->
	<div id="searchArea" style="width:98%; margin:10px auto 0">
		<form name="qryForm" id="qryForm" action="#">
		<table border="0" align="center" cellpadding="0" cellspacing="4" width="100%" class="condition" >
		    <tr height="6"><td colspan="6"></td></tr>
			<tr>
				<td class="textRight" width="8%">城市：</td>
				<td class="textLeft" width="25%"><input type="hidden" id="countrycode" name="countrycode" value="cn" >
					<s:textfield autoname="cityid"/></td>
				<td class="textRight" width="8%">岗位：</td>
				<td class="textLeft"  width="25%"><input id="qry_title" name="title" /></td>
				<td class="textRight" width="9%">工作时间：</td>
				<td class="textLeft" width="25%"><dict:select id="qry_worktimetype" name="worktimetype" field="workTimeType" inithead="0"/></td>
			</tr>
			<tr>
				<td class="textRight">用户编号：</td>
				<td class="textLeft"><input id="qry_userid" name="userid" /></td>
				<td class="textRight"></td>
				<td class="textLeft"></td>
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
