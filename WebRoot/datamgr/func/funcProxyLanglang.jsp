<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
		<title>语言代理维护</title>
		<jsp:include page="/common/head.jsp" />
		<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/funcProxyLang.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：代理管理  &gt;&gt; <dict:text field="func" initvalue="${funcId}"/>能力维护</td>
    	</tr>
	  </table>
	</div>
	<form id="editForm" name="editForm" action="#">
		<s:if test="proxys==null || proxys.isEmpty()">没有可用的代理</s:if>
		<s:elseif test="langs==null || langs.isEmpty()">没有可用的语言</s:elseif>
		<s:else>
			<table id="tb" class="tabletopblue" cellpadding="0" cellspacing="0" align="center" width="98%">
			<tr>
				<th width="20%">语言↓ &nbsp;&nbsp;&nbsp;&nbsp; 代理→</th>
				<s:iterator value="proxys">
					<th>
						<input type="checkbox" id="proxy${proxyid }" value="${proxyid }" onclick="checkProxy(this);" class="checkbox">
						<s:property value="proxyname"/>&nbsp;
						<input type="text" id="priority${proxyid }" value="${priority}" onkeyup="chgPriority(this,'${name }');" class="input3"/>
					</th>
				</s:iterator>
			</tr>
			<s:iterator value="langs" var="from">
				<s:iterator value="langs" var="to">
					<s:if test="%{!#from.langcode.equals(#to.langcode)}">
					<tr>
						<td style="text-align: left;padding-left: 20px;">${from.langcn}${from.langcode} —> ${to.langcn}${to.langcode}</td>
						<s:iterator value="proxys">
						<td>
							<input type="checkbox" flag="proxy${proxyid }"	value="${from.langcode }|${to.langcode }|${proxyid }" 
								<s:if test="%{map[#from.langcode+'|'+#to.langcode+'|'+proxyid]!=null}">checked="checked"</s:if>
								 class="checkbox"
							/>
							<s:textfield flag="priority${proxyid }" name="map['%{#from.langcode}|%{#to.langcode}|%{proxyid}'].priority"
								id="%{#from.langcode}|%{#to.langcode}|%{proxyid}" class="input3"
								/>
						</td>
						</s:iterator>
					</tr>
					</s:if>
				</s:iterator>
			</s:iterator>
			<tr>
				<td colspan="<s:property value="proxys.size+1"/>" style="height:50">
					<s:hidden id="funcId" name="funcId"/>
					<input type="button" class="btn2" value="保存并刷新" onclick="save();" />
					<input type="reset" class="btn2" value="重 置">
				</td>
			</tr>
			</table>
		</s:else>
	</form>
	<br/>
	</body>
</html>