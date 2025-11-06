package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcLtCompany;
import com.qcmz.cmc.entity.CmcLtTask;
import com.qcmz.cmc.service.ILocalCompanyService;
import com.qcmz.cmc.service.ILocalTaskService;
import com.qcmz.cmc.util.LocalTaskUtil;
import com.qcmz.cmc.util.SystemUtil;
import com.qcmz.cmc.ws.provide.vo.LocalCompanyBasicBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobAddBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobAddResult;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobAddressUpdateBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobBasicUpdateBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobDutyUpdateBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobEduUpdateBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobExpUpdateBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobListBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobQueryBean;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobRequireUpdateBean;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.geography.GeographyBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;

@Component
public class LocalTaskZhaopinProcess {
	@Autowired
	private ILocalTaskService localTaskService;
	@Autowired
	private ILocalCompanyService localCompanyService;
	
	/**
	 * 获取岗位列表
	 * @param query
	 * @return
	 */
	public List<LocalZhaopinJobListBean> findJob(LocalZhaopinJobQueryBean query){
		List<LocalZhaopinJobListBean> result = new ArrayList<LocalZhaopinJobListBean>();
		LocalZhaopinJobListBean bean = null;
		
		List<CmcLtTask> tasks = localTaskService.findTask(query);
		for (CmcLtTask task : tasks) {
			bean = new LocalZhaopinJobListBean();
			bean.setTaskId(task.getTaskid());
			bean.setTitle(task.getTitle());
			bean.setWorkTimeType(task.getWorktimetype());
			bean.setRewardType(task.getRewardtype());
			bean.setReward(task.getReward());
			bean.setExp(task.getExp());
			bean.setEdu(task.getEdu());
			bean.setCityName(task.getCityname());
			bean.setAddress(task.getAddress());
			bean.setStatus(task.getStatus());
			result.add(bean);
		}
		
		return result;
	}
	
