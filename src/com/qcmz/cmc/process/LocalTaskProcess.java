package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.bdc.ws.provide.cache.CityMap;
import com.qcmz.cmc.cache.LocalCompanyMap;
import com.qcmz.cmc.cache.LocalSourceMap;
import com.qcmz.cmc.cache.RewardActivityMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcLtSub;
import com.qcmz.cmc.entity.CmcLtTask;
import com.qcmz.cmc.service.ILocalTaskService;
import com.qcmz.cmc.service.ILocalTaskSubService;
import com.qcmz.cmc.thrd.LocalTaskStatusNotifyThrd;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.cmc.util.LocalTaskUtil;
import com.qcmz.cmc.util.SystemUtil;
import com.qcmz.cmc.vo.LocalTaskPageSearchBean;
import com.qcmz.cmc.vo.LocalTastSearchBean;
import com.qcmz.cmc.ws.provide.vo.LocalTaskBean;
import com.qcmz.cmc.ws.provide.vo.LocalTaskListBean;
import com.qcmz.cmc.ws.provide.vo.LocalTaskQueryBean;
import com.qcmz.cmc.ws.provide.vo.LocalTaskUserSubBean;
import com.qcmz.cmc.ws.provide.vo.LocalTaskUserSubQueryBean;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.umc.ws.provide.locator.UserMsgWS;
import com.qcmz.umc.ws.provide.vo.UserMsgAddBean;

@Component
public class LocalTaskProcess {
	@Autowired
	private ILocalTaskService localTaskService;
	@Autowired
	private ILocalTaskSubService localTaskSubService;
	@Autowired
	private RewardActivityMap rewardActivityMap;
	@Autowired
	private LocalCompanyMap localCompanyMap;
	@Autowired
	private LocalSourceMap localSourceMap;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 分页获取有效的同城任务列表
	 * @param title
	 * @param cityName
	 * @param pageSize
	 * @param moreBaseId
	 * @return
	 */
	public List<LocalTaskListBean> findTask(LocalTaskQueryBean query, int pageSize, boolean encrypt){
		List<LocalTaskListBean> result = new ArrayList<LocalTaskListBean>();
		LocalTaskListBean bean = null;
		
		//城市处理
		Long cityId = query.getCityId();
		if(cityId==null && StringUtil.notBlankAndNull(query.getCityName())){
			cityId = SystemUtil.getCityIdOfChina(query.getCityName());
			if(cityId==null) return result;
		}
		
		//TODO:经纬度处理周边
		
		//报酬薪资处理，默认月薪
		Integer rewardType = query.getRewardType();
		if(query.getReward()!=null && query.getRewardType()==null){
			rewardType = DictConstant.DICT_LOCALTASK_REWARDTYPE_MONTH;
		}
		
		LocalTaskPageSearchBean search = new LocalTaskPageSearchBean();
		search.setCityId(cityId);
		search.setAddress(query.getAddress());
		search.setTitle(query.getTitle());
		search.setReward(query.getReward());
		search.setRewardType(rewardType);
		search.setWrokTimeType(query.getWorkTimeType());
		search.setWorkYear(query.getWorkYear());
		search.setEdu(query.getEdu());
		search.setPageSize(pageSize);
		search.setMoreBaseId(query.getMoreBaseId());

		List<CmcLtTask> tasks = localTaskService.findTask(search);
		for (CmcLtTask task : tasks) {
			bean = new LocalTaskListBean();
			bean.setTaskId(task.getTaskid());
			if(encrypt){
				bean.setTitle(CmcUtil.encrypt(task.getTitle()));
			}else{
				bean.setTitle(task.getTitle());
			}
			bean.setCompanyName(localCompanyMap.getCompanyName(task.getCompanyid()));
			bean.setWorkTimeType(task.getWorktimetype());
			bean.setRewardType(task.getRewardtype());
			bean.setReward(task.getReward());
			bean.setCityName(task.getCityname());
			bean.setAddress(task.getAddress());
			bean.setExp(task.getExp());
			bean.setEdu(task.getEdu());
			bean.setPublishTime(task.getPublishtime().getTime());
			bean.setStartTime(bean.getPublishTime());
			bean.setSortId(task.getSortquery());
			bean.setMaxInviteRewardAmount(rewardActivityMap.getMaxRewardAmount(task.getActid()));
			
			result.add(bean);
		}
		
		return result;
	}
	
