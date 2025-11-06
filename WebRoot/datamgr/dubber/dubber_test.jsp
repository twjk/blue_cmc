<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt"%>
<html>
  <head>
	<title></title>
	<jsp:include page="/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="<lt:contextPath/>/ueditor/ueditor.config.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/ueditor/ueditor.all.min.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="<lt:contextPath/>/ueditor/ueditor.plus.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：配音管理  &gt;&gt; 配音员维护</td>
		</tr>
		</table>
	</div>	
	<!-- 查询区域 -->
	<div id="searchArea" style="width:98%; margin:10px auto 0">
		<form name="qryForm" id="qryForm" action="#">
		<table border="0" align="center" cellpadding="0" cellspacing="4" width="100%" class="condition" >
		    <tr height="6"><td colspan="6"></td></tr>
			<tr>
				<td class="textRight" width="8%">名称：</td>
				<td class="textLeft" width="25%"><input id="qry_title" name="title" /></td>
				<td class="textRight" width="8%">姓名：</td>
				<td class="textLeft"  width="25%"><input id="qry_fullname" name="fullname" /></td>
				<td class="textRight" width="9%">状态：</td>
				<td class="textLeft" width="25%"><dict:select id="qry_status" name="status" field="status" inithead="0" dataSource="xml" table="common"/></td>
			</tr>
			<tr>
				<td class="textRight">分类：</td>
				<td class="textLeft" id="qry_selCat"><s:hidden id="qry_currCat" name="cat"/></td>
				<td class="textRight"></td>
				<td class="textLeft"></td>
				<td class="textRight"></td>
				<td class="textLeft">
					<s:textfield id="dubber1" name="entity.srcIntl.video" cssClass="input450"/>
					<a href="javascript:uploadFile('dubber1');">上传视频</a>
					<script id="uploadEditor" type="text/plain" style="display:none"></script>
					<script type="text/javascript">	
						var uploadEditor = new UploadFile('uploadEditor', 'dubber1', callback_uploadFile);
						function uploadFile(id){
							uploadEditor.uploadFile(id);
						}	
						function callback_uploadFile(t, result, actionData) {
							$("#"+actionData).val(result[0].url);
						}
					</script>
				</td>
			</tr>
			<tr height="6"><td colspan="6"></td></tr>
	  </table>
	  </form>
	</div>
  </body>
</html>
