package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.CrowdTaskLibMap;
import com.qcmz.cmc.cache.CrowdTaskMap;
import com.qcmz.cmc.cache.CrowdTaskSubjectCatMap;
import com.qcmz.cmc.cache.RewardActivityMap;
import com.qcmz.cmc.comparator.CrowdTaskCompletionComparator;
import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcCtPlatform;
import com.qcmz.cmc.entity.CmcCtSubject;
import com.qcmz.cmc.entity.CmcCtSubjectcat;
import com.qcmz.cmc.entity.CmcCtTask;
import com.qcmz.cmc.entity.CmcCtUseranswer;
import com.qcmz.cmc.entity.CmcCtUsertask;
import com.qcmz.cmc.entity.CmcRTask;
import com.qcmz.cmc.service.ICrowdTaskLibService;
import com.qcmz.cmc.service.ICrowdTaskOrderService;
import com.qcmz.cmc.service.ICrowdTaskPlatformService;
import com.qcmz.cmc.service.ICrowdTaskService;
import com.qcmz.cmc.service.IUserCrowdTaskService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.util.CrowdTaskUtil;
import com.qcmz.cmc.vo.CrowdTaskAddBean;
import com.qcmz.cmc.vo.CrowdTaskEditBean;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskCompletionListBean;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskCompletionUserAnswerBean;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskCompletionUserSubjectBean;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskDetailBean;
import com.qcmz.cmc.ws.provide.vo.CrowdTaskListBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.util.BeanUtil;
import com.qcmz.framework.util.CollectionUtil;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.vo.UserBean;
import com.qcmz.umc.ws.provide.locator.UserMap;

/**
 * 众包任务处理
 */
@Component
public class CrowdTaskProcess {
	@Autowired
	private IUserCrowdTaskService userCrowdTaskService;
	@Autowired
	private ICrowdTaskService crowdTaskService;
	@Autowired
	private ICrowdTaskLibService crowdTaskLibService;
	@Autowired
	private ICrowdTaskPlatformService crowdTaskPlatformService;
	@Autowired
	private ICrowdTaskOrderService crowdTaskOrderService;
	
	@Autowired
	private CrowdTaskMap crowdTaskMap;
	@Autowired
	private CrowdTaskLibMap crowdTaskLibMap;
	@Autowired
	private CrowdTaskSubjectCatMap crowdTaskSubjectCatMap;
	@Autowired
	private RewardActivityMap rewardActivityMap;
	
