package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.LocalZhaopinInterface;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobAddReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobAddressUpdateReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobBasicUpdateReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobDelReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobDutyUpdateReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobEduUpdateReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobExpUpdateReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobGetReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobPublishReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobQueryReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobReEditReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobRequireUpdateReq;
import com.qcmz.cmc.ws.provide.vo.LocalZhaopinJobTakeDownReq;
import com.qcmz.framework.action.BaseWSAction;

public class LocalZhaopinWSAction extends BaseWSAction {
	@Autowired
	private LocalZhaopinInterface localZhaopinInterface;
	
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 岗位
	 */
	private String title;
	/**
	 * 工作时间类型
	 */
	private Integer workTimeType;
	/**
	 * 报酬类型
	 */
	private Integer rewardType;
	/**
	 * 最低报酬，元
	 */
	private Integer minReward;
	/**
	 * 最高报酬，元
	 */
	private Integer maxReward;
	/**
	 * 报酬描述
	 */
	private String reward;
	/**
	 * 招聘人数
	 */
	private String peopleNum;
	/**
	 * 学历要求说明
	 */
	private String edu;
	/**
	 * 工作经验说明
	 */
	private String exp;
	/**
	 * 岗位职责
	 */
	private String job;
	/**
	 * 任职要求
	 */
	private String jobRequire;
	/**
	 * 工作城市名称
	 */
	private String cityName;
	/**
	 * 工作地址
	 */
	private String address;
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 获取岗位列表
	 */
	public String findJob(){
		LocalZhaopinJobQueryReq req = new LocalZhaopinJobQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setTitle(title);
		req.getBean().setStatus(status);

		jsonObj = localZhaopinInterface.findJob(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	/**
	 * 获取岗位详情
	 * @return
	 */
	public String getJobDetail(){
		LocalZhaopinJobGetReq req = new LocalZhaopinJobGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setTaskId(taskId);
		
		jsonObj = localZhaopinInterface.getJobDetail(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 添加新岗位
	 */
	public String addJob(){
		LocalZhaopinJobAddReq req = new LocalZhaopinJobAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setTitle(title);
		req.getBean().setWorkTimeType(workTimeType);
		req.getBean().setRewardType(rewardType);
		req.getBean().setReward(reward);
		req.getBean().setPeopleNum(peopleNum);
		req.getBean().setEdu(edu);
		req.getBean().setExp(exp);
		req.getBean().setJob(job);
		req.getBean().setJobRequire(jobRequire);
		req.getBean().setCityName(cityName);
		req.getBean().setAddress(address);
		
		jsonObj = localZhaopinInterface.addJob(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 更新基本信息
	 */
	public String updateJobBasic(){
		LocalZhaopinJobBasicUpdateReq req = new LocalZhaopinJobBasicUpdateReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setTaskId(taskId);
		req.getBean().setTitle(title);
		req.getBean().setWorkTimeType(workTimeType);
		req.getBean().setRewardType(rewardType);
		req.getBean().setReward(reward);
		req.getBean().setPeopleNum(peopleNum);
		
		jsonObj = localZhaopinInterface.updateJobBasic(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 更新工作地址
	 */
	public String updateJobAddress(){
		LocalZhaopinJobAddressUpdateReq req = new LocalZhaopinJobAddressUpdateReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setTaskId(taskId);
		req.getBean().setCityName(cityName);
		req.getBean().setAddress(address);
		
		jsonObj = localZhaopinInterface.updateJobAddress(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 更新学历要求
	 */
	public String updateJobEdu(){
		LocalZhaopinJobEduUpdateReq req = new LocalZhaopinJobEduUpdateReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setTaskId(taskId);
		req.getBean().setEdu(edu);
		
		jsonObj = localZhaopinInterface.updateJobEdu(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 更新工作经验要求
	 */
	public String updateJobExp(){
		LocalZhaopinJobExpUpdateReq req = new LocalZhaopinJobExpUpdateReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setTaskId(taskId);
		req.getBean().setExp(exp);
		
		jsonObj = localZhaopinInterface.updateJobExp(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 更新工作职责
	 */
	public String updateJobDuty(){
		LocalZhaopinJobDutyUpdateReq req = new LocalZhaopinJobDutyUpdateReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setTaskId(taskId);
		req.getBean().setJob(job);
		
		jsonObj = localZhaopinInterface.updateJobDuty(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 更新任职要求
	 */
	public String updateJobRequire(){
		LocalZhaopinJobRequireUpdateReq req = new LocalZhaopinJobRequireUpdateReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setTaskId(taskId);
		req.getBean().setJobRequire(jobRequire);
		
		jsonObj = localZhaopinInterface.updateJobRequire(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 重新编辑岗位
	 */
	public String reEditJob(){
		LocalZhaopinJobReEditReq req = new LocalZhaopinJobReEditReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setTaskId(taskId);
		
		jsonObj = localZhaopinInterface.reEditJob(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 发布岗位
	 */
	public String publishJob(){
		LocalZhaopinJobPublishReq req = new LocalZhaopinJobPublishReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setTaskId(taskId);
		
		jsonObj = localZhaopinInterface.publishJob(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 下架岗位
	 */
	public String takeDownJob(){
		LocalZhaopinJobTakeDownReq req = new LocalZhaopinJobTakeDownReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setTaskId(taskId);
		
		jsonObj = localZhaopinInterface.takeDownJob(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 删除岗位
	 */
	public String delJob(){
		LocalZhaopinJobDelReq req = new LocalZhaopinJobDelReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setTaskId(taskId);
		
		jsonObj = localZhaopinInterface.delJob(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getWorkTimeType() {
		return workTimeType;
	}
	public void setWorkTimeType(Integer workTimeType) {
		this.workTimeType = workTimeType;
	}
	public Integer getRewardType() {
		return rewardType;
	}
	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}
	public Integer getMinReward() {
		return minReward;
	}
	public void setMinReward(Integer minReward) {
		this.minReward = minReward;
	}
	public Integer getMaxReward() {
		return maxReward;
	}
	public void setMaxReward(Integer maxReward) {
		this.maxReward = maxReward;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public String getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
	}
	public String getEdu() {
		return edu;
	}
	public void setEdu(String edu) {
		this.edu = edu;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getJobRequire() {
		return jobRequire;
	}
	public void setJobRequire(String jobRequire) {
		this.jobRequire = jobRequire;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}


	public Long getTaskId() {
		return taskId;
	}


	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}
}