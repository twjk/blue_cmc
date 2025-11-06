package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.cmc.constant.BusinessConstant;

/**
 * 类说明：翻译结果
 * 修改历史：
 */
public class TransResult {
	/**
	 * 原文语言
	 */
	private String from;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 译文语言
	 */
	private String to;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 相似度
	 */
	private int similar;
	/**
	 * 翻译代理编号
	 */
	private long proxyid;
	/**
	 * 合成方式，0译文合成1原文合成2按指定译文合成
	 */
	private int ttsSrc;
	/**
	 * 指定合成文本
	 */
	private String ttsText;
	/**
	 * 分词列表
	 * 20170605为了版本兼容，弃用该属性，启用新属性keywords
	 */
	@Deprecated
	private List<TransTermBean> terms = new ArrayList<TransTermBean>();
	/**
	 * 分词列表
	 */
	private List<TransTermBean> keywords = new ArrayList<TransTermBean>();

	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public List<TransTermBean> getTerms() {
		return terms;
	}
	public void setTerms(List<TransTermBean> terms) {
		this.terms = terms;
	}
	public int getSimilar() {
		return similar;
	}
	public void setSimilar(int similar) {
		this.similar = similar;
	}
	public long getProxyid() {
		return proxyid;
	}
	public void setProxyid(long proxyid) {
		this.proxyid = proxyid;
	}
	public void setProxyid(Long proxyid) {
		if(proxyid==null || proxyid==0){
			proxyid = BusinessConstant.PROXYID_BAIDU;
		}
		this.proxyid = proxyid;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public List<TransTermBean> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<TransTermBean> keywords) {
		this.keywords = keywords;
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
