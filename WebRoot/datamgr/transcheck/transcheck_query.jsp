<%@page import="com.qcmz.cmc.cache.DictMap"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<html>
  <head>
	<title></title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/transcheck.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/audioplayer.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：订单管理  &gt;&gt; 翻译订单校对</td>
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
				<td class="textLeft" width="25%"><input name="orderid" /></td>
				<td class="textRight" width="8%">订单类型：</td>
				<td class="textLeft"  width="25%">
					<select name="ordertype">
						<option value="">--请选择--</option>
						<option value="1">图片翻译</option>
						<option value="3">短文快译</option>
					</select>
				</td>
				<td class="textRight" width="9%">校对状态：</td>
				<td class="textLeft" width="25%">
					<select name="checkstatus">
						<option value="">--请选择--</option>
						<option selected="selected" value="0,1">待校对+校对中</option>
						<option value="2,3">已校对+已修订</option>
						<option value="0">待校对</option>
						<option value="1">校对中</option>
						<option value="2">已校对</option>
						<option value="3">已修订</option>
					</select>
				</td>
			</tr>			
			<tr>
				<td class="textRight">语言：</td>
				<td class="textLeft"><dict:select id="qry_lang" name="lang" field="textLang" inithead="0"/></td>
				<td class="textRight">译员：</td>
				<td class="textLeft"><input name="oper"/></td>
				<td class="textRight"></td>
				<td class="textLeft">
					<input type="hidden" name="ordertype" value="${ordertype }"/>
					<input class="btn2" id="btnQuery" type="button" value="查 询" onClick="dataQuery();" />
					<input class="btn2" type="reset" value="重 置"/>
				</td>
			</tr>
			<tr height="6"><td colspan="6"></td></tr>
	  </table>
	  </form>
	</div>
	
	<!-- 数据编辑区域  -->
	<div id="editorArea" class="editDiv" style="display:none; margin:0 auto 10 auto"></div>
	
	<!-- 查询结果区域  -->
	<div id="resultArea"></div>
  </body>
</html>
