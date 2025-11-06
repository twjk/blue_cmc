package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.bdc.ws.provide.locator.MsgPushThrd;
import com.qcmz.bdc.ws.provide.locator.MsgPushWS;
import com.qcmz.cmc.cache.EvalMap;
import com.qcmz.cmc.cache.TransTextLangMap;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcEval;
import com.qcmz.cmc.entity.CmcREval;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcTtTrans;
import com.qcmz.cmc.entity.CmcWalletAccount;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransTextService;
import com.qcmz.cmc.service.IWalletAccountService;
import com.qcmz.cmc.thrd.FinishOrderActivityThrd;
import com.qcmz.cmc.thrd.OrderCheckNotifyCsThrd;
import com.qcmz.cmc.thrd.OrderCommissionThrd;
import com.qcmz.cmc.thrd.OrderNotifyThrd;
import com.qcmz.cmc.thrd.TransTextGetSrcVoiceThrd;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.OrderEvalDetailBean;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayBean;
import com.qcmz.cmc.ws.provide.vo.TransBean;
import com.qcmz.cmc.ws.provide.vo.TransPriceBean;
import com.qcmz.cmc.ws.provide.vo.TransTextAddBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealListBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.util.ArrayUtil;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.umc.ws.provide.locator.UserCouponWS;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * 短文快译订单处理
 */
@Component
public class TransTextProcess {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ITransTextService transTextService;
	@Autowired
	private IWalletAccountService walletAccountService;
	
	@Autowired
	private TransTextLangMap transTextLangMap;
	@Autowired
	private EvalMap evalMap;
	
