package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcCtTask;
import com.qcmz.cmc.service.ICrowdTaskService;
import com.qcmz.cmc.util.CrowdTaskUtil;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.StringUtil;

/**
 * 众包任务缓存
 */
@Component
public class CrowdTaskMap extends AbstractCacheMap {
	@Autowired
	private ICrowdTaskService crowdTaskService;
	
	/**
	 * 所有任务缓存,<taskId, CmcCtTask带platforms>
	 */
	private Map<Long, CmcCtTask> taskMap = null;
	/**
	 * 有效任务缓存，带platforms
	 */
	private List<CmcCtTask> validTask = null;
	
	@Override @PostConstruct
	protected void init() {
		Map<Long, CmcCtTask> taskMapTemp = new HashMap<Long, CmcCtTask>();
		List<CmcCtTask> validTaskTemp = new ArrayList<CmcCtTask>();
		
		Long now = System.currentTimeMillis();
		
		//任务
		List<CmcCtTask> tasks = crowdTaskService.findTask();
		for (CmcCtTask task : tasks) {
			task.setLimitUserIds(StringUtil.split2LongList(task.getLimituserid(), ","));
			
			taskMapTemp.put(task.getTaskid(), task);
			
			if(task.getStatus().equals(SystemConstants.STATUS_ON)
					&& task.getEndtime().getTime()>now
					&& DictConstant.DICT_CROWDTASK_TASKTYPE_TASK.equals(task.getTasktype())){
				validTaskTemp.add(task);
			}
		}
		
		taskMap = taskMapTemp;
		validTask = validTaskTemp;
	}

	/**
	 * 获取当前有效任务列表
	 * @return
	 */
	public List<CmcCtTask> findValidTask(){
		List<CmcCtTask> result = new ArrayList<CmcCtTask>();
		if(safeInit(validTask)){
			for (CmcCtTask task : validTask) {
				if(CrowdTaskUtil.isValid(task)){
					result.add(task);
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取任务
	 * @param taskId
	 * @return
	 */
	public CmcCtTask getTask(Long taskId){
		if(safeInit(taskMap)){
			return taskMap.get(taskId);
		}
		return null;
	}
	
	@Override
	public void update(Object obj) {
	}

	@Override
	public void delete(Object obj) {
	}

}
