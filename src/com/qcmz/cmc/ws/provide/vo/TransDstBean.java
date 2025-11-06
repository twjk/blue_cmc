package com.qcmz.cmc.ws.provide.vo;

public class TransDstBean {
	/**
	 * 代理编号
	 */
	private long proxyId;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 相似度
	 */
	private int similar;
	
	/**
	 * 合成方式，0译文合成1原文合成2按指定译文合成
	 */
	private int ttsSrc;
	/**
	 * 指定合成文本
	 */
	private String ttsText;
	
	public TransDstBean() {
		super();
	}
	public TransDstBean(String dst) {
		super();
		this.dst = dst;
	}
	public long getProxyId() {
		return proxyId;
	}
	public void setProxyId(long proxyId) {
		this.proxyId = proxyId;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public int getSimilar() {
		return similar;
	}
	public void setSimilar(int similar) {
		this.similar = similar;
	}
	public int getTtsSrc() {
		return ttsSrc;
	}
	public void setTtsSrc(int ttsSrc) {
		this.ttsSrc = ttsSrc;
	}
	public String getTtsText() {
		return ttsText;
	}
	public void setTtsText(String ttsText) {
		this.ttsText = ttsText;
	}
}