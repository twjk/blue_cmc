package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.cache.SceneMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ITransVideoDao;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcTpPrice;
import com.qcmz.cmc.entity.CmcVtTrans;
import com.qcmz.cmc.service.IOrderLogService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransVideoService;
import com.qcmz.cmc.service.IUserTransComboService;
import com.qcmz.cmc.service.IWalletConsumeService;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoAddBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoDealBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoDealListBean;
import com.qcmz.cmc.ws.provide.vo.TransVideoDetailBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 *
 */
@Service
public class TransVideoServiceImpl implements ITransVideoService {
	@Autowired
	private ITransVideoDao transVideoDao;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderLogService orderLogService;
	@Autowired
	private IWalletConsumeService walletConsumeService;
	@Autowired
	private IUserTransComboService userTransComboService;
	
	@Autowired
	private SceneMap sceneMap;
	
	/**
	 * 分页获取视频口译信息，含订单信息
	 * @param map
	 * @param pageBean<CmcVtTrans>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		transVideoDao.queryByMapTerm(map, pageBean);
		List<CmcVtTrans> transList = (List<CmcVtTrans>) pageBean.getResultList();
		if(transList.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcVtTrans trans : transList) {
			userIds.add(trans.getUserid());				
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcVtTrans trans : transList) {
			trans.setUser(userMap.get(trans.getUserid()));
		}
	}
	
	/**
	 * 分页获取视频口译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<TransVideoDealListBean> findVideo4Deal(OrderDealQueryBean query, int pageSize){
		return transVideoDao.findVideo4Deal(query, pageSize);
	}
	
	/**
	 * 获取视频口译列表
	 * @param orderIds
	 * @return
	 */
	public List<CmcVtTrans> findVideo(List<String> orderIds){
		return transVideoDao.findVideo(orderIds);				
	}
	
	/**
	 * 获取视频口译详细信息
	 * @param orderId
	 * @param userId
	 * @return
	 * @exception ParamException
	 */
	public TransVideoDetailBean getVideoDetail(String orderId, Long userId){
		//订单新
		TransVideoDetailBean result = new TransVideoDetailBean(orderService.getOrderDetail(orderId, userId));
		
		//视频信息
		CmcVtTrans trans = getTrans(orderId);
		result.setFrom(trans.getFromlang());
		result.setTo(trans.getTolang());
		if(trans.getSceneid()!=null){
			result.setSceneBean(sceneMap.getSceneBean(trans.getSceneid()));
		}
		if(trans.getConnecttime()!=null){
			result.setConnectedTime(trans.getConnecttime().getTime());
		}
		if(trans.getStartbillingtime()!=null){
			result.setBillingTime(trans.getStartbillingtime().getTime());
		}
		if(trans.getHanduptime()!=null){
			result.setHandupTime(trans.getHanduptime().getTime());
		}
		result.setBillingDuration(trans.getBillingduration());
		result.setRoomId(trans.getRoomid());
		
		return result;
	}
	
