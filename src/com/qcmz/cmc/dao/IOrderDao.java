package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcREval;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.vo.OrderCountBean;
import com.qcmz.cmc.vo.OrderDayCountBean;
import com.qcmz.cmc.ws.provide.vo.OrderListBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IOrderDao extends IBaseDAO {
	/**
	 * 分页获取订单列表，带用户套餐
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取翻译处理订单列表，带用户套餐
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	public void findTransDeal(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取校对订单列表
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	public void findCheck(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取订单列表
	 * @param userId
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<OrderListBean> findOrderInfo(Long userId, String employeeId, Integer orderType, int pageSize, String lastId);
	/**
	 * 获取需要退款的订单编号
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public List<String> findNeedRefund();
	/**
	 * 分页获取需取消订单编号
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<String> findNeedCancel(int pageSize, String lastId);
	/**
	 * 获取待处理的订单
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @return
	 */
	public List<CmcROrder> findWaitDeal(Integer orderCat, List<Integer> orderTypes);
	/**
	 * 获取即将需要处理的预约订单，带用户套餐
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @return
	 */
	public List<CmcROrder> findSoonDeal(Integer orderCat, List<Integer> orderTypes);
	/**
	 * 获取未完成处理的订单
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @return
	 * 修改历史：
	 */
	public List<CmcROrder> findUndealt(Integer orderCat, List<Integer> orderTypes);
	/**
	 * 获取评价列表
	 * @param orderIds
	 * @return
	 */
	public List<CmcREval> findEval(List<String> orderIds);
	/**
	 * 订单日统计
	 * @param orderCat not null
	 * @param dealStatus
	 * @param start not null
	 * @param end not null
	 * @return
	 */
	public List<OrderDayCountBean> findOrderDayCount(Integer orderCat, String dealStatus, Date start, Date end);
	/**
	 * 订单统计
	 * @param start
	 * @param end
	 * @return
	 */
	public List<OrderCountBean> findOrderCount(Date start, Date end);
	/**
	 * 获取订单信息
	 * @param orderId
	 * @param userId
	 * @return
	 */
	public CmcROrder getOrder(String orderId, Long userId);
	/**
	 * 获取订单评价
	 * @param orderId
	 * @return
	 */
	public CmcREval getOrderEval(String orderId);
	/**
	 * 获取用户指定日期订单数
	 * @param userId
	 * @param date
	 * @param orderType
	 * @return
	 */
	public Long getUserDayCount(Long userId, Date date, Integer orderType);
	/**
	 * 获取用户处理（待处理、处理中、已处理）订单数
	 * @param userId not null
	 * @param orderType
	 * @param dealProgress
	 * @return
	 */
	public Long getUserProcessCount(Long userId, Integer orderType, String dealProgress);
	/**
	 * 获取处理订单数
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @param dealprogressList not null
	 * @return
	 */
	public Long getProcessCount(Integer orderCat, List<Integer> orderTypes, List<String> dealprogressList);
	/**
	 * 获取新的订单编号
	 * @return
	 * 修改历史：
	 */
	public String newOrderId();
	/**
	 * 更新状态
	 * @param orderId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(String orderId, Integer status);
	/**
	 * 更新处理状态
	 * @param orderId
	 * @param dealStatus
	 * 修改历史：
	 */
	public void updateDealStatus(String orderId, String dealStatus);
	/**
	 * 更新处理进度
	 * @param orderId
	 * @param dealProgress
	 * 修改历史：
	 */
	public void updateDealProgress(String orderId, String dealProgress);
	/**
	 * 更新地址
	 * @param orderId
	 * @param address
	 */
	public void updateAddress(String orderId, String address);
	/**
	 * 更新是否修正过
	 * @param orderId
	 * @param revise
	 */
	public void updateRevise(String orderId, Integer revise);
	/**
	 * 保存完成校对结果
	 * @param orderId
	 * @param revise
	 * @param checkStatus
	 */
	public void saveFinishCheck(String orderId, Integer revise, Integer checkStatus);
	/**
	 * 更新评价状态
	 * @param orderId
	 * @param evalStatus
	 */
	public void updateEvalStatus(String orderId, Integer evalStatus);
	/**
	 * 更新佣金结算结果
	 * @param orderId
	 * @param commissionStatus
	 * @param commissionAmount
	 * @param commissionDate
	 */
	public void updateCommission(String orderId, Integer commissionStatus, Double commissionAmount, Date commissionDate);
	/**
	 * 更新即将开始处理状态
	 * @param orderId
	 * @param soonDealStatus
	 */
	public void updateSoonDealStatus(String orderId, Integer soonDealStatus);
}
