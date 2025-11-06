package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcJobArticle;
import com.qcmz.cmc.service.IJobArticleService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class JobArticleAction extends BaseAction {
	@Autowired
	private IJobArticleService jobArticleService;
	
	private PageBean pageBean;
	private CmcJobArticle entity;
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

		jobArticleService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 添加/修改初始化
	 */
	public String doEdit(){
		if(id!=null){
			entity = jobArticleService.getArticleJoin(id);
		}
		return EDIT;
	}
	
	/**
	 * 添加/修改
	 */
	public String doSave(){
		try {
			jobArticleService.saveOrUpdate(entity);
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 删除
	 */
	public String doDelete(){
		try {
			//删除
			jobArticleService.delete(id);
		} catch (Exception e) {
			logger.error("删除信息失败",e);
			handleResult = false;
			setResult("删除信息失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	/**
	 *	变更状态 
	 */
	public String doUpdateStatus(){
		try {
			jobArticleService.updateStatus(id, status);
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

	public CmcJobArticle getEntity() {
		return entity;
	}

	public void setEntity(CmcJobArticle entity) {
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
