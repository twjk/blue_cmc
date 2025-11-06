package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcLtTask;
import com.qcmz.cmc.vo.LocalTaskPageSearchBean;
import com.qcmz.cmc.vo.LocalTastSearchBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobQueryBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface ILocalTaskDao extends IBaseDAO {
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcLtTask带CmcLtCompany>
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
	 * 更新状态
	 * @param taskId
	 * @param status
	 */
	public void updateStatus(Long taskId, Integer status);
}
