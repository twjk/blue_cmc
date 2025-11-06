package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.service.IOrderService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

/**
 * 翻译订单查询
 */
public class TransOrderAction extends BaseAction {
	@Autowired
	private IOrderService orderService;

	
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

		orderService.findOrder(getParameterMap(), pageBean);
		return LIST;
	}
	
	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
}
