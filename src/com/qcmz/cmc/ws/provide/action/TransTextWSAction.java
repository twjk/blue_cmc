package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.TransTextInterface;
import com.qcmz.cmc.ws.provide.vo.OrderGetReq;
import com.qcmz.cmc.ws.provide.vo.TransTextAddReq;
import com.qcmz.cmc.ws.provide.vo.TransTextPriceQueryReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.umc.constant.DictConstant;

/**
 * 短文快译接口
 */
public class TransTextWSAction extends BaseWSAction {
	@Autowired
	private TransTextInterface transTextInterface;
	
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
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 推送注册编号
	 */
	private String pushRegid;
	/**
	 * 翻译模式
	 */
	private Integer transModel;
	/**
	 * 会话标识
	 */
	private String sid;
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
	 * 总金额
	 */
	private double amount;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 获取价格信息
	 * @return
	 * 修改历史：
	 */
	public String findPrice(){
		TransTextPriceQueryReq req = new TransTextPriceQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setLanguage(language);
		
		req.getBean().setSrc(src);
		req.getBean().setFrom(from);
		req.getBean().setTo(to);
		
		jsonObj = transTextInterface.findPrice(req, interfaceType, getRemoteAddr());
		
		return JSON;		
	}
	
	/**
	 * 添加短文快译订单
	 * @return
	 * 修改历史：
	 */
	public String addText(){
		TransTextAddReq req = new TransTextAddReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setLanguage(language);
		
		req.getBean().setUid(uid);
		req.getBean().setUserType(DictConstant.getUserType(userType));
		req.getBean().setEmployeeId(employeeId);
		req.getBean().setEmployeeName(employeeName);
		req.getBean().setPushRegid(pushRegid);
		req.getBean().setTransModel(transModel);
		req.getBean().setSid(sid);
		req.getBean().setFrom(from);
		req.getBean().setTo(to);
		req.getBean().setSrc(src);
		req.getBean().setDst(dst);
		req.getBean().setAmount(amount);
		req.getBean().setCouponId(couponId);
		req.getBean().setCouponAmount(couponAmount);
		req.getBean().setWalletAmount(walletAmount);
		req.getBean().setTradeWay(tradeWay);
		
		jsonObj = transTextInterface.addText(req, interfaceType, getRemoteAddr());
		
		return JSON;		
	}

	/**
	 * 获取短文快译订单详细信息
	 * @return
	 * 修改历史：
	 */
	public String getText(){
		OrderGetReq req = new OrderGetReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.setUid(uid);
		req.setOrderId(orderId);
		
		jsonObj = transTextInterface.getText(req, interfaceType, getRemoteAddr());
		
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

	public String getPushRegid() {
		return pushRegid;
	}

	public void setPushRegid(String pushRegid) {
		this.pushRegid = pushRegid;
	}

	public Integer getTransModel() {
		return transModel;
	}

	public void setTransModel(Integer transModel) {
		this.transModel = transModel;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
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

	public double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(double walletAmount) {
		this.walletAmount = walletAmount;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}