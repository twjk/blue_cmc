package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ILockDao;
import com.qcmz.cmc.entity.CmcSLock;
import com.qcmz.cmc.service.ILockService;
import com.qcmz.framework.util.DateUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class LockServiceImpl implements ILockService {
	@Autowired
	private ILockDao lockDao;
	
	/**
	 * 获取锁
	 * @param lockType
	 * @param lockKey
	 * @return
	 * 修改历史：
	 */
	public CmcSLock getLock(String lockType, String lockKey){
		return lockDao.getLock(lockType, lockKey);
	}
	
	/**
	 * 添加锁
	 * 数据库做了lockType+lockKey唯一限制，入库时可能会抛出异常，所以在事务外catch异常 处理
	 * @param lockType
	 * @param lockKey
	 * @param validTime 有效时长，秒；<=0时默认为10秒
	 * @return
	 * 修改历史：
	 */
	public Long addLock(String lockType, String lockKey, int validTime, String ip){
		CmcSLock lock = new CmcSLock();
		lock.setLocktype(lockType);
		lock.setLockkey(lockKey);
		lock.setCreatetime(new Date());
		lock.setCreateip(ip);
		lock.setExpiretime(DateUtil.addSecond(lock.getCreatetime(), validTime>0?validTime:10));
		
		lockDao.save(lock);
		return lock.getLockid();
	}
	
	/**
	 * 更新失效时间
	 * @param lockId
	 * @param validTime
	 */
	public void updateExpireTime(Long lockId, int validTime){
		lockDao.updateExpireTime(lockId, validTime);
	}
	
	/**
	 * 解锁
	 * @param lockId
	 * 修改历史：
	 */
	public void deleteLock(Long lockId){
//		lockDao.delete(CmcSLock.class, lockId);	先获取实例再删除，如果不存在会异常
//		lockDao.delete(entity);	//如果不存在会异常
		lockDao.deleteLock(lockId);
	}
	
	/**
	 * 删除锁
	 * @param lockIds
	 * 修改历史：
	 */
	public void deleteLock(List<Long> lockIds){
		lockDao.deleteLock(lockIds);
	}
	
	/**
	 * 解锁
	 * @param lockId
	 * 修改历史：
	 */
	public void deleteLock(String lockType, String lockKey){
		lockDao.deleteLock(lockType, lockKey);
	}
	
	/**
	 * 解锁
	 * @param lockType
	 * @param lockKeys
	 * 修改历史：
	 */
	public void deleteLock(String lockType, List<String> lockKeys){
		lockDao.deleteLock(lockType, lockKeys);
	}
	
	/**
	 * 清除过期锁 
	 * 修改历史：
	 */
	public void clearExpiredLock(){
		lockDao.clearExpiredLock();
	}
}
