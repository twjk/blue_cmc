package com.qcmz.cmc.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.ws.provide.vo.OrderLogBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.SecretUtil;
import com.qcmz.framework.util.StringUtil;

public class OrderUtil {
	/**
	 * 操作日志-已支付
	 */
	private static OrderLogBean paid = new OrderLogBean(DictConstant.DICT_ORDER_DEALSTATUS_PAID);
	/**
	 * 操作日志-翻译中
	 */
	private static OrderLogBean processing = new OrderLogBean(DictConstant.DICT_ORDER_DEALSTATUS_PROCESSING);
	/**
	 * 操作日志-已完成
	 */
	private static OrderLogBean finish = new OrderLogBean(DictConstant.DICT_ORDER_DEALSTATUS_FINISH);
	
	/**
	 * 支持的交易途径
	 */
	public static List<String> TRADEWAYS_COMMON = new ArrayList<String>();
	/**
	 * 订阅支持的交易途径
	 */
	public static List<String> TRADEWAYS_SUB = new ArrayList<String>();
	static{
		TRADEWAYS_COMMON.add(DictConstants.DICT_TRADEWAY_WXPAY);
		TRADEWAYS_COMMON.add(DictConstants.DICT_TRADEWAY_ALIPAY);
		
		TRADEWAYS_SUB.add(DictConstants.DICT_TRADEWAY_WXPAY);
		TRADEWAYS_SUB.add(DictConstants.DICT_TRADEWAY_ALIPAY);
		TRADEWAYS_SUB.add(DictConstants.DICT_TRADEWAY_APPLEPAY);
	}
	
	
	/**
	 * 获取支持的支付方式
	 * @param orderCat
	 * @return
	 */
	public static List<String> findTradeWay(Integer orderCat){
		if(DictConstant.DICT_ORDERCAT_SUB.equals(orderCat)){
			return TRADEWAYS_SUB;
		}else{
			return TRADEWAYS_COMMON;
		}
	}
	
	/**
	 * 获取支付描述
	 * @param orderTypeDesc
	 * @param orderId
	 * @return
	 */
	public static String getPayDesc(String orderTypeDesc, String orderId){
		return new StringBuilder(orderTypeDesc).append("-").append(orderId).toString();
	}
	
	/**
	 * 加密订单编号
	 * @param orderId
	 * @return
	 * 修改历史：
	 */
	public static String encryptOrderId(String orderId){
		return SecretUtil.encryptByMd5(orderId).toLowerCase();
	}
	
	/**
	 * 处理状态兼容处理
	 * @param dealStatus
	 * @param platform
	 * @param version
	 * @return
	 * 修改历史：
	 */
	public static String dealStatusCompat(String dealStatus){
		if(DictConstant.DICT_ORDER_DEALSTATUS_WAITTRANS.equals(dealStatus)){
			dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_PAID;
		}else if(DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY.equals(dealStatus)){
			dealStatus = DictConstant.DICT_ORDER_DEALSTATUS_SUBMITTED;
		}
		return dealStatus;
	}
	
	/**
	 * 处理状态转为处理进度
	 * @param dealStatus
	 * @return
	 * 修改历史：
	 */
	public static String dealStatus2ProgressStatus(String dealStatus){
		if(DictConstant.DICT_ORDER_DEALSTATUS_PAID.equals(dealStatus)
				|| DictConstant.DICT_ORDER_DEALSTATUS_WAITTRANS.equals(dealStatus)
				|| DictConstant.DICT_ORDER_DEALSTATUS_WAITDUB.equals(dealStatus)){
			return DictConstants.DICT_DEALPROGRESS_WAITING;
		}else if(DictConstant.DICT_ORDER_DEALSTATUS_PROCESSING.equals(dealStatus)
				|| DictConstant.DICT_ORDER_DEALSTATUS_DUBING.equals(dealStatus)
				|| DictConstant.DICT_ORDER_DEALSTATUS_DEALING.equals(dealStatus)){
			return DictConstants.DICT_DEALPROGRESS_PROCESSING;
		}else if(DictConstant.DICT_ORDER_DEALSTATUS_FINISH.equals(dealStatus)
				|| DictConstant.DICT_ORDER_DEALSTATUS_CANCEL.equals(dealStatus)
				|| DictConstant.DICT_ORDER_DEALSTATUS_REFUNDED.equals(dealStatus)
				|| DictConstant.DICT_ORDER_DEALSTATUS_TRANSED.equals(dealStatus)
				){
			return DictConstants.DICT_DEALPROGRESS_DEALT;
		}
		return null;
	}
	
