package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IProxyAccountDao;
import com.qcmz.cmc.entity.CmcProxyAccount;
import com.qcmz.framework.dao.impl.BaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Repository
public class ProxyAccountDao extends BaseDAO implements IProxyAccountDao {
	
	/**
	 * 获取有效的帐户列表
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcProxyAccount> findValidAccount(){
		return (List<CmcProxyAccount>) qryList("from CmcProxyAccount where status=1");
	}
	
	/**
	 * 获取指定代理的帐户列表
	 * @param proxyId
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcProxyAccount> findAccount(Long proxyId){
		return (List<CmcProxyAccount>) qryList("from CmcProxyAccount where status=1 and proxyid=?", proxyId);
	}
	
	/**
	 * 获取单个帐户信息
	 * @param proxyId
	 * @param account
	 * @return
	 * 修改历史：
	 */
	public CmcProxyAccount getAccount(Long proxyId, String account){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("proxyid", proxyId);
		params.put("account", account);
		
		return (CmcProxyAccount) load("from CmcProxyAccount where proxyid=:proxyid and account=:account", params);
	}

	/**
	 * 保存访问次数	 
	 * 修改历史：
	 */
	public void saveCallCount(Long accountId, Long count){
		String hql = "update CmcProxyAccount set called=? where accountid=?";
		update(hql, new Object[]{count, accountId});
	}
	
	/**
	 * 增加访问次数
	 * @param accountId
	 * @param delta 增量
	 */
	public void increaseCallCount(Long accountId, Long delta){
		String hql = "update CmcProxyAccount set called=called+? where accountid=?";
		update(hql, new Object[]{delta, accountId});
	}
	
	/**
	 * 更新账户状态
	 * @param accountId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(Long accountId, Integer status){
		update("update CmcProxyAccount set status=? where accountid=?", new Object[]{status, accountId});
	}
	
	/**
	 * 重新启用停用的帐户	 
	 * 修改历史：
	 */
	public void restartAccount(Long accountId){
		String hql = "update CmcProxyAccount set status=1, restartdate=null, called=0  where accountid=?";
		update(hql, accountId);
	}
	
	/**
	 * 重新启用停用的帐户	 
	 * 修改历史：
	 */
	public void restartAccount(){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		sbHql.append("update CmcProxyAccount set status=1, restartdate=null, called=0")
			 .append(" where status=0")
			 .append(" and restartdate<=curdate()")
		;
	
		updateBatch(sbHql.toString(), params);
	}
}
