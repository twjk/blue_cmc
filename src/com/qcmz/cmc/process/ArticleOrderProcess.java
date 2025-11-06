package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ArticleItemMap;
import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcWalletAccount;
import com.qcmz.cmc.service.IArticleOrderService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.IWalletAccountService;
import com.qcmz.cmc.thrd.FinishOrderActivityThrd;
import com.qcmz.cmc.thrd.OrderNotifyThrd;
import com.qcmz.cmc.ws.provide.vo.ArticleSubBean;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayBean;
import com.qcmz.cms.ws.provide.locator.ArticleRightWS;
import com.qcmz.cms.ws.provide.vo.ArticleItemBean;
import com.qcmz.cms.ws.provide.vo.ArticleRightAddBean;
import com.qcmz.cms.ws.provide.vo.ArticleRightAddResp;
import com.qcmz.cms.ws.provide.vo.ArticleRightAddResult;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.umc.ws.provide.locator.UserCouponWS;

@Component
public class ArticleOrderProcess {
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IWalletAccountService walletAccountService;
	@Autowired
	private IArticleOrderService articleOrderService;
	
	@Autowired
	private OrderProcess orderProcess;
	
	@Autowired
	private ArticleItemMap articleItemMap;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 资讯订阅
	 * @param bean
	 * @param platform
	 * @param version
	 * @return
	 * @exception DataException、ParamException、SystemException
	 */
	public OrderCreateResult subArticle(ArticleSubBean bean, String serviceType, String platform, String version){
		Date now = new Date();
		OrderCreateResult result = new OrderCreateResult();
		
		//1.校验
		//商品校验
		ArticleItemBean item = articleItemMap.getItem(bean.getItemId());
		if(item==null){
			throw new DataException("商品不存在");
		}else if(now.getTime()>item.getEndTime()){
			throw new DataException("已过期");
		}else if(now.getTime()<item.getStartTime()){
			throw new DataException("未开始销售");
		}else if(!item.getPrice().equals(bean.getItemPrice())
				|| item.getPrice()!=bean.getAmount()){
			throw new DataException("价格有误");
		}
		
		//校验重复订阅
		Integer orderType = null;
		boolean hasBought = false; 
		if(item.getCat().startsWith(BusinessConstant.CATID_TRANSVIP)){
			orderType = DictConstant.DICT_ORDERTYPE_TRANSVIP;
		}else if(item.getCat().startsWith(BusinessConstant.CATID_NOAD)){
			orderType = DictConstant.DICT_ORDERTYPE_NOAD;
		}else if(item.getCat().startsWith(BusinessConstant.CATID_BREADENGLISH)){
			orderType = DictConstant.DICT_ORDERTYPE_SUBBREADENGLISH;
		}else if(item.getCat().startsWith(BusinessConstant.CATID_MCGG)){
			orderType = DictConstant.DICT_ORDERTYPE_SUBMCGG;
		}if(item.getCat().startsWith(BusinessConstant.CATID_USERVIP)){
			orderType = DictConstant.DICT_ORDERTYPE_USERVIP;
		}
		if(orderType==null){
			throw new ParamException("订阅栏目有误");
		}
		
		String subServiceType = DictConstant.getSubServiceType(orderType);		
		
		if(bean.getArticleId()!=null){
			hasBought = orderService.hasBought(bean.getUid(), orderType, item.getItemId(), String.valueOf(bean.getArticleId()));
		}else if(DictConstant.DICT_ORDERTYPE_SUBBREADENGLISH.equals(orderType)
				|| DictConstant.DICT_ORDERTYPE_NOAD.equals(orderType)){
			//面包英语，去广告，栏目只能订阅一次
			hasBought = orderService.hasBought(bean.getUid(), orderType, item.getItemId(), null);
		}
		if(hasBought){
			throw new DataException("请不要重复购买");
		}
		
		//抵扣金额校验
		if(bean.getWalletAmount()>0){
			CmcWalletAccount account = walletAccountService.getAccount(bean.getUid());
			if(account==null || bean.getWalletAmount()>account.getBalance()){
				throw new DataException("钱包余额不足");
			}
		}
		
		//生成订单号
		String orderId = orderService.newOrderId();
		
		//2.使用优惠券
		boolean useCoupon = false;
		if(bean.getCouponId()!=null && bean.getCouponAmount()>0){
			Response resp = UserCouponWS.useCoupon(bean.getUid(), subServiceType, orderId, bean.getCouponId(), bean.getCouponAmount(), bean.getAmount());
			if(resp.successed()){
				useCoupon = true;
			}else{
				if(resp.dataError()){
					throw new DataException(resp.getRespMsg());
				}else{
					throw new DataException("使用优惠券失败");
				}
			}
		}
		
		try {
			//3.保存资讯订单信息
			CmcROrder order = articleOrderService.subArticle(orderId, bean, item, serviceType, subServiceType, orderType, platform, version);
			result.setOrderId(orderId);
			
			//4.创建订单后，预支付处理，失败返回空，客户端重新发起预支付
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
			//5.回滚优惠券处理
			if(useCoupon){
				boolean rollback = UserCouponWS.rollbackCoupon(bean.getUid(), orderId);
				if(!rollback){
					logger.error("回滚优惠券失败："+orderId);
				}
			}
			logger.error("资讯订阅失败：", e);
			throw new SystemException();
		}
	}
	
