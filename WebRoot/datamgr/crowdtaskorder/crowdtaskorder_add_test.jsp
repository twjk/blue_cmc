<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>创建众包任务订单</title>
		<jsp:include page="/common/head.jsp" />
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：订单管理  &gt;&gt; 创建众包任务订单</td>
    	</tr>
	  </table>
	</div>
	<br/>
	<form id="editForm" name="editForm" enctype="multipart/form-data" method="post" action="/cmc/services-ssl/crowdTaskOrder!addCrowdTask.do">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th colspan="2">创建众包任务订单</th>
			</tr>
			<tr>
				<td></td>
				<td width="20%">version：</td>
				<td width="78%"><input type="text" name="appVer" value="1.0.0"></td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>authAccount：</td>
				<td><input type="text" name="authAccount" value="app_zc_ios"></td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>authToken：</td>
				<td><input type="text" name="authToken"/></td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>用户编号：</td>
				<td><input type="text" name="uid" value="11000001"/></td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>订单总金额：</td>
				<td><input type="text" name="amount" value="10"/></td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>任务单价：</td>
				<td><input type="text" name="price" value="1.0"/></td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>参与人数：</td>
				<td><input type="text" name="peopleNum" value="10"/></td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>订单类型：</td>
				<td>
					<ul class="raddefault">
						<li><input type="radio" name="orderType" value="13"/>用户调研</li>
						<li><input type="radio" name="orderType" value="14"/>产品测试</li>
						<li><input type="radio" name="orderType" value="15"/>应用加热</li>
						<li><input type="radio" name="orderType" value="16" checked="checked"/>多多助力</li>
						<li><input type="radio" name="orderType" value="17"/>帮忙投票</li>
						<li><input type="radio" name="orderType" value="18"/>公众号起量</li>
					</ul>
				</td>
			</tr>
			<tr>
				<td class="required">*</td>
				<td>任务说明：</td>
				<td><input type="text" name="content" value=""/></td>
			</tr>
			<tr>
				<td></td>
				<td>相关网址：</td>
				<td><input type="text" name="url" value=""/></td>
			</tr>
			<tr>
				<td></td>
				<td>任务图片：</td>
				<td><input type="file" name="file"></td>
			</tr>			
			<tr height="30">
				<td colspan="3" align="center">
					<input type="submit" class="btn2" value="保 存"/>
				</td>
			</tr>
		</table>	 
	</form>
	<br/>
	</body>
</html>