package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ProductMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcDubber;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.IDubOrderService;
import com.qcmz.cmc.service.IDubberService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.ws.provide.vo.DubAddBean;
import com.qcmz.cmc.ws.provide.vo.DubberBean;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayBean;
import com.qcmz.cmc.ws.provide.vo.ProductBean;
import com.qcmz.cmc.ws.provide.vo.ProductItemBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 配音订单处理 
 */
@Component
public class DubOrderProcess {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IDubOrderService dubOrderService;
	@Autowired
	private IDubberService dubberService;
	
	@Autowired
	private ProductMap productMap;	
	@Autowired
	private OrderProcess orderProcess;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 获取配音员列表
	 * @param catId
	 * @return
	 */
	public List<DubberBean> findDubber(Long catId){
		List<DubberBean> result = new ArrayList<DubberBean>();
		DubberBean bean = null;
		ProductItemBean itemBean = null;
		
		List<CmcDubber> dubberList = dubberService.findValidDubber(catId);
		for (CmcDubber cmcDubber : dubberList) {
			itemBean = productMap.getItemInfo(cmcDubber.getItemid());
			if(itemBean==null) continue;
			
			bean = new DubberBean();
			bean.setDubberId(cmcDubber.getDubberid());
			bean.setTitle(cmcDubber.getTitle());
			bean.setIcon(cmcDubber.getIcon());
			bean.setSpecialty(cmcDubber.getSpecialty());
			bean.setStyle(cmcDubber.getStyle());
			bean.setAudition(cmcDubber.getAudition());
			bean.setItem(itemBean);
			
			result.add(bean);
		}
		
		return result;
	}
	
	/**
	 * 创建机器配音订单
	 * @param bean
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 * @exception ParamException
	 */
	public OrderCreateResult addDub(DubAddBean bean, String serviceType, String platform, String version){
		//1.校验产品和价格
		ProductItemBean item = null;
		int wordNum = StringUtil.countWord(bean.getTxt());
		ProductBean product = productMap.getProductInfo4Dub();
		for (ProductItemBean i : product.getItems()) {
			if((wordNum>=i.getMinNum()||i.getMinNum()==null) 
					&& (wordNum<=i.getMaxNum() || i.getMaxNum()==null)){
				item = i;
				break;
			}
		}
		if(item==null || bean.getAmount()<item.getPrice()){
			throw new ParamException("价格有误");
		}
		
		//2.创建订单
		String orderId = orderService.newOrderId();
		
		CmcROrder order = dubOrderService.addDub(orderId, bean, item, serviceType, platform, version);
		
		OrderCreateResult result = new OrderCreateResult();
		result.setOrderId(order.getOrderid());
		result.setDealStatus(order.getDealstatus());
		
		//3.创建订单后，预支付处理，失败返回空，客户端重新发起预支付
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
	}
	
	/**
	 * 创建真人配音订单
	 * @param bean
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 * @exception ParamException
	 */
	public OrderCreateResult addHumanDub(DubAddBean bean, String serviceType, String platform, String version){
		//1.校验产品和价格
		CmcDubber dubber = dubberService.getDubber(bean.getDubberId());
		if(dubber==null || !SystemConstants.STATUS_ON.equals(dubber.getStatus()) || dubber.getItemid()==null){
			throw new ParamException("配音员有误");
		}
		ProductItemBean item = productMap.getItemInfo(dubber.getItemid());
		if(item==null){
			throw new ParamException("配音员有误");
		}
		
		int wordNum = StringUtil.countWord(bean.getTxt());
		int num = wordNum/100;
		if(wordNum%100>0){
			num++;
		}
		Double amount = DoubleUtil.multiplyRound(item.getPrice(), Double.valueOf(num));
		if(bean.getAmount()<amount){
			throw new ParamException("订单金额有误");
		}
		
		//2.创建订单
		String orderId = orderService.newOrderId();
		
		CmcROrder order = dubOrderService.addDub(orderId, bean, item, serviceType, platform, version);
		
		OrderCreateResult result = new OrderCreateResult();
		result.setOrderId(order.getOrderid());
		result.setDealStatus(order.getDealstatus());
		
		//3.创建订单后，预支付处理，失败返回空，客户端重新发起预支付
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
	}
}
