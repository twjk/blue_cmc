package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcBWxpayaccount;
import com.qcmz.framework.dao.IBaseDAO;

public interface IWxpayAccountDao extends IBaseDAO {
	/**
	 * 获取所有微信支付账号
	 * @return
	 */
	public List<CmcBWxpayaccount> findAccount();
}
