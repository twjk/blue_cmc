package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.dao.ITaskDao;
import com.qcmz.cmc.entity.CmcSTask;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.dao.impl.BaseDAO;

/**
 * 类说明：任务
 * 修改历史：
 */
@Repository
public class TaskDao extends BaseDAO implements ITaskDao {
	
	/**
	 * 获得任务
	 * @param taskType
	 * @param identify
	 * @return
	 * 修改历史：
	 */
	public CmcSTask getTask(String taskType, String identify){
		String hql = "from CmcSTask where tasktype=:tasktype and identify=:identify";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tasktype", taskType);
		params.put("identify", identify);
		return (CmcSTask) load(hql, params);
	}
	
	/**
	 * 获取待处理任务列表
	 * @param taskType
	 * @param limit
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<CmcSTask> findTask4Process(String taskType, int limit){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tasktype", taskType);
		params.put("st", SystemConstants.STATUS_SUCCESS);
		params.put("opertimes", SystemConfig.TASK_RETRY);
		
		return (List<CmcSTask>) qryListTop("from CmcSTask where tasktype=:tasktype and opertimes<:opertimes and status<>:st order by opertimes, taskid", params, limit);
	}
	
	/**
	 * 获取异常任务数
	 * @return
	 * 修改历史：
	 */
	public Long getAbnormalCount(){
		return (Long) load("select count(taskid) from CmcSTask where opertimes>=?", SystemConfig.TASK_RETRY);
	}
}
