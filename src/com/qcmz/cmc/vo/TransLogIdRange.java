package com.qcmz.cmc.vo;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransLogIdRange {
	private Long minLogId;
	private Long maxLogId;
	public TransLogIdRange() {
		super();
	}
	public TransLogIdRange(Long minLogId, Long maxLogId) {
		super();
		this.minLogId = minLogId;
		this.maxLogId = maxLogId;
	}
	public Long getMaxLogId() {
		return maxLogId;
	}
	public void setMaxLogId(Long maxLogId) {
		this.maxLogId = maxLogId;
	}
	public Long getMinLogId() {
		return minLogId;
	}
	public void setMinLogId(Long minLogId) {
		this.minLogId = minLogId;
	}
}
