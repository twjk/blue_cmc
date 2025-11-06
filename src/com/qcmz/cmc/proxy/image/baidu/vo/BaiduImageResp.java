package com.qcmz.cmc.proxy.image.baidu.vo;

public class BaiduImageResp {
	/**
	 * 唯一的log id，用于问题定位
	 */
	private Long log_id;
	/**
	 * 识别结果数
	 */
	private Integer result_num;
	public Long getLog_id() {
		return log_id;
	}
	public void setLog_id(Long log_id) {
		this.log_id = log_id;
	}
	public Integer getResult_num() {
		return result_num;
	}

	public void setResult_num(Integer result_num) {
		this.result_num = result_num;
	}
}
