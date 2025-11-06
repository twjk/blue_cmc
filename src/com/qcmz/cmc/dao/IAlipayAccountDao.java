package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcBAlipayaccount;
import com.qcmz.framework.dao.IBaseDAO;

public interface IAlipayAccountDao extends IBaseDAO {
	/**
	 * 获取所有支付宝账号
	 * @return
	 */
	public List<CmcBAlipayaccount> findAccount();
}
