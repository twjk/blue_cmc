package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ICrowdTaskPlatformDao;
import com.qcmz.cmc.entity.CmcCtPlatform;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class CrowdTaskPlatformDao extends BaseDAO implements ICrowdTaskPlatformDao {
	/**
	 * 获取所有适用平台列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtPlatform> findPlatform(){
		return (List<CmcCtPlatform>) qryList("from CmcCtPlatform");
	}
	
	/**
	 * 获取任务适用平台列表
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcCtPlatform> findPlatform(Long taskId){
		return (List<CmcCtPlatform>) qryList("from CmcCtPlatform where taskid=?", taskId);
	}
	
	/**
	 * 清除任务适用平台列表
	 * @param taskId
	 */
	public void deleteAll(Long taskId){
		delete("delete from CmcCtPlatform where taskid=?", taskId);
	}
}
