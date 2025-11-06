<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>图片翻译测试</title>
		<jsp:include page="/common/head.jsp" />
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：图片翻译管理  &gt;&gt; 图片翻译测试</td>
    	</tr>
	  </table>
	</div>
	<br/>
	<form id="editForm" name="editForm" enctype="multipart/form-data" method="post" action="/cmc/services-ssl/transPic!addPic.do">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th colspan="2">添加图片翻译</th>
			</tr>
			<tr>
				<td></td>
				<td width="20%">version：</td>
				<td width="78%"><input type="text" name="appVer" value="2.6.0"></td>
			</tr>
			<tr>
				<td></td>
				<td>authAccount：</td>
				<td><input type="text" name="authAccount" value="trans"></td>
			</tr>
			<tr>
				<td></td>
				<td>authToken：</td>
				<td><input type="text" name="authToken"/></td>
			</tr>
			<tr>
				<td></td>
				<td>用户编号：</td>
				<td><input type="text" name="uid" value="10017388"/></td>
			</tr>
			<tr>
				<td></td>
				<td>用户类型：</td>
				<td><dict:radio name="userType" field="userType" initvalue="1" cssClass="raddefault"/></td>
			</tr>
			<tr>
				<td></td>
				<td>翻译途径：</td>
				<td><dict:radio name="transWay" field="transWay" cssClass="raddefault" initvalue="03"/></td>
			</tr>
			<tr>
				<td></td>
				<td>源语言：</td>
				<td><input type="text" name="from" value="en"/></td>
			</tr>
			<tr>
				<td></td>
				<td>原文：</td>
				<td><textarea name="src"></textarea></td>
			</tr>
			<tr>
				<td></td>
				<td>目标语言：</td>
				<td><input type="text" name="to" value="zh-cn"/></td>
			</tr>
			<tr>
				<td></td>
				<td>译文：</td>
				<td><textarea name="dst"></textarea></td>
			</tr>
			<tr>
				<td></td>
				<td>用户需求：</td>
				<td><textarea name="requirement"></textarea></td>
			</tr>
			<tr>
				<td></td>
				<td>经纬度：</td>
				<td><input type="text" name="lon" value="116.481488"/>-<input type="text" name="lat" value="39.990464"/></td>
			</tr>
			<tr>
				<td></td>
				<td>地址：</td>
				<td><input type="text" name="address" value="北京sohu现代城"/></td>
			</tr>
			<tr>
				<td></td>
				<td>图片：</td>
				<td><input type="file" name="file" accept="image/gif,image/jpeg"></td>
			</tr>
			<tr>
				<td></td>
				<td>缩略图：</td>
				<td><input type="file" name="thumb"></td>
			</tr>
			<tr>
				<td></td>
				<td>优惠券编号：</td>
				<td><input type="text" name="couponId" value=""/></td>
			</tr>
			<tr>
				<td></td>
				<td>优惠券面额：</td>
				<td><input type="text" name="couponAmount" value=""/></td>
			</tr>
			<tr>
				<td></td>
				<td>交易途径：</td>
				<td><dict:radio name="tradeWay" field="tradeWay" cssClass="raddefault"/></td>
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