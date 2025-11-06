package com.qcmz.cmc.process;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.CrowdTaskBlacklistMap;
import com.qcmz.cmc.cache.CrowdTaskLibMap;
import com.qcmz.cmc.cache.CrowdTaskMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcCtOption;
import com.qcmz.cmc.entity.CmcCtQc;
import com.qcmz.cmc.entity.CmcCtSubject;
import com.qcmz.cmc.entity.CmcCtTask;
import com.qcmz.cmc.entity.CmcCtUseranswer;
import com.qcmz.cmc.entity.CmcCtUsersubject;
import com.qcmz.cmc.entity.CmcCtUsertask;
import com.qcmz.cmc.service.ICrowdTaskLibService;
import com.qcmz.cmc.service.ICrowdTaskService;
import com.qcmz.cmc.service.ILockService;
import com.qcmz.cmc.service.IUserCrowdTaskQcService;
import com.qcmz.cmc.service.IUserCrowdTaskService;
import com.qcmz.cmc.thrd.UserCrowdTaskQcNotifyThrd;
import com.qcmz.cmc.thrd.UserCrowdTaskRegeocodeThrd;
import com.qcmz.cmc.thrd.UserCrowdTaskVerifyNotifyThrd;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.util.CrowdTaskUtil;
import com.qcmz.cmc.util.UserUtil;
import com.qcmz.cmc.ws.provide.vo.CrowdSubjectBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdAnswerBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdOptionBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectAnswerBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectQcBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskApplyBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskApplyResult;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskCountBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskDealCountBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskDetailBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskUploadBean;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskUploadResult;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyBean;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.geography.GeographyBean;
import com.qcmz.framework.geography.GeographyUtil;
import com.qcmz.framework.media.AudioUtil;
import com.qcmz.framework.media.vo.AudioBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.FileServiceUtil;
import com.qcmz.framework.util.FileTypeUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserMap;

/**
 * 众包任务处理
 */
@Component
public class UserCrowdTaskProcess {
	@Autowired
	private IUserCrowdTaskService userCrowdTaskService;
	@Autowired
	private IUserCrowdTaskQcService userCrowdTaskQcService;
	@Autowired
	private ICrowdTaskLibService crowdTaskLibService;
	@Autowired
	private ICrowdTaskService crowdTaskService;

	@Autowired
	private CrowdTaskMap crowdTaskMap;
	@Autowired
	private CrowdTaskLibMap crowdTaskLibMap;
	@Autowired
	private CrowdTaskBlacklistMap crowdTaskBlacklistMap;
	
	@Autowired
	private CrowdTaskProcess crowdTaskProcess;
	
	@Autowired
	private ILockService lockService;
	@Autowired
	private LockProcess lockProcess;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 获取用户任务详情
	 * @param utId
	 * @exception DataException
	 */
	public UserCrowdTaskDetailBean getUserTask(String utId){
		//获取并校验用户任务
		CmcCtUsertask utask = userCrowdTaskService.getUserTask(utId);
		if(utask==null){
			throw new DataException("用户任务不存在");
		}
		CmcCtTask task = crowdTaskMap.getTask(utask.getTaskid());
		
		//任务基本信息
		UserCrowdTaskDetailBean result = new UserCrowdTaskDetailBean();
		result.setUtId(utask.getUtid());
		result.setUid(utask.getUserid());
		result.setTaskId(task.getTaskid());
		result.setTitle(task.getTitle());
		result.setPic2(task.getPic2());
		result.setRewardType(task.getRewardtype());
		result.setUnitName(task.getUnitname());
		result.setSubjectNum(task.getSubjectnum());
		result.setSubjectAssign(task.getSubjectassign());
		result.setStartTime(task.getStarttime().getTime());
		result.setEndTime(task.getEndtime().getTime());
		result.setMaxNum(task.getMaxnum());
		result.setApplyNum(task.getApplynum());
		result.setFinishNum(task.getFinishnum());
		result.setDescription(task.getDescription());
		result.setAutoVerify(SystemConstants.STATUS_ON.equals(task.getAutoverify()));
		result.setAutoQc(SystemConstants.STATUS_ON.equals(task.getAutoqc()));
		
		result.setFinishSubjectNum(utask.getFinishsubjectnum());
		result.setUtStatus(utask.getStatus());
		result.setApplyTime(utask.getApplytime().getTime());
		result.setCancelTime(DateUtil.getTime(utask.getCanceltime()));
		result.setCancelReason(utask.getCancelreason());
		result.setUtFinishTime(DateUtil.getTime(utask.getFinishtime()));
		result.setTaskReward(utask.getTaskreward());
		result.setTaskRewardSettle(task.getTaskrewardsettle());
		result.setUtReward(utask.getUtreward());
		result.setUtRewardTime(DateUtil.getTime(utask.getUtrewardtime()));
		
		result.setVerifyUid(utask.getVerifyuserid());
		result.setVerifyStatus(utask.getVerifystatus());
		result.setVerifyPass(utask.getVerifypass());
		result.setRefuseReason(utask.getRefusereason());
		
		//已完成用户列表
		List<Long> userIds = userCrowdTaskService.findFinishUserId(task.getTaskid(), 10);
		result.setFinishUsers(UserMap.findUserBean(userIds));

		//前置任务题目列表
		if(utask.getPretaskid()!=null){
			result.setPreUserSubjects(findUserSubject(utId, utask.getPretaskid(), null, null));
		}
		
		return result;
	}
	