	/**
	 * 获取有效任务
	 * @param userId
	 * @param platform
	 * @param version
	 * @return
	 */
	public List<CrowdTaskListBean> findTask(Long userId, String serviceType, String platform, String version){
		List<CrowdTaskListBean> result = new ArrayList<CrowdTaskListBean>();
		Map<Long, CrowdTaskListBean> map = new HashMap<Long, CrowdTaskListBean>();
		CrowdTaskListBean bean = null;
		
		//有效任务列表
		List<CmcCtTask> taskList = crowdTaskMap.findValidTask();
		for (CmcCtTask task : taskList) {
			//限定用户
			if(!task.getLimitUserIds().isEmpty()){
				if(userId==null || !task.getLimitUserIds().contains(userId)) continue;
			}
			
			//适用平台
			if(!task.getPlatforms().isEmpty() || DictConstants.DICT_SERVICETYPE_QADD.equals(serviceType)){
				boolean match = false;
				for (CmcCtPlatform pf : task.getPlatforms()) {
					if((StringUtil.isBlankOrNull(pf.getServicetype()) || pf.getServicetype().equals(serviceType))
							&& (StringUtil.isBlankOrNull(pf.getPlatform()) || pf.getPlatform().equals(platform))
							&& (StringUtil.isBlankOrNull(pf.getMinversion()) || StringUtil.afterOrEqualVersion(version, pf.getMinversion()))
							&& (StringUtil.isBlankOrNull(pf.getMaxversion()) || StringUtil.beforeOrEqualVersion(version, pf.getMaxversion()))){
						match = true;
						break;
					}
				}
				if(!match) continue;
			}
			
			bean = new CrowdTaskListBean();
			bean.setTaskId(task.getTaskid());
			bean.setTitle(task.getTitle());
			bean.setPic(task.getPic());
			bean.setTaskReward(task.getTaskreward());
			bean.setTaskRewardSettle(task.getTaskrewardsettle());
			bean.setRewardType(task.getRewardtype());
			bean.setUnitName(task.getUnitname());
			bean.setSubjectNum(task.getSubjectnum());
			bean.setSubjectAssign(task.getSubjectassign());
			bean.setStartTime(task.getStarttime().getTime());
			bean.setEndTime(task.getEndtime().getTime());
			bean.setMaxNum(task.getMaxnum());
			bean.setApplyNum(task.getApplynum());
			bean.setFinishNum(task.getFinishnum());
			result.add(bean);
			
			if(userId!=null){
				map.put(bean.getTaskId(), bean);
			}
		}
		
		//用户任务信息
		if(userId!=null && !map.isEmpty()){
			List<CmcCtUsertask> userTasks = userCrowdTaskService.findUserTask(userId, map.keySet());
			for (CmcCtUsertask ut : userTasks) {
				bean = map.get(ut.getTaskid()); 
				bean.setUtId(ut.getUtid());
				bean.setUtStatus(ut.getStatus());
				bean.setTaskReward(ut.getTaskreward());
				bean.setSubjectNum(ut.getSubjectnum());
			}
		}
		
		return result;
	}
	
