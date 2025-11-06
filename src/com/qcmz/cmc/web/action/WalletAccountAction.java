package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcWalletAccount;
import com.qcmz.cmc.service.IWalletAccountService;
import com.qcmz.cmc.service.IWalletBillService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

public class WalletAccountAction extends BaseAction {
	@Autowired
	private IWalletAccountService walletAccountService;
	@Autowired
	private IWalletBillService walletBillService;

	private Long userId;
	private CmcWalletAccount entity;
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

		walletAccountService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 详细
	 */
	public String doDetail(){
		entity = walletAccountService.getAccountJoinUser(userId);
		return DETAIL;
	}

	/**
	 * 查询
	 * @return
	 */
	public String doQueryBill(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		walletBillService.queryByMapTerm(getParameterMap(), pageBean);
		
		return "billlist";
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

	public CmcWalletAccount getEntity() {
		return entity;
	}

	public void setEntity(CmcWalletAccount entity) {
		this.entity = entity;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
}
