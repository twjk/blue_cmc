package com.qcmz.cmc.service;

import com.qcmz.cmc.entity.CmcCtDevice;

public interface ICrowdTaskDeviceService {
	/**
	 * 根据uuid获取设备
	 * @param uuid
	 * @return
	 */
	public CmcCtDevice getDeviceByUuid(String uuid);
	/**
	 * 添加设备
	 * @param uuid
	 * @param userId
	 */
	public void addDevice(String uuid, Long userId);
}
