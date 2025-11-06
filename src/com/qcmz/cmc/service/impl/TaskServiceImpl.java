package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ITaskDao;
import com.qcmz.cmc.entity.CmcSTask;
import com.qcmz.cmc.entity.CmcSTaskhis;
import com.qcmz.cmc.service.ITaskService;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.BeanUtil;

/**
 * 类说明：任务
 * 修改历史：
 */
@Service
public class TaskServiceImpl implements ITaskService {
	@Autowired
	private ITaskDao taskDao;
	
	/**
	 * 获取任务列表
	 * @param taskType
	 * @param limit
	 * @return
	 * 修改历史：
	 */
	public List<CmcSTask> findTask4Process(String taskType, int limit){
		return taskDao.findTask4Process(taskType, limit);
	}
	
	/**
	 * 获取任务信息
	 * @param taskId
	 * @return
	 * 修改历史：
	 */
	public CmcSTask getTask(Long taskId){
		return (CmcSTask) taskDao.load(CmcSTask.class, taskId);
	}
	
	/**
	 * 添加任务
	 * @param taskType
	 * @param identify
	 * @param content
	 */
	public void saveTask(String taskType, String identify, String content){
		CmcSTask task = new CmcSTask();
		task.setTasktype(taskType);
		task.setIdentify(identify);
		task.setContent(content);
		task.setStatus(SystemConstants.STATUS_UNDEAL);
		task.setCreatetime(new Date());
		taskDao.saveOrUpdate(task);
	}
	
	/**
	 * 保存任务处理结果
	 * @param task
	 * @param success
	 * @param operResult
	 */
	public void saveOperResult(CmcSTask task, boolean success, String operResult){
		Date now = new Date();
		if(success){
			CmcSTaskhis his = new CmcSTaskhis();
			BeanUtil.copyProperties(his, task);
			his.setOpertime(now);
			his.setOpertimes(his.getOpertimes()+1);
			his.setOperresult(operResult);
			taskDao.save(his);

			taskDao.delete(task);
		}else{
			task.setStatus(SystemConstants.STATUS_FAIL);
			task.setOpertime(now);
			task.setOpertimes(task.getOpertimes()+1);
			task.setOperresult(operResult);
			taskDao.saveOrUpdate(task);
		}
	}
	
	/**
	 * 获取异常任务数
	 * @return
	 * 修改历史：
	 */
	public Long getAbnormalCount(){
		return taskDao.getAbnormalCount();
	}
}
