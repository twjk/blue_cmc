package com.qcmz.cmc.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 4, 2014 2:56:53 PM
 * 修改历史：
 */
public class ApiKeyTypeCallBean {
	/**
	 * key类型
	 */
	private String keyType;
	/**
	 * 访问时间,yyyy-mm-dd
	 */
	private String callDate;
	/**
	 * 使用次数
	 */
	private Long callTimes = 0L;
	
	public ApiKeyTypeCallBean() {
		super();
	}

	public ApiKeyTypeCallBean(String keyType, String callDate, Long callTimes) {
		super();
		this.keyType = keyType;
		this.callDate = callDate;
		this.callTimes = callTimes;
	}



	public String getKeyType() {
		return keyType;
	}

	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}

	public String getCallDate() {
		return callDate;
	}

	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}

	public Long getCallTimes() {
		return callTimes;
	}

	public void setCallTimes(Long callTimes) {
		this.callTimes = callTimes;
	}
}
