package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.CrowdTaskBlacklistMap;
import com.qcmz.cmc.cache.CrowdTaskMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IUserCrowdTaskDao;
import com.qcmz.cmc.entity.CmcCtQc;
import com.qcmz.cmc.entity.CmcCtSubject;
import com.qcmz.cmc.entity.CmcCtTask;
import com.qcmz.cmc.entity.CmcCtUseranswer;
import com.qcmz.cmc.entity.CmcCtUsersubject;
import com.qcmz.cmc.entity.CmcCtUsertask;
import com.qcmz.cmc.service.ICrowdTaskLibService;
import com.qcmz.cmc.service.IRewardService;
import com.qcmz.cmc.service.IUserCrowdTaskQcService;
import com.qcmz.cmc.service.IUserCrowdTaskService;
import com.qcmz.cmc.util.CrowdTaskUtil;
import com.qcmz.cmc.vo.UserCrowdRewardGrantResult;
import com.qcmz.cmc.ws.provide.vo.UserCrowdAnswerBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdAnswerQcBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskApplyBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskListBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcListBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcQueryBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyListBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyQueryBean;
import com.qcmz.framework.constant.BusinessConstants;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class UserCrowdTaskServiceImpl implements IUserCrowdTaskService {
	@Autowired
	private IUserCrowdTaskDao userCrowdTaskDao;
	@Autowired
	private IUserCrowdTaskQcService userCrowdTaskQcService;
	@Autowired
	private IRewardService rewardService;
	@Autowired
	private ICrowdTaskLibService crowdTaskLibService;
	
	@Autowired
	private CrowdTaskMap crowdTaskMap;
	@Autowired
	private CrowdTaskBlacklistMap crowdTaskBlacklistMap;
	
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcCtUsertask，带CmcCtTask>
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		userCrowdTaskDao.queryByMapTerm(map, pageBean);
		
		List<CmcCtUsertask> list = (List<CmcCtUsertask>) pageBean.getResultList();
		if(list.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcCtUsertask utask : list) {
			userIds.add(utask.getUserid());
		}
		
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcCtUsertask utask : list) {
			utask.setInBlacklist(crowdTaskBlacklistMap.contains(utask.getUserid()));
			utask.setUser(userMap.get(utask.getUserid()));
		}
	}
	
	/**
	 * 获取最近完成任务的用户编号
	 * @param taskId
	 * @param limit
	 * @return
	 */
	public List<Long> findFinishUserId(Long taskId, int limit){
		return userCrowdTaskDao.findFinishUserId(taskId, limit);
	}
	
	/**
	 * 分页获取用户任务列表，带任务信息
	 * @param userId
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<UserCrowdTaskListBean> findUserTask(Long userId, int pageSize, String lastId){
		List<UserCrowdTaskListBean> result = new ArrayList<UserCrowdTaskListBean>();
		UserCrowdTaskListBean bean = null;
		
		List<CmcCtUsertask> list = userCrowdTaskDao.findUserTaskJoin(userId, pageSize, lastId);
		for (CmcCtUsertask utask : list) {
			bean = new UserCrowdTaskListBean();
			bean.setSortId(utask.getUtid());
			bean.setUtId(utask.getUtid());
			bean.setTitle(utask.getCmcCtTask().getTitle());
			bean.setPic(utask.getCmcCtTask().getPic());
			bean.setRewardType(utask.getCmcCtTask().getRewardtype());
			bean.setUnitName(utask.getCmcCtTask().getUnitname());
			bean.setTaskStartTime(utask.getCmcCtTask().getStarttime().getTime());
			bean.setTaskEndTime(utask.getCmcCtTask().getEndtime().getTime());
			bean.setSubjectNum(utask.getSubjectnum());
			bean.setSubjectAssign(utask.getCmcCtTask().getSubjectassign());
			
			bean.setApplyTime(DateUtil.getTime(utask.getApplytime()));
			bean.setUtStartTime(DateUtil.getTime(utask.getStarttime()));
			bean.setUtFinishTime(DateUtil.getTime(utask.getFinishtime()));
			bean.setFinishSubjectNum(utask.getFinishsubjectnum());
			bean.setTaskReward(utask.getTaskreward());
			bean.setTaskRewardSettle(utask.getCmcCtTask().getTaskrewardsettle());
			bean.setUtReward(utask.getUtreward());
			bean.setUtRewardTime(DateUtil.getTime(utask.getUtrewardtime()));
			bean.setRefuseReason(utask.getRefusereason());
			bean.setUtStatus(utask.getStatus());
			
			result.add(bean);
		}
		
		return result;
	}
	
	/**
	 * 获取用户指定任务列表
	 * @param userId
	 * @param taskIds
	 * @return
	 */
	public List<CmcCtUsertask> findUserTask(Long userId, Set<Long> taskIds){
		return userCrowdTaskDao.findUserTask(userId, taskIds);
	}
	
	/**
	 * 获取用户的用户任务
	 * @param userId
	 * @param statuses
	 * @return
	 */
	public List<CmcCtUsertask> findUserTask(Long userId, List<Integer> statuses){
		return userCrowdTaskDao.findUserTask(userId, statuses);
	}
	
	/**
	 * 分页获取用户任务列表
	 * @param taskId
	 * @param status
	 * @param minFinishTime
	 * @param maxFinishTime
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<CmcCtUsertask> findUserTaskByTaskId(Long taskId, Integer status, Date minFinishTime, Date maxFinishTime, int pageSize, String lastId){
		return userCrowdTaskDao.findUserTaskByTaskId(taskId, status, minFinishTime, maxFinishTime, pageSize, lastId);
	}
	
	/**
	 * 根据设备码获取用户任务编号列表
	 * @param taskId
	 * @param uuid
	 * @param status
	 * @return
	 */
	public List<String> findUserTaskByUuid(Long taskId, String uuid, Integer status){
		return userCrowdTaskDao.findUserTaskByUuid(taskId, uuid, status);
	}
	
	/**
	 * 分页获取用户任务报名审核列表
	 * @param query
	 * @param serviceType
	 * @param pageSize
	 * @return
	 */
	public List<UserCrowdTaskVerifyListBean> findUserTask4Verify(UserCrowdTaskVerifyQueryBean query, String serviceType, int pageSize){
		return userCrowdTaskDao.findUserTask4Verify(query, serviceType, pageSize);
	}
	
	/**
	 * 获取提醒用户完成的用户任务列表
	 * @return
	 */
	public List<CmcCtUsertask> findUserTask4RemindToFinish(){
		return userCrowdTaskDao.findUserTask4RemindToFinish();
	}
	
	/**
	 * 分页获取用户任务审校列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<UserCrowdTaskQcListBean> findUserTask4Qc(UserCrowdTaskQcQueryBean query, String serviceType, int pageSize){
		return userCrowdTaskDao.findUserTask4Qc(query, serviceType, pageSize);
	}
	
	/**
	 * 获取待发放任务奖励的列表
	 * @return
	 */
	public List<String> findUserTask4GrantTaskReward(){
		return userCrowdTaskDao.findUserTask4GrantTaskReward();
	}
	
	
	/**
	 * 获取待发放审核奖励的列表
	 * @return
	 */
	public List<String> findUserTask4GrantVerifyReward(){
		return userCrowdTaskDao.findUserTask4GrantVerifyReward();
	}
	
	/**
	 * 获取用户任务数
	 * @param userId
	 * @param statuses
	 * @return
	 */
	public Long getCount(Long userId, List<Integer> statuses){
		return userCrowdTaskDao.getCount(userId, statuses);
	}
	
	/**
	 * 获取审核数量
	 * @param serviceType not null
	 * @param verifyStatus not null
	 * @param verifyUserId
	 * @return
	 */
	public Long getVerifyCount(String serviceType, Integer verifyStatus, Long verifyUserId){
		return userCrowdTaskDao.getVerifyCount(serviceType, verifyStatus, verifyUserId);
	}
	
	/**
	 * 获取审校数量
	 * @param serviceType not null
	 * @param qcStatus not null
	 * @param qcUserId
	 * @return
	 */
	public Long getQcCount(String serviceType, Integer qcStatus, Long qcUserId){
		return userCrowdTaskDao.getQcCount(serviceType, qcStatus, qcUserId);
	}
	
	/**
	 * 获取超时未发放任务奖励的数量
	 * @return
	 */
	public Long getCount4UngrantTaskReward(){
		return userCrowdTaskDao.getCount4UngrantTaskReward();
	}
	
	/**
	 * 获取超时未发放审核奖励的数量
	 * @return
	 */
	public Long getCount4UngrantVerifyReward(){
		return userCrowdTaskDao.getCount4UngrantVerifyReward();
	}
	
	/**
	 * 获取用户任务
	 * @param utId
	 * @return
	 */
	public CmcCtUsertask getUserTask(String utId){
		return (CmcCtUsertask) userCrowdTaskDao.load(CmcCtUsertask.class, utId);
	}
	
	/**
	 * 获取用户任务，带任务信息
	 * @param utId
	 * @return
	 */
	public CmcCtUsertask getUserTaskJoin(String utId){
		return (CmcCtUsertask) userCrowdTaskDao.getUserTaskJoin(utId);
	}
	
	/**
	 * 获取用户任务，带用户、任务、题目、答案
	 * @param utId
	 * @return
	 */
	public CmcCtUsertask getUserTaskJoinAll(String utId){
		CmcCtUsertask utask = getUserTaskJoin(utId);

		//题目
		List<CmcCtUsersubject> userSubjects = findUserSubjectJoin(utId, utask.getTaskid(), null, null);
		utask.setUserSubjects(userSubjects);
		
		Map<Long, CmcCtUsersubject> userSubjectMap = new HashMap<Long, CmcCtUsersubject>();		
		for (CmcCtUsersubject userSubject : userSubjects) {
			userSubjectMap.put(userSubject.getSubjectid(), userSubject);
		}
		
		//答案
		List<CmcCtUseranswer> userAnswers = findUserAnswer(utId, utask.getTaskid(), null, null);
		for (CmcCtUseranswer userAnswer : userAnswers) {
			userSubjectMap.get(userAnswer.getSubjectid()).getUserAnswers().add(userAnswer);
		}
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		userIds.add(utask.getUserid());
		if(utask.getVerifyuserid()!=null){
			userIds.add(utask.getVerifyuserid());
		}
				
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		utask.setUser(userMap.get(utask.getUserid()));
		if(utask.getVerifyuserid()!=null){
			utask.setVerifyUser(userMap.get(utask.getVerifyuserid()));
		}
		
		return utask;
	}
	
	/**
	 * 获取用户任务
	 * @param userId
	 * @param taskId
	 * @return
	 */
	public CmcCtUsertask getUserTask(Long userId, Long taskId){
		return userCrowdTaskDao.getUserTask(userId, taskId);
	}
	
	/**
	 * 用户是否已参与同组任务（已取消的不算）
	 * @param userId not null
	 * @param groupCode not null
	 * @return
	 */
	public boolean isPartInGroupTask(Long userId, String groupCode){
		return userCrowdTaskDao.isPartInGroupTask(userId, groupCode);
	}
	
	/**
	 * 更新国家和城市
	 * @param utId
	 * @param countryName
	 * @param cityName
	 */
	public void updateCountryAndCity(String utId, String countryName, String cityName){
		userCrowdTaskDao.updateCountryAndCity(utId, countryName, cityName);
	}
	
	/**
	 * 获取用户任务题目列表
	 * @param utId not null
	 * @param taskId not null
	 * @param subjectIds
	 * @return
	 */
	public List<CmcCtUsersubject> findUserSubject(String utId, Long taskId, List<Long> subjectIds){
		return userCrowdTaskDao.findUserSubject(utId, taskId, subjectIds); 
	}
	
	/**
	 * 获取用户任务题目列表，带CmcCtSubject
	 * @param utId
	 * @param taskId
	 * @param qcId
	 * @param qcStatuses
	 * @return
	 */
	public List<CmcCtUsersubject> findUserSubjectJoin(String utId, Long taskId, Long qcId, List<Integer> qcStatuses){
		return userCrowdTaskDao.findUserSubjectJoin(utId, taskId, qcId, qcStatuses);
	}

	/**
	 * 获取用户任务题目编号列表
	 * @param utId not null
	 * @param taskId not null
	 * @param subjectIds
	 * @param answerStatus
	 * @return
	 */
	public List<Long> findUserSubjectId(String utId, Long taskId, List<Long> subjectIds, Integer answerStatus){
		return userCrowdTaskDao.findUserSubjectId(utId, taskId, subjectIds, answerStatus);
	}
	
	/**
	 * 获取待发放任务奖励的用户题目列表
	 * @return
	 */
	public List<Long> findUserSubject4GrantTaskReward(){
		return userCrowdTaskDao.findUserSubject4GrantTaskReward();
	}
	
	/**
	 * 获取用户题目
	 * @param utId
	 * @param subjectId
	 * @return
	 */
	public CmcCtUsersubject getUserSubject(String utId, Long subjectId){
		return userCrowdTaskDao.getUserSubject(utId, subjectId);
	}
	
	/**
	 * 获取用户任务最大题目编号
	 * @param utId
	 * @return
	 */
	public Long getMaxSubjectId(String utId){
		return userCrowdTaskDao.getMaxSubjectId(utId);
	}
	
	/**
	 * 获取用户任务答案列表
	 * @param utId
	 * @param taskId
	 * @param subjectIds
	 * @param qcId
	 * @return
	 */
	public List<CmcCtUseranswer> findUserAnswer(String utId, Long taskId, List<Long> subjectIds, Long qcId){
		return userCrowdTaskDao.findUserAnswer(utId, taskId, subjectIds, qcId);
	}
	
	/**
	 * 获取用户任务答案列表
	 * @param utId
	 * @param taskId
	 * @return
	 */
	public List<CmcCtUseranswer> findUserAnswerJoinSubject(String utId, Long taskId){
		return userCrowdTaskDao.findUserAnswerJoinSubject(utId, taskId);
	}
	
	/**
	 * 获取任务的用户任务答案列表，带CmcCtUsertask
	 * @param taskId not null
	 * @param taskStatus
	 * @return
	 */
	public List<CmcCtUseranswer> findUserAnswerJoin(Long taskId, Integer taskStatus){
		return userCrowdTaskDao.findUserAnswerJoin(taskId, taskStatus);
	}
	
	/**
	 * 分页获取要删除文件的用户任务答案列表
	 * 自动取消、公司任务、已完成/已取消、指定完成/取消日期
	 * @param date 完成/取消日期 not null
	 * @param pageSize not null
	 * @param lastAnswerId
	 * @return
	 */
	public List<CmcCtUseranswer> findUserAnswer4DeleteFile(Date date, int pageSize, Long lastAnswerId){
		return userCrowdTaskDao.findUserAnswer4DeleteFile(date, pageSize, lastAnswerId);
	}
	
	/**
	 * 获取用户任务答案信息
	 * @param uaid
	 * @return
	 */
	public CmcCtUseranswer getUserAnswer(Long uaid){
		return (CmcCtUseranswer) userCrowdTaskDao.load(CmcCtUseranswer.class, uaid);
	}
	
	/**
	 * 获取用户任务答案信息，带CmcCtUsertask
	 * @param uaid
	 * @return
	 */
	public CmcCtUseranswer getUserAnswerJoin(Long uaid){
		return userCrowdTaskDao.getUserAnswerJoin(uaid);
	}
	
	/**
	 * 报名
	 * @param bean
	 * @param task
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcCtUsertask applyTask(UserCrowdTaskApplyBean bean, CmcCtTask task, String serviceType, String platform, String version){
		CmcCtUsertask utask = new CmcCtUsertask();
		utask.setUtid(userCrowdTaskDao.newUtId());
		utask.setServicetype(serviceType);
		utask.setUserid(bean.getUid());
		utask.setTaskid(bean.getTaskId());
		utask.setPretaskid(task.getPretaskid());
		utask.setTaskreward(task.getTaskreward());
		utask.setSubjectnum(task.getSubjectnum());
		utask.setLon(bean.getLon());
		utask.setLat(bean.getLat());
		utask.setPlatform(platform);
		utask.setVersion(version);
		utask.setUuid(bean.getUuid());
		utask.setApplytime(new Date());
		utask.setStatus(DictConstant.DICT_USERCROWDTASK_STATUS_APPLY);
		utask.setVerifyreward(task.getVerifyreward());
		utask.setVerifystatus(DictConstant.DICT_USERCROWDTASK_VERIFYSTATUS_WAITING);
		userCrowdTaskDao.save(utask);
		
		//前置任务答题
		if(task.getPretaskid()!=null){
			answerSubject(utask.getUtid(), task.getPretaskid(), bean.getPreAnswers());
		}
		
		//自动审核
		if(SystemConstants.STATUS_ON.equals(task.getAutoverify())){
			utask.setCmcCtTask(task);
			autoVerify(utask, bean.getPreAnswers(), task.getVerifypassscore());
		}
		
		return utask;
	}
	
	/**
	 * 自动审核
	 * @param utask
	 * @param preAnswers
	 * @param passScore
	 * @return
	 */
	private boolean autoVerify(CmcCtUsertask utask, List<UserCrowdAnswerBean> preAnswers, Integer passScore){
		//审核通过、不通过
		boolean passVerify = false;
		if(utask.getPretaskid()==null){
			passVerify = true;
		}else{
			//有前置任务，没有得分，不自动审核
			Integer totalScore = CrowdTaskUtil.calTotalScore(preAnswers);
			if(totalScore==null){
				return false;
			}else if(totalScore>=passScore){
				passVerify = true;
			}
		}
		
		//开始审核
		saveStartVerify(utask, BusinessConstants.getSystemUserId(utask.getServicetype()));
		
		//审核
		if(passVerify){
			savePassApply(utask);
		}else{
			saveRejectApply(utask, "资格测试没通过");
		}
		
		//审核奖励
		utask.setVerifyreward(0.0);
		utask.setVerifyrewardtime(new Date());
		utask.setVerifyrewardstatus(DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_DEALT);
		userCrowdTaskDao.update(utask);
		
		return true;
	}
	
	/**
	 * 开始审核
	 * @param utask
	 * @param verifyUserId
	 */
	public void saveStartVerify(CmcCtUsertask utask, Long verifyUserId){
		utask.setVerifyuserid(verifyUserId);
		utask.setVerifystarttime(new Date());
		utask.setVerifystatus(DictConstant.DICT_USERCROWDTASK_VERIFYSTATUS_PROCESSING);
		userCrowdTaskDao.update(utask);
	}
	
	/**
	 * 拒绝报名申请
	 * @param utask
	 * @param reason
	 */
	public void saveRejectApply(CmcCtUsertask utask, String reason){
		utask.setVerifyfinishtime(new Date());
		utask.setVerifypass(SystemConstants.STATUS_OFF);
		utask.setVerifystatus(DictConstant.DICT_USERCROWDTASK_VERIFYSTATUS_DEALT);
		utask.setStatus(DictConstant.DICT_USERCROWDTASK_STATUS_REJECTAPPLY);
		utask.setVerifyrewardstatus(DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
		utask.setRefusereason(reason);
		userCrowdTaskDao.saveOrUpdate(utask);
	}
	
	/**
	 * 通过报名申请
	 * @param utask 带CmcCtTask
	 * @exception DataException
	 */
	public void savePassApply(CmcCtUsertask utask){
		CmcCtTask task = utask.getCmcCtTask();
		
		//报名审核结果
		utask.setVerifyfinishtime(new Date());
		utask.setVerifypass(SystemConstants.STATUS_ON);
		utask.setVerifystatus(DictConstant.DICT_USERCROWDTASK_VERIFYSTATUS_DEALT);
		utask.setStatus(DictConstant.DICT_USERCROWDTASK_STATUS_ACCEPTAPPLY);
		utask.setVerifyrewardstatus(DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
		
		
		//分配题目
		Double taskReward = 0.0;
		Long lastSubjectId = null;
		List<CmcCtUsersubject> userSubjects = new ArrayList<CmcCtUsersubject>();
		CmcCtUsersubject userSubject = null;
		
		if(DictConstant.DICT_CROWDTASK_SUBJECTASSIGNMETHOD_SYSTEM.equals(task.getSubjectassign())){
			List<CmcCtSubject> subjects = crowdTaskLibService.findSubject(task.getLibid(), true, task.getSubjectnum());
			if(subjects.size()<task.getSubjectnum()){
				subjects.addAll(crowdTaskLibService.findSubject(task.getLibid(), false, task.getSubjectnum()-subjects.size()));
			}
			
			for (CmcCtSubject subject : subjects) {
				userSubject = new CmcCtUsersubject();
				userSubject.setUtid(utask.getUtid());
				userSubject.setTasktype(task.getTasktype());
				userSubject.setTaskid(utask.getTaskid());
				userSubject.setSubjectid(subject.getSubjectid());
				userSubject.setSubjectreward(subject.getSubjectreward());
				userSubject.setAnswerstatus(SystemConstants.STATUS_OFF);
				userSubjects.add(userSubject);
				
				taskReward = DoubleUtil.add(taskReward, subject.getSubjectreward());
				lastSubjectId = subject.getSubjectid();
			}
			
			//重新计算任务奖励
			utask.setTaskreward(taskReward);
		}
		
		//入库
		userCrowdTaskDao.update(utask);
		if(!userSubjects.isEmpty()){
			userCrowdTaskDao.saveOrUpdateAll(userSubjects);
			//更新上次已分配的题目编号
			crowdTaskLibService.updateAssignedSubjectId(task.getLibid(), lastSubjectId);
		}
	}
	
	/**
	 * 取消任务
	 * @param utId
	 * @param reason
	 * @param operCd
	 * @param operName
	 */
	public void saveCancelTask(String utId, String reason, String operCd, String operName){
		//更新用户任务
		CmcCtUsertask utask = getUserTask(utId);
		utask.setCanceltime(new Date());
		utask.setCancelreason(reason);
		utask.setCancelcd(operCd);
		utask.setCancelname(operName);
		utask.setStatus(DictConstant.DICT_USERCROWDTASK_STATUS_CANCEL);
		userCrowdTaskDao.update(utask);
	}
	
	/**
	 * 保存用户任务
	 * @param ut
	 */
	public void saveOrUpdateTask(CmcCtUsertask ut){
		userCrowdTaskDao.saveOrUpdate(ut);
	}
	
	/**
	 * 答题
	 * @param utId
	 * @param taskType
	 * @param taskId
	 * @param answers
	 */
	public void answerSubject(String utId, Long taskId, List<UserCrowdAnswerBean> answers){
		List<CmcCtUsersubject> userSubjects = new ArrayList<CmcCtUsersubject>();
		List<CmcCtUseranswer> userAnswers = new ArrayList<CmcCtUseranswer>();
		Set<Long> existSubjectIds = new HashSet<Long>();

		CmcCtUsersubject userSubject;
		CmcCtUseranswer userAnswer;
		
		Integer qcStatus = null;
		Date now = new Date();
		
		CmcCtTask task = crowdTaskMap.getTask(taskId);
		//按题结算的任务，答题后题目置为待审校
		if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_SUBJECT.equals(task.getTaskrewardsettle())){
			qcStatus = DictConstant.DICT_USERCROWDTASK_QCSTATUS_WAITING;
		}

		//涉及题目编号
		List<Long> subjectIds = new ArrayList<Long>();
		for (UserCrowdAnswerBean answer : answers) {
			if(!subjectIds.contains(answer.getSubjectId())){
				subjectIds.add(answer.getSubjectId());
			}
		}
		
		//已存在的用户题目
		List<CmcCtUsersubject> existUserSubjects = findUserSubject(utId, taskId, subjectIds);
		for (CmcCtUsersubject usubject : existUserSubjects) {
			usubject.setAnswerstatus(SystemConstants.STATUS_ON);
			usubject.setAnswertime(now);
			usubject.setQcstatus(qcStatus);
			existSubjectIds.add(usubject.getSubjectid());
		}
		userSubjects.addAll(existUserSubjects);
		
		//新加用户题目
		List<Long> newSubjectIds = new ArrayList<Long>();
		for (Long subjectId : subjectIds) {
			if(!existSubjectIds.contains(subjectId)){
				newSubjectIds.add(subjectId);
			}
		}
		if(!newSubjectIds.isEmpty()){
			Map<Long, CmcCtSubject> subjectMap = new HashMap<Long, CmcCtSubject>();
			List<CmcCtSubject> subjects = crowdTaskLibService.findSubjectBySubjectId(newSubjectIds);
			for (CmcCtSubject subject : subjects) {
				subjectMap.put(subject.getSubjectid(), subject);
			}
			for (Long subjectId : newSubjectIds) {
				userSubject = new CmcCtUsersubject();
				userSubject.setUtid(utId);
				userSubject.setTasktype(task.getTasktype());
				userSubject.setTaskid(taskId);
				userSubject.setSubjectid(subjectId);
				userSubject.setSubjectreward(subjectMap.get(subjectId).getSubjectreward());
				userSubject.setAnswerstatus(SystemConstants.STATUS_ON);
				userSubject.setAnswertime(now);
				userSubject.setQcstatus(qcStatus);
				userSubjects.add(userSubject);
			}
		}
		
		//用户答案
		for (UserCrowdAnswerBean answer : answers) {
			userAnswer = new CmcCtUseranswer();
			userAnswer.setUtid(utId);
			userAnswer.setTasktype(task.getTasktype());
			userAnswer.setTaskid(taskId);
			userAnswer.setSubjectid(answer.getSubjectId());
			userAnswer.setOptionid(answer.getOptionId());
			userAnswer.setOptionvalue(answer.getOptionValue());
			userAnswer.setMorecontent(answer.getMoreContent());
			userAnswer.setVoice(answer.getVoice());
			userAnswer.setVoiceduration(answer.getVoiceDuration());
			userAnswer.setDst(answer.getDst());
			userAnswer.setScore(answer.getAnswerScore());
			userAnswer.setPic(answer.getPic());
			
			userAnswers.add(userAnswer);
		}
		
		//用户题目入库
		userCrowdTaskDao.saveOrUpdateAll(userSubjects);
		
		//清除用户答案,先清除再入库
		if(!existSubjectIds.isEmpty()){
			userCrowdTaskDao.clearAnswer(utId, existSubjectIds);
		}
		userCrowdTaskDao.saveOrUpdateAll(userAnswers);
		
		//更新用户已答题数
		userCrowdTaskDao.updateFinishSubjectNum(utId);
		
		//一题一结+自动审校
		if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_SUBJECT.equals(task.getTaskrewardsettle())){
			boolean autoQc = false;
			if(SystemConstants.STATUS_ON.equals(task.getAutoqc())){
				//自动审校
				autoQc = autoQc(utId, taskId, subjectIds);
			}
			if(!autoQc){
				//创建审校任务，人工审校
				userCrowdTaskQcService.saveQc(utId, taskId);
			}
		}
	}
	
	/**
	 * 保存完成任务，如果可以则完成自动审校
	 * @param utId
	 * @return
	 */
	public CmcCtUsertask saveFinishTask(String utId){
		CmcCtUsertask utask = getUserTaskJoin(utId);
		utask.setFinishtime(new Date());
		if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_SUBJECT.equals(utask.getCmcCtTask().getTaskrewardsettle())){
			utask.setStatus(DictConstant.DICT_USERCROWDTASK_STATUS_FINISH);
		}else{
			utask.setStatus(DictConstant.DICT_USERCROWDTASK_STATUS_SUBMIT);
		}
		userCrowdTaskDao.update(utask);
		
		if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_TASK.equals(utask.getCmcCtTask().getTaskrewardsettle())){
			boolean autoQc = false;
			if(SystemConstants.STATUS_ON.equals(utask.getCmcCtTask().getAutoqc())){
				autoQc = autoQc(utask.getUtid(), utask.getTaskid(), null);
			}
			if(!autoQc){
				//创建审校任务，人工审校
				userCrowdTaskQcService.saveQc(utId, utask.getTaskid());
				//题目审校状态置为待审校
				userCrowdTaskDao.updateSubjectWaitQc(utId, utask.getTaskid());
			}
		}
			
		return utask;
	}
	
	/**
	 * 自动审校
	 * 自动审校失败，创建审校任务转人工审校
	 * @param utask
	 * @return
	 */
	private boolean autoQc(String utId, Long taskId, List<Long> subjectIds){
		List<CmcCtUseranswer> subjectAnswers;
		Map<Long, List<CmcCtUseranswer>> answerMap = new HashMap<Long, List<CmcCtUseranswer>>();
		Integer compositeScore = null;
		boolean autoQc = true;
		
		CmcCtTask task = crowdTaskMap.getTask(taskId);
		
		List<CmcCtUseranswer> userAnswers = findUserAnswer(utId, taskId, subjectIds, null);
		for (CmcCtUseranswer userAnswer : userAnswers) {
			subjectAnswers = answerMap.get(userAnswer.getSubjectid());
			if(subjectAnswers==null){
				subjectAnswers = new ArrayList<CmcCtUseranswer>();
			}
			subjectAnswers.add(userAnswer);
			answerMap.put(userAnswer.getSubjectid(), subjectAnswers);
		}
		
		List<CmcCtUsersubject> userSubjects = findUserSubject(utId, taskId, subjectIds);
		for (CmcCtUsersubject userSubject : userSubjects) {
			compositeScore = userSubject.getScore();
			if(compositeScore==null){
				subjectAnswers = answerMap.get(userSubject.getSubjectid());
				for (CmcCtUseranswer userAnswer : subjectAnswers) {
					if(userAnswer.getScore()==null) continue;
					if(compositeScore==null || compositeScore<userAnswer.getScore()){
						compositeScore = userAnswer.getScore();
					}
				}
			}
			if(compositeScore==null){
				autoQc = false;
				break;
			}
			userSubject.setUsrewardstatus(DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
			userSubject.setCompositescore(compositeScore);
			userSubject.setQcstatus(DictConstant.DICT_USERCROWDTASK_QCSTATUS_DEALT);
		}
		
		if(autoQc){
			//更新题目综合积分
			userCrowdTaskDao.saveOrUpdateAll(userSubjects);
			
			//更新用户任务奖励到账状态
			if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_TASK.equals(task.getTaskrewardsettle())){
				userCrowdTaskDao.updateUtRewardStatus(utId, DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
			}
		}
		return autoQc;
	}
	
	/**
	 * 保存题目审校结果
	 * @param utId
	 * @param qcId
	 * @param subjectId
	 * @param subjectScore
	 * @param answerQcs
	 */
	public void saveSubjectQc(String utId, Long qcId, Long subjectId, Integer subjectScore, List<UserCrowdAnswerQcBean> answerQcs){
		Integer compositeScore = subjectScore;
		//答案审校
		if(answerQcs!=null && !answerQcs.isEmpty()){
			Integer maxScore = null;
			for (UserCrowdAnswerQcBean qc : answerQcs) {
				userCrowdTaskDao.updateAnswerScore(qc.getUaId(), qc.getAnswerScore());
				if(maxScore==null || maxScore<qc.getAnswerScore()){
					maxScore = qc.getAnswerScore();
				}
			}
			if(compositeScore==null){
				compositeScore = maxScore;
			}
		}
		
		//题目得分与审校状态
		CmcCtUsersubject userSubject = userCrowdTaskDao.getUserSubject(utId, subjectId);
		if(subjectScore!=null){
			userSubject.setScore(subjectScore);
		}
		userSubject.setCompositescore(compositeScore);
		userSubject.setQcstatus(DictConstant.DICT_USERCROWDTASK_QCSTATUS_DEALT);
		userSubject.setQcid(qcId);
		
		CmcCtTask task = crowdTaskMap.getTask(userSubject.getTaskid());
		if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_SUBJECT.equals(task.getTaskrewardsettle())){
			userSubject.setUsrewardstatus(DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
		}
		
		userCrowdTaskDao.update(userSubject);
		
		//更新已审校题目数
		userCrowdTaskQcService.updateQcFinishNum(qcId);
	}
	
	/**
	 * 完成审校
	 * @param utId
	 * @param qcUserId
	 * @exception DataException
	 */
	public void finishQc(String utId, Long qcUserId){
		//校验
		CmcCtQc qc = userCrowdTaskQcService.getQc(utId, DictConstant.DICT_USERCROWDTASK_QCSTATUS_PROCESSING);
		if(!qc.getQcuserid().equals(qcUserId)){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		//按任务结算的任务检查是否所有题目都已完成
		CmcCtUsertask utask = getUserTaskJoin(utId);
		if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_TASK.equals(utask.getCmcCtTask().getTaskrewardsettle())){
			if(qc.getQcfinishnum()<utask.getFinishsubjectnum()){
				throw new DataException("有题目未审校");
			}
		}	
		
		//更新用户任务审校
		qc.setQcfinishtime(new Date());
		qc.setQcstatus(DictConstant.DICT_USERCROWDTASK_QCSTATUS_DEALT);
		qc.setQcrewardstatus(DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
		userCrowdTaskQcService.updateQc(qc);
		
		//按任务结算的任务
		if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_TASK.equals(utask.getCmcCtTask().getTaskrewardsettle())){
			//用户题目奖励状态置为待发放
			userCrowdTaskDao.updateSubjectRewardStatus(qc.getUtid(), qc.getQcid(), DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
			//更新用户任务奖励到账状态
			userCrowdTaskDao.updateUtRewardStatus(utId, DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_WAITING);
		}

		
		//一题一结的任务，如果还有题目未审校则再生成新的审校任务
		if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_SUBJECT.equals(utask.getCmcCtTask().getTaskrewardsettle())){
			Long qcFinishNum = userCrowdTaskDao.getUserSubjectCount(utId, DictConstant.DICT_USERCROWDTASK_QCSTATUS_DEALT);
			if(qcFinishNum<utask.getFinishsubjectnum()){
				userCrowdTaskQcService.saveQc(utId, qc.getTaskid());
			}
		}
	}
	
	/**
	 * 按任务发放任务奖励
	 * @param utId
	 */
	public UserCrowdRewardGrantResult grantTaskReward(String utId){
		Double taskReward = 0.0;
		Double subjectReward;
		Date now = new Date();
		
		CmcCtUsertask utask = getUserTask(utId);
		CmcCtTask task = crowdTaskMap.getTask(utask.getTaskid());
		
		//计算题目奖励和任务奖励
		List<CmcCtUsersubject> userSubjects = findUserSubjectJoin(utask.getUtid(), utask.getTaskid(), null, null);
		for (CmcCtUsersubject userSubject : userSubjects) {
			if(userSubject.getCompositescore()>=userSubject.getCmcCtSubject().getPassscore()){
				if(userSubject.getSubjectreward()!=null){
					subjectReward = userSubject.getSubjectreward();
				}else{
					subjectReward = userSubject.getCmcCtSubject().getSubjectreward();
				}
			}else{
				subjectReward = 0.0;
			}
			userSubject.setUsreward(subjectReward);
			userSubject.setUsrewardtime(now);
			taskReward = DoubleUtil.add(taskReward, subjectReward);
		}		
		
		//发放奖励
		if(taskReward>0){
			rewardService.grantReward(utask.getServicetype(), utask.getUserid(), taskReward, task.getTitle(), DictConstants.DICT_SUBSERVICETYPE_CROWDTASK, utask.getUtid());
		}
		
		//题目奖励入库
		userCrowdTaskDao.saveOrUpdateAll(userSubjects);

		//任务奖励入库
		utask.setUtreward(taskReward);
		utask.setUtrewardtime(new Date());
		utask.setUtrewardstatus(DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_DEALT);
		utask.setStatus(DictConstant.DICT_USERCROWDTASK_STATUS_FINISH);
		userCrowdTaskDao.save(utask);
		
		UserCrowdRewardGrantResult result = new UserCrowdRewardGrantResult();
		result.setRewardCat(DictConstant.DICT_USERCROWDTASK_REWARDCAT_TASK);
		result.setUtId(utId);
		result.setUserId(utask.getUserid());
		result.setReward(taskReward);
		result.setUnitName(task.getUnitname());
		result.setTitle(task.getTitle());
		return result;
	}
	
	/**
	 * 按题目发放任务奖励
	 * @param usId
	 */
	public UserCrowdRewardGrantResult grantTaskRewardBySubject(Long usId){
		Double subjectReward;
		
		//计算题目奖励和任务奖励
		CmcCtUsersubject userSubject = userCrowdTaskDao.getUserSubjectJoin(usId);
		CmcCtTask task = userSubject.getCmcCtTask();
		CmcCtUsertask utask = userSubject.getCmcCtUsertask();
		
		if(userSubject.getCompositescore()>=userSubject.getCmcCtSubject().getPassscore()){
			subjectReward = userSubject.getCmcCtSubject().getSubjectreward();
		}else{
			subjectReward = 0.0;
		}
		
		//发放奖励
		if(subjectReward>0){
			rewardService.grantReward(utask.getServicetype(), utask.getUserid(), subjectReward, task.getTitle(), DictConstants.DICT_SUBSERVICETYPE_CROWDTASK, utask.getUtid()+"-"+userSubject.getUsid());
		}
		
		//题目奖励入库
		userSubject.setUsreward(subjectReward);
		userSubject.setUsrewardtime(new Date());
		userSubject.setUsrewardstatus(DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_DEALT);
		userCrowdTaskDao.update(userSubject);

		//更新用户任务奖励
		userCrowdTaskDao.updateUtReward(utask.getUtid());		
		
		UserCrowdRewardGrantResult result = new UserCrowdRewardGrantResult();
		result.setRewardCat(DictConstant.DICT_USERCROWDTASK_REWARDCAT_TASK);
		result.setUtId(userSubject.getUtid());
		result.setUserId(utask.getUserid());
		result.setReward(subjectReward);
		result.setUnitName(task.getUnitname());
		result.setTitle(task.getTitle());
		return result;
	}
	
	/**
	 * 发放报名审核奖励
	 * @param utId
	 */
	public UserCrowdRewardGrantResult grantVerifyReward(String utId){
		CmcCtUsertask ut = getUserTask(utId);
		CmcCtTask task = crowdTaskMap.getTask(ut.getTaskid());
		
		//发放奖励
		String taskTitle = new StringBuilder(task.getTitle()).append("（审核）").toString();
		if(ut.getVerifyreward()>0){
			rewardService.grantReward(ut.getServicetype(), ut.getVerifyuserid(), ut.getVerifyreward(), taskTitle, DictConstants.DICT_SUBSERVICETYPE_CROWDTASK, ut.getUtid());
		}
		
		//奖励入库
		ut.setVerifyrewardtime(new Date());
		ut.setVerifyrewardstatus(DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_DEALT);
		userCrowdTaskDao.save(ut);
		
		UserCrowdRewardGrantResult result = new UserCrowdRewardGrantResult();
		result.setRewardCat(DictConstant.DICT_USERCROWDTASK_REWARDCAT_VERIFY);
		result.setUtId(utId);
		result.setUserId(ut.getVerifyuserid());
		result.setReward(ut.getVerifyreward());
		result.setUnitName(task.getUnitname());
		result.setTitle(taskTitle);
		return result;
	}
	
	/**
	 * 发放审校奖励
	 * @param qcId
	 */
	public UserCrowdRewardGrantResult grantQcReward(Long qcId){
		CmcCtQc qc = userCrowdTaskQcService.getQc(qcId);
		CmcCtUsertask utask = getUserTask(qc.getUtid());
		CmcCtTask task = crowdTaskMap.getTask(utask.getTaskid());
		
		//审校奖励
		Double qcReward = task.getQcreward();
		if(DictConstant.DICT_CROWDTASK_MEASURE_SUBJECT.equals(task.getQcrewardmeasure())){
			qcReward = qc.getQcfinishnum()*task.getQcreward();
		}
		
		//发放奖励
		String taskTitle = new StringBuilder(task.getTitle()).append("（审校）").toString();
		if(qcReward>0){
			rewardService.grantReward(utask.getServicetype(), qc.getQcuserid(), qcReward, taskTitle, DictConstants.DICT_SUBSERVICETYPE_CROWDTASK, utask.getUtid()+"-"+qc.getQcid());
		}
		
		//奖励入库
		qc.setQcreward(qcReward);
		qc.setQcrewardtime(new Date());
		qc.setQcrewardstatus(DictConstant.DICT_USERCROWDTASK_GRANTSTATUS_DEALT);
		userCrowdTaskQcService.updateQc(qc);
		
		UserCrowdRewardGrantResult result = new UserCrowdRewardGrantResult();
		result.setRewardCat(DictConstant.DICT_USERCROWDTASK_REWARDCAT_QC);
		result.setUtId(utask.getUtid());
		result.setUserId(qc.getQcuserid());
		result.setReward(qc.getQcreward());
		result.setUnitName(task.getUnitname());
		result.setTitle(taskTitle);
		return result;
	}
	
	/**
	 * 批量取消指定任务未完成的用户任务
	 * @param taskId not null
	 * @param reason 取消原因
	 * @param daysAgo 多少天之前申请
	 * @param operCd 操作人账号
	 * @param operName 操作人姓名
	 */
	public int cancelByTaskId(Long taskId, String reason, Integer daysAgo, String operCd, String operName){
		return userCrowdTaskDao.cancelByTaskId(taskId, reason, daysAgo, operCd, operName);
	}
	
	/**
	 * 批量取消指定用户未完成的用户任务
	 * @param userId
	 * @param reason
	 * @param operCd
	 * @param operName
	 * @return
	 */
	public int cancelByUserId(Long userId, String reason, String operCd, String operName){
		return userCrowdTaskDao.cancelByUserId(userId, reason, operCd, operName);
	}
}
