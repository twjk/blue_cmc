package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.service.IImageRecognitionService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class ImageRecognitionAction extends BaseAction {
	@Autowired
	private IImageRecognitionService imageRecognitionService;
	
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

		imageRecognitionService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

}
