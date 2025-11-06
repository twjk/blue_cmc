package com.qcmz.cmc.process;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcWalletAccount;
import com.qcmz.cmc.entity.CmcWalletRecharge;
import com.qcmz.cmc.proxy.pay.alipay.AlipayProxy;
import com.qcmz.cmc.proxy.pay.applepay.ApplepayProxy;
import com.qcmz.cmc.proxy.pay.wxpay.WxpayProxy;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.IWalletAccountService;
import com.qcmz.cmc.service.IWalletBillService;
import com.qcmz.cmc.service.IWalletRechargeService;
import com.qcmz.cmc.util.WalletUtil;
import com.qcmz.cmc.vo.PrepayBean;
import com.qcmz.cmc.vo.TradeResultBean;
import com.qcmz.cmc.vo.WalletOfflineRechargeBean;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayBean;
import com.qcmz.cmc.ws.provide.vo.PrepayApplepayBean;
import com.qcmz.cmc.ws.provide.vo.PrepayResult;
import com.qcmz.cmc.ws.provide.vo.WalletEncourageResult;
import com.qcmz.cmc.ws.provide.vo.WalletRechargeCreateBean;
import com.qcmz.cmc.ws.provide.vo.WalletRechargeCreateResult;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * 钱包充值处理
 */
@Component
public class WalletRechargeProcess {
	@Autowired
	private IWalletRechargeService walletRechargeService;
	@Autowired
	private IWalletAccountService walletAccountService;
	@Autowired
	private IWalletBillService walletBillService;
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private WxpayProxy wxpayProxy;
	@Autowired
	private AlipayProxy alipayProxy;
	
	private Logger logger = Logger.getLogger(this.getClass());
		
	/**
	 * 创建充值订单
	 * @param bean
	 * @param platform
	 * @param version
	 * @return
	 */
	public WalletRechargeCreateResult create(WalletRechargeCreateBean bean, String serviceType, String platform, String version){
		//1.数据校验
		if(bean.getAmount()<=0){
			throw new DataException("充值金额有误");
		}
		
		//帐户
		CmcWalletAccount account = walletAccountService.getAccount(bean.getUid());
		if(account==null){
			walletAccountService.saveAccount(bean.getUid(), serviceType);
		}
		
		//2.保存入库
		String orderId = orderService.newOrderId();
		CmcWalletRecharge order = walletRechargeService.createRecharge(orderId, bean, platform, version);
		WalletRechargeCreateResult result = new WalletRechargeCreateResult();
		result.setOrderId(order.getOrderid());
				
		//3.预支付
		if(StringUtil.notBlankAndNull(bean.getTradeWay())){
			try {
				OrderPrepayBean prepayBean = new OrderPrepayBean();
				prepayBean.setOrderId(order.getOrderid());
				prepayBean.setUid(order.getUserid());
				prepayBean.setAmount(order.getPayableamount());
				prepayBean.setTradeWay(bean.getTradeWay());
				prepayBean.setOpenid(bean.getOpenid());
				result.setPrepayResult(prepay(prepayBean, platform));
			} catch (Exception e) {
				logger.error("预支付处理失败："+order.getOrderid(), e);
			}
		}
		
		return result;
	}
	
	/**
	 * 取消充值
	 * @param orderId
	 * @param userId
	 */
	public void cancel(String orderId, Long userId){
		CmcWalletRecharge order = walletRechargeService.getRecharge(orderId, userId);
		if(order==null
				|| !DictConstant.DICT_WALLET_RECHARGESTATUS_WAITPAY.equals(order.getStatus())){
			throw new ParamException("订单有误");
		}
		
		walletRechargeService.cancelRecharge(orderId);
	}
	
	/**
	 * 支付预处理
	 * @param bean
	 * @return
	 * @exception ProxyException/ParamException
	 */
	public PrepayResult prepay(OrderPrepayBean bean, String platform){
		//校验
		CmcWalletRecharge order = walletRechargeService.getRechargeJoinAccount(bean.getOrderId(), bean.getUid());
		if(order==null
				|| !DictConstant.DICT_WALLET_RECHARGESTATUS_WAITPAY.equals(order.getStatus())){
			throw new ParamException("订单有误");
		}
		if(!order.getPayableamount().equals(bean.getAmount())){
			throw new ParamException("金额有误");
		}
		
		//预支付
		PrepayResult preResult = new PrepayResult();
		if(DictConstants.DICT_TRADEWAY_APPLEPAY.equals(bean.getTradeWay())){
			preResult.setApplepay(new PrepayApplepayBean(order.getIapid()));
			return preResult;
		}
		
		PrepayBean preBean = new PrepayBean();
		preBean.setAmount(bean.getAmount());
		preBean.setOrderId(bean.getOrderId());
		preBean.setOrderDesc(WalletUtil.getPayOrderDesc(order.getOrderid()));
		preBean.setOpenid(bean.getOpenid());
		preBean.setPlatform(platform);
		preBean.setServiceType(order.getCmcWalletAccount().getServicetype());
		preBean.setSubServiceType(DictConstants.DICT_SUBSERVICETYPE_RECHARGE);
		
		if(DictConstants.DICT_TRADEWAY_WXPAY.equals(bean.getTradeWay())){
			preResult.setWxpay(wxpayProxy.prePay(preBean));
		}else if(DictConstants.DICT_TRADEWAY_ALIPAY.equals(bean.getTradeWay())){
			preResult.setAlipay(alipayProxy.prePay(preBean));				
		}

		return preResult;
	}
	
