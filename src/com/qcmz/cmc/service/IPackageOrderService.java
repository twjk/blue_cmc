package com.qcmz.cmc.service;

import java.util.Date;
import java.util.Map;

import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcRPackage;
import com.qcmz.cmc.ws.provide.vo.PackageOrderAddBean;
import com.qcmz.cmc.ws.provide.vo.PackageOrderDetailBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.umc.ws.provide.vo.ActivityBean;

public interface IPackageOrderService {
	/**
	 * 分页获取优惠套餐，含订单信息
	 * @param map
	 * @param pageBean<CmcRPackage>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取优惠套餐订单信息，含套餐、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId);
	/**
	 * 获取套餐信息
	 */
	public CmcRPackage getPackage(Long pkgId);
	/**
	 * 获取套餐信息
	 * @param orderId
	 * @return
	 */
	public CmcRPackage getPackage(String orderId);
	/**
	 * 获取套餐订单详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public PackageOrderDetailBean getPackageDetail(String orderId, Long userId);
	/**
	 * 创建优惠套餐订单
	 * @param orderId
	 * @param bean
	 * @param activity
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder addPackage(String orderId, PackageOrderAddBean bean, ActivityBean activity, String exchangeCode, String serviceType, String platform, String version);
	/**
	 * 更新套餐订单
	 * @param entity
	 */
	public void updatePackage(CmcRPackage entity);
	/**
	 * 更新兑换码
	 * @param pkgId
	 * @param exchangeCode
	 */
	public void updateExchangeCode(Long pkgId, String exchangeCode);
	/**
	 * 保存兑换结果，并将订单状态置为已完成
	 * @param orderId
	 * @param userId
	 * @param exchangeTime
	 */
	public void saveExchangeSuccess(String orderId, Long userId, Date exchangeTime);
}
