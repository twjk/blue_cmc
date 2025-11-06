package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IUserCrowdTaskSimilarDao;
import com.qcmz.cmc.entity.CmcCtSimilar;
import com.qcmz.cmc.service.IUserCrowdTaskSimilarService;

@Service
public class UserCrowdTaskSimilarServiceImpl implements IUserCrowdTaskSimilarService {
	@Autowired
	private IUserCrowdTaskSimilarDao userCrowdTaskSimilarDao;
	
	/**
	 * 获取相似列表
	 * @param contentType
	 * @return
	 */
	public List<CmcCtSimilar> findSimilar(Integer contentType){
		return userCrowdTaskSimilarDao.findSimilar(contentType);
	}
	
	/**
	 * 获取数量
	 * @param similarUserId
	 * @param contentType
	 * @return
	 */
	public Long getCountBySimilarUserId(Long similarUserId, Integer contentType){
		return userCrowdTaskSimilarDao.getCountBySimilarUserId(similarUserId, contentType);
	}
	
	/**
	 * 更新相似信息
	 * @param entity
	 */
	public void updateSimilar(CmcCtSimilar entity){
		userCrowdTaskSimilarDao.update(entity);
	}
}
