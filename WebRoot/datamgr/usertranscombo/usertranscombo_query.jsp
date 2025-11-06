<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<html>
  <head>
	<title></title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/usertranscombo.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：用户管理  &gt;&gt; 用户翻译套餐</td>
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
				<td class="textLeft" width="25%"><input name="userid" /></td>
				<td class="textRight" width="8%">套餐名称：</td>
				<td class="textLeft"  width="25%"><input name="combotitle"></td>
				<td class="textRight" width="9%">订单编号：</td>
				<td class="textLeft" width="25%"><input name="orderid" /></td>
			</tr>
			<tr>
				<td class="textRight">付款方：</td>
				<td class="textLeft"><dict:select field="transComboPaySide" name="payside" inithead="0"/></td>
				<td class="textRight">服务途径：</td>
				<td class="textLeft"><dict:select field="transComboServiceWay" name="serviceway" inithead="0"/></td>
				<td class="textRight"></td>
				<td class="textLeft">
					<input type="hidden" id="orderby" name="orderby">
					<input class="btn2" id="btnQuery" type="button" value="查 询" onClick="dataQuery();" />
					<input class="btn2" type="reset" value="重 置"/>
					<input class="btn2" type="button" value="增 加" onClick="editData();" />
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