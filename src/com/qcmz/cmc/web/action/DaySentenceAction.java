package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.entity.CmcPkgDaysentence;
import com.qcmz.cmc.process.CacheSynProcess;
import com.qcmz.cmc.service.IDaySentenceService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataAccessException;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class DaySentenceAction extends BaseAction {
	@Autowired
	private IDaySentenceService daySentenceService;
	
	private PageBean pageBean;
	private CmcPkgDaysentence entity;
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

		daySentenceService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 添加/修改初始化
	 */
	public String doEdit(){
		if(id!=null){
			entity = daySentenceService.load(id);
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
			daySentenceService.saveOrUpdate(entity, oper);
			CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_DAYSENTENCE);
		} catch(SystemException e){
			handleResult = false;
			setResult(e.getMessage());
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
	 *	变更状态 
	 */
	public String doUpdateStatus(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			daySentenceService.updateStatus(id, status, oper);
			CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_DAYSENTENCE);
		} catch(SystemException e){
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
			daySentenceService.delete(id, oper);
			CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_DAYSENTENCE);
		} catch(DataAccessException e){
			handleResult = false;
			setResult(e.getMessage());
		} catch(SystemException e){
			handleResult = false;
			setResult(e.getMessage());
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
	 * 生成静态页
	 */
	public String doHtml(){
		try {
			daySentenceService.createHtml(id);
			CacheSynProcess.synCache(BeanConstant.BEANID_CACHE_DAYSENTENCE);
		} catch(DataException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch(SystemException e){
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			logger.error("生成静态页失败",e);
			handleResult = false;
			setResult("生成静态页失败");
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

	public CmcPkgDaysentence getEntity() {
		return entity;
	}

	public void setEntity(CmcPkgDaysentence entity) {
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
