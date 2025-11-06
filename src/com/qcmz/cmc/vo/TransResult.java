package com.qcmz.cmc.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Nov 27, 2014 6:56:04 PM
 * 修改历史：
 */
public class TransResult {
	/**
	 * 代理编号
	 */
	private Long proxyId;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 回译相似度
	 */
	private int similar;
	
	public Long getProxyId() {
		return proxyId;
	}
	public void setProxyId(Long proxyId) {
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
	
}
