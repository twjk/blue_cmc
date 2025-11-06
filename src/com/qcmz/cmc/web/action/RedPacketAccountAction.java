package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcRpAccount;
import com.qcmz.cmc.service.IRedPacketAccountService;
import com.qcmz.cmc.service.IRedPacketCashService;
import com.qcmz.cmc.service.IRedPacketService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

public class RedPacketAccountAction extends BaseAction {
	@Autowired
	private IRedPacketCashService redPacketCashService;
	@Autowired
	private IRedPacketAccountService redPacketAccountService;
	@Autowired
	private IRedPacketService redPacketService;
	
	private Long id;
	private Long userid;
	private CmcRpAccount entity;

	
	private PageBean pageBean;
	
	public String doInit(){
		return QUERY;
	}
	
	/**
	 * 分页查询红包账户
	 * @return
	 * 修改历史：
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		redPacketAccountService.findAccount(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 获取账户详情
	 * @return
	 * 修改历史：
	 */
	public String doEdit(){
		entity = redPacketAccountService.getAccountJoin(userid);
		return EDIT;
	}
	
	/**
	 * 分页查询用户发红包列表
	 * @return
	 * 修改历史：
	 */
	public String doPacketQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		redPacketService.findPacket(getParameterMap(), pageBean);
		
		return "packetList";
	}
	
	/**
	 * 分页查询用户领取红包记录
	 * @return
	 * 修改历史：
	 */
	public String doReceiveQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		redPacketService.findReceived(getParameterMap(), pageBean);
		
		return "receiveList";
	}
	
	/**
	 * 分页查询用户提现记录
	 * @return
	 * 修改历史：
	 */
	public String doCashQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		redPacketCashService.findCash(getParameterMap(), pageBean);
		
		return "cashList";
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

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}


	public CmcRpAccount getEntity() {
		return entity;
	}

	public void setEntity(CmcRpAccount entity) {
		this.entity = entity;
	}
}
