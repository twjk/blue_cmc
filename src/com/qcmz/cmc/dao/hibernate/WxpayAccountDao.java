package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IWxpayAccountDao;
import com.qcmz.cmc.entity.CmcBWxpayaccount;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class WxpayAccountDao extends BaseDAO implements IWxpayAccountDao {
	/**
	 * 获取所有微信支付账号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcBWxpayaccount> findAccount(){
		return (List<CmcBWxpayaccount>) qryList("from CmcBWxpayaccount");
	}
}
