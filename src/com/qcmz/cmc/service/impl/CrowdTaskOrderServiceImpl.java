package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ICrowdTaskOrderDao;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcRTask;
import com.qcmz.cmc.service.ICrowdTaskOrderService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderAddBean;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderDetailBean;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskOrderListBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class CrowdTaskOrderServiceImpl implements ICrowdTaskOrderService {
	@Autowired
	private ICrowdTaskOrderDao crowdTaskOrderDao;
	@Autowired
	private IOrderService orderService;
	
	/**
	 * 分页获取众包任务订单，含订单信息
	 * @param map
	 * @param pageBean<CmcRTask>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		crowdTaskOrderDao.queryByMapTerm(map, pageBean);
		
		List<CmcRTask> list = (List<CmcRTask>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcRTask task : list) {
			userIds.add(task.getUserid());				
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRTask task : list) {
			task.setUser(userMap.get(task.getUserid()));
		}
	}
	
	/**
	 * 分页获取处理众包任务订单列表，含订单
	 * @param map
	 * @param pageBean<CmcRTask>
	 */
	@SuppressWarnings("unchecked")
	public void findDeal(Map<String, String> map, PageBean pageBean){
		crowdTaskOrderDao.findDeal(map, pageBean);
		
		List<CmcRTask> list = (List<CmcRTask>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcRTask task : list) {
			userIds.add(task.getUserid());				
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcRTask task : list) {
			task.setUser(userMap.get(task.getUserid()));
		}
	}
	
	/**
	 * 分页获取用户众包任务订单列表
	 * @param userId
	 * @param orderType
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<CrowdTaskOrderListBean> findCrowdTaskInfo(Long userId, Integer orderType, int pageSize, String lastId){
		List<CrowdTaskOrderListBean> result = new ArrayList<CrowdTaskOrderListBean>();
		CrowdTaskOrderListBean bean = null;
		
		List<CmcRTask> list = crowdTaskOrderDao.findCrowdTask(userId, orderType, pageSize, lastId);
		for (CmcRTask task : list) {
			bean = new CrowdTaskOrderListBean();
			bean.setOrderId(task.getOrderid());
			bean.setOrderType(task.getCmcROrder().getOrdertype());
			bean.setAmount(task.getCmcROrder().getAmount());
			bean.setDealStatus(task.getCmcROrder().getDealstatus());
			bean.setCreateTime(task.getCmcROrder().getCreatetime().getTime());
			bean.setContent(task.getContent());
			bean.setPeopleNum(task.getPeoplenum());
			
			if(task.getCmcCtTask()!=null){
				bean.setTaskId(task.getTaskid());
				bean.setTitle(task.getCmcCtTask().getTitle());
				bean.setPic(task.getCmcCtTask().getPic());
				bean.setApplyNum(task.getCmcCtTask().getApplynum());
				bean.setFinishNum(task.getCmcCtTask().getFinishnum());
			}
			
			result.add(bean);
		}
		
		return result;
	}
	
	/**
	 * 获取众包任务订单详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @exception ParamException
	 */
	public CrowdTaskOrderDetailBean getDetail(String orderId, Long userId){
		//订单信息
		CrowdTaskOrderDetailBean result = new CrowdTaskOrderDetailBean(orderService.getOrderDetail(orderId, userId));
		
		//任务信息
		CmcRTask cmcRTask = crowdTaskOrderDao.getCrowdTaskJoin(orderId);
		result.setContent(cmcRTask.getContent());
		result.setUrl(cmcRTask.getUrl());
		result.setPrice(cmcRTask.getPrice());
		result.setPeopleNum(cmcRTask.getPeoplenum());
		result.setDays(cmcRTask.getDays());
		
		if(cmcRTask.getCmcCtTask()!=null){
			result.setTaskId(cmcRTask.getCmcCtTask().getTaskid());
			result.setTitle(cmcRTask.getCmcCtTask().getTitle());
			result.setApplyNum(cmcRTask.getCmcCtTask().getApplynum());
			result.setFinishNum(cmcRTask.getCmcCtTask().getFinishnum());
		}
		
		return result;
	}
	
	/**
	 * 获取众包任务订单信息，含订单任务CmcRTask、任务CmcCtTask、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId){
		CmcROrder order = orderService.getOrderJoin(orderId);
		order.setCmcRTask(crowdTaskOrderDao.getCrowdTaskJoin(orderId));
		order.setUser(UserMap.getUser(order.getUserid()));
		return order;
	} 
	
	/**
	 * 获取众包任务订单任务信息
	 * @param orderId
	 * @return
	 */
	public CmcRTask getCrowdTask(String orderId){
		return crowdTaskOrderDao.getCrowdTask(orderId);
	}
	
	/**
	 * 获取众包任务订单任务信息，带cmcROrder
	 * @param orderId
	 * @return
	 */
	public CmcRTask getCrowdTaskJoinOrder(String orderId){
		return crowdTaskOrderDao.getCrowdTaskJoinOrder(orderId);
	}
	
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
	public CmcROrder addCrowdTask(String orderId, CrowdTaskOrderAddBean bean, String picUrl, String serviceType, String platform, String version){
		//组装数据
		CmcROrder order = new CmcROrder();
		order.setOrderid(orderId);
		order.setUserid(bean.getUid());
		order.setOrdercat(DictConstant.DICT_ORDERCAT_CROWDTASK);
		order.setOrdertype(bean.getOrderType());
		order.setAmount(bean.getAmount());
		order.setCouponamount(0.0);
		order.setWalletamount(0.0);
		order.setPayableamount(bean.getAmount());
		order.setMobile(bean.getMobile());
		order.setCreatetime(new Date());
		order.setServicetype(serviceType);
		order.setPlatform(platform);
		order.setVersion(version);
		order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY);
		order.setStatus(SystemConstants.STATUS_ON);

		CmcRItem ritem = new CmcRItem();
		ritem.setOrderid(orderId);
		ritem.setItemid(0L);
		ritem.setItemname(DictMap.getOrderTypeMean(order.getOrdertype()));
		ritem.setOriprice(0.0);
		ritem.setPrice(0.0);
		
		CmcRTask cmcRTask = new CmcRTask();
		cmcRTask.setOrderid(orderId);
		cmcRTask.setUserid(bean.getUid());
		cmcRTask.setContent(bean.getContent());
		cmcRTask.setUrl(bean.getUrl());
		cmcRTask.setPicurl(picUrl);
		cmcRTask.setPrice(bean.getPrice());
		cmcRTask.setPeoplenum(bean.getPeopleNum());
		cmcRTask.setDays(bean.getDays());
		
		//信息入库
		orderService.saveOrder(order, ritem);
		crowdTaskOrderDao.save(cmcRTask);
		
		return order;
	}
	
	/**
	 * 更新任务编号
	 * @param orderId
	 * @param taskId
	 */
	public void updateTaskId(String orderId, Long taskId){
		crowdTaskOrderDao.updateTaskId(orderId, taskId);
	}
}
