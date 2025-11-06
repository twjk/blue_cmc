<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.qcmz.cmc.constant.BeanConstant"%>
<%@page import="com.qcmz.cmc.config.SystemConfig"%>
<%@page import="com.qcmz.framework.util.ArrayUtil"%>
<%@page import="com.qcmz.framework.util.CollectionUtil"%>
<%@page import="com.qcmz.bdc.ws.provide.locator.BdcWSLocator"%>
<%@page import="com.qcmz.dmc.ws.provide.locator.DmcWSLocator"%>
<%@page import="com.qcmz.dmc.ws.provide.locator.AccessLogWS"%>
<%@page import="com.qcmz.umc.ws.provide.locator.UmcWSLocator"%>
<%@page import="com.qcmz.cms.ws.provide.locator.CmsWSLocator"%>
<%@page import="com.qcmz.framework.util.FileServiceUtil"%>
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
    		<td width="98%" class="text12black">&nbsp;现在您的位置：系统管理  &gt;&gt; 系统配置管理</td>
    	</tr>
	  </table>
	</div>
	<br/>
	<form id="editForm" name="editForm" action="#">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th colspan="2">系统配置管理<span class="mark">（请分别到各节点进行修改）</span></th>
			</tr>
			<tr>
				<td></td>
				<td width="20%">配置版本：</td>
				<td width="78%"><%=SystemConfig.CATEGORY%></td>
			</tr>
			<tr>
				<td></td>
				<td>管理员邮箱：</td>
				<td>
					<input type="text" name="cfgMap['mails']" value="<%=ArrayUtil.toString(SystemConfig.MAILS)%>" />
					<span class="hint">（多个用,分隔，系统通知收件人）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>经理邮箱：</td>
				<td>
					<input type="text" name="cfgMap['manager.mails']" value="<%=ArrayUtil.toString(SystemConfig.MANAGER_MAILS)%>" />
					<span class="hint">（多个用,分隔，业务数据通知收件人）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>客服工作时间：</td>
				<td>
					<input type="text" name="cfgMap['cs.workTime']" value="<%=SystemConfig.CS_WORKTIME%>" />
					<span class="hint">（HHmm-HHmm，空表示不上班，0000-2400表示全天，工作时间外不推送不发短信，邮件照发）</span>
				</td>				
			</tr>
			<tr>
				<td></td>
				<td>客服邮箱：</td>
				<td>
					<input type="text" name="cfgMap['cs.mails']" value="<%=ArrayUtil.toString(SystemConfig.CS_MAILS)%>" />
					<span class="hint">（多个用,分隔）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>客服手机号：</td>
				<td>
					<input type="text" name="cfgMap['cs.mobiles']" value="<%=ArrayUtil.toString(SystemConfig.CS_MOBILES)%>" />
					<span class="hint">（多个用,分隔）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>多少分钟后可以捡单：</td>
				<td>
					<input type="text" name="cfgMap['orderpick.afterTime']" value="<%=SystemConfig.ORDERPICK_AFTERTIME%>" />
					<span class="hint">（分钟）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>每日一句缓存最大记录数：</td>
				<td>
					<input type="text" name="cfgMap['daysentence.maxResult']" value="<%=SystemConfig.DAYSENTENCE_MAXRESULT%>" />
					<span class="hint">（每日一句获取下一条句子时，超过该数则返回null）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>钱包激励开关：</td>
				<td><dict:radio name="cfgMap['wallet.encourage.switch']" table="common" field="boolean" dataSource="xml" initvalue="<%=String.valueOf(SystemConfig.WALLET_ENCOURAGE_SWITCH)%>" cssClass="raddefault"/></td>
			</tr>			
			<tr>
				<td></td>
				<td>钱包激励用户日次数：</td>
				<td><input type="text" name="cfgMap['wallet.encourage.dateTimes']" value="<%=SystemConfig.WALLET_ENCOURAGE_DATETIMES%>" /></td>
			</tr>
			<tr>
				<td></td>
				<td>钱包激励每次金额：</td>
				<td><input type="text" name="cfgMap['wallet.encourage.amount']" value="<%=SystemConfig.WALLET_ENCOURAGE_AMOUNT%>" /></td>
			</tr>
			<tr>
				<td></td>
				<td>奖励金奖励开关：</td>
				<td><dict:radio name="cfgMap['reward.reward.switch']" table="common" field="boolean" dataSource="xml" initvalue="<%=String.valueOf(SystemConfig.REWARD_REWARD_SWITCH)%>" cssClass="raddefault"/></td>
			</tr>			
			<tr>
				<td></td>
				<td>奖励金奖励单用户日次数：</td>
				<td><input type="text" name="cfgMap['reward.reward.dateTimes']" value="<%=SystemConfig.REWARD_REWARD_DATETIMES%>" /></td>
			</tr>
			<tr>
				<td></td>
				<td>奖励金奖励单次最大金额：</td>
				<td><input type="text" name="cfgMap['reward.reward.maxAmount']" value="<%=SystemConfig.REWARD_REWARD_MAXAMOUNT%>" /></td>
			</tr>			
			<tr>
				<td></td>
				<td>尖叫红包有效天数：</td>
				<td>
					<input type="text" name="cfgMap['redpacket.validDays']" value="<%=SystemConfig.REDPACKET_VALIDDAYS%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>尖叫红包服务费率：</td>
				<td>
					<input type="text" name="cfgMap['redpacket.servicePercent']" value="<%=SystemConfig.REDPACKET_SERVICEPERCENT%>" />
					<span class="hint">（千分之几）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>cmc服务地址：</td>
				<td>
					<input type="text" name="cfgMap['cmc.server']" value="<%=SystemConfig.CMC_SERVER%>" class="input2"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>cmc分发服务地址：</td>
				<td>
					<input type="text" name="cfgMap['cmc.dists']" value="<%=ArrayUtil.toString(SystemConfig.CMC_DISTS)%>" class="input2"/>
					<span class="hint">（多个用,分隔）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>bdc服务地址：</td>
				<td>
					<input type="text" name="cfgMap['bdc.server']" value="<%=SystemConfig.BDC_SERVER%>" class="input2"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>bdc局域网内服务地址：</td>
				<td>
					<input type="text" name="cfgMap['bdc.lanserver']" value="<%=BdcWSLocator.BDC_SERVER%>" class="input2"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>umc服务地址：</td>
				<td>
					<input type="text" name="cfgMap['umc.lanserver']" value="<%=UmcWSLocator.UMC_SERVER%>" class="input2"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>cms服务地址：</td>
				<td>
					<input type="text" name="cfgMap['cms.server']" value="<%=CmsWSLocator.CMS_SERVER%>" class="input2"/>
				</td>
			</tr>			
			<tr>
				<td></td>
				<td>dmc服务地址：</td>
				<td>
					<input type="text" name="cfgMap['dmc.server']" value="<%=DmcWSLocator.DMC_SERVER%>" class="input2"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>文件服务代理通道：</td>
				<td><dict:radio name="cfgMap['fileService.proxy']" table="common" field="fileProxy" dataSource="xml" initvalue="<%=FileServiceUtil.PROXY%>" cssClass="raddefault"/></td>
			</tr>
			<tr>
				<td></td>
				<td>文件服务地址：</td>
				<td>
					<input type="text" name="cfgMap['fileService.server']" value="<%=FileServiceUtil.SERVERURL%>" class="input2"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>文件服务根文件夹名称：</td>
				<td>
					<input type="text" name="cfgMap['fileService.rootDirName']" value="<%=FileServiceUtil.ROOTDIRNAME%>" class="input2"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>任务最大重试次数：</td>
				<td>
					<input type="text" name="cfgMap['task.retry']" value="<%=SystemConfig.TASK_RETRY%>" />
					<span class="hint">（超过该数后不再重试）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td> 记录本系统访问日志开关：</td>
				<td><dict:radio name="cfgMap['logAccess.switch']" table="common" field="status" dataSource="xml" initvalue="<%=String.valueOf(AccessLogWS.ACCESSLOG_LOCAL_SWITCH)%>" cssClass="raddefault"/></td>
			</tr> 
			<tr>
				<td></td>
				<td>记录访问日志的地址：</td>
				<td>
					<input class="system_input" type="text" name="cfgMap['logAccess.includes']" value="<%=CollectionUtil.toString(AccessLogWS.ACCESSLOG_LOCALURI_INCLUDES)%>" />
					<span class="hint">（支持正则表达式，多个用,分隔，空表示所有地址都记录）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>不记录访问日志的地址：</td>
				<td>
					<input class="system_input" type="text" name="cfgMap['logAccess.excepts']" value="<%=CollectionUtil.toString(AccessLogWS.ACCESSLOG_LOCALURI_EXCEPTS)%>" />
					<span class="hint">（支持正则表达式，多个用,分隔，空表示所有地址都记录）</span>
				</td>
			</tr>
			<tr height="30">
				<td colspan="3" align="center">
					<input type="hidden" name="beanId" id="beanId" value="<%=BeanConstant.BEANID_CONFIG_SYSTEM %>" />
					<input type="button" class="btn2" value="保 存" onClick="saveConfig()" />
				</td>
			</tr>
		</table>	 
	</form>
	<br/>
	</body>
</html>