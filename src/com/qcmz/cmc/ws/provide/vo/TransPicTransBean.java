package com.qcmz.cmc.ws.provide.vo;

/**
 * 类说明：图片翻译结果信息
 * 修改历史：
 */
public class TransPicTransBean {
	/**
	 * 翻译编号
	 */
	private Long transId;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 横坐标
	 */
	private int x;
	/**
	 * 纵坐标
	 */
	private int y;
	
	public Long getTransId() {
		return transId;
	}
	public void setTransId(Long transId) {
		this.transId = transId;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
