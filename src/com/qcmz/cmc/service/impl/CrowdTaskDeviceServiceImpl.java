package com.qcmz.cmc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ICrowdTaskDeviceDao;
import com.qcmz.cmc.entity.CmcCtDevice;
import com.qcmz.cmc.service.ICrowdTaskDeviceService;

@Service
public class CrowdTaskDeviceServiceImpl implements ICrowdTaskDeviceService {
	@Autowired
	private ICrowdTaskDeviceDao crowdTaskDeviceDao;
	
	/**
	 * 根据uuid获取设备
	 * @param uuid
	 * @return
	 */
	public CmcCtDevice getDeviceByUuid(String uuid){
		return crowdTaskDeviceDao.getDeviceByUuid(uuid);
	}
	
	/**
	 * 添加设备
	 * @param uuid
	 * @param userId
	 */
	public void addDevice(String uuid, Long userId){
		CmcCtDevice bean = new CmcCtDevice();
		bean.setUuid(uuid);
		bean.setUserid(userId);
		bean.setCreatetime(new Date());
		crowdTaskDeviceDao.save(bean);
	}
}
