package com.qcmz.cmc.vo;

import com.qcmz.cmc.config.TransConfig;

/**
 * 类说明：临时缓存的翻译结果
 * 修改历史：
 */
public class TransResult4Cache {
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 失效时间
	 */
	private long expiration;
	/**
	 * 命中次数
	 */
	private int times = 1;
	/**
	 * 是否已入库
	 */
	private boolean saved;
	/**
	 * 相似度
	 */
	private int similar;
	
	public TransResult4Cache(String dst, int similar) {
		super();
		this.dst = dst;
		this.similar = similar;
		this.expiration = System.currentTimeMillis() + TransConfig.TRANS_CACHE_DURATION;
	}

	/**
	 * 命中处理：次数+1，并延时
	 * @param duration
	 * 修改历史：
	 */
	public void hited(){
		times++;
		expiration = System.currentTimeMillis() + times*TransConfig.TRANS_CACHE_DURATION;
	}
	
	public void saved(){
		saved = true;
	}
	
	/**
	 * 是否有效
	 * @return
	 * 修改历史：
	 */
	public boolean isValid(){
		return System.currentTimeMillis()<expiration;
	}
	
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public long getExpiration() {
		return expiration;
	}
	public void setExpiration(long expiration) {
		this.expiration = expiration;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public boolean isSaved() {
		return saved;
	}
	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	public int getSimilar() {
		return similar;
	}
	public void setSimilar(int similar) {
		this.similar = similar;
	}
}