	/**
	 * 分页获取用户订阅的同城任务列表
	 * @param userId
	 * @param pageSize
	 * @param moreBaseId
	 * @return
	 */
	public List<LocalTaskListBean> findSubTask(LocalTaskUserSubQueryBean subQuery, int pageSize){
		CmcLtSub sub = localTaskSubService.getSubByUserId(subQuery.getUid());
		if(sub==null){
			return new ArrayList<LocalTaskListBean>();
		}
		
		LocalTaskQueryBean query = new LocalTaskQueryBean();
		query.setCityId(sub.getCityid());
		query.setAddress(sub.getAddress());
		query.setLon(sub.getLon());
		query.setLat(sub.getLat());
		query.setTitle(sub.getTitle());
		query.setWorkTimeType(sub.getWorktimetype());
		query.setWorkYear(sub.getWorkyear());
		query.setReward(sub.getReward());
		query.setRewardType(sub.getRewardtype());
		query.setEdu(sub.getEdu());
		query.setMoreBaseId(subQuery.getMoreBaseId());
		
		return findTask(query, pageSize, true);
	}
	
	/**
	 * 获取同城任务信息
	 * @param taskId
	 * @return
	 * @exception DataException
	 */
	public LocalTaskBean getTask(Long taskId, boolean encrypt){
		CmcLtTask entity = localTaskService.getTaskJoin(taskId);
		if(entity==null 
				|| !DictConstant.DICT_LOCALTASK_STATUS_ON.equals(entity.getStatus())){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		
		LocalTaskBean result = new LocalTaskBean();
		result.setTaskId(entity.getTaskid());
		if(encrypt){
			result.setTitle(CmcUtil.encrypt(entity.getTitle()));
		}else{
			result.setTitle(entity.getTitle());
		}
		result.setWorkTimeType(entity.getWorktimetype());
		result.setRewardType(entity.getRewardtype());
		result.setReward(entity.getReward());
		if(entity.getCmcLtCompany()!=null){
			result.setCompanyName(entity.getCmcLtCompany().getCompanyname());
			result.setCompanyLogo(entity.getCmcLtCompany().getLogo());
			result.setCompanyIntroduce(entity.getCmcLtCompany().getIntroduce());
		}
		result.setContact(entity.getContact());
		result.setCityName(entity.getCityname());
		result.setAddress(entity.getAddress());
		result.setExp(entity.getExp());
		result.setEdu(entity.getEdu());
		result.setPeopleNum(entity.getPeoplenum());
		result.setJob(entity.getJob());
		result.setJobRequire(entity.getJobrequire());
		if(entity.getSourceid()!=null){
			result.setSourceName(localSourceMap.getSourceName(entity.getSourceid()));
		}
		result.setLink(entity.getLink());
		result.setPublishTime(entity.getPublishtime().getTime());
		result.setStartTime(result.getPublishTime());
		result.setMaxInviteRewardAmount(rewardActivityMap.getMaxRewardAmount(entity.getActid()));
		
		/*
		if(entity.getCmcLtPics()!=null && !entity.getCmcLtPics().isEmpty()){
			for (CmcLtPic pic : entity.getCmcLtPics()) {
				result.getPics().add(new LocalTaskPicBean(pic.getPicurl()));
			}
		}*/
		
		return result;
	}
	
	/**
	 * 通过审核
	 * @param taskId
	 */
	public void passVerify(Long taskId){
		CmcLtTask task = localTaskService.getTask(taskId);
		
		task.setStatus(DictConstant.DICT_LOCALTASK_STATUS_ON);
		task.setVerifyresult("");
		task.setPublishtime(new Date());
		localTaskService.update(task);
		
		//通知用户
		LocalTaskStatusNotifyThrd.notifyStatus(task.getUserid(), task.getTaskid()
				, task.getTitle(), DictConstant.DICT_LOCALTASK_STATUS_ON);
	}
	
	/**
	 * 驳回审核
	 * @param taskId
	 * @param reason
	 */
	public void rejectVerify(Long taskId, String reason){
		CmcLtTask task = localTaskService.getTask(taskId);
		
		task.setStatus(DictConstant.DICT_LOCALTASK_STATUS_VERIFYREFUSE);
		task.setVerifyresult(reason);
		localTaskService.update(task);
		
		//通知用户
		LocalTaskStatusNotifyThrd.notifyStatus(task.getUserid(), task.getTaskid()
				, task.getTitle(), DictConstant.DICT_LOCALTASK_STATUS_VERIFYREFUSE);
	}
	
	/**
	 * 订阅
	 * @param bean
	 * @exception DataException
	 */
	public void sub(LocalTaskUserSubBean bean){
		//城市处理
		Long cityId = bean.getCityId();
		String cityName = bean.getCityName();
		if(cityId!=null && StringUtil.isBlankOrNull(cityName)){
			cityName = CityMap.getName(cityId);
		}else if(cityId==null && StringUtil.notBlankAndNull(bean.getCityName())){
			cityId = SystemUtil.getCityIdOfChina(bean.getCityName());
			if(cityId==null) throw new DataException("城市有误");
		}
		
		//TODO:经纬度处理
		
		//报酬类型处理
		Integer rewardType = bean.getRewardType();
		if(rewardType==null 
				&& bean.getReward()!=null && bean.getReward()>0){
			rewardType = DictConstant.DICT_LOCALTASK_REWARDTYPE_MONTH;
		}
		
		Date now = new Date();
		
		CmcLtSub sub = localTaskSubService.getSubByUserId(bean.getUid());
		if(sub==null){
			sub = new CmcLtSub();
			sub.setUserid(bean.getUid());
			sub.setCreatetime(now);
		}
		
		sub.setCityid(cityId);
		sub.setCityname(cityName);
		sub.setAddress(bean.getAddress());
		sub.setLon(bean.getLon());
		sub.setLat(bean.getLat());
		sub.setTitle(bean.getTitle());
		sub.setWorktimetype(bean.getWorkTimeType());
		sub.setWorkyear(bean.getWorkYear());
		sub.setReward(bean.getReward());
		sub.setRewardtype(rewardType);
		sub.setEdu(bean.getEdu());
		sub.setContent(bean.getContent());
		sub.setUpdatetime(now);
		
		localTaskSubService.saveOrUpdate(sub);
	}
	
	/**
	 * 取消订阅
	 * @param userId
	 */
	public void cancelSub(Long userId){
		localTaskSubService.deleteSubByUserId(userId);
	}
	
	/**
	 * 获取用户的订阅信息
	 * @param userId
	 * @return
	 * @exception DataException
	 */
	public LocalTaskUserSubBean getUserSub(Long userId){
		CmcLtSub sub = localTaskSubService.getSubByUserId(userId);
		if(sub==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		
		LocalTaskUserSubBean result = new LocalTaskUserSubBean();
		result.setUid(sub.getUserid());
		result.setCityId(sub.getCityid());
		result.setCityName(sub.getCityname());
		result.setAddress(sub.getAddress());
		result.setLon(sub.getLon());
		result.setLat(sub.getLat());
		result.setTitle(sub.getTitle());
		result.setWorkTimeType(sub.getWorktimetype());
		result.setWorkYear(sub.getWorkyear());
		result.setReward(sub.getReward());
		result.setRewardType(sub.getRewardtype());
		result.setEdu(sub.getEdu());
		result.setContent(sub.getContent());
		
		return result;
	}

	
	/**
	 * 订阅通知
	 */
	public void subNotify(){
		logger.info("开始订阅通知");
		List<CmcLtSub> subs = null;
		Long lastId = null;
		Long taskCount = null;
		int notifyCount = 0;
		LocalTastSearchBean search = null;
		List<Long> toUserIds = new ArrayList<Long>();
		List<Long> subIds = new ArrayList<Long>();
		UserMsgAddBean bean = null;
		boolean addMsg = false;
		int pageSize = 10;
		do{
			toUserIds.clear();
			subIds.clear();

			subs = localTaskSubService.findSub4Notify(pageSize, lastId);
			if(subs.isEmpty()) break;
			for (CmcLtSub sub : subs) {
				lastId = sub.getSubid();
				search = new LocalTastSearchBean();
				search.setTitle(sub.getTitle());
				search.setWrokTimeType(sub.getWorktimetype());
				search.setCityId(sub.getCityid());
				search.setAddress(sub.getAddress());
				search.setWorkYear(sub.getWorkyear());
				search.setReward(sub.getReward());
				search.setRewardType(sub.getRewardtype());
				search.setEdu(sub.getEdu());
				search.setCreateTimeGreaterThan(sub.getNotifytime());
				taskCount = localTaskService.getTaskCount(search);
				if(taskCount>0){
					toUserIds.add(sub.getUserid());
					subIds.add(sub.getSubid());
					notifyCount++;
				}
			}
			if(!toUserIds.isEmpty()){
				bean = new UserMsgAddBean();
				bean.setMsgType(71L);
				bean.setToUserIds(toUserIds);
				addMsg = UserMsgWS.addMsg(bean);
				if(addMsg){
					localTaskSubService.updateNotifyTime(subIds);
				}
			}
		}while(true);
		logger.info("完成订阅通知："+notifyCount);
	}
	
	//TODO: 
	public void getLonLat(){
		logger.info("开始完善就业精选数据");
		List<CmcLtTask> tasks = null;
		Long lastTaskId = null;
		List<Integer> rewards = null;
		int pageSize = 50;
		int count = 0;
		
		do{
			tasks = localTaskService.findTask(pageSize, lastTaskId);
			if(tasks.isEmpty()) break;
			
			for (CmcLtTask task : tasks) {
				count++;
				lastTaskId = task.getTaskid();
				rewards = LocalTaskUtil.parseReward(task.getReward());
				task.setMinreward(rewards.get(0));
				task.setMaxreward(rewards.get(1));
				task.setRefreward(LocalTaskUtil.calRefReward(task.getMinreward(), task.getMaxreward(), task.getRewardtype()));
				localTaskService.update(task);
			}
			if(count%1000==0){
				logger.info("完善就业精选数据，已处理【"+count+"】lastTaskId【"+lastTaskId+"】");
			}
		}while(true);
		
		logger.info("完成完善就业精选数据，总计"+count);
	}
	
	/**
	 * 完善数据
	 */
	public void refineData(Map<String, String> params){
		logger.info("开始完善就业精选数据");
		List<CmcLtTask> tasks = null;
		Long lastTaskId = null;
		List<Integer> rewards = null;
		int pageSize = 50;
		int count = 0;
		
		do{
			tasks = localTaskService.findTask(pageSize, lastTaskId);
			if(tasks.isEmpty()) break;
			
			for (CmcLtTask task : tasks) {
				count++;
				lastTaskId = task.getTaskid();
				rewards = LocalTaskUtil.parseReward(task.getReward());
				task.setMinreward(rewards.get(0));
				task.setMaxreward(rewards.get(1));
				task.setRefreward(LocalTaskUtil.calRefReward(task.getMinreward(), task.getMaxreward(), task.getRewardtype()));
				localTaskService.update(task);
			}
			if(count%1000==0){
				logger.info("完善就业精选数据，已处理【"+count+"】lastTaskId【"+lastTaskId+"】");
			}
		}while(true);
		
		logger.info("完成完善就业精选数据，总计"+count);
	}
}
