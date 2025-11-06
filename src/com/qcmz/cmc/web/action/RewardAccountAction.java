package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.entity.CmcRewardAccount;
import com.qcmz.cmc.process.CacheSynProcess;
import com.qcmz.cmc.process.RewardProcess;
import com.qcmz.cmc.service.IRewardAccountService;
import com.qcmz.cmc.service.IRewardBillService;
import com.qcmz.cmc.vo.RewardOfflineBean;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

public class RewardAccountAction extends BaseAction {
	@Autowired
	private IRewardAccountService rewardAccountService;	
	@Autowired
	private IRewardBillService rewardBillService;
	
	@Autowired
	private RewardProcess rewardProcess;

	private Long userId;
	private CmcRewardAccount entity;
	private RewardOfflineBean offlineBean;
	private Integer status;
	
	private PageBean pageBean;
	private String orderby;
	
	/**
	 * 页面初始化
	 */
	public String doInit(){
		return QUERY;
	}
	
	/**
	 * 查询
	 * @return
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		rewardAccountService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 详细
	 */
	public String doDetail(){
		entity = rewardAccountService.getAccountJoinUser(userId);
		return DETAIL;
	}

	/**
	 * 查询
	 * @return
	 */
	public String doQueryBill(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		rewardBillService.queryByMapTerm(getParameterMap(), pageBean);
		
		return "billlist";
	}
	
	/**
	 * 变更状态 
	 */
	public String doUpdateStatus(){
		try {
			rewardAccountService.updateStatus(userId, status);
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 添加手工发放奖励金
	 */
	public String doEditOffline(){
		return "offline";
	}

	/**
	 * 保存手工发放奖励金
	 */
	public String doSaveOffline(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			rewardProcess.offlineReward(offlineBean, oper);
			setResult(String.valueOf(offlineBean.getUserId()));
		} catch(DataException e){
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();
		
		return null;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public CmcRewardAccount getEntity() {
		return entity;
	}

	public void setEntity(CmcRewardAccount entity) {
		this.entity = entity;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public RewardOfflineBean getOfflineBean() {
		return offlineBean;
	}

	public void setOfflineBean(RewardOfflineBean offlineBean) {
		this.offlineBean = offlineBean;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
