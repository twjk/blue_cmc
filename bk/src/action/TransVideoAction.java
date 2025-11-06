package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.ITransVideoService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：视频口译
 * 修改历史：
 */
public class TransVideoAction extends BaseAction {
	@Autowired
	private ITransVideoService transVideoService;
	
	//返回分页
	private PageBean pageBean;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 短文快译信息
	 */
	private CmcROrder entity;
	
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

		transVideoService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 详细
	 */
	public String doDetail(){
		entity = transVideoService.getOrderJoin(orderId);
		return DETAIL;
	}
		
	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public CmcROrder getEntity() {
		return entity;
	}

	public void setEntity(CmcROrder entity) {
		this.entity = entity;
	}
}