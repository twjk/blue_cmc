package com.qcmz.cmc.process;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.EvalMap;
import com.qcmz.cmc.cache.TransPicLangMap;
import com.qcmz.cmc.config.TransConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcEval;
import com.qcmz.cmc.entity.CmcPtPic;
import com.qcmz.cmc.entity.CmcREval;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcWalletAccount;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITransPicService;
import com.qcmz.cmc.service.IWalletAccountService;
import com.qcmz.cmc.thrd.FinishOrderActivityThrd;
import com.qcmz.cmc.thrd.OrderCheckNotifyCsThrd;
import com.qcmz.cmc.thrd.OrderCommissionThrd;
import com.qcmz.cmc.thrd.OrderNotifyThrd;
import com.qcmz.cmc.thrd.OrderRegeocodeThrd;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.util.OrderUtil;
import com.qcmz.cmc.util.UserUtil;
import com.qcmz.cmc.ws.provide.vo.OrderCreateResult;
import com.qcmz.cmc.ws.provide.vo.OrderDealQueryBean;
import com.qcmz.cmc.ws.provide.vo.OrderEvalDetailBean;
import com.qcmz.cmc.ws.provide.vo.OrderPrepayBean;
import com.qcmz.cmc.ws.provide.vo.PrepayResult;
import com.qcmz.cmc.ws.provide.vo.TransPicAddBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealBean;
import com.qcmz.cmc.ws.provide.vo.TransPicDealListBean;
import com.qcmz.cmc.ws.provide.vo.TransPriceBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.ExceptionConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.FileServiceUtil;
import com.qcmz.framework.util.FileTypeUtil;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.ImageUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.ws.vo.Response;
import com.qcmz.umc.ws.provide.locator.UserCouponWS;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

/**
 * 类说明：图片翻译处理
 * 修改历史：
 */
@Component
public class TransPicProcess {
	@Autowired
	private ITransPicService transPicService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IWalletAccountService walletAccountService;
	
