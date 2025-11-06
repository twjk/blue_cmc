package com.qcmz.cmc.process;

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.JobConfig;
import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.service.ICrowdTaskService;

/**
 * 众包任务计数处理 
 */
@Component
public class CrowdTaskCountProcess {
	@Autowired
	private ICrowdTaskService crowdTaskService;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 报名人数变动的任务编号队列<taskId>
	 */
	private static ConcurrentSkipListSet<Long> applyCountTasks = new ConcurrentSkipListSet<Long>();
	/**
	 * 进行中人数变动的任务编号队列<taskId>
	 */
	private static ConcurrentSkipListSet<Long> ingCountTasks = new ConcurrentSkipListSet<Long>();
	/**
	 * 完成人数变动的任务编号队列<taskId>
	 */
	private static ConcurrentSkipListSet<Long> finishCountTasks = new ConcurrentSkipListSet<Long>();
	
	/**
	 * 计数入库
	 */
	public void saveCount(){
		int applyCount = updateApplyCount();
		updateIngCount();
		int finishCount = updateFinishCount();
		
		if(applyCount>0 || finishCount>0){
			CacheSynProcess.synCacheThrd(BeanConstant.BEANID_CACHE_CROWDTASK);
		}
	}
	
	/**
	 * 标记任务报名数变动
	 * @param taskId
	 */
	public static void changeApplyCount(Long taskId){
		if(taskId==null) return;
		
		if(JobConfig.CROWDTASK_COUNTSAVE_ISWORK){
			applyCountTasks.add(taskId);
		}
	}
	
	/**
	 * 更新任务报名数
	 */
	public int updateApplyCount(){
		int count = 0;
		Iterator<Long> it = applyCountTasks.iterator();
		Long taskId = null;
		while(it.hasNext()){
			taskId = it.next();
			try {
				crowdTaskService.updateApplyCount(taskId);
				it.remove();
				count++;
			} catch (Exception e) {
				logger.error("更新任务报名数失败："+taskId, e);
			}
		}
		return count;
	}
	
	/**
	 * 标记进行中任务数变动
	 * @param taskId
	 */
	public static void changeIngCount(Long taskId){
		if(taskId==null) return;
		
		if(JobConfig.CROWDTASK_COUNTSAVE_ISWORK){
			ingCountTasks.add(taskId);
		}
	}
	
	/**
	 * 更新进行中任务数
	 */
	public int updateIngCount(){
		int count = 0;
		Iterator<Long> it = ingCountTasks.iterator();
		Long taskId = null;
		while(it.hasNext()){
			taskId = it.next();
			try {
				crowdTaskService.updateIngCount(taskId);
				it.remove();
				count++;
			} catch (Exception e) {
				logger.error("更新进行中任务数失败："+taskId, e);
			}
		}
		return count;
	}
	
	/**
	 * 标记任务完成数变动
	 * @param taskId
	 */
	public static void changeFinishCount(Long taskId){
		if(taskId==null) return;
		
		if(JobConfig.CROWDTASK_COUNTSAVE_ISWORK){
			finishCountTasks.add(taskId);
		}
	}
	
	/**
	 * 更新任务完成数
	 */
	public int updateFinishCount(){
		int count = 0;
		Iterator<Long> it = finishCountTasks.iterator();
		Long taskId = null;
		while(it.hasNext()){
			taskId = it.next();
			try {
				crowdTaskService.updateFinishCount(taskId);
				it.remove();
				count++;
			} catch (Exception e) {
				logger.error("更新任务完成数失败："+taskId, e);
			}
		}
		return count;
	}

}