	/**
	 * 获取岗位信息
	 * @param taskId
	 * @param userId
	 * @return
	 */
	public LocalZhaopinJobBean getJob(Long taskId, Long userId){
		CmcLtTask entity = localTaskService.getTaskJoin(taskId);
		if(entity==null) {
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		if(!userId.equals(entity.getUserid())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		LocalZhaopinJobBean result = new LocalZhaopinJobBean();
		result.setTaskId(entity.getTaskid());
		result.setTitle(entity.getTitle());
		result.setWorkTimeType(entity.getWorktimetype());
		result.setRewardType(entity.getRewardtype());
		result.setReward(entity.getReward());
		if(entity.getCmcLtCompany()!=null){
			LocalCompanyBasicBean company = new LocalCompanyBasicBean();
			company.setCompanyId(entity.getCmcLtCompany().getCompanyid());
			company.setCompanyName(entity.getCmcLtCompany().getCompanyname());
			company.setHr(entity.getCmcLtCompany().getHr());
			company.setHrTel(entity.getCmcLtCompany().getHrtel());
			result.setCompany(company);
		}
		result.setCityName(entity.getCityname());
		result.setAddress(entity.getAddress());
		result.setExp(entity.getExp());
		result.setEdu(entity.getEdu());
		result.setPeopleNum(entity.getPeoplenum());
		result.setJob(entity.getJob());
		result.setJobRequire(entity.getJobrequire());
		result.setStatus(entity.getStatus());
		result.setVerifyResult(entity.getVerifyresult());
		
		return result;
	}
	
	/**
	 * 添加新岗位
	 * @param bean
	 * @return
	 * @exception DataException
	 */
	public LocalZhaopinJobAddResult addJob(LocalZhaopinJobAddBean bean){
		//公司
		CmcLtCompany company = localCompanyService.getCompanyByUserId(bean.getUid());
		if(company==null || StringUtil.isBlankOrNull(company.getCompanyname())){
			throw new DataException("请完善公司信息");
		}else if(StringUtil.isBlankOrNull(company.getHr())
				|| StringUtil.isBlankOrNull(company.getHrtel())){
			throw new DataException("请完善招聘联系信息");
		}
		
		//查重
		if(localTaskService.isRepeat(null, company.getCompanyid(), bean.getTitle())){
			throw new DataException("岗位已经存在");
		}
		
		CmcLtTask task = new CmcLtTask();
		task.setUserid(bean.getUid());
		task.setCompanyid(company.getCompanyid());
		task.setTitle(bean.getTitle());
		task.setWorktimetype(bean.getWorkTimeType()!=null?bean.getWorkTimeType():DictConstant.DICT_LOCALTASK_WORKTIMETYPE_FULLTIME);
		if(bean.getRewardType()!=null && StringUtil.notBlankAndNull(bean.getReward())){
			task.setRewardtype(bean.getRewardType());
			task.setReward(LocalTaskUtil.formatReward(bean.getReward(), bean.getRewardType()));
			List<Integer> rewards = LocalTaskUtil.parseReward(task.getReward());
			task.setMinreward(rewards.get(0));
			task.setMaxreward(rewards.get(1));
		}
		task.setRefreward(LocalTaskUtil.calRefReward(task.getMinreward(), task.getMaxreward(), task.getRewardtype()));
		task.setContact(company.getHr()+company.getHrtel());
		task.setPeoplenum(bean.getPeopleNum());
		task.setExp(bean.getExp());
		task.setMinexp(LocalTaskUtil.parseExp(bean.getExp()));
		task.setEdu(bean.getEdu());
		task.setMinedu(LocalTaskUtil.parseEdu(bean.getEdu()));
		task.setJob(bean.getJob());
		task.setJobrequire(bean.getJobRequire());
		
		//城市处理
		if(StringUtil.notBlankAndNull(bean.getCityName())){
			GeographyBean geo = SystemUtil.getGeography(bean.getCityName());
			if(geo==null) throw new DataException("城市有误");
			task.setCityid(geo.getCityId());
			task.setCityname(geo.getCityName());
		}else{
			task.setCityid(0L);
			task.setCityname("");
		}
		task.setAddress(bean.getAddress());
		
		Date now = DateUtil.getSysDate();
		task.setStatus(DictConstant.DICT_LOCALTASK_STATUS_EDITING);
		task.setCreatetime(now);
		task.setCreateway(DictConstant.DICT_LOCALTASK_CREATEWAY_COMPANY);
		
		localTaskService.saveOrUpdate(task);
		
		LocalZhaopinJobAddResult result = new LocalZhaopinJobAddResult();
		result.setTaskId(task.getTaskid());
		return result;
	}
	
	/**
	 * 更新基本信息
	 * @param bean
	 * @exception DataException
	 */
	public void updateJobBasic(LocalZhaopinJobBasicUpdateBean bean){
		CmcLtTask task = getAndCheckEdit(bean.getTaskId(), bean.getUid());
		task.setTitle(bean.getTitle());
		task.setWorktimetype(bean.getWorkTimeType());
		task.setRewardtype(bean.getRewardType());
		task.setReward(LocalTaskUtil.formatReward(bean.getReward(), bean.getRewardType()));
		List<Integer> rewards = LocalTaskUtil.parseReward(task.getReward());
		task.setMinreward(rewards.get(0));
		task.setMaxreward(rewards.get(1));
		task.setPeoplenum(bean.getPeopleNum());
		localTaskService.update(task);
	}
	
	/**
	 * 更新地址信息
	 * @param bean
	 * @exception DataException
	 */
	public void updateJobAddress(LocalZhaopinJobAddressUpdateBean bean){
		CmcLtTask task = getAndCheckEdit(bean.getTaskId(), bean.getUid());
		
		if(StringUtil.notBlankAndNull(bean.getCityName())){
			GeographyBean geo = SystemUtil.getGeography(bean.getCityName());
			if(geo==null) throw new DataException("城市有误");
			task.setCityid(geo.getCityId());
			task.setCityname(geo.getCityName());
		}else{
			task.setCityid(0L);
			task.setCityname("");
		}
		task.setAddress(bean.getAddress());
		
		localTaskService.update(task);
	}
	
	/**
	 * 更新学历要求信息
	 * @param bean
	 * @exception DataException
	 */
	public void updateJobEdu(LocalZhaopinJobEduUpdateBean bean){
		CmcLtTask task = getAndCheckEdit(bean.getTaskId(), bean.getUid());
		
		task.setEdu(bean.getEdu());
		task.setMinedu(LocalTaskUtil.parseEdu(bean.getEdu()));
		
		localTaskService.update(task);
	}
	
	/**
	 * 更新工作经验要求
	 * @param bean
	 * @exception DataException
	 */
	public void updateJobExp(LocalZhaopinJobExpUpdateBean bean){
		CmcLtTask task = getAndCheckEdit(bean.getTaskId(), bean.getUid());
		
		task.setExp(bean.getExp());
		task.setMinexp(LocalTaskUtil.parseExp(bean.getExp()));
		
		localTaskService.update(task);
	}
	
	/**
	 * 更新工作职责描述
	 * @param bean
	 * @exception DataException
	 */
	public void updateJobDuty(LocalZhaopinJobDutyUpdateBean bean){
		CmcLtTask task = getAndCheckEdit(bean.getTaskId(), bean.getUid());
		
		task.setJob(bean.getJob());
		
		localTaskService.update(task);
	}
	
	/**
	 * 更新任职要求
	 * @param bean
	 * @exception DataException
	 */
	public void updateJobRequire(LocalZhaopinJobRequireUpdateBean bean){
		CmcLtTask task = getAndCheckEdit(bean.getTaskId(), bean.getUid());
		
		task.setJobrequire(bean.getJobRequire());
		
		localTaskService.update(task);
	}
	
	/**
	 * 获取岗位信息，并校验能否编辑
	 * @param taskId
	 * @param userId
	 * @return
	 * @exception DataException
	 */
	private CmcLtTask getAndCheckEdit(Long taskId, Long userId){
		CmcLtTask task = localTaskService.getTask(taskId);
		if(task==null) {
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		if(!userId.equals(task.getUserid()) 
				|| !DictConstant.DICT_LOCALTASK_STATUS_EDITING.equals((task.getStatus()))){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		return task;
	}
	
	/**
	 * 用户重新编辑岗位
	 * @param taskId
	 * @param userId
	 * @exception DataException
	 */
	public void reEditJob(Long taskId, Long userId){
		CmcLtTask task = localTaskService.getTask(taskId);
		if(task==null) {
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		if(!userId.equals(task.getUserid()) || !LocalTaskUtil.canReEdit(task.getStatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}

		task.setStatus(DictConstant.DICT_LOCALTASK_STATUS_EDITING);
		task.setVerifyresult("");
		localTaskService.update(task);
	}
	
	/**
	 * 用户发布岗位（提交审核）
	 * @param taskId
	 * @param userId
	 * @exception DataException
	 */
	public void publishJob(Long taskId, Long userId){
		CmcLtTask task = localTaskService.getTaskJoinCompany(taskId);
		if(task==null) {
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		if(!userId.equals(task.getUserid())	|| !LocalTaskUtil.canPublish(task.getStatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		//岗位信息验证
		CmcLtCompany company = task.getCmcLtCompany();
		if(company==null 
				|| !DictConstant.DICT_LOCACOMPANY_CERTIFYSTATUS_CERTIFIED.equals(company.getCertifystatus())){
			throw new DataException("请完善企业信息并完成企业认证");
		}
		if(StringUtil.isBlankOrNull(task.getReward())){
			throw new DataException("薪酬信息为空");
		}
		if(StringUtil.isBlankOrNull(task.getContact())){
			throw new DataException("联系方式为空");
		}
		
		localTaskService.updateStatus(taskId, DictConstant.DICT_LOCALTASK_STATUS_VERIFYING);
		
		//通知
		String msg = "新岗位待审核："+task.getTitle();
		MailUtil.sendSimpleMail(SystemConfig.CS_MAILS, msg, msg);
	}
	
	/**
	 * 下架岗位
	 * @param taskId
	 * @param userId
	 * @exception DataException
	 */
	public void takeDownJob(Long taskId, Long userId){
		CmcLtTask task = localTaskService.getTask(taskId);
		if(task==null) {
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		if(!userId.equals(task.getUserid())	|| !LocalTaskUtil.canTakeDown(task.getStatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}

		localTaskService.updateStatus(taskId, DictConstant.DICT_LOCALTASK_STATUS_OFF);
	}
	
	/**
	 * 删除岗位
	 * @param taskId
	 * @param userId
	 * @exception DataException
	 */
	public void delJob(Long taskId, Long userId){
		CmcLtTask task = localTaskService.getTask(taskId);
		if(task==null) return ;
		if(!userId.equals(task.getUserid())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		localTaskService.delete(taskId);
	}
}
