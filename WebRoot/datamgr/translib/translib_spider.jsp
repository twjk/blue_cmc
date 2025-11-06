<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
  <head>
	<title>译库采集</title>
	<jsp:include page="/common/head.jsp" />
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/translibSpider.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：译库管理  &gt;&gt; 译库采集</td>
		</tr>
		</table>
	</div>	
	<br/>
	<form id="editForm" name="editForm" action="#">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">booking采集酒店名称</th>
			</tr>
			<tr>
			 	<td align="right"><span class="textrse12">*</span></td>
				<td width="10%">国家：</td>
				<td>
					<s:select id="booking_selCountry" list="@com.qcmz.cmc.proxy.spider.booking.BookingSpiderProxy@countries"
						listKey="destId" listValue="show" headerKey="" headerValue="--请选择--"/>					
				</td>			
			</tr>	
			<tr height="30">
				<td align="right"></td>
				<td colspan="2">
					<input id="btnSyn" type="button" class="btn2" value="采集" onClick="doSpiderBooking();" />
				</td>
			</tr>
		</table>
	</form>
  </body>
</html>
