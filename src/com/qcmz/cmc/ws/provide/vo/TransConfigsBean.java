package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

public class TransConfigsBean {
	/**
	 * 代理账户
	 */
	private List<ProxyAccountBean> proxyAccounts = new ArrayList<ProxyAccountBean>();
	/**
	 * 功能能力
	 */
	private CapsBean funcCapCfg = new CapsBean();
	/**
	 * 语音语言配置
	 */
	private List<Lang4SpeechBean> speechLangs = new ArrayList<Lang4SpeechBean>();
	
	/**
	 * 短文快译配置
	 */
	private TransTextConfigBean transTextCfg = new TransTextConfigBean();
	/**
	 * 图片翻译配置
	 */
	private TransPicConfigBean transPicCfg = new TransPicConfigBean();
	
	public TransTextConfigBean getTransTextCfg() {
		return transTextCfg;
	}
	public void setTransTextCfg(TransTextConfigBean transTextCfg) {
		this.transTextCfg = transTextCfg;
	}
	public TransPicConfigBean getTransPicCfg() {
		return transPicCfg;
	}
	public void setTransPicCfg(TransPicConfigBean transPicCfg) {
		this.transPicCfg = transPicCfg;
	}
	public CapsBean getFuncCapCfg() {
		return funcCapCfg;
	}
	public void setFuncCapCfg(CapsBean funcCapCfg) {
		this.funcCapCfg = funcCapCfg;
	}
	public List<ProxyAccountBean> getProxyAccounts() {
		return proxyAccounts;
	}
	public void setProxyAccounts(List<ProxyAccountBean> proxyAccounts) {
		this.proxyAccounts = proxyAccounts;
	}
	public List<Lang4SpeechBean> getSpeechLangs() {
		return speechLangs;
	}
	public void setSpeechLangs(List<Lang4SpeechBean> speechLangs) {
		this.speechLangs = speechLangs;
	}
}
