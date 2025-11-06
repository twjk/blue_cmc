package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.process.OrderMsgProcess;
import com.qcmz.cmc.process.OrderProcess;
import com.qcmz.cmc.service.ITransTextService;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.ws.provide.vo.TransTextDealBean;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

/**
 * 类说明：图片翻译人工处理
 * 修改历史：
 */
public class TransTextHumanAction extends BaseAction {
	@Autowired
	private ITransTextService transTextService;
	
	@Autowired
	private OrderProcess orderProcess;
	@Autowired
	private OrderMsgProcess orderMsgProcess;
	
	private PageBean pageBean;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 短文快译信息
	 */
	private CmcROrder entity;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 留言
	 */
	private String msg;
	/**
	 * 是否可以开始翻译
	 */
	private boolean canStart;
	/**
	 * 是否可以翻译
	 */
	private boolean canTrans;
	/**
	 * 是否可以完成
	 */
	private boolean canFinish;
	/**
	 * 是否可以取消
	 */
	private boolean canCancel;
	/**
	 * 是否他人处理的订单
	 */
	private boolean other;
	/**
	 * 原因
	 */
	private String reason;
	
	/**
	 * 页面初始化
	 */
	public String doInit(){
		return QUERY;
	}
	
	/**
	 * 查询
	 * @return
	 * 修改历史：
	 */
	public String doQuery(){
		pageBean = new PageBean(request.getParameter("page"), request.getParameter("pageSize"));

		transTextService.queryByMapTerm(getParameterMap(), pageBean);
		
		return LIST;
	}
	
	/**
	 * 编辑
	 */
	public String doEdit(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		entity = transTextService.getOrderJoin(orderId);
		
		other = StringUtil.notBlankAndNull(entity.getOpercd()) && !oper.getOperCd().equals(entity.getOpercd());
		canStart = OrderUtil.canAccept(entity.getOrdercat(), entity.getDealstatus());
		canTrans = OrderUtil.canTrans(entity.getDealstatus());
		canFinish = OrderUtil.canFinish(entity.getDealstatus());
		canCancel = OrderUtil.canReject(entity.getOrdercat(), entity.getDealstatus());

		return EDIT;
	}
	
	/**
	 * 取消
	 */
	public String doCancel(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderProcess.rejectOrderAndRefund(orderId, reason, oper);
		} catch(DataException e){
			handleResult = false;
			setResult(e.getMessage());
		} catch (Exception e) {
			logger.error("取消失败",e);
			handleResult = false;
			setResult("取消失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	/**
	 * 开始处理
	 */
	public String doStart(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderProcess.acceptOrder(orderId, oper);
		} catch(DataException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch (Exception e) {
			logger.error("开始翻译失败",e);
			handleResult = false;
			setResult("开始翻译失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	/**
	 * 保存翻译结果
	 */
	public String doSave(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			TransTextDealBean bean = new TransTextDealBean();
			bean.setOrderId(orderId);
			bean.setDst(dst);
			bean.setOperator(oper.getOperCd());
			bean.setOperatorName(oper.getOperName());
			
			transTextService.saveTrans(bean);
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
	 * 完成翻译
	 */
	public String doFinish(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			TransTextDealBean bean = new TransTextDealBean();
			bean.setOrderId(orderId);
			bean.setDst(dst);
			bean.setOperator(oper.getOperCd());
			bean.setOperatorName(oper.getOperName());
			
			transTextService.finishTrans(bean);
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
	
	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public CmcROrder getEntity() {
		return entity;
	}

	public void setEntity(CmcROrder entity) {
		this.entity = entity;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public boolean isOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}

	public boolean isCanStart() {
		return canStart;
	}

	public void setCanStart(boolean canStart) {
		this.canStart = canStart;
	}

	public boolean isCanTrans() {
		return canTrans;
	}

	public void setCanTrans(boolean canTrans) {
		this.canTrans = canTrans;
	}

	public boolean isCanFinish() {
		return canFinish;
	}

	public void setCanFinish(boolean canFinish) {
		this.canFinish = canFinish;
	}

	public boolean isCanCancel() {
		return canCancel;
	}

	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
