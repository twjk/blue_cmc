package com.qcmz.cmc.process;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcRCombo;
import com.qcmz.cmc.entity.CmcRDub;
import com.qcmz.cmc.entity.CmcRLog;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcRPackage;
import com.qcmz.cmc.entity.CmcRTrade;
import com.qcmz.cmc.proxy.pay.alipay.AlipayProxy;
import com.qcmz.cmc.proxy.pay.applepay.ApplepayProxy;
import com.qcmz.cmc.proxy.pay.wxpay.WxpayProxy;
import com.qcmz.cmc.service.IDubOrderService;
import com.qcmz.cmc.service.ILockService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.IPackageOrderService;
import com.qcmz.cmc.service.ITransComboOrderService;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.thrd.FinishOrderActivityThrd;
import com.qcmz.cmc.thrd.OrderCancelNotifyCsThrd;
import com.qcmz.cmc.thrd.OrderCommissionThrd;
import com.qcmz.cmc.thrd.OrderNewNotifyCsThrd;
import com.qcmz.cmc.thrd.OrderNotifyThrd;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.vo.PrepayBean;
import com.qcmz.cmc.vo.TradeResultBean;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayBean;
import com.qcmz.cmc.ws.provide.vo.PrepayApplepayBean;
import com.qcmz.cmc.ws.provide.vo.PrepayResult;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserCouponWS;

@Component
public class OrderProcess {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private ITransComboOrderService transComboOrderService;
	@Autowired
	private IPackageOrderService packageOrderService;
	@Autowired
	private IDubOrderService dubOrderService;
	
	@Autowired
	private WxpayProxy wxpayProxy;
	@Autowired
	private AlipayProxy alipayProxy;
	
	@Autowired
	private ILockService lockService;
	@Autowired
	private LockProcess lockProcess;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 用户删除订单
	 * 机器翻译：完全删除信息，其他则隐藏订单 
	 * @param picId
	 * @param userId
	 * 修改历史：
	 */
	public void delOrder(String orderId, Long userId){
		CmcROrder order = orderService.getOrder(orderId, userId);
		if(order==null) return;
		
		//删除数据
		if(DictConstant.DICT_ORDER_DEALSTATUS_MACHINE.equals(order.getDealstatus())){
			transPicService.delPic(orderId, OperLogUtil.getUserOperator(userId));
		}else{
			orderService.hideOrder(orderId);
		}
	}
	
