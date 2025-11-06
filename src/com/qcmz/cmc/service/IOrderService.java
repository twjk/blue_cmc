package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcREval;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcRLog;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcRTrade;
import com.qcmz.cmc.ws.provide.vo.OrderDealDetailBean;
import com.qcmz.cmc.ws.provide.vo.OrderDetailBean;
import com.qcmz.cmc.ws.provide.vo.OrderEvalAddBean;
import com.qcmz.cmc.ws.provide.vo.OrderEvalDetailBean;
import com.qcmz.cmc.ws.provide.vo.OrderListBean;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

public interface IOrderService {
	/**
	 * 分页获取订单列表
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return 
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取订单列表
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	public void findOrder(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取翻译处理订单列表，含图片/文本、评价、用户信息、用户套餐
	 * @param map
	 * @param pageBean<CmcROrder>
	 * @return
	 */
	public void findTransDeal(Map<String, String> map, PageBean pageBean);
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
	 * 获取待处理的订单
	 * @param orderCat not null
	 * @param orderType not null
	 * @return
	 */
	public List<CmcROrder> findWaitDeal(Integer orderCat, Integer orderType);
	/**
	 * 获取即将需要处理的预约订单，带用户套餐
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @return
	 */
	public List<CmcROrder> findSoonDeal(Integer orderCat, List<Integer> orderTypes);
	/**
	 * 获取即将需要处理的预约订单，带用户套餐
	 * @param orderCat not null
	 * @param orderType not null
	 * @return
	 */
	public List<CmcROrder> findSoonDeal(Integer orderCat, Integer orderType);
	/**
	 * 获取未完成处理的订单
	 * @param orderCat not null
	 * @param orderTypes not null
	 * @return
	 * 修改历史：
	 */
	public List<CmcROrder> findUndealt(Integer orderCat, List<Integer> orderTypes);
	/**
	 * 获取未完成处理的订单
	 * @param orderCat not null
	 * @param orderType not null
	 * @return
	 * 修改历史：
	 */
	public List<CmcROrder> findUndealt(Integer orderCat, Integer orderType);
	/**
	 * 获取商品列表
	 * @param orderId
	 * @return
	 */
	public List<CmcRItem> findItem(String orderId);
	/**
	 * 获取评价列表
	 * @param orderIds
	 * @return
	 */
	public List<CmcREval> findEval(List<String> orderIds);
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
	 * 获取订单详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @exception ParamException
	 */
	public OrderDetailBean getOrderDetail(String orderId, Long userId);
	/**
	 * 获取处理 订单详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @exception ParamException
	 */
	public OrderDealDetailBean getOrderDetail4Deal(String orderId);
	/**
	 * 获取订单信息
	 * @param orderId
	 * @return
	 */
	public CmcROrder getOrder(String orderId);
	/**
	 * 获取订单信息，带日志、留言
	 * @param orderId
	 * @return
	 */
	public CmcROrder getOrderJoin(String orderId);
	/**
	 * 获取用户订单信息
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
	 * 获取订单评价信息
	 * @param orderId
	 * @return
	 * @exception ParamException
	 */
	public OrderEvalDetailBean getOrderEvalInfo(String orderId);
	/**
	 * 获取用户指定日期订单数
	 * @param userId
	 * @param date
	 * @param orderType
	 * @return
	 */
	public Long getUserDayCount(Long userId, Date date, Integer orderType);
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
	 * 保存订单
	 * @param order
	 * @param ritem
	 */
	public void saveOrder(CmcROrder order, CmcRItem ritem);
	/**
	 * 修改预约时间
	 * @param order
	 * @param appointTime
	 * @param oper
	 */
	public void updateAppointTime(CmcROrder order, Date appointTime, Operator oper);
	/**
	 * 操作员接单
	 * 不校验订单是否存在以及能否接单，在外部处理
	 * @param order
	 * @param oper
	 * @return
	 */
	public void acceptOrder(CmcROrder order, Operator oper);
	/**
	 * 交单
	 * @param orderId
	 * @param from
	 * @param to
	 * @param oper
	 */
	public void handoverOrder(String orderId, String from, String to, Operator oper);
	/**
	 * 操作员捡单
	 * @param orderId
	 * @param oper
	 * @return
	 * @exception DataException
	 */
	public CmcROrder pickOrder(String orderId, Operator oper);
	/**
	 * 操作员拒单
	 * @param orderId
	 * @param reason
	 * @param oper
	 * @return
	 * @exception DataException
	 */
	public CmcROrder rejectOrder(String orderId, String reason, Operator oper);
	/**
	 * 退回钱包抵扣金额
	 * @param orderId
	 * @return
	 */
	public boolean refundWallet(String orderId);
	/**
	 * 取消订单并退钱包抵扣款
	 * @param orderId
	 * @param reason
	 * @param oper
	 * @return
	 * @exception DataException
	 */
	public CmcROrder cancelOrder(String orderId, Long userId, String reason, Operator opertor);
	/**
	 * 删除订单
	 * 有交易记录的订单不能删除
	 * @param orderId
	 * @param oper
	 * 修改历史：
	 */
	public void deleteOrder(String orderId, Operator oper);
	/**
	 * 隐藏订单
	 * @param orderId
	 * 修改历史：
	 */
	public void hideOrder(String orderId);
	/**
	 * 更新处理进度
	 * @param orderId
	 * @param dealProgress
	 * 修改历史：
	 */
	public void updateDealProgress(String orderId, String dealProgress);
	/**
	 * 保存交易结果
	 * @param trade
	 * @param order
	 * @param log
	 */
	public void saveTradeReslt(CmcRTrade trade, CmcROrder order, CmcRLog log);
	/**
	 * 保存交易信息
	 * @param trade
	 */
	public void saveTrade(CmcRTrade trade);
	/**
	 * 保存订单处理结果
	 * @param orderId
	 * @param needTime 预计处理时长
	 * @param from 源语言
	 * @param to 目标语言
	 * @param operator 操作员帐户
	 * @param operatorName 操作员姓名
	 */
	public void saveOrderDeal(String orderId, Long needTime, String from, String to, String operator, String operatorName);
	/**
	 * 保存订单完成信息（翻译订单之图片翻译、文本翻译）
	 * 状态变更、翻译语言变更、日志
	 * @param orderId
	 * @param from 源语言
	 * @param to 目标语言
	 * @param operator 操作员帐户
	 * @param operatorName 操作员姓名
	 */
	public void saveOrderFinish(String orderId, String from, String to, String operator, String operatorName);
	/**
	 * 保存订单完成信息（订阅订单、套餐订单）
	 * 状态变更、订单商品变更、日志
	 * @param orderId
	 * @param items
	 * @param operator
	 * @param operatorName
	 */
	public void saveOrderFinish(String orderId, List<CmcRItem> items, String operator, String operatorName);
	/**
	 * 保存订单完成信息
	 * 状态变更、日志
	 * @param orderId
	 * @param operator
	 * @param operatorName
	 */
	public void saveOrderFinish(String orderId, String operator, String operatorName);
	/**
	 * 更新地址
	 * @param orderId
	 * @param address
	 */
	public void updateAddress(String orderId, String address);
	/**
	 * 评价订单
	 * @param entity
	 * @exception ParamException DataException
	 */
	public void evalOrder(OrderEvalAddBean bean);
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