	/**
	 * 获取任务详情
	 * @param taskId
	 * @return
	 */
	public CrowdTaskDetailBean getTask(Long taskId, Long userId){
		CmcCtTask task = crowdTaskMap.getTask(taskId);
		
		//任务基本信息
		CrowdTaskDetailBean bean = new CrowdTaskDetailBean();
		bean.setTaskId(task.getTaskid());
		bean.setTitle(task.getTitle());
		bean.setPic2(task.getPic2());
		bean.setTaskReward(task.getTaskreward());
		bean.setTaskRewardSettle(task.getTaskrewardsettle());
		bean.setRewardType(task.getRewardtype());
		bean.setUnitName(task.getUnitname());
		bean.setSubjectNum(task.getSubjectnum());
		bean.setSubjectAssign(task.getSubjectassign());
		bean.setStartTime(task.getStarttime().getTime());
		bean.setEndTime(task.getEndtime().getTime());
		bean.setMaxNum(task.getMaxnum());
		bean.setApplyNum(task.getApplynum());
		bean.setFinishNum(task.getFinishnum());
		bean.setDescription(task.getDescription());
		bean.setAutoVerify(SystemConstants.STATUS_ON.equals(task.getAutoverify()));
		bean.setAutoQc(SystemConstants.STATUS_ON.equals(task.getAutoqc()));
		
		if(userId!=null){
			CmcCtUsertask utask = userCrowdTaskService.getUserTask(userId, taskId);
			if(utask!=null){
				bean.setUtId(utask.getUtid());
				bean.setUtStatus(utask.getStatus());
			}
		}
		
		//前置任务题目列表
		if(task.getPretaskid()!=null){
			//从题库中获取题目
			CmcCtTask preTask = crowdTaskMap.getTask(task.getPretaskid());
			List<CmcCtSubject> preSubjects = new ArrayList<CmcCtSubject>();
			if(preTask.getSubjectnum()==1){
				preSubjects.add(crowdTaskLibMap.getSubjectFromLib(preTask.getLibid()));
			}else if(preTask.getSubjectnum()>1){
				preSubjects.addAll(crowdTaskLibMap.findSubjectFromLib(preTask.getLibid(), preTask.getSubjectnum()));
			}
			
			bean.setPreSubjects(BeanConvertUtil.toCrowdSubjectBean(preSubjects, task));
		}
		
		//已完成用户列表
		List<Long> userIds = userCrowdTaskService.findFinishUserId(taskId, 10);
		bean.setFinishUsers(UserMap.findUserBean(userIds));
		
		bean.setMaxInviteRewardAmount(rewardActivityMap.getMaxRewardAmount(task.getActid()));
		
		return bean;
	}
	
	
	/**
	 * 获取任务完成列表
	 * @param taskId
	 * @return
	 */
	public List<CrowdTaskCompletionListBean> findCompletion(Long taskId){
		List<CrowdTaskCompletionListBean> result = new ArrayList<CrowdTaskCompletionListBean>();
		
		List<CmcCtUseranswer> answerList = userCrowdTaskService.findUserAnswerJoin(taskId, DictConstant.DICT_USERCROWDTASK_STATUS_FINISH);
		if(answerList.isEmpty()) return result;
			
		CrowdTaskCompletionUserSubjectBean userSubjectBean = null;
		CrowdTaskCompletionUserAnswerBean userAnswerBean = null;
		CrowdTaskCompletionListBean bean = null;
		UserBean userBean = null;
		Map<String, CrowdTaskCompletionListBean> map = new HashMap<String, CrowdTaskCompletionListBean>();
		Map<Long, UserBean> userMap = new HashMap<Long, UserBean>();
		String utId = null;
		Long subjectId = null;
		Long userId = null;
		for (CmcCtUseranswer userAnswer : answerList) {
			if(StringUtil.isBlankOrNull(userAnswer.getPic())) continue;
			
			utId = userAnswer.getUtid();
			subjectId = userAnswer.getSubjectid();
			userId = userAnswer.getCmcCtUsertask().getUserid();
			
			//用户众包任务信息
			bean = map.get(utId);
			if(bean==null){
				userBean = userMap.get(userId);
				if(userBean==null){
					userBean = new UserBean(userId);
					userMap.put(userId, userBean);
				}
				
				bean = new CrowdTaskCompletionListBean();
				
				bean.setUser(userBean);
				bean.setFinishTime(userAnswer.getCmcCtUsertask().getFinishtime().getTime());
				map.put(utId, bean);
				result.add(bean);
			}
			
			//用户题目
			userSubjectBean = null;
			for (CrowdTaskCompletionUserSubjectBean userSubjectBeanTemp : bean.getSubjects()) {
				if(userSubjectBeanTemp.getSubjectId().equals(subjectId)){
					userSubjectBean = userSubjectBeanTemp;
				}
			}
			if(userSubjectBean==null){
				userSubjectBean = new CrowdTaskCompletionUserSubjectBean();
				userSubjectBean.setSubjectId(subjectId);
				bean.getSubjects().add(userSubjectBean);
			}
			
			//用户答案
			userAnswerBean = new CrowdTaskCompletionUserAnswerBean();
			userAnswerBean.setPic(userAnswer.getPic());
			userSubjectBean.getAnswers().add(userAnswerBean);
		}
		
		//用户信息
		List<Long> userIds = new ArrayList<Long>();
		userIds.addAll(userMap.keySet());
		List<UserBean> userBeanList = UserMap.findUserBean(userIds);
		for (UserBean ub : userBeanList) {
			userBean = userMap.get(ub.getUid());
			userBean.setNick(ub.getNick());
			userBean.setIconUrl(ub.getIconUrl());
		}
		
		//排序
		Collections.sort(result, new CrowdTaskCompletionComparator());
		
		return result;
	}
	
	/**
	 * 从缓存获取并校验任务
	 * 1.任务是否存在
	 * 2.任务是否有效
	 * @return
	 * @exception DataException
	 */
	public CmcCtTask getAndCheckTask(Long taskId){
		//校验任务
		CmcCtTask task = crowdTaskMap.getTask(taskId);
		if(task==null){
			throw new DataException("任务不存在");
		}
		if(!CrowdTaskUtil.isValid(task)){
			throw new DataException("任务已过期");
		}
		return task;
	}
	
