<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.qcmz.cmc.config.TransConfig"%>
<%@page import="com.qcmz.cmc.constant.BeanConstant"%>
<%@page import="com.qcmz.framework.util.ArrayUtil"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<%@ taglib uri="dictionary" prefix="dict"%>
<html>
	<head>
		<title>系统配置管理</title>
		<jsp:include page="/common/head.jsp" />
		<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/config.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：配置管理  &gt;&gt; 翻译配置管理</td>
    	</tr>
	  </table>
	</div>
	<br/>
	<form id="editForm" name="editForm" action="#">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th colspan="2">翻译配置管理<span class="mark">（请分别到各节点进行修改）</span></th>
			</tr>
			<tr>
				<td></td>
				<td width="20%">配置版本：</td>
				<td width="78%"><%=TransConfig.CATEGORY%></td>
			</tr>
			<tr>
				<td></td>
				<td>每次最大字符数：</td>
				<td>
					<input type="text" name="cfgMap['maxchars']" value="<%=TransConfig.TRANS_MAXCHARS%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>回译开关：</td>
				<td><dict:radio name="cfgMap['backtrans.on']" table="common" field="backtrans" dataSource="xml" initvalue="<%=String.valueOf(TransConfig.BACKTRANS_ON)%>" cssClass="raddefault"/></td>
			</tr>
			<tr>
				<td></td>
				<td>回译最低匹配度：</td>
				<td>
					<input type="text" name="cfgMap['similar.min']" value="<%=TransConfig.TRANS_SIMILAR_MIN%>" />
					<span class="hint">（最大值100表示完全匹配；当回译结果和原文匹配度高于此值时，可直接返回翻译结果，而不通过下一通道翻译后比较再返回翻译结果）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>翻译结果缓存时长：</td>
				<td>
					<input type="text" name="cfgMap['cache.duration']" value="<%=TransConfig.TRANS_CACHE_DURATION%>" />
					<span class="hint">（毫秒，该值要和定时任务“清除过期缓存的翻译结果”相匹配）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>翻译结果入译库阀值：</td>
				<td>
					<input type="text" name="cfgMap['cache.dbthreshold']" value="<%=TransConfig.TRANS_CACHE_DBTHRESHOLD%>" />
					<span class="hint">（清除过期翻译缓存任务启用并且重复次数超过该值后入译库）</span>
				</td>
			</tr>			
			<tr>
				<td></td>
				<td>翻译日志记录开关：</td>
				<td><dict:radio name="cfgMap['log.on']" table="common" field="translog" dataSource="xml" initvalue="<%=String.valueOf(TransConfig.TRANS_LOG_ON)%>" cssClass="raddefault"/></td>
			</tr>
			<tr>
				<td></td>
				<td>翻译日志批量入库数：</td>
				<td>
					<input type="text" name="cfgMap['log.batchsize']" value="<%=TransConfig.TRANS_LOG_BATCHSIZE%>" />
					<span class="hint">（翻译日志记录开关为"存入本地库"时有效）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>翻译差异保留时长：</td>
				<td>
					<input type="text" name="cfgMap['trans.diff.duration']" value="<%=TransConfig.TRANS_DIFF_DURATION%>" />
					<span class="hint">（分钟）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>必应翻译超时时间：</td>
				<td>
					<input type="text" name="cfgMap['bing.timeout']" value="<%=TransConfig.BING_TRANS_TIMEOUT%>"/>
					<span class="hint">（毫秒）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>百度翻译超时时间：</td>
				<td>
					<input type="text" name="cfgMap['baidu.timeout']" value="<%=TransConfig.BAIDU_TRANS_TIMEOUT%>"/>
					<span class="hint">（毫秒）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>图片翻译缩略图最大宽/高：</td>
				<td>
					<input type="text" name="cfgMap['transpic.thumb.maxWidth']" value="<%=TransConfig.TRANSPIC_THUMB_MAXWIDTH%>"/>
					&nbsp;/&nbsp;
					<input type="text" name="cfgMap['transpic.thumb.maxHeight']" value="<%=TransConfig.TRANSPIC_THUMB_MAXHEIGHT%>"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>图片翻译结果返回最大长度：</td>
				<td>
					<input type="text" name="cfgMap['transpic.transResult.maxLength']" value="<%=TransConfig.TRANSPIC_TRANSRESULT_MAXLENGTH%>"/>
					<span class="hint">（超长加...）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>短文快译开关：</td>
				<td><dict:radio name="cfgMap['transtext.on']" table="common" field="boolean" dataSource="xml" initvalue="<%=String.valueOf(TransConfig.TRANSTEXT_ON)%>" cssClass="raddefault"/></td>
			</tr>
			<tr>
				<td></td>
				<td>短文快译工作时间段：</td>
				<td>
					<input type="text" name="cfgMap['transtext.workTime']" value="<%=TransConfig.TRANSTEXT_WORKTIME%>"/>
					<span class="hint">（HH:mm-HH:mm，00:00-24:00表示全天）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>短文快译工作时间描述：</td>
				<td><input type="text" name="cfgMap['transtext.workTimeDesc']" value="<%=TransConfig.TRANSTEXT_WORKTIMEDESC%>"/></td>
			</tr>
			<tr>
				<td></td>
				<td>短文快译响应时长：</td>
				<td>
					<input type="text" name="cfgMap['transtext.timeout']" value="<%=TransConfig.TRANSTEXT_TIMEOUT%>"/>
					<span class="hint">（秒，-1表示不限时长）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>短文快译最大字词数：</td>
				<td>
					<input type="text" name="cfgMap['transtext.maxWordNum']" value="<%=TransConfig.TRANSTEXT_MAXWORDNUM%>"/>
					<span class="hint">（-1表示不限字词数）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>短文快译限定渠道：</td>
				<td>
					<input type="text" name="cfgMap['transtext.channels']" value="<%=ArrayUtil.toString(TransConfig.TRANSTEXT_CHANNELS)%>" />
					<span class="hint">（多个用,分隔，空表示不限）</span>
				</td>
			</tr>
			<tr height="30">
				<td colspan="3" align="center">
					<input type="hidden" name="beanId" id="beanId" value="<%=BeanConstant.BEANID_CONFIG_TRANS %>" />
					<input type="button" class="btn2" value="保 存" onClick="saveConfig()" />
				</td>
			</tr>
		</table>	 
	</form>
	<br/>
	</body>
</html>