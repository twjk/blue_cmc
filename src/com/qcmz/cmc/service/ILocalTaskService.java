package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcLtTask;
import com.qcmz.cmc.vo.LocalTaskPageSearchBean;
import com.qcmz.cmc.vo.LocalTastSearchBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobQueryBean;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;

public interface ILocalTaskService {
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcLtTask带CmcLtCompany/CmcRewardActivity>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取有效的同城任务列表
	 * @param search
	 * @return
	 */
	public List<CmcLtTask> findTask(LocalTaskPageSearchBean search);
	/**
	 * 分页获取同城任务列表
	 * @param pageSize
	 * @param lastTaskId
	 * @return
	 */
	public List<CmcLtTask> findTask(int pageSize, Long lastTaskId);
	/**
	 * 获取用户的同城任务列表
	 * @param search
	 * @return
	 */
	public List<CmcLtTask> findTask(LocalZhaopinJobQueryBean search);
	/**
	 * 获取同城任务
	 * @param taskId
	 */
	public CmcLtTask getTask(Long taskId);
	/**
	 * 获取同城任务，带图片、公司、来源、活动
	 * @param taskId
	 */
	public CmcLtTask getTaskJoin(Long taskId);
	/**
	 * 获取同城任务，带公司
	 * @param taskId
	 */
	public CmcLtTask getTaskJoinCompany(Long taskId);
	/**
	 * 获取有效的同城任务数
	 * @param search
	 * @return
	 */
	public Long getTaskCount(LocalTastSearchBean search);
	/**
	 * 查重
	 * @param taskId
	 * @param companyId
	 * @param title
	 * @return
	 */
	public boolean isRepeat(Long taskId, Long companyId, String title);
	/**
	 * 保存同城任务
	 * @param bean
	 * @exception DataException
	 */
	public void saveOrUpdate(CmcLtTask bean);
	/**
	 * 更新入库
	 * @param entity
	 */
	public void update(CmcLtTask entity);
	/**
	 * 更新状态
	 * @param taskId
	 * @param status
	 */
	public void updateStatus(Long taskId, Integer status);
	/**
	 * 删除
	 * @param id
	 */
	public void delete(Long id);
}
