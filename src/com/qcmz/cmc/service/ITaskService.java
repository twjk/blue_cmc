package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcSTask;

/**
 * 类说明：任务
 * 修改历史：
 */
public interface ITaskService {
	/**
	 * 获取任务列表
	 * @param taskType
	 * @param limit
	 * @return
	 * 修改历史：
	 */
	public List<CmcSTask> findTask4Process(String taskType, int limit);
	/**
	 * 获取任务信息
	 * @param taskId
	 * @return
	 * 修改历史：
	 */
	public CmcSTask getTask(Long taskId);
	/**
	 * 添加任务
	 * @param taskType
	 * @param identify
	 * @param content
	 */
	public void saveTask(String taskType, String identify, String content);
	/**
	 * 保存任务处理结果
	 * @param task
	 * @param success
	 * @param operResult
	 */
	public void saveOperResult(CmcSTask task, boolean success, String operResult);
	/**
	 * 获取异常任务数
	 * @return
	 * 修改历史：
	 */
	public Long getAbnormalCount();
}
