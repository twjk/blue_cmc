package com.qcmz.cmc.dao;

import java.util.Map;

import com.qcmz.cmc.entity.CmcRpAccount;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IRedPacketAccountDao extends IBaseDAO {

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
	 * 更新用户帐户信息
	 * @param userId
	 */
	public void updateAccount(Long userId);
	/**
	 * 更新用户微信openid
	 * @param userId
	 * @param wxopenid
	 */
	public void updateWxopenid(Long userId, String wxopenid);
}
