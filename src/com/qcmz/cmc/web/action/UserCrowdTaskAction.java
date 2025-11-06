package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcCtUsertask;
import com.qcmz.cmc.process.UserCrowdTaskProcess;
import com.qcmz.cmc.service.IUserCrowdTaskService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

/**
 * 用户众包任务
 */
public class UserCrowdTaskAction extends BaseAction {
	@Autowired
	private IUserCrowdTaskService userCrowdTaskService;
	@Autowired
	private UserCrowdTaskProcess userCrowdTaskProcess;
	
	private String utId;
	private CmcCtUsertask entity;
	
	private Long taskId;
	private Integer daysAgo;
	private String reason;
	
	private PageBean pageBean;
		
	/**
	 * 页面初始化
	 */
	public String doInit(){
		return QUERY;
	}
	
	/**
	 * 分页查询
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		userCrowdTaskService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 详细
	 */
	public String doDetail(){
		entity = userCrowdTaskService.getUserTaskJoinAll(utId);
		
		return DETAIL;
	}
	
	/**
	 * 批量取消指定任务未完成的用户任务
	 */
	public String doCancelBatch(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			int count = userCrowdTaskProcess.cancelTaskByTaskId(taskId, reason, daysAgo, oper);
			setResult(String.valueOf(count));
		} catch (Exception e) {
			logger.error("取消用户任务失败",e);
			handleResult = false;
			setResult("取消用户任务失败");
		}		
		
		print();
		
		return null;
	}
	
	/**
	 * 取消用户任务
	 */
	public String doCancel(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			userCrowdTaskProcess.cancelTaskByUtId(utId, reason, oper);
		} catch (Exception e) {
			logger.error("取消用户任务失败",e);
			handleResult = false;
			setResult("取消用户任务失败");
		}		
		
		print();
		
		return null;
	}
	
	/**
	 * 完结用户任务
	 */
	public String doFinish(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			userCrowdTaskProcess.forceFinishTask(utId, oper);
		} catch (Exception e) {
			logger.error("强制完结用户任务失败",e);
			handleResult = false;
			setResult("强制完结用户任务失败");
		}		
		
		print();
		
		return null;
	} 
	
	public String getUtId() {
		return utId;
	}

	public void setUtId(String utId) {
		this.utId = utId;
	}

	public CmcCtUsertask getEntity() {
		return entity;
	}

	public void setEntity(CmcCtUsertask entity) {
		this.entity = entity;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Integer getDaysAgo() {
		return daysAgo;
	}

	public void setDaysAgo(Integer daysAgo) {
		this.daysAgo = daysAgo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
}
