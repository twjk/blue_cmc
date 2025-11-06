package com.qcmz.cmc.ws.provide.action;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.ws.provide.service.UserCrowdTaskInterface;
import com.qcmz.cmc.ws.provide.vo.UserCrowdNewSubjectQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectAnswerReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskApplyReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskCancelReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskCountReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskFinishReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskGetReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskStartReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskUploadReq;
import com.qcmz.framework.action.BaseWSAction;

/**
 * 用户众包任务
 */
public class UserCrowdTaskWSAction extends BaseWSAction {
	@Autowired
	private UserCrowdTaskInterface userCrowdTaskInterface;
	
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 用户设备标识
	 */
	private String uuid;
	/**
	 * 任务编号
	 */
	private Long taskId;
	/**
	 * 题目编号
	 */
	private Long subjectId;
	/**
	 * 最后一条题目编号
	 */
	private Long lastSubjectId;

	/**
	 * 答案
	 * 多个答案用§;§分隔，每个答案用§|§分隔
	 * 格式为"题目编号§|§选项编号§|§更多内容§|§录音§|§录音时长§|§译文§|§得分§|§图片§;§题目编号§|§选项编号§|§更多内容§|§录音§|§录音时长§|§译文§|§得分§|§图片"
	 */
	private String answer;
	/**
	 * 原因
	 */
	private String reason;
	
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 纬度
	 */
	private String lat;
		
	/**
	 * 更多基准编号
	 */
	private String moreBaseId;
	/**
	 * 每页记录数
	 */
	private String pageSize;
	/**
	 * 文件
	 */
	private File file;
	
	/**
	 * 分页获取用户任务列表
	 * @return
	 */
	public String findUserTask(){
		UserCrowdTaskQueryReq req = new UserCrowdTaskQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setMoreBaseId(moreBaseId);
		
		jsonObj = userCrowdTaskInterface.findUserTask(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取用户任务数
	 * @return
	 */
	public String getCount(){
		UserCrowdTaskCountReq req = new UserCrowdTaskCountReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		
		jsonObj = userCrowdTaskInterface.getCount(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取用户任务详情
	 * @return
	 */
	public String getUserTask(){
		UserCrowdTaskGetReq req = new UserCrowdTaskGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUtId(utId);
		
		jsonObj = userCrowdTaskInterface.getUserTask(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取用户任务新的题目列表
	 * @return
	 */
	public String findNewSubject(){
		UserCrowdNewSubjectQueryReq req = new UserCrowdNewSubjectQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUtId(utId);
		req.setUid(uid);
		req.setLastSubjectId(lastSubjectId);
		
		jsonObj = userCrowdTaskInterface.findNewSubject(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取用户任务题目列表
	 * @return
	 */
	public String findUserSubject(){
		UserCrowdSubjectQueryReq req = new UserCrowdSubjectQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUtId(utId);
		
		jsonObj = userCrowdTaskInterface.findUserSubject(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 报名
	 * @return
	 */
	public String applyTask(){
		UserCrowdTaskApplyReq req = new UserCrowdTaskApplyReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setTaskId(taskId);
		req.getBean().setUuid(uuid);
		req.getBean().setLon(lon);
		req.getBean().setLat(lat);
		req.getBean().setPreAnswers(BeanConvertUtil.toUserCrowdAnswer(answer));
		
		jsonObj = userCrowdTaskInterface.applyTask(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 取消任务
	 * @return
	 */
	public String cancelTask(){
		UserCrowdTaskCancelReq req = new UserCrowdTaskCancelReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setUtId(utId);
		req.setReason(reason);
		
		jsonObj = userCrowdTaskInterface.cancelTask(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 开始任务
	 * @return
	 */
	public String startTask(){
		UserCrowdTaskStartReq req = new UserCrowdTaskStartReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setUtId(utId);
		
		jsonObj = userCrowdTaskInterface.startTask(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 上传前置任务文件
	 * @return
	 */
	public String uploadPreTask(){
		UserCrowdTaskUploadReq req = new UserCrowdTaskUploadReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setTaskId(taskId);
		req.getBean().setSubjectId(subjectId);
		req.getBean().setFile(file);
		
		jsonObj = userCrowdTaskInterface.uploadPreTask(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	/**
	 * 上传任务文件
	 * @return
	 */
	public String uploadTask(){
		UserCrowdTaskUploadReq req = new UserCrowdTaskUploadReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setUtId(utId);
		req.getBean().setSubjectId(subjectId);
		req.getBean().setFile(file);
		
		jsonObj = userCrowdTaskInterface.uploadTask(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 答题
	 * @return
	 */
	public String answerSubject(){
		UserCrowdSubjectAnswerReq req = new UserCrowdSubjectAnswerReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setUtId(utId);
		
		req.getBean().setAnswers(BeanConvertUtil.toUserCrowdAnswer(answer));
		
		jsonObj = userCrowdTaskInterface.answerSubject(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 完成任务
	 * @return
	 */
	public String finishTask(){
		UserCrowdTaskFinishReq req = new UserCrowdTaskFinishReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setUtId(utId);
		
		jsonObj = userCrowdTaskInterface.finishTask(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public String getUtId() {
		return utId;
	}

	public void setUtId(String utId) {
		this.utId = utId;
	}

	public Long getUid() {
		return uid;
	}
	
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getLastSubjectId() {
		return lastSubjectId;
	}

	public void setLastSubjectId(Long lastSubjectId) {
		this.lastSubjectId = lastSubjectId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
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

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