	/**
	 * 获取用户任务数
	 * @param userId
	 * @return
	 */
	public UserCrowdTaskCountBean getCount(Long userId){
		UserCrowdTaskCountBean result = new UserCrowdTaskCountBean();
		
		result.setIngCount(userCrowdTaskService.getCount(userId, DictConstant.DICT_USERCROWDTASK_STATUSES_ING));
		
		return result;
	}
	
	/**
	 * 获取用户任务新题目列表
	 * @param utId
	 * @param userId
	 * @param pageSize
	 * @param lastSubjectId
	 * @return
	 */
	public List<CrowdSubjectBean> findNewSubject(String utId, Long userId, int pageSize, Long lastSubjectId){
		CmcCtUsertask utask = userCrowdTaskService.getUserTaskJoin(utId);
		if(utask==null || !utask.getUserid().equals(userId)){
			throw new ParamException("用户任务不存在");
		}
		
		CmcCtTask task = utask.getCmcCtTask();
		if(!DictConstant.DICT_USERCROWDTASK_STATUS_ANSWERING.equals(utask.getStatus())
				|| !DictConstant.DICT_CROWDTASK_SUBJECTASSIGNMETHOD_USER.equals(task.getSubjectassign())){
			throw new ParamException("不能获取新的题目");
		}
		
		Long maxSubjectId = userCrowdTaskService.getMaxSubjectId(utId);
		if(lastSubjectId!=null 
				&& (maxSubjectId==null || lastSubjectId>maxSubjectId)){
			maxSubjectId = lastSubjectId;
		}
		
		List<CmcCtSubject> subjects = crowdTaskLibService.findSubject(task.getLibid(), maxSubjectId, pageSize);
		
		//选项
		if(!subjects.isEmpty()){
			Map<Long, CmcCtSubject> subjectMap = new HashMap<Long, CmcCtSubject>();
			for (CmcCtSubject subject : subjects) {
				subjectMap.put(subject.getSubjectid(), subject);
			}
			List<CmcCtOption> options = crowdTaskLibService.findOptionBySubjectId(new ArrayList<Long>(subjectMap.keySet()));
			for (CmcCtOption option : options) {
				subjectMap.get(option.getSubjectid()).getOptions().add(option);
			}
		}
		
		return BeanConvertUtil.toCrowdSubjectBean(subjects, task);
	}
	
