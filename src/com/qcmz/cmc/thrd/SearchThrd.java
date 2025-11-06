package com.qcmz.cmc.thrd;

import com.qcmz.cmc.process.TransProcess;
import com.qcmz.framework.thrd.AbstractThrd;

/**
 * 类说明：关联信息检索
 * 修改历史：
 */
public class SearchThrd extends AbstractThrd {
	private TransProcess transProcess;
	/**
	 * 语言
	 */
	private String lang;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 纬度
	 */
	private String lat;
	
	public SearchThrd() {
		super();
	}

	public SearchThrd(TransProcess transProcess, String lang, String content, String lon, String lat) {
		super();
		this.transProcess = transProcess;
		this.lang = lang;
		this.content = content;
		this.lon = lon;
		this.lat = lat;
	}

	/** 
	 * 
	 * 修改历史：
	 */
	@Override
	protected void work() {
		bean = transProcess.matchTerm(lang, content, lon, lat);
	}
}
