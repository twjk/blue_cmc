package com.qcmz.cmc.ws.provide.vo;


public class ArticleSubBean extends OrderCreateBean{
	/**
	 * 文章编号
	 */
	private Long articleId;	

	/**
	 * 用户邀请码
	 */
	private String inviteCode;
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
	public Long getArticleId() {
		return articleId;
	}
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
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
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
}
