package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcSLock;


/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface ILockService {
	/**
	 * 获取锁
	 * @param lockType
	 * @param lockKey
	 * @return
	 * 修改历史：
	 */
	public CmcSLock getLock(String lockType, String lockKey);
	/**
	 * 添加锁
	 * @param lockType
	 * @param lockKey
	 * @param validTime 有效时长，秒，默认10秒
	 * @return
	 * 修改历史：
	 */
	public Long addLock(String lockType, String lockKey, int validTime, String ip);
	/**
	 * 更新失效时间
	 * @param lockId
	 * @param validTime
	 */
	public void updateExpireTime(Long lockId, int validTime);
	/**
	 * 解锁
	 * @param lockId
	 * 修改历史：
	 */
	public void deleteLock(Long lockId);
	/**
	 * 删除锁
	 * @param lockIds
	 * 修改历史：
	 */
	public void deleteLock(List<Long> lockIds);
	/**
	 * 解锁
	 * @param lockId
	 * 修改历史：
	 */
	public void deleteLock(String lockType, String lockKey);
	/**
	 * 解锁
	 * @param lockType
	 * @param lockKeys
	 * 修改历史：
	 */
	public void deleteLock(String lockType, List<String> lockKeys);
	/**
	 * 清除过期锁 
	 * 修改历史：
	 */
	public void clearExpiredLock();
}
