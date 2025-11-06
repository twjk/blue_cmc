package com.qcmz.cmc.service;

import java.util.Date;

public interface IOrderLogService {
	/**
	 * 保存流转日志
	 * @param orderId
	 * @param dealStatus
	 * @param operCd
	 * @param operName
	 * @param operTime
	 * 修改历史：
	 */
	public void saveFlowLog(String orderId, String dealStatus, String operCd, String operName, Date operTime);
	/**
	 * 保存流转日志
	 * @param orderId
	 * @param dealStatus
	 * @param operCd
	 * @param operName
	 * @param operTime
	 * @param log
	 * 修改历史：
	 */
	public void saveFlowLog(String orderId, String dealStatus, String operCd, String operName, Date operTime, String log);
	/**
	 * 保存操作日志
	 * @param orderId
	 * @param dealStatus
	 * @param operCd
	 * @param operName
	 * @param log
	 * 修改历史：
	 */
	public void saveOperLog(String orderId, String dealStatus, String operCd, String operName, String log);
}
