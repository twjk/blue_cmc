package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.process.TransPicProcess;
import com.qcmz.cmc.process.TransTextProcess;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.service.ITransTextService;
import com.qcmz.cmc.service.ITransVideoService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.vo.TransPicIPictureBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealBean;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.JsonUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

/**
 * 翻译订单处理
 */
public class TransOrderDealAction extends BaseAction {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ITransTextService transTextService;
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private ITransVideoService transVideoService;
	
	@Autowired
	private TransTextProcess transTextProcess;
	@Autowired
	private TransPicProcess transPicProcess;
	
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 订单类型
	 */
	private Integer orderType;
	/**
	 * 订单信息
	 */
	private CmcROrder order;
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
	 * 图片翻译结果
	 * 多个翻译结果用§;§分隔，每个翻译内容用§|§分隔
	 * 格式为"翻译编号§|§横坐标§|§纵坐标§|§译文§;§翻译编号§|§横坐标§|§纵坐标§|§译文"
	 */
	private String transResult;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 译员用户名
	 */
	private String transCd;
	/**
	 * 译员姓名
	 */
	private String transName;
	/**
	 * 图片顺时针旋转角度
	 */
	private Integer degree;
	
	/**
	 * 是否可以开始翻译
	 */
	private boolean canStart;
	/**
	 * 是否可以翻译
	 */
	private boolean canTrans;
	/**
	 * 是否可以交单
	 */
	private boolean canHandover;
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
	 * 预计处理时长，秒
	 */
	private Long needTime;
	private PageBean pageBean;
	
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

		orderService.findTransDeal(getParameterMap(), pageBean);
		
		return LIST;
	}

	/**
	 * 处理
	 */
	public String doEdit(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		String result = null;
		
		if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(orderType)){
			order = transPicService.getOrderJoin(orderId);
			
			TransPicIPictureBean ipicture = new TransPicIPictureBean();
			ipicture.setPicture1(BeanConvertUtil.toTransPicIPictureTransBean(order.getCmcPtPic().getTrans()));
			ipictureJson = JsonUtil.object2String(ipicture);
					
			result = "dealPic";			
		}else if(DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(orderType)){
			//文本翻译
			order = transTextService.getOrderJoin(orderId);
			result = "dealText";
		}else if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)){
			order = transVideoService.getOrderJoin(orderId);
			//视频翻译暂无处理，故直接返回
			return "dealVideo";
		}
		
		other = StringUtil.notBlankAndNull(order.getOpercd()) && !oper.getOperCd().equals(order.getOpercd());
		canHandover = OrderUtil.canHandover(order.getOrdercat(), order.getDealprogress());
		canStart = OrderUtil.canAccept(order.getOrdercat(), order.getDealprogress(), order.getWaittime());
		canTrans = OrderUtil.canTrans(order.getDealstatus(), order.getDealprogress());
		canFinish = OrderUtil.canFinish(order.getDealprogress());
		canCancel = OrderUtil.canReject(order.getOrdercat(), order.getDealstatus());
		
		return result;
	}

	/**
	 * 保存短文快译翻译结果
	 */
	public String doSaveText(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			TransTextDealBean bean = new TransTextDealBean();
			bean.setOrderId(orderId);
			bean.setDst(dst);
			bean.setFrom(from);
			bean.setTo(to);
			bean.setNeedTime(needTime);
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
	 * 完成短文快译
	 */
	public String doFinishText(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			TransTextDealBean bean = new TransTextDealBean();
			bean.setOrderId(orderId);
			bean.setDst(dst);
			bean.setFrom(from);
			bean.setTo(to);
			bean.setNeedTime(needTime);
			bean.setOperator(oper.getOperCd());
			bean.setOperatorName(oper.getOperName());
			bean.setNeedTime(needTime);
			transTextProcess.finishTrans(bean);
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
	 * 旋转图片
	 * @return
	 * 修改历史：
	 */
	public String doRotatePic(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		boolean rotate = false;
		try {
			rotate = transPicService.rotatePicAndSave(orderId, degree, oper);
		} catch (Exception e) {
			logger.error("保存信息失败",e);
		}
		
		if(!rotate){
			handleResult = false;
			setResult("保存信息失败");
		}
		
		print();
		
		return null;
	}
	
	/**
	 * 添加/修改
	 */
	public String doSavePic(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			TransPicDealBean bean = new TransPicDealBean();
			bean.setOrderId(orderId);
			bean.setFrom(from);
			bean.setTo(to);
			bean.setTrans(BeanConvertUtil.toTransResult(transResult));
			bean.setOperator(oper.getOperCd());
			bean.setOperatorName(oper.getOperName());
			bean.setNeedTime(needTime);
			transPicService.saveTrans(bean);
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
	 * 添加/修改
	 */
	public String doFinishPic(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			TransPicDealBean bean = new TransPicDealBean();
			bean.setOrderId(orderId);
			bean.setFrom(from);
			bean.setTo(to);
			bean.setTrans(BeanConvertUtil.toTransResult(transResult));
			bean.setOperator(oper.getOperCd());
			bean.setOperatorName(oper.getOperName());
			bean.setNeedTime(needTime);
			transPicProcess.finishTrans(bean);
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

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public boolean isCanStart() {
		return canStart;
	}

	public void setCanStart(boolean canStart) {
		this.canStart = canStart;
	}

	public boolean isCanHandover() {
		return canHandover;
	}

	public void setCanHandover(boolean canHandover) {
		this.canHandover = canHandover;
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

	public boolean isOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public String getTransResult() {
		return transResult;
	}

	public void setTransResult(String transResult) {
		this.transResult = transResult;
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

	public String getTransCd() {
		return transCd;
	}

	public void setTransCd(String transCd) {
		this.transCd = transCd;
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public Long getNeedTime() {
		return needTime;
	}

	public void setNeedTime(Long needTime) {
		this.needTime = needTime;
	}
}
