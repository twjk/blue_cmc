package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcProxyAccount;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IProxyAccountDao extends IBaseDAO {
	/**
	 * 获取有效的帐户列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxyAccount> findValidAccount();
	/**
	 * 获取指定代理的帐户列表
	 * @param proxyId
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxyAccount> findAccount(Long proxyId);
	/**
	 * 获取单个帐户信息
	 * @param proxyId
	 * @param account
	 * @return
	 * 修改历史：
	 */
	public CmcProxyAccount getAccount(Long proxyId, String account);
	/**
	 * 保存访问次数	 
	 * 修改历史：
	 */
	public void saveCallCount(Long accountId, Long count);
	/**
	 * 增加访问次数
	 * @param accountId
	 * @param delta 增量
	 */
	public void increaseCallCount(Long accountId, Long delta);
	/**
	 * 更新账户状态
	 * @param accountId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(Long accountId, Integer status);
	/**
	 * 重新启用帐户	 
	 * 修改历史：
	 */
	public void restartAccount(Long accountId);
	/**
	 * 重新启用停用的帐户	 
	 * 修改历史：
	 */
	public void restartAccount();
}
