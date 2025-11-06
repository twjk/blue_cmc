package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcProxyAccount;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IProxyAccountService {
	/**
	 * 保存帐户信息
	 * @param account
	 * 修改历史：
	 */
	public void saveOrUpdateAccount(CmcProxyAccount account);
	/**
	 * 保存访问次数
	 * @param accountId
	 * @param count
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
	 * 获取帐户信息
	 * @param accountId
	 * @return
	 * 修改历史：
	 */
	public CmcProxyAccount loadAccount(Long accountId);
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
	 * 重新启用账户
	 * @param accountId
	 * 修改历史：
	 */
	public void restartAccount(Long accountId);
	/**
	 * 重新启用停用的帐户	 
	 * 修改历史：
	 */
	public void restartAccount();
	/**
	 * 停用账户
	 * @param accountId
	 * 修改历史：
	 */
	public void stopAccount(Long accountId);
	/**
	 * 停用必应帐户
	 * @param account
	 * 修改历史：
	 */
	public void stopBingAccount(Long proxyId, String account);	
	/**
	 * 停用必应帐户
	 * @param account
	 * 修改历史：
	 */
	public void stopBingAccount(Long accountId);
	/**
	 * 停用必应帐户
	 * @param account
	 * 修改历史：
	 */
	public void stopBingAccount(CmcProxyAccount account);
}
