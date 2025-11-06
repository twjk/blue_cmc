package com.qcmz.cmc.config;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.stereotype.Component;

import com.qcmz.framework.config.AbstractConfig;

/**
 * 类说明：定时任务配置
 * 修改历史：
 */
@Component
public class JobConfig extends AbstractConfig {
	/**
	 * 配置分类
	 */
	public static String CATEGORY;
	/**
	 * 每日一句推送任务是否启用
	 */
	public static boolean DAYSENTENCE_PUSH_ISWORK;
	/**
	 * 每日一句推送频度配置
	 */
	public static String DAYSENTENCE_PUSH_CRONEXP;
	/**
     * 每日一句推送任务锁有效时长，秒
     */
    public static int DAYSENTENCE_PUSH_VALIDTIME;
    
	/**
	 * 每日一句缓存刷新任务是否启用
	 */
	public static boolean DAYSENTENCE_REFRESH_ISWORK;
	/**
	 * 每日一句缓存刷新频度配置
	 */
	public static String DAYSENTENCE_REFRESH_CRONEXP;
	/**
	 * 每日一句生产任务是否启用
	 */
	public static boolean DAYSENTENCE_PRODUCE_ISWORK;
	/**
	 * 每日一句生产频度配置
	 */
	public static String DAYSENTENCE_PRODUCE_CRONEXP;
	/**
     * 每日一句生产任务锁有效时长，秒
     */
    public static int DAYSENTENCE_PRODUCE_VALIDTIME;

	/**
	 * 清除过期翻译缓存任务是否启用
	 */
	public static boolean TRANSCACHE_CLEAREXPIRATION_ISWORK;
	/**
	 * 清除过期翻译缓存任务频度配置
	 */
	public static String TRANSCACHE_CLEAREXPIRATION_CRONEXP;

	/**
	 * 译库入库任务频度配置
	 */
	public static String TRANLIB_SAVE_CRONEXP;
	/**
	 * 译库入库任务是否启用
	 */
	public static boolean TRANLIB_SAVE_ISWORK;
	/**
	 * 译库缓存命中次数入库任务频度配置
	 */
	public static String TRANLIBCACHE_SAVEHIT_CRONEXP;
	/**
	 * 译库缓存命中次数入库任务是否启用
	 */
	public static boolean TRANLIBCACHE_SAVEHIT_ISWORK;
	/**
	 * 译库缓存重新加载任务是否启用
	 */
	public static boolean TRANLIBCACHE_REFRESH_ISWORK;
	/**
	 * 译库缓存重新加载任务频度配置
	 */
	public static String TRANLIBCACHE_REFRESH_CRONEXP;
	
	/**
	 * 翻译差异入库任务频度配置
	 */
	public static String TRANSDIFF_SAVE_CRONEXP;
	/**
	 * 翻译差异入库任务是否启用
	 */
	public static boolean TRANSDIFF_SAVE_ISWORK;
	/**
	 * 翻译差异清除任务频度配置
	 */
	public static String TRANSDIFF_CLEAR_CRONEXP;
	/**
	 * 翻译差异清除任务是否启用
	 */
	public static boolean TRANSDIFF_CLEAR_ISWORK;
	
	/**
	 * 拒单任务是否启用
	 */
	public static boolean ORDER_REJECT_ISWORK;
	/**
	 * 拒单任务频度配置
	 */
	public static String ORDER_REJECT_CRONEXP;
	/**
     * 拒单任务锁有效时长，秒
     */
    public static int ORDER_REJECT_VALIDTIME;
	
	/**
	 * 订单退款任务是否启用
	 */
	public static boolean ORDER_REFUND_ISWORK;
	/**
	 * 订单译退款任务频度配置
	 */
	public static String ORDER_REFUND_CRONEXP;
	/**
     * 订单退款任务锁有效时长，秒
     */
    public static int ORDER_REFUND_VALIDTIME;
    
    /**
	 * 取消超时订单任务是否启用
	 */
	public static boolean ORDER_CANCEL_ISWORK;
	/**
	 * 取消超时订单任务频度配置
	 */
	public static String ORDER_CANCEL_CRONEXP;
	/**
     * 取消超时订单任务锁有效时长，秒
     */
    public static int ORDER_CANCEL_VALIDTIME;
    
    /**
	 * 订单佣金结算任务是否启用
	 */
	public static boolean ORDER_COMMISSION_ISWORK;
	/**
	 * 订单佣金结算任务频度配置
	 */
	public static String ORDER_COMMISSION_CRONEXP;
    
