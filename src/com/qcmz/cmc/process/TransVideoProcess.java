package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.EvalMap;
import com.qcmz.cmc.cache.SceneMap;
import com.qcmz.cmc.cache.TransComboMap;
import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcComboLang;
import com.qcmz.cmc.entity.CmcEval;
import com.qcmz.cmc.entity.CmcREval;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcTpPrice;
import com.qcmz.cmc.entity.CmcUCombo;
import com.qcmz.cmc.entity.CmcVtTrans;
import com.qcmz.cmc.entity.CmcWalletAccount;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransVideoService;
import com.qcmz.cmc.service.IUserTransComboService;
import com.qcmz.cmc.service.IWalletAccountService;
import com.qcmz.cmc.thrd.OrderCommissionThrd;
import com.qcmz.cmc.thrd.OrderNewNotifyCsThrd;
import com.qcmz.cmc.thrd.OrderNotifyThrd;
import com.qcmz.cmc.thrd.OrderRegeocodeThrd;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.util.TransComboUtil;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.OrderEvalDetailBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoAddBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoDealBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoDealListBean;
import com.qcmz.cmc.ws.provide.vo.UserTransComboBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.BalanceNotEnoughException;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.umc.ws.provide.locator.UserCouponWS;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * 视频口译处理
 */
@Component
public class TransVideoProcess {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ITransVideoService transVideoService;
	@Autowired
	private IWalletAccountService walletAccountService;
	@Autowired
	private IUserTransComboService userTransComboService;
	
	@Autowired
	private TransComboMap transComboMap;
	@Autowired
	private EvalMap evalMap;
	@Autowired
	private SceneMap sceneMap;
	
	@Autowired
	private TransPriceProcess transPriceProcess;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 分页获取视频口译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<TransVideoDealListBean> findVideo4Deal(OrderDealQueryBean query, int pageSize){
		List<TransVideoDealListBean> list = transVideoService.findVideo4Deal(query, pageSize);
		if(list.isEmpty()) return list;
		
		//价格
		Set<Long> userIds = new HashSet<Long>();
		Set<Long> ucids = new HashSet<Long>();
		List<String> evalOrderIds = new ArrayList<String>();
		CmcTpPrice price = null;
		for (TransVideoDealListBean bean : list) {
			userIds.add(bean.getUid());
			if(bean.getUcid()!=null){
				ucids.add(bean.getUcid());
			}
			if(SystemConstants.STATUS_OPER_ALREADY.equals(bean.getEvalStatus())){
				evalOrderIds.add(bean.getOrderId());
			}
			
			price = transPriceProcess.getTransVideoPrice(bean.getFrom(), bean.getTo());
			if(price!=null){
				bean.setPrice(price.getPrice());
			}
		}
	
		//获取评价信息
		Map<String, OrderEvalDetailBean> orderEvalMap = new HashMap<String, OrderEvalDetailBean>();
		if(!evalOrderIds.isEmpty()){
			List<CmcREval> evals = orderService.findEval(evalOrderIds);
			CmcEval eval = null;
			for (CmcREval cmcREval : evals) {
				eval = evalMap.getEval(cmcREval.getEvalid());
				orderEvalMap.put(cmcREval.getOrderid(), BeanConvertUtil.toOrderEvalDetailBean(cmcREval, eval));
			}
		}
		
		//用户套餐
		Map<Long, UserTransComboBean> userComboMap = new HashMap<Long, UserTransComboBean>();
		if(!ucids.isEmpty()){
			CmcCombo combo = null;
			List<CmcUCombo> ucList = userTransComboService.findUserCombo(ucids);
			for (CmcUCombo uc : ucList) {
				combo = transComboMap.getCombo(uc.getComboid());
				userComboMap.put(uc.getUcid(), BeanConvertUtil.toUserTransComboBean(uc, combo));
			}
		}
		
		//用户
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		
		//封装用户、套餐
		UserSimpleBean userSimpleBean = null;
		UserTransComboBean ucBean = null;
		for (TransVideoDealListBean bean : list) {
			bean.setCanAccept(OrderUtil.canAccept(bean.getOrderCat(), bean.getDealProgress(), bean.getWaitTime()));
			userSimpleBean = userMap.get(bean.getUid());
			if(userSimpleBean!=null){
				bean.setNick(userSimpleBean.getNick());
			}
			if(bean.getUcid()!=null){
				ucBean = userComboMap.get(bean.getUcid());		
				if(ucBean!=null){
					bean.setUcBean(ucBean);
				}
			}
			bean.setEvalBean(orderEvalMap.get(bean.getOrderId()));
			if(bean.getSceneBean()!=null){
				bean.setSceneBean(sceneMap.getSceneBean(bean.getSceneBean().getSceneId()));
			}
		}
		
		return list;
	}
	
