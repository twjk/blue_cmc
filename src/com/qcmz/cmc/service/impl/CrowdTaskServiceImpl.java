package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.RewardActivityMap;
import com.qcmz.cmc.dao.ICrowdTaskDao;
import com.qcmz.cmc.entity.CmcCtLib;
import com.qcmz.cmc.entity.CmcCtPlatform;
import com.qcmz.cmc.entity.CmcCtSubject;
import com.qcmz.cmc.entity.CmcCtTask;
import com.qcmz.cmc.service.ICrowdTaskLibService;
import com.qcmz.cmc.service.ICrowdTaskOrderService;
import com.qcmz.cmc.service.ICrowdTaskPlatformService;
import com.qcmz.cmc.service.ICrowdTaskService;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;

@Service
public class CrowdTaskServiceImpl implements ICrowdTaskService {
	@Autowired
	private ICrowdTaskDao crowdTaskDao;
	@Autowired
	private ICrowdTaskLibService crowdTaskLibService;
	@Autowired
	private ICrowdTaskOrderService crowdTaskOrderService;
	@Autowired
	private ICrowdTaskPlatformService crowdTaskPlatformService;
	@Autowired
	private RewardActivityMap rewardActivityMap;
	
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcCtTask 带cmcCtLib|activity>
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		crowdTaskDao.queryByMapTerm(map, pageBean);
		List<CmcCtTask> list = (List<CmcCtTask>) pageBean.getResultList();
		for (CmcCtTask entity : list) {
			if(entity.getActid()!=null){
				entity.setActivity(rewardActivityMap.getActivity(entity.getActid()));
			}
		}
	}
	
	/**
	 * 获取所有任务列表，带适用平台列表
	 * @return
	 */
	public List<CmcCtTask> findTask(){
		//任务
		List<CmcCtTask> result = crowdTaskDao.findTask();
		if(result.isEmpty()) return result;
			
		//适用平台
		List<CmcCtPlatform> platforms = crowdTaskPlatformService.findPlatform();
		if(platforms.isEmpty()) return result;
			
		Map<Long, CmcCtTask> map = new HashMap<Long, CmcCtTask>();
		for (CmcCtTask task : result) {
			map.put(task.getTaskid(), task);
		}		
		for (CmcCtPlatform platform : platforms) {
			map.get(platform.getTaskid()).getPlatforms().add(platform);
		}
		
		return result;
	}
	
	/**
	 * 获取已启用任务列表
	 * @return
	 */
	public List<CmcCtTask> findOnTask(){
		return crowdTaskDao.findOnTask();
	}
	
	/**
	 * 获取父任务列表
	 * @param taskFreq
	 * @param status
	 * @return
	 */
	public List<CmcCtTask> findParentTask(Integer taskFreq, Integer status){
		return crowdTaskDao.findParentTask(taskFreq, status);
	}
	
	/**
	 * 获取需要取消用户任务的任务
	 * @return
	 */
	public List<CmcCtTask> findTask4Cancel(){
		return crowdTaskDao.findTask4Cancel();
	}
	
	/**
	 * 获取任务
	 * @param taskId
	 * @return
	 */
	public CmcCtTask getTask(Long taskId){
		return (CmcCtTask) crowdTaskDao.load(CmcCtTask.class, taskId);
	}
	
	/**
	 * 获取任务，带适用平台
	 * @param taskId
	 * @return
	 */
	public CmcCtTask getTaskJoin(Long taskId){
		CmcCtTask task = (CmcCtTask) crowdTaskDao.load(CmcCtTask.class, taskId);
		if(task!=null){
			List<CmcCtPlatform> platforms = crowdTaskPlatformService.findPlatform(taskId);
			if(platforms!=null && !platforms.isEmpty()){
				task.setPlatforms(platforms);
			}
		}
		return task;
	}
	
	/**
	 * 获取任务
	 * @param parentTaskId not null
	 * @param date 仅日期 not null
	 * @return
	 */
	public CmcCtTask getTaskByParentId(Long parentTaskId, Date date){
		return crowdTaskDao.getTaskByParentId(parentTaskId, date);
	}
	
	/**
	 * 获取有效任务的最小排序值
	 * @return
	 */
	public Integer getMinValidSortIndex(){
		return crowdTaskDao.getMinValidSortIndex();
	}
	
	/**
	 * 添加任务
	 * @param task
	 */
	public void saveTask(CmcCtTask task){
		crowdTaskDao.save(task);
		crowdTaskPlatformService.saveAll(task.getTaskid(), task.getPlatforms());
	}
	
	/**
	 * 添加任务及其题库题目
	 * @param task 任务信息
	 * @param libName 题库名 not null
	 * @param subject 题目信息
	 * @param orderId 任务挂钩的订单编号
	 */
	public void saveTask(CmcCtTask task, String libName, CmcCtSubject subject, String orderId){
		//创建题库
		CmcCtLib lib = crowdTaskLibService.saveLib(libName);
		
		//保存题目
		subject.setLibid(lib.getLibid());
		crowdTaskLibService.saveSubject(subject);
		
		//创建任务
		task.setLibid(lib.getLibid());
		saveTask(task);
		
		//订单挂钩任务
		if(StringUtil.notBlankAndNull(orderId)){
			crowdTaskOrderService.updateTaskId(orderId, task.getTaskid());
		}
	}
	
	/**
	 * 修改任务及其题库题目
	 * @param task
	 * @param subject
	 */
	public void updateTask(CmcCtTask task, CmcCtSubject subject){
		if(subject!=null){
			crowdTaskLibService.updateSubject(subject);
		}
		
		crowdTaskPlatformService.updateAll(task.getTaskid(), task.getPlatforms());
		
		crowdTaskDao.update(task);
	}
	
	/**
	 * 更新任务状态
	 * @param taskId
	 * @param status
	 */
	public void updateStatus(Long taskId, Integer status){
		crowdTaskDao.updateStatus(taskId, status);
	}
	
	/**
	 * 更新任务描述
	 * @param taskId
	 * @param description
	 */
	public void updateDescription(Long taskId, String description){
		crowdTaskDao.updateDescription(taskId, description);
	}
	
	/**
	 * 更新任务报名数
	 * @param taskId
	 */
	public void updateApplyCount(Long taskId){
		crowdTaskDao.updateApplyCount(taskId);
	}
	
	/**
	 * 更新进行中任务数
	 * @param taskId
	 */
	public void updateIngCount(Long taskId){
		crowdTaskDao.updateIngCount(taskId);
	}
	
	/**
	 * 更新任务完成数
	 * @param taskId
	 */
	public void updateFinishCount(Long taskId){
		crowdTaskDao.updateFinishCount(taskId);
	}
	
	/**
	 * 取消停止过期任务（父任务除外）
	 */
	public void cancelTask(){
		crowdTaskDao.cancelTask();
	}
}
