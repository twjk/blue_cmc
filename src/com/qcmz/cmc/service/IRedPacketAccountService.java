package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.entity.CmcRpAccount;
import com.qcmz.cmc.ws.provide.vo.RedPacketAccountBean;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;

public interface IRedPacketAccountService {

	/**
	 * 分页获取红包账户列表
	 * @param map
	 * @param pageBean<CmcRpAccount>
	 * 修改历史：
	 */
	public void findAccount(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取红包帐户信息
	 * @param userId
	 * @return
	 */
	public CmcRpAccount getAccount(Long userId);
	/**
	 * 获取红包帐户信息--带用户信息
	 * @param userId
	 * @return
	 */
	public CmcRpAccount getAccountJoin(Long userId);
	/**
	 * 获取红包帐户信息
	 * @param userId
	 * @return
	 * @exception ParamException
	 */
	public RedPacketAccountBean getAccountInfo(Long userId);
	/**
	 * 更新用户微信openid
	 * @param userId
	 * @param wxopenid
	 */
	public void updateWxopenid(Long userId, String wxopenid);
}
