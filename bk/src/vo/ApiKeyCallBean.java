package com.qcmz.cmc.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 4, 2014 2:56:53 PM
 * 修改历史：
 */
public class ApiKeyCallBean implements Comparable<ApiKeyCallBean>{
	private Long keyid;
	/**
	 * 使用次数
	 */
	private Long callTimes = 0L;
	
	public ApiKeyCallBean() {
		super();
	}
	
	public ApiKeyCallBean(Long keyid, Long callTimes) {
		super();
		this.keyid = keyid;
		this.callTimes = callTimes;
	}

	public Long getKeyid() {
		return keyid;
	}
	public void setKeyid(Long keyid) {
		this.keyid = keyid;
	}

	public Long getCallTimes() {
		return callTimes;
	}

	public void setCallTimes(Long callTimes) {
		this.callTimes = callTimes;
	}

	/** 
	 * @param o
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 5, 2014 4:11:07 PM
	 * 修改历史：
	 */
	@Override
	public int compareTo(ApiKeyCallBean o) {
		return this.getCallTimes().intValue()-o.getCallTimes().intValue();
	}
	
}
