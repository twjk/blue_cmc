package com.qcmz.cmc.ws.provide.action;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.qcmz.cmc.ws.provide.service.FeeInterface;
import com.qcmz.cmc.ws.provide.vo.AccountReq;
import com.qcmz.cmc.ws.provide.vo.ConsumeAddBean;
import com.qcmz.cmc.ws.provide.vo.ConsumeReq;
import com.qcmz.cmc.ws.provide.vo.ProductQueryReq;
import com.qcmz.cmc.ws.provide.vo.RechargeAddBean;
import com.qcmz.cmc.ws.provide.vo.RechargeReq;
import com.qcmz.cmc.ws.provide.vo.TradeQueryBean;
import com.qcmz.cmc.ws.provide.vo.TradeQueryReq;
import com.qcmz.framework.action.BaseWSAction;
import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：费用接口
 * 修改历史：
 */
public class FeeWSAction extends BaseWSAction {
	@Autowired
	private FeeInterface feeInterface;
	
	/**
	 * 用户编号
	 */
	private Long uid;
	/**
	 * 产品编号
	 */
	private Long pid;
	/**
	 * 产品代码
	 */
	private String pcode;
	/**
	 * 产品数量
	 */
	private Integer num;
	/**
	 * 金额
	 */
	private Double amount;
	/**
	 * 代理编号
	 */
	private Long proxyId;
	/**
	 * 代理流水号
	 */
	private String serial;
	/**
	 * 交易时间
	 */
	private String time;
	/**
	 * 消费项目
	 */
	private String item;
	/**
	 * 语言代码
	 */
	private String langCode;
	/**
	 * 交易类型
	 */
	private String tradeType;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 开始时间
	 */
	private Date begin;
	/**
	 * 结束时间
	 */
	private Date end;
	/**
	 * 当前页数
	 */
	private String page;
	/**
	 * 每页记录数
	 */
	private String pageSize;
	
	/**
	 * 用户帐户信息
	 * @return
	 * 修改历史：
	 */
	public String getAccount(){
		AccountReq req = new AccountReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUserId(uid);
		
		jsonObj = feeInterface.getAccount(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取产品列表
	 * @return
	 * 修改历史：
	 */
	public String findProduct(){
		ProductQueryReq req = new ProductQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		req.setUserId(uid);
		
		jsonObj = feeInterface.findProduct(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 充值
	 * @return
	 * 修改历史：
	 */
	public String recharge(){
		RechargeReq req = new RechargeReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		RechargeAddBean bean = req.getRecharge();
		bean.setUserId(uid);
		bean.setProductId(pid);
		bean.setProductCode(pcode);
		bean.setNum(num);
		bean.setAmount(amount);
		bean.setProxyId(proxyId);
		bean.setSerial(serial);
		bean.setTime(time);
		
		jsonObj = feeInterface.recharge(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 获取消费规则
	 * @return
	 * 修改历史：
	 */
	public String getConsumeRule(){
		Request req = new Request();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		jsonObj = feeInterface.getConsumeRule(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 消费
	 * @return
	 * 修改历史：
	 */
	public String consume(){
		ConsumeReq req = new ConsumeReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		ConsumeAddBean bean = req.getConsume();
		bean.setUserId(uid);
		bean.setItem(item);
		bean.setLangCode(langCode);
		bean.setContent(content);
		bean.setTime(time);
		
		jsonObj = feeInterface.consume(req, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	/**
	 * 交易明细
	 * @return
	 * 修改历史：
	 */
	public String findTrade(){
		TradeQueryReq req = new TradeQueryReq();
		req.setAuthAccount(authAccount);
		req.setAuthToken(authToken);
		req.setAppVer(appVer);
		
		TradeQueryBean bean = req.getSearch();
		bean.setUserId(uid);
		bean.setTradeType(tradeType);
		bean.setBegin(begin);
		bean.setEnd(end);
		
		jsonObj = feeInterface.findTrade(req, page, pageSize, interfaceType, getRemoteAddr());
		
		return JSON;
	}
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getProxyId() {
		return proxyId;
	}

	public void setProxyId(Long proxyId) {
		this.proxyId = proxyId;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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
	
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
