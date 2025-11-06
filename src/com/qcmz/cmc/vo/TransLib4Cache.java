package com.qcmz.cmc.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransLib4Cache {
	/**
	 * 译库类
	 */
	private String libClass;
	/**
	 * 译库编号
	 */
	private Long libId;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 按原文合成
	 */
	private int ttsSrc;
	/**
	 * 指定合成文本
	 */
	private String ttsText;
	/**
	 * 准确度
	 */
	private Integer similar;
	/**
	 * 命中次数
	 */
	private int times;
	
	public TransLib4Cache() {
		super();
	}

	public TransLib4Cache(TransLibraryEntity lib) {
		super();
		this.libClass = lib.getLibClass();
		this.libId = lib.getLibid();
		this.dst = lib.getDst();
		this.similar = lib.getSimilar();
		this.ttsSrc = lib.getTtssrc();
		this.ttsText = lib.getTtstext();
	}

	public void update(TransLibraryEntity lib){
		this.libClass = lib.getLibClass();
		this.libId = lib.getLibid();
		this.dst = lib.getDst();
		this.similar = lib.getSimilar();
		this.ttsSrc = lib.getTtssrc();
		this.ttsText = lib.getTtstext();
	}
	
	/**
	 * 计数+1 
	 * 修改历史：
	 */
	public void hited(){
		times++;
	}
	
	/**
	 * 计数回0 
	 * 修改历史：
	 */
	public void saved(){
		times = 0;
	}
	
	public String getLibClass() {
		return libClass;
	}
	public void setLibClass(String libClass) {
		this.libClass = libClass;
	}
	public Long getLibId() {
		return libId;
	}
	public void setLibId(Long libId) {
		this.libId = libId;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public Integer getSimilar() {
		return similar;
	}
	public void setSimilar(Integer similar) {
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
