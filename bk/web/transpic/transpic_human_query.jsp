<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.qcmz.cmc.constant.DictConstant"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<html>
  <head>
	<title></title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/transpic_human.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：订单管理  &gt;&gt; 图片翻译处理</td>
		</tr>
		</table>
	</div>	
	<!-- 查询区域 -->
	<div id="searchArea" style="width:98%; margin:10px auto 0">
		<form name="qryForm" id="qryForm" action="#">
		<table border="0" align="center" cellpadding="0" cellspacing="4" width="100%" class="condition" >
		    <tr height="6"><td colspan="6"></td></tr>
			<tr>
				<td class="textRight" width="8%">订单编号：</td>
				<td class="textLeft" width="25%"><input name="picid" /></td>
				<td class="textRight" width="8%">处理人：</td>
				<td class="textLeft"  width="25%"><input name="oper"/></td>
				<td class="textRight" width="9%">处理进度：</td>
				<td class="textLeft" width="25%"><dict:select name="dealProgress" field="dealProgress"/></td>
			</tr>
			<tr>
				<td class="textRight">提交日期：</td>
				<td class="textLeft">
					<input name="begin" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
					-
					<input name="end" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate"/>
				</td>
				<td class="textRight">用户编号：</td>
				<td class="textLeft"><input name="userid" /></td>
				<td class="textRight"></td>
				<td class="textLeft">
					<input type="hidden" name="exceptTransway" value="<%=DictConstant.DICT_TRANSWAY_MACHINE %>"/>
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
