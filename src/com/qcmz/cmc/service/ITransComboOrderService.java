package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcComboPackage;
import com.qcmz.cmc.entity.CmcRCombo;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.ws.provide.vo.TransComboAddBean;
import com.qcmz.framework.page.PageBean;

public interface ITransComboOrderService {
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
	 * 获取套餐订单信息，含套餐、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId);
	/**
	 * 获取订单翻译套餐信息
	 * @param orderId
	 * @return
	 */
	public CmcRCombo getCombo(String orderId);
	/**
	 * 添加套餐订单
	 * @param orderId
	 * @param bean
	 * @param combo
	 * @param pkg
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder addCombo(String orderId, TransComboAddBean bean, CmcCombo combo, CmcComboPackage pkg, String serviceType, String platform, String version);
	/**
	 * 处理翻译套餐订单，生成用户翻译套餐
	 * @param orderId
	 */
	public CmcRCombo dealCombo(String orderId);
}
