<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.qcmz.cmc.config.SystemConfig"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<html>
  <head>
	<title>主题语句维护</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<%=SystemConfig.BDC_SERVER %>/ws/cat_translib.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/themesentence.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：翻译管理  &gt;&gt; 主题语句维护</td>
		</tr>
		</table>
	</div>	
	<!-- 查询区域 -->
	<div id="searchArea" style="width:98%; margin:10px auto 0">
		<form name="qryForm" id="qryForm" action="#">
		<table border="0" align="center" cellpadding="0" cellspacing="4" width="100%" class="condition" >
		    <tr height="6"><td colspan="6"></td></tr>
			<tr>
				<td class="textRight" width="8%">原文：</td>
				<td class="textLeft" width="25%"><input id="qry_src" name="src" /></td>
				<td class="textRight" width="8%">目标语言：</td>
				<td class="textLeft" width="25%"><dict:select id="qry_tolang" name="tolang" field="speechLang" inithead="0"/></td>
				<td class="textRight" width="9%">分类</td>
				<td class="textLeft" width="25%" id="qry_selCat"><s:hidden id="qry_currCat" name="cat"/></td>
			</tr>
			<tr>
				<td class="textRight"></td>
				<td class="textLeft"></td>
				<td class="textRight"></td>
				<td class="textLeft"></td>
				<td class="textRight"></td>
				<td class="textLeft">
					<s:hidden id="qry_themeid" name="themeid" />
					<input type="hidden" id="themeQueryString" value="${themeQueryString}"/>
					<input class="btn2" id="btnQuery" type="button" value="查 询" onClick="dataQuery();" />
					<input class="btn2" type="reset" value="重 置" onclick="resetQuery();"/>
					<input class="btn2" type="button" value="增 加" onClick="addData();" />
					<input class="btn2" type="button" value="导 入" onClick="addDataBatch();" />
					<input class="btn2" type="button" value="返回主题" onclick="backTheme();"/>
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
	
	<script type="text/javascript">
		initCat("qry_");
	</script>
  </body>
</html>
