<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.qcmz.cmc.config.TransLibConfig"%>
<%@page import="com.qcmz.cmc.constant.BeanConstant"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>译库配置管理</title>
		<jsp:include page="/common/head.jsp" />
		<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/config.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：配置管理  &gt;&gt; 译库配置管理</td>
    	</tr>
	  </table>
	</div>
	<br/>
	<form id="editForm" name="editForm" action="#">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th colspan="2">译库配置管理<span class="mark">（请分别到各节点进行修改）</span></th>
			</tr>
			<tr>
				<td></td>
				<td width="20%">配置版本：</td>
				<td width="78%"><%=TransLibConfig.CATEGORY%></td>
			</tr>
			<tr>
				<td></td>
				<td>译库下载版本号：</td>
				<td>
					<input type="text" name="cfgMap['download.ver']" value="<%=TransLibConfig.TRANSLIB_DOWNLOAD_VERSION%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>译库文件密钥：</td>
				<td>
					<input type="text" name="cfgMap['download.key']" value="<%=TransLibConfig.TRANSLIB_DOWNLOAD_KEY%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>译库文件md5：</td>
				<td>
					<input type="text" name="cfgMap['download.md5']" value="<%=TransLibConfig.TRANSLIB_DOWNLOAD_MD5%>" class="input2"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>译库下载地址：</td>
				<td>
					<input type="text" name="cfgMap['download.url']" value="<%=TransLibConfig.TRANSLIB_DOWNLOAD_URL%>" class="input2"/>
				</td>
			</tr>
			<tr height="30">
				<td colspan="3" align="center">
					<input type="hidden" name="beanId" id="beanId" value="<%=BeanConstant.BEANID_CONFIG_TRANSLIB %>" />
					<input type="button" class="btn2" value="保 存" onClick="saveConfig()" />
				</td>
			</tr>
		</table>	 
	</form>
	<br/>
	</body>
</html>