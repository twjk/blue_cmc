package com.qcmz.cmc.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcRewardCash;
import com.qcmz.cmc.process.RewardProcess;
import com.qcmz.cmc.service.IRewardCashService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

public class RewardCashAction extends BaseAction {
	@Autowired
	private IRewardCashService rewardCashService;
	@Autowired
	private RewardProcess rewardProcess;
	
	private Long id;
	private CmcRewardCash entity;
	private String reason;

	private List<CmcRewardCash> cashList;
	private PageBean pageBean;
	
	public String doInit(){
		return QUERY;
	}
	
	/**
	 * 分页查询
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		rewardCashService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 详细信息
	 */
	public String doEdit(){
		entity = rewardCashService.getCashJoin(id);
		cashList = rewardCashService.findCash(entity.getUserid());
		return EDIT;
	}
	
	/**
	 * 向用户付款
	 */
	public String doTransfer(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			CmcRewardCash cash = rewardProcess.transfer(id, oper);
			if(DictConstants.DICT_CASHSTATUS_APPLY.equals(cash.getStatus())){
				handleResult = false;
				setResult("向用户付款失败【"+cash.getTraderesult()+"】");
			}
		} catch (Exception e) {
			logger.error("向用户付款失败",e);
			handleResult = false;
			setResult("向用户付款失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 驳回提现申请
	 */
	public String doRejectCash(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			rewardCashService.rejectCash(id, reason, oper);
		} catch (Exception e) {
			logger.error("驳回提现申请失败",e);
			handleResult = false;
			setResult("驳回提现申请失败");
		}
		
		print();
		
		return null;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CmcRewardCash getEntity() {
		return entity;
	}

	public void setEntity(CmcRewardCash entity) {
		this.entity = entity;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<CmcRewardCash> getCashList() {
		return cashList;
	}

	public void setCashList(List<CmcRewardCash> cashList) {
		this.cashList = cashList;
	}
}
