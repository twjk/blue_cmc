package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IProxyAccountDao;
import com.qcmz.cmc.entity.CmcProxyAccount;
import com.qcmz.cmc.service.IProxyAccountService;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.DateUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class ProxyAccountServiceImpl implements IProxyAccountService {
	@Autowired
	private IProxyAccountDao proxyAccountDao;

	/**
	 * 保存帐户信息
	 * @param account
	 * 修改历史：
	 */
	public void saveOrUpdateAccount(CmcProxyAccount account){
		proxyAccountDao.saveOrUpdate(account);
	}
	
	/**
	 * 保存访问次数
	 * @param accountId
	 * @param count
	 * 修改历史：
	 */
	public void saveCallCount(Long accountId, Long count){
		proxyAccountDao.saveCallCount(accountId, count);
	}
	
	/**
	 * 增加访问次数
	 * @param accountId
	 * @param delta 增量
	 */
	public void increaseCallCount(Long accountId, Long delta){
		proxyAccountDao.increaseCallCount(accountId, delta);
	}
	
	/**
	 * 获取帐户信息
	 * @param accountId
	 * @return
	 * 修改历史：
	 */
	public CmcProxyAccount loadAccount(Long accountId){
		return (CmcProxyAccount) proxyAccountDao.load(CmcProxyAccount.class, accountId);
	}
	
	/**
	 * 获取有效的帐户列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxyAccount> findValidAccount(){
		return proxyAccountDao.findValidAccount();
	}
	
	/**
	 * 获取指定代理的帐户列表
	 * @param proxyId
	 * @return
	 * 修改历史：
	 */
	public List<CmcProxyAccount> findAccount(Long proxyId){
		return proxyAccountDao.findAccount(proxyId);
	}
	
	/**
	 * 重新启用账户
	 * @param accountId
	 * 修改历史：
	 */
	public void restartAccount(Long accountId){
		proxyAccountDao.restartAccount(accountId);
	}
	
	
	/**
	 * 重新启用停用到期的帐户	 
	 * 修改历史：
	 */
	public void restartAccount(){
		proxyAccountDao.restartAccount();
	}
	
	/**
	 * 停用账户
	 * @param accountId
	 * 修改历史：
	 */
	public void stopAccount(Long accountId){
		proxyAccountDao.updateStatus(accountId, SystemConstants.STATUS_OFF);
	}
	
	/**
	 * 停用必应帐户
	 * @param account
	 * 修改历史：
	 */
	public void stopBingAccount(Long proxyId, String account){
		stopBingAccount(proxyAccountDao.getAccount(proxyId, account));
	}
	
	/**
	 * 停用必应帐户
	 * @param account
	 * 修改历史：
	 */
	public void stopBingAccount(Long accountId){
		stopBingAccount(loadAccount(accountId));
	}
	
	/**
	 * 停用必应帐户
	 * @param account
	 * 修改历史：
	 */
	public void stopBingAccount(CmcProxyAccount account){
		if(account==null) return;
		
		//下个月注册日期重新启用
		String day = DateUtil.format(account.getRegdate(), "dd");
		String yyyyMM = DateUtil.formatDate3(new Date());
		String restart = yyyyMM + "-" + day;
		Date date = DateUtil.parseDate(restart);
		if(date.getTime()<DateUtil.getSysDateOnly().getTime()){
			date = DateUtil.add(date, DateUtil.MONTH, 1);
		}
		
		account.setStatus(SystemConstants.STATUS_OFF);
		account.setRestartdate(date);
		
		proxyAccountDao.saveOrUpdate(account);
	}
}
