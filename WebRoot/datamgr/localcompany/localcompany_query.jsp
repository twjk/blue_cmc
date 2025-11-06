<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<html>
  <head>
	<title>来源信息维护</title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" charset="utf-8" src="<lt:contextPath/>/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="<lt:contextPath/>/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="<lt:contextPath/>/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/ueditor/ueditor.plus.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/localCompany.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：同城管理  &gt;&gt; 公司维护</td>
		</tr>
		</table>
	</div>	
	<!-- 查询区域 -->
	<div id="searchArea" style="width:98%; margin:10px auto 0">
		<form name="qryForm" id="qryForm" action="#" >
		<table border="0" align="center" cellpadding="0" cellspacing="4" width="100%" class="condition" >
		    <tr height="6"><td colspan="6"></td></tr>
			<tr>
				<td class="textRight" width="8%">名称：</td>
				<td class="textLeft" width="25%"><input name="companyname"/></td>
				<td class="textRight" width="8%">认证状态：</td>
				<td class="textLeft" width="25%"><dict:select id="qry_certifystatus" name="certifystatus" field="localCompanyCertifyStatus" inithead="0" /></td>
				<td class="textRight" width="9%">用户编号：</td>
				<td class="textLeft" width="25%"><input id="qry_userid" name="userid" /></td>
			</tr>
			<tr>
				<td class="textRight">创建途径：</td>
				<td class="textLeft"><dict:select id="qry_createwary" name="createway" field="localCompanyCreateWay" inithead="0" initvalue="3"/></td>
				<td class="textRight"></td>
				<td class="textLeft"></td>
				<td class="textRight"></td>
				<td class="textLeft">
					<input class="btn2" id="btnQuery" type="button" value="查 询" onClick="dataQuery();" />
					<input class="btn2" type="reset" value="重 置" onclick="resetQuery();"/>
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
