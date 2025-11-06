<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<html>
  <head>
	<title>奖励金提现</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/rewardcash.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<%=request.getContextPath() %>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：用户管理  &gt;&gt; 奖励金提现</td>
		</tr>
		</table>
	</div>	
	<!-- 查询区域 -->
	<div id="searchArea" style="width:98%; margin:10px auto 0">
		<form name="qryForm" id="qryForm" action="#">
		<table border="0" align="center" cellpadding="0" cellspacing="4" width="100%" class="condition" >
		    <tr height="6"><td colspan="6"></td></tr>
		    <tr>
				<td class="textRight" width="8%">用户编号：</td>
				<td class="textLeft" width="25%"><s:textfield id="qry_userid" name="userid" /></td>
				<td class="textRight" width="8%">提现状态：</td>
				<td class="textLeft" width="25%"><dict:select id="qry_status" name="status" initvalue="1" field="cashStatus" inithead="0"/></td>
				<td class="textRight" width="9%">业务类型：</td>
				<td class="textLeft" width="25%"><dict:select field="serviceType" name="servicetype" inithead="0"/></td>
			</tr>
			<tr>
				<td class="textRight"></td>
				<td class="textLeft"></td>
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
