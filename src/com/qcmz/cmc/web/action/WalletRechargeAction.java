package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.process.WalletRechargeProcess;
import com.qcmz.cmc.service.IWalletRechargeService;
import com.qcmz.cmc.vo.WalletOfflineRechargeBean;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

public class WalletRechargeAction extends BaseAction {
	@Autowired
	private IWalletRechargeService walletRechargeService;
	@Autowired
	private WalletRechargeProcess walletRechargeProcess;

	private String orderId;
	private WalletOfflineRechargeBean offlineBean;
	private PageBean pageBean;
	
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

		walletRechargeService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 同步支付结果
	 */
	public String doSynPay(){
		try {
			walletRechargeProcess.synPay(orderId);
		} catch(ProxyException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch (Exception e) {
			logger.error("同步支付结果失败",e);
			handleResult = false;
			setResult("同步支付结果失败");
		}
		
		//返回
		print();
		
		return null;
	} 
	
	/**
	 * 退款
	 */
	public String doRefund(){
		boolean refund = false;
		try {
			UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
			Operator oper = new Operator(user.getUsername(), IPUtil.getRemoteAddr(request));
			
			refund = walletRechargeProcess.refund(orderId, oper);
		} catch (Exception e) {
			logger.error("退款失败",e);
		}
		
		if(!refund){
			handleResult = false;
			setResult("退款失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	/**
	 * 添加离线充值
	 */
	public String doEditOfflineRecharge(){
		return "offline";
	}

	/**
	 * 保存离线充值
	 */
	public String doSaveOfflineRecharge(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			walletRechargeProcess.saveOfflineRecharge(offlineBean, oper);
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
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public WalletOfflineRechargeBean getOfflineBean() {
		return offlineBean;
	}

	public void setOfflineBean(WalletOfflineRechargeBean offlineBean) {
		this.offlineBean = offlineBean;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
}
