package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcDialog;
import com.qcmz.cmc.service.IDialogService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

public class DialogAction extends BaseAction {
	@Autowired
	private IDialogService dialogService;
	
	private Long dialogId;
	
	private CmcDialog entity;
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
		
		dialogService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 详情
	 */
	public String doDetail(){
		entity = dialogService.getDialogJoin(dialogId);
		return DETAIL;
	}

	public Long getDialogId() {
		return dialogId;
	}

	public void setDialogId(Long dialogId) {
		this.dialogId = dialogId;
	}

	public CmcDialog getEntity() {
		return entity;
	}

	public void setEntity(CmcDialog entity) {
		this.entity = entity;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
}
