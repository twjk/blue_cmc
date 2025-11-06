package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcRTask;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface ICrowdTaskOrderDao extends IBaseDAO {
	/**
	 * 分页获取众包任务订单，含订单
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
	 * 分页获取用户众包任务订单列表，含订单、任务信息
	 * @param userId
	 * @param orderType
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<CmcRTask> findCrowdTask(Long userId, Integer orderType, int pageSize, String lastId);
	/**
	 * 获取众包任务订单任务信息
	 * @param orderId
	 * @return
	 */
	public CmcRTask getCrowdTask(String orderId);
	/**
	 * 获取众包任务订单任务信息，带cmcCtTask
	 * @param orderId
	 * @return
	 */
	public CmcRTask getCrowdTaskJoin(String orderId);
	/**
	 * 获取众包任务订单任务信息，带cmcROrder
	 * @param orderId
	 * @return
	 */
	public CmcRTask getCrowdTaskJoinOrder(String orderId);
	/**
	 * 更新任务编号
	 * @param orderId
	 * @param taskId
	 */
	public void updateTaskId(String orderId, Long taskId);
}
