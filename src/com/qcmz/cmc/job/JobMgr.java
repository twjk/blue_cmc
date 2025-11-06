package com.qcmz.cmc.job;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.framework.job.AbstractJobMgr;

/**
 * 类说明：定时任务管理服务
 * 修改历史：
 */
@Component
public class JobMgr extends AbstractJobMgr{
	
	@Autowired
	private SchedulerFactoryBean schedulerFactory;
	
	/**
	 * 更新定时任务配置
	 * 修改历史：
	 */
	public void updateJob(){
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler(scheduler, "daySentencePushTrigger", "每日一句推送任务", JobConfig.DAYSENTENCE_PUSH_CRONEXP, JobConfig.DAYSENTENCE_PUSH_ISWORK);
		scheduler(scheduler, "daySentenceRefreshTrigger", "每日一句缓存刷新任务", JobConfig.DAYSENTENCE_REFRESH_CRONEXP, JobConfig.DAYSENTENCE_REFRESH_ISWORK);
		scheduler(scheduler, "daySentenceProduceTrigger", "每日一句生产任务", JobConfig.DAYSENTENCE_PRODUCE_CRONEXP, JobConfig.DAYSENTENCE_PRODUCE_ISWORK);
		scheduler(scheduler, "transCacheClearExpirationTrigger", "清除过期翻译缓存任务", JobConfig.TRANSCACHE_CLEAREXPIRATION_CRONEXP, JobConfig.TRANSCACHE_CLEAREXPIRATION_ISWORK);
		scheduler(scheduler, "transLibSaveTrigger", "译库入库任务", JobConfig.TRANLIB_SAVE_CRONEXP, JobConfig.TRANLIB_SAVE_ISWORK);
		scheduler(scheduler, "transLibCacheRefreshTrigger", "译库缓存重新加载任务", JobConfig.TRANLIBCACHE_REFRESH_CRONEXP, JobConfig.TRANLIBCACHE_REFRESH_ISWORK);
		scheduler(scheduler, "transLibCacheHitSaveTrigger", "译库缓存命中次数入库任务", JobConfig.TRANLIBCACHE_SAVEHIT_CRONEXP, JobConfig.TRANLIBCACHE_SAVEHIT_ISWORK);
		scheduler(scheduler, "transDiffSaveTrigger", "翻译差异入库任务", JobConfig.TRANSDIFF_SAVE_CRONEXP, JobConfig.TRANSDIFF_SAVE_ISWORK);
		scheduler(scheduler, "transDiffClearTrigger", "翻译差异清除任务", JobConfig.TRANSDIFF_CLEAR_CRONEXP, JobConfig.TRANSDIFF_CLEAR_ISWORK);
		scheduler(scheduler, "orderRejectTrigger", "超时拒单任务", JobConfig.ORDER_REJECT_CRONEXP, JobConfig.ORDER_REJECT_ISWORK);
		scheduler(scheduler, "orderRefundTrigger", "订单退款任务", JobConfig.ORDER_REFUND_CRONEXP, JobConfig.ORDER_REFUND_ISWORK);
		scheduler(scheduler, "orderCancelTrigger", "取消超时订单任务", JobConfig.ORDER_CANCEL_CRONEXP, JobConfig.ORDER_CANCEL_ISWORK);
		scheduler(scheduler, "orderCommissionTrigger", "订单佣金结算任务", JobConfig.ORDER_COMMISSION_CRONEXP, JobConfig.ORDER_COMMISSION_ISWORK);
		scheduler(scheduler, "orderMonitorSoonDealTrigger", "即将开始处理订单监控任务", JobConfig.ORDERMONITOR_SOONDEAL_CRONEXP, JobConfig.ORDERMONITOR_SOONDEAL_ISWORK);
		scheduler(scheduler, "orderMonitorWaitDealTrigger", "待处理订单监控任务", JobConfig.ORDERMONITOR_WAITDEAL_CRONEXP, JobConfig.ORDERMONITOR_WAITDEAL_ISWORK);
		scheduler(scheduler, "orderMonitorUndealtTrigger", "未完成订单监控任务", JobConfig.ORDERMONITOR_UNDEALT_CRONEXP, JobConfig.ORDERMONITOR_UNDEALT_ISWORK);
		scheduler(scheduler, "articleSubDealTrigger", "资讯订阅处理任务", JobConfig.ARTICLESUB_SUCCESSDEAL_CRONEXP, JobConfig.ARTICLESUB_SUCCESSDEAL_ISWORK);
		scheduler(scheduler, "packageOrderDealTrigger", "套餐订单处理任务", JobConfig.PACKAGEORDER_DEAL_CRONEXP, JobConfig.PACKAGEORDER_DEAL_ISWORK);
		scheduler(scheduler, "crowdTaskCountSaveTrigger", "众包任务计数入库任务", JobConfig.CROWDTASK_COUNTSAVE_CRONEXP, JobConfig.CROWDTASK_COUNTSAVE_ISWORK);
		scheduler(scheduler, "crowdTaskAutoCreateTrigger", "众包任务自动创建任务", JobConfig.CROWDTASK_AUTOCREATE_CRONEXP, JobConfig.CROWDTASK_AUTOCREATE_ISWORK);
		scheduler(scheduler, "crowdTaskCancelTrigger", "众包任务过期取消任务", JobConfig.CROWDTASK_CANCEL_CRONEXP, JobConfig.CROWDTASK_CANCEL_ISWORK);
		scheduler(scheduler, "userCrowdTaskRemindToFinishTrigger", "用户众包任务提醒用户完成任务", JobConfig.USERCROWDTASK_REMINDTOFINISH_CRONEXP, JobConfig.USERCROWDTASK_REMINDTOFINISH_ISWORK);
		scheduler(scheduler, "userCrowdRewardGrantTrigger", "用户众包任务奖励发放任务", JobConfig.USERCROWDTASK_REWARDGRANT_CRONEXP, JobConfig.USERCROWDTASK_REWARDGRANT_ISWORK);
		scheduler(scheduler, "userCrowdTaskCancelTrigger", "用户众包任务过期取消任务", JobConfig.USERCROWDTASK_CANCEL_CRONEXP, JobConfig.USERCROWDTASK_CANCEL_ISWORK);
		scheduler(scheduler, "userCrowdTaskDeleteFileTrigger", "用户众包任务删除文件任务", JobConfig.USERCROWDTASK_DELETEFILE_CRONEXP, JobConfig.USERCROWDTASK_DELETEFILE_ISWORK);
		scheduler(scheduler, "localTaskSpiderSortingTrigger", "同城采集结果分拣任务", JobConfig.LOALTASK_SPIDERSORTING_CRONEXP, JobConfig.LOALTASK_SPIDERSORTING_ISWORK);
		scheduler(scheduler, "localTaskSubNotifyTrigger", "就业精选订阅通知任务", JobConfig.LOALTASK_SUBNOTIFY_CRONEXP, JobConfig.LOALTASK_SUBNOTIFY_ISWORK);
		scheduler(scheduler, "articleTagCacheRefreshTrigger", "资讯标签缓存重载任务", JobConfig.ARTICLETAGCACHE_REFRESH_CRONEXP, JobConfig.ARTICLETAGCACHE_REFRESH_ISWORK);
		scheduler(scheduler, "redPacketExpireDealTrigger", "红包过期处理任务", JobConfig.REDPACKET_EXPIREDEAL_CRONEXP, JobConfig.REDPACKET_EXPIREDEAL_ISWORK);
		scheduler(scheduler, "redPacketCancelDealTrigger", "红包取消处理任务", JobConfig.REDPACKET_CANCELDEAL_CRONEXP, JobConfig.REDPACKET_CANCELDEAL_ISWORK);
		scheduler(scheduler, "appUserForbidCacheRefreshTrigger", "用户黑名单缓存重载任务", JobConfig.APPUSERFORBIDCACHE_REFRESH_CRONEXP, JobConfig.APPUSERFORBIDCACHE_REFRESH_ISWORK);
		scheduler(scheduler, "taskFinishOrderTrigger", "参加完成订单活动任务", JobConfig.TASK_FINISHORDER_CRONEXP, JobConfig.TASK_FINISHORDER_ISWORK);
		scheduler(scheduler, "monitorPerdayTrigger", "每日监控", JobConfig.MONITOR_PERDAY_CRONEXP, JobConfig.MONITOR_PERDAY_ISWORK);
		scheduler(scheduler, "tempFileCleanTrigger", "清理临时文件", JobConfig.FILE_CLEANTEMP_CRONEXP, JobConfig.FILE_CLEANTEMP_ISWORK);
		scheduler(scheduler, "lockClearTrigger", "清除过期锁", JobConfig.LOCK_CLEAR_CRONEXP, JobConfig.LOCK_CLEAR_ISWORK);
	}
}
