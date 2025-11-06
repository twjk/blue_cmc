package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcWalletRecharge;
import com.qcmz.cmc.vo.WalletRechargeCountBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IWalletRechargeDao extends IBaseDAO {
	/**
	 * 分页获取充值列表，带帐户
	 * @param map
	 * @param pageBean<CmcWalletRecharge>
	 * @return 
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 充值统计
	 * @param start
	 * @param end
	 * @return
	 */
	public List<WalletRechargeCountBean> findRechargeCount(Date start, Date end);
	/**
	 * 获取充值信息，带帐户
	 * @param orderId
	 * @return
	 */
	public CmcWalletRecharge getRechargeJoinAccount(String orderId);
	/**
	 * 更新充值状态
	 * @param orderId
	 * @param status
	 */
	public void updateStatus(String orderId, Integer status);
}