	/**
	 * 到第三方同步支付结果
	 * @param orderId
	 * 修改历史：
	 */
	public void synPay(String orderId){
		CmcWalletRecharge order = walletRechargeService.getRechargeJoinAccount(orderId);
		TradeResultBean tradeResult = null;
		if(DictConstants.DICT_TRADEWAY_WXPAY.equals(order.getTradeway())){
			tradeResult = wxpayProxy.queryPay(orderId, order.getCmcWalletAccount().getServicetype(), DictConstants.DICT_SUBSERVICETYPE_RECHARGE, order.getPayplatform());
		}else if(DictConstants.DICT_TRADEWAY_ALIPAY.equals(order.getTradeway())){
			tradeResult = alipayProxy.queryPay(orderId, order.getCmcWalletAccount().getServicetype());
		}else{
			tradeResult = wxpayProxy.queryPay(orderId, order.getCmcWalletAccount().getServicetype(), DictConstants.DICT_SUBSERVICETYPE_RECHARGE, order.getPayplatform());
			if(tradeResult==null){
				tradeResult = alipayProxy.queryPay(orderId, order.getCmcWalletAccount().getServicetype());
			}
		}
		if(tradeResult!=null){
			saveTradeResult(tradeResult);
		}else{
			throw new ProxyException("查无结果");
		}
	}
	
	/**
	 * 处理微信支付异步通知
	 * @param xml
	 * 修改历史：
	 */
	public boolean synWxpay(String xml, String serviceType, String subServiceType, String platform){
		TradeResultBean tradeResult = wxpayProxy.parseSyn(xml, serviceType, subServiceType, platform);
		if(tradeResult!=null){
			return saveTradeResult(tradeResult);
		}
		return false;
	}
	
	/**
	 * 处理支付宝异步通知
	 * @param params
	 * 修改历史：
	 */
	public boolean synAlipay(Map<String, String> params, String serviceType){
		TradeResultBean tradeResult = alipayProxy.parseSyn(params, serviceType);
		if(tradeResult!=null){
			return saveTradeResult(tradeResult);
		}
		return false;
	}
	
	/**
	 * 处理苹果支付异步通知
	 * @param xml
	 * 修改历史：
	 */
	public boolean synApplepay(String orderId, Long userId, String receipt){
		TradeResultBean tradeResult = ApplepayProxy.parseSyn(orderId, userId, receipt);
		if(tradeResult!=null){
			return saveTradeResult(tradeResult);
		}
		return false;
	}
	
	/**
	 * 退款，含回滚使用的优惠券
	 * @param orderId
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public boolean refund(String orderId, Operator oper){
		CmcWalletRecharge order = walletRechargeService.getRechargeJoinAccount(orderId);				
		
		//退款
		if(order.getPayamount()==0){
			return true;
		}
		
		TradeResultBean tradeResult = null;
		if(DictConstants.DICT_TRADEWAY_WXPAY.equals(order.getTradeway())){
			tradeResult = wxpayProxy.refund(orderId, order.getPayamount(), order.getCmcWalletAccount().getServicetype(), DictConstants.DICT_SUBSERVICETYPE_RECHARGE, order.getPayplatform());
			return saveTradeResult(tradeResult);
		}else if(DictConstants.DICT_TRADEWAY_ALIPAY.equals(order.getTradeway())){
			tradeResult = alipayProxy.refund(orderId, order.getCmcWalletAccount().getServicetype(), order.getPayamount());
			return saveTradeResult(tradeResult);
		}
		
		return false;
	}
	
	/**
	 * 保存支付结果
	 * @param tradeResult
	 * 修改历史：
	 */
	public boolean saveTradeResult(TradeResultBean tradeResult){
		CmcWalletRecharge order = walletRechargeService.getRecharge(tradeResult.getOrderId());
		if(order==null) return false;
		
		if(DictConstants.DICT_TRADEWAY_APPLEPAY.equals(tradeResult.getTradeWay())){
			tradeResult.setAmount(order.getPayableamount());
		}
		
		if(!DictConstant.DICT_TRADESTATUS_SUCCESS.equals(tradeResult.getTradeStatus())) return true;
		
		if(DictConstants.DICT_TRADETYPE_PAY.equals(tradeResult.getTradeType())
				&& (DictConstant.DICT_WALLET_RECHARGESTATUS_WAITPAY.equals(order.getStatus())
						|| DictConstant.DICT_WALLET_RECHARGESTATUS_CANCEL.equals(order.getStatus()))){
			//2.1付款
			//金额校验
			if(DictConstants.DICT_TRADEWAY_APPLEPAY.equals(tradeResult.getTradeWay())){
				if(!order.getIapid().equals(tradeResult.getIapId())){
					StringBuilder sbTitle = new StringBuilder().append("商品不匹配：").append(order.getOrderid());
					StringBuilder sbMsg = new StringBuilder(sbTitle)
					.append("，订单商品[").append(order.getIapid()).append("]")
					.append("，苹果商品[").append(tradeResult.getIapId()).append("]")
					;
					MailUtil.sendSimpleMail(SystemConfig.MAILS, null, sbTitle.toString(), sbMsg.toString());
					return false;
				}
			}else{
				if(!order.getPayableamount().equals(tradeResult.getAmount())){
					StringBuilder sbTitle = new StringBuilder().append("支付金额不匹配：").append(order.getOrderid());
					StringBuilder sbMsg = new StringBuilder(sbTitle)
					.append("，支付金额[").append(tradeResult.getAmount()).append("]")
					.append("，应付金额[").append(order.getPayableamount()).append("]")
					;
					MailUtil.sendSimpleMail(SystemConfig.MAILS, null, sbTitle.toString(), sbMsg.toString());
					return false;
				}
			}
			
			//入库
			order.setTradeway(tradeResult.getTradeWay());
			order.setPayamount(tradeResult.getAmount());
			order.setPaytime(tradeResult.getTradeTime());
			order.setPayouttradeid(tradeResult.getOutTradeId());
			order.setStatus(DictConstant.DICT_WALLET_RECHARGESTATUS_PAID);
			walletRechargeService.paySuccess(order);
		}else if(DictConstants.DICT_TRADETYPE_REFUND.equals(tradeResult.getTradeType())){
			//2.2退款
			//组织数据
			order.setPayamount(order.getPayamount()-tradeResult.getAmount());
			order.setRefundtime(tradeResult.getTradeTime());
			order.setRefundouttradeid(tradeResult.getOutTradeId());
			order.setStatus(DictConstant.DICT_WALLET_RECHARGESTATUS_REFUND);
			walletRechargeService.refundSuccess(order);	
		}
		
		return true;
	}
	
