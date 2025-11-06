package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.dao.ITradeDao;
import com.qcmz.cmc.entity.CmcFeeAccount;
import com.qcmz.cmc.entity.CmcFeeProduct;
import com.qcmz.cmc.entity.CmcFeeRule;
import com.qcmz.cmc.entity.CmcFeeRuledetail;
import com.qcmz.cmc.entity.CmcFeeTrade;
import com.qcmz.cmc.service.IAccountService;
import com.qcmz.cmc.service.IProductService;
import com.qcmz.cmc.service.ITradeService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.ws.provide.vo.AccountBean;
import com.qcmz.cmc.ws.provide.vo.ConsumeAddBean;
import com.qcmz.cmc.ws.provide.vo.ConsumeRuleBean;
import com.qcmz.cmc.ws.provide.vo.RechargeAddBean;
import com.qcmz.cmc.ws.provide.vo.TradeBean;
import com.qcmz.cmc.ws.provide.vo.TradeQueryBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class TradeServiceImpl implements ITradeService {
	@Autowired
	private IProductService productService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private ITradeDao tradeDao;
	@Autowired
	private DictMap dictMap;
	
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		tradeDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 分页获取用户交易列表
	 * @param search
	 * @param pageBean
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public void findTradeInfo(TradeQueryBean search, PageBean pageBean){
		tradeDao.findTrade(search, pageBean);
		List<CmcFeeTrade> list = (List<CmcFeeTrade>) pageBean.getResultList();
		List<TradeBean> result = new ArrayList<TradeBean>();
		if(list!=null && !list.isEmpty()){
			TradeBean bean = null;
			for (CmcFeeTrade entity : list) {
				bean = new TradeBean();
				bean.setTradeId(entity.getTradeid());
				bean.setTradeType(entity.getTradetype());
				bean.setTradeTypeDesc(dictMap.getValueMean(DictConstants.DICTNAME_TRADETYPE, entity.getTradetype()));
				bean.setPoint(entity.getPoint());
				bean.setTradeTime(DateUtil.formatDateTime(entity.getTradetime()));
				bean.setDescription(entity.getDescription());
				result.add(bean);
			}
		}
		pageBean.setResultList(result);
	}
	
	/**
	 * 获取交易信息
	 * @param tradeId
	 * @return
	 * 修改历史：
	 */
	public CmcFeeTrade loadTrade(Long tradeId){
		return (CmcFeeTrade) tradeDao.load(CmcFeeTrade.class, tradeId);
	}
	
	/**
	 * 充值
	 * @param bean
	 * @return 帐户信息
	 * 修改历史：
	 */
	public AccountBean recharge(RechargeAddBean bean){
		Date tradeTime = null;
		try {
			tradeTime = DateUtil.parseDateTime(bean.getTime());
		} catch (Exception e) {
			throw new ParamException("充值时间格式有误");
		}

		//检测产品是否存在
		CmcFeeProduct product = productService.loadValidProduct(bean.getProductId(), bean.getProductCode());
		if(product==null){
			throw new ParamException("产品不存在");
		}
		
		//免费产品
		if(product.getPrice()==0){
			if(bean.getNum()!=1){
				throw new ParamException("免费产品领取数量有误");
			}
			if(tradeDao.existTrade(bean.getUserId(), product.getProductid())){
				throw new DataException("已经领取过该免费产品");
			}
		}
		
		//交易记录
		CmcFeeTrade trade = new CmcFeeTrade();
		trade.setUserid(bean.getUserId());
		trade.setTradetype("01");
		trade.setPoint(product.getPoint()*bean.getNum());
		trade.setAmount(bean.getAmount());
		trade.setTradetime(tradeTime);
		trade.setDescription(product.getProductname());
		trade.setProductid(product.getProductid());
		trade.setProductnum(bean.getNum());
		trade.setProxyid(bean.getProxyId());
		trade.setSerial(bean.getSerial());
		
		//账户
		CmcFeeAccount account = accountService.loadAccount(bean.getUserId());
		if(account==null){
			account = new CmcFeeAccount();
			account.setUserid(bean.getUserId());
			account.setPoint(trade.getPoint());
		}else{
			account.setPoint(account.getPoint()+trade.getPoint());
		}
		
		//广告状态
		if(!SystemConstants.STATUS_OFF.equals(account.getAdstatus())){
			if(bean.getAmount()>0){
				account.setAdstatus(SystemConstants.STATUS_OFF);
			}else{
				account.setAdstatus(SystemConstants.STATUS_ON);
			}
		}
		
		accountService.saveOrUpdate(account);
		tradeDao.save(trade);
		
		return BeanConvertUtil.toAccountBean(account);
	}
	
	/**
	 * 获取消费规则
	 * @return
	 * 修改历史：
	 */
	public ConsumeRuleBean getConsumeRule(){
		ConsumeRuleBean result = null;
		List<CmcFeeRule> rules = tradeDao.findValidRule();
		if(!rules.isEmpty()){
			result = new ConsumeRuleBean();
			
			CmcFeeRule rule = rules.get(0);
			result.setDescription(rule.getDescription());
			result.getRules().addAll(tradeDao.findRuleDetailInfo(rule.getRuleid()));
		}
		return result;
	}
	
	/**
	 * 消费
	 * @param bean
	 * @return 帐户信息
	 * 修改历史：
	 */
	public AccountBean consume(ConsumeAddBean bean){
		Date tradeTime = null;
		try {
			tradeTime = DateUtil.parseDateTime(bean.getTime());
		} catch (Exception e) {
			throw new ParamException("消费时间格式有误");
		}
		
		//账户
		CmcFeeAccount account = accountService.loadAccount(bean.getUserId());
		if(account==null){
			throw new ParamException("账户有误");
		}
		
		//获取匹配的交易规则
		CmcFeeRuledetail ruleDetail = null;
		List<CmcFeeRule> rules = tradeDao.findValidRule();
		if(!rules.isEmpty()){
			//匹配细则
			CmcFeeRule rule = rules.get(0);
			List<CmcFeeRuledetail> details = tradeDao.findRuleDetail(rule.getRuleid());
			for (CmcFeeRuledetail detail : details) {
				if(detail.getItem().equals(bean.getItem())
						&& detail.getLangcode().equals(bean.getLangCode())){
					ruleDetail = detail;
					break;
				}
			}
		}
		
		//交易记录
		if(ruleDetail!=null || SystemConfig.FREETRADESAVE_ON){
			CmcFeeTrade trade = new CmcFeeTrade();
			trade.setUserid(bean.getUserId());
			trade.setTradetype("02");
			trade.setTradetime(tradeTime);
			if(ruleDetail!=null){
				trade.setPoint(ruleDetail.getPoint());
			}else{
				trade.setPoint(0);
			}
			trade.setAmount(0D);
			trade.setProxyid(0L);
			trade.setDescription(bean.getContent());
			trade.setItem(bean.getItem());
			trade.setLangcode(bean.getLangCode());
			
			//账户
			if(trade.getPoint()>0){
				account.setPoint(account.getPoint()-trade.getPoint());
				accountService.saveOrUpdate(account);
			}
			
			tradeDao.save(trade);
		}
		
		return BeanConvertUtil.toAccountBean(account);
	}
}
