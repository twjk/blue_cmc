package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.ws.provide.service.UserCrowdTaskQcInterface;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectQcReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdSubjectQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcCountReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcQueryReq;
import com.qcmz.cmc.ws.provide.vo.UserCrowdTaskQcReq;
import com.qcmz.framework.action.BaseWSAction;

/**
 * 众包任务审校
 */
public class UserCrowdTaskQcWSAction extends BaseWSAction {
	@Autowired
	private UserCrowdTaskQcInterface userCrowdTaskQcInterface;
	
	/**
	 * 用户任务编号
	 */
	private String utId;
	/**
	 * 审校状态
	 */
	private Integer qcStatus;
	/**
	 * 审校人用户编号
	 */
	private Long qcUid;
	/**
	 * 题目编号
	 */
	private Long subjectId;
	/**
	 * 题目得分
	 */
	private Integer subjectScore;
	/**
	 * 答案审校结果
	 * 多个用;分隔
	 * 格式为"答案编号|得分;答案编号|得分"
	 */
	private String answerQc;
	/**
	 * 审校编号
	 */
	private Long qcId;
		
	/**
	 * 最后一条记录编号
	 */
	private String moreBaseId;
	private String pageSize;
	
	/**
	 * 分页获取用户任务审校列表
	 * @return
	 */
	public String findUserTask(){
		UserCrowdTaskQcQueryReq req = new UserCrowdTaskQcQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setQcUid(qcUid);
		req.getBean().setQcStatus(qcStatus);
		req.getBean().setMoreBaseId(moreBaseId);
		
		jsonObj = userCrowdTaskQcInterface.findUserTask(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取审校任务数
	 * @return
	 */
	public String getCount(){
		UserCrowdTaskQcCountReq req = new UserCrowdTaskQcCountReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setQcUid(qcUid);
		
		jsonObj = userCrowdTaskQcInterface.getCount(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 开始审校
	 * @return
	 */
	public String startQc(){
		UserCrowdTaskQcReq req = new UserCrowdTaskQcReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUtId(utId);
		req.setQcUid(qcUid);
				
		jsonObj = userCrowdTaskQcInterface.startQc(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取审校题目列表
	 * @return
	 */
	public String findUserSubject(){
		UserCrowdSubjectQueryReq req = new UserCrowdSubjectQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUtId(utId);
		req.setQcId(qcId);
		
		jsonObj = userCrowdTaskQcInterface.findUserSubject(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 题目审校
	 * @return
	 */
	public String qcSubject(){
		UserCrowdSubjectQcReq req = new UserCrowdSubjectQcReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUtId(utId);
		req.getBean().setQcUid(qcUid);
		req.getBean().setSubjectId(subjectId);
		req.getBean().setSubjectScore(subjectScore);
		req.getBean().setAnswerQcs(BeanConvertUtil.toUserCrowdAnswerQcBean(answerQc));
				
		jsonObj = userCrowdTaskQcInterface.qcSubject(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 完成审校
	 * @return
	 */
	public String finishQc(){
		UserCrowdTaskQcReq req = new UserCrowdTaskQcReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUtId(utId);
		req.setQcUid(qcUid);
				
		jsonObj = userCrowdTaskQcInterface.finishQc(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public String getUtId() {
		return utId;
	}

	public void setUtId(String utId) {
		this.utId = utId;
	}

	public Integer getQcStatus() {
		return qcStatus;
	}

	public void setQcStatus(Integer qcStatus) {
		this.qcStatus = qcStatus;
	}

	public Long getQcUid() {
		return qcUid;
	}

	public void setQcUid(Long qcUid) {
		this.qcUid = qcUid;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getSubjectScore() {
		return subjectScore;
	}

	public void setSubjectScore(Integer subjectScore) {
		this.subjectScore = subjectScore;
	}

	public String getAnswerQc() {
		return answerQc;
	}

	public void setAnswerQc(String answerQc) {
		this.answerQc = answerQc;
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

	public Long getQcId() {
		return qcId;
	}

	public void setQcId(Long qcId) {
		this.qcId = qcId;
	}
	
}