	/**
	 * 即将开始处理订单监控任务是否启用
	 */
	public static boolean ORDERMONITOR_SOONDEAL_ISWORK;
	/**
	 * 即将开始处理订单监控任务频度配置
	 */
	public static String ORDERMONITOR_SOONDEAL_CRONEXP;
	/**
     * 即将开始处理订单监控任务锁有效时长，秒
     */
    public static int ORDERMONITOR_SOONDEAL_VALIDTIME;
	
	/**
	 * 待处理订单监控任务是否启用
	 */
	public static boolean ORDERMONITOR_WAITDEAL_ISWORK;
	/**
	 * 待处理订单监控任务频度配置
	 */
	public static String ORDERMONITOR_WAITDEAL_CRONEXP;
	/**
     * 待处理订单监控任务锁有效时长，秒
     */
    public static int ORDERMONITOR_WAITDEAL_VALIDTIME;
    
	/**
	 * 未完成订单监控任务是否启用
	 */
	public static boolean ORDERMONITOR_UNDEALT_ISWORK;
	/**
	 * 未完成订单监控任务频度配置
	 */
	public static String ORDERMONITOR_UNDEALT_CRONEXP;
	/**
     * 未完成订单监控任务锁有效时长，秒
     */
    public static int ORDERMONITOR_UNDEALT_VALIDTIME;
	
    /**
	 * 资讯订阅处理任务是否启用
	 */
	public static boolean ARTICLESUB_SUCCESSDEAL_ISWORK;
	/**
	 * 资讯订阅处理任务频度配置
	 */
	public static String ARTICLESUB_SUCCESSDEAL_CRONEXP;
	/**
     * 资讯订阅处理任务任务锁有效时长，秒
     */
    public static int ARTICLESUB_SUCCESSDEAL_VALIDTIME;
    
    /**
 	 * 套餐订单处理任务是否启用
 	 */
 	public static boolean PACKAGEORDER_DEAL_ISWORK;
 	/**
 	 * 套餐订单处理任务频度配置
 	 */
 	public static String PACKAGEORDER_DEAL_CRONEXP;
 	/**
      * 套餐订单处理任务任务锁有效时长，秒
      */
    public static int PACKAGEORDER_DEAL_VALIDTIME;
    
 	/**
 	 * 众包任务计数入库任务是否启用
 	 */
 	public static boolean CROWDTASK_COUNTSAVE_ISWORK;
 	/**
 	 * 众包任务计数入库任务频度配置
 	 */
 	public static String CROWDTASK_COUNTSAVE_CRONEXP;
       
    /**
   	 * 众包任务自动创建任务是否启用
   	 */
   	public static boolean CROWDTASK_AUTOCREATE_ISWORK;
   	/**
   	 * 众包任务自动创建任务频度配置
   	 */
   	public static String CROWDTASK_AUTOCREATE_CRONEXP;
   	/**
     * 众包任务自动创建任务锁有效时长，秒
     */
   	public static int CROWDTASK_AUTOCREATE_VALIDTIME;
   	/**
   	 * 众包任务过期取消任务是否启用
   	 */
   	public static boolean CROWDTASK_CANCEL_ISWORK;
   	/**
   	 * 众包任务过期取消任务频度配置
   	 */
   	public static String CROWDTASK_CANCEL_CRONEXP;
   	/**
     * 众包任务过期取消任务锁有效时长，秒
     */
   	public static int CROWDTASK_CANCEL_VALIDTIME; 
   	
    /**
  	 * 众包任务奖励发放任务是否启用
  	 */
  	public static boolean USERCROWDTASK_REWARDGRANT_ISWORK;
  	/**
  	 * 众包任务奖励发放任务频度配置
  	 */
  	public static String USERCROWDTASK_REWARDGRANT_CRONEXP;
  	/**
     * 众包任务奖励发放任务锁有效时长，秒
     */
    public static int USERCROWDTASK_REWARDGRANT_VALIDTIME; 
   	/**
   	 * 众包任务提醒完成任务是否启用
   	 */
   	public static boolean USERCROWDTASK_REMINDTOFINISH_ISWORK;
   	/**
   	 * 众包任务提醒完成任务频度配置
   	 */
   	public static String USERCROWDTASK_REMINDTOFINISH_CRONEXP;
   	/**
     * 众包任务提醒完成任务锁有效时长，秒
     */
   	public static int USERCROWDTASK_REMINDTOFINISH_VALIDTIME; 
   	/**
   	 * 用户众包任务过期取消任务是否启用
   	 */
   	public static boolean USERCROWDTASK_CANCEL_ISWORK;
   	/**
   	 * 用户众包任务过期取消任务频度配置
   	 */
   	public static String USERCROWDTASK_CANCEL_CRONEXP;
   	/**
     * 用户众包任务过期取消任务锁有效时长，秒
     */
   	public static int USERCROWDTASK_CANCEL_VALIDTIME; 
   	/**
   	 * 用户众包任务删除文件任务是否启用
   	 */
   	public static boolean USERCROWDTASK_DELETEFILE_ISWORK;
   	/**
   	 * 用户众包任务删除文件任务频度配置
   	 */
   	public static String USERCROWDTASK_DELETEFILE_CRONEXP;
   	/**
     * 用户众包任务删除文件任务锁有效时长，秒
     */
   	public static int USERCROWDTASK_DELETEFILE_VALIDTIME; 
   	
	/**
   	 * 同城采集结果分拣任务是否启用
   	 */
   	public static boolean LOALTASK_SPIDERSORTING_ISWORK;
   	/**
   	 * 同城采集结果分拣任务频度配置
   	 */
   	public static String LOALTASK_SPIDERSORTING_CRONEXP;
   	/**
     * 同城采集结果分拣任务锁有效时长，秒
     */
   	public static int LOALTASK_SPIDERSORTING_VALIDTIME; 
   	
   	/**
   	 * 就业精选订阅通知任务是否启用
   	 */
   	public static boolean LOALTASK_SUBNOTIFY_ISWORK;
   	/**
   	 * 就业精选订阅通知任务频度配置
   	 */
   	public static String LOALTASK_SUBNOTIFY_CRONEXP;
   	/**
     * 就业精选订阅通知任务锁有效时长，秒
     */
   	public static int LOALTASK_SUBNOTIFY_VALIDTIME; 
   	
    /**
	 * 资讯标签缓存重载任务是否启用
	 */
	public static boolean ARTICLETAGCACHE_REFRESH_ISWORK;
	/**
	 * 资讯标签缓存重载任务频度配置
	 */
	public static String ARTICLETAGCACHE_REFRESH_CRONEXP;
	
	/**
	 * 红包过期处理任务是否启用
	 */
	public static boolean REDPACKET_EXPIREDEAL_ISWORK;
	/**
	 * 红包过期处理任务频度配置
	 */
	public static String REDPACKET_EXPIREDEAL_CRONEXP;
	
	/**
	 * 红包取消处理任务是否启用
	 */
	public static boolean REDPACKET_CANCELDEAL_ISWORK;
	/**
	 * 红包取消处理任务频度配置
	 */
	public static String REDPACKET_CANCELDEAL_CRONEXP;
	/**
     * 红包取消处理任务锁有效时长，秒
     */
    public static int REDPACKET_CANCELDEAL_VALIDTIME;
	
	/**
	 * 用户黑名单缓存重载任务是否启用
	 */
	public static boolean APPUSERFORBIDCACHE_REFRESH_ISWORK;
	/**
	 * 用户黑名单缓存重载任务频度配置
	 */
	public static String APPUSERFORBIDCACHE_REFRESH_CRONEXP;
	
	/**
	 * 参加完成订单活动任务是否启用
	 */
	public static boolean TASK_FINISHORDER_ISWORK;
	/**
	 * 参加完成订单活动任务频度配置
	 */
	public static String TASK_FINISHORDER_CRONEXP;
	/**
     * 参加完成订单活动任务锁有效时长，秒
     */
    public static int TASK_FINISHORDER_VALIDTIME;
	
	/**
     * 每日监控是否启用
     */
    public static boolean MONITOR_PERDAY_ISWORK;
    /**
     * 每日监控时间配置
     */
    public static String MONITOR_PERDAY_CRONEXP;	
    
	/**
     * 清理临时文件是否启用
     */
    public static boolean FILE_CLEANTEMP_ISWORK;
    /**
     * 清理临时文件时间配置
     */
    public static String FILE_CLEANTEMP_CRONEXP;
	
    /**
     * 清除过期锁任务是否启用
     */
    public static boolean LOCK_CLEAR_ISWORK;
    /**
     * 清除过期锁时间配置
     */
    public static String LOCK_CLEAR_CRONEXP;
    /**
     * 清除过期锁任务锁有效时长，秒
     */
    public static int LOCK_CLEAR_VALIDTIME;
    
