package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;

import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.framework.dao.IBaseDAO;

public interface IOrderItemDao extends IBaseDAO {
	/**
	 * 获取商品列表
	 * @param orderId
	 * @return
	 */
	public List<CmcRItem> findItem(String orderId);
	/**
	 * 获取商品列表
	 * @param orderIds
	 * @return
	 */
	public List<CmcRItem> findItem(List<String> orderIds);
	/**
	 * 获取商品列表，带订单信息
	 * @param orderCat not null
	 * @param dealStatus not null
	 * @param start not null
	 * @param end not null
	 * @return
	 */
	public List<CmcRItem> findItemJoinOrder(Integer orderCat, String dealStatus, Date start, Date end);
	/**
	 * 是否有已经购买的订单
	 * @param userId
	 * @param orderType
	 * @param itemId
	 * @param refId
	 * @return
	 */
	public boolean hasBought(Long userId, Integer orderType, Long itemId, String refId);
	/**
	 * 清除商品
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public void clearItem(String orderId);
}
