package com.qcmz.cmc.dao;

import java.util.Map;

import com.qcmz.cmc.entity.CmcRCombo;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface ITransComboOrderDao extends IBaseDAO {
	/**
	 * 分页获取优惠套餐，含订单信息
	 * @param map
	 * @param pageBean<CmcRCombo>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取用户已购买套餐订单数
	 * @param userId
	 * @param pkgId
	 * @return
	 */
	public Long getBoughtCount(Long userId, Long pkgId);
	/**
	 * 获取翻译订单套餐信息
	 * @param orderId
	 * @return
	 */
	public CmcRCombo getCombo(String orderId);
}
