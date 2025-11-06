package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IWalletAccountDao;
import com.qcmz.cmc.dao.IWalletBillDao;
import com.qcmz.cmc.entity.CmcWalletAccount;
import com.qcmz.cmc.entity.CmcWalletBill;
import com.qcmz.cmc.service.IWalletAccountService;
import com.qcmz.cmc.ws.provide.vo.WalletAccountBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class WalletAccountServiceImpl implements IWalletAccountService {
	@Autowired
	private IWalletAccountDao walletAccountDao;
	@Autowired
	private IWalletBillDao walletBillDao;
	
	
	/**
	 * 分页获取帐户
	 * @param map
	 * @param pageBean<CmcWalletAccount>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		walletAccountDao.queryByMapTerm(map, pageBean);
		List<CmcWalletAccount> list = (List<CmcWalletAccount>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		Set<Long> userIds = new HashSet<Long>();
		Map<Long, CmcWalletAccount> accountMap = new HashMap<Long, CmcWalletAccount>();
		for (CmcWalletAccount account : list) {
			userIds.add(account.getUserid());
			accountMap.put(account.getUserid(), account);
		}
		
		//获取用户信息
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcWalletAccount account : list) {
			account.setUser(userMap.get(account.getUserid()));
		}
		
		//获取最后一条
		CmcWalletAccount account = null;
		List<CmcWalletBill> bills = walletBillDao.findLastBill(userIds);
		for (CmcWalletBill bill : bills) {
			account = accountMap.get(bill.getUserid());
			if(account!=null){
				account.getBills().add(bill);
			}
		}
	}
	
	/**
	 * 获取帐户
	 * @param userId
	 * @return
	 */
	public CmcWalletAccount getAccount(Long userId){
		return (CmcWalletAccount) walletAccountDao.load(CmcWalletAccount.class, userId);
	}
	
	/**
	 * 获取帐户，带用户信息
	 * @param userId
	 * @return
	 */
	public CmcWalletAccount getAccountJoinUser(Long userId){
		CmcWalletAccount account = getAccount(userId);
		account.setUser(UserMap.getUser(userId));
		return account;
	}
	
	/**
	 * 获取帐户信息
	 * @param userId
	 * @return
	 */
	public WalletAccountBean getAccountInfo(Long userId){
		WalletAccountBean result = new WalletAccountBean(userId);
		
		CmcWalletAccount account = getAccount(userId);
		if(account!=null){
			result.setBalance(account.getBalance());
		}
		
		return result;
	}
	
	/**
	 * 创建帐户
	 * @param userId
	 * @return
	 */
	public CmcWalletAccount saveAccount(Long userId, String serviceType){
		CmcWalletAccount entity = new CmcWalletAccount();
		entity.setUserid(userId);
		entity.setServicetype(serviceType);
		entity.setCreatetime(new Date());
		walletAccountDao.save(entity);
		return entity;
	}
	
	/**
	 * 更新帐户金额
	 * @param userId
	 */
	public void updateAmount(Long userId){
		walletAccountDao.updateAmount(userId);
	}
}