	/**
	 * 获取编辑用的任务信息
	 * @param taskId
	 * @return
	 */
	public CrowdTaskEditBean getTask4Edit(Long taskId){
		CrowdTaskEditBean result = new CrowdTaskEditBean();
		
		//任务
		CmcCtTask task = crowdTaskService.getTaskJoin(taskId);
		result.setTaskId(task.getTaskid());
		result.setTaskTitle(task.getTitle());
		result.setPeopleNum(task.getMaxnum());
		result.setApplyNum(task.getApplynum());
		result.setGroupCode(task.getGroupcode());
		result.setStartTime(task.getStarttime());
		result.setEndTime(task.getEndtime());
		result.setLimitUserId(task.getLimituserid());
		result.setDescription(task.getDescription());
		result.setSortIndex(task.getSortindex());
		result.setOrderId(task.getOrderid());
		
		//活动
		if(task.getActid()!=null){
			result.setActId(task.getActid());
			result.setActivity(rewardActivityMap.getActivity(task.getActid()));
		}
		
		//平台
		if(!task.getPlatforms().isEmpty()){
			Set<String> platforms = new HashSet<String>();
			for (CmcCtPlatform pf : task.getPlatforms()) {
				platforms.add(pf.getServicetype());
			}
			result.setLimitServiceTypeStr(CollectionUtil.toString(platforms, ";"));
		}
		
		
		//题目（题库题目数为1时可以修改题目）
		List<CmcCtSubject> subjects = crowdTaskLibService.findSubject(task.getLibid(), false, 2);
		if(subjects.size()==1){
			CmcCtSubject subject = subjects.get(0);
			result.setSubjectId(subject.getSubjectid());
			result.setSubjectReward(subject.getSubjectreward());
			result.setSubjectContent(subject.getContent());
			result.setSubjectUrl(subject.getVideo());
			result.setSubjectSource(subject.getVideosource());
			result.setSubjectPicUrl(subject.getPic());
		}
		
		return result;
	}
	