	/**
	 * 操作员是否可以修改预约时间
	 * @param dealProgress
	 * @return
	 * 修改历史：
	 */
	public static boolean canModAppointTime(String dealProgress){
		return DictConstants.DICT_DEALPROGRESS_WAITING.equals(dealProgress);
	}
	
	/**
	 * 操作员是否可以接单
	 * @param orderCat
	 * @param dealProgress
	 * @param waitTime
	 * @return
	 * 修改历史：
	 */
	public static boolean canAccept(Integer orderCat, String dealProgress, Long waitTime){
		Date wt = null;
		if(waitTime!=null){
			wt = new Date(waitTime);
		}
		return canAccept(orderCat, dealProgress, wt);
	}
	
	/**
	 * 操作员是否可以接单
	 * @param orderCat
	 * @param dealProgress
	 * @param waitTime
	 * @return
	 * 修改历史：
	 */
	public static boolean canAccept(Integer orderCat, String dealProgress, Date waitTime){
		if(waitTime==null) return false;
		
		if(DictConstant.DICT_ORDERCAT_TRANS.equals(orderCat)){
			if(DictConstants.DICT_DEALPROGRESS_WAITING.equals(dealProgress)){
				return DateUtil.betweenMinute(new Date(), waitTime)<=2;
			}
		}else if(DictConstant.DICT_ORDERCAT_DUB.equals(orderCat)
				|| DictConstant.DICT_ORDERCAT_CROWDTASK.equals(orderCat)){
			return DictConstants.DICT_DEALPROGRESS_WAITING.equals(dealProgress);
		}
		
		return false;
	}
	
