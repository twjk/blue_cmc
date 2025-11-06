package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.IDubOrderService;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

public class DubOrderDealAction extends BaseAction {
	@Autowired
	private IDubOrderService dubOrderService;
	
	private String orderId;
	private CmcROrder order;
	/**
	 * 是否他人处理的订单
	 */
	private boolean other;
	/**
	 * 是否可以开始
	 */
	private boolean canStart;
	/**
	 * 是否可以完成
	 */
	private boolean canFinish;
	/**
	 * 是否可以取消
	 */
	private boolean canCancel;
	
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
	 * 修改历史：
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));
		
		dubOrderService.findDeal(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 处理
	 */
	public String doEdit(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		order = dubOrderService.getOrderJoin(orderId);
		
		other = StringUtil.notBlankAndNull(order.getOpercd()) && !oper.getOperCd().equals(order.getOpercd());
		canStart = OrderUtil.canAccept(order.getOrdercat(), order.getDealprogress(), order.getWaittime());
		canFinish = OrderUtil.canFinish(order.getDealprogress());
		canCancel = OrderUtil.canReject(order.getOrdercat(), order.getDealstatus());
		
		return "deal";
	}

	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public CmcROrder getOrder() {
		return order;
	}

	public void setOrder(CmcROrder order) {
		this.order = order;
	}

	public boolean isOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}

	public boolean isCanStart() {
		return canStart;
	}

	public void setCanStart(boolean canStart) {
		this.canStart = canStart;
	}

	public boolean isCanFinish() {
		return canFinish;
	}

	public void setCanFinish(boolean canFinish) {
		this.canFinish = canFinish;
	}

	public boolean isCanCancel() {
		return canCancel;
	}

	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
}
