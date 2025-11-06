package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcCtPlatform;

public interface ICrowdTaskPlatformService {
	/**
	 * 获取所有适用平台列表
	 * @return
	 */
	public List<CmcCtPlatform> findPlatform();
	/**
	 * 获取任务适用平台列表
	 * @param taskId
	 * @return
	 */
	public List<CmcCtPlatform> findPlatform(Long taskId);
	/**
	 * 任务适用平台列表入库
	 * @param taskId
	 * @param list
	 */
	public void saveAll(Long taskId, List<CmcCtPlatform> list);
	/**
	 * 任务适用平台列表入库
	 * @param taskId
	 * @param list
	 */
	public void updateAll(Long taskId, List<CmcCtPlatform> list);
	/**
	 * 清除任务适用平台列表
	 * @param taskId
	 */
	public void deleteAll(Long taskId);
}
