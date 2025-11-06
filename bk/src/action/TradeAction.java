package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcFeeTrade;
import com.qcmz.cmc.service.ITradeService;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public class TradeAction extends BaseAction {
	@Autowired
	private ITradeService tradeService;
	
	private PageBean pageBean;
	
	private CmcFeeTrade entity;
	
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

		tradeService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 详细信息
	 */
	public String doDetail(){
		
		return DETAIL;
	}
	
	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public CmcFeeTrade getEntity() {
		return entity;
	}

	public void setEntity(CmcFeeTrade entity) {
		this.entity = entity;
	}
}
