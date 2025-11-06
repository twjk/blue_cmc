package com.qcmz.cmc.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.Constant;
import com.qcmz.cmc.process.TransLibProcess;
import com.qcmz.cmc.service.ITransLibService;
import com.qcmz.cmc.util.TransUtil;
import com.qcmz.cmc.vo.LibClassBean;
import com.qcmz.cmc.vo.TransLibraryEntity;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.DataRepeatException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransLibAction extends BaseAction {
	@Autowired
	private ITransLibService transLibService;
	@Autowired
	private TransLibProcess transLibProcess;
	
	private PageBean pageBean;
	private TransLibraryEntity entity;
	private String libClass;
	private Long themeId;
	private Long id;
	private Integer status;
	private Integer checkStatus;
	private List<LibClassBean> libClasses;
	/**
	 * 上次选择的源语言
	 */
	private String lastFrom;
	/**
	 * 上次选择的目标语言
	 */
	private String lastTo;
	private String orderby;
	
	/**
	 * 页面初始化
	 */
	public String doInit(){
		libClasses = TransUtil.findLibClassBean();
		return QUERY;
	}
	
	/**
	 * 查询
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		transLibService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 添加/修改初始化
	 */
	public String doEdit(){
		if(id!=null){
			entity = transLibService.getLib(libClass, id);
		}else{
			lastFrom = getCookie(Constant.COOKIENAME_TRANSLIB_FROM_LAST);
			lastTo = getCookie(Constant.COOKIENAME_TRANSLIB_TO_LAST);
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
			transLibProcess.saveOrUpdate(entity, oper);
			
			//记录最近选择的源语言和目标语言
			addCookie(Constant.COOKIENAME_TRANSLIB_FROM_LAST, entity.getFromlang());
			addCookie(Constant.COOKIENAME_TRANSLIB_TO_LAST, entity.getTolang());
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
	 *	变更状态 
	 */
	public String doUpdateStatus(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			transLibProcess.updateStatus(libClass, id, status, oper);
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 *	变更核对状态 
	 */
	public String doUpdateCheckStatus(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			transLibService.updateCheckStatus(libClass, id, checkStatus, oper);
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
			transLibProcess.delete(libClass, id, oper);
		} catch (Exception e) {
			logger.error("删除信息失败",e);
			handleResult = false;
			setResult("删除信息失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	//译库分拣
	public String doSorting(){
		try {
			transLibProcess.sortingData(libClass, themeId);
		} catch(DataException e){
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			logger.error("译库重新分拣失败",e);
			handleResult = false;
			setResult("译库重新分拣失败");
		}
		
		print();
		
		return null;
	}
	
	//自动校验
	public String doAutoCheck(){
		try {
			transLibProcess.doAutoCheck();
		} catch (Exception e) {
			logger.error("自动校验译库数据失败",e);
			handleResult = false;
			setResult("自动校验译库数据失败");
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

	public TransLibraryEntity getEntity() {
		return entity;
	}

	public void setEntity(TransLibraryEntity entity) {
		this.entity = entity;
	}

	public String getLibClass() {
		return libClass;
	}

	public void setLibClass(String libClass) {
		this.libClass = libClass;
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

	public List<LibClassBean> getLibClasses() {
		return libClasses;
	}

	public void setLibClasses(List<LibClassBean> libClasses) {
		this.libClasses = libClasses;
	}

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getLastFrom() {
		return lastFrom;
	}

	public void setLastFrom(String lastFrom) {
		this.lastFrom = lastFrom;
	}

	public String getLastTo() {
		return lastTo;
	}

	public void setLastTo(String lastTo) {
		this.lastTo = lastTo;
	}

	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
}