	/**
	 * 定时处理资讯订阅
	 */
	public void dealArticleSub(){
		List<CmcROrder> orders = orderService.findWaitDeal(DictConstant.DICT_ORDERCAT_SUB, DictConstant.DICT_ORDERTYPES_SUB);
		List<CmcRItem> items = null;
		List<CmcRItem> updateItems = null;
		ArticleRightAddBean addRightBean = null;
		ArticleRightAddResp resp = null;
		List<ArticleRightAddBean> list = null;
		Map<Long, CmcRItem> itemMap = null;
		StringBuilder sbMsg = null;
		List<String> msgs = null;
		
		for (CmcROrder order : orders) {
			try {
				if(order.getDealprogress().equals(DictConstants.DICT_DEALPROGRESS_WAITING)){
					items = orderService.findItem(order.getOrderid());
					list = new ArrayList<ArticleRightAddBean>();
					itemMap = new HashMap<Long, CmcRItem>();
					for (CmcRItem item : items) {
						addRightBean = new ArticleRightAddBean();
						addRightBean.setUid(order.getUserid());
						addRightBean.setRitemId(item.getRitemid());
						addRightBean.setItemId(item.getItemid());
						addRightBean.setItemNum(item.getItemnum());
						if(StringUtil.notBlankAndNull(item.getRefid())){
							addRightBean.setArticleId(Long.valueOf(item.getRefid()));
						}
						list.add(addRightBean);
						itemMap.put(item.getRitemid(), item);
					}
					orderService.updateDealProgress(order.getOrderid(), DictConstants.DICT_DEALPROGRESS_PROCESSING);
					resp = ArticleRightWS.addRight(list);
					if(resp==null || !resp.successed()){
						orderService.updateDealProgress(order.getOrderid(), DictConstants.DICT_DEALPROGRESS_WAITING);
					}else{
						msgs = new ArrayList<String>();
						
						CmcRItem item = null;
						updateItems = new ArrayList<CmcRItem>();
						for (ArticleRightAddResult resultBean : resp.getResult()) {
							item = itemMap.get(resultBean.getRitemId());
							if(resultBean.getStartTime()!=null){
								item.setStarttime(new Date(resultBean.getStartTime()));
								if(resultBean.getEndTime()!=null){
									item.setEndtime(new Date(resultBean.getEndTime()));
								}
								updateItems.add(item);
							}
							
							sbMsg = new StringBuilder();
							
							if(DictConstant.DICT_ORDERTYPE_NOAD.equals(order.getOrdertype())){
								//您已成功订阅服务《》
								sbMsg.append("《").append(item.getItemname()).append("》");
							}else{
								//您已成功订阅服务《梅出过国--鸡最怕过周几》
								sbMsg.append("《").append(DictMap.getOrderTypeMean(order.getOrdertype()))
								.append("--").append(item.getItemname()).append("》")
								;
							}
							if(item.getEndtime()!=null){
								sbMsg.append("，有效期至").append(DateUtil.formatDateTime3(item.getEndtime()));
							}
							
							msgs.add(sbMsg.toString());
						}
						//保存结果
						orderService.saveOrderFinish(order.getOrderid(), updateItems, SystemConstants.OPERATOR_DEFAULT, SystemConstants.OPERATOR_DEFAULT);
						
						//通知用户
						order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_SUB);
						for (String msg : msgs) {
							OrderNotifyThrd.notifyStatus(order, msg, null);
						}
						
						//参加活动
						FinishOrderActivityThrd.start(order);
					}
				}
			} catch (Exception e) {
				logger.error("处理订阅失败："+order.getOrderid(), e);
			}
		}
	}
}
