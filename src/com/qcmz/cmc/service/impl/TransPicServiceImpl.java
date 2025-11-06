package com.qcmz.cmc.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IOrderLogDao;
import com.qcmz.cmc.dao.ITransPicDao;
import com.qcmz.cmc.entity.CmcPtPic;
import com.qcmz.cmc.entity.CmcPtTrans;
import com.qcmz.cmc.entity.CmcPtTranshis;
import com.qcmz.cmc.entity.CmcRItem;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.service.IOrderLogService;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.service.IWalletConsumeService;
import com.qcmz.cmc.thrd.FinishOrderActivityThrd;
import com.qcmz.cmc.thrd.OrderNewNotifyCsThrd;
import com.qcmz.cmc.thrd.OrderNotifyThrd;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.util.UserUtil;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.OrderLogBean;
import com.qcmz.cmc.ws.provide.vo.TransPicAddBean;
import com.qcmz.cmc.ws.provide.vo.TransPicBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealDetailBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealListBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDetailBean;
import com.qcmz.cmc.ws.provide.vo.TransPicRotateResult;
import com.qcmz.cmc.ws.provide.vo.TransPicTransBean;
import com.qcmz.cmc.ws.provide.vo.TransPriceBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.AliyunOssUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.FileServiceUtil;
import com.qcmz.framework.util.FileTypeUtil;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.ImageUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * 类说明：图片翻译
 * 修改历史：
 */
@Service
public class TransPicServiceImpl implements ITransPicService {
	@Autowired
	private ITransPicDao transPicDao;
	@Autowired
	private IOrderLogDao orderLogDao;
	
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderLogService orderLogService;
	@Autowired
	private IWalletConsumeService walletConsumeService;

	/**
	 * 分页获取图片翻译信息，含用户信息
	 * @param map
	 * @param pageBean<CmcPtPic>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		transPicDao.queryByMapTerm(map, pageBean);
		
		List<CmcPtPic> pics = (List<CmcPtPic>) pageBean.getResultList();
		if(pics.isEmpty()) return;
		
		//获取用户信息
		Set<Long> userIds = new HashSet<Long>();
		for (CmcPtPic pic : pics) {
			userIds.add(pic.getUserid());				
		}
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcPtPic pic : pics) {
			pic.setUser(userMap.get(pic.getUserid()));
		}
	}
	
	/**
	 * 分页获取用户图片翻译列表
	 * 用于20171123之前单独图片翻译订单
	 * @param userId
	 * @param pageSize
	 * @param lastPicId
	 * @return
	 * 修改历史：
	 */
	public List<TransPicBean> findPic(Long userId, int pageSize, String lastPicId){
		List<TransPicBean> list = transPicDao.findPic(userId, pageSize, lastPicId);
		
		List<String> picIds = new ArrayList<String>();
		for (TransPicBean bean : list) {
			if(OrderUtil.showTransResult(bean.getDealStatus())){
				picIds.add(bean.getPicId());
			}
		}
		
		Map<String, StringBuilder> transMap = new HashMap<String, StringBuilder>();
		List<String> okPicIds = new ArrayList<String>();
		List<CmcPtTrans> transes = transPicDao.findTrans(picIds);
		StringBuilder sb = null;
		for (CmcPtTrans trans : transes) {
			if(okPicIds.contains(trans.getPicid())) continue;
			if(StringUtil.isBlankOrNull(trans.getDst())) continue;
			
			sb = transMap.get(trans.getPicid());
			if(sb==null){
				sb = new StringBuilder();
			}
			sb.append(" ").append(trans.getDst().replaceAll("\n", "").replaceAll("\r", ""));
			if(sb.length()>TransConfig.TRANSPIC_TRANSRESULT_MAXLENGTH){
				sb = new StringBuilder(StringUtil.abbreviate(sb.toString(), TransConfig.TRANSPIC_TRANSRESULT_MAXLENGTH));
				okPicIds.add(trans.getPicid());
			}
			transMap.put(trans.getPicid(), sb);
		}
		
		StringBuilder sbTransResult = null;
		for (TransPicBean bean : list) {
			sbTransResult = transMap.get(bean.getPicId());
			if(sbTransResult!=null){
				bean.setTransResult(sbTransResult.toString().trim());
			}
		}
		
		return list;
	}
	
