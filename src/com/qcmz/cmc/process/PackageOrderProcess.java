package com.qcmz.cmc.process;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ActivityMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcRPackage;
import com.qcmz.cmc.entity.CmcWalletAccount;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.IPackageOrderService;
import com.qcmz.cmc.service.IWalletAccountService;
import com.qcmz.cmc.thrd.OrderNotifyThrd;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayBean;
import com.qcmz.cmc.ws.provide.vo.PackageOrderAddBean;
import com.qcmz.cmc.ws.provide.vo.PackageOrderExchangedBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.umc.ws.provide.locator.ActivityPackageWS;
import com.qcmz.umc.ws.provide.vo.ActivityBean;
import com.qcmz.umc.ws.provide.vo.ActivityPackageAddResult;
import com.qcmz.umc.ws.provide.vo.ActivityPackageExchangeResult;

@Component
public class PackageOrderProcess {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IWalletAccountService walletAccountService;
	@Autowired
	private IPackageOrderService packageOrderService;
	
	@Autowired
	private ActivityMap activityMap;
	
	@Autowired
	private OrderProcess orderProcess;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 创建优惠券包订单
	 * @param bean
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public OrderCreateResult addPackage(PackageOrderAddBean bean, String serviceType, String platform, String version){
		//1.校验
		//活动校验
		ActivityBean activity = activityMap.getActivity(bean.getActId());
		if(activity==null){
			throw new ParamException("活动不存在");
		}else if(!activity.getPrice().equals(bean.getAmount())){
			throw new ParamException("价格有误");
		}
		
		//抵扣金额校验
		if(bean.getWalletAmount()>0){
			CmcWalletAccount account = walletAccountService.getAccount(bean.getUid());
			if(account==null || bean.getWalletAmount()>account.getBalance()){
				throw new DataException("钱包余额不足");
			}
		}
		
		//2.创建订单
		String orderId = orderService.newOrderId();
		
		//2.1创建优惠券包
		String exchangeCode = null;
		if(DictConstants.DICT_TRADEWAY_POSTPAY.equals(bean.getTradeWay())
				|| bean.getAmount()<=bean.getWalletAmount()){
			ActivityPackageAddResult ap = ActivityPackageWS.addPackage(bean.getActId(), bean.getUid(), orderId, bean.getStartTime());
			if(ap==null || StringUtil.isBlankOrNull(ap.getExchangeCode())){
				throw new SystemException("添加优惠券包失败");
			}
			exchangeCode = ap.getExchangeCode(); 
		}		
		
		try {
			//2.2创建订单
			CmcROrder order = packageOrderService.addPackage(orderId, bean, activity, exchangeCode, serviceType, platform, version);
			OrderCreateResult result = new OrderCreateResult();
			result.setOrderId(order.getOrderid());
			result.setDealStatus(order.getDealstatus());
			result.setExchangeCode(exchangeCode);
			
			//2.3创建订单后，预支付处理，失败返回空，客户端重新发起预支付
			if(DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY.equals(order.getDealstatus())
					&& StringUtil.notBlankAndNull(bean.getTradeWay())){
				try {
					OrderPrepayBean prepayBean = new OrderPrepayBean();
					prepayBean.setOrderId(order.getOrderid());
					prepayBean.setUid(order.getUserid());
					prepayBean.setAmount(order.getPayableamount());
					prepayBean.setTradeWay(bean.getTradeWay());
					result.setPrepayResult(orderProcess.prepay(prepayBean, platform));
				} catch (Exception e) {
					logger.error("预支付处理失败："+orderId, e);
				}
			}
			
			return result;
		} catch (Exception e) {
			if(StringUtil.notBlankAndNull(exchangeCode)){
				ActivityPackageWS.deletePackage(exchangeCode);
			}
			throw new SystemException(e);
		}
	}
	
	/**
	 * 处理优惠券包订单
	 */
	public void dealPackage(){
		List<CmcROrder> orders = orderService.findWaitDeal(DictConstant.DICT_ORDERCAT_PACKAGE, DictConstant.DICT_ORDERTYPE_PACKAGE);
		if(orders.isEmpty()) return;
		
		CmcRPackage pkg = null;
		ActivityPackageAddResult ap = null;
		boolean exchange = false;
		Long startTime = null;
		
		for (CmcROrder order : orders) {
			try {
				if(!order.getDealprogress().equals(DictConstants.DICT_DEALPROGRESS_WAITING)) continue;
				
				pkg = packageOrderService.getPackage(order.getOrderid());
				
				//生成兑换码
				String exchangeCode = pkg.getExchangecode();
				if(StringUtil.isBlankOrNull(exchangeCode)){
					startTime = null;
					if(pkg.getStarttime()!=null){
						startTime = pkg.getStarttime().getTime();
					}
					ap = ActivityPackageWS.addPackage(pkg.getActid(), pkg.getUserid(), pkg.getOrderid(), startTime);
					if(ap==null || StringUtil.isBlankOrNull(ap.getExchangeCode())){
						logger.error("添加优惠套餐失败："+pkg.getOrderid());
						continue;
					}
					exchangeCode = ap.getExchangeCode();
					packageOrderService.updateExchangeCode(pkg.getPkgid(), exchangeCode);
				}
				
				//兑换
				exchange = ActivityPackageWS.exchangePackage(order.getUserid(), exchangeCode);
				if(exchange){
					packageOrderService.saveExchangeSuccess(order.getOrderid(), order.getUserid(), new Date());
				}else if(!exchange){
					//兑换失败主动去查询兑换结果
					ActivityPackageExchangeResult er = ActivityPackageWS.getExchangeResult(exchangeCode);
					if(er!=null && SystemConstants.STATUS_ON.equals(er.getExhcangeStatus())
							&& order.getUserid().equals(er.getExchangeUid())){
						exchange = true;
						packageOrderService.saveExchangeSuccess(order.getOrderid(), er.getExchangeUid(), new Date(er.getExchangeTime()));
					}
				}
				
				if(exchange){
					//获取最新的订单信息
					order = orderService.getOrder(order.getOrderid());
					
					//通知用户
					OrderNotifyThrd.notifyStatus(order, pkg.getActtitle(), null);
				}
				
				if(!exchange){
					logger.error("兑换优惠套餐失败："+pkg.getOrderid());
				}
			} catch (Exception e) {
				logger.error("处理套餐订单失败："+order.getOrderid(), e);
			}
		}
	}
	
	/**
	 * 保存优惠券包兑换信息
	 * @param bean
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public void saveExchanged(PackageOrderExchangedBean bean){
		//1.校验
		//订单
		CmcROrder order = orderService.getOrder(bean.getOrderId());
		if(order==null){
			throw new ParamException("订单有误");
		}
		
		//兑换码
		CmcRPackage pkg = packageOrderService.getPackage(bean.getOrderId());
		if(!pkg.getExchangecode().equals(bean.getExchangeCode())){
			throw new ParamException("兑换码有误");
		}else if(SystemConstants.STATUS_ON.equals(pkg.getExchangestatus())){
			throw new ParamException("兑换码已兑换");
		}		
		
		//2.入库
		pkg.setExchangeuserid(bean.getUid());
		pkg.setExchangestatus(SystemConstants.STATUS_ON);
		pkg.setExchangetime(bean.getExchangeTime()==null?new Date():new Date(bean.getExchangeTime()));
		packageOrderService.updatePackage(pkg);
	}
}
