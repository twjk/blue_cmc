package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.entity.CmcRDub;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.ws.provide.vo.DubAddBean;
import com.qcmz.cmc.ws.provide.vo.DubOrderDetailBean;
import com.qcmz.cmc.ws.provide.vo.ProductItemBean;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;

public interface IDubOrderService {
	/**
	 * 分页获取配音订单，含订单信息、配音员
	 * @param map
	 * @param pageBean<CmcRDub>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取处理配音订单列表，含订单、配音员
	 * @param map
	 * @param pageBean
	 */
	public void findDeal(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取配音订单信息，含配音、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId);
	/**
	 * 获取配音订单配音信息
	 * @param orderId
	 * @return
	 */
	public CmcRDub getDub(String orderId);
	/**
	 * 获取配音订单详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @exception ParamException
	 */
	public DubOrderDetailBean getDetail(String orderId, Long userId);
	/**
	 * 创建配音订单
	 * @param orderId
	 * @param bean
	 * @param item
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder addDub(String orderId, DubAddBean bean, ProductItemBean item, String serviceType, String platform, String version);
}
