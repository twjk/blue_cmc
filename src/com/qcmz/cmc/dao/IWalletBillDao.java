package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.qcmz.cmc.entity.CmcWalletBill;
import com.qcmz.cmc.ws.provide.vo.WalletBillBean;
import com.qcmz.cmc.ws.provide.vo.WalletBillQueryBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IWalletBillDao extends IBaseDAO {
	/**
	 * 分页获取账单
	 * @param map
	 * @param pageBean<CmcWalletBill>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取用户最新账单
	 * @param userIds
	 * @return
	 */
	public List<CmcWalletBill> findLastBill(Set<Long> userIds);
	/**
	 * 分页获取账单列表
	 * @param query
	 * @return
	 */
	public List<WalletBillBean> findBillInfo(WalletBillQueryBean query);
	/**
	 * 获取账单数量
	 * @param userId not null
	 * @param billType not null
	 * @param date
	 * @return
	 */
	public Long getCount(Long userId, Integer billType, Date date);
	/**
	 * 获取用户账单
	 * @param userId not null
	 * @param orderId not null
	 * @param billType not null
	 */
	public CmcWalletBill getBill(Long userId, String orderId, Integer billType);
}
