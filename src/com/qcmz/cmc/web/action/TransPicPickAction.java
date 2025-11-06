package com.qcmz.cmc.web.action;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcPtPic;
import com.qcmz.cmc.process.OrderMsgProcess;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.vo.TransPicIPictureBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealBean;
import com.qcmz.framework.action.BaseAction;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.IPUtil;
import com.qcmz.framework.util.JsonUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.srm.vo.UserBean;

/**
 * 类说明：图片翻译捡漏
 * 修改历史：
 */
public class TransPicPickAction extends BaseAction {
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private OrderMsgProcess orderMsgProcess;
	
	private PageBean pageBean;
	/**
	 * 图片编号
	 */
	private String picId;
	/**
	 * 订单金额
	 */
	private Double amount;
	/**
	 * 图片信息
	 */
	private CmcPtPic entity;
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
	 * 留言
	 */
	private String msg;
	/**
	 * 是否可以开始翻译
	 */
	private boolean canPick;
	/**
	 * 是否可以翻译
	 */
	private boolean canTrans;
	/**
	 * 是否可以修改订单金额
	 */
	private boolean canModAmount;
	/**
	 * 是否可以完成翻译
	 */
	private boolean canCompleteTrans;	
	/**
	 * 是否他人处理的订单
	 */
	private boolean other;
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 顺时针旋转角度
	 */
	private Integer degree;
	
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
		Map<String, String> paramMap = getParameterMap();
		String dealstatus = paramMap.get("dealstatus");
		if(DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY.equals(dealstatus)){
			paramMap.put("endtime", DateUtil.formatDateTime(DateUtil.addMinute(new Date(), -SystemConfig.ORDERPICK_AFTERTIME)));
			paramMap.put("pickstatus", SystemConstants.STATUS_OPER_ENABLED+","+SystemConstants.STATUS_OPER_ALREADY);			
		}else{
			paramMap.put("pickstatus", String.valueOf(SystemConstants.STATUS_OPER_ALREADY));	
		}
		
		transPicService.queryByMapTerm(paramMap, pageBean);
		return LIST;
	}
	
	/**
	 * 编辑
	 */
	public String doEdit(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		entity = transPicService.getPicJoinTransLogMsg(picId);
		
		TransPicIPictureBean ipicture = new TransPicIPictureBean();
		ipicture.setPicture1(BeanConvertUtil.toTransPicIPictureTransBean(entity.getTrans()));
		ipictureJson = JsonUtil.object2String(ipicture);
		
		other = StringUtil.notBlankAndNull(entity.getCmcROrder().getOpercd()) && !oper.getOperCd().equals(entity.getCmcROrder().getOpercd());
		canPick = SystemConstants.STATUS_OPER_ENABLED.equals(entity.getCmcROrder().getPickstatus());
		canTrans = OrderUtil.canPickTrans(entity.getCmcROrder().getPickstatus());
		canCompleteTrans = OrderUtil.canCompleteTrans(entity.getCmcROrder().getDealstatus(), entity.getCmcROrder().getPickstatus());
		canModAmount = canTrans && !DictConstants.DICT_DEALPROGRESS_DEALT.equals(entity.getCmcROrder().getDealprogress());
		return EDIT;
	}
		
	/**
	 * 开始处理
	 */
	public String doStart(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderService.pickOrder(picId, oper);
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
	 * 添加/修改
	 */
	public String doSave(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			TransPicDealBean bean = new TransPicDealBean();
			bean.setOrderId(picId);
			bean.setAmount(amount);
			bean.setTrans(BeanConvertUtil.toTransResult(transResult));
			bean.setOperator(oper.getOperCd());
			bean.setOperatorName(oper.getOperName());
			transPicService.savePickTrans(bean);
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
	 * 翻译完成
	 */
	public String doCompleteTrans(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			TransPicDealBean bean = new TransPicDealBean();
			bean.setOrderId(picId);
			bean.setAmount(amount);
			bean.setTrans(BeanConvertUtil.toTransResult(transResult));
			bean.setOperator(oper.getOperCd());
			bean.setOperatorName(oper.getOperName());
			transPicService.completeTrans(bean);
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
			rotate = transPicService.rotatePicAndSave(picId, degree, oper);
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
	 * 添加留言
	 * @return
	 * 修改历史：
	 */
	public String doAddMsg(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderMsgProcess.addCsMsg(picId, msg, oper);
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

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public CmcPtPic getEntity() {
		return entity;
	}

	public void setEntity(CmcPtPic entity) {
		this.entity = entity;
	}

	public String getIpictureJson() {
		return ipictureJson;
	}

	public void setIpictureJson(String ipictureJson) {
		this.ipictureJson = ipictureJson;
	}

	public boolean isOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}

	public boolean isCanTrans() {
		return canTrans;
	}

	public void setCanTrans(boolean canTrans) {
		this.canTrans = canTrans;
	}
	

	public boolean isCanPick() {
		return canPick;
	}

	public void setCanPick(boolean canPick) {
		this.canPick = canPick;
	}

	public boolean isCanModAmount() {
		return canModAmount;
	}

	public void setCanModAmount(boolean canModAmount) {
		this.canModAmount = canModAmount;
	}

	public boolean isCanCompleteTrans() {
		return canCompleteTrans;
	}

	public void setCanCompleteTrans(boolean canCompleteTrans) {
		this.canCompleteTrans = canCompleteTrans;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