	/**
	 * 分页获取图片翻译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<TransPicDealListBean> findPic4Deal(OrderDealQueryBean query, int pageSize){
		return transPicDao.findPic4Deal(query, pageSize);
	}
	
	/**
	 * 分页获取捡漏图片翻译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<TransPicDealListBean> findPic4PickDeal(OrderDealQueryBean query, int pageSize){
		return transPicDao.findPic4PickDeal(query, pageSize);
	}
	
	/**
	 * 获取图片列表
	 * @param picIds
	 * @return
	 */
	public List<CmcPtPic> findPic(List<String> picIds){
		return transPicDao.findPic(picIds);
	}
	
	/**
	 * 获取图片译文列表
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public List<CmcPtTrans> findTrans(String orderId){
		return transPicDao.findTrans(orderId);
	}
	
	/**
	 * 获取翻译图片信息
	 * @param picId
	 * @return
	 * 修改历史：
	 */
	public CmcPtPic getPic(String picId){
		return (CmcPtPic) transPicDao.load(CmcPtPic.class, picId);
	}
	
	/**
	 * 获取翻译图片信息
	 * @param picId
	 * @param userId
	 * @return
	 * 修改历史：
	 */
	public CmcPtPic getPic(Long userId, String picId){
		CmcPtPic cmcPtPic = getPic(picId);
		if(cmcPtPic!=null && userId!=null){
			if(!cmcPtPic.getUserid().equals(userId)){
				cmcPtPic = null;
			}
		}
		return cmcPtPic;
	}
	
	/**
	 * 获取翻译图片信息，含翻译记录、操作日志、留言
	 * @param picId
	 * @return
	 * 修改历史：
	 */
	public CmcPtPic getPicJoinTransLogMsg(String picId){
		CmcROrder order = orderService.getOrderJoin(picId);
		
		CmcPtPic cmcPtPic = getPic(picId);
		cmcPtPic.setTrans(transPicDao.findTrans(picId));
		cmcPtPic.setCmcROrder(order);
		return cmcPtPic;
	}
	
	/**
	 * 获取图片翻译订单信息，含图片、翻译记录、操作日志、留言
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public CmcROrder getOrderJoin(String orderId){
		CmcROrder order = orderService.getOrderJoin(orderId);
		
		CmcPtPic cmcPtPic = getPic(orderId);
		cmcPtPic.setTrans(transPicDao.findTrans(orderId));
		order.setCmcPtPic(cmcPtPic);
		order.setUser(UserMap.getUser(order.getUserid()));
		
		return order;
	}
	
	/**
	 * 获取翻译图片详细信息
	 * @param picId
	 * @param userId
	 * @return
	 * @exception ParamException
	 * 修改历史：
	 */
	public TransPicDetailBean getPicDetail(Long userId, String picId){
		//订单信息
		TransPicDetailBean result = new TransPicDetailBean(orderService.getOrderDetail(picId, userId));
		
		//图片信息
		CmcPtPic cmcPtPic = getPic(picId);
		result.setPicId(cmcPtPic.getPicid());
		result.setTransWay(cmcPtPic.getTransway());
		result.setFrom(cmcPtPic.getFromlang());
		result.setTo(cmcPtPic.getTolang());
		result.setSrc(cmcPtPic.getSrc());
		result.setPicUrl(cmcPtPic.getPicurl());
		
		//译文
		if(OrderUtil.showTransResult(result.getDealStatus())){
			List<CmcPtTrans> trans = transPicDao.findTrans(picId);
			for (CmcPtTrans cmcPtTrans : trans) {
				result.getTrans().add(BeanConvertUtil.toTransPicTransBean(cmcPtTrans));
			}
		}
		
		//日志
		result.setLogs(orderLogDao.findLogInfo(picId, DictConstant.DICT_LOGTYPE_FLOW, false));

		//添加未操作的日志
		List<OrderLogBean> notHappen = OrderUtil.getNotHappenLog(result.getDealStatus());
		if(!notHappen.isEmpty()){
			result.getLogs().addAll(notHappen);
		}
		
		return result;
	}
	
	/**
	 * 获取翻译图片详细信息
	 * @param picId
	 * @return
	 * 修改历史：
	 */
	public TransPicDealDetailBean getPicDetail4Deal(String picId){
		//订单信息
		TransPicDealDetailBean result = new TransPicDealDetailBean(orderService.getOrderDetail4Deal(picId));
		
		//图片信息
		CmcPtPic cmcPtPic = getPic(picId);
		result.setPicId(cmcPtPic.getPicid());
		result.setTransWay(cmcPtPic.getTransway());
		result.setFrom(cmcPtPic.getFromlang());
		result.setTo(cmcPtPic.getTolang());
		result.setSrc(cmcPtPic.getSrc());
		result.setPicUrl(cmcPtPic.getPicurl());
		
		//译文
		List<CmcPtTrans> trans = transPicDao.findTrans(picId);
		for (CmcPtTrans cmcPtTrans : trans) {
			result.getTrans().add(BeanConvertUtil.toTransPicTransBean(cmcPtTrans));
		}
			
		return result;
	}
	
	/**
	 * 添加翻译图片
	 * @param bean
	 * @param platform
	 * @return
	 * 修改历史：
	 */
	public CmcROrder addPic(String picId, TransPicAddBean bean, TransPriceBean price, String picUrl, String thumbUrl, String serviceType, String platform, String version){
		Date now = new Date();
		String dealStatus = null;
		double amount = 0;			//总金额
		double couponAmount = 0;	//优惠金额
		double walletAmount = 0;
		double payableAmount = 0;	//应付金额
		Date waitTime = null;

		if(DictConstant.DICT_TRANSWAY_MACHINE.equals(bean.getTransWay())){
			dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_MACHINE;
		}else{
			amount = bean.getAmount();
			//优惠券
			couponAmount = bean.getCouponAmount();
			if(couponAmount>amount){
				couponAmount = amount;
			}
			payableAmount = DoubleUtil.subtract(amount, couponAmount);
			
			//钱包抵扣
			walletAmount = bean.getWalletAmount();
			if(payableAmount>0 && walletAmount>0){
				if(walletAmount<payableAmount){
					payableAmount = DoubleUtil.subtract(payableAmount, bean.getWalletAmount());
				}else{
					walletAmount = payableAmount;
					payableAmount = 0;
				}
				
				walletConsumeService.consume(bean.getUid(), walletAmount, DictConstants.DICT_SUBSERVICETYPE_TRANSPIC, picId);
			}
			
			if(payableAmount>0){
				dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY;
			}else{
				dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_WAITTRANS;
				waitTime = now;
			}
		}
		
		//数据处理
		CmcROrder order = new CmcROrder();
		order.setOrderid(picId);
		order.setUserid(bean.getUid());
		order.setUsertype(bean.getUserType());
		order.setEmployeeid(bean.getEmployeeId());
		order.setEmployeename(bean.getEmployeeName());
		order.setOrdercat(DictConstant.DICT_ORDERCAT_TRANS);
		order.setOrdertype(DictConstant.DICT_ORDERTYPE_TRANSPIC);
		order.setFromlang(bean.getFrom());
		order.setTolang(bean.getTo());
		order.setRequirement(bean.getRequirement());
		order.setAmount(amount);
		order.setCouponamount(couponAmount);
		order.setWalletamount(walletAmount);
		order.setPayableamount(payableAmount);
		order.setCreatetime(now);
		order.setWaittime(waitTime);
		order.setServicetype(serviceType);
		order.setPlatform(platform);
		order.setVersion(version);
		order.setAddress(bean.getAddress());
		order.setLon(bean.getLon());
		order.setLat(bean.getLat());
		order.setDealstatus(dealStatus);
		order.setDealprogress(OrderUtil.dealStatus2ProgressStatus(dealStatus));
		order.setStatus(SystemConstants.STATUS_ON);
		if(OrderUtil.canPick(serviceType, order.getOrdertype(), platform, version, order.getDealstatus())){
			order.setPickstatus(SystemConstants.STATUS_OPER_ENABLED);
		}
		
		CmcRItem ritem = new CmcRItem();
		ritem.setOrderid(picId);
		ritem.setRefid(picId);
		ritem.setItemid(price.getPriceId());
		ritem.setItemname(DictMap.getOrderTypeMean(order.getOrdertype()));
		ritem.setOriprice(price.getTotalOriPrice());
		ritem.setPrice(price.getTotalPrice());
		
		CmcPtPic cmcPtPic = new CmcPtPic();
		cmcPtPic.setPicid(picId);
		cmcPtPic.setOrderid(picId);
		cmcPtPic.setUserid(bean.getUid());
		cmcPtPic.setTransway(bean.getTransWay());
		cmcPtPic.setSrc(bean.getSrc());
		cmcPtPic.setFromlang(bean.getFrom());
		cmcPtPic.setTolang(bean.getTo());
		
		//图片信息入库
		cmcPtPic.setPicurl(picUrl);
		cmcPtPic.setThumburl(thumbUrl);
		ritem.setPic(thumbUrl);
		orderService.saveOrder(order, ritem);
		transPicDao.save(cmcPtPic);
		
		//译文
		if(StringUtil.notBlankAndNull(bean.getDst())){
			CmcPtTrans cmcPtTrans = new CmcPtTrans();
			cmcPtTrans.setPicid(picId);
			cmcPtTrans.setDst(bean.getDst());
			cmcPtTrans.setPosx(50);
			cmcPtTrans.setPosy(50);
			transPicDao.save(cmcPtTrans);
		}
		
		//通知客服
		OrderNewNotifyCsThrd.newOrderNotify(order);
		
		return order;
	}

	/**
	 * 机器翻译转人工翻译
	 * @param picId
	 * @param userId
	 * @param requirement
	 * 修改历史：
	 */
	public void toHuman(Long userId, String picId, String requirement, double amount, double couponAmount){
		CmcPtPic cmcPtPic = getPic(userId, picId);
		CmcROrder order = orderService.getOrder(picId, userId);
		
		String dealStatus = null;
		Date waitTime = null;
		Double payableAmount = 0D;	//应付金额
		
		if(couponAmount>amount){
			couponAmount = amount;
		}
		payableAmount = amount - couponAmount;
		
		if(payableAmount>0){
			dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY;
		}else{
			dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_WAITTRANS;
			waitTime = new Date();
		}
		
		cmcPtPic.setTransway(DictConstant.DICT_TRANSWAY_OVERVIEW);
		cmcPtPic.setSrc(null);
		order.setRequirement(requirement);
		order.setAmount(amount);
		order.setCouponamount(couponAmount);
		order.setPayableamount(payableAmount);
		order.setWaittime(waitTime);
		order.setDealstatus(dealStatus);
		order.setDealprogress(OrderUtil.dealStatus2ProgressStatus(order.getDealstatus()));
		transPicDao.update(cmcPtPic);
		
		transPicDao.clearTrans(picId);
		
		//保存日志
		orderLogService.saveFlowLog(picId, DictConstant.DICT_ORDER_DEALSTATUS_SUBMITTED, String.valueOf(cmcPtPic.getUserid()), SystemConstants.OPERATOR_USER, order.getCreatetime(), null);
		
		OrderNewNotifyCsThrd.newOrderNotify(order);
	}
			