	/**
	 * 操作员是否可以交单
	 * @param orderCat
	 * @param dealProgress
	 * @return
	 * 修改历史：
	 */
	public static boolean canHandover(Integer orderCat, String dealProgress){
		if(DictConstant.DICT_ORDERCAT_TRANS.equals(orderCat)){
			if(DictConstants.DICT_DEALPROGRESS_PROCESSING.equals(dealProgress)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 订单是否可以捡漏
	 * @param serviceType
	 * @param orderType
	 * @param platform
	 * @param version
	 * @param dealStatus
	 * @return
	 */
	public static boolean canPick(String serviceType, Integer orderType, String platform, String version, String dealStatus){
		if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(orderType)
				&& DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY.equals(dealStatus)){
			if(DictConstants.DICT_SERVICETYPE_VOICETRANS.equals(serviceType)){
				if(DictConstants.DICT_PLATFORM_ANDROID.equals(platform) && StringUtil.afterVersion(version, "2.6.7")){
					return true;
				}
				if(DictConstants.DICT_PLATFORM_IOS.equals(platform) && StringUtil.afterVersion(version, "2.7.1")){
					return true;
				}
				return false;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 操作员是否可以拒单
	 * @param orderCat
	 * @param dealStatus
	 * @return
	 * 修改历史：
	 */
	public static boolean canReject(Integer orderCat, String dealStatus){
		if(DictConstant.DICT_ORDERCAT_TRANS.equals(orderCat)){
			if(DictConstant.DICT_ORDER_DEALSTATUS_WAITTRANS.equals(dealStatus)
					|| DictConstant.DICT_ORDER_DEALSTATUS_PROCESSING.equals(dealStatus)){
				return true;
			}
		}else if(DictConstant.DICT_ORDERCAT_DUB.equals(orderCat)){
			if(DictConstant.DICT_ORDER_DEALSTATUS_WAITDUB.equals(dealStatus)
					|| DictConstant.DICT_ORDER_DEALSTATUS_DUBING.equals(dealStatus)){
				return true;
			}
		}else if(DictConstant.DICT_ORDER_DEALSTATUS_DEALING.equals(dealStatus)){
			return true;
		}
		return false;
	}
	
	/**
	 * 用户是否可以取消订单
	 * @param orderCat
	 * @param dealStatus
	 * @return
	 */
	public static boolean canCancel(Integer orderCat, Integer orderType, String dealStatus){
		if(DictConstant.DICT_ORDERCAT_SUB.equals(orderCat)){
			if(DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY.equals(dealStatus)){
				return true;
			}
		}else{
			if(DictConstant.DICT_ORDER_DEALSTATUS_SUBMITTED.equals(dealStatus)
					|| DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY.equals(dealStatus)){
				return true;
			}
			if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)
					&& DictConstant.DICT_ORDER_DEALSTATUS_WAITTRANS.equals(dealStatus)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 是否可以处理翻译
	 * @param dealStatus
	 * @param dealProgress
	 * @return
	 */
	public static boolean canTrans(String dealStatus, String dealProgress){
		if(DictConstant.DICT_ORDER_DEALSTATUS_PROCESSING.equals(dealStatus) 
				&& DictConstants.DICT_DEALPROGRESS_PROCESSING.equals(dealProgress)){
			return true;
		}else if(DictConstant.DICT_ORDER_DEALSTATUS_FINISH.equals(dealStatus)
				|| DictConstant.DICT_ORDER_DEALSTATUS_TRANSED.equals(dealStatus)){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否可以捡漏翻译
	 * @param dealStatus
	 * @return
	 */
	public static boolean canPickTrans(Integer pickStatus){
		return SystemConstants.STATUS_OPER_ALREADY.equals(pickStatus);
	}
	
	/**
	 * 是否可以处理已翻译
	 * @param dealStatus
	 * @param pickStatus
	 * @return
	 */
	public static boolean canCompleteTrans(String dealStatus, Integer pickStatus){
		if(DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY.equals(dealStatus)
				&& SystemConstants.STATUS_OPER_ALREADY.equals(pickStatus)){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否可以处理完成订单
	 * @param dealProgress
	 * @return
	 * 修改历史：
	 */
	public static boolean canFinish(String dealProgress){
		return DictConstants.DICT_DEALPROGRESS_PROCESSING.equals(dealProgress);
	}

	/**
	 * 是否可以开始校对
	 * @param checkStatus
	 * @return
	 */
	public static boolean canStartCheck(Integer checkStatus){
		return DictConstants.DICT_CHECKSTATUS_UNCHECK.equals(checkStatus);
	}
	
	/**
	 * 是否可以完成校对
	 * @param checkStatus
	 * @return
	 */
	public static boolean canFinishCheck(Integer checkStatus){
		return DictConstants.DICT_CHECKSTATUS_CHECKING.equals(checkStatus);
	}

	/**
	 * 是否可以删除
	 * @param dealStatus
	 * @return
	 * 修改历史：
	 */
	public static boolean canDelete(String dealStatus){
		return DictConstant.DICT_ORDER_DEALSTATUS_MACHINE.equals(dealStatus);
	}
	
	/**
	 * 操作员是否可以强行退款
	 * @param payAmount
	 * @param tradeWay
	 * @return
	 */
	public static boolean canForceRefund(double walletAmount, double payAmount, String tradeWay){
		if(payAmount>0){
			if(DictConstants.DICT_TRADEWAY_ALIPAY.equals(tradeWay)
					|| DictConstants.DICT_TRADEWAY_WXPAY.equals(tradeWay)){
				return true;
			}
		}else if(walletAmount>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否展示翻译结果
	 * @param dealStatus
	 * @return
	 * 修改历史：
	 */
	public static boolean showTransResult(String dealStatus){
		if(DictConstant.DICT_ORDER_DEALSTATUS_MACHINE.equals(dealStatus)
				|| DictConstant.DICT_ORDER_DEALSTATUS_FINISH.equals(dealStatus)
				|| DictConstant.DICT_ORDER_DEALSTATUS_REFUNDED.equals(dealStatus)){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取未发生的日志
	 * @param dealStatus
	 * @return
	 * 修改历史：
	 */
	public static List<OrderLogBean> getNotHappenLog(String dealStatus){
		List<OrderLogBean> result = new ArrayList<OrderLogBean>();
		if(DictConstant.DICT_ORDER_DEALSTATUS_WAITPAY.equals(dealStatus)){
			result.add(paid);
			result.add(processing);
			result.add(finish);
		}else if(DictConstant.DICT_ORDER_DEALSTATUS_WAITTRANS.equals(dealStatus)){
			result.add(processing);
			result.add(finish);
		}else if(DictConstant.DICT_ORDER_DEALSTATUS_PROCESSING.equals(dealStatus)){
			result.add(finish);
		}
		return result;
	}
}
