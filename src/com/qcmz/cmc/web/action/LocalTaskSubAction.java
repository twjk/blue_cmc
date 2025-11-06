package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.service.ILocalTaskSubService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

public class LocalTaskSubAction extends BaseAction {
	@Autowired
	private ILocalTaskSubService localTaskSubService;
	
	private Long id;
	
	private PageBean pageBean;
	
	/**
	 * 页面初始化
	 */
	public String doInit(){
		return QUERY;
	}
	
	/**
	 * 查询
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		localTaskSubService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

}