	/**
	 * 审校题目列表
	 * @param utId
	 * @param qcId
	 * @return
	 * @exception ParamException
	 */
	public List<UserCrowdSubjectBean> findUserSubject4Qc(String utId, Long qcId){
		CmcCtUsertask utask = userCrowdTaskService.getUserTaskJoin(utId);
		if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_SUBJECT.equals(utask.getCmcCtTask().getTaskrewardsettle())){
			CmcCtQc qc = userCrowdTaskQcService.getQc(qcId);
			if(DictConstant.DICT_USERCROWDTASK_QCSTATUSES_ING.contains(qc.getQcstatus())){
				return findUserSubject(utId, null, null, DictConstant.DICT_USERCROWDTASK_QCSTATUSES_ING);
			}else{
				return findUserSubject(utId, null, qcId, null);
			}
			
		}else{
			return findUserSubject(utId);
		}
	}
	
	/**
	 * 获取用户任务题目列表
	 * @param utId
	 * @return
	 * @exception ParamException
	 */
	public List<UserCrowdSubjectBean> findUserSubject(String utId){
		return findUserSubject(utId, null, null, null);
	}
	
	/**
	 * 获取用户任务题目列表
	 * @param utId
	 * @param taskId
	 * @return
	 * @exception ParamException
	 */
	public List<UserCrowdSubjectBean> findUserSubject(String utId, Long taskId, Long qcId, List<Integer> qcStatuses){
		CmcCtUsertask utask = userCrowdTaskService.getUserTask(utId);
		if(utask==null){
			throw new ParamException("用户任务不存在");
		}
		if(taskId==null){
			taskId = utask.getTaskid();
		}
		CmcCtTask task = crowdTaskMap.getTask(taskId);

		//用于处理自动审核、自动审校标识
		CmcCtTask oriTask = task;
		if(DictConstant.DICT_CROWDTASK_TASKTYPE_PRETASK.equals(task.getTasktype())){
			oriTask = crowdTaskMap.getTask(utask.getTaskid());
		}
		
		List<UserCrowdSubjectBean> result = new ArrayList<UserCrowdSubjectBean>();
		Map<Long, UserCrowdOptionBean> userOptionMap = new HashMap<Long, UserCrowdOptionBean>();
		Map<Long, UserCrowdSubjectBean> userSubjectMap = new HashMap<Long, UserCrowdSubjectBean>();		
		UserCrowdSubjectBean userSubjectBean = null;
		UserCrowdAnswerBean userAnswerBean = null;
		UserCrowdOptionBean userOptionBean = null;
		
		//题目
		List<CmcCtUsersubject> userSubjects = userCrowdTaskService.findUserSubjectJoin(utId, taskId, qcId, qcStatuses);
		if(userSubjects.isEmpty()) return result;
		
		for (CmcCtUsersubject userSubject : userSubjects) {
			//题目
			userSubjectBean = new UserCrowdSubjectBean();
			userSubjectBean.setSubjectId(userSubject.getSubjectid());
			userSubjectBean.setSubjectCat(userSubject.getCmcCtSubject().getSubjectcat());
			userSubjectBean.setSubjectType(userSubject.getCmcCtSubject().getSubjecttype());
			userSubjectBean.setPassScore(userSubject.getCmcCtSubject().getPassscore());
			userSubjectBean.setSubjectReward(userSubject.getSubjectreward());
			userSubjectBean.setRewardType(task.getRewardtype());
			userSubjectBean.setUnitName(task.getUnitname());
			userSubjectBean.setFrom(userSubject.getCmcCtSubject().getFromlang());
			userSubjectBean.setFromLangName(CrowdTaskUtil.getLangName(userSubject.getCmcCtSubject().getFromlang()));
			userSubjectBean.setTo(userSubject.getCmcCtSubject().getTolang());
			userSubjectBean.setContentType(userSubject.getCmcCtSubject().getContenttype());
			userSubjectBean.setContent(userSubject.getCmcCtSubject().getContent());
			userSubjectBean.setAudio(userSubject.getCmcCtSubject().getAudio());
			userSubjectBean.setVideo(userSubject.getCmcCtSubject().getVideo());
			userSubjectBean.setVideoSource(userSubject.getCmcCtSubject().getVideosource());
			userSubjectBean.setPic(userSubject.getCmcCtSubject().getPic());
			userSubjectBean.setRecordTimes(userSubject.getCmcCtSubject().getRecordtimes());
			userSubjectBean.setBgPic(task.getPic3());
			userSubjectBean.setAutoVerify(SystemConstants.STATUS_ON.equals(oriTask.getAutoverify()));
			userSubjectBean.setAutoQc(SystemConstants.STATUS_ON.equals(oriTask.getAutoqc()));
			userSubjectBean.setUserSubjectReward(userSubject.getUsreward());
			userSubjectBean.setSubjectScore(userSubject.getScore());
			userSubjectBean.setAnswerStatus(userSubject.getAnswerstatus());
			if(userSubject.getAnswertime()!=null){
				userSubjectBean.setAnswerTime(userSubject.getAnswertime().getTime());
			}
						
			result.add(userSubjectBean);
			userSubjectMap.put(userSubjectBean.getSubjectId(), userSubjectBean);
		}
		
		List<Long> subjectIds = new ArrayList<Long>(userSubjectMap.keySet());
		
		//选项
		List<CmcCtOption> options = crowdTaskLibService.findOptionBySubjectId(subjectIds);
		for (CmcCtOption option : options) {
			userOptionBean = new UserCrowdOptionBean();
			userOptionBean.setOptionId(option.getOptionid());
			userOptionBean.setOptionTitle(option.getOptiontitle());
			userOptionBean.setMore(SystemConstants.STATUS_ON.equals(option.getWritemore()));
			userSubjectMap.get(option.getSubjectid()).getOptions().add(userOptionBean);		
			userOptionMap.put(option.getOptionid(), userOptionBean);
		}
		
		//答案
		List<CmcCtUseranswer> userAnswers;
		if(qcStatuses!=null && !qcStatuses.isEmpty()){
			userAnswers = userCrowdTaskService.findUserAnswer(utId, taskId, subjectIds, qcId);
		}else{
			userAnswers = userCrowdTaskService.findUserAnswer(utId, taskId, null, qcId);
		}
		for (CmcCtUseranswer userAnswer : userAnswers) {
			userAnswerBean = new UserCrowdAnswerBean();
			userAnswerBean.setUaId(userAnswer.getUaid());
			userAnswerBean.setSubjectId(userAnswer.getSubjectid());
			userAnswerBean.setOptionId(userAnswer.getOptionid());
			userAnswerBean.setMoreContent(userAnswer.getMorecontent());
			userAnswerBean.setVoice(userAnswer.getVoice());
			userAnswerBean.setVoiceDuration(userAnswer.getVoiceduration());
			userAnswerBean.setDst(userAnswer.getDst());
			userAnswerBean.setAnswerScore(userAnswer.getScore());
			userAnswerBean.setPic(userAnswer.getPic());
			
			//题目用户答案
			userSubjectMap.get(userAnswer.getSubjectid()).getAnswers().add(userAnswerBean);
			
			//选项答案
			if(userAnswer.getOptionid()!=null){
				userOptionBean = userOptionMap.get(userAnswer.getOptionid());
				userOptionBean.setUaId(userAnswer.getUaid());
				userOptionBean.setChecked(true);
				userOptionBean.setMoreContent(userAnswer.getMorecontent());
			}
			
		}
		
		return result;
	}
	
	/**
	 * 获取并校验用户任务和任务，带任务信息
	 * 校验用户任务是否存在
	 * 如果userId不为空，校验用户任务的userId是否和指定的userId匹配
	 * @param utId not null
	 * @param userId
	 * @return
	 * @exception DataException
	 */
	public CmcCtUsertask getAndCheckUserTask(String utId, Long userId){
		//获取并校验用户任务
		CmcCtUsertask utask = userCrowdTaskService.getUserTaskJoin(utId);
		if(utask==null){
			throw new DataException("用户任务不存在");
		}
		
		if(userId!=null && !utask.getUserid().equals(userId)){
			throw new DataException("用户任务不存在");
		}
		
		return utask;
	}
	
	/**
	 * 上传前置任务文件
	 * @param bean
	 * @return
	 * @exception DataException
	 */
	public UserCrowdTaskUploadResult uploadPreTask(UserCrowdTaskUploadBean bean){
		//获取并校验用户任务
		crowdTaskProcess.getAndCheckTask(bean.getTaskId());
		
		//校验文件：16k16bit单通道
		AudioBean audioBean = AudioUtil.getAudioInfo(bean.getFile().getPath());
		if(!CrowdTaskUtil.checkAudioFormat(audioBean)){
			throw new DataException("请安装最新版本");
		}		
		
		return uploadFile(bean.getUid(), bean.getTaskId(), null, bean.getSubjectId(), bean.getFile());
	}
	
	/**
	 * 上传任务文件
	 * @param bean
	 * @return
	 * @exception DataException
	 */
	public UserCrowdTaskUploadResult uploadTask(UserCrowdTaskUploadBean bean){
		//获取并校验用户任务
		CmcCtUsertask utask = getAndCheckUserTask(bean.getUtId(), bean.getUid());
		
		return uploadFile(bean.getUid(), utask.getTaskid(), bean.getUtId(), bean.getSubjectId(), bean.getFile());
	}
	
	/**
	 * 上传文件
	 * @param bean
	 * @return
	 * @exception DataException
	 */
	public UserCrowdTaskUploadResult uploadFile(Long userId, Long taskId, String utId, Long subjectId, File file){
		//校验文件类型：音频采集上传音频文件、助力|添加剂采集上传图片
		CmcCtSubject subject = crowdTaskLibService.getSubject(subjectId);
		if(!DictConstant.DICT_CROWDTASK_SUBJECTCAT_AUDIOCOLLECTION.equals(subject.getSubjectcat())
				&& !DictConstant.DICT_CROWDTASK_SUBJECTCAT_HELP.equals(subject.getSubjectcat())
				&& !DictConstant.DICT_CROWDTASK_SUBJECTCAT_ADDITIVE.equals(subject.getSubjectcat())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		String fileType = null;
		if(DictConstant.DICT_CROWDTASK_SUBJECTCAT_AUDIOCOLLECTION.equals(subject.getSubjectcat())){
			fileType = FileTypeUtil.getMultimediaType(file);
		}else if(DictConstant.DICT_CROWDTASK_SUBJECTCAT_HELP.equals(subject.getSubjectcat())
				|| DictConstant.DICT_CROWDTASK_SUBJECTCAT_ADDITIVE.equals(subject.getSubjectcat())){
			fileType = FileTypeUtil.getImageType(file);
		}
		if(fileType==null){
			throw new ParamException("文件类型有误");
		}
		
		//上传
		String dirPath = UserUtil.getCrowdTaskBasePath(userId, taskId, utId);
		String url = FileServiceUtil.saveOrUploadFile(file, dirPath, FilePathUtil.newFileName(fileType));
		if(StringUtil.isBlankOrNull(url)){
			throw new SystemException("文件保存失败");
		}
		
		UserCrowdTaskUploadResult result = new UserCrowdTaskUploadResult();
		result.setUrl(url);
		
		return result;
	}
		
	/**
	 * 报名
	 * @param bean
	 * @exception DataException、ParamException
	 */
	public UserCrowdTaskApplyResult applyTask(UserCrowdTaskApplyBean bean, String serviceType, String platform, String version){
		//校验用户
		//用户是否在任务黑名单中
		boolean blacklist = crowdTaskBlacklistMap.contains(bean.getUid());
		if(blacklist){
			throw new DataException("账户异常");
		}
				
		//校验任务
		CmcCtTask task = crowdTaskProcess.getAndCheckTask(bean.getTaskId());
		if(!task.getLimitUserIds().isEmpty() && !task.getLimitUserIds().contains(bean.getUid())){
			throw new DataException("账户异常");
		}
		if(DictConstant.DICT_CROWDTASK_TASKTYPE_PRETASK.equals(task.getTasktype())){
			throw new ParamException("资格测试任务不能报名");
		}
		if(task.getMaxnum()!=null && task.getApplynum()>=task.getMaxnum()){
			throw new DataException("名额已满");
		}

		//检查资格测试答案
		if(task.getPretaskid()!=null){
			if(bean.getPreAnswers().isEmpty()){
				throw new DataException("资格测试答案为空");
			}
			
			Integer totalScore = CrowdTaskUtil.calTotalScore(bean.getPreAnswers());
			if(totalScore!=null && task.getVerifypassscore()!=null && totalScore<task.getVerifypassscore()){
				throw new ParamException("资格测试得分未达标");
			}
			
			Map<Long, CmcCtSubject> preSubjectMap = new HashMap<Long, CmcCtSubject>();
			for (UserCrowdAnswerBean preAnswer : bean.getPreAnswers()) {
				preSubjectMap.put(preAnswer.getSubjectId(), crowdTaskLibMap.getSubject(preAnswer.getSubjectId()));
			}			
			if(!CrowdTaskUtil.checkUserAnswers(bean.getPreAnswers(), preSubjectMap)){
				throw new ParamException("资格测试答案数据有误");
			}
		}
		
		//是否已经报过名
		CmcCtUsertask utask = userCrowdTaskService.getUserTask(bean.getUid(), bean.getTaskId());
		if(utask!=null){
			throw new DataException("您已参加过该任务");
		}
		if(StringUtil.notBlankAndNull(task.getGroupcode()) 
				&& userCrowdTaskService.isPartInGroupTask(bean.getUid(), task.getGroupcode())){
			throw new DataException("您已参加过同类任务");
		}
		
		//报名
		utask = userCrowdTaskService.applyTask(bean, task, serviceType, platform, version);
		
		//自动审核，将审核结果通知用户；不自动审核，通知审核人员审核
		UserCrowdTaskVerifyNotifyThrd.notify(utask.getServicetype(), utask.getUserid(), utask.getUtid(), utask.getStatus(), task.getTitle(), utask.getRefusereason());
				
		//计数
		CrowdTaskCountProcess.changeApplyCount(bean.getTaskId());
		if(DictConstant.DICT_USERCROWDTASK_STATUS_ACCEPTAPPLY.equals(utask.getStatus())){
			CrowdTaskCountProcess.changeIngCount(bean.getTaskId());
		}
		
		//经纬度获取国家城市
		if(StringUtil.notBlankAndNull(bean.getLon()) && StringUtil.notBlankAndNull(bean.getLat())){
			UserCrowdTaskRegeocodeThrd.start(utask.getUtid());
		}
		
		//设备入库
		/*
		if(device==null 
				&& StringUtil.notBlankAndNull(bean.getUuid())
				&& DictConstants.DICT_PLATFORM_IOS.equals(platform)){
			crowdTaskDeviceService.addDevice(bean.getUuid(), bean.getUid());
		}
		*/
		
		//返回
		UserCrowdTaskApplyResult result = new UserCrowdTaskApplyResult();
		result.setUtId(utask.getUtid());
		return result;
	}
	
	
	/**
	 * 根据经纬度获取并保存国家城市
	 * @param utId
	 */
	public void regeocodeTask(String utId){
		CmcCtUsertask ut = userCrowdTaskService.getUserTask(utId);
		if(StringUtil.notBlankAndNull(ut.getLon()) && StringUtil.notBlankAndNull(ut.getLat())){
			GeographyBean geography = GeographyUtil.regeocode(ut.getLon(), ut.getLat());
			if(geography!=null){
				userCrowdTaskService.updateCountryAndCity(utId, geography.getCountryName(), geography.getCityName());
			}
		}
	}
	
	/**
	 * 用户取消任务
	 * @param userId
	 * @param utId
	 * @param reason
	 * @exception DataException
	 */
	public void cancelTask(Long userId, String utId, String reason){
		//获取并校验用户任务
		CmcCtUsertask utask = userCrowdTaskService.getUserTask(utId);
		if(utask==null || !utask.getUserid().equals(userId)){
			throw new DataException("用户任务不存在");
		}		
				
		if(!CrowdTaskUtil.canCancel(utask.getStatus())){
			throw new DataException("用户任务不能取消");
		}
		
		//取消任务
		userCrowdTaskService.saveCancelTask(utId, reason, String.valueOf(userId), SystemConstants.OPERATOR_USER);
		
		//计数
		CrowdTaskCountProcess.changeApplyCount(utask.getTaskid());
		if(DictConstant.DICT_USERCROWDTASK_STATUSES_ING.contains(utask.getStatus())){
			CrowdTaskCountProcess.changeIngCount(utask.getTaskid());
		}
	}
	
	/**
	 * 操作员取消指定用户所有未完成任务
	 * @param userId
	 * @param reason
	 * @param oper
	 * @return
	 */
	public int cancelTaskByUserId(Long userId, String reason, Operator oper){
		List<CmcCtUsertask> userTasks = userCrowdTaskService.findUserTask(userId, DictConstant.DICT_USERCROWDTASK_STATUSES_ING);
		if(userTasks.isEmpty()) return 0;
		
		//取消用户任务
		int count = userCrowdTaskService.cancelByUserId(userId, reason, oper.getOperCd(), oper.getOperName());
		
		//更新计数
		for (CmcCtUsertask utask : userTasks) {
			CrowdTaskCountProcess.changeApplyCount(utask.getTaskid());
			CrowdTaskCountProcess.changeIngCount(utask.getTaskid());
		}
		
		return count;
	}
	
	/**
	 * 操作员取消用户任务
	 * @param utId
	 * @param reason
	 * @param oper
	 */
	public void cancelTaskByUtId(String utId, String reason, Operator oper){
		//获取并校验用户任务
		CmcCtUsertask utask = userCrowdTaskService.getUserTask(utId);
		if(!CrowdTaskUtil.canCancel(utask.getStatus())){
			throw new DataException("用户任务不能取消");
		}
				
		//取消任务
		userCrowdTaskService.saveCancelTask(utId, reason, oper.getOperCd(), oper.getOperName());
		
		//计数
		CrowdTaskCountProcess.changeApplyCount(utask.getTaskid());
		if(DictConstant.DICT_USERCROWDTASK_STATUSES_ING.contains(utask.getStatus())){
			CrowdTaskCountProcess.changeIngCount(utask.getTaskid());
		}
	}
	
	/**
	 * 操作员批量取消指定任务未完成的用户任务
	 * @param taskId not null
	 * @param reason 取消原因
	 * @param daysAgo 多少天之前申请
	 * @param operCd 操作人账号
	 * @param operName 操作人姓名
	 */
	public int cancelTaskByTaskId(Long taskId, String reason, Integer daysAgo, Operator oper){
		//取消用户任务
		int count = userCrowdTaskService.cancelByTaskId(taskId, reason, daysAgo, oper.getOperCd(), oper.getOperName());
		
		//更新计数
		crowdTaskService.updateApplyCount(taskId);
		crowdTaskService.updateIngCount(taskId);
		
		return count;
	}
	
	/**
	 * 取消过期的用户众包任务
	 */
	public void cancelOvertime(){
		String reason = "超时未完成";
		int daysAgo = 2;
		Operator oper = new Operator(SystemConstants.OPERATOR_DEFAULT, SystemConstants.OPERATOR_DEFAULT, null);
		
		List<CmcCtTask> list = crowdTaskService.findTask4Cancel();
		StringBuilder sbLog = null;
		int count;
		for (CmcCtTask task : list) {
			count = cancelTaskByTaskId(task.getTaskid(), reason, daysAgo, oper);
			sbLog = new StringBuilder()
					.append("取消任务【")
					.append(task.getTaskid()).append(",").append(task.getTitle())
					.append("】过期未完成用户任务数：").append(count);
			logger.info(sbLog);
		}
	}
	
	/**
	 * 开始任务
	 * @param userId
	 * @param utId
	 * @exception DataException
	 */
	public void startTask(Long userId, String utId){
		//获取并校验用户任务
		CmcCtUsertask utask = getAndCheckUserTask(utId, userId);
		if(!DictConstant.DICT_USERCROWDTASK_STATUS_ACCEPTAPPLY.equals(utask.getStatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}

		//入库
		utask.setStarttime(new Date());
		utask.setStatus(DictConstant.DICT_USERCROWDTASK_STATUS_ANSWERING);
		userCrowdTaskService.saveOrUpdateTask(utask);
		
		//计数
		CrowdTaskCountProcess.changeIngCount(utask.getTaskid());
	}
	
	/**
	 * 答题
	 * @param bean
	 */
	public void answerSubject(UserCrowdSubjectAnswerBean bean){
		//获取并校验用户任务
		CmcCtUsertask utask = getAndCheckUserTask(bean.getUtId(), bean.getUid());
		
		//校验用户任务，前置任务不校验
		CmcCtTask task = utask.getCmcCtTask();
		if(!DictConstant.DICT_CROWDTASK_TASKTYPE_PRETASK.equals(task.getTasktype())){
			if(!DictConstant.DICT_USERCROWDTASK_STATUS_ANSWERING.equals(utask.getStatus())){
				throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
			}
		}
		
		//从答案中抽取题目编号
		Set<Long> subjectIds = new HashSet<Long>();
		for (UserCrowdAnswerBean answerBean : bean.getAnswers()) {
			subjectIds.add(answerBean.getSubjectId());
		}
		List<Long> subjectIdList = new ArrayList<Long>(subjectIds);

		//一题一结的任务，答过的题目不能再答
		if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_SUBJECT.equals(task.getTaskrewardsettle())){
			List<Long> userSubjectIds = userCrowdTaskService.findUserSubjectId(utask.getUtid(), utask.getTaskid(), subjectIdList, SystemConstants.STATUS_ON);
			if(!userSubjectIds.isEmpty()){
				throw new DataException("一题一结的任务，答过的题目不能再答");
			}
		}
		
		//检查答案
		Map<Long, CmcCtSubject> subjectMap = new HashMap<Long, CmcCtSubject>();
		List<CmcCtSubject> subjects = crowdTaskLibService.findSubjectBySubjectIdJoin(subjectIdList);
		for (CmcCtSubject subject : subjects) {
			subjectMap.put(subject.getSubjectid(), subject);
		}
		if(!CrowdTaskUtil.checkUserAnswers(bean.getAnswers(), subjectMap)){
			throw new ParamException("答案数据有误");
		}		
		
		//答案入库
		userCrowdTaskService.answerSubject(bean.getUtId(), utask.getTaskid(), bean.getAnswers());
		
		//通知审校人
		List<CmcCtQc> qcs = userCrowdTaskQcService.findQc(bean.getUtId(), DictConstant.DICT_USERCROWDTASK_QCSTATUS_WAITING);
		if(!qcs.isEmpty()){
			UserCrowdTaskQcNotifyThrd.notify(utask.getServicetype(), utask.getUtid(), utask.getCmcCtTask().getTitle());
		}
	}
	
	/**
	 * 提醒用户完成任务 
	 */
	public void remindToFinish(){
		List<CmcCtUsertask> userTasks = userCrowdTaskService.findUserTask4RemindToFinish();
		if(userTasks.isEmpty()) return;
		
		for (CmcCtUsertask utask : userTasks) {
			UserCrowdTaskVerifyNotifyThrd.notify(utask.getServicetype(), utask.getUserid(), utask.getUtid(), utask.getStatus(), utask.getCmcCtTask().getTitle(), null);
		}
	}
	
	/**
	 * 完成任务
	 * @param userId
	 * @param utId
	 * @exception DataException
	 */
	public void finishTask(Long userId, String utId){
		//获取并校验用户任务
		CmcCtUsertask utask = getAndCheckUserTask(utId, userId);
		if(!DictConstant.DICT_USERCROWDTASK_STATUS_ANSWERING.equals(utask.getStatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		//检查是否所有题目都已完成
		if(utask.getCmcCtTask().getSubjectnum()!=null 
				&& utask.getFinishsubjectnum()<utask.getCmcCtTask().getSubjectnum()){
			throw new DataException("有题目未完成");
		}
		
		//入库
		utask = userCrowdTaskService.saveFinishTask(utId);
		
		//更新计数
		CrowdTaskCountProcess.changeFinishCount(utask.getTaskid());
		CrowdTaskCountProcess.changeIngCount(utask.getTaskid());
		
		//通知审校人
		List<CmcCtQc> qcs = userCrowdTaskQcService.findQc(utId, DictConstant.DICT_USERCROWDTASK_QCSTATUS_WAITING);
		if(!qcs.isEmpty()){
			UserCrowdTaskQcNotifyThrd.notify(utask.getServicetype(), utask.getUtid(), utask.getCmcCtTask().getTitle());
		}
	}
	
	/**
	 * 强制完结任务，用于一题一结任务
	 * @param utId
	 * @param oper
	 */
	public void forceFinishTask(String utId, Operator oper){
		//完成任务
		CmcCtUsertask utask = userCrowdTaskService.getUserTaskJoin(utId);
		utask.setFinishtime(new Date());
		if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_SUBJECT.equals(utask.getCmcCtTask().getTaskrewardsettle())){
			utask.setStatus(DictConstant.DICT_USERCROWDTASK_STATUS_FINISH);
		}else{
			utask.setStatus(DictConstant.DICT_USERCROWDTASK_STATUS_SUBMIT);
		}		
		userCrowdTaskService.saveOrUpdateTask(utask);
		
		//操作日志
		OperLogUtil.saveOperLog(CmcCtUsertask.class, utId, oper, "强制完结任务");
		
		
		//更新计数
		CrowdTaskCountProcess.changeFinishCount(utask.getTaskid());
		CrowdTaskCountProcess.changeIngCount(utask.getTaskid());
	}
	
	/**
	 * 开始报名审核
	 * @param bean
	 * @exception DataException
	 */
	public void startVerify(String utId, Long verifyUserId){
		Long lockId = lockProcess.addLock4Unique(DictConstant.DICT_LOCKTYPE_USERCROWDTASK_VEIRFY, utId);
		if(lockId==null){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		try {
			CmcCtUsertask utask = userCrowdTaskService.getUserTask(utId);
			if(utask==null){
				throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
			}
			if(!DictConstant.DICT_USERCROWDTASK_STATUS_APPLY.equals(utask.getStatus())){
				throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
			}
			userCrowdTaskService.saveStartVerify(utask, verifyUserId);
		} finally{
			lockService.deleteLock(lockId);
		}
	}
	
	/**
	 * 完成报名审核
	 * @param bean
	 * @exception DataException
	 */
	public void finishVerify(UserCrowdTaskVerifyBean bean){
		CmcCtUsertask utask = userCrowdTaskService.getUserTaskJoin(bean.getUtId());
		CmcCtTask task = utask.getCmcCtTask();
		
		if(!DictConstant.DICT_USERCROWDTASK_VERIFYSTATUS_PROCESSING.equals(utask.getVerifystatus())
				|| !bean.getVerifyUid().equals(utask.getVerifyuserid())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		if(SystemConstants.STATUS_ON.equals(bean.getPass())){
			//审核通过，由于要分配题目，所以加锁
			Long lockId = lockProcess.addLock4Unique(DictConstant.DICT_LOCKTYPE_USERCROWDTASK_PASSVEIRFY, String.valueOf(utask.getTaskid()));
			if(lockId==null){
				throw new DataException("请稍后重试");
			}
			
			try {
				userCrowdTaskService.savePassApply(utask);
			}finally{
				lockService.deleteLock(lockId);
			}
			CrowdTaskCountProcess.changeIngCount(utask.getTaskid());
		}else{
			userCrowdTaskService.saveRejectApply(utask, bean.getReason());
			//报名失败释放库存
			CrowdTaskCountProcess.changeApplyCount(utask.getTaskid());
		}
		
		//通知用户
		UserCrowdTaskVerifyNotifyThrd.notify(utask.getServicetype(), utask.getUserid(), utask.getUtid(), utask.getStatus(), task.getTitle(), null);
	}
	
	/**
	 * 开始审校
	 * @param bean
	 * @exception DataException
	 */
	public void startQc(String utId, Long qcUserId){
		Long lockId = lockProcess.addLock4Unique(DictConstant.DICT_LOCKTYPE_USERCROWDTASK_QC, utId);
		if(lockId==null){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		try {
			CmcCtQc qc = userCrowdTaskQcService.getQc(utId, DictConstant.DICT_USERCROWDTASK_QCSTATUS_WAITING);
			if(qc==null){
				throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
			}
			
			qc.setQcuserid(qcUserId);
			qc.setQcstarttime(new Date());
			qc.setQcstatus(DictConstant.DICT_USERCROWDTASK_QCSTATUS_PROCESSING);
			userCrowdTaskQcService.updateQc(qc);
		} finally{
			lockService.deleteLock(lockId);
		}
	}
	
	/**
	 * 题目审校
	 * @param bean
	 * @exception DataException,ParamException
	 */
	public void qcSubject(UserCrowdSubjectQcBean bean){
		//校验
		CmcCtQc qc = userCrowdTaskQcService.getQc(bean.getUtId(), DictConstant.DICT_USERCROWDTASK_QCSTATUS_PROCESSING);
		if(qc==null || !qc.getQcuserid().equals(bean.getQcUid())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		//检查审校结果
		CmcCtSubject subject = crowdTaskLibService.getSubject(bean.getSubjectId());
		if(!CrowdTaskUtil.checkQc(subject, bean.getSubjectScore(), bean.getAnswerQcs())){
			throw new ParamException("审校数据有误");
		}		

		//按题目结算，已经审过的题目不能再审
		CmcCtTask task = crowdTaskMap.getTask(qc.getTaskid());
		if(DictConstant.DICT_CROWDTASK_SETTLEMETHOD_SUBJECT.equals(task.getTaskrewardsettle())){
			CmcCtUsersubject userSubject = userCrowdTaskService.getUserSubject(bean.getUtId(), bean.getSubjectId());						
			if(DictConstant.DICT_USERCROWDTASK_QCSTATUS_DEALT.equals(userSubject.getQcstatus())){
				throw new DataException("一题一结的任务，已经审过的题目不能再审");
			}
		}
		
		//入库
		userCrowdTaskService.saveSubjectQc(bean.getUtId(), qc.getQcid(), bean.getSubjectId(), bean.getSubjectScore(), bean.getAnswerQcs());
	} 
	
	/**
	 * 完成审校
	 * @param bean
	 * @exception DataException
	 */
	public void finishQc(String utId, Long qcUserId){
		userCrowdTaskService.finishQc(utId, qcUserId);
	}
	
	/**
	 * 获取审核任务数
	 * @param verifyUserId
	 * @return
	 */
	public UserCrowdTaskDealCountBean getVerifyCount(String serviceType, Long verifyUserId){
		UserCrowdTaskDealCountBean result = new UserCrowdTaskDealCountBean();
		result.setWaitingCount(userCrowdTaskService.getVerifyCount(serviceType, DictConstant.DICT_USERCROWDTASK_VERIFYSTATUS_WAITING, null));
		if(verifyUserId!=null){
			result.setUserProcessingCount(userCrowdTaskService.getVerifyCount(serviceType, DictConstant.DICT_USERCROWDTASK_VERIFYSTATUS_PROCESSING, verifyUserId));
		}
		return result;
	}
	
	/**
	 * 获取审校任务数
	 * @param qcUserId
	 * @return
	 */
	public UserCrowdTaskDealCountBean getQcCount(String serviceType, Long qcUserId){
		UserCrowdTaskDealCountBean result = new UserCrowdTaskDealCountBean();
		result.setWaitingCount(userCrowdTaskService.getQcCount(serviceType, DictConstant.DICT_USERCROWDTASK_QCSTATUS_WAITING, null));
		if(qcUserId!=null){
			result.setUserProcessingCount(userCrowdTaskService.getQcCount(serviceType, DictConstant.DICT_USERCROWDTASK_QCSTATUS_PROCESSING, qcUserId));
		}
		return result;
	}
}
