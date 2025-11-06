package com.qcmz.cmc.ws.provide.webservice;

import com.qcmz.cmc.ws.provide.vo.AccountReq;
import com.qcmz.cmc.ws.provide.vo.AccountResp;
import com.qcmz.cmc.ws.provide.vo.ConsumeReq;
import com.qcmz.cmc.ws.provide.vo.ConsumeResp;
import com.qcmz.cmc.ws.provide.vo.ConsumeRuleResp;
import com.qcmz.cmc.ws.provide.vo.ProductQueryReq;
import com.qcmz.cmc.ws.provide.vo.ProductQueryResp;
import com.qcmz.cmc.ws.provide.vo.RechargeReq;
import com.qcmz.cmc.ws.provide.vo.RechargeResp;
import com.qcmz.cmc.ws.provide.vo.TradeQueryReq;
import com.qcmz.framework.ws.vo.PageBeanResponse;
import com.qcmz.framework.ws.vo.Request;

/**
 * 类说明：费用
 * 修改历史：
 */
public interface IFeeWS {
	/**
	 * 获取帐户信息
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public AccountResp getAccount(AccountReq req);
	/**
	 * 获取产品列表
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public ProductQueryResp findProduct(ProductQueryReq req);
	/**
	 * 充值
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public RechargeResp recharge(RechargeReq req);
	/**
	 * 获取消费规则
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public ConsumeRuleResp getConsumeRule(Request req);
	/**
	 * 消费
	 * @param req
	 * @return
	 * 修改历史：
	 */
	public ConsumeResp consume(ConsumeReq req);
	/**
	 * 分页获取用户交易明细
	 * @param req
	 * @param page
	 * @param pageSize
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public PageBeanResponse findTrade(TradeQueryReq req, String page, String pageSize);
}