	@Autowired
	private TransPriceProcess transPriceProcess;
	@Autowired
	private OrderProcess orderProcess;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 分页获取短文快译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<TransTextDealListBean> findText4Deal(OrderDealQueryBean query, int pageSize){
		List<TransTextDealListBean> result = transTextService.findText4Deal(query, pageSize);
		if(result.isEmpty()) return result;
		
		Set<Long> userIds = new HashSet<Long>();
		List<String> evalOrderIds = new ArrayList<String>();
		for (TransTextDealListBean bean : result) {
			userIds.add(bean.getUid());
			if(SystemConstants.STATUS_OPER_ALREADY.equals(bean.getEvalStatus())){
				evalOrderIds.add(bean.getOrderId());
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
				
		//获取用户信息
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		
		//组装数据
		UserSimpleBean userBean = null;
		for (TransTextDealListBean bean : result) {
			bean.setCanAccept(OrderUtil.canAccept(bean.getOrderCat(), bean.getDealProgress(), bean.getWaitTime()));
			userBean = userMap.get(bean.getUid());
			if(userBean!=null){
				bean.setNick(userBean.getNick());
			}
			bean.setEvalBean(orderEvalMap.get(bean.getOrderId()));
		}
		
		return result;
	}
	
	/**
	 * 创建短文快译订单
	 * @param transBean
	 * @param dst
	 * @param similar
	 * @param platform
	 * @param version
	 * @return
	 */
	public OrderCreateResult addTextFromTrans(TransBean transBean, String dst, int similar, String serviceType, String platform, String version){
		//业务
		if(!DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
			return null;
		}
		//翻译模式
		if(transBean.getTransModel()==null || DictConstant.DICT_TRANSMODEL_FREE.equals(transBean.getTransModel())){
			return null;
		}
		//渠道
		if(StringUtil.isBlankOrNull(transBean.getChannel())){
			return null;
		}else{
			if(TransConfig.TRANSTEXT_CHANNELS.length>0
					&& !ArrayUtil.contain(TransConfig.TRANSTEXT_CHANNELS, transBean.getChannel())){
				return null;
			}
		}
		
		if(transBean.getUid()==null || StringUtil.isBlankOrNull(transBean.getPushRegid()) || StringUtil.isBlankOrNull(transBean.getSid())){
			return null;
		}
		
		TransTextAddBean bean = new TransTextAddBean();
		bean.setUid(transBean.getUid());
		bean.setPushRegid(transBean.getPushRegid());
		bean.setTransModel(transBean.getTransModel());
		bean.setSid(transBean.getSid());
		bean.setFrom(transBean.getFrom());
		bean.setTo(transBean.getTo());
		bean.setSrc(transBean.getSrc());
		bean.setDst(dst);
		
		//创建订单
		try {
			return addText(bean, similar, serviceType, platform, version);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 创建短文快译订单
	 * @param bean
	 * @param platform
	 * @param version
	 * @return
	 */
	public OrderCreateResult addText(TransTextAddBean bean, int similar, String serviceType, String platform, String version){
		//1.校验
		//开关
		if(!TransConfig.TRANSTEXT_ON){
			throw new DataException("功能不可用");
		};
		//工作时间
		if(!DateUtil.inTime4(TransConfig.TRANSTEXT_WORKTIME_FORMAT)){
			throw new DataException("不在工作时间内");
		}
		//语言校验
		if(!transTextLangMap.contains(bean.getFrom(), bean.getTo())){
			throw new DataException("语言不支持");
		}
		//字词数
		if(TransConfig.TRANSTEXT_MAXWORDNUM!=-1){
			int wordNum = StringUtil.countWord(bean.getSrc());
			if(wordNum>TransConfig.TRANSTEXT_MAXWORDNUM){
				throw new DataException("字数超了");
			}
		}
		
		//价格校验
		TransPriceBean price = transPriceProcess.getTransPrice(DictConstants.DICT_TRANSTYPE_TEXT, DictConstant.DICT_TRANSWAY_DETAIL
				, bean.getSrc(), bean.getFrom(), bean.getTo(), null);
		if(price==null
				|| !price.getTotalPrice().equals(bean.getAmount())){
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
		
		//2.1使用优惠券
		boolean useCoupon = false;
		if(bean.getCouponId()!=null && bean.getCouponAmount()>0){
			Response resp = UserCouponWS.useCoupon(bean.getUid(), DictConstants.DICT_SUBSERVICETYPE_TRANSTEXT, orderId, bean.getCouponId(), bean.getCouponAmount(), bean.getAmount());
			if(resp.successed()){
				useCoupon = true;
			}else{
				if(resp.dataError()){
					throw new ParamException(resp.getRespMsg());
				}else{
					throw new ParamException("使用优惠券失败");
				}
			}
		}
		
		try {
			//2.2创建订单
			CmcROrder order = transTextService.addTransText(orderId, bean, price, similar, serviceType, platform, version);
			
			OrderCreateResult result = new OrderCreateResult();
			result.setOrderId(order.getOrderid());
			result.setDealStatus(order.getDealstatus());

			//语音
			TransTextGetSrcVoiceThrd.start(orderId, bean.getSid());
			
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
			//回滚优惠券处理
			if(useCoupon){
				boolean rollback = UserCouponWS.rollbackCoupon(bean.getUid(), orderId);
				if(!rollback){
					logger.error("回滚优惠券失败："+orderId);
				}
			}
			logger.error("创建短文快译订单失败：", e);
			throw new SystemException();
		}
	}
	
	/**
	 * 完成文本翻译
	 * @param dealBean
	 */
	public void finishTrans(TransTextDealBean dealBean){
		CmcROrder order = orderService.getOrder(dealBean.getOrderId());
		//校验能否完成
		if(!OrderUtil.canFinish(order.getDealprogress())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		//完成短文快译相关信息入库
		transTextService.finishTrans(dealBean, order.getDealstatus());
		
		//获取最新的订单信息
		order = orderService.getOrder(dealBean.getOrderId());
		CmcTtTrans trans = transTextService.getTrans(dealBean.getOrderId());
		
		//佣金结算
		OrderCommissionThrd.start(order);
		
		//参加活动
		FinishOrderActivityThrd.start(order);
		
		//通知用户
		OrderNotifyThrd.notifyStatus(order, null, null);
				
		//通知修正对话翻译列表翻译结果
		if(StringUtil.notBlankAndNull(trans.getPushregid())
				&& StringUtil.notBlankAndNull(trans.getSessionid())){
			String extra = new StringBuilder("from=").append(order.getFromlang()).append(";to=").append(order.getTolang()).toString();
			MsgPushThrd.pushMsg(1023L, MsgPushWS.PUSHTOTYPE_REGID, trans.getPushregid(), trans.getDst(), trans.getSessionid(), extra, null, null);
		}
		
		//通知校对组校对
		if(DictConstants.DICT_CHECKSTATUS_UNCHECK.equals(order.getCheckstatus())){
			OrderCheckNotifyCsThrd.newCheckNotify(order.getOrderid(), order.getOrdertype(), order.getFromlang(), order.getTolang());
		}
	}
}
