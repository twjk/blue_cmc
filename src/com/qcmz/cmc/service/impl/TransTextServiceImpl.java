package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ITransTextDao;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcTtTrans;
import com.qcmz.cmc.entity.CmcTtTranshis;
import com.qcmz.cmc.service.IOrderLogService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransTextService;
import com.qcmz.cmc.service.IWalletConsumeService;
import com.qcmz.cmc.thrd.OrderNewNotifyCsThrd;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.TransPriceBean;
import com.qcmz.cmc.ws.provide.vo.TransTextAddBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealDetailBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDealListBean;
import com.qcmz.cmc.ws.provide.vo.TransTextDetailBean;
import com.qcmz.cmc.ws.provide.vo.TransTextTransBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class TransTextServiceImpl implements ITransTextService{
	@Autowired
	private ITransTextDao transTextDao;
	
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderLogService orderLogService;
	@Autowired
	private IWalletConsumeService walletConsumeService;
	
	/**
	 * 分页获取短文快译信息，含订单信息
	 * @param map
	 * @param pageBean<CmcTtTrans>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		transTextDao.queryByMapTerm(map, pageBean);
		
		List<CmcTtTrans> transList = (List<CmcTtTrans>) pageBean.getResultList();
		if(transList.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcTtTrans trans : transList) {
			userIds.add(trans.getUserid());				
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcTtTrans trans : transList) {
			trans.setUser(userMap.get(trans.getUserid()));
		}
		
	}
	
	/**
	 * 分页获取短文快译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<TransTextDealListBean> findText4Deal(OrderDealQueryBean query, int pageSize){
		return transTextDao.findText4Deal(query, pageSize);
	}
	
	/**
	 * 获取短文快译列表
	 * @param orderIds
	 * @return
	 */
	public List<CmcTtTrans> findText(List<String> orderIds){
		return transTextDao.findText(orderIds);
	}
	
	/**
	 * 获取短文快译订单信息，含翻译、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId){
		CmcROrder order = orderService.getOrderJoin(orderId);
		order.setCmcTtTrans(transTextDao.getTrans(orderId));
		order.setUser(UserMap.getUser(order.getUserid()));
		return order;
	}
	
	/**
	 * 获取短文快译信息
	 * @param orderId
	 * @return
	 */
	public CmcTtTrans getTrans(String orderId){
		return transTextDao.getTrans(orderId);
	}
	
	/**
	 * 获取短文快译详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @exception ParamException
	 */
	public TransTextDetailBean getTextDetail(String orderId, Long userId){
		//订单信息
		TransTextDetailBean result = new TransTextDetailBean(orderService.getOrderDetail(orderId, userId));
		
		//翻译信息
		CmcTtTrans cmcTtTrans = transTextDao.getTrans(orderId);
		result.setSid(cmcTtTrans.getSessionid());
		result.setTransModel(cmcTtTrans.getTransmodel());
		result.setFrom(cmcTtTrans.getFromlang());
		result.setTo(cmcTtTrans.getTolang());
		result.setSrc(cmcTtTrans.getSrc());
		result.setDst(cmcTtTrans.getDst());
		
		return result;
	}
	
	/**
	 * 获取短文快译详细信息
	 * @param orderId
	 * @return
	 * @exception ParamException
	 */
	public TransTextDealDetailBean getTextDetail4Deal(String orderId){
		//订单信息
		TransTextDealDetailBean result = new TransTextDealDetailBean(orderService.getOrderDetail4Deal(orderId));
		
		//翻译信息
		CmcTtTrans trans = transTextDao.getTrans(orderId);
		result.setFrom(trans.getFromlang());
		result.setSrc(trans.getSrc());
		result.setTo(trans.getTolang());
		result.setDst(trans.getDst());
		result.setVoice(trans.getVoice());
		
		//上下文
		result.getContexts().addAll(transTextDao.findUserTransBefore(trans.getUserid(), 10, orderId));
		result.getContexts().add(new TransTextTransBean(orderId, trans.getFromlang(), trans.getTolang(), trans.getSrc(), trans.getDst()));
		result.getContexts().addAll(transTextDao.findUserTransAfter(trans.getUserid(), 5, orderId));
		
		return result;
	}
	
	/**
	 * 创建短文快译订单
	 * @param orderId
	 * @param bean
	 * @param price
	 * @param similar
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder addTransText(String orderId, TransTextAddBean bean, TransPriceBean price, int similar, String serviceType, String platform, String version){
		Date now = new Date();
		Date finishTime = null;		
		String dealStatus = null;
		
		double payableAmount = bean.getAmount();
		
		double couponAmount = bean.getCouponAmount();
		if(couponAmount<bean.getAmount()){
			payableAmount = DoubleUtil.subtract(bean.getAmount(), bean.getCouponAmount());	//应付金额
		}else{
			couponAmount = bean.getAmount();
			payableAmount = 0;			
		}
		
		double walletAmount = bean.getWalletAmount();
		if(payableAmount>0 && walletAmount>0){
			if(walletAmount<payableAmount){
				payableAmount = DoubleUtil.subtract(payableAmount, bean.getWalletAmount());
			}else{
				walletAmount = payableAmount;
				payableAmount = 0;
			}
			
			//钱包抵扣
			walletConsumeService.consume(bean.getUid(), walletAmount, DictConstants.DICT_SUBSERVICETYPE_TRANSTEXT, orderId);
		}
		
		if(payableAmount==0){
			if(similar<TransConfig.TRANS_SIMILAR_MIN){
				dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_WAITTRANS;
			}else{
				dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_FINISH;
				finishTime = now;
			}
		}else{
			dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY;
		}
		
		//组装数据
		CmcROrder order = new CmcROrder();
		order.setOrderid(orderId);
		order.setUserid(bean.getUid());
		order.setUsertype(bean.getUserType());
		order.setEmployeeid(bean.getEmployeeId());
		order.setEmployeename(bean.getEmployeeName());
		order.setOrdercat(DictConstant.DICT_ORDERCAT_TRANS);
		order.setOrdertype(DictConstant.DICT_ORDERTYPE_TRANSTEXT);
		order.setFromlang(bean.getFrom());
		order.setTolang(bean.getTo());
		order.setRequirement(null);
		order.setAmount(bean.getAmount());
		order.setCouponamount(couponAmount);
		order.setWalletamount(walletAmount);
		order.setPayableamount(payableAmount);
		order.setCreatetime(now);
		order.setWaittime(now);
		order.setFinishtime(finishTime);
		order.setServicetype(serviceType);
		order.setPlatform(platform);
		order.setVersion(version);
		order.setDealstatus(dealStatus);
		order.setDealprogress(OrderUtil.dealStatus2ProgressStatus(order.getDealstatus()));
		order.setStatus(SystemConstants.STATUS_ON);
		
		CmcRItem ritem = new CmcRItem();
		ritem.setOrderid(orderId);
		ritem.setItemid(price.getPriceId());
		ritem.setItemname(DictMap.getOrderTypeMean(order.getOrdertype()));
		ritem.setOriprice(price.getTotalOriPrice());
		ritem.setPrice(price.getTotalPrice());
		
		CmcTtTrans cmcTtTrans = new CmcTtTrans();
		cmcTtTrans.setOrderid(orderId);
		cmcTtTrans.setUserid(bean.getUid());
		cmcTtTrans.setTransmodel(bean.getTransModel()==null?DictConstant.DICT_TRANSMODEL_FREE:bean.getTransModel());
		cmcTtTrans.setFromlang(bean.getFrom());
		cmcTtTrans.setTolang(bean.getTo());
		cmcTtTrans.setSrc(bean.getSrc());
		cmcTtTrans.setDst(bean.getDst());
		cmcTtTrans.setPushregid(bean.getPushRegid());
		cmcTtTrans.setSessionid(bean.getSid());
		cmcTtTrans.setCreatetime(now);
		
		//信息入库
		transTextDao.save(order);
		transTextDao.save(ritem);
		transTextDao.save(cmcTtTrans);
		
		//操作
		orderLogService.saveFlowLog(order.getOrderid(), order.getDealstatus(), String.valueOf(order.getUserid()), SystemConstants.OPERATOR_USER, order.getCreatetime(),null);
		
		//通知客服
		OrderNewNotifyCsThrd.newOrderNotify(order);
		
		return order;
	}
	
	/**
	 * 保存翻译结果
	 * @param dealBean
	 */
	public void saveTrans(TransTextDealBean dealBean){
		//校验
		CmcROrder order = orderService.getOrder(dealBean.getOrderId());
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}else if(!OrderUtil.canTrans(order.getDealstatus(), order.getDealprogress())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		//保存翻译结果
		saveTransResult(dealBean, order.getDealstatus());
		
		//保存订单处理结果
		orderService.saveOrderDeal(order.getOrderid(), dealBean.getNeedTime(), dealBean.getFrom(), dealBean.getTo(), dealBean.getOperator(), dealBean.getOperatorName());
	}
	
	/**
	 * 完成短文快译
	 * @param dealBean
	 * @param dealStatus
	 */
	public void finishTrans(TransTextDealBean dealBean, String dealStatus){
		//保存翻译结果
		saveTransResult(dealBean, dealStatus);

		//保存订单完成信息
		orderService.saveOrderFinish(dealBean.getOrderId(), dealBean.getFrom(), dealBean.getTo(), dealBean.getOperator(), dealBean.getOperatorName());
	}
	
	/**
	 * 保存翻译结果，无校验订单
	 * @param dealBean
	 * @param dealStatus
	 */
	public void saveTransResult(TransTextDealBean dealBean, String dealStatus){
		//1.获取翻译结果
		CmcTtTrans trans = transTextDao.getTrans(dealBean.getOrderId());
				
		//2.比较前后翻译结果，看是否做过修改，无变动返回
		boolean isModifyDst = false;
		if(StringUtil.isBlankOrNull(dealBean.getDst())){
			isModifyDst = StringUtil.notBlankAndNull(trans.getDst());
		}else{
			isModifyDst = !dealBean.getDst().equals(trans.getDst());
		}		
		boolean changeFrom = StringUtil.notBlankAndNull(dealBean.getFrom())	&& !dealBean.getFrom().equals(trans.getFromlang());
		boolean changeTo = StringUtil.notBlankAndNull(dealBean.getTo())	&& !dealBean.getTo().equals(trans.getTolang());
		if(!isModifyDst && !changeFrom && !changeTo) return;
		
		Date now = new Date();
		//3.是否换人修改翻译，是则记录翻译历史
		boolean isSameOperator = true;
		if(isModifyDst 
				&& StringUtil.notBlankAndNull(trans.getTranscd()) 
				&& !dealBean.getOperator().equals(trans.getTranscd())){
			isSameOperator = false;
		}
		if(!isSameOperator){
			CmcTtTranshis his = new CmcTtTranshis();
			his.setOrderid(dealBean.getOrderId());
			his.setVercode(CmcUtil.getTransVerCode(trans.getTranstime()));
			his.setDst(trans.getDst());			
			his.setTranscd(trans.getTranscd());
			his.setTransname(trans.getTransname());
			his.setTranstime(trans.getTranstime());
			his.setOpercd(dealBean.getOperator());
			his.setOpername(dealBean.getOperatorName());
			his.setOpertime(now);
			transTextDao.save(his);
		}
		
		//4.保存翻译结果
		if(changeFrom)	trans.setFromlang(dealBean.getFrom());
		if(changeTo)	trans.setTolang(dealBean.getTo());
		trans.setDst(dealBean.getDst());
		trans.setTranscd(dealBean.getOperator());
		trans.setTransname(dealBean.getOperatorName());
		trans.setTranstime(now);
		transTextDao.update(trans);
		
		//5.操作日志
		if(!isSameOperator){
			orderLogService.saveOperLog(dealBean.getOrderId(), dealStatus, dealBean.getOperator(), dealBean.getOperatorName(), "修改翻译结果");
		}
	}
	
	/**
	 * 更新语音
	 * @param orderId
	 * @param voice
	 */
	public void updateVoice(String orderId, String voice){
		transTextDao.updateVoice(orderId, voice);
	}
}