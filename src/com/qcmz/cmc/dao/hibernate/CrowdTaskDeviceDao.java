package com.qcmz.cmc.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ICrowdTaskDeviceDao;
import com.qcmz.cmc.entity.CmcCtDevice;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class CrowdTaskDeviceDao extends BaseDAO implements ICrowdTaskDeviceDao {
	/**
	 * 根据uuid获取设备
	 * @param uuid
	 * @return
	 */
	public CmcCtDevice getDeviceByUuid(String uuid){
		return (CmcCtDevice) load("from CmcCtDevice where uuid=?", uuid);
	}
}
