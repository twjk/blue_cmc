package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcDialog;
import com.qcmz.cmc.process.DialogProcess;
import com.qcmz.cmc.service.IDialogMsgService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

public class DialogMsgAction extends BaseAction {
	@Autowired
	private IDialogMsgService dialogMsgService;
	@Autowired
	private DialogProcess dialogProcess;
	
	private Long dialogId;
	private String msg;
	
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
		
		dialogMsgService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 添加消息
	 */
	public String doAddMsg(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			dialogProcess.addCsMsg(dialogId, msg, oper);
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();

		return null;
	}	
	
	public Long getDialogId() {
		return dialogId;
	}

	public void setDialogId(Long dialogId) {
		this.dialogId = dialogId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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
