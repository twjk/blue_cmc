package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.process.TransCorrectProcess;
import com.qcmz.cmc.service.ITransDiffService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TransDiffAction extends BaseAction {
	@Autowired
	private ITransDiffService transDiffService;
	@Autowired
	private TransCorrectProcess transCorrectProcess;
	
	private PageBean pageBean;
	private Long id;
	private String dst;
	private boolean toLib;
	
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

		transDiffService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 修正译文
	 */
	public String doCorrectDst(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			transCorrectProcess.correctDiff(id, dst, toLib, oper);
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public boolean isToLib() {
		return toLib;
	}

	public void setToLib(boolean toLib) {
		this.toLib = toLib;
	}
}
