<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
  <head>
	<title>译库整理</title>
	<jsp:include page="/common/head.jsp" />
	<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/translibSorting.js"></script>
  </head>
  
  <body>
  	<div id="funcNavigator" class="navDiv" align="left" >
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
		<tr>
		    <td width="1%">&nbsp;</td>
		    <td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
		    <td width="98%" class="text12black">&nbsp;现在您的位置：译库管理  &gt;&gt; 译库整理</td>
		</tr>
		</table>
	</div>	
	<br/>
	<form id="editForm" name="editForm" action="#">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">译库分拣</th>
			</tr>
			<tr>
				<td></td>
				<td width="5%" align="left">译库：</td>				
				<td >
					<select id="libClass" onchange="chgLib();">
						<option value="">所有</option>
					    <option value="CmcTransLibraryCn2en">中英</option>
					    <option value="CmcTransLibraryEn2cn">英中</option>
					    <option value="CmcTransLibraryCn2ja">中日</option>
					    <option value="CmcTransLibraryJa2cn">日中</option>
					    <option value="CmcTransLibraryCn2ko">中韩</option>
					    <option value="CmcTransLibraryKo2cn">韩中</option>
					    <option value="CmcTransLibrary">其他</option>
					    <option value="CmcTransLibraryTemp">临时</option>
					</select>
					<span id="spanToTheme" style="display:none">&nbsp;&nbsp;并导入主题库编号：<input type="text"  id="themeId"  class="input3"/></span>
				</td>
			</tr>
			<tr height="30">
				<td align="right"></td>
				<td colspan="2">
					<input id="btnSorting" type="button" class="btn2" value="重新分拣" onClick="doSorting();" />
					<span class="hint">（按照分库规则，将各译库中的数据重新分拣）</span>
				</td>
			</tr>
		</table>
		<br/>
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th width="98%" colspan="2">译库校验</th>
			</tr>
			<tr>
				<td></td>
				<td colspan="2">
					<ul class="hintUL">
						<li>此操作针对译库停用的数据</li>
						<li>使用百度接口检测文本对应的语言代码，删除不相符的数据</li>
						<li>使用翻译接口检测译文，一致的数据直接启用</li>
					</ul>
				</td>
			</tr>
			<tr height="30">
				<td align="right"></td>
				<td colspan="2">
					<input id="btnAutoCheck" type="button" class="btn2" value="自动校验" onClick="doAutoCheck();" />
				</td>
			</tr>
		</table>
		<br/>
	</form>
  </body>
</html>
