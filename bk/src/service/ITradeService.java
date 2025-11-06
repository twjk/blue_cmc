package com.qcmz.cmc.service;

import java.util.Map;

import com.qcmz.cmc.ws.provide.vo.AccountBean;
import com.qcmz.cmc.ws.provide.vo.ConsumeAddBean;
import com.qcmz.cmc.ws.provide.vo.ConsumeRuleBean;
import com.qcmz.cmc.ws.provide.vo.RechargeAddBean;
import com.qcmz.cmc.ws.provide.vo.TradeQueryBean;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface ITradeService {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取用户交易列表
	 * @param search
	 * @param pageBean
	 * 修改历史：
	 */
	public void findTradeInfo(TradeQueryBean search, PageBean pageBean);
	/**
	 * 充值
	 * @param bean
	 * @return 帐户信息
	 * 修改历史：
	 */
	public AccountBean recharge(RechargeAddBean bean);
	/**
	 * 获取消费规则
	 * @return
	 * 修改历史：
	 */
	public ConsumeRuleBean getConsumeRule();
	/**
	 * 消费
	 * @param bean
	 * @return 帐户信息
	 * 修改历史：
	 */
	public AccountBean consume(ConsumeAddBean bean);
}
