package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.LocalTaskInterface;
import com.qcmz.cmc.ws.provide.vo.LocalTaskGetReq;
import com.qcmz.cmc.ws.provide.vo.LocalTaskQueryReq;
import com.qcmz.cmc.ws.provide.vo.LocalTaskSubCancelReq;
import com.qcmz.cmc.ws.provide.vo.LocalTaskSubReq;
import com.qcmz.cmc.ws.provide.vo.LocalTaskUserSubGetReq;
import com.qcmz.cmc.ws.provide.vo.LocalTaskUserSubQueryReq;
import com.qcmz.framework.action.BaseWSAction;

public class LocalTaskWSAction extends BaseWSAction {
	@Autowired
	private LocalTaskInterface localTaskInterface;
	
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 工作地址
	 */
	private String address;
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 纬度
	 */
	private String lat;
	/**
	 * 任务名称
	 */
	private String title;
	/**
	 * 工作时间类型
	 */
	private Integer workTimeType;
	/**
	 * 工作多少年
	 */
	private Integer workYear;
	/**
	 * 期望报酬
	 */
	private Integer reward;
	@Deprecated
	private Integer salary;
	/**
	 * 报酬类型
	 */
	private Integer rewardType;
	/**
	 * 学历
	 */
	private Integer edu;
	/**
	 * 每页记录数
	 */
	private String pageSize;
	/**
	 * 最后一条sortId
	 */
	private String moreBaseId;
	/**
	 * 订阅内容
	 */
	private String content;
	
	/**
	 * 分页获取同城任务列表
	 */
	public String findTask(){
		LocalTaskQueryReq req = new LocalTaskQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.getBean().setCityName(cityName);
		req.getBean().setAddress(address);
		req.getBean().setLon(lon);
		req.getBean().setLat(lat);
		req.getBean().setTitle(title);
		req.getBean().setWorkTimeType(workTimeType);
		req.getBean().setWorkYear(workYear);
		req.getBean().setEdu(edu);
		//兼容老版本
		if(reward==null && salary!=null){
			reward = salary;
		}
		req.getBean().setReward(reward);
		req.getBean().setRewardType(rewardType);
		req.getBean().setMoreBaseId(moreBaseId);
		
		jsonObj = localTaskInterface.findTask(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取同城任务列表
	 */
	public String findSubTask(){
		LocalTaskUserSubQueryReq req = new LocalTaskUserSubQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.getBean().setUid(uid);
		req.getBean().setMoreBaseId(moreBaseId);
		
		jsonObj = localTaskInterface.findSubTask(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取同城任务信息
	 * @return
	 */
	public String getTask(){
		LocalTaskGetReq req = new LocalTaskGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setTaskId(taskId);
		
		jsonObj = localTaskInterface.getTask(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 订阅
	 * @return
	 */
	public String sub(){
		LocalTaskSubReq req = new LocalTaskSubReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setContent(content);
		req.getBean().setCityName(cityName);
		req.getBean().setAddress(address);
		req.getBean().setLon(lon);
		req.getBean().setLat(lat);
		req.getBean().setTitle(title);
		req.getBean().setWorkTimeType(workTimeType);
		req.getBean().setWorkYear(workYear);
		req.getBean().setEdu(edu);
		req.getBean().setRewardType(rewardType);
		req.getBean().setReward(reward);
		
		jsonObj = localTaskInterface.sub(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 取消订阅
	 * @return
	 */
	public String cancelSub(){
		LocalTaskSubCancelReq req = new LocalTaskSubCancelReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		
		jsonObj = localTaskInterface.cancelSub(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取用户订阅信息
	 * @return
	 */
	public String getSub(){
		LocalTaskUserSubGetReq req = new LocalTaskUserSubGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		
		jsonObj = localTaskInterface.getSub(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public Long getTaskId() {
		return taskId;
	}
	
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
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

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
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

	public Integer getWorkYear() {
		return workYear;
	}

	public void setWorkYear(Integer workYear) {
		this.workYear = workYear;
	}

	public Integer getReward() {
		return reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
	}

	public Integer getRewardType() {
		return rewardType;
	}

	public void setRewardType(Integer rewardType) {
		this.rewardType = rewardType;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Integer getEdu() {
		return edu;
	}

	public void setEdu(Integer edu) {
		this.edu = edu;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMoreBaseId() {
		return moreBaseId;
	}

	public void setMoreBaseId(String moreBaseId) {
		this.moreBaseId = moreBaseId;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

}
