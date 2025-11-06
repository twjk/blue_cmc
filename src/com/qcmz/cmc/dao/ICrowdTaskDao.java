package com.qcmz.cmc.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcCtTask;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface ICrowdTaskDao extends IBaseDAO {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcCtTask 带cmcCtLib>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取所有任务列表
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
