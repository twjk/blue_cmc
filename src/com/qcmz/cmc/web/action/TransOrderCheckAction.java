package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcPtPic;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.process.OrderCheckProcess;
import com.qcmz.cmc.service.IOrderCheckService;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.service.ITransTextService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.vo.TransPicIPictureBean;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.JsonUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

public class TransOrderCheckAction extends BaseAction {
	@Autowired
	private IOrderCheckService orderCheckService;
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private ITransTextService transTextService;
	
	@Autowired
	private OrderCheckProcess orderCheckProcess; 
	
	/**
	 * 分页
	 */
	private PageBean pageBean;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 短文快译信息
	 */
	private CmcROrder order;
	/**
	 * 图片信息
	 */
	private CmcPtPic pic;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 标注信息json串
	 */
	private String ipictureJson;
	/**
	 * 翻译结果
	 * 多个翻译结果用§;§分隔，每个翻译内容用§|§分隔
	 * 格式为"翻译编号§|§横坐标§|§纵坐标§|§译文§;§翻译编号§|§横坐标§|§纵坐标§|§译文"
	 */
	private String transResult;
	/**
	 * 是否他人处理的订单
	 */
	private boolean other;
	/**
	 * 是否可以开始校对
	 */
	private boolean canStartCheck;
	/**
	 * 是否可以完成校对
	 */
	private boolean canFinishCheck;
	
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

		orderCheckService.findCheck(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 校对
	 */
	public String doEdit(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(orderType)){
			//图片翻译
			pic = transPicService.getPicJoinTransLogMsg(orderId);
			
			order = pic.getCmcROrder();
			
			TransPicIPictureBean ipicture = new TransPicIPictureBean();
			ipicture.setPicture1(BeanConvertUtil.toTransPicIPictureTransBean(pic.getTrans()));
			ipictureJson = JsonUtil.object2String(ipicture);
			
			
			other = StringUtil.notBlankAndNull(order.getCheckcd()) && !oper.getOperCd().equals(order.getCheckcd());
			canStartCheck = OrderUtil.canStartCheck(order.getCheckstatus());
			canFinishCheck = OrderUtil.canFinishCheck(order.getCheckstatus());
			
			return "checkPic";			
		}else if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(orderType)){
			//文本翻译
			order = transTextService.getOrderJoin(orderId);
			
			other = StringUtil.notBlankAndNull(order.getCheckcd()) && !oper.getOperCd().equals(order.getCheckcd());
			canFinishCheck = OrderUtil.canFinishCheck(order.getCheckstatus());
			canStartCheck = OrderUtil.canStartCheck(order.getCheckstatus());
			
			return "checkText";
		}
		
		return null;
	}
	
	/**
	 * 开始处理
	 */
	public String doStartCheck(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderCheckService.startCheck(orderId, oper);
		} catch(DataException ae){
			handleResult = false;
			setResult(ae.getMessage());
		} catch (Exception e) {
			logger.error("开始校对失败："+orderId,e);
			handleResult = false;
			setResult("开始校对失败");
		}
		
		//返回
		print();
		
		return null;
	}
	
	/**
	 * 保存图片翻译校对
	 */
	public String doSaveCheckPic(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderCheckProcess.saveCheck4Pic(orderId, BeanConvertUtil.toTransResult(transResult), from, to, oper);
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
	 * 完成图片翻译校对
	 */
	public String doFinishCheckPic(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderCheckProcess.finishCheck4Pic(orderId, BeanConvertUtil.toTransResult(transResult), from, to, oper);
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
	 * 保存文本翻译校对
	 */
	public String doSaveCheckText(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderCheckProcess.saveCheck4Text(orderId, transResult, from, to, oper);
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
	 * 完成文本翻译校对
	 */
	public String doFinishCheckText(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderCheckProcess.finishCheck4Text(orderId, transResult, from , to, oper);
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

	public CmcROrder getOrder() {
		return order;
	}

	public void setOrder(CmcROrder order) {
		this.order = order;
	}

	public String getIpictureJson() {
		return ipictureJson;
	}

	public void setIpictureJson(String ipictureJson) {
		this.ipictureJson = ipictureJson;
	}

	public String getTransResult() {
		return transResult;
	}

	public void setTransResult(String transResult) {
		this.transResult = transResult;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public CmcPtPic getPic() {
		return pic;
	}

	public void setPic(CmcPtPic pic) {
		this.pic = pic;
	}

	public boolean isOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}

	public boolean isCanStartCheck() {
		return canStartCheck;
	}

	public void setCanStartCheck(boolean canStartCheck) {
		this.canStartCheck = canStartCheck;
	}

	public boolean isCanFinishCheck() {
		return canFinishCheck;
	}

	public void setCanFinishCheck(boolean canFinishCheck) {
		this.canFinishCheck = canFinishCheck;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
