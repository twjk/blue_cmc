package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcCtBlacklist;

public interface ICrowdTaskBlacklistService {
	/**
	 * 获取所有用户编号
	 * @return
	 */
	public List<Long> findUserId();
	/**
	 * 获取黑名单用户
	 * @param userId
	 * @return
	 */
	public CmcCtBlacklist getBlacklist(Long userId);
	/**
	 * 用户加入黑名单
	 * @param userId
	 */
	public void addBlacklist(Long userId);
	/**
	 * 用户移出黑名单
	 * @param userId
	 */
	public void deleteBlacklist(Long userId);
}
