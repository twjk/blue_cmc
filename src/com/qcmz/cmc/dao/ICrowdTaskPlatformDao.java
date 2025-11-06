package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcCtPlatform;
import com.qcmz.framework.dao.IBaseDAO;

public interface ICrowdTaskPlatformDao extends IBaseDAO {
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
	 * 清除任务适用平台列表
	 * @param taskId
	 */
	public void deleteAll(Long taskId);
}
