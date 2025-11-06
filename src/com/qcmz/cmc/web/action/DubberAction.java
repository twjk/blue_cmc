package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcDubber;
import com.qcmz.cmc.service.IDubberService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class DubberAction extends BaseAction {
	@Autowired
	private IDubberService dubberService;
	
	private PageBean pageBean;
	private CmcDubber entity;
	private Long id;
	private Integer status;
	
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

		dubberService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 添加/修改初始化
	 */
	public String doEdit(){
		if(id!=null){
			entity = dubberService.getDubber(id);
		}
		return EDIT;
	}
	
	/**
	 * 添加/修改
	 */
	public String doSave(){
		try {
			dubberService.saveOrUpdateDubber(entity);
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 *	变更状态 
	 */
	public String doUpdateStatus(){
		try {
			dubberService.updateStatus(id, status);
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();
		
		return null;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public CmcDubber getEntity() {
		return entity;
	}

	public void setEntity(CmcDubber entity) {
		this.entity = entity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
