package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.entity.CmcWalletAccount;
import com.qcmz.cmc.ws.provide.vo.WalletAccountBean;
import com.qcmz.framework.page.PageBean;

public interface IWalletAccountService {
	/**
	 * 分页获取帐户
	 * @param map
	 * @param pageBean<CmcWalletAccount>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取帐户
	 * @param userId
	 * @return
	 */
	public CmcWalletAccount getAccount(Long userId);
	/**
	 * 获取帐户，带用户信息
	 * @param userId
	 * @return
	 */
	public CmcWalletAccount getAccountJoinUser(Long userId);
	/**
	 * 获取帐户信息
	 * @param userId
	 * @return
	 */
	public WalletAccountBean getAccountInfo(Long userId);
	/**
	 * 创建帐户
	 * @param userId
	 * @return
	 */
	public CmcWalletAccount saveAccount(Long userId, String serviceType);
	/**
	 * 更新帐户金额
	 * @param userId
	 */
	public void updateAmount(Long userId);
}