	/**
	 * 激励
	 * @param userId
	 * @param type
	 * @param orderId
	 * @return
	 * @exception ParamException、DataException
	 */
	public WalletEncourageResult encourage(Long userId, Integer type, String orderId){
		if(!SystemConfig.WALLET_ENCOURAGE_SWITCH){
			throw new DataException("活动已结束，去看看别的吧");
		}
		
		Double amount = 0.0;
		String desc = null;
		if(DictConstant.DICT_WALLET_ENCOURAGETYPE_VIDEO.equals(type)){
			amount = SystemConfig.WALLET_ENCOURAGE_AMOUNT;
			desc = "看视频";
		}else{
			throw new ParamException("类型有误");
		}
		
		//次数
		if(SystemConfig.WALLET_ENCOURAGE_DATETIMES!=null 
				&& SystemConfig.WALLET_ENCOURAGE_DATETIMES>0){
			Long count = walletBillService.getCount(userId, DictConstant.DICT_WALLET_BILLTYPE_ENCOURAGE, DateUtil.getSysDateOnly());
			if(count>=SystemConfig.WALLET_ENCOURAGE_DATETIMES){
				throw new DataException("今日赚够，明天再战");
			}
		}
		
		Operator operator = new Operator();		
		operator.setOperCd(SystemConstants.OPERATOR_DEFAULT);
		operator.setOperName(SystemConstants.OPERATOR_DEFAULT);
		
		WalletOfflineRechargeBean bean = new WalletOfflineRechargeBean();
		bean.setUserId(userId);
		bean.setAmount(amount);
		bean.setBillType(DictConstant.DICT_WALLET_BILLTYPE_ENCOURAGE);
		bean.setOrderId(orderId);
		bean.setDescription(desc);
		
		saveOfflineRecharge(bean, operator);
		
		WalletEncourageResult result = new WalletEncourageResult();
		result.setAmount(amount);
		return result;
	}
	
	/**
	 * 手工充值
	 * @param bean
	 * @param operator
	 */
	public void saveOfflineRecharge(WalletOfflineRechargeBean bean, Operator operator){
		//获取用户信息
		UserSimpleBean user = null;
		if(bean.getUserId()!=null){
			user = UserMap.getUser(bean.getUserId());
		}else if(StringUtil.notBlankAndNull(bean.getServiceType()) 
				&& StringUtil.notBlankAndNull(bean.getMobile())){
			user = UserMap.getUser(bean.getServiceType(), bean.getMobile());
		}
		if(user==null){
			throw new DataException(ExceptionConstants.MSG_USER_NOTEXIST);
		}else if(!user.isValid()){
			throw new DataException(ExceptionConstants.MSG_USER_INVALID);
		}
		bean.setUserId(user.getUid());
		bean.setServiceType(user.getServiceType());
		
		//钱包帐户
		CmcWalletAccount account = walletAccountService.getAccount(user.getUid());
		if(account==null){
			account = walletAccountService.saveAccount(user.getUid(), user.getServiceType());
		}
		
		//手工充值
		walletRechargeService.offlineRecharge(bean, operator);
	}
}
