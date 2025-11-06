package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcRTask;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderAddBean;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderDetailBean;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderListBean;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;

public interface ICrowdTaskOrderService {
	/**
	 * 分页获取众包任务订单，含订单信息
	 * @param map
	 * @param pageBean<CmcRTask>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取处理众包任务订单列表，含订单
	 * @param map
	 * @param pageBean<CmcRTask>
	 */
	public void findDeal(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取用户众包任务订单列表
	 * @param userId
	 * @param orderType
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<CrowdTaskOrderListBean> findCrowdTaskInfo(Long userId, Integer orderType, int pageSize, String lastId);
	/**
	 * 获取众包任务订单详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @exception ParamException
	 */
	public CrowdTaskOrderDetailBean getDetail(String orderId, Long userId);
	/**
	 * 获取众包任务订单信息，含任务、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId);
	/**
	 * 获取众包任务订单任务信息
	 * @param orderId
	 * @return
	 */
	public CmcRTask getCrowdTask(String orderId);
	/**
	 * 获取众包任务订单任务信息，带cmcROrder
	 * @param orderId
	 * @return
	 */
	public CmcRTask getCrowdTaskJoinOrder(String orderId);
	/**
	 * 创建众包任务订单
	 * @param orderId
	 * @param bean
	 * @param picUrl
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder addCrowdTask(String orderId, CrowdTaskOrderAddBean bean, String picUrl, String serviceType, String platform, String version);
	/**
	 * 更新任务编号
	 * @param orderId
	 * @param taskId
	 */
	public void updateTaskId(String orderId, Long taskId);
}
