package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcSLock;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface ILockDao extends IBaseDAO {
	/**
	 * 获取锁
	 * @param lockType
	 * @param lockKey
	 * @return
	 * 修改历史：
	 */
	public CmcSLock getLock(String lockType, String lockKey);
	/**
	 * 更新失效时间
	 * @param lockId
	 * @param validTime
	 */
	public void updateExpireTime(Long lockId, int validTime);
	/**
	 * 删除锁
	 * @param lockIds
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
	 * 删除锁
	 * @param lockType
	 * @param lockKey
	 * 修改历史：
	 */
	public void deleteLock(String lockType, String lockKey);
	/**
	 * 删除锁
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
