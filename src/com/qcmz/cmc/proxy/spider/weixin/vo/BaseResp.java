package com.qcmz.cmc.proxy.spider.weixin.vo;

public class BaseResp {
	/**
	 * 0成功
	 */
	private Integer ret;
	private String errMsg;
	public Integer getRet() {
		return ret;
	}
	public void setRet(Integer ret) {
		this.ret = ret;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
