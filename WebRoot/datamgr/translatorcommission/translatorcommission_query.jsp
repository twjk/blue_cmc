<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.qcmz.cmc.constant.DictConstant"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
	<title></title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/translatorcommission.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：订单管理  &gt;&gt; 译员佣金结算</td>
		</tr>
		</table>
	</div>	
	<!-- 查询区域 -->
	<div id="searchArea" style="width:98%; margin:10px auto 0">
		<form name="qryForm" id="qryForm" action="#">
		<table border="0" align="center" cellpadding="0" cellspacing="4" width="100%" class="condition" >
		    <tr height="6"><td colspan="6"></td></tr>			
			<tr>
				<td class="textRight" width="8%">结算日期：</td>
				<td class="textLeft" width="25%">
					<input id="start" name="start" value="<fmt:formatDate value="${start }" pattern='yyyy-MM-dd'/>" 
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'2018-11-01', maxDate:'#F{$dp.$D(\'end\');}', maxDate:'%y-%M-%d'})" class="Wdate"/>
					-
					<input id="end" name="end" value="<fmt:formatDate value="${end }" pattern='yyyy-MM-dd'/>" 
						onFocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\');}', maxDate:'%y-%M-%d'})" class="Wdate"/>
				</td>
				<td class="textRight" width="8%">译员用户名：</td>
				<td class="textLeft" width="25%"><input name="operCd"/></td>
				<td class="textRight" width="9%" ></td>
				<td class="textLeft" width="25%">
					<input class="btn2" id="btnQuery" type="button" value="查 询" onclick="dataQuery();" />
					<input class="btn2" type="reset" value="重 置"/>
					<!-- <input class="btn2" type="button" value="导 出" onclick="dataExport();"/> -->
				</td>
			</tr>
			<tr>
				<td></td>
				<td><span class="hint">结算日期不要超过60天</span></td>
				<td colspan="4"></td>
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
