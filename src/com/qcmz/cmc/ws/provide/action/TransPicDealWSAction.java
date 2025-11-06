package com.qcmz.cmc.ws.provide.action;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.ws.provide.service.OrderDealInterface;
import com.qcmz.cmc.ws.provide.service.TransPicDealInterface;
import com.qcmz.cmc.ws.provide.vo.OrderAcceptReq;
import com.qcmz.cmc.ws.provide.vo.OrderDealMsgAddReq;
import com.qcmz.cmc.ws.provide.vo.OrderDealMsgQueryReq;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryReq;
import com.qcmz.cmc.ws.provide.vo.OrderRejectReq;
import com.qcmz.cmc.ws.provide.vo.TransPicCompleteReq;
import com.qcmz.cmc.ws.provide.vo.TransPicDealGetReq;
import com.qcmz.cmc.ws.provide.vo.TransPicFinishReq;
import com.qcmz.cmc.ws.provide.vo.TransPicRotateReq;
import com.qcmz.cmc.ws.provide.vo.TransPicTransReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：图片翻译处理
 * 修改历史：
 */
public class TransPicDealWSAction extends BaseWSAction {
	@Autowired
	private TransPicDealInterface transPicDealInterface;
	@Autowired
	private OrderDealInterface orderDealInterface;
	