	/**
	 * 创建视频口译订单
	 * @param bean
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 * @exception DataException,BalanceNotEnoughException
	 */
	public OrderCreateResult addVideo(TransVideoAddBean bean, String serviceType, String platform, String version){
		//1.校验
		//语言
		CmcTpPrice price = transPriceProcess.getTransVideoPrice(bean.getFrom(), bean.getTo());
		if(price==null){
			throw new DataException("语言不支持");
		}
		
		//工作时间
		String workTime = new StringBuilder(price.getStarttime()).append("-").append(price.getEndtime()).toString();
		workTime = workTime.replaceAll(":", "");
		boolean inWorkTime = false;
		if(bean.getAppointTime()!=null){
			inWorkTime = DateUtil.inTime4(new Date(bean.getAppointTime()), workTime);
		}else{
			inWorkTime = DateUtil.inTime4(workTime); 
		}
		if(!inWorkTime){
			throw new DataException("超出服务时间");
		}

		double couponAmount = 0.0;		
		if(bean.getUcid()!=null){
			//校验套餐
			CmcUCombo uc = userTransComboService.getUserCombo(bean.getUcid());
			if(!TransComboUtil.isValid(uc, bean.getUid(), DictConstants.DICT_TRANSTYPE_VIDEO)){
				throw new DataException("使用的翻译套餐无效");
			}
			CmcCombo combo = transComboMap.getCombo(uc.getComboid());
			if(!combo.getLangs().isEmpty()){
				boolean matchLang = false;
				for (CmcComboLang lang : combo.getLangs()) {
					if(lang.getFromlang().equals(bean.getFrom())
							&& lang.getTolang().equals(bean.getTo())){
						matchLang = true;
						break;
					}
				}
				if(!matchLang){
					throw new DataException("使用的翻译套餐不支持该语言");
				}
			}
		}else{
			//校验帐户余额
			Double minBalance = price.getStartprice();
			if(minBalance==0){
				minBalance = price.getPrice();
			}
			
			CmcWalletAccount account = walletAccountService.getAccount(bean.getUid());
			if(account==null){
				account = walletAccountService.saveAccount(bean.getUid(), serviceType);
			}
			
			if(bean.getCouponId()!=null){
				couponAmount = bean.getCouponAmount();			
			}		
			if(couponAmount<minBalance){
				if((account.getBalance()+couponAmount)<minBalance){
					throw new BalanceNotEnoughException();
				}
			}
		}
		
		String orderId = orderService.newOrderId();
		
		//使用优惠券
		boolean useCoupon = false;
		if(couponAmount>0){
			Response resp = UserCouponWS.useCoupon(bean.getUid(), DictConstants.DICT_SUBSERVICETYPE_TRANSVIDEO, orderId, bean.getCouponId(), bean.getCouponAmount(), 0.0);
			if(resp.successed()){
				useCoupon = true;
			}else{
				if(resp.dataError()){
					throw new DataException(resp.getRespMsg());
				}else{
					throw new ParamException("使用优惠券失败");
				}
			}
		}
		
		//2.创建订单
		try {
			//房间号处理
			if(StringUtil.isBlankOrNull(bean.getRoomId())){
				bean.setRoomId(StringUtil.right(orderId, 6));
			}
			//创建订单
			CmcROrder order = transVideoService.addTransVideo(orderId, bean, serviceType, platform, version);
			
			//通知客服
			OrderNewNotifyCsThrd.newOrderNotify(order);
			
			//经纬度->地址
			if(StringUtil.isBlankOrNull(bean.getAddress())
					&& StringUtil.notBlankAndNull(bean.getLon())
					&& StringUtil.notBlankAndNull(bean.getLat())){
				OrderRegeocodeThrd.start(orderId, bean.getLon(), bean.getLat());
			}
			
			OrderCreateResult result = new OrderCreateResult();
			result.setOrderId(order.getOrderid());
			result.setDealStatus(order.getDealstatus());
			result.setRoomId(bean.getRoomId());
			
			return result;
		} catch (Exception e) {
			//回滚优惠券处理
			if(useCoupon){
				boolean rollback = UserCouponWS.rollbackCoupon(bean.getUid(), orderId);
				if(!rollback){
					logger.error("回滚优惠券失败："+orderId);
				}
			}
			logger.error("创建视频口译订单失败：", e);
			throw new SystemException();
		}
	}
	
	/**
	 * 完成翻译
	 * @param dealBean
	 * @exception DataException
	 */
	public void finishTrans(TransVideoDealBean dealBean){
		CmcROrder order = orderService.getOrder(dealBean.getOrderId());
		
		//校验
		if(!OrderUtil.canFinish(order.getDealprogress())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		CmcVtTrans trans = transVideoService.getTrans(dealBean.getOrderId());
		
		CmcTpPrice price = transPriceProcess.getTransVideoPrice(trans.getFromlang(), trans.getTolang());
		if(price==null){
			throw new DataException("价格有误");
		}
		
		//完成真人口译相关信息入库
		transVideoService.finishTrans(order, trans, dealBean, price);
		
		//获取最新的订单信息
		order = orderService.getOrder(dealBean.getOrderId());
		
		//佣金结算
		OrderCommissionThrd.start(order);
		
		//通知用户
		double pay = DoubleUtil.add(order.getWalletamount(), order.getPayamount());
		if(pay>0){
			String content = StringUtil.right(order.getOrderid(), 4)+"|"+String.valueOf(pay);
			OrderNotifyThrd.notifyStatus(order, content, null);
		}
	}
}
