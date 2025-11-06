package com.qcmz.cmc.ws.provide.vo;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.ws.vo.Response;

/**
 * 类说明：翻译返回
 * 修改历史：
 */
public class TransResp extends Response {
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 翻译结果
	 */
	private List<TransResult> trans_result = new ArrayList<TransResult>();
	
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
	public List<TransResult> getTrans_result() {
		return trans_result;
	}
	public void setTrans_result(List<TransResult> trans_result) {
		this.trans_result = trans_result;
	}
}