	/**
	 * 自动创建任务
	 * 1.添加第二天的天天领任务
	 */
	public void autoCreate(){
		List<CmcCtTask> parentTasks = crowdTaskService.findParentTask(DictConstant.DICT_CROWDTASK_TASKFREQ_DAILY, SystemConstants.STATUS_ON);
		if(parentTasks.isEmpty()) return;
		
		Date now = new Date();
		Date tomorrow = DateUtil.truncDate(DateUtil.addDay(now, 1));
		String tomorrowStr= DateUtil.formatDate(tomorrow);
		String tomorrowShort = DateUtil.format(tomorrow,"MMdd");
		List<CmcCtPlatform> platforms = null;
		CmcCtPlatform platform = null;
		CmcCtTask task = null;
		String startTime, endTime;
		for (CmcCtTask parentTask : parentTasks) {
			task = crowdTaskService.getTaskByParentId(parentTask.getTaskid(), tomorrow);
			if(task==null){
				CmcCtTask entity = (CmcCtTask) BeanUtil.cloneBean(parentTask);
				entity.setTaskid(null);
				entity.setParenttaskid(parentTask.getTaskid());
				if(StringUtil.notBlankAndNull(parentTask.getTitlebase())){
					entity.setTitle(new StringBuilder(parentTask.getTitlebase()).append("-").append(tomorrowShort).toString());
				}
				entity.setCreatetime(now);
				entity.setApplynum(0);
				entity.setIngnum(0);
				entity.setFinishnum(0);

				startTime = new StringBuilder(tomorrowStr).append(" ").append(DateUtil.formatTime(parentTask.getStarttime())).toString();
				endTime = new StringBuilder(tomorrowStr).append(" ").append(DateUtil.formatTime(parentTask.getEndtime())).toString();
				entity.setStarttime(DateUtil.parseDateTime(startTime));
				entity.setEndtime(DateUtil.parseDateTime(endTime));
				
				//适用平台
				platforms = crowdTaskPlatformService.findPlatform(parentTask.getTaskid());
				for (CmcCtPlatform pf : platforms) {
					platform = (CmcCtPlatform) BeanUtil.cloneBean(pf);
					platform.setId(null);
					platform.setTaskid(null);					
					entity.getPlatforms().add(platform);
				}
				
				crowdTaskService.saveTask(entity);
			}
		}
		
		//更新任务缓存
		CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_CROWDTASK);
	}
	
	/**
	 * 创建任务
	 * @param bean 
	 * @exception DataException
	 */
	public void createTask(CrowdTaskAddBean bean){
		//订单校验
		CmcRTask orderTask = null;
		if(StringUtil.notBlankAndNull(bean.getOrderId())){
			orderTask = crowdTaskOrderService.getCrowdTaskJoinOrder(bean.getOrderId());
			if(orderTask==null){
				throw new DataException("订单不存在");
			}
			if(orderTask.getTaskid()!=null){
				throw new DataException("订单已经转任务，请不要重复操作");
			}
			if(!DictConstant.DICT_ORDER_DEALSTATUS_DEALING.equals(orderTask.getCmcROrder().getDealstatus())){
				throw new DataException("订单不能转任务，请核查订单状态");
			}
		}
		
		//封装题库名称
		String libName = bean.getLibName();
		if(StringUtil.isBlankOrNull(libName)){
			libName = bean.getTaskTitle();
			if(StringUtil.notBlankAndNull(bean.getOrderId())){
				libName = new StringBuilder(libName).append("-").append(bean.getOrderId()).toString();
			}
		}
		
		//封装题目
		CmcCtSubject subject = new CmcCtSubject();
		subject.setSubjectcat(bean.getSubjectCat());
		subject.setSubjecttype(DictConstant.DICT_CROWDTASK_SUBJECTTYPE_OTHER);
		subject.setPassscore(0);
		subject.setSubjectreward(bean.getSubjectReward());
		subject.setContenttype(DictConstant.DICT_CROWDTASK_CONTENTTYPE_TXT);
		subject.setContent(bean.getSubjectContent());
		subject.setRecordtimes(0);
		subject.setVideo(bean.getSubjectUrl());
		subject.setVideosource(bean.getSubjectSource());
		subject.setPic(bean.getSubjectPicUrl());
		subject.setStatus(SystemConstants.STATUS_ON);
		
		//封装任务
		Date now = new Date();
		CmcCtTask task = new CmcCtTask();
		if(StringUtil.notBlankAndNull(bean.getOrderId())){
			task.setTasksource(DictConstant.DICT_CROWDTASK_TASKSOURCE_ORDER);
		}else{
			task.setTasksource(DictConstant.DICT_CROWDTASK_TASKSOURCE_ENT);
		}
		task.setTasktype(DictConstant.DICT_CROWDTASK_TASKTYPE_TASK);
		task.setTaskfreq(DictConstant.DICT_CROWDTASK_TASKFREQ_TIMES);
		task.setParenttaskid(0L);
		task.setGroupcode(bean.getGroupCode());
		task.setTitle(bean.getTaskTitle());
		task.setStarttime(bean.getStartTime());
		task.setEndtime(bean.getEndTime());
		if(DictConstant.DICT_CROWDTASK_TASKSOURCE_ORDER.equals(task.getTasksource())){
			task.setAutocancel(0);
		}else{
			task.setAutocancel(1);
		}
		task.setSubjectassign(DictConstant.DICT_CROWDTASK_SUBJECTASSIGNMETHOD_SYSTEM);
		task.setSubjectassignseq(2);
		task.setSubjectnum(1);
		task.setTaskreward(DoubleUtil.multiply(subject.getSubjectreward(), (double)task.getSubjectnum()));
		task.setTaskrewardsettle(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_TASK);		
		task.setAutoverify(1);
		task.setVerifyreward(0.0);
		task.setVerifypassscore(0);
		task.setAutoqc(1);
		task.setQcreward(0.0);
		task.setQcrewardmeasure(DictConstant.DICT_CROWDTASK_MEASURE_TASK);
		task.setRewardtype(1);
		task.setUnitname("奖励金(元)");		
		task.setMaxnum(bean.getPeopleNum());
		task.setApplynum(0);
		task.setIngnum(0);
		task.setFinishnum(0);
		task.setDescription(bean.getDescription());
		task.setCreatetime(now);
		task.setSortindex(bean.getSortIndex());
		task.setStatus(SystemConstants.STATUS_ON);
		task.setLimituserid(bean.getLimitUserId());
		task.setOrderid(bean.getOrderId());
		task.setActid(bean.getActId());
		
		//适用平台
		if(bean.getLimitServiceType()!=null && bean.getLimitServiceType().length>0){
			CmcCtPlatform platform = null;
			for (String st : bean.getLimitServiceType()) {
				platform = new CmcCtPlatform();
				platform.setServicetype(st);
				task.getPlatforms().add(platform);
			}
		}
		
		//入库
		crowdTaskService.saveTask(task, libName, subject, bean.getOrderId());

		//更新任务缓存
		CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_CROWDTASK);
	}
	
	/**
	 * 修改任务
	 * @param bean
	 */
	public void updateTask(CrowdTaskEditBean bean){
		//封装题目
		CmcCtSubject subject = null;
		if(bean.getSubjectId()!=null){
			subject = crowdTaskLibService.getSubject(bean.getSubjectId());
			subject.setSubjectreward(bean.getSubjectReward());
			subject.setContent(bean.getSubjectContent());
			subject.setVideo(bean.getSubjectUrl());
			subject.setVideosource(bean.getSubjectSource());
			subject.setPic(bean.getSubjectPicUrl());
		}
		
		//封装任务
		CmcCtTask task = crowdTaskService.getTask(bean.getTaskId());
		task.setGroupcode(bean.getGroupCode());
		task.setTitle(bean.getTaskTitle());
		task.setStarttime(bean.getStartTime());
		task.setEndtime(bean.getEndTime());
		if(subject!=null){
			task.setTaskreward(DoubleUtil.multiply(subject.getSubjectreward(), (double)task.getSubjectnum()));
		}
		if(bean.getPeopleNum()>task.getApplynum()){
			task.setMaxnum(bean.getPeopleNum());
		}else{
			task.setMaxnum(bean.getApplyNum());
		}
		task.setDescription(bean.getDescription());
		task.setLimituserid(bean.getLimitUserId());
		task.setSortindex(bean.getSortIndex());
		task.setActid(bean.getActId());
		
		//适用平台
		if(bean.getLimitServiceType()!=null && bean.getLimitServiceType().length>0){
			CmcCtPlatform platform = null;
			for (String st : bean.getLimitServiceType()) {
				platform = new CmcCtPlatform();
				platform.setServicetype(st);
				task.getPlatforms().add(platform);
			}
		}
		
		//入库
		crowdTaskService.updateTask(task, subject);

		//更新任务缓存
		CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_CROWDTASK);
	}
	
	/**
	 * 取消停用过期任务（父任务除外）
	 */
	public void cancelTask(){
		//取消过期任务
		crowdTaskService.cancelTask();
		
		//更新任务缓存
		CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_CROWDTASK);
	}
	
	/**
	 * 修改任务描述
	 */
	public void updateTaskDescription(){
		List<CmcCtSubject> subjects = null;
		CmcCtSubjectcat subjectCat = null;
		String description = null;
		
		List<CmcCtTask> tasks = crowdTaskService.findOnTask();
		for (CmcCtTask task : tasks) {
			subjects = crowdTaskLibService.findSubject(task.getLibid(), null, 1);
			if(subjects.isEmpty()) continue;
			
			subjectCat = crowdTaskSubjectCatMap.getCat(Long.valueOf(subjects.get(0).getSubjectcat()));
			description = subjectCat.getDesctemplet().replaceAll("%subjectNum%", String.valueOf(task.getSubjectnum()));
			crowdTaskService.updateDescription(task.getTaskid(), description);
		}
		System.out.println("完成");
	}
}