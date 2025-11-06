<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
		<title>能力维护</title>
		<jsp:include page="/common/head.jsp" />
		<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/funcLang.js"></script>
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
	<br/>
	<form id="editForm" name="editForm" action="#">
		<s:if test="langs==null || langs.isEmpty()">没有可用的语言</s:if>
		<s:else>
			<table class="tableHeader" cellpadding="0" cellspacing="0" align="center" width="98%">
			<tr>
				<th width="2%"></th>
				<th>选择支持的语言</th>
			</tr>
			<tr>
				<td></td>
				<td>
					<s:iterator value="langs" var="from">
					<ul class="langList" id="ul${from.langcode }">
						<li style="width:100%">
							<input type="checkbox" value="${from.langcode }" class="checkbox" onclick="checkAll(this)">
							${from.langcn}${from.langcode} —> 全部
						</li>
						<s:iterator value="langs" var="to">
							<s:if test="%{!#from.langcode.equals(#to.langcode)}">
							<li>
							<input type="checkbox" name="funclang" value="${from.langcode }|${to.langcode }" 
								<s:if test="%{caps.contains(#from.langcode+'|'+#to.langcode)}">checked="checked"</s:if>
								 class="checkbox"
							/>
							${from.langcn}${from.langcode} —> ${to.langcn}${to.langcode}
							</li>
							</s:if>
						</s:iterator>
					</ul>
					</s:iterator>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center" height="50">					
					<s:hidden id="funcId" name="funcId"/>
					<input type="button" class="btn2" value="保存并刷新" onclick="saveFuncLang();" />
					<input type="reset" class="btn2" value="重 置">
				</td>
			</tr>
			</table>
		</s:else>
	</form>
	<br/>
	</body>
</html>