	/**
	 * 获取视频口译订单信息，含翻译、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId){
		CmcROrder order = orderService.getOrderJoin(orderId);
		order.setCmcVtTrans(getTrans(orderId));
		if(order.getCmcVtTrans().getSceneid()!=null){
			order.getCmcVtTrans().setCmcBScene(sceneMap.getScene(order.getCmcVtTrans().getSceneid()));
		}
		order.setUser(UserMap.getUser(order.getUserid()));
		return order;
	}
	
	/**
	 * 获取视频口译信息
	 * @param orderId
	 * @return
	 */
	public CmcVtTrans getTrans(String orderId){
		return transVideoDao.getTrans(orderId);
	}
	
	
	/**
	 * 创建视频口译订单
	 * @param orderId
	 * @param bean
	 * @param serviceType
	 * @param platform
	 * @param version
	 * @return
	 */
	public CmcROrder addTransVideo(String orderId, TransVideoAddBean bean, String serviceType, String platform, String version){
		Date now = new Date();
		Date appointTime = null;
		Date waitTime = null;
		Integer appointFlag = SystemConstants.STATUS_OFF;
		if(bean.getAppointTime()!=null){
			appointTime = new Date(bean.getAppointTime());
		}
		if(appointTime!=null && appointTime.after(now)){
			appointFlag = SystemConstants.STATUS_ON;
			waitTime = appointTime;
		}else{
			waitTime = now;
		}
		
		//组装数据
		CmcROrder order = new CmcROrder();
		order.setOrderid(orderId);
		order.setUserid(bean.getUid());
		order.setUsertype(bean.getUserType());
		order.setEmployeeid(bean.getEmployeeId());
		order.setEmployeename(bean.getEmployeeName());
		order.setOrdercat(DictConstant.DICT_ORDERCAT_TRANS);
		order.setOrdertype(DictConstant.DICT_ORDERTYPE_TRANSVIDEO);
		order.setFromlang(bean.getFrom());
		order.setTolang(bean.getTo());
		order.setAmount(0.0);
		order.setCouponamount(bean.getCouponAmount());
		order.setWalletamount(0.0);
		order.setPayableamount(0.0);
		if(bean.getUcid()!=null && bean.getUcid()>0){
			order.setUcid(bean.getUcid());
		}
		order.setCreatetime(now);
		order.setAppointflag(appointFlag);
		order.setAppointtime(appointTime);
		order.setWaittime(waitTime);
		order.setServicetype(serviceType);
		order.setPlatform(platform);
		order.setVersion(version);
		order.setAddress(bean.getAddress());
		order.setLon(bean.getLon());
		order.setLat(bean.getLat());
		order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_WAITTRANS);
		order.setDealprogress(OrderUtil.dealStatus2ProgressStatus(order.getDealstatus()));
		order.setStatus(SystemConstants.STATUS_ON);
		if(appointTime!=null && appointTime.after(now)){
			order.setSoondealstatus(SystemConstants.STATUS_OPER_ENABLED);
		}
		
		CmcRItem ritem = new CmcRItem();
		ritem.setOrderid(orderId);
		ritem.setItemid(0L);
		ritem.setItemname(DictMap.getOrderTypeMean(order.getOrdertype()));
		ritem.setOriprice(0.0);
		ritem.setPrice(0.0);
		
		CmcVtTrans cmcVtInfo = new CmcVtTrans();
		cmcVtInfo.setOrderid(orderId);
		cmcVtInfo.setUserid(bean.getUid());
		cmcVtInfo.setFromlang(bean.getFrom());
		cmcVtInfo.setTolang(bean.getTo());
		cmcVtInfo.setSceneid(bean.getSceneId());
		cmcVtInfo.setRoomid(bean.getRoomId());
		cmcVtInfo.setCalltype(bean.getCallType());
		
		//使用套餐
		if(bean.getUcid()!=null && bean.getUcid()>0){
			userTransComboService.useUserCombo4User(bean.getUcid(), bean.getUid(), DictConstants.DICT_TRANSTYPE_VIDEO, orderId);
		}
		
		//信息入库
		transVideoDao.save(order);
		transVideoDao.save(ritem);
		transVideoDao.save(cmcVtInfo);
		
		//操作
		orderLogService.saveFlowLog(order.getOrderid(), order.getDealstatus(), String.valueOf(order.getUserid()), SystemConstants.OPERATOR_USER, order.getCreatetime(),null);
		
