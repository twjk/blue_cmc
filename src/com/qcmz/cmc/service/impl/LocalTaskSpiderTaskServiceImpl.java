package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ILocalTaskSpiderTaskDao;
import com.qcmz.cmc.entity.CmcLtSpd;
import com.qcmz.cmc.entity.CmcLtSpdtask;
import com.qcmz.cmc.service.ILocalTaskSpiderTaskService;

@Service
public class LocalTaskSpiderTaskServiceImpl implements ILocalTaskSpiderTaskService {
	@Autowired
	private ILocalTaskSpiderTaskDao localTaskSpiderTaskDao;
	
	/**
	 * 获取有效的采集任务，带来源
	 * @param maxTime 上次抓取时间<=maxTime
	 * @return
	 */
	public List<CmcLtSpdtask> findValidTaskJoin(Date maxTime){
		return localTaskSpiderTaskDao.findValidTaskJoin(maxTime);
	}
	
	/**
	 * 保存采集结果
	 * @param spdTaskId
	 * @param lastIdentify
	 * @param spdList
	 */
	public void saveSpiderResult(Long spdTaskId, String lastIdentify, List<CmcLtSpd> spdList){
		localTaskSpiderTaskDao.saveOrUpdateAll(spdList);
		localTaskSpiderTaskDao.updateSpiderResult(spdTaskId, lastIdentify);
	}
}