	/**
	 * 操作员用户名
	 */
	private String operator;
	/**
	 * 操作员姓名
	 */
	private String operatorName;
	/**
	 * 图片编号
	 */
	private String picId;
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 排序标识
	 */
	private String moreBaseId;
	/**
	 * 最后一条图片编号
	 */
	private String lastPicId;
	/**
	 * 订单金额
	 */
	private Double amount;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 翻译结果
	 * 多个翻译结果用§;§分隔，每个翻译内容用§|§分隔
	 * 格式为"翻译编号§|§横坐标§|§纵坐标§|§译文§;§翻译编号§|§横坐标§|§纵坐标§|§译文"
	 */
	private String transResult;
	/**
	 * 原因
	 */
	private String reason;
	/**
	 * 处理状态
	 */
	private String dealStatus;
	/**
	 * 处理进度
	 */
	private String dealProgress;
	/**
	 * 语言代码，多个用;分隔
	 */
	private String langs;
	/**
	 * 每页记录数
	 */
	private String pageSize;
	/**
	 * 图片地址
	 */
	private String picUrl;
	/**
	 * 缩略图地址
	 */
	private String thumbUrl;
	/**
	 * 图片文件
	 */
	private File file;
	/**
	 * 缩略图
	 */
	private File thumb;
	/**
	 * 顺时针旋转度数
	 */
	private Integer degree;
	/**
	 * 最后一条留言编号
	 */
	private Long lastMsgId;
	/**
	 * 留言内容
	 */
	private String msg;
	/**
	 * 预计处理时长，秒
	 */
	private Long needTime;
	/**
	 * 分页获取图片翻译列表
	 * @return
	 * 修改历史：
	 */
	public String findPic(){
		OrderDealQueryReq req = new OrderDealQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setOperator(operator);
		req.getBean().setMoreBaseId(lastPicId);
		req.getBean().setDealProgress(dealProgress);
		req.getBean().setLangs(StringUtil.split2List(langs, ";"));
		
		jsonObj = transPicDealInterface.findPic(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取捡漏图片翻译列表
	 * @return
	 * 修改历史：
	 */
	public String findPickPic(){
		OrderDealQueryReq req = new OrderDealQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setOperator(operator);		
		req.getBean().setMoreBaseId(moreBaseId);
		req.getBean().setDealStatus(dealStatus);
		req.getBean().setLangs(StringUtil.split2List(langs, ";"));
		
		jsonObj = transPicDealInterface.findPickPic(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取图片翻译详细信息
	 * @return
	 * 修改历史：
	 */
	public String getPic(){
		TransPicDealGetReq req = new TransPicDealGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setOperator(operator);
		req.setPicId(picId);
		
		jsonObj = transPicDealInterface.getPic(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 开始翻译
	 * OrderDealWSAction.acceptOrder
	 * @return
	 * 修改历史：
	 */
	@Deprecated
	public String startTrans(){
		OrderAcceptReq req = new OrderAcceptReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOrderId(picId);
		req.setOperator(operator);
		req.setOperatorName(operatorName);
		
		jsonObj = orderDealInterface.acceptOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 保存翻译结果
	 * @return
	 * 修改历史：
	 */
	public String saveTrans(){
		TransPicTransReq req = new TransPicTransReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setOrderId(picId);
		req.getBean().setFrom(from);
		req.getBean().setTo(to);
		req.getBean().setTrans(BeanConvertUtil.toTransResult(transResult));
		req.getBean().setPicUrl(picUrl);
		req.getBean().setThumbUrl(thumbUrl);
		req.getBean().setNeedTime(needTime);
		req.getBean().setOperator(operator);
		req.getBean().setOperatorName(operatorName);
		
		jsonObj = transPicDealInterface.saveTrans(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 保存翻译结果
	 * @return
	 * 修改历史：
	 */
	public String savePickTrans(){
		TransPicTransReq req = new TransPicTransReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setOrderId(picId);
		req.getBean().setAmount(amount);
		req.getBean().setTrans(BeanConvertUtil.toTransResult(transResult));
		req.getBean().setPicUrl(picUrl);
		req.getBean().setThumbUrl(thumbUrl);
		req.getBean().setOperator(operator);
		req.getBean().setOperatorName(operatorName);
		
		
		jsonObj = transPicDealInterface.savePickTrans(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 完成翻译
	 * @return
	 * 修改历史：
	 */
	public String completeTrans(){
		TransPicCompleteReq req = new TransPicCompleteReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setOrderId(picId);
		req.getBean().setAmount(amount);
		req.getBean().setPicUrl(picUrl);
		req.getBean().setThumbUrl(thumbUrl);
		req.getBean().setTrans(BeanConvertUtil.toTransResult(transResult));
		req.getBean().setOperator(operator);
		req.getBean().setOperatorName(operatorName);
		
		jsonObj = transPicDealInterface.completeTrans(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 完成翻译订单
	 * @return
	 * 修改历史：
	 */
	public String finishTrans(){
		TransPicFinishReq req = new TransPicFinishReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.getBean().setOperator(operator);
		req.getBean().setOperatorName(operatorName);
		req.getBean().setOrderId(picId);
		req.getBean().setFrom(from);
		req.getBean().setTo(to);
		req.getBean().setTrans(BeanConvertUtil.toTransResult(transResult));
		req.getBean().setPicUrl(picUrl);
		req.getBean().setThumbUrl(thumbUrl);
		
		jsonObj = transPicDealInterface.finishTrans(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 取消翻译
	 * @return
	 * 修改历史：
	 */
	public String cancelTrans(){
		OrderRejectReq req = new OrderRejectReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOrderId(picId);
		req.setReason(reason);
		req.setOperator(operator);
		req.setOperatorName(operatorName);
		
		jsonObj = orderDealInterface.rejectOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 旋转图片
	 * @return
	 * 修改历史：
	 */
	public String rotatePic(){
		TransPicRotateReq req = new TransPicRotateReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setOperator(operator);
		req.setOperatorName(operatorName);
		req.setPicId(picId);
		req.setDegree(degree);
		
		jsonObj = transPicDealInterface.rotatePic(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 添加客服留言
	 * @return
	 * 修改历史：
	 */
	public String addMsg(){
		OrderDealMsgAddReq req = new OrderDealMsgAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOperator(operator);
		req.setOperatorName(operatorName);
		req.setOrderId(picId);
		req.setMsg(msg);
		
		jsonObj = orderDealInterface.addMsg(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取留言列表
	 * @return
	 * 修改历史：
	 */
	public String findMsg(){
		OrderDealMsgQueryReq req = new OrderDealMsgQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setOperator(operator);
		req.setOrderId(picId);
		req.setLastMsgId(lastMsgId);
		
		jsonObj = orderDealInterface.findMsg(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public TransPicDealInterface getTransPicDealInterface() {
		return transPicDealInterface;
	}

	public void setTransPicDealInterface(TransPicDealInterface transPicDealInterface) {
		this.transPicDealInterface = transPicDealInterface;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getMoreBaseId() {
		return moreBaseId;
	}

	public void setMoreBaseId(String moreBaseId) {
		this.moreBaseId = moreBaseId;
	}

	public String getLastPicId() {
		return lastPicId;
	}

	public void setLastPicId(String lastPicId) {
		this.lastPicId = lastPicId;
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

	public String getTransResult() {
		return transResult;
	}

	public void setTransResult(String transResult) {
		this.transResult = transResult;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}

	public String getDealProgress() {
		return dealProgress;
	}

	public void setDealProgress(String dealProgress) {
		this.dealProgress = dealProgress;
	}

	public String getLangs() {
		return langs;
	}

	public void setLangs(String langs) {
		this.langs = langs;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getThumb() {
		return thumb;
	}

	public void setThumb(File thumb) {
		this.thumb = thumb;
	}

	public Integer getDegree() {
		return degree;
	}

	public void setDegree(Integer degree) {
		this.degree = degree;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public Long getLastMsgId() {
		return lastMsgId;
	}

	public void setLastMsgId(Long lastMsgId) {
		this.lastMsgId = lastMsgId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getNeedTime() {
		return needTime;
	}

	public void setNeedTime(Long needTime) {
		this.needTime = needTime;
	}
}
