package com.qcmz.cmc.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcCtSubject;
import com.qcmz.cmc.entity.CmcCtTask;
import com.qcmz.framework.page.PageBean;

public interface ICrowdTaskService {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcCtTask 带cmcCtLib|activity>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取所有任务列表，带适用平台列表
	 * @return
	 */
	public List<CmcCtTask> findTask();
	/**
	 * 获取已启用任务列表
	 * @return
	 */
	public List<CmcCtTask> findOnTask();
	/**
	 * 获取父任务列表
	 * @param taskFreq
	 * @param status
	 * @return
	 */
	public List<CmcCtTask> findParentTask(Integer taskFreq, Integer status);
	/**
	 * 获取需要取消用户任务的任务
	 * @return
	 */
	public List<CmcCtTask> findTask4Cancel();
	/**
	 * 获取任务
	 * @param taskId
	 * @return
	 */
	public CmcCtTask getTask(Long taskId);
	/**
	 * 获取任务，带适用平台
	 * @param taskId
	 * @return
	 */
	public CmcCtTask getTaskJoin(Long taskId);
	/**
	 * 获取任务
	 * @param parentTaskId not null
	 * @param date 仅日期 not null
	 * @return
	 */
	public CmcCtTask getTaskByParentId(Long parentTaskId, Date date);
	/**
	 * 获取有效任务的最小排序值
	 * @return
	 */
	public Integer getMinValidSortIndex();
	/**
	 * 添加任务
	 * @param task
	 */
	public void saveTask(CmcCtTask task);
	/**
	 * 添加任务及其题库题目
	 * @param task 任务信息
	 * @param libName 题库名 not null
	 * @param subject 题目信息
	 * @param orderId 任务挂钩的订单编号
	 */
	public void saveTask(CmcCtTask task, String libName, CmcCtSubject subject, String orderId);
	/**
	 * 修改任务及其题库题目
	 * @param task
	 * @param subject
	 */
	public void updateTask(CmcCtTask task, CmcCtSubject subject);
	/**
	 * 更新任务状态
	 * @param taskId
	 * @param status
	 */
	public void updateStatus(Long taskId, Integer status);
	/**
	 * 更新任务描述
	 * @param taskId
	 * @param description
	 */
	public void updateDescription(Long taskId, String description);
	/**
	 * 更新任务报名数
	 * @param taskId
	 */
	public void updateApplyCount(Long taskId);
	/**
	 * 更新进行中任务数
	 * @param taskId
	 */
	public void updateIngCount(Long taskId);
	/**
	 * 更新任务完成数
	 * @param taskId
	 */
	public void updateFinishCount(Long taskId);
	/**
	 * 取消停止过期任务（父任务除外）
	 */
	public void cancelTask();
}
