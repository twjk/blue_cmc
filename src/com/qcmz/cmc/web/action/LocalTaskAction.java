package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.Constant;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcLtTask;
import com.qcmz.cmc.process.LocalTaskProcess;
import com.qcmz.cmc.service.ILocalTaskService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;

public class LocalTaskAction extends BaseAction {
	@Autowired
	private ILocalTaskService localTaskService;
	@Autowired
	private LocalTaskProcess localTaskProcess;
	
	private PageBean pageBean;
	private CmcLtTask entity;
	private Long id;
	private Integer status;
	private String reason;
	
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

		localTaskService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 添加/修改初始化
	 */
	public String doEdit(){
		if(id==null){
			entity= new CmcLtTask();
		}else{
			entity = localTaskService.getTaskJoin(id);
		}
		return EDIT;
	}
	
	/**
	 * 添加/修改
	 */
	public String doSave(){
		try {
			if(entity.getTaskid()==null){
				entity.setCreateway(DictConstant.DICT_LOCALTASK_CREATEWAY_ENTER);
			}
			localTaskService.saveOrUpdate(entity);
			
			//记录最近操作
			if(entity.getCompanyid()!=null){
				appendCookie(Constant.COOKIENAME_COMPANYIDS_RECENT, String.valueOf(entity.getCompanyid()));
			}
			if(entity.getSourceid()!=null){
				appendCookie(Constant.COOKIENAME_SOURCEIDS_RECENT, String.valueOf(entity.getSourceid()));
			}
		} catch(DataException e){
			handleResult = false;
			setResult(e.getMessage());
		}catch (Exception e) {
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
			localTaskService.delete(id);
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
	 * 通过审核
	 */
	public String doPassVerify(){
		try {
			localTaskProcess.passVerify(id);
		} catch (Exception e) {
			logger.error("通过审核失败",e);
			handleResult = false;
			setResult("通过审核失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 驳回审核
	 */
	public String doRejectVerify(){
		try {
			localTaskProcess.rejectVerify(id, reason);
		} catch (Exception e) {
			logger.error("驳回审核失败",e);
			handleResult = false;
			setResult("驳回审核失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 *	变更状态 
	 */
	public String doUpdateStatus(){
		try {
			localTaskService.updateStatus(id, status);
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

	public CmcLtTask getEntity() {
		return entity;
	}

	public void setEntity(CmcLtTask entity) {
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
