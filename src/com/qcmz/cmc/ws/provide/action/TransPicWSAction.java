package com.qcmz.cmc.ws.provide.action;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.OrderInterface;
import com.qcmz.cmc.ws.provide.service.TransPicInterface;
import com.qcmz.cmc.ws.provide.vo.OrderCancelReq;
import com.qcmz.cmc.ws.provide.vo.OrderDelReq;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.OrderMsgAddReq;
import com.qcmz.cmc.ws.provide.vo.OrderMsgQueryReq;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayReq;
import com.qcmz.cmc.ws.provide.vo.TransPicAddReq;
import com.qcmz.cmc.ws.provide.vo.TransPicLangQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransPicPriceQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransPicQueryReq;
import com.qcmz.cmc.ws.provide.vo.TransPicToHumanReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.umc.constant.DictConstant;

/**
 * 类说明：图片翻译接口
 * 修改历史：
 */
public class TransPicWSAction extends BaseWSAction {
	@Autowired
	private TransPicInterface transPicInterface;
	@Autowired
	private OrderInterface orderInterface;
	
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 用户类型
	 */
	private Integer userType;
	/**
	 * 员工编号
	 */
	private String employeeId;
	/**
	 * 员工姓名
	 */
	private String employeeName;
	/**
	 * 图片编号
	 */
	private String picId;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 最后一条图片编号
	 */
	private String lastPicId;
	/**
	 * 最后一条留言编号
	 */
	private Long lastMsgId;
	/**
	 * 翻译途径，01机器翻译02概要翻译03文档翻译
	 */
	private String transWay;
	/**
	 * 源语言
	 */
	private String from;
	/**
	 * 目标语言
	 */
	private String to;
	/**
	 * 原文
	 */
	private String src;
	/**
	 * 译文
	 */
	private String dst;
	/**
	 * 文件
	 */
	private File file;
	/**
	 * 缩略图
	 */
	private File thumb;
	/**
	 * 用户需求
	 */
	private String requirement;
	/**
	 * 经度
	 */
	private String lon;
	/**
	 * 纬度
	 */
	private String lat;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 金额，元
	 */
	private double amount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 留言内容
	 */
	private String msg;
	/**
	 * 使用的优惠券编号
	 */
	private Long couponId;
	/**
	 * 优惠券面额
	 */
	private double couponAmount;
	/**
	 * 钱包抵扣金额
	 */
	private double walletAmount;
	/**
	 * 原因
	 */
	private String reason;
	
	private String page;
	private String pageSize;
	
