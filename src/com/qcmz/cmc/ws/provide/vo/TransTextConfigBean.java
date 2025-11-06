package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 短文快译配置
 */
public class TransTextConfigBean {
	/**
	 * 服务开关
	 */
	private boolean on;
	/**
	 * 服务时间段，格式HH:mm-HH:mm
	 */
	private String serviceTime;
	/**
	 * 服务时间段说明
	 */
	private String serviceTimeDesc;
	/**
	 * 响应时长，秒，-1不限
	 */
	private int timeout = -1;
	/**
	 * 最大字词数，-1表示不限
	 */
	private int maxWordNum = -1;
	/**
	 * 语言列表
	 */
	private List<TransTextLangBean> langs = new ArrayList<TransTextLangBean>();
	/**
	 * 限定服务渠道，空表示所有渠道均可用
	 */
	private String[] channels = new String[0];
	
	public boolean isOn() {
		return on;
	}
	public void setOn(boolean on) {
		this.on = on;
	}
	public String getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getServiceTimeDesc() {
		return serviceTimeDesc;
	}
	public void setServiceTimeDesc(String serviceTimeDesc) {
		this.serviceTimeDesc = serviceTimeDesc;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public List<TransTextLangBean> getLangs() {
		return langs;
	}
	public void setLangs(List<TransTextLangBean> langs) {
		this.langs = langs;
	}
	public int getMaxWordNum() {
		return maxWordNum;
	}
	public void setMaxWordNum(int maxWordNum) {
		this.maxWordNum = maxWordNum;
	}
	public String[] getChannels() {
		return channels;
	}
	public void setChannels(String[] channels) {
		this.channels = channels;
	}
}