		return order;
	}
	
	/**
	 * 接通用户
	 * @param dealBean
	 * 修改历史：
	 */
	public void connected(TransVideoDealBean dealBean){
		//校验
		CmcROrder order = orderService.getOrder(dealBean.getOrderId());
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		//非翻译中的订单，不做处理
		if(!DictConstant.DICT_ORDER_DEALSTATUS_PROCESSING.equals(order.getDealstatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		transVideoDao.updateConnectTime(dealBean.getOrderId());
		
		//操作日志
		if(!dealBean.getOperator().equals(order.getOpercd())){
			orderLogService.saveOperLog(order.getOrderid(), order.getDealstatus(), dealBean.getOperator(), dealBean.getOperatorName(), "接通用户");
		}
	}
	
	
	/**
	 * 开始计费
	 * @param dealBean
	 * 修改历史：
	 */
	public void startBilling(TransVideoDealBean dealBean){
		//校验
		CmcROrder order = orderService.getOrder(dealBean.getOrderId());
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		CmcVtTrans trans = getTrans(dealBean.getOrderId());
		if(trans.getConnecttime()==null){
			transVideoDao.updateConnectAndStartBillingTime(dealBean.getOrderId());
		}else{
			transVideoDao.updateStartBillingTime(dealBean.getOrderId());
		}
		
		//操作日志
		if(!dealBean.getOperator().equals(order.getOpercd())){
			orderLogService.saveOperLog(order.getOrderid(), order.getDealstatus(), dealBean.getOperator(), dealBean.getOperatorName(), "开始计费");
		}
	}
	
	/**
	 * 接通并开始计费
	 * @param dealBean
	 */
	public void connectedAndBilling(TransVideoDealBean dealBean){
		//校验
		CmcROrder order = orderService.getOrder(dealBean.getOrderId());
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		//非翻译中的订单，不做处理
		if(!DictConstant.DICT_ORDER_DEALSTATUS_PROCESSING.equals(order.getDealstatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		transVideoDao.updateConnectAndStartBillingTime(dealBean.getOrderId());
		
		//操作日志
		if(!dealBean.getOperator().equals(order.getOpercd())){
			orderLogService.saveOperLog(order.getOrderid(), order.getDealstatus(), dealBean.getOperator(), dealBean.getOperatorName(), "接通用户并开始计费");
		}
	}
	
	/**
	 * 完成翻译
	 * @param order
	 * @param trans
	 * @param dealBean
	 * @param price
	 */
	public void finishTrans(CmcROrder order, CmcVtTrans trans, TransVideoDealBean dealBean, CmcTpPrice price){
		Date now = new Date();
		
		//计费
		Date start = trans.getStartbillingtime();
		if(start==null){
			start = trans.getConnecttime();
		}
		int billDuration = (int)DateUtil.betweenSecond(start, now);
		int connectDuration = (int)DateUtil.betweenSecond(trans.getConnecttime(), now);
	
		double amount = 0;
		//使用套餐，订单金额为0；未套餐，才计算金额
		if(order.getUcid()==null){
			if(DictConstant.DICT_PRICEUNIT_ORDER.equals(price.getPriceunit())){
				amount = price.getPrice();
			}else if(DictConstant.DICT_PRICEUNIT_MINUTE.equals(price.getPriceunit())){
				int billMinute = billDuration/60;
				if(billDuration%60>0){
					billMinute++;
				}
				if(billMinute>price.getStartnum()){
					amount = price.getStartprice()+price.getPrice()*(billMinute-price.getStartnum());
					amount = DoubleUtil.floor(amount, 2);
				}else{
					amount = price.getStartprice();
				}
			}
		}
		
		//优惠券
		double payableAmount = amount;
		double couponAmount = order.getCouponamount();
		if(couponAmount<amount){
			payableAmount = DoubleUtil.subtract(amount, couponAmount);	//应付金额
		}else{
			couponAmount = amount;
			payableAmount = 0;			
		}
		
		//订单信息
		order.setAmount(amount);
		order.setCouponamount(couponAmount);
		order.setPayableamount(payableAmount);
		order.setWalletamount(payableAmount);
		order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_FINISH);
		order.setDealprogress(OrderUtil.dealStatus2ProgressStatus(order.getDealstatus()));
		order.setEvalstatus(SystemConstants.STATUS_OPER_ENABLED);
		order.setFinishtime(now);
		order.setCommissionstatus(SystemConstants.STATUS_OPER_ENABLED);
		
		//商品信息
		List<CmcRItem> items = orderService.findItem(order.getOrderid());
		for (CmcRItem item : items) {
			item.setOriprice(price.getOriprice());
			item.setPrice(price.getPrice());
			item.setStarttime(start);
			item.setEndtime(now);
		}
		transVideoDao.saveOrUpdateAll(items);
		
		//翻译信息
		trans.setHanduptime(now);
		trans.setConnectduration(connectDuration);
		trans.setBillingduration(billDuration);
		
		//钱包支付
		if(order.getWalletamount()>0){
			walletConsumeService.consumeForce(order.getUserid(), order.getWalletamount(), DictConstants.DICT_SUBSERVICETYPE_TRANSVIDEO, order.getOrderid());
		}
		//保存信息
		transVideoDao.update(order);
		transVideoDao.update(trans);
		
		//日志
		orderLogService.saveFlowLog(dealBean.getOrderId(), order.getDealstatus(), dealBean.getOperator(), dealBean.getOperatorName(), now);
	}
	
	/**
	 * 拒单处理
	 * @param orderId
	 * @param date
	 */
	public void rejectOrder(String orderId, Date date){
		CmcVtTrans trans = getTrans(orderId);
		if(trans.getConnecttime()!=null){
			trans.setHanduptime(date);
			trans.setConnectduration((int)DateUtil.betweenSecond(trans.getConnecttime(), date));
			transVideoDao.update(trans);
		}
	}
}
