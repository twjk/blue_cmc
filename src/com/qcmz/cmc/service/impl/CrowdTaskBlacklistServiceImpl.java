package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ICrowdTaskBlacklistDao;
import com.qcmz.cmc.entity.CmcCtBlacklist;
import com.qcmz.cmc.service.ICrowdTaskBlacklistService;

@Service
public class CrowdTaskBlacklistServiceImpl implements ICrowdTaskBlacklistService {
	@Autowired
	private ICrowdTaskBlacklistDao crowdTaskBlacklistDao;
	
	/**
	 * 获取所有用户编号
	 * @return
	 */
	public List<Long> findUserId(){
		return crowdTaskBlacklistDao.findUserId();
	}
	
	/**
	 * 获取黑名单用户
	 * @param userId
	 * @return
	 */
	public CmcCtBlacklist getBlacklist(Long userId){
		return (CmcCtBlacklist) crowdTaskBlacklistDao.load(CmcCtBlacklist.class, userId);
	}
	
	/**
	 * 用户加入黑名单
	 * @param userId
	 */
	public void addBlacklist(Long userId){
		CmcCtBlacklist bean = getBlacklist(userId);
		if(bean!=null) return;
		bean = new CmcCtBlacklist();
		bean.setUserid(userId);
		bean.setCreatetime(new Date());
		crowdTaskBlacklistDao.save(bean);
	}
	
	/**
	 * 用户移出黑名单
	 * @param userId
	 */
	public void deleteBlacklist(Long userId){
		crowdTaskBlacklistDao.delete(CmcCtBlacklist.class, userId);
	}
}
