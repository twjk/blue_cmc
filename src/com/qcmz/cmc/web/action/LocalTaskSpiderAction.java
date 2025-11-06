package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.process.LocalTaskSpiderProcess;
import com.qcmz.cmc.service.ILocalTaskSpiderService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

public class LocalTaskSpiderAction extends BaseAction {
	@Autowired
	private ILocalTaskSpiderService localTaskSpiderService;
	@Autowired
	private LocalTaskSpiderProcess localTaskSpiderProcess;
	
	
	private Long id;
	private String fullCatId;
	
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

		localTaskSpiderService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 开始处理
	 */
	public String doStartDeal(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			localTaskSpiderProcess.startDeal(id, oper);
		} catch(DataException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch (Exception e) {
			logger.error("开始处理失败",e);
			handleResult = false;
			setResult("开始处理失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	/**
	 * 完成处理
	 */
	public String doFinishDeal(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			localTaskSpiderProcess.finishDeal(id, oper);
		} catch(DataException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch (Exception e) {
			logger.error("完成处理失败",e);
			handleResult = false;
			setResult("完成处理失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	/**
	 * 转就业信息
	 */
	public String doToArticle(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			localTaskSpiderProcess.toArticle(id, fullCatId, oper);
		} catch(DataException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch (Exception e) {
			logger.error("转就业信息失败",e);
			handleResult = false;
			setResult("转就业信息失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullCatId() {
		return fullCatId;
	}

	public void setFullCatId(String fullCatId) {
		this.fullCatId = fullCatId;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

}
