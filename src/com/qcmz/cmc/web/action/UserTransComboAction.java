package com.qcmz.cmc.web.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcUCombo;
import com.qcmz.cmc.process.TransComboProcess;
import com.qcmz.cmc.service.IUserTransComboService;
import com.qcmz.cmc.ws.provide.vo.TransComboBean;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataAccessException;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

public class UserTransComboAction extends BaseAction {
	@Autowired
	private IUserTransComboService userTransComboService;
	@Autowired
	private TransComboProcess transComboProcess;
	
	private Long ucid;
	private int copies;
	private Date usedTime;
	private String orderId;
	private String remark;
	
	private CmcUCombo entity;
	private PageBean pageBean;
	private List<TransComboBean> combos;
	
	/**
	 * 页面初始化
	 */
	public String doInit(){
		return QUERY;
	}
	
	/**
	 * 查询
	 * @return
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		userTransComboService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 详细
	 */
	public String doDetail(){
		entity = userTransComboService.getUserComboJoin(ucid);
		return DETAIL;
	}

	/**
	 * 编辑
	 * @return
	 */
	public String doEdit(){
		combos = transComboProcess.findCombo(DictConstants.DICT_TRANSTYPE_VIDEO);
		return EDIT;
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String doSave(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			setResult(transComboProcess.handAddUserCombo(entity, copies, oper));
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败："+e.getMessage());
		}
		
		print();
		
		return null;
	} 
	
	/**
	 * 作废
	 */
	public String doInvalid(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), IPUtil.getRemoteAddr(request));
		
		try {
			//作废
			userTransComboService.invalidUserCombo(ucid, oper);
		}catch (DataAccessException e) {
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			logger.error("作废信息失败",e);
			handleResult = false;
			setResult("作废信息失败");
		}
		
		//返回
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
			//作废
			userTransComboService.deleteUserCombo(ucid, oper);
		}catch (DataAccessException e) {
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
	 * 保存套餐使用
	 * @return
	 */
	public String doSaveUsed(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			userTransComboService.useUserCombo4Operator(ucid, usedTime, orderId, remark, oper);
		} catch (DataException e) {
			handleResult = false;
			setResult("保存信息失败："+e.getMessage());
		} catch (Exception e) {
			logger.error("保存信息失败",e);
			handleResult = false;
			setResult("保存信息失败："+e.getMessage());
		}
		
		print();
		
		return null;
	} 	
	
	public Long getUcid() {
		return ucid;
	}

	public void setUcid(Long ucid) {
		this.ucid = ucid;
	}

	public CmcUCombo getEntity() {
		return entity;
	}

	public void setEntity(CmcUCombo entity) {
		this.entity = entity;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public List<TransComboBean> getCombos() {
		return combos;
	}

	public void setCombos(List<TransComboBean> combos) {
		this.combos = combos;
	}

	public int getCopies() {
		return copies;
	}

	public void setCopies(int copies) {
		this.copies = copies;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
