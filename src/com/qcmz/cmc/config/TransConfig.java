package com.qcmz.cmc.config;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.stereotype.Component;

import com.qcmz.framework.config.AbstractConfig;

/**
 * 类说明：翻译配置
 * 修改历史：
 */
@Component
public class TransConfig extends AbstractConfig {
	/**
	 * 配置分类
	 */
	public static String CATEGORY;
	/**
	 * 最大翻译字符数
	 */
	public static int TRANS_MAXCHARS;
	/**
	 * 回译开关，0关闭1开启2有多通道时开启回译
	 */
	public static int BACKTRANS_ON;
	/**
	 * 回译最低匹配度，高于此值直接返回翻译结果，不试其他通道
	 */
	public static int TRANS_SIMILAR_MIN;
	
	/**
	 * 翻译结果缓存时长，毫秒
	 */
	public static long TRANS_CACHE_DURATION;
	/**
	 * 翻译结果入译库阀值，重复翻译次数超过该值则入库
	 */
	public static int TRANS_CACHE_DBTHRESHOLD;
	
	/**
	 * 翻译日志记录开关，0关闭1存入本地库2存入Azure库
	 */
	public static int TRANS_LOG_ON;
	/**
	 * 翻译日志批量入库数
	 */
	public static int TRANS_LOG_BATCHSIZE;

	/**
	 * 翻译差异留存时长，分钟
	 */
	public static int TRANS_DIFF_DURATION;
	
	/**
	 * 必应翻译超时时间,毫秒
	 */
	public static int BING_TRANS_TIMEOUT;
	/**
	 * 百度翻译超时时间,毫秒
	 */
	public static int BAIDU_TRANS_TIMEOUT;
	
	/**
	 * 图片翻译缩略图最大宽度
	 */
	public static int TRANSPIC_THUMB_MAXWIDTH;
	/**
	 * 图片翻译缩略图最大高度
	 */
	public static int TRANSPIC_THUMB_MAXHEIGHT;
	/**
	 * 图片翻译结果返回最大长度
	 */
	public static int TRANSPIC_TRANSRESULT_MAXLENGTH;
	
	/**
	 * 短文快译开关
	 */
	public static boolean TRANSTEXT_ON;
	/**
	 * 短文快译工作时间段HH:mm-HH:mm
	 */
	public static String TRANSTEXT_WORKTIME;
	/**
	 * 短文快译工作时间段HHmm-HHmm
	 */
	public static String TRANSTEXT_WORKTIME_FORMAT;
	/**
	 * 短文快译工作时间段描述
	 */
	public static String TRANSTEXT_WORKTIMEDESC;
	/**
	 * 短文快译响应时长，秒，-1不限
	 */
	public static int TRANSTEXT_TIMEOUT;
	/**
	 * 短文快译最大字词数，-1不限
	 */
	public static int TRANSTEXT_MAXWORDNUM;
	/**
	 * 短文快译限定渠道列表，空表示不限
	 */
	public static String[] TRANSTEXT_CHANNELS;
	
	/** 
	 * @throws ConfigurationException
	 * 修改历史：
	 */
	@Override
	protected void loadConfig() throws ConfigurationException {
		setConfig("trans.properties");
	}

	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void readConfig() {
		CATEGORY = category;
		
		TRANS_MAXCHARS = config.getInt(getPropName("maxchars"));
		BACKTRANS_ON = config.getInt(getPropName("backtrans.on"));
		TRANS_SIMILAR_MIN = config.getInt(getPropName("similar.min"));
		if(TRANS_SIMILAR_MIN>100){
			TRANS_SIMILAR_MIN = 100;
		}
		TRANS_CACHE_DURATION = config.getLong(getPropName("cache.duration"));
		TRANS_CACHE_DBTHRESHOLD = config.getInt(getPropName("cache.dbthreshold"));
		
		TRANS_LOG_ON = config.getInt(getPropName("log.on"));
		TRANS_LOG_BATCHSIZE = config.getInt(getPropName("log.batchsize"));
		
		TRANS_DIFF_DURATION = config.getInt(getPropName("diff.duration"));
		
		//翻译超时
		BING_TRANS_TIMEOUT = config.getInt(getPropName("bing.timeout"));
		BAIDU_TRANS_TIMEOUT = config.getInt(getPropName("baidu.timeout"));
		
		//图片翻译
		TRANSPIC_THUMB_MAXWIDTH = config.getInt(getPropName("transpic.thumb.maxWidth"));
		TRANSPIC_THUMB_MAXHEIGHT = config.getInt(getPropName("transpic.thumb.maxHeight"));
		TRANSPIC_TRANSRESULT_MAXLENGTH = config.getInt(getPropName("transpic.transResult.maxLength"));
		
		//文本翻译
		TRANSTEXT_ON = config.getBoolean(getPropName("transtext.on"));
		TRANSTEXT_WORKTIME = config.getString(getPropName("transtext.workTime"));
		TRANSTEXT_WORKTIME_FORMAT = TRANSTEXT_WORKTIME.replaceAll(":", "");
		TRANSTEXT_WORKTIMEDESC = config.getString(getPropName("transtext.workTimeDesc"));
		TRANSTEXT_TIMEOUT = config.getInt(getPropName("transtext.timeout"));
		TRANSTEXT_MAXWORDNUM = config.getInt(getPropName("transtext.maxWordNum"));
		TRANSTEXT_CHANNELS = config.getStringArray(getPropName("transtext.channels"));
	}

}
