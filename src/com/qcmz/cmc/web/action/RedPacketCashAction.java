package com.qcmz.cmc.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcRpCash;
import com.qcmz.cmc.process.RedPacketProcess;
import com.qcmz.cmc.service.IRedPacketCashService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

public class RedPacketCashAction extends BaseAction {
	@Autowired
	private IRedPacketCashService redPacketCashService;
	@Autowired
	private RedPacketProcess redPacketProcess;
	
	private Long id;
	private CmcRpCash entity;
	private String reason;

	private List<CmcRpCash> cashList;
	private PageBean pageBean;
	
	public String doInit(){
		return QUERY;
	}
	
	/**
	 * 分页查询
	 * @return
	 * 修改历史：
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		redPacketCashService.findCash(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 详细信息
	 * @return
	 * 修改历史：
	 */
	public String doEdit(){
		entity = redPacketCashService.getCashJoin(id);
		cashList = redPacketCashService.findCash(entity.getUserid());
		return EDIT;
	}
	
	/**
	 * 审核通过提现申请
	 */
	public String doTransfer(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			redPacketProcess.transfer(id, oper);
		} catch(ProxyException e){
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			logger.error("审核通过失败",e);
			handleResult = false;
			setResult("审核通过失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 驳回提现申请
	 */
	public String doRejectCash(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			redPacketProcess.rejectCash(id, reason, oper);
		} catch (Exception e) {
			logger.error("驳回提现申请失败",e);
			handleResult = false;
			setResult("驳回提现申请失败");
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

	public CmcRpCash getEntity() {
		return entity;
	}

	public void setEntity(CmcRpCash entity) {
		this.entity = entity;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<CmcRpCash> getCashList() {
		return cashList;
	}

	public void setCashList(List<CmcRpCash> cashList) {
		this.cashList = cashList;
	}
}