	@Autowired
	private TransPicLangMap transPicLangMap;
	@Autowired
	private EvalMap evalMap;
	@Autowired
	private TransPriceProcess transPriceProcess;
	@Autowired
	private OrderProcess orderProcess;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 分页获取图片翻译列表
	 * @param query
	 * @param pageSize
	 * @return
	 */
	public List<TransPicDealListBean> findPic4Deal(OrderDealQueryBean query, int pageSize){
		List<TransPicDealListBean> result = transPicService.findPic4Deal(query, pageSize);
		if(result.isEmpty()) return result;
		
		
		Set<Long> userIds = new HashSet<Long>();
		List<String> evalOrderIds = new ArrayList<String>();
		for (TransPicDealListBean bean : result) {
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
		for (TransPicDealListBean bean : result) {
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
	 * 添加翻译图片
	 * @param bean
	 * @param platform
	 * @return
	 * @exception ParamException,DataException
	 * 修改历史：
	 */
	public OrderCreateResult addPic(TransPicAddBean bean, String serviceType, String platform, String version){
		OrderCreateResult result = new OrderCreateResult();
		
		//1.数据校验
		//图片校验
		String fileType = FileTypeUtil.getImageType(bean.getFile());
		if(!"jpg".equals(fileType) && !"png".equals(fileType)){
			throw new ParamException("图片格式有误");
		}
		//语言校验
		if(!transPicLangMap.contains(bean.getTransWay(), bean.getFrom(), bean.getTo())){
			throw new ParamException("语言不支持");
		}
		//价格校验
		TransPriceBean price = transPriceProcess.getTransPicPrice(bean.getTransWay(), bean.getSrc(), bean.getFrom(), bean.getTo(), null);
		if(price==null){
			throw new ParamException("价格有误");
		}
		if(bean.getAmount()==0){
			bean.setAmount(price.getTotalPrice());		//兼容老版本没有上传金额
		}else if(price.getTotalPrice()>bean.getAmount()){
			throw new ParamException("价格有误");
		}
		
		//抵扣金额校验
		if(bean.getWalletAmount()>0){
			CmcWalletAccount account = walletAccountService.getAccount(bean.getUid());
			if(account==null || bean.getWalletAmount()>account.getBalance()){
				throw new DataException("钱包余额不足");
			}
		}
		
		String orderId = orderService.newOrderId();
		
		//2.图片文件存储
		String picPath = null;
		String thumbPath = null;
		String picUrl = null;
		String thumbUrl = null;
		
		//图片
		String dirPath = UserUtil.getTransPicBasePath(orderId);
		String fileName = FilePathUtil.newFileName(fileType);
		
		picPath = new StringBuilder(dirPath).append("/").append(fileName).toString();
		picUrl = FileServiceUtil.saveOrUploadFile(bean.getFile(), dirPath, fileName);

		
		if(StringUtil.isBlankOrNull(picUrl)){
			throw new SystemException("图片保存失败");
		}
		
		//缩略图
		thumbPath = FilePathUtil.toThumb(picPath);
		if(bean.getThumb()==null){
			String picTempPath = UserUtil.getTransPicTempPath(bean.getUid(), FilePathUtil.getFileName(picPath));
			FileUtil.writeToFile(bean.getFile(), picTempPath);
			
			String thumbTempPath = UserUtil.getTransPicTempPath(bean.getUid(), FilePathUtil.getFileName(thumbPath));
			boolean zoom = ImageUtil.zoomImageScale(new File(picTempPath), TransConfig.TRANSPIC_THUMB_MAXWIDTH, TransConfig.TRANSPIC_THUMB_MAXHEIGHT, thumbTempPath);
			if(zoom){
				thumbUrl = FileServiceUtil.saveOrUploadFile(new File(thumbTempPath), dirPath);
			}
			
			//删除临时文件
			FileUtil.deleteQuietly(picTempPath);
			FileUtil.deleteQuietly(thumbTempPath);
		}else{
			thumbUrl = FileServiceUtil.saveOrUploadFile(bean.getThumb(), dirPath);
		}
		if(StringUtil.isBlankOrNull(thumbUrl)){
			throw new SystemException("缩略图保存失败");
		}
		
		//使用优惠券
		boolean useCoupon = false;
		if(bean.getCouponId()!=null && bean.getCouponAmount()>0){
			Response resp = UserCouponWS.useCoupon(bean.getUid(), DictConstants.DICT_SUBSERVICETYPE_TRANSPIC, orderId, bean.getCouponId(), bean.getCouponAmount(), bean.getAmount());
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
		
		//保存图片订单信息
		try {
			CmcROrder order = transPicService.addPic(orderId, bean, price, picUrl, thumbUrl, serviceType, platform, version);
			result.setOrderId(orderId);
			result.setDealStatus(order.getDealstatus());
			
			//经纬度->地址
			if(StringUtil.isBlankOrNull(bean.getAddress())
					&& StringUtil.notBlankAndNull(bean.getLon())
					&& StringUtil.notBlankAndNull(bean.getLat())){
				OrderRegeocodeThrd.start(orderId, bean.getLon(), bean.getLat());
			}
			
			//创建订单后，预支付处理，失败返回空，客户端重新发起预支付
			if(DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY.equals(order.getDealstatus())
					&& StringUtil.notBlankAndNull(bean.getTradeWay())){
				try {
					OrderPrepayBean prepayBean = new OrderPrepayBean();
					prepayBean.setOrderId(order.getOrderid());
					prepayBean.setUid(order.getUserid());
					prepayBean.setAmount(order.getPayableamount());
					prepayBean.setTradeWay(bean.getTradeWay());
					PrepayResult prepayResult = orderProcess.prepay(prepayBean, platform);
					result.setPrepayResult(prepayResult);
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
			throw new SystemException();
		}
		
	}
	
	/**
	 * 机器翻译转人工翻译
	 * @param picId
	 * @param userId
	 * @param requirement
	 * 修改历史：
	 */
	public void toHuman(Long userId, String picId, String requirement){
		CmcPtPic pic = transPicService.getPic(userId, picId);
		if(pic==null){
			throw new ParamException(ExceptionConstants.MSG_DATA_NOTEXIST);
		}
		
		//价格校验
		TransPriceBean price = transPriceProcess.getTransPrice(DictConstants.DICT_TRANSTYPE_PIC, pic.getTransway(), pic.getSrc(), pic.getFromlang(), pic.getTolang(), null);
		if(price==null){
			throw new ParamException("价格有误");
		}
		
		transPicService.toHuman(userId, picId, requirement, price.getTotalPrice(), 0);
	}
	
	/**
	 * 完成图片翻译
	 * @param dealBean
	 * 修改历史：
	 */
	public void finishTrans(TransPicDealBean dealBean){
		CmcROrder order = orderService.getOrder(dealBean.getOrderId());
		
		//校验能否完成
		if(!OrderUtil.canFinish(order.getDealprogress())){
			throw new DataException(ExceptionConstants.MSG_OPER_CANNOT);
		}
		
		//完成图片翻译相关信息入库
		transPicService.finishTrans(dealBean, order.getDealstatus());
		
		//获取最新的订单信息
		order = orderService.getOrder(dealBean.getOrderId());
		
		//佣金结算
		OrderCommissionThrd.start(order);
		
		//参加活动
		FinishOrderActivityThrd.start(order);
		
		//通知用户
		OrderNotifyThrd.notifyStatus(order, null, null);

		//通知校对组校对
		if(DictConstants.DICT_CHECKSTATUS_UNCHECK.equals(order.getCheckstatus())){
			OrderCheckNotifyCsThrd.newCheckNotify(order.getOrderid(), order.getOrdertype(), order.getFromlang(), order.getTolang());
		}
	}
}