	/**
	 * 修改预约时间
	 * @param orderId
	 * @param appointTime
	 * @param oper
	 */
	public void modAppointTime(String orderId, Date appointTime, Operator oper){
		CmcROrder order = orderService.getOrder(orderId);
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		if(!OrderUtil.canModAppointTime(order.getDealprogress())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		if(appointTime.equals(order.getAppointtime())){
			return;
		}
		
		orderService.updateAppointTime(order, appointTime, oper);
	}
	
	/**
	 * 操作员接单
	 * @param orderId
	 * @param oper
	 * @return
	 * @exception DataException
	 */
	public void acceptOrder(String orderId, Operator oper){
		Long lockId = lockProcess.addLock4Unique(DictConstant.DICT_LOCKTYPE_ORDER_ACCEPT, orderId);
		if(lockId==null){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		try {
			CmcROrder order = orderService.getOrder(orderId);
			if(order==null){
				throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
			}
			
			if(!OrderUtil.canAccept(order.getOrdercat(), order.getDealprogress(), order.getWaittime())){
				throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
			}
			
			orderService.acceptOrder(order, oper);
		} finally {
			lockService.deleteLock(lockId);
		}
	}
	
	/**
	 * 取消订单
	 * 系统取消：userId为空
	 * 用户取消：userId不为空
	 * @param orderId
	 * @param userId
	 * @param reason
	 * @exception DataException
	 * 修改历史：
	 */
	public void cancelOrder(String orderId, Long userId, String reason){
		Operator operator = new Operator();		
		if(userId!=null){
			operator.setOperCd(String.valueOf(userId));
			operator.setOperName(SystemConstants.OPERATOR_USER);
		}else{
			operator.setOperCd(SystemConstants.OPERATOR_DEFAULT);
			operator.setOperName(SystemConstants.OPERATOR_DEFAULT);
		}
		
		//取消订单
		CmcROrder order = orderService.cancelOrder(orderId, userId, reason, operator);
		
		//回滚优惠券
		if(order.getCouponamount()>0){
			boolean rollback = UserCouponWS.rollbackCoupon(order.getUserid(), order.getOrderid());
			if(!rollback){
				logger.error("回滚优惠券失败");
			}
		}
		
		//用户取消通知操作员
		if(userId!=null){
			if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(order.getOrdertype())){
				OrderCancelNotifyCsThrd.cancelOrderNotify(order.getOrderid(), order.getOrdertype(), order.getDealprogress(), order.getFromlang(), order.getTolang());
			}
		}
	}

	/**
	 * 取消超时订单
	 */
	public void cancelOrder(){
		logger.info("开始取消超时订单");
		int pageSize = 1;
		String lastId = null;
		String reason = "超时取消";
		
		List<String> orderIds = orderService.findNeedCancel(pageSize, lastId);
		
		while(!orderIds.isEmpty()){
			for (String orderId : orderIds) {
				try {
					lastId = orderId;
					cancelOrder(orderId, null, reason);
				} catch (Exception e) {
					logger.error("超时取消失败："+orderId, e);
				}
			}
			orderIds = orderService.findNeedCancel(pageSize, lastId);
		}
		logger.info("完成取消超时订单");
	}
	
	/**
	 * 支付预处理
	 * @param bean
	 * @return
	 * @exception ProxyException/ParamException
	 */
	public PrepayResult prepay(OrderPrepayBean bean, String platform){
		//校验
		CmcROrder order = orderService.getOrder(bean.getOrderId(), bean.getUid());
		if(order==null){
			throw new ParamException("订单有误");
		}
		double needPay = DoubleUtil.subtract(order.getPayableamount(), order.getPayamount());
		if(needPay!=bean.getAmount()){
			throw new ParamException("金额有误");
		}
		if(!OrderUtil.findTradeWay(order.getOrdercat()).contains(bean.getTradeWay())){
			throw new ParamException("交易途径有误");
		}
		
		//预支付
		PrepayResult preResult = new PrepayResult();
		if(DictConstants.DICT_TRADEWAY_APPLEPAY.equals(bean.getTradeWay())){
			preResult.setApplepay(new PrepayApplepayBean(order.getIapid()));
			return preResult;
		}
		
		String orderTypeDesc = null;
		if(DictConstant.DICT_ORDERTYPE_PACKAGE.equals(order.getOrdertype())){
			CmcRPackage pkg = packageOrderService.getPackage(order.getOrderid());
			orderTypeDesc = pkg.getActtitle();
		}else if(DictConstant.DICT_ORDERTYPE_TRANSCOMBO.equals(order.getOrdertype())){
			CmcRCombo combo = transComboOrderService.getCombo(order.getOrderid());
			orderTypeDesc = combo.getTitle();
		}else if(DictConstant.DICT_ORDERTYPE_DUB.equals(order.getOrdertype())){
			CmcRDub dub = dubOrderService.getDub(order.getOrderid());
			orderTypeDesc = dub.getTitle();
		}else if(DictConstant.DICT_ORDERCAT_CROWDTASK.equals(order.getOrdercat())){
			orderTypeDesc = new StringBuilder("众包业务类型").append(order.getOrdertype()).toString();
		}
		
		if(StringUtil.isBlankOrNull(orderTypeDesc)){
			orderTypeDesc = DictMap.getOrderTypeMean(order.getOrdertype());
		}
		
		PrepayBean preBean = new PrepayBean();
		preBean.setAmount(bean.getAmount());
		preBean.setOrderId(bean.getOrderId());
		preBean.setOrderDesc(OrderUtil.getPayDesc(orderTypeDesc, order.getOrderid()));
		preBean.setOpenid(bean.getOpenid());
		preBean.setPlatform(platform);
		preBean.setServiceType(order.getServicetype());
		preBean.setSubServiceType(DictConstant.getSubServiceType(order.getOrdertype()));
		
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
		CmcROrder order = orderService.getOrder(orderId);
		String subServiceType = DictConstant.getSubServiceType(order.getOrdertype());
		TradeResultBean tradeResult = null;
		if(DictConstants.DICT_TRADEWAY_WXPAY.equals(order.getTradeway())){
			tradeResult = wxpayProxy.queryPay(orderId, order.getServicetype(), subServiceType, order.getPayplatform());
		}else if(DictConstants.DICT_TRADEWAY_ALIPAY.equals(order.getTradeway())){
			tradeResult = alipayProxy.queryPay(orderId, order.getServicetype());
		}else{
			tradeResult = wxpayProxy.queryPay(orderId, order.getServicetype(), subServiceType, order.getPayplatform());
			if(tradeResult==null){
				tradeResult = alipayProxy.queryPay(orderId, order.getServicetype());
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
	 * 到第三方同步退款结果
	 * @param orderId
	 * 修改历史：
	 */
	public void synRefund(String orderId){
		CmcROrder order = orderService.getOrder(orderId);
		TradeResultBean tradeResult = null;
		if(DictConstants.DICT_TRADEWAY_WXPAY.equals(order.getTradeway())){
			String subServiceType = DictConstant.getSubServiceType(order.getOrdertype());
			tradeResult = wxpayProxy.queryRefund(orderId, order.getServicetype(), subServiceType, order.getPayplatform());
		}else if(DictConstants.DICT_TRADEWAY_ALIPAY.equals(order.getTradeway())){
			tradeResult = alipayProxy.queryRefund(orderId, order.getServicetype());
		}
		if(tradeResult!=null){
			saveTradeResult(tradeResult);
		}else{
			throw new ProxyException("查无结果");
		}
	}
	
	/**
	 * 退款，含回滚使用的优惠券
	 * @param orderId
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public boolean refund(String orderId, Operator oper){
		//回滚优惠券
		CmcROrder order = orderService.getOrder(orderId);
		if(order.getCouponamount()>0){
			boolean rollback = UserCouponWS.rollbackCoupon(order.getUserid(), orderId);
			if(!rollback){
				logger.error("回滚优惠券失败："+orderId);
				return false;
			}
		}
		
		//钱包抵扣退款
		if(order.getWalletamount()>0){
			boolean refund = orderService.refundWallet(orderId);
			if(!refund){
				return false;
			}
		}
		
		//退款
		if(order.getPayamount()>0){
			TradeResultBean tradeResult = null;
			boolean refund = false;
			if(DictConstants.DICT_TRADEWAY_WXPAY.equals(order.getTradeway())){
				String subServiceType = DictConstant.getSubServiceType(order.getOrdertype());
				tradeResult = wxpayProxy.refund(orderId, order.getPayamount(), order.getServicetype(), subServiceType, order.getPayplatform());
				refund = saveTradeResult(tradeResult);
			}else if(DictConstants.DICT_TRADEWAY_ALIPAY.equals(order.getTradeway())){
				tradeResult = alipayProxy.refund(orderId, order.getServicetype(), order.getPayamount());
				refund = saveTradeResult(tradeResult);
			}
			if(!refund) return false;
		}
		
		//佣金结算
		order = orderService.getOrder(orderId);
		OrderCommissionThrd.start(order);
		
		return true;
	}
	
	/**
	 * 批量退款处理
	 * 修改历史：
	 */
	public void refund(){
		List<String> orderIds = orderService.findNeedRefund();
		Operator oper = OperLogUtil.getSystemOperator();
		for (String orderId : orderIds) {
			try {
				refund(orderId, oper);
			} catch (Exception e) {
				logger.error("自动退款失败："+orderId, e);
			}
		}
	}
	
	/**
	 * 超时未处理订单自动拒单并退款
	 * 1.超过3天未处理
	 * 2.非iOS翻译订单
	 */
	public void rejectOrderAndRefund(){
		String reason = "感谢您使用出国翻译官。";
		Operator oper = OperLogUtil.getSystemOperator();
		Date now = new Date();
		
		List<CmcROrder> orders = orderService.findWaitDeal(DictConstant.DICT_ORDERCAT_TRANS, DictConstant.DICT_ORDERTYPES_TRANS);
		for (CmcROrder order : orders) {
			if(!DictConstants.DICT_PLATFORM_IOS.equals(order.getPlatform()) && DateUtil.betweenDay(order.getWaittime(), now)>3){
				rejectOrderAndRefund(order.getOrderid(), reason, oper);
			}
		}
	}
	
	/**
	 * 拒单并退款
	 * @param orderId
	 * @param reason
	 * @param oper
	 * @exception DataException
	 * 修改历史：
	 */
	public void rejectOrderAndRefund(String orderId, String reason, Operator oper){
		//拒单
		CmcROrder order = orderService.rejectOrder(orderId, reason, oper);
		
		//通知用户
		OrderNotifyThrd.notifyStatus(order, null, reason);
		
		//第三方退款
		boolean refund = refund(orderId, oper);
		if(!refund){
			logger.error("拒单退款失败："+ orderId);
		}
	}
	
	/**
	 * 保存支付结果
	 * @param tradeResult
	 * 修改历史：
	 */
	public boolean saveTradeResult(TradeResultBean tradeResult){
		CmcROrder order = orderService.getOrder(tradeResult.getOrderId());
		if(order==null) return false;
		
		if(DictConstants.DICT_TRADEWAY_APPLEPAY.equals(tradeResult.getTradeWay())){
			tradeResult.setAmount(order.getPayableamount());
		}
		
		//交易记录
		CmcRTrade trade = new CmcRTrade();
		trade.setOrderid(order.getOrderid());
		trade.setTradetype(tradeResult.getTradeType());
		trade.setTraderesult(tradeResult.getTradeResult());
		trade.setTradestatus(tradeResult.getTradeStatus());
		trade.setOuttradeid(tradeResult.getOutTradeId());
		trade.setTradetime(tradeResult.getTradeTime());
		trade.setAmount(tradeResult.getAmount());

		//1.非交易成功，记录交易结果后退出
		if(!DictConstant.DICT_TRADESTATUS_SUCCESS.equals(tradeResult.getTradeStatus())){
			//保存交易记录
			orderService.saveTrade(trade);
			return true;
		}
		
		//2.交易成功处理		
		if(DictConstants.DICT_TRADETYPE_PAY.equals(tradeResult.getTradeType())
				&& (DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY.equals(order.getDealstatus()) 
						|| DictConstant.DICT_ORDER_DEALSTATUS_TRANSED.equals(order.getDealstatus()))){
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
			
			//组织数据
			Date now = new Date();

			order.setTradeway(tradeResult.getTradeWay());
			order.setPayamount(tradeResult.getAmount());
			order.setPaytime(tradeResult.getTradeTime());
			if(DictConstants.DICT_DEALPROGRESS_DEALT.equals(order.getDealprogress())){
				//先处理(翻译)后支付
				order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_FINISH);
				if(order.getFinishtime()==null){
					order.setFinishtime(now);
				}
				order.setCommissionstatus(SystemConstants.STATUS_OPER_ENABLED);
			}else{
				order.setWaittime(now);
				if(DictConstant.DICT_ORDERCAT_TRANS.equals(order.getOrdercat())){
					order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_WAITTRANS);
					order.setDealprogress(DictConstants.DICT_DEALPROGRESS_WAITING);
				}else if(DictConstant.DICT_ORDERCAT_DUB.equals(order.getOrdercat())){
					if(DictConstant.DICT_ORDERTYPE_DUB.equals(order.getOrdertype())){
						order.setFinishtime(now);
						order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_FINISH);
						order.setDealprogress(DictConstants.DICT_DEALPROGRESS_DEALT);
					}else{
						order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_WAITDUB);
						order.setDealprogress(DictConstants.DICT_DEALPROGRESS_WAITING);
					}
				}else{
					order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_WAITDEAL);
					order.setDealprogress(DictConstants.DICT_DEALPROGRESS_WAITING);
				}
			}

			CmcRLog log = new CmcRLog();
			log.setOrderid(order.getOrderid());
			log.setLogtype(DictConstant.DICT_LOGTYPE_FLOW);
			log.setDealstatus(order.getDealstatus());
			log.setOpercd(String.valueOf(order.getUserid()));
			log.setOpername(SystemConstants.OPERATOR_USER);
			log.setOpertime(tradeResult.getTradeTime());
			
			//入库
			orderService.saveTradeReslt(trade, order, log);
			
			if(DictConstant.DICT_ORDER_DEALSTATUS_FINISH.equals(order.getDealstatus())){
				OrderCommissionThrd.start(order);		//佣金结算
				FinishOrderActivityThrd.start(order);	//参加活动
			}else{
				//图片/文本翻译通知客服
				if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(order.getOrdertype())
						|| DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(order.getOrdertype())
						|| DictConstant.DICT_ORDERTYPE_HUMANDUB.equals(order.getOrdertype())){
					OrderNewNotifyCsThrd.newOrderNotify(order);
				}
			}
		}else if(DictConstants.DICT_TRADETYPE_REFUND.equals(tradeResult.getTradeType())){
			//2.2退款
			//组织数据
			order.setPayamount(order.getPayamount()-tradeResult.getAmount());
			order.setDealprogress(DictConstants.DICT_DEALPROGRESS_DEALT);
			order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_REFUNDED);
			
			CmcRLog log = new CmcRLog();
			log.setOrderid(order.getOrderid());
			log.setLogtype(DictConstant.DICT_LOGTYPE_FLOW);
			log.setDealstatus(order.getDealstatus());
			log.setOpercd(SystemConstants.OPERATOR_DEFAULT);
			log.setOpername(SystemConstants.OPERATOR_DEFAULT);
			log.setOpertime(tradeResult.getTradeTime());
			
			//入库
			orderService.saveTradeReslt(trade, order, log);

			//通知用户
			OrderNotifyThrd.notifyStatus(order);
		}
		
		return true;
	}
	
	/**
	 * 完成订单
	 * @param orderId
	 * @param oper
	 */
	public void finishOrder(String orderId, Operator oper){
		CmcROrder order = orderService.getOrder(orderId);
		//校验能否完成
		if(!OrderUtil.canFinish(order.getDealprogress())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		orderService.saveOrderFinish(orderId, oper.getOperCd(), oper.getOperName());
		
		//通知用户
		OrderNotifyThrd.notifyStatus(order, null, null);
	}
}
