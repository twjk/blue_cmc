package com.qcmz.cmc.ws.provide.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.ArticleOrderInterface;
import com.qcmz.cmc.ws.provide.vo.ArticleSubReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.umc.constant.DictConstant;

public class ArticleOrderWSAction extends BaseWSAction{
	@Autowired
	private ArticleOrderInterface articleOrderInterface;
	
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
	 * 文章编号
	 */
	private Long articleId;
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
	 * 邀请码
	 */
	private String inviteCode;
	/**
	 * 交易途径
	 */
	private String tradeWay;
	/**
	 * 小程序支付用户标识
	 */
	private String openid;
	/**
	 * 商品编号
	 */
	private Long itemId;
	/**
	 * 商品名称
	 */
	private String itemName;
	/**
	 * 商品售价
	 */
	private Double itemPrice;
	
	//订阅资讯
	public String subArticle(){
		ArticleSubReq req = new ArticleSubReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		req.getBean().setUid(uid);
		req.getBean().setUserType(DictConstant.getUserType(userType));
		req.getBean().setEmployeeId(employeeId);
		req.getBean().setEmployeeName(employeeName);
		req.getBean().setArticleId(articleId);
		req.getBean().setAmount(amount);
		req.getBean().setCouponId(couponId);
		req.getBean().setCouponAmount(couponAmount);
		req.getBean().setWalletAmount(walletAmount);
		req.getBean().setInviteCode(inviteCode);
		req.getBean().setTradeWay(tradeWay);
		req.getBean().setOpenid(openid);
		req.getBean().setItemId(itemId);
		req.getBean().setItemName(itemName);
		req.getBean().setItemPrice(itemPrice);
		
		jsonObj = articleOrderInterface.subArticle(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getTradeWay() {
		return tradeWay;
	}

	public void setTradeWay(String tradeWay) {
		this.tradeWay = tradeWay;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public double getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(double walletAmount) {
		this.walletAmount = walletAmount;
	}
}
