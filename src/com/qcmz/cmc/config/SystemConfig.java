package com.qcmz.cmc.config;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.stereotype.Component;

import com.qcmz.bdc.ws.provide.locator.BdcWSLocator;
import com.qcmz.cms.ws.provide.locator.CmsWSLocator;
import com.qcmz.dmc.ws.provide.locator.AccessLogWS;
import com.qcmz.dmc.ws.provide.locator.DmcWSLocator;
import com.qcmz.framework.config.AbstractConfig;
import com.qcmz.framework.util.FileServiceUtil;
import com.qcmz.framework.ws.util.AuthUtil;
import com.qcmz.umc.ws.provide.locator.UmcWSLocator;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class SystemConfig extends AbstractConfig {
	/**
	 * 配置分类
	 */
	public static String CATEGORY;
	
	/**
	 * CMC服务地址
	 */
	public static String CMC_SERVER;
	/**
	 * CMC分发服务地址
	 */
	public static String[] CMC_DISTS;
	/**
	 * BDC外网服务地址
	 */
	public static String BDC_SERVER;

	/**
	 * 每日一句缓存最大记录数
	 */
	public static int DAYSENTENCE_MAXRESULT;
	/**
	 * 老翻译接口trans开关
	 */
	public static boolean TRANSWS_TRANS_SWITCH;
	
	/**
	 * 创建多少分钟后可以捡单
	 */
	public static int ORDERPICK_AFTERTIME;
	
	/**
	 * 钱包激励开关
	 */
	public static boolean WALLET_ENCOURAGE_SWITCH;
	/**
	 * 钱包激励一个用户每日可参加次数
	 */
	public static Integer WALLET_ENCOURAGE_DATETIMES;
	/**
	 * 钱包激励每次金额
	 */
	public static Double WALLET_ENCOURAGE_AMOUNT;
	
	/**
	 * 奖励金奖励开关
	 */
	public static boolean REWARD_REWARD_SWITCH;
	/**
	 * 奖励金奖励单用户每日可参加次数
	 */
	public static Integer REWARD_REWARD_DATETIMES;
	/**
	 * 奖励金奖励单次最大金额，元
	 */
	public static Double REWARD_REWARD_MAXAMOUNT;
	
	/**
	 * 尖叫红包有效天数
	 */
	public static int REDPACKET_VALIDDAYS;
	/**
	 * 尖叫红包服务费率(千分之几)
	 */
	public static int REDPACKET_SERVICEPERCENT;
	
	/**
	 * 系统管理员邮箱
	 */
	public static String[] MAILS;
	/**
	 * 经理邮箱
	 */
	public static String[] MANAGER_MAILS;
	
	/**
	 * 客服工作时间
	 */
	public static String CS_WORKTIME;
	/**
	 * 客服邮箱
	 */
	public static String[] CS_MAILS;
	/**
	 * 客服手机号
	 */
	public static String[] CS_MOBILES;
	/**
	 * 任务最大重试次数
	 */
	public static int TASK_RETRY;
	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void loadConfig() throws ConfigurationException{
		setConfig("system.properties");
	}

	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void readConfig() {
		CATEGORY = category;
		
		CMC_SERVER = config.getString(getPropName("cmc.server"));
		CMC_DISTS = config.getStringArray(getPropName("cmc.dists"));
		BDC_SERVER = config.getString(getPropName("bdc.server"));
		
		DAYSENTENCE_MAXRESULT = config.getInt(getPropName("daysentence.maxResult"), 50);
		
		TRANSWS_TRANS_SWITCH = config.getBoolean(getPropName("transws.trans.switch"));
		
		ORDERPICK_AFTERTIME = config.getInt(getPropName("orderpick.afterTime"),30);
		
		WALLET_ENCOURAGE_SWITCH = config.getBoolean(getPropName("wallet.encourage.switch"));
		WALLET_ENCOURAGE_DATETIMES = config.getInt(getPropName("wallet.encourage.dateTimes"));
		WALLET_ENCOURAGE_AMOUNT = config.getDouble(getPropName("wallet.encourage.amount"));
		
		REWARD_REWARD_SWITCH = config.getBoolean(getPropName("reward.reward.switch"));
		REWARD_REWARD_DATETIMES = config.getInt(getPropName("reward.reward.dateTimes"));
		REWARD_REWARD_MAXAMOUNT = config.getDouble(getPropName("reward.reward.maxAmount"));
		
		REDPACKET_VALIDDAYS = config.getInt(getPropName("redpacket.validDays"),7);
		REDPACKET_SERVICEPERCENT = config.getInt(getPropName("redpacket.servicePercent"));
		
		//任务最大重试次数
		TASK_RETRY = config.getInt(getPropName("task.retry"));
		
		MAILS = config.getStringArray(getPropName("mails"));
		MANAGER_MAILS = config.getStringArray(getPropName("manager.mails"));
		
		CS_WORKTIME = config.getString(getPropName("cs.workTime"));
		CS_MAILS = config.getStringArray(getPropName("cs.mails"));
		CS_MOBILES = config.getStringArray(getPropName("cs.mobiles"));
		
		//文件服务
		FileServiceUtil.init(config.getString(getPropName("fileService.proxy")), 
							 config.getString(getPropName("fileService.server")), 
							 config.getString(getPropName("fileService.rootDirName")));
		
		//接口服务
		AuthUtil.init("sys_cmc", "jk28A63398jBb6G8P5x64ri2", "6JQkH8ZU");
		BdcWSLocator.init(config.getString(getPropName("bdc.lanserver")));
		UmcWSLocator.init(config.getString(getPropName("umc.lanserver")));
		CmsWSLocator.init(config.getString(getPropName("cms.server")));
		DmcWSLocator.init(config.getString(getPropName("dmc.server")));
		
		//记录访问日志配置
		AccessLogWS.init(config.getInt(getPropName("logAccess.switch"), 0)
				, config.getStringArray(getPropName("logAccess.includes"))
				, config.getStringArray(getPropName("logAccess.excepts")));
	}
	
	public static boolean isDebug(){
		return CATEGORY.startsWith(CATEGORY_DEBUG);
	}
	
	public static boolean isRelease(){
		return CATEGORY.startsWith(CATEGORY_RELEASE);
	}
}
