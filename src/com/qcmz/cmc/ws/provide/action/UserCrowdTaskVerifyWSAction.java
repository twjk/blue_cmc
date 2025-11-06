package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.UserCrowdTaskVerifyInterface;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyCountReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskVerifyStartReq;
import com.qcmz.framework.action.BaseWSAction;

/**
 * 众包任务报名审核
 */
public class UserCrowdTaskVerifyWSAction extends BaseWSAction {
	@Autowired
	private UserCrowdTaskVerifyInterface userCrowdTaskVerifyInterface;
	
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 审核人用户编号
	 */
	private Long verifyUid;
	/**
	 * 审核状态
	 */
	private Integer verifyStatus;
	/**
	 * 是否审核通过
	 */
	private Integer pass;
	/**
	 * 原因
	 */
	private String reason;
	
	/**
	 * 更多基准编号
	 */
	private String moreBaseId;
	private String pageSize;
	
	/**
	 * 分页获取用户任务报名审核列表
	 * @return
	 */
	public String findUserTask(){
		UserCrowdTaskVerifyQueryReq req = new UserCrowdTaskVerifyQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setVerifyUid(verifyUid);
		req.getBean().setVerifyStatus(verifyStatus);
		req.getBean().setMoreBaseId(moreBaseId);
		
		jsonObj = userCrowdTaskVerifyInterface.findUserTask(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取审核任务数
	 * @return
	 */
	public String getCount(){
		UserCrowdTaskVerifyCountReq req = new UserCrowdTaskVerifyCountReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setVerifyUid(verifyUid);
		
		jsonObj = userCrowdTaskVerifyInterface.getCount(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 开始报名审核
	 * @return
	 */
	public String startVerify(){
		UserCrowdTaskVerifyStartReq req = new UserCrowdTaskVerifyStartReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUtId(utId);
		req.setVerifyUid(verifyUid);
				
		jsonObj = userCrowdTaskVerifyInterface.startVerify(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 完成报名审核
	 * @return
	 */
	public String finishVerify(){
		UserCrowdTaskVerifyReq req = new UserCrowdTaskVerifyReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUtId(utId);
		req.getBean().setPass(pass);
		req.getBean().setReason(reason);
		req.getBean().setVerifyUid(verifyUid);
				
		jsonObj = userCrowdTaskVerifyInterface.finishVerify(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public String getUtId() {
		return utId;
	}

	public void setUtId(String utId) {
		this.utId = utId;
	}

	public Long getVerifyUid() {
		return verifyUid;
	}

	public void setVerifyUid(Long verifyUid) {
		this.verifyUid = verifyUid;
	}

	public Integer getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(Integer verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public Integer getPass() {
		return pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
