package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ICrowdTaskPlatformDao;
import com.qcmz.cmc.entity.CmcCtPlatform;
import com.qcmz.cmc.service.ICrowdTaskPlatformService;

@Service
public class CrowdTaskPlatformServiceImpl implements ICrowdTaskPlatformService {
	@Autowired
	private ICrowdTaskPlatformDao crowdTaskPlatformDao;
	
	/**
	 * 获取所有适用平台列表
	 * @return
	 */
	public List<CmcCtPlatform> findPlatform(){
		return crowdTaskPlatformDao.findPlatform();
	}
	
	/**
	 * 获取任务适用平台列表
	 * @param taskId
	 * @return
	 */
	public List<CmcCtPlatform> findPlatform(Long taskId){
		return crowdTaskPlatformDao.findPlatform(taskId);
	}
	
	/**
	 * 任务适用平台列表入库
	 * @param taskId
	 * @param list
	 */
	public void saveAll(Long taskId, List<CmcCtPlatform> list){
		if(list!=null && !list.isEmpty()){
			for (CmcCtPlatform pf : list) {
				pf.setTaskid(taskId);
			}
			crowdTaskPlatformDao.saveOrUpdateAll(list);
		}
	} 
	
	/**
	 * 任务适用平台列表入库
	 * @param taskId
	 * @param list
	 */
	public void updateAll(Long taskId, List<CmcCtPlatform> list){
		deleteAll(taskId);
		if(list!=null && !list.isEmpty()){
			for (CmcCtPlatform pf : list) {
				pf.setTaskid(taskId);
			}
			crowdTaskPlatformDao.saveOrUpdateAll(list);
		}
	}
	
	/**
	 * 清除任务适用平台列表
	 * @param taskId
	 */
	public void deleteAll(Long taskId){
		crowdTaskPlatformDao.deleteAll(taskId);
	}
}
