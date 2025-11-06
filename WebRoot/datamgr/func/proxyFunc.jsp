<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
		<title>代理功能维护</title>
		<jsp:include page="/common/head.jsp" />
		<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/proxy.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：代理通道管理  &gt;&gt; 代理功能维护</td>
    	</tr>
	  </table>
	</div>
	<form id="editForm" name="editForm" action="#">
		<s:if test="proxys==null || proxys.isEmpty()">没有可用的代理</s:if>
		<s:elseif test="funcs==null || funcs.isEmpty()">没有可用的功能</s:elseif>
		<s:else>
			<table id="tb" class="tabletopblue" cellpadding="0" cellspacing="0" align="center" width="98%">
			<tr>
				<th width="150">代理↓ &nbsp;&nbsp;&nbsp;&nbsp; 功能→</th>
				<s:iterator value="funcs">
				<th><s:property value="funcname"/></th>
				</s:iterator>
			</tr>
			<s:iterator value="proxys">
			<tr>
				<td>${proxyid}-${proxyname}</td>
				<s:iterator value="funcs">
				<td>
					<input style="width:20px" type="checkbox" name="proxyfunc"	value="${proxyid }|${funcid }" 
						<s:if test="%{proxyfuncs.contains(proxyid+'|'+funcid)}">checked="checked"</s:if>
					/>					
				</td>
				</s:iterator>
			</tr>
			</s:iterator>
			<tr>
				<td colspan="<s:property value="funcs.size+1"/>" style="height:50">
					<input type="button" class="btn2" value="保存并刷新" onclick="saveProxyFunc();" />
					<input type="reset" class="btn2" value="重 置">
				</td>
			</tr>
			</table>
		</s:else>
	</form>
	<br/>
	</body>
</html>