package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.service.IPackageOrderService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

public class PackageOrderAction extends BaseAction {
	@Autowired
	private IPackageOrderService packageOrderService;
	
	//返回分页
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

		packageOrderService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
		
	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
}
