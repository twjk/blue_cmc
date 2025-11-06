package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.service.ITransCorrectService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransCorrectAction extends BaseAction {
	@Autowired
	private ITransCorrectService transCorrectService;
	
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

		transCorrectService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	
}