	/**
	 * 获取支持的语言列表
	 * @return
	 * 修改历史：
	 */
	public String findLang(){
		TransPicLangQueryReq req = new TransPicLangQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		
		jsonObj = transPicInterface.findLang(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取用户图片翻译列表
	 * @return
	 * 修改历史：
	 */
	public String findPic(){
		TransPicQueryReq req = new TransPicQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setLastPicId(lastPicId);
		
		jsonObj = transPicInterface.findPic(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取图片翻译详细信息
	 * @return
	 * 修改历史：
	 */
	public String getPic(){
		OrderGetReq req = new OrderGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);

		req.setUid(uid);
		req.setOrderId(orderId);
		if(orderId==null && picId!=null){
			req.setOrderId(picId);
		}
		
		
		jsonObj = transPicInterface.getPic(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 添加图片翻译信息
	 * @return
	 * 修改历史：
	 */
	public String addPic(){
		TransPicAddReq req = new TransPicAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setUserType(DictConstant.getUserType(userType));
		req.getBean().setEmployeeId(employeeId);
		req.getBean().setEmployeeName(employeeName);
		req.getBean().setTransWay(transWay);
		req.getBean().setFrom(from);
		req.getBean().setTo(to);
		req.getBean().setSrc(src);
		req.getBean().setFile(file);
		req.getBean().setThumb(thumb);
		req.getBean().setDst(dst);
		req.getBean().setRequirement(requirement);
		req.getBean().setLon(lon);
		req.getBean().setLat(lat);
		req.getBean().setAddress(address);
		req.getBean().setAmount(amount);
		req.getBean().setCouponId(couponId);
		req.getBean().setCouponAmount(couponAmount);
		req.getBean().setWalletAmount(walletAmount);
		req.getBean().setTradeWay(tradeWay);
		
		jsonObj = transPicInterface.addPic(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 转为人工翻译
	 * @return
	 * 修改历史：
	 */
	public String toHuman(){
		TransPicToHumanReq req = new TransPicToHumanReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setPicId(picId);
		req.setRequirement(requirement);
		
		jsonObj = transPicInterface.toHuman(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取价格信息
	 * @return
	 * 修改历史：
	 */
	@Deprecated
	public String getPrice(){
		TransPicPriceQueryReq req = new TransPicPriceQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setLanguage(language);
		req.getBean().setUid(uid);
		
		jsonObj = transPicInterface.getPrice(req, interfaceType, getRemoteAddr());
		
		return JSON;		
	}
	
	/**
	 * 获取价格信息
	 * @return
	 * 修改历史：
	 */
	public String findPrice(){
		TransPicPriceQueryReq req = new TransPicPriceQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setLanguage(language);
		req.getBean().setUid(uid);
		req.getBean().setTransWay(transWay);
		req.getBean().setSrc(src);
		req.getBean().setFrom(from);
		req.getBean().setTo(to);
		
		jsonObj = transPicInterface.findPrice(req, interfaceType, getRemoteAddr());
		
		return JSON;		
	}
	
	/**
	 * 支付预处理
	 * @return
	 * 修改历史：
	 */
	public String prePay(){
		OrderPrepayReq req = new OrderPrepayReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.getBean().setUid(uid);
		req.getBean().setOrderId(picId);
		req.getBean().setAmount(amount);
		req.getBean().setTradeWay(tradeWay);
		
		jsonObj = orderInterface.prepay(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 取消图片翻译
	 * @return
	 * 修改历史：
	 */
	public String cancelPic(){
		OrderCancelReq req = new OrderCancelReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setOrderId(picId);
		req.setReason(reason);
		
		jsonObj = orderInterface.cancelOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 删除图片翻译信息
	 * @return
	 * 修改历史：
	 */
	public String delPic(){
		OrderDelReq req = new OrderDelReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setOrderId(picId);
		
		jsonObj = orderInterface.delOrder(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	/**
	 * 分页获取用户历史图片留言列表
	 * @return
	 * 修改历史：
	 */
	public String findMsg(){
		OrderMsgQueryReq req = new OrderMsgQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setOrderId(picId);
		req.setLastMsgId(lastMsgId);
		
		jsonObj = orderInterface.findMsg(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 分页获取用户新的图片留言列表
	 * @return
	 * 修改历史：
	 */
	public String findLastMsg(){
		OrderMsgQueryReq req = new OrderMsgQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUid(uid);
		req.setOrderId(picId);
		req.setLastMsgId(lastMsgId);
		
		jsonObj = orderInterface.findLastMsg(req, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 添加留言
	 * @return
	 * 修改历史：
	 */
	public String addMsg(){
		OrderMsgAddReq req = new OrderMsgAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setOrderId(picId);
		req.getBean().setMsg(msg);
		
		jsonObj = orderInterface.addMsg(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getPicId() {
		return picId;
	}

	public void setPicId(String picId) {
		this.picId = picId;
	}

	public String getLastPicId() {
		return lastPicId;
	}

	public void setLastPicId(String lastPicId) {
		this.lastPicId = lastPicId;
	}

	public Long getLastMsgId() {
		return lastMsgId;
	}

	public void setLastMsgId(Long lastMsgId) {
		this.lastMsgId = lastMsgId;
	}

	public String getTransWay() {
		return transWay;
	}

	public void setTransWay(String transWay) {
		this.transWay = transWay;
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

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
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

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getTradeWay() {
		return tradeWay;
	}

	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public double getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(double couponAmount) {
		this.couponAmount = couponAmount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(double walletAmount) {
		this.walletAmount = walletAmount;
	}
}
