package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ILockDao;
import com.qcmz.cmc.entity.CmcSLock;
import com.qcmz.framework.dao.impl.BaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Repository
public class LockDao extends BaseDAO implements ILockDao {
	/**
	 * 获取锁
	 * @param lockType
	 * @param lockKey
	 * @return
	 * 修改历史：
	 */
	public CmcSLock getLock(String lockType, String lockKey){
		String hql = "from CmcSLock where locktype=:locktype and lockkey=:lockkey";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("locktype", lockType);
		params.put("lockkey", lockKey);
		return (CmcSLock) load(hql, params);
	}
	
	/**
	 * 更新失效时间
	 * @param lockId
	 * @param validTime
	 */
	public void updateExpireTime(Long lockId, int validTime){
		String hql = "update CMC_S_LOCK set expiretime=DATE_ADD(createtime,INTERVAL :interval SECOND) where lockid=:lockid";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("interval", validTime);
		params.put("lockid", lockId);
		updateBatchSQL(hql, params);
	} 
	
	/**
	 * 删除锁
	 * @param lockIds
	 * 修改历史：
	 */
	public void deleteLock(Long lockId){
		delete("delete from CmcSLock where lockid=?", lockId);
	}
	
	/**
	 * 删除锁
	 * @param lockIds
	 * 修改历史：
	 */
	public void deleteLock(List<Long> lockIds){
		String hql = "delete from CmcSLock where lockid in(:lockids)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lockids", lockIds);
		
		deleteByMap(hql, params);
	}
	
	/**
	 * 删除锁
	 * @param lockType
	 * @param lockKey
	 * 修改历史：
	 */
	public void deleteLock(String lockType, String lockKey){
		String hql = "delete from CmcSLock where locktype=:locktype and lockkey=:lockkey";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("locktype", lockType);
		params.put("lockkey", lockKey);
		
		deleteByMap(hql, params);
	}
	
	/**
	 * 删除锁
	 * @param lockType
	 * @param lockKeys
	 * 修改历史：
	 */
	public void deleteLock(String lockType, List<String> lockKeys){
		String hql = "delete from CmcSLock where locktype=:locktype and lockkey in(:lockkeys)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("locktype", lockType);
		params.put("lockkeys", lockKeys);
		
		deleteByMap(hql, params);
	}
	
	/**
	 * 清除过期锁 
	 * 修改历史：
	 */
	public void clearExpiredLock(){
		delete("delete from CmcSLock where expiretime<now()");
	}
}
