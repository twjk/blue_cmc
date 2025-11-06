package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcCtSimilar;

public interface IUserCrowdTaskSimilarService {
	/**
	 * 获取相似列表
	 * @param contentType
	 * @return
	 */
	public List<CmcCtSimilar> findSimilar(Integer contentType);
	/**
	 * 获取数量
	 * @param similarUserId
	 * @param contentType
	 * @return
	 */
	public Long getCountBySimilarUserId(Long similarUserId, Integer contentType);
	/**
	 * 更新相似信息
	 * @param entity
	 */
	public void updateSimilar(CmcCtSimilar entity);
}
