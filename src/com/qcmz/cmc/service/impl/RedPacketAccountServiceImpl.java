package com.qcmz.cmc.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IRedPacketAccountDao;
import com.qcmz.cmc.entity.CmcRpAccount;
import com.qcmz.cmc.service.IRedPacketAccountService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.ws.provide.vo.RedPacketAccountBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class RedPacketAccountServiceImpl implements IRedPacketAccountService {
	@Autowired
	private IRedPacketAccountDao redPacketAccountDao;
	
	/**
	 * 分页获取红包账户列表
	 * @param map
	 * @param pageBean<CmcRpAccount>
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public void findAccount(Map<String, String> map, PageBean pageBean){
		redPacketAccountDao.findAccount(map, pageBean);
		List<CmcRpAccount> list = (List<CmcRpAccount>) pageBean.getResultList();
		
		if(list.isEmpty())return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcRpAccount comment : list) {
			userIds.add(comment.getUserid());
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRpAccount account : list) {
			account.setUser(userMap.get(account.getUserid()));
		}
	}
	
	/**
	 * 获取红包帐户信息
	 * @param userId
	 * @return
	 */
	public CmcRpAccount getAccount(Long userId){
		return redPacketAccountDao.getAccount(userId);
	}
	
	/**
	 * 获取红包帐户信息--带用户信息
	 * @param userId
	 * @return
	 */
	public CmcRpAccount getAccountJoin(Long userId){
		CmcRpAccount account = redPacketAccountDao.getAccount(userId);
		account.setUser(UserMap.getUser(userId));
		return account;
	}
	
	/**
	 * 获取红包帐户信息
	 * @param userId
	 * @return
	 */
	public RedPacketAccountBean getAccountInfo(Long userId){
		CmcRpAccount account = getAccount(userId);
		if(account==null){
			return new RedPacketAccountBean(userId);
		}
		
		return BeanConvertUtil.toRedPacketAccountBean(account);
	}
	
	/**
	 * 更新用户微信openid
	 * @param userId
	 * @param wxopenid
	 */
	public void updateWxopenid(Long userId, String wxopenid){
		redPacketAccountDao.updateWxopenid(userId, wxopenid);
	}
}
