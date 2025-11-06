package com.qcmz.cmc.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.entity.CmcPtPic;
import com.qcmz.cmc.process.OrderMsgProcess;
import com.qcmz.cmc.process.OrderProcess;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.vo.TransPicIPictureBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealBean;
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
 * 类说明：图片翻译人工处理
 * 修改历史：
 */
public class TransPicHumanAction extends BaseAction {
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private OrderProcess orderProcess;
	@Autowired
	private OrderMsgProcess orderMsgProcess;
	
	private PageBean pageBean;
	/**
	 * 图片编号
	 */
	private String picId;
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

		transPicService.queryByMapTerm(getParameterMap(), pageBean);
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
		canStart = OrderUtil.canAccept(entity.getCmcROrder().getOrdercat(), entity.getCmcROrder().getDealstatus());
		canTrans = OrderUtil.canTrans(entity.getCmcROrder().getDealstatus());
		canFinish = OrderUtil.canFinish(entity.getCmcROrder().getDealstatus());
		canCancel = OrderUtil.canReject(entity.getCmcROrder().getOrdercat(), entity.getCmcROrder().getDealstatus());
		
		return EDIT;
	}
	
	/**
	 * 取消
	 */
	public String doCancel(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			orderProcess.rejectOrderAndRefund(picId, reason, oper);
		} catch(DataException ae){
			handleResult = false;
			setResult(ae.getMessage());
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
			orderProcess.acceptOrder(picId, oper);
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
			bean.setPicId(picId);
			bean.setTrans(BeanConvertUtil.toTransResult(transResult));
			bean.setOperator(oper.getOperCd());
			bean.setOperatorName(oper.getOperName());
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
	public String doFinish(){
		UserBean user = (UserBean) request.getSession().getAttribute(SystemConstants.SESSION_USER);	
		Operator oper = new Operator(user.getUsername(), user.getName(), IPUtil.getRemoteAddr(request));
		
		try {
			TransPicDealBean bean = new TransPicDealBean();
			bean.setPicId(picId);
			bean.setTrans(BeanConvertUtil.toTransResult(transResult));
			bean.setOperator(oper.getOperCd());
			bean.setOperatorName(oper.getOperName());
			transPicService.finishTrans(bean);
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
