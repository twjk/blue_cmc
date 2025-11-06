package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.process.CacheSynProcess;
import com.qcmz.cmc.process.CrowdTaskProcess;
import com.qcmz.cmc.service.ICrowdTaskService;
import com.qcmz.cmc.vo.CrowdTaskAddBean;
import com.qcmz.cmc.vo.CrowdTaskEditBean;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;

public class CrowdTaskAction extends BaseAction {
	@Autowired
	private ICrowdTaskService crowdTaskService;
	@Autowired
	private CrowdTaskProcess crowdTaskProcess;
	
	private Long id;
	private Integer status;
	private Integer minSortIndex;
	private PageBean pageBean;
	private CrowdTaskAddBean bean;
	private CrowdTaskEditBean editBean;
	
	/**
	 * 页面初始化
	 */
	public String doInit(){
		return QUERY;
	}
	
	/**
	 * 分页查询
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		crowdTaskService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 添加初始化
	 */
	public String doEdit(){
		minSortIndex = crowdTaskService.getMinValidSortIndex();
		if(id==null){
			bean = new CrowdTaskAddBean();
			bean.setStartTime(DateUtil.getSysDateOnly());
			return ADD;
		}else{
			editBean = crowdTaskProcess.getTask4Edit(id);
			return EDIT;
		}
	}
	
	/**
	 * 添加
	 */
	public String doSave(){
		try {
			crowdTaskProcess.createTask(bean);
		} catch(DataException e){
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 修改
	 */
	public String doUpdate(){
		try {
			crowdTaskProcess.updateTask(editBean);
		} catch(DataException e){
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 变更状态 
	 */
	public String doUpdateStatus(){
		try {
			crowdTaskService.updateStatus(id, status);
			CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_CROWDTASK);
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();
		
		return null;
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

	public Integer getMinSortIndex() {
		return minSortIndex;
	}

	public void setMinSortIndex(Integer minSortIndex) {
		this.minSortIndex = minSortIndex;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public CrowdTaskAddBean getBean() {
		return bean;
	}

	public void setBean(CrowdTaskAddBean bean) {
		this.bean = bean;
	}

	public CrowdTaskProcess getCrowdTaskProcess() {
		return crowdTaskProcess;
	}

	public void setCrowdTaskProcess(CrowdTaskProcess crowdTaskProcess) {
		this.crowdTaskProcess = crowdTaskProcess;
	}

	public CrowdTaskEditBean getEditBean() {
		return editBean;
	}

	public void setEditBean(CrowdTaskEditBean editBean) {
		this.editBean = editBean;
	}
}
