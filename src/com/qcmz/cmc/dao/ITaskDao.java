package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcSTask;
import com.qcmz.framework.dao.IBaseDAO;

/**
 * 类说明：任务
 * 修改历史：
 */
public interface ITaskDao extends IBaseDAO {
	/**
	 * 获得任务
	 * @param taskType
	 * @param identify
	 * @return
	 * 修改历史：
	 */
	public CmcSTask getTask(String taskType, String identify);
	/**
	 * 获取任务列表
	 * @param taskType
	 * @param limit
	 * @return
	 * 修改历史：
	 */
	public List<CmcSTask> findTask4Process(String taskType, int limit);
	/**
	 * 获取异常任务数
	 * @return
	 * 修改历史：
	 */
	public Long getAbnormalCount();
}
