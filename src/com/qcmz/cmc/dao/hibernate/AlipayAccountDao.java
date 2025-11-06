package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IAlipayAccountDao;
import com.qcmz.cmc.entity.CmcBAlipayaccount;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class AlipayAccountDao extends BaseDAO implements IAlipayAccountDao {
	/**
	 * 获取所有支付宝账号
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcBAlipayaccount> findAccount(){
		return (List<CmcBAlipayaccount>) qryList("from CmcBAlipayaccount");
	}
}
