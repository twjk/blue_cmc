package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.ws.provide.vo.RewardBillBean;
import com.qcmz.cmc.ws.provide.vo.RewardBillQueryBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IRewardBillDao extends IBaseDAO {
	/**
	 * 分页获取账单
	 * @param map
	 * @param pageBean<CmcRewardBill>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取用户账单列表
	 * @param query
	 * @return
	 */
	public List<RewardBillBean> findBillInfo(RewardBillQueryBean query);
	/**
	 * 获取账单数量
	 * @param userId not null
	 * @param billType not null
	 * @param subServiceType
	 * @param date 日期
	 * @return
	 */
	public Long getCount(Long userId, Integer billType, String subServiceType, Date date);
	/**
	 * 是否存在
	 * @param userId
	 * @param billType
	 * @param subServiceType
	 * @param orderId
	 * @return
	 */
	public Boolean isExist(Long userId, Integer billType, String subServiceType, String orderId);
}
