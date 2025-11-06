package com.qcmz.cmc.dao;

import com.qcmz.cmc.entity.CmcCtDevice;
import com.qcmz.framework.dao.IBaseDAO;

public interface ICrowdTaskDeviceDao extends IBaseDAO {
	/**
	 * 根据uuid获取设备
	 * @param uuid
	 * @return
	 */
	public CmcCtDevice getDeviceByUuid(String uuid);
}
