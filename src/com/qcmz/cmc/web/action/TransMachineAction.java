package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.entity.CmcTm;
import com.qcmz.cmc.process.CacheSynProcess;
import com.qcmz.cmc.service.ITmService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataRepeatException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransMachineAction extends BaseAction {
	@Autowired
	private ITmService tmService;
	
	private PageBean pageBean;
	private CmcTm entity;
	private Long id;
	
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

		tmService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 添加/修改初始化
	 */
	public String doEdit(){
		if(id!=null){
			entity = tmService.getTm(id);
		}
		return EDIT;
	}

	
	/**
	 * 添加/修改
	 */
	public String doSave(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			tmService.saveOrUpdate(entity, oper);
			CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_TM);
		} catch(DataRepeatException e){
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
	 * 删除
	 */
	public String doDelete(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), IPUtil.getRemoteAddr(request));
		
		try {
			//删除
			tmService.delete(id, oper);
			CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_TM);
		} catch (Exception e) {
			logger.error("删除信息失败",e);
			handleResult = false;
			setResult("删除信息失败");
		}
		
		//返回
		print();
		
		return null;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public CmcTm getEntity() {
		return entity;
	}

	public void setEntity(CmcTm entity) {
		this.entity = entity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
