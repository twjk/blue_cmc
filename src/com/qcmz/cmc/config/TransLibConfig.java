package com.qcmz.cmc.config;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.stereotype.Component;

import com.qcmz.framework.config.AbstractConfig;

/**
 * 类说明：译库配置
 * 修改历史：
 */
@Component
public class TransLibConfig extends AbstractConfig {
	/**
	 * 配置分类
	 */
	public static String CATEGORY;
	
	/**
	 * 译库文件版本号
	 */
	public static String TRANSLIB_DOWNLOAD_VERSION;
	/**
	 * 译库文件加密密钥
	 */
	public static String TRANSLIB_DOWNLOAD_KEY;
	/**
	 * 译库文件MD5
	 */
	public static String TRANSLIB_DOWNLOAD_MD5;
	/**
	 * 译库文件下载地址
	 */
	public static String TRANSLIB_DOWNLOAD_URL;

	/** 
	 * @throws ConfigurationException
	 * 修改历史：
	 */
	@Override
	protected void loadConfig() throws ConfigurationException {
		setConfig("translib.properties");
	}

	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void readConfig() {
		CATEGORY = category;
		
		TRANSLIB_DOWNLOAD_VERSION = config.getString(getPropName("download.ver"));
		TRANSLIB_DOWNLOAD_KEY = config.getString(getPropName("download.key"));
		TRANSLIB_DOWNLOAD_MD5 = config.getString(getPropName("download.md5"));
		TRANSLIB_DOWNLOAD_URL = config.getString(getPropName("download.url"));
	}

}
