package com.qcmz.cmc.web.action;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcRpReceive;
import com.qcmz.cmc.service.IRedPacketService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

public class RedPacketAction extends BaseAction {
	@Autowired
	private IRedPacketService redPacketService;
	
	private String packetid;
	
	private List<CmcRpReceive> receiveList;
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

		redPacketService.findPacketJoin(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 获取红包领取列表
	 * @return
	 * 修改历史：
	 */
	public String doReceivedQuery(){
		receiveList = redPacketService.findReceived(packetid);
		return "receivedList";
	}

	public String getPacketid() {
		return packetid;
	}

	public void setPacketid(String packetid) {
		this.packetid = packetid;
	}

	public List<CmcRpReceive> getReceiveList() {
		return receiveList;
	}

	public void setReceiveList(List<CmcRpReceive> receiveList) {
		this.receiveList = receiveList;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
}