	/** 
	 * @throws ConfigurationException
	 * 修改历史：
	 */
	@Override
	protected void loadConfig() throws ConfigurationException {
		setConfig("job.properties");
	}

	/** 
	 * 修改历史：
	 */
	@Override
	protected void readConfig() {
		CATEGORY = category;
		
		//每日一句推送
		DAYSENTENCE_PUSH_ISWORK = config.getBoolean(getPropName("daysentence.push.isWork"));
		DAYSENTENCE_PUSH_CRONEXP = config.getString(getPropName("daysentence.push.cronExpression"));
		DAYSENTENCE_PUSH_VALIDTIME = config.getInt(getPropName("daysentence.push.validTime"));
		//每日一句缓存刷新
		DAYSENTENCE_REFRESH_ISWORK = config.getBoolean(getPropName("daysentence.refresh.isWork"));
		DAYSENTENCE_REFRESH_CRONEXP = config.getString(getPropName("daysentence.refresh.cronExpression"));
		//每日一句生产
		DAYSENTENCE_PRODUCE_ISWORK = config.getBoolean(getPropName("daysentence.produce.isWork"));
		DAYSENTENCE_PRODUCE_CRONEXP = config.getString(getPropName("daysentence.produce.cronExpression"));
		DAYSENTENCE_PRODUCE_VALIDTIME = config.getInt(getPropName("daysentence.produce.validTime"));
		
		//清除过期翻译缓存
		TRANSCACHE_CLEAREXPIRATION_ISWORK = config.getBoolean(getPropName("transCache.clearExpiration.isWork"));
		TRANSCACHE_CLEAREXPIRATION_CRONEXP = config.getString(getPropName("transCache.clearExpiration.cronExpression"));
		
		//译库入库
		TRANLIB_SAVE_ISWORK = config.getBoolean(getPropName("transLib.save.isWork"));
		TRANLIB_SAVE_CRONEXP = config.getString(getPropName("transLib.save.cronExpression"));
		//译库缓存重新加载
		TRANLIBCACHE_REFRESH_ISWORK = config.getBoolean(getPropName("transLibCache.refresh.isWork"));
		TRANLIBCACHE_REFRESH_CRONEXP = config.getString(getPropName("transLibCache.refresh.cronExpression"));
		//译库缓存命中次数入库
		TRANLIBCACHE_SAVEHIT_ISWORK = config.getBoolean(getPropName("transLibCache.saveHit.isWork"));
		TRANLIBCACHE_SAVEHIT_CRONEXP = config.getString(getPropName("transLibCache.saveHit.cronExpression"));

		//翻译差异入库
		TRANSDIFF_SAVE_ISWORK = config.getBoolean(getPropName("transDiff.save.isWork"));
		TRANSDIFF_SAVE_CRONEXP = config.getString(getPropName("transDiff.save.cronExpression"));
		//翻译差异清除
		TRANSDIFF_CLEAR_ISWORK = config.getBoolean(getPropName("transDiff.clear.isWork"));
		TRANSDIFF_CLEAR_CRONEXP = config.getString(getPropName("transDiff.clear.cronExpression"));
		
		//超时拒单
		ORDER_REJECT_ISWORK = config.getBoolean(getPropName("order.reject.isWork"));
		ORDER_REJECT_CRONEXP = config.getString(getPropName("order.reject.cronExpression"));
		ORDER_REJECT_VALIDTIME = config.getInt(getPropName("order.reject.validTime"));
		
		//退款
		ORDER_REFUND_ISWORK = config.getBoolean(getPropName("order.refund.isWork"));
		ORDER_REFUND_CRONEXP = config.getString(getPropName("order.refund.cronExpression"));
		ORDER_REFUND_VALIDTIME = config.getInt(getPropName("order.refund.validTime"));
		
		//取消超时订单
		ORDER_CANCEL_ISWORK = config.getBoolean(getPropName("order.cancel.isWork"));
		ORDER_CANCEL_CRONEXP = config.getString(getPropName("order.cancel.cronExpression"));
		ORDER_CANCEL_VALIDTIME = config.getInt(getPropName("order.cancel.validTime"));
		
		//订单佣金结算
		ORDER_COMMISSION_ISWORK = config.getBoolean(getPropName("order.commission.isWork"));
		ORDER_COMMISSION_CRONEXP = config.getString(getPropName("order.commission.cronExpression"));
		
		//即将开始处理订单监控
		ORDERMONITOR_SOONDEAL_ISWORK = config.getBoolean(getPropName("orderMonitor.soonDeal.isWork"));
		ORDERMONITOR_SOONDEAL_CRONEXP = config.getString(getPropName("orderMonitor.soonDeal.cronExpression"));
		ORDERMONITOR_SOONDEAL_VALIDTIME = config.getInt(getPropName("orderMonitor.soonDeal.validTime"));

		//待处理订单监控
		ORDERMONITOR_WAITDEAL_ISWORK = config.getBoolean(getPropName("orderMonitor.waitDeal.isWork"));
		ORDERMONITOR_WAITDEAL_CRONEXP = config.getString(getPropName("orderMonitor.waitDeal.cronExpression"));
		ORDERMONITOR_WAITDEAL_VALIDTIME = config.getInt(getPropName("orderMonitor.waitDeal.validTime"));
		
		//未完成订单监控
		ORDERMONITOR_UNDEALT_ISWORK = config.getBoolean(getPropName("orderMonitor.undealt.isWork"));
		ORDERMONITOR_UNDEALT_CRONEXP = config.getString(getPropName("orderMonitor.undealt.cronExpression"));
		ORDERMONITOR_UNDEALT_VALIDTIME = config.getInt(getPropName("orderMonitor.undealt.validTime"));
		
		//资讯订阅处理
		ARTICLESUB_SUCCESSDEAL_ISWORK = config.getBoolean(getPropName("articleSub.successDeal.isWork"));
		ARTICLESUB_SUCCESSDEAL_CRONEXP = config.getString(getPropName("articleSub.successDeal.cronExpression"));
		ARTICLESUB_SUCCESSDEAL_VALIDTIME = config.getInt(getPropName("articleSub.successDeal.validTime"));
				
		//套餐订单处理
		PACKAGEORDER_DEAL_ISWORK = config.getBoolean(getPropName("packageOrder.deal.isWork"));
		PACKAGEORDER_DEAL_CRONEXP = config.getString(getPropName("packageOrder.deal.cronExpression"));
		PACKAGEORDER_DEAL_VALIDTIME = config.getInt(getPropName("packageOrder.deal.validTime"));
		
		//众包任务计数入库
		CROWDTASK_COUNTSAVE_ISWORK = config.getBoolean(getPropName("crowdtask.countSave.isWork"));
		CROWDTASK_COUNTSAVE_CRONEXP = config.getString(getPropName("crowdtask.countSave.cronExpression"));
		//众包任务自动创建
		CROWDTASK_AUTOCREATE_ISWORK = config.getBoolean(getPropName("crowdtask.autoCreate.isWork"));
		CROWDTASK_AUTOCREATE_CRONEXP = config.getString(getPropName("crowdtask.autoCreate.cronExpression"));
		CROWDTASK_AUTOCREATE_VALIDTIME = config.getInt(getPropName("crowdtask.autoCreate.validTime"));
		//众包任务过期取消
		CROWDTASK_CANCEL_ISWORK = config.getBoolean(getPropName("crowdtask.cancel.isWork"));
		CROWDTASK_CANCEL_CRONEXP = config.getString(getPropName("crowdtask.cancel.cronExpression"));
		CROWDTASK_CANCEL_VALIDTIME = config.getInt(getPropName("crowdtask.cancel.validTime"));
				
		//用户众包任务奖励发放
		USERCROWDTASK_REWARDGRANT_ISWORK = config.getBoolean(getPropName("userCrowdtask.rewardGrant.isWork"));
		USERCROWDTASK_REWARDGRANT_CRONEXP = config.getString(getPropName("userCrowdtask.rewardGrant.cronExpression"));
		USERCROWDTASK_REWARDGRANT_VALIDTIME = config.getInt(getPropName("userCrowdtask.rewardGrant.validTime"));
		//用户众包任务提醒完成
		USERCROWDTASK_REMINDTOFINISH_ISWORK = config.getBoolean(getPropName("userCrowdtask.remindToFinish.isWork"));
		USERCROWDTASK_REMINDTOFINISH_CRONEXP = config.getString(getPropName("userCrowdtask.remindToFinish.cronExpression"));
		USERCROWDTASK_REMINDTOFINISH_VALIDTIME = config.getInt(getPropName("userCrowdtask.remindToFinish.validTime"));
		//用户众包任务过期取消
		USERCROWDTASK_CANCEL_ISWORK = config.getBoolean(getPropName("userCrowdtask.cancel.isWork"));
		USERCROWDTASK_CANCEL_CRONEXP = config.getString(getPropName("userCrowdtask.cancel.cronExpression"));
		USERCROWDTASK_CANCEL_VALIDTIME = config.getInt(getPropName("userCrowdtask.cancel.validTime"));
		//用户众包任务删除文件
		USERCROWDTASK_DELETEFILE_ISWORK = config.getBoolean(getPropName("userCrowdtask.deleteFile.isWork"));
		USERCROWDTASK_DELETEFILE_CRONEXP = config.getString(getPropName("userCrowdtask.deleteFile.cronExpression"));
		USERCROWDTASK_DELETEFILE_VALIDTIME = config.getInt(getPropName("userCrowdtask.deleteFile.validTime"));
	   	
	   	//同城采集结果分拣
	   	LOALTASK_SPIDERSORTING_ISWORK = config.getBoolean(getPropName("localTask.spiderSorting.isWork"));
	   	LOALTASK_SPIDERSORTING_CRONEXP = config.getString(getPropName("localTask.spiderSorting.cronExpression"));
	   	LOALTASK_SPIDERSORTING_VALIDTIME = config.getInt(getPropName("localTask.spiderSorting.validTime"));
		
	   	//就业精选订阅通知
	   	LOALTASK_SUBNOTIFY_ISWORK = config.getBoolean(getPropName("localTask.subNotify.isWork"));
	   	LOALTASK_SUBNOTIFY_CRONEXP = config.getString(getPropName("localTask.subNotify.cronExpression"));
	   	LOALTASK_SUBNOTIFY_VALIDTIME = config.getInt(getPropName("localTask.subNotify.validTime"));
	   	
		//资讯标签缓存重载
		ARTICLETAGCACHE_REFRESH_ISWORK = config.getBoolean(getPropName("articleTagCache.refresh.isWork"));
		ARTICLETAGCACHE_REFRESH_CRONEXP = config.getString(getPropName("articleTagCache.refresh.cronExpression"));
		
		//红包过期处理
		REDPACKET_EXPIREDEAL_ISWORK = config.getBoolean(getPropName("redPacket.expireDeal.isWork"));
		REDPACKET_EXPIREDEAL_CRONEXP = config.getString(getPropName("redPacket.expireDeal.cronExpression"));
		
		//红包取消处理
		REDPACKET_CANCELDEAL_ISWORK = config.getBoolean(getPropName("redPacket.cancelDeal.isWork"));
		REDPACKET_CANCELDEAL_CRONEXP = config.getString(getPropName("redPacket.cancelDeal.cronExpression"));
		REDPACKET_CANCELDEAL_VALIDTIME = config.getInt(getPropName("redPacket.cancelDeal.validTime"));
		
		//用户黑名单缓存重载
		APPUSERFORBIDCACHE_REFRESH_ISWORK = config.getBoolean(getPropName("appUserForbidCache.refresh.isWork"));
		APPUSERFORBIDCACHE_REFRESH_CRONEXP = config.getString(getPropName("appUserForbidCache.refresh.cronExpression"));
		
		//参加完成订单活动
		TASK_FINISHORDER_ISWORK = config.getBoolean(getPropName("task.finishActivity.isWork"));
		TASK_FINISHORDER_CRONEXP = config.getString(getPropName("task.finishActivity.cronExpression"));
		TASK_FINISHORDER_VALIDTIME = config.getInt(getPropName("task.finishActivity.validTime"));
		
		//每日监控
		MONITOR_PERDAY_ISWORK = config.getBoolean(getPropName("monitor.perday.isWork"));
		MONITOR_PERDAY_CRONEXP = config.getString(getPropName("monitor.perday.cronExpression"));
		
		//清理临时文件
		FILE_CLEANTEMP_ISWORK = config.getBoolean(getPropName("file.cleanTemp.isWork"));
		FILE_CLEANTEMP_CRONEXP = config.getString(getPropName("file.cleanTemp.cronExpression"));
		
		//清除过期锁
		LOCK_CLEAR_ISWORK = config.getBoolean(getPropName("lock.clear.isWork"));
		LOCK_CLEAR_CRONEXP = config.getString(getPropName("lock.clear.cronExpression"));
		LOCK_CLEAR_VALIDTIME = config.getInt(getPropName("lock.clear.validTime"));
	}
}
