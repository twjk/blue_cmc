package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcFeeRule;
import com.qcmz.cmc.entity.CmcFeeRuledetail;
import com.qcmz.cmc.entity.CmcFeeTrade;
import com.qcmz.cmc.ws.provide.vo.ConsumeRuleDetailBean;
import com.qcmz.cmc.ws.provide.vo.TradeQueryBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface ITradeDao extends IBaseDAO {
	/**
	 * 分页获取交易列表
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
	public void findTrade(TradeQueryBean search, PageBean pageBean);
	/**
	 * 获取用户某产品充值记录
	 * @param userId
	 * @param productId
	 * @return
	 * 修改历史：
	 */
	public List<CmcFeeTrade> findTrade(Long userId, Long productId);
	/**
	 * 指定用户是否已经充值过某产品
	 * @param userId
	 * @param productId
	 * @return
	 * 修改历史：
	 */
	public boolean existTrade(Long userId, Long productId);
	/**
	 * 获取用户已经领取的免费产品
	 * @param userId
	 * @return
	 * 修改历史：
	 */
	public List<Long> findFreeProduct(Long userId);
	/**
	 * 获取用户已经充值的非消耗产品
	 * @param userId
	 * @return
	 * 修改历史：
	 */
	public List<Long> findUnconsumableProduct(Long userId);
	/**
	 * 获取所有有效的消费规则列表
	 * @return
	 * 修改历史：
	 */
	public List<CmcFeeRule> findValidRule();
	/**
	 * 获取指定消费规则的细则
	 * @return
	 * 修改历史：
	 */
	public List<CmcFeeRuledetail> findRuleDetail(Long ruleId);
	/**
	 * 获取指定消费规则的细则
	 * @return
	 * 修改历史：
	 */
	public List<ConsumeRuleDetailBean> findRuleDetailInfo(Long ruleId);
}