	/**
	 * 保存翻译结果
	 * @param picId
	 * @param trans
	 * @param oper
	 * 修改历史：
	 */
	public void saveTrans(TransPicDealBean dealBean){
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
	 * 保存捡漏翻译结果
	 * @param picId
	 * @param trans
	 * @param oper
	 * 修改历史：
	 */
	public void savePickTrans(TransPicDealBean dealBean){
		CmcROrder order = orderService.getOrder(dealBean.getOrderId());
		if(order==null){
			throw new DataException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}else if(!OrderUtil.canPickTrans(order.getPickstatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		saveTransResult(dealBean, order.getDealstatus());
		
		//变更金额
		if(dealBean.getAmount()!=null && !dealBean.getAmount().equals(order.getAmount())){
			Double oriAmount = order.getAmount();
			Double newAmount = dealBean.getAmount();
			Double payableAmount = DoubleUtil.subtract(order.getPayableamount(), DoubleUtil.subtract(oriAmount, newAmount));
			if(payableAmount<0){
				payableAmount = 0D;
			}
			order.setAmount(newAmount);
			order.setPayableamount(payableAmount);
			transPicDao.update(order);
			orderLogService.saveOperLog(dealBean.getOrderId(), order.getDealstatus(), dealBean.getOperator(), dealBean.getOperatorName(), "修改订单金额："+oriAmount+"->"+newAmount);
		}
	}

	/**
	 * 完成翻译
	 * @param dealBean
	 * @param dealStatus
	 * 修改历史：
	 */
	public void finishTrans(TransPicDealBean dealBean, String dealStatus){
		//保存翻译结果
		if(dealBean.getTrans()!=null && !dealBean.getTrans().isEmpty()){
			saveTransResult(dealBean, dealStatus);
		}
		
		//保存订单完成信息
		orderService.saveOrderFinish(dealBean.getOrderId(), dealBean.getFrom(), dealBean.getTo(), dealBean.getOperator(), dealBean.getOperatorName());
	}
	
	/**
	 * 翻译完成
	 * @param picId
	 * @param ipictureJson
	 * @param oper
	 * 修改历史：
	 */
	public void completeTrans(TransPicDealBean dealBean){
		String picId = dealBean.getOrderId();
		Date now = new Date();

		CmcROrder order = orderService.getOrder(picId);
		if(!OrderUtil.canCompleteTrans(order.getDealstatus(), order.getPickstatus())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		Double oriAmount = order.getAmount();
		Double newAmount = dealBean.getAmount();
		if(newAmount==null){
			newAmount = order.getAmount();
		}		
		if(!newAmount.equals(oriAmount)){
			Double payableAmount = DoubleUtil.subtract(order.getPayableamount(), DoubleUtil.subtract(oriAmount, newAmount));
			if(payableAmount<0){
				payableAmount = 0.0;
			}
			order.setAmount(newAmount);
			order.setPayableamount(payableAmount);
		}
				
		//保存翻译结果
		if(dealBean.getTrans()!=null && !dealBean.getTrans().isEmpty()){
			saveTransResult(dealBean, order.getDealstatus());
		}
		
		//变更状态
		if(order.getPayableamount()>0){
			order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_TRANSED);
		}else{
			order.setDealstatus(DictConstant.DICT_ORDER_DEALSTATUS_FINISH);
		}
		order.setFinishtime(now);
		order.setDealprogress(OrderUtil.dealStatus2ProgressStatus(order.getDealstatus()));
		transPicDao.update(order);
		
		//日志
		orderLogService.saveFlowLog(picId, order.getDealstatus(), dealBean.getOperator(), dealBean.getOperatorName(), now);
		if(!newAmount.equals(oriAmount)){
			orderLogService.saveOperLog(picId, order.getDealstatus(), dealBean.getOperator(), dealBean.getOperatorName(), "修改订单金额："+oriAmount+"->"+newAmount);
		}
		
		//参加活动
		if(DictConstant.DICT_ORDER_DEALSTATUS_FINISH.equals(order.getDealstatus())){
			FinishOrderActivityThrd.start(order);
		}
		
		//通知用户
		OrderNotifyThrd.notifyStatus(order);
	}
	
	/**
	 * 保存翻译结果，无校验订单
	 * @param dealBean
	 * @param dealStatus
	 */
	public void saveTransResult(TransPicDealBean dealBean, String dealStatus){
		Date now = new Date();

		//1.获取数据库中的翻译结果
		CmcPtPic pic = getPic(dealBean.getOrderId());
		List<CmcPtTrans> oldTransList = findTrans(dealBean.getOrderId());
		Map<Long, CmcPtTrans> oldTransMap = new HashMap<Long, CmcPtTrans>();
		for (CmcPtTrans cmcPtTrans : oldTransList) {
			oldTransMap.put(cmcPtTrans.getTransid(), cmcPtTrans);
		}
		
		//2.比较前后翻译结果，看是否做过修改
		boolean isSameOperator = true;
		if(StringUtil.notBlankAndNull(pic.getTranscd()) 
				&& !dealBean.getOperator().equals(pic.getTranscd())){
			isSameOperator = false;
		}
		
		boolean isModifyDst = false;
		if(oldTransList.size()!=dealBean.getTrans().size()){
			isModifyDst = true;
		}else{
			boolean hasMatch = false;
			for (CmcPtTrans trans : oldTransList) {
				hasMatch = false;
				for (TransPicTransBean transBean : dealBean.getTrans()) {
					if(trans.getPosx().equals(transBean.getX())
							&& trans.getPosy().equals(transBean.getY())
							&& trans.getDst().equals(transBean.getDst())){
						hasMatch = true;
						break;
					}
				}
				if(!hasMatch){
					isModifyDst = true;
					break;
				}
			}
		}
		
		//3.换人修改翻译，记录翻译历史
		if(isModifyDst && !isSameOperator){
			List<CmcPtTranshis> hises = new ArrayList<CmcPtTranshis>();
			CmcPtTranshis his = null;
			String verCode = CmcUtil.getTransVerCode(pic.getTranstime());
			for (CmcPtTrans trans : oldTransList) {
				his = new CmcPtTranshis();
				his.setPicid(trans.getPicid());
				his.setVercode(verCode);
				his.setDst(trans.getDst());
				his.setPosx(trans.getPosx());
				his.setPosy(trans.getPosy());
				his.setTranscd(pic.getTranscd());
				his.setTransname(pic.getTransname());
				his.setTranstime(pic.getTranstime());
				his.setOpercd(dealBean.getOperator());
				his.setOpername(dealBean.getOperatorName());
				his.setOpertime(now);
				hises.add(his);
			}
			transPicDao.saveOrUpdateAll(hises);
		}
		
		//4.更新图片和语言
		boolean changeFrom = StringUtil.notBlankAndNull(dealBean.getFrom())	&& !dealBean.getFrom().equals(pic.getFromlang());
		boolean changeTo = StringUtil.notBlankAndNull(dealBean.getTo())	&& !dealBean.getTo().equals(pic.getTolang());
		if(changeFrom)	pic.setFromlang(dealBean.getFrom());
		if(changeTo)	pic.setTolang(dealBean.getTo());

		boolean updatePic = false;
		if(StringUtil.notBlankAndNull(dealBean.getPicUrl())){
			pic.setPicurl(dealBean.getPicUrl());
			pic.setThumburl(dealBean.getThumbUrl());
			updatePic = true;
		}
		if(isModifyDst){
			pic.setTranscd(dealBean.getOperator());
			pic.setTransname(dealBean.getOperatorName());
			pic.setTranstime(now);
			updatePic = true;
		}
		if(isModifyDst || updatePic || changeFrom || changeTo){
			transPicDao.update(pic);
		}
		
		//修改图片，相应修改订单商品图片
		if(updatePic && StringUtil.notBlankAndNull(dealBean.getThumbUrl())){
			List<CmcRItem> items = orderService.findItem(dealBean.getOrderId());
			for (CmcRItem item : items) {
				item.setPic(dealBean.getThumbUrl());
			}
			transPicDao.saveOrUpdateAll(items);
		}
		
		
		if(!isModifyDst) return;
		
		//5.翻译结果入库
		//5.1整理数据
		List<CmcPtTrans> delList = new ArrayList<CmcPtTrans>();
		List<CmcPtTrans> newList = new ArrayList<CmcPtTrans>();
		List<Long> newTransIdList = new ArrayList<Long>();
		CmcPtTrans entity = null;
		for (TransPicTransBean transBean : dealBean.getTrans()) {
			if(transBean.getTransId()!=null){
				newTransIdList.add(transBean.getTransId());
				entity = oldTransMap.get(transBean.getTransId());
			}else{
				entity = new CmcPtTrans();
				entity.setPicid(dealBean.getOrderId());
				entity.setTransid(transBean.getTransId());
			}
			entity.setPosx(transBean.getX());
			entity.setPosy(transBean.getY());
			entity.setDst(transBean.getDst());
			newList.add(entity);
		}
		
		//5.2过滤出需要删除的数据
		for (CmcPtTrans trans : oldTransList) {
			if(!newTransIdList.contains(trans.getTransid())){
				delList.add(trans);
			}
		}
		if(!delList.isEmpty()){
			transPicDao.deleteAll(delList);
		}
		
		//5.3增加或更新
		if(!newList.isEmpty()){
			transPicDao.saveOrUpdateAll(newList);
		}
		
		//6.操作日志
		if(!isSameOperator){
			orderLogService.saveOperLog(dealBean.getOrderId(), dealStatus, dealBean.getOperator(), dealBean.getOperatorName(), "修改翻译结果");
		}
	}
	
	/**
	 * 更新图片地址
	 * @param picId
	 * @param picUrl
	 * @param thumbUrl
	 * 修改历史：
	 */
	public void updatePicUrl(String picId, String picUrl, String thumbUrl){
		transPicDao.updatePicUrl(picId, picUrl, thumbUrl);
	}
	
	/**
	 * 更新地址
	 * @param picId
	 * @param address
	 */
	public void updateAddress(String picId, String address){
		transPicDao.updateAddress(picId, address);
	}
	
	/**
	 * 旋转图片
	 * @param picId
	 * @param degree
	 * @param oper
	 * @return 新图片URL
	 * 修改历史：
	 */
	public boolean rotatePicAndSave(String picId, Integer degree, Operator oper){
		TransPicRotateResult result = rotatePic(picId, degree, oper);
		if(result==null){
			return false;
		}
		
		CmcPtPic cmcPtPic = getPic(picId);
		cmcPtPic.setPicurl(result.getPicUrl());
		cmcPtPic.setThumburl(result.getThumbUrl());
		transPicDao.saveOrUpdate(cmcPtPic);
		
		//修改图片，相应修改订单商品图片
		if(StringUtil.notBlankAndNull(result.getThumbUrl())){
			List<CmcRItem> items = orderService.findItem(cmcPtPic.getOrderid());
			for (CmcRItem item : items) {
				item.setPic(result.getThumbUrl());
			}
			transPicDao.saveOrUpdateAll(items);
		}
		
		return true;
	}
	
	/**
	 * 旋转图片
	 * @param picId
	 * @param degree
	 * @param oper
	 * @return 失败返回null
	 * 修改历史：
	 */
	public TransPicRotateResult rotatePic(String picId, Integer degree, Operator oper){
		TransPicRotateResult result = null;
		
		if(degree==null	|| degree<=0 || degree>=360) return result;
		
		CmcPtPic pic = getPic(picId);
		
		String picTempDirPath = UserUtil.getTransPicTempBasePath(pic.getUserid());
		
		//下载图片
		File picFie = FileServiceUtil.downloadOuterPic(pic.getPicurl(), picTempDirPath, false);
		if(!picFie.exists()) return result;
		
		//下载缩略图
		File thumbFie = FileServiceUtil.downloadOuterPic(pic.getThumburl(), picTempDirPath, false);
		if(!thumbFie.exists()) return result;

		//旋转图片
		boolean rotate = ImageUtil.rotateImage(picFie, picFie, degree);
		if(rotate){
			rotate = ImageUtil.rotateImage(thumbFie, thumbFie, degree);
		}
		
		//上传OSS
		String filePath = FileServiceUtil.url2FilePath(pic.getPicurl());
		String fileType = FileTypeUtil.getFileType(pic.getPicurl());
		String picDir = filePath.substring(0, filePath.lastIndexOf("/"));
		String newFileName = FilePathUtil.newFileName(fileType);
		String newPicUrl = FileServiceUtil.saveOrUploadFile(picFie, picDir, newFileName);
		if(StringUtil.notBlankAndNull(newPicUrl)){
			String newThumbUrl = FileServiceUtil.saveOrUploadFile(thumbFie, picDir, FilePathUtil.toThumb(newFileName));
			result = new TransPicRotateResult();
			result.setPicId(picId);
			result.setPicUrl(newPicUrl);
			result.setThumbUrl(newThumbUrl);
		}
		
		//删除临时文件
		FileUtil.deleteQuietly(picFie);
		FileUtil.deleteQuietly(thumbFie);
		
		return result;
	}
	
	/**
	 * 删除图片
	 * @param pic
	 * 修改历史：
	 */
	public void delPic(String picId, Operator oper){
		//删除订单信息
		orderService.deleteOrder(picId, oper);
		
		//删除图片翻译信息
		CmcPtPic pic = getPic(picId);
		transPicDao.clearTrans(picId);
		transPicDao.delete(pic);
		OperLogUtil.saveDelLog(pic.getPicid(), oper, pic);
		
		//删除文件
		String picUrl = pic.getPicurl();
		if(StringUtil.notBlankAndNull(picUrl)){
			String customUrl = AliyunOssUtil.getCustomUrl(AliyunOssUtil.BUCKETNAME_VOICETRANS);
			String picPath = picUrl.substring(customUrl.length()+1);
			String picDir = AliyunOssUtil.getFileDir(picPath);
			AliyunOssUtil.deleteDir(AliyunOssUtil.BUCKETNAME_VOICETRANS, picDir);
		}
	}
}
