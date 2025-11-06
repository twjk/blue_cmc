package com.qcmz.cmc.ws.provide.vo;


/**
 * 类说明：文本翻译详细信息
 * 修改历史：
 */
public class DubOrderDetailBean extends OrderDetailBean{
	/**
	 * 作品名称
	 */
	private String title;
	/**
	 * 配音文本
	 */
	private String txt;
	/**
	 * 配音员名称
	 */
	private String dubberTitle;
	
	public DubOrderDetailBean() {
		super();
	}
	public DubOrderDetailBean(OrderDetailBean orderDetail) {
		super(orderDetail);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public String getDubberTitle() {
		return dubberTitle;
	}
	public void setDubberTitle(String dubberTitle) {
		this.dubberTitle = dubberTitle;
	}
}