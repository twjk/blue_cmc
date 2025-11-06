package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcCtSimilar;
import com.qcmz.framework.dao.IBaseDAO;

public interface IUserCrowdTaskSimilarDao extends IBaseDAO {
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
}
