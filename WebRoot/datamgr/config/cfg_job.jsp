<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.qcmz.cmc.config.JobConfig"%>
<%@page import="com.qcmz.cmc.constant.BeanConstant"%>
<%@ taglib uri="dictionary" prefix="dict"%>
<%@ taglib uri="qcmz" prefix="lt" %>
<html>
	<head>
		<title>配置管理</title>
		<jsp:include page="/common/head.jsp" />
		<script type="text/javascript" src="<lt:contextPath/>/javascript/datamgr/config.js"></script>
	</head>
	<body topmargin="0" leftmargin="0" class="editLayer">
	<div id="funcNavigator" class="navDiv" align="left">
	  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="32">
  		<tr><td width="1%">&nbsp;</td>
    		<td width="1%"><img src="<lt:contextPath/>/images/wei.jpg" ></td>
    		<td width="98%" class="text12black">&nbsp;现在您的位置：系统管理  &gt;&gt; 定时任务维护</td>
    	</tr>
	  </table>
	</div>
	<br/>
	<form id="editForm" name="editForm" action="#">
		<table width="98%" cellpadding="0" cellspacing="0" border="0" class="tableHeader">
			<tr>
				<td width="2%"></td>
				<th colspan="2">定时任务配置维护<span class="mark">（请分别到各节点进行修改）</span></th>
			</tr>
			<tr>
				<td></td>
				<td width="20%">配置版本：</td>
				<td width="78%"><%=JobConfig.CATEGORY%></td>
			</tr>
			<tr>
				<td></td>
				<td>每日一句推送任务启用：</td>
				<td>
					<dict:radio name="cfgMap['daysentence.push.isWork']" initvalue="<%=String.valueOf(JobConfig.DAYSENTENCE_PUSH_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>每日一句推送频度：</td>
				<td>
					<input type="text" name="cfgMap['daysentence.push.cronExpression']" value="<%=JobConfig.DAYSENTENCE_PUSH_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>每日一句推送任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['daysentence.push.validTime']" value="<%=JobConfig.DAYSENTENCE_PUSH_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>每日一句缓存刷新任务启用：</td>
				<td>
					<dict:radio name="cfgMap['daysentence.refresh.isWork']" initvalue="<%=String.valueOf(JobConfig.DAYSENTENCE_REFRESH_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>每日一句缓存刷新频度：</td>
				<td>
					<input type="text" name="cfgMap['daysentence.refresh.cronExpression']" value="<%=JobConfig.DAYSENTENCE_REFRESH_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>每日一句生产任务启用：</td>
				<td>
					<dict:radio name="cfgMap['daysentence.produce.isWork']" initvalue="<%=String.valueOf(JobConfig.DAYSENTENCE_PRODUCE_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
					<span class="hint">（定时补全31天数据）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>每日一句生产频度：</td>
				<td>
					<input type="text" name="cfgMap['daysentence.produce.cronExpression']" value="<%=JobConfig.DAYSENTENCE_PRODUCE_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>每日一句生产任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['daysentence.produce.validTime']" value="<%=JobConfig.DAYSENTENCE_PRODUCE_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>清除过期翻译缓存任务启用：</td>
				<td>
					<dict:radio name="cfgMap['transCache.clearExpiration.isWork']" initvalue="<%=String.valueOf(JobConfig.TRANSCACHE_CLEAREXPIRATION_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>清除过期翻译缓存频度：</td>
				<td>
					<input type="text" name="cfgMap['transCache.clearExpiration.cronExpression']" value="<%=JobConfig.TRANSCACHE_CLEAREXPIRATION_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>译库入库任务启用：</td>
				<td>
					<dict:radio name="cfgMap['transLib.save.isWork']" initvalue="<%=String.valueOf(JobConfig.TRANLIB_SAVE_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>译库入库任务频度：</td>
				<td>
					<input type="text" name="cfgMap['transLib.save.cronExpression']" value="<%=JobConfig.TRANLIB_SAVE_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>译库缓存重载任务启用：</td>
				<td>
					<dict:radio name="cfgMap['transLibCache.refresh.isWork']" initvalue="<%=String.valueOf(JobConfig.TRANLIBCACHE_REFRESH_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>译库缓存重载任务频度：</td>
				<td>
					<input type="text" name="cfgMap['transLibCache.refresh.cronExpression']" value="<%=JobConfig.TRANLIBCACHE_REFRESH_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>译库缓存命中次数入库任务启用：</td>
				<td>
					<dict:radio name="cfgMap['transLibCache.saveHit.isWork']" initvalue="<%=String.valueOf(JobConfig.TRANLIBCACHE_SAVEHIT_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>译库缓存命中次数入库任务频度：</td>
				<td>
					<input type="text" name="cfgMap['transLibCache.saveHit.cronExpression']" value="<%=JobConfig.TRANLIBCACHE_SAVEHIT_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>翻译差异入库任务启用：</td>
				<td>
					<dict:radio name="cfgMap['transDiff.save.isWork']" initvalue="<%=String.valueOf(JobConfig.TRANSDIFF_SAVE_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>翻译差异入库任务频度：</td>
				<td>
					<input type="text" name="cfgMap['transDiff.save.cronExpression']" value="<%=JobConfig.TRANSDIFF_SAVE_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>翻译差异清除任务启用：</td>
				<td>
					<dict:radio name="cfgMap['transDiff.clear.isWork']" initvalue="<%=String.valueOf(JobConfig.TRANSDIFF_CLEAR_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>翻译差异清除任务频度：</td>
				<td>
					<input type="text" name="cfgMap['transDiff.clear.cronExpression']" value="<%=JobConfig.TRANSDIFF_CLEAR_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>资讯标签缓存重载任务启用：</td>
				<td>
					<dict:radio name="cfgMap['articleTagCache.refresh.isWork']" initvalue="<%=String.valueOf(JobConfig.ARTICLETAGCACHE_REFRESH_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>资讯标签缓存重载任务频度：</td>
				<td>
					<input type="text" name="cfgMap['articleTagCache.refresh.cronExpression']" value="<%=JobConfig.ARTICLETAGCACHE_REFRESH_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>用户黑名单缓存重载任务启用：</td>
				<td>
					<dict:radio name="cfgMap['appUserForbidCache.refresh.isWork']" initvalue="<%=String.valueOf(JobConfig.APPUSERFORBIDCACHE_REFRESH_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>用户黑名单缓存重载任务频度：</td>
				<td>
					<input type="text" name="cfgMap['appUserForbidCache.refresh.cronExpression']" value="<%=JobConfig.APPUSERFORBIDCACHE_REFRESH_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>超时拒单任务启用：</td>
				<td>
					<dict:radio name="cfgMap['order.reject.isWork']" initvalue="<%=String.valueOf(JobConfig.ORDER_REJECT_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>超时拒单任务频度：</td>
				<td>
					<input type="text" name="cfgMap['order.reject.cronExpression']" value="<%=JobConfig.ORDER_REJECT_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>超时拒单任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['order.reject.validTime']" value="<%=JobConfig.ORDER_REJECT_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>订单退款任务启用：</td>
				<td>
					<dict:radio name="cfgMap['order.refund.isWork']" initvalue="<%=String.valueOf(JobConfig.ORDER_REFUND_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>订单退款任务频度：</td>
				<td>
					<input type="text" name="cfgMap['order.refund.cronExpression']" value="<%=JobConfig.ORDER_REFUND_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>订单退款任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['order.refund.validTime']" value="<%=JobConfig.ORDER_REFUND_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>取消超时订单任务启用：</td>
				<td>
					<dict:radio name="cfgMap['order.cancel.isWork']" initvalue="<%=String.valueOf(JobConfig.ORDER_CANCEL_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>取消超时订单任务频度：</td>
				<td>
					<input type="text" name="cfgMap['order.cancel.cronExpression']" value="<%=JobConfig.ORDER_CANCEL_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>取消超时订单任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['order.cancel.validTime']" value="<%=JobConfig.ORDER_CANCEL_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>订单佣金结算任务启用：</td>
				<td>
					<dict:radio name="cfgMap['order.commission.isWork']" initvalue="<%=String.valueOf(JobConfig.ORDER_COMMISSION_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>订单佣金结算任务频度：</td>
				<td>
					<input type="text" name="cfgMap['order.commission.cronExpression']" value="<%=JobConfig.ORDER_COMMISSION_CRONEXP%>" />
					<span class="hint">（处理前5日内待结算订单）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>即将开始处理订单监控任务启用：</td>
				<td>
					<dict:radio name="cfgMap['orderMonitor.soonDeal.isWork']" initvalue="<%=String.valueOf(JobConfig.ORDERMONITOR_SOONDEAL_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>即将开始处理订单监控任务频度：</td>
				<td>
					<input type="text" name="cfgMap['orderMonitor.soonDeal.cronExpression']" value="<%=JobConfig.ORDERMONITOR_SOONDEAL_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>即将开始处理订单监控任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['orderMonitor.soonDeal.validTime']" value="<%=JobConfig.ORDERMONITOR_SOONDEAL_VALIDTIME%>" />
					<span class="hint">（秒，必须设为任务频度的一半，该值同时用于获取即将开始处理的订单）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>待处理订单监控任务启用：</td>
				<td>
					<dict:radio name="cfgMap['orderMonitor.waitDeal.isWork']" initvalue="<%=String.valueOf(JobConfig.ORDERMONITOR_WAITDEAL_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>待处理订单监控任务频度：</td>
				<td>
					<input type="text" name="cfgMap['orderMonitor.waitDeal.cronExpression']" value="<%=JobConfig.ORDERMONITOR_WAITDEAL_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>待处理订单监控任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['orderMonitor.waitDeal.validTime']" value="<%=JobConfig.ORDERMONITOR_WAITDEAL_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>未完成订单监控任务启用：</td>
				<td>
					<dict:radio name="cfgMap['orderMonitor.undealt.isWork']" initvalue="<%=String.valueOf(JobConfig.ORDERMONITOR_UNDEALT_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>未完成订单监控任务频度：</td>
				<td>
					<input type="text" name="cfgMap['orderMonitor.undealt.cronExpression']" value="<%=JobConfig.ORDERMONITOR_UNDEALT_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>未完成订单监控任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['orderMonitor.undealt.validTime']" value="<%=JobConfig.ORDERMONITOR_UNDEALT_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>资讯订阅处理任务启用：</td>
				<td>
					<dict:radio name="cfgMap['articleSub.successDeal.isWork']" initvalue="<%=String.valueOf(JobConfig.ARTICLESUB_SUCCESSDEAL_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>资讯订阅处理任务频度：</td>
				<td>
					<input type="text" name="cfgMap['articleSub.successDeal.cronExpression']" value="<%=JobConfig.ARTICLESUB_SUCCESSDEAL_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>资讯订阅处理任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['articleSub.successDeal.validTime']" value="<%=JobConfig.ARTICLESUB_SUCCESSDEAL_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>优惠套餐订单处理任务启用：</td>
				<td>
					<dict:radio name="cfgMap['packageOrder.deal.isWork']" initvalue="<%=String.valueOf(JobConfig.PACKAGEORDER_DEAL_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
					<span class="hint">（含优惠券包订单、翻译套餐订单）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>优惠套餐订单处理任务频度：</td>
				<td>
					<input type="text" name="cfgMap['packageOrder.deal.cronExpression']" value="<%=JobConfig.PACKAGEORDER_DEAL_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>套餐订单处理任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['packageOrder.deal.validTime']" value="<%=JobConfig.PACKAGEORDER_DEAL_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>众包任务计数入库任务启用：</td>
				<td>
					<dict:radio name="cfgMap['crowdtask.countSave.isWork']" initvalue="<%=String.valueOf(JobConfig.CROWDTASK_COUNTSAVE_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>众包任务计数入库任务频度：</td>
				<td>
					<input type="text" name="cfgMap['crowdtask.countSave.cronExpression']" value="<%=JobConfig.CROWDTASK_COUNTSAVE_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>众包任务自动创建任务启用：</td>
				<td>
					<dict:radio name="cfgMap['crowdtask.autoCreate.isWork']" initvalue="<%=String.valueOf(JobConfig.CROWDTASK_AUTOCREATE_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>众包任务自动创建任务频度：</td>
				<td>
					<input type="text" name="cfgMap['crowdtask.autoCreate.cronExpression']" value="<%=JobConfig.CROWDTASK_AUTOCREATE_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>众包任务自动创建任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['crowdtask.autoCreate.validTime']" value="<%=JobConfig.CROWDTASK_AUTOCREATE_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>众包任务过期取消任务启用：</td>
				<td><dict:radio name="cfgMap['crowdtask.cancel.isWork']" initvalue="<%=String.valueOf(JobConfig.CROWDTASK_CANCEL_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" /></td>
			</tr>
			<tr>
				<td></td>
				<td>众包任务过期取消任务频度：</td>
				<td><input type="text" name="cfgMap['crowdtask.cancel.cronExpression']" value="<%=JobConfig.CROWDTASK_CANCEL_CRONEXP%>" /></td>
			</tr>
			<tr>
				<td></td>
				<td>众包任务过期取消任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['crowdtask.cancel.validTime']" value="<%=JobConfig.CROWDTASK_CANCEL_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>用户众包任务发放奖励任务启用：</td>
				<td>
					<dict:radio name="cfgMap['userCrowdtask.rewardGrant.isWork']" initvalue="<%=String.valueOf(JobConfig.USERCROWDTASK_REWARDGRANT_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>用户众包任务发放奖励任务频度：</td>
				<td>
					<input type="text" name="cfgMap['userCrowdtask.rewardGrant.cronExpression']" value="<%=JobConfig.USERCROWDTASK_REWARDGRANT_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>用户众包任务发放奖励任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['userCrowdtask.rewardGrant.validTime']" value="<%=JobConfig.USERCROWDTASK_REWARDGRANT_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>用户众包任务提醒完成任务启用：</td>
				<td>
					<dict:radio name="cfgMap['userCrowdtask.remindToFinish.isWork']" initvalue="<%=String.valueOf(JobConfig.USERCROWDTASK_REMINDTOFINISH_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>用户众包任务提醒完成任务频度：</td>
				<td>
					<input type="text" name="cfgMap['userCrowdtask.remindToFinish.cronExpression']" value="<%=JobConfig.USERCROWDTASK_REMINDTOFINISH_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>用户众包任务提醒完成任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['userCrowdtask.remindToFinish.validTime']" value="<%=JobConfig.USERCROWDTASK_REMINDTOFINISH_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>用户众包任务过期取消任务启用：</td>
				<td><dict:radio name="cfgMap['userCrowdtask.cancel.isWork']" initvalue="<%=String.valueOf(JobConfig.USERCROWDTASK_CANCEL_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" /></td>
			</tr>
			<tr>
				<td></td>
				<td>用户众包任务过期取消任务频度：</td>
				<td><input type="text" name="cfgMap['userCrowdtask.cancel.cronExpression']" value="<%=JobConfig.USERCROWDTASK_CANCEL_CRONEXP%>" /></td>
			</tr>
			<tr>
				<td></td>
				<td>用户众包任务过期取消任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['userCrowdtask.cancel.validTime']" value="<%=JobConfig.USERCROWDTASK_CANCEL_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>用户众包任务删除文件任务启用：</td>
				<td><dict:radio name="cfgMap['userCrowdtask.deleteFile.isWork']" initvalue="<%=String.valueOf(JobConfig.USERCROWDTASK_DELETEFILE_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" /></td>
			</tr>
			<tr>
				<td></td>
				<td>用户众包任务删除文件任务频度：</td>
				<td><input type="text" name="cfgMap['userCrowdtask.deleteFile.cronExpression']" value="<%=JobConfig.USERCROWDTASK_DELETEFILE_CRONEXP%>" /></td>
			</tr>
			<tr>
				<td></td>
				<td>用户众包任务删除文件任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['userCrowdtask.deleteFile.validTime']" value="<%=JobConfig.USERCROWDTASK_DELETEFILE_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>同城采集结果分拣任务启用：</td>
				<td><dict:radio name="cfgMap['localTask.spiderSorting.isWork']" initvalue="<%=String.valueOf(JobConfig.LOALTASK_SPIDERSORTING_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" /></td>
			</tr>
			<tr>
				<td></td>
				<td>同城采集结果分拣任务频度：</td>
				<td><input type="text" name="cfgMap['localTask.spiderSorting.cronExpression']" value="<%=JobConfig.LOALTASK_SPIDERSORTING_CRONEXP%>" /></td>
			</tr>
			<tr>
				<td></td>
				<td>同城采集结果分拣任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['localTask.spiderSorting.validTime']" value="<%=JobConfig.LOALTASK_SPIDERSORTING_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>就业精选订阅通知任务启用：</td>
				<td><dict:radio name="cfgMap['localTask.subNotify.isWork']" initvalue="<%=String.valueOf(JobConfig.LOALTASK_SUBNOTIFY_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" /></td>
			</tr>
			<tr>
				<td></td>
				<td>就业精选订阅通知任务频度：</td>
				<td><input type="text" name="cfgMap['localTask.subNotify.cronExpression']" value="<%=JobConfig.LOALTASK_SUBNOTIFY_CRONEXP%>" /></td>
			</tr>
			<tr>
				<td></td>
				<td>就业精选订阅通知任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['localTask.subNotify.validTime']" value="<%=JobConfig.LOALTASK_SUBNOTIFY_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>红包过期处理任务启用：</td>
				<td>
					<dict:radio name="cfgMap['redPacket.expireDeal.isWork']" initvalue="<%=String.valueOf(JobConfig.REDPACKET_EXPIREDEAL_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>红包过期处理任务频度：</td>
				<td>
					<input type="text" name="cfgMap['redPacket.expireDeal.cronExpression']" value="<%=JobConfig.REDPACKET_EXPIREDEAL_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>红包取消处理任务启用：</td>
				<td>
					<dict:radio name="cfgMap['redPacket.cancelDeal.isWork']" initvalue="<%=String.valueOf(JobConfig.REDPACKET_CANCELDEAL_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>红包取消处理任务频度：</td>
				<td>
					<input type="text" name="cfgMap['redPacket.cancelDeal.cronExpression']" value="<%=JobConfig.REDPACKET_CANCELDEAL_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>红包取消处理任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['redPacket.cancelDeal.validTime']" value="<%=JobConfig.REDPACKET_CANCELDEAL_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>参加完成/评价订单活动任务：</td>
				<td>
					<dict:radio name="cfgMap['task.finishActivity.isWork']" initvalue="<%=String.valueOf(JobConfig.TASK_FINISHORDER_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>参加完成/评价订单活动任务频度：</td>
				<td>
					<input type="text" name="cfgMap['task.finishActivity.cronExpression']" value="<%=JobConfig.TASK_FINISHORDER_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>参加完成/评价订单活动任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['task.finishActivity.validTime']" value="<%=JobConfig.TASK_FINISHORDER_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>每日监控任务：</td>
				<td>
					<dict:radio name="cfgMap['monitor.perday.isWork']" initvalue="<%=String.valueOf(JobConfig.MONITOR_PERDAY_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
					<span class="hint">（订阅统计与明细、红包提现、订单统计）</span>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>每日监控任务频度：</td>
				<td>
					<input type="text" name="cfgMap['monitor.perday.cronExpression']" value="<%=JobConfig.MONITOR_PERDAY_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>清理临时文件：</td>
				<td>
					<dict:radio name="cfgMap['file.cleanTemp.isWork']" initvalue="<%=String.valueOf(JobConfig.FILE_CLEANTEMP_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>清理临时文件频度：</td>
				<td>
					<input type="text" name="cfgMap['file.cleanTemp.cronExpression']" value="<%=JobConfig.FILE_CLEANTEMP_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>清除过期锁任务：</td>
				<td>
					<dict:radio name="cfgMap['lock.clear.isWork']" initvalue="<%=String.valueOf(JobConfig.LOCK_CLEAR_ISWORK)%>" table="common" field="boolean" dataSource="xml" cssClass="raddefault" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>清除过期锁任务频度：</td>
				<td>
					<input type="text" name="cfgMap['lock.clear.cronExpression']" value="<%=JobConfig.LOCK_CLEAR_CRONEXP%>" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>清除过期锁任务锁有效时长：</td>
				<td>
					<input type="text" name="cfgMap['lock.clear.validTime']" value="<%=JobConfig.LOCK_CLEAR_VALIDTIME%>" />
					<span class="hint">（秒，可设为任务频度的一半）</span>
				</td>
			</tr>
			<tr height="30">
				<td colspan="3" align="center">
					<input type="hidden" name="beanId" id="beanId" value="<%=BeanConstant.BEANID_CONFIG_JOB %>" />
					<input type="button" class="btn2" value="保 存" onClick="saveConfig()" />
				</td>
			</tr>
		</table>	 
	</form>
	<br/>
	</body>
</html>