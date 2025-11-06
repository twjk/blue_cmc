package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IRewardAccountDao;
import com.qcmz.cmc.entity.CmcRewardAccount;
import com.qcmz.cmc.service.IRewardAccountService;
import com.qcmz.cmc.ws.provide.vo.RewardAccountBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class RewardAccountServiceImpl implements IRewardAccountService {
	@Autowired
	private IRewardAccountDao rewardAccountDao;
	
	/**
	 * 分页获取帐户
	 * @param map
	 * @param pageBean<CmcRewardAccount>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		rewardAccountDao.queryByMapTerm(map, pageBean);
		List<CmcRewardAccount> list = (List<CmcRewardAccount>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		Set<Long> userIds = new HashSet<Long>();
		Map<Long, CmcRewardAccount> accountMap = new HashMap<Long, CmcRewardAccount>();
		for (CmcRewardAccount account : list) {
			userIds.add(account.getUserid());
			accountMap.put(account.getUserid(), account);
		}
		
		//获取用户信息
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRewardAccount account : list) {
			account.setUser(userMap.get(account.getUserid()));
		}
	}
	
	/**
	 * 获取帐户
	 * @param userId
	 * @return
	 */
	public CmcRewardAccount getAccount(Long userId){
		return (CmcRewardAccount) rewardAccountDao.load(CmcRewardAccount.class, userId);
	}
	
	/**
	 * 获取帐户，带用户信息
	 * @param userId
	 * @return
	 */
	public CmcRewardAccount getAccountJoinUser(Long userId){
		CmcRewardAccount account = getAccount(userId);
		account.setUser(UserMap.getUser(userId));
		return account;
	}
	
	/**
	 * 获取账户信息
	 * @param userId
	 * @return
	 */
	public RewardAccountBean getAccountInfo(Long userId){
		RewardAccountBean result = new RewardAccountBean(userId);
		CmcRewardAccount entity = getAccount(userId);
		if(entity!=null){
			result.setBalance(entity.getBalance());
			result.setRewardTotal(entity.getRewardtotal());
		}
		return result;
	}
	
	/**
	 * 创建帐户，如果已经存在不创建
	 * @param userId
	 * @return
	 */
	public CmcRewardAccount createAccount(String serviceType, Long userId){
		CmcRewardAccount account = getAccount(userId);
		if(account==null){
			UserSimpleBean user = UserMap.getUser(userId);
			
			account = new CmcRewardAccount();
			account.setUserid(userId);
			account.setServicetype(serviceType);
			account.setInviterid(user.getInviterId());
			account.setCreatetime(new Date());
			account.setStatus(SystemConstants.STATUS_ON);
			
			rewardAccountDao.save(account);
		}
		return account;
	}
	
	/**
	 * 更新帐户金额
	 * @param userId
	 */
	public void updateAmount(Long userId){
		rewardAccountDao.updateAmount(userId);
	}
	
	/**
	 * 更新帐户状态
	 * @param userId
	 * @param status
	 */
	public void updateStatus(Long userId, Integer status){
		rewardAccountDao.updateStatus(userId, status);
	}
}
