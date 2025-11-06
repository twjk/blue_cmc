package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.cache.LocalCompanyMap;
import com.qcmz.cmc.constant.BeanConstant;
import com.qcmz.cmc.constant.Constant;
import com.qcmz.cmc.entity.CmcLtCompany;
import com.qcmz.cmc.process.CacheSynProcess;
import com.qcmz.cmc.process.LocalCompanyProcess;
import com.qcmz.cmc.service.ILocalCompanyService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;

public class LocalCompanyAction extends BaseAction {
	
	@Autowired
	private ILocalCompanyService localCompanyService;
	@Autowired
	private LocalCompanyProcess localCompanyProcess;
	
	@Autowired
	private LocalCompanyMap localCompanyMap;

	
	private CmcLtCompany entity;
	private Long id;
	private String reason;
	/**
	 * 查询关键词
	 */
	private String key;
	
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

		localCompanyService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 添加/修改初始化
	 */
	public String doEdit(){
		if(id!=null){
			entity = localCompanyService.getCompany(id);
		}
		return EDIT;
	}
	
	/**
	 * 添加/修改
	 */
	public String doSave(){
		try {
			localCompanyService.saveOrUpdate4System(entity);
			CacheSynProcess.synUpdateCacheThrd(BeanConstant.BEANID_CACHE_LOCALCOMPANY, entity);
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
	 * 通过认证
	 */
	public String doPassCertify(){
		try {
			localCompanyProcess.passCertify(id);
		} catch (Exception e) {
			logger.error("通过认证失败",e);
			handleResult = false;
			setResult("通过认证失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 驳回认证
	 */
	public String doRejectCertify(){
		try {
			localCompanyProcess.rejectCertify(id, reason);
		} catch (Exception e) {
			logger.error("驳回认证失败",e);
			handleResult = false;
			setResult("驳回认证失败");
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
			localCompanyService.deleteCompany(id);
			CacheSynProcess.synDeleteCacheThrd(BeanConstant.BEANID_CACHE_LOCALCOMPANY, id);
		} catch(DataException e){
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
	 * 查询
	 * @return
	 */
	public String findCompany(){
		if(key!=null) key = key.trim();
		
		String recentCompanyIds = getCookie(Constant.COOKIENAME_COMPANYIDS_RECENT);
		jsonObj = localCompanyMap.findSystemCompany(key, StringUtil.split2LongList(recentCompanyIds, ","));
		
		return JSON;
	}
	
	/**
	 * 获取公司
	 * @return
	 */
	public String getCompany(){
		if(id!=null){
			jsonObj = localCompanyMap.getCompany(id);
		}else if(StringUtil.notBlankAndNull(key)){
			jsonObj = localCompanyMap.getSystemCompany(key);
		}
		return JSON;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CmcLtCompany getEntity() {
		return entity;
	}

	public void setEntity(CmcLtCompany entity) {
		this.entity = entity;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
