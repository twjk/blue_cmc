package com.qcmz.cmc.constant;

import java.util.ArrayList;
import java.util.List;

import com.qcmz.framework.constant.DictConstants;

/**
 * 类说明：数据字典相关常量
 * 修改历史：
 */
public class DictConstant {
	/**
	 * 字典名称--数据字典
	 */
	public static final String DICTNAME_DICTIONARY = "dictionary";
	/**
	 * 字典名称--分类
	 */
	public static final String DICTNAME_CAT = "cat";
	
	/**
	 * 译库类型-01译库
	 */
	public static final String DICT_LIBTYPE_LIB = "01";
	/**
	 * 译库类型-02日常用语
	 */
	public static final String DICT_LIBTYPE_DAILY = "02";
	/**
	 * 译库类型-03高频用语
	 */
	public static final String DICT_LIBTYPE_FREQUENT = "03";
	/**
	 * 译库分类-酒店
	 */
	public static final String DICT_LIBCAT_HOTEL = "12581261";
	/**
	 * 译库分类-机场
	 */
	public static final String DICT_LIBCAT_AIRPORT = "12591262";
	/**
	 * 译库来源-01系统
	 */
	public static final String DICT_LIBSOURCE_SYSTEM = "01";
	/**
	 * 译库来源-02录入
	 */
	public static final String DICT_LIBSOURCE_INPUT = "02";
	/**
	 * 译库来源-03Booking
	 */
	public static final String DICT_LIBSOURCE_BOOKING = "03";
	
	/**
	 * 翻译途径-01机器翻译
	 */
	public static final String DICT_TRANSWAY_MACHINE = "01";
	/**
	 * 翻译途径-02概要翻译
	 */
	public static final String DICT_TRANSWAY_OVERVIEW = "02";
	/**
	 * 翻译途径-03详细翻译
	 */
	public static final String DICT_TRANSWAY_DETAIL = "03";
	
	/**
	 * 翻译模式-1即时翻译
	 */
	public static final Integer DICT_TRANSMODEL_FREE = 1;
	/**
	 * 翻译模式-2陪同翻译
	 */
	public static final Integer DICT_TRANSMODEL_CONSECUTIVE = 2;
	
	/**
	 * 订单处理状态-01机器翻译
	 */
	public static final String DICT_ORDER_DEALSTATUS_MACHINE = "01";
	/**
	 * 订单处理状态-02已提交
	 */
	public static final String DICT_ORDER_DEALSTATUS_SUBMITTED = "02";
	/**
	 * 订单处理状态-03已支付
	 */
	public static final String DICT_ORDER_DEALSTATUS_PAID = "03";
	/**
	 * 订单处理状态-04翻译中
	 */
	public static final String DICT_ORDER_DEALSTATUS_PROCESSING = "04";
	/**
	 * 订单处理状态-05已完成
	 */
	public static final String DICT_ORDER_DEALSTATUS_FINISH = "05";
	/**
	 * 订单处理状态-06已取消
	 */
	public static final String DICT_ORDER_DEALSTATUS_CANCEL = "06";
	/**
	 * 订单处理状态-07已退款
	 */
	public static final String DICT_ORDER_DEALSTATUS_REFUNDED = "07";
	/**
	 * 订单处理状态-08已关闭
	 */
	public static final String DICT_ORDER_DEALSTATUS_CLOSE = "08";
	/**
	 * 订单处理状态-09待支付
	 */
	public static final String DICT_ORDER_DEALSTATUS_WAITPAY = "09";
	/**
	 * 订单处理状态-10待翻译
	 */
	public static final String DICT_ORDER_DEALSTATUS_WAITTRANS = "10";
	/**
	 * 订单处理状态-11待处理
	 */
	public static final String DICT_ORDER_DEALSTATUS_WAITDEAL = "11";
	/**
	 * 订单处理状态-12已订阅
	 */
	public static final String DICT_ORDER_DEALSTATUS_SUB = "12";
	/**
	 * 订单处理状态-13已翻译，待付款
	 */
	public static final String DICT_ORDER_DEALSTATUS_TRANSED = "13";
	/**
	 * 订单处理状态-14待配音
	 */
	public static final String DICT_ORDER_DEALSTATUS_WAITDUB = "14";
	/**
	 * 订单处理状态-15正在配音
	 */
	public static final String DICT_ORDER_DEALSTATUS_DUBING = "15";
	/**
	 * 订单处理状态-16处理中
	 */
	public static final String DICT_ORDER_DEALSTATUS_DEALING = "16";
	
	/**
	 * 订单处理状态-已处理
	 */
	public static final List<String> DICT_ORDER_DEALSTATUS_DEALT = new ArrayList<String>();
	static{
		DICT_ORDER_DEALSTATUS_DEALT.add(DICT_ORDER_DEALSTATUS_FINISH);
		DICT_ORDER_DEALSTATUS_DEALT.add(DICT_ORDER_DEALSTATUS_CANCEL);
		DICT_ORDER_DEALSTATUS_DEALT.add(DICT_ORDER_DEALSTATUS_REFUNDED);
	}

	/**
	 * 交易状态-01成功
	 */
	public static final String DICT_TRADESTATUS_SUCCESS = "01";
	/**
	 * 交易状态-02失败
	 */
	public static final String DICT_TRADESTATUS_FAIL = "02";
	/**
	 * 交易状态-03取消关闭
	 */
	public static final String DICT_TRADESTATUS_CANCEL = "03";
	
	/**
	 * 日志类型-01流转
	 */
	public static final String DICT_LOGTYPE_FLOW = "01";
	/**
	 * 日志类型-02操作
	 */
	public static final String DICT_LOGTYPE_OPER = "02";
	
	/**
	 * 人员类型-01用户
	 */
	public static final String DICT_SIDETYPE_USER = "01";
	/**
	 * 人员类型-02客服
	 */
	public static final String DICT_SIDETYPE_CS = "02";
	
	/**
	 * 计价单位-1订单
	 */
	public static final Integer DICT_PRICEUNIT_ORDER = 1;
	/**
	 * 计价单位-2字词
	 */
	public static final Integer DICT_PRICEUNIT_WORD = 2;
	/**
	 * 计价单位-3分钟
	 */
	public static final Integer DICT_PRICEUNIT_MINUTE = 3;
	
	/**
	 * 合成方式-0按译文合成
	 */
	public static final Integer DICT_TTSMETHOD_DST = 0;
	/**
	 * 合成方式-1按原文合成
	 */
	public static final Integer DICT_TTSMETHOD_SRC = 1;
	/**
	 * 合成方式-2指定译文合成
	 */
	public static final Integer DICT_TTSMETHOD_DSTTEXT = 2;

	/**
	 * 代理账户使用范围-通用
	 */
	public static final Integer DICT_PROXYACCOUNT_USESCOPE_COMMON = 0;
	/**
	 * 代理账户使用范围-普通用户
	 */
	public static final Integer DICT_PROXYACCOUNT_USESCOPE_GENERAL = 1;
	/**
	 * 代理账户使用范围-翻译机
	 */
	public static final Integer DICT_PROXYACCOUNT_USESCOPE_TM = 2;
	public static Integer[] DICT_PROXYACCOUNT_USESCOPES = new Integer[]{
		DICT_PROXYACCOUNT_USESCOPE_COMMON, DICT_PROXYACCOUNT_USESCOPE_GENERAL, DICT_PROXYACCOUNT_USESCOPE_TM
	};
	
	/**
	 * 锁类型，定时任务
	 */
	public final static String DICT_LOCKTYPE_JOB = "JOB";
	/**
	 * 锁类型-创建红包
	 */
	public static final String DICT_LOCKTYPE_REDPACKET_CREATE = "REDPACKET_CREATE";
	/**
	 * 锁类型-领红包
	 */
	public static final String DICT_LOCKTYPE_REDPACKET_RECEIVE = "REDPACKET_RECEIVE";
	/**
	 * 锁类型-申请提现
	 */
	public static final String DICT_LOCKTYPE_REDPACKET_APPLYCASH = "REDPACKET_APPLYCASH";
	/**
	 * 锁类型-接单
	 */
	public static final String DICT_LOCKTYPE_ORDER_ACCEPT = "ORDER_ACCEPT";
	/**
	 * 锁类型-众包任务报名
	 */
	public static final String DICT_LOCKTYPE_USERCROWDTASK_APPLY = "USERCROWDTASK_APPLY";
	/**
	 * 锁类型-众包任务审核
	 */
	public static final String DICT_LOCKTYPE_USERCROWDTASK_VEIRFY = "USERCROWDTASK_VERIFY";
	/**
	 * 锁类型-众包任务审核通过
	 */
	public static final String DICT_LOCKTYPE_USERCROWDTASK_PASSVEIRFY = "USERCROWDTASK_PASSVEIRFY";
	/**
	 * 锁类型-众包任务审校
	 */
	public static final String DICT_LOCKTYPE_USERCROWDTASK_QC = "USERCROWDTASK_QC";
	/**
	 * 锁类型-奖励金申请提现
	 */
	public static final String DICT_LOCKTYPE_REWARD_APPLYCASH = "REWARD_APPLYCASH";
	/**
	 * 锁类型-同城任务采集处理
	 */
	public static final String DICT_LOCKTYPE_LOCALTASK_SPDDEAL = "LOCALTASK_SPDDEAL";
	
	/**
	 * 任务类型-01完成订单活动
	 */
	public static final String DICT_TASKTYPE_FINISHORDER = "01";
	/**
	 * 任务类型-02评价订单活动
	 */
	public static final String DICT_TASKTYPE_EVALORDER = "02";
	
	/**
	 * 订单分类-1翻译
	 */
	public final static Integer DICT_ORDERCAT_TRANS = 1;
	/**
	 * 订单分类-2订阅
	 */
	public final static Integer DICT_ORDERCAT_SUB = 2;
	/**
	 * 订单分类-3优惠套餐
	 */
	public final static Integer DICT_ORDERCAT_PACKAGE = 3;
	/**
	 * 订单分类-4配音
	 */
	public final static Integer DICT_ORDERCAT_DUB = 4;
	/**
	 * 订单分类-5众包任务
	 */
	public final static Integer DICT_ORDERCAT_CROWDTASK = 5;
	
	/**
	 * 订单处理分组-1文字翻译（图片翻译、文本翻译）
	 */
	public final static Integer DICT_ORDER_DEALGROUP_TRANS = 1;
	/**
	 * 订单处理分组-2订阅
	 */
	public final static Integer DICT_ORDER_DEALGROUP_SUB = 2;
	/**
	 * 订单处理分组-3视频口译
	 */
	public final static Integer DICT_ORDER_DEALGROUP_TRANSVIDEO = 2;
	
	/**
	 * 订单类型-1图片翻译
	 */
	public final static Integer DICT_ORDERTYPE_TRANSPIC = 1;
	/**
	 * 订单类型-2梅出过国订阅
	 */
	public final static Integer DICT_ORDERTYPE_SUBMCGG = 2;
	/**
	 * 订单类型-3短文快译
	 */
	public final static Integer DICT_ORDERTYPE_TRANSTEXT = 3;
	/**
	 * 订单类型-4面包英语订阅
	 */
	public final static Integer DICT_ORDERTYPE_SUBBREADENGLISH = 4;
	/**
	 * 订单类型-5视频口译
	 */
	public final static Integer DICT_ORDERTYPE_TRANSVIDEO = 5;
	/**
	 * 订单类型-6优惠券包
	 */
	public final static Integer DICT_ORDERTYPE_PACKAGE = 6;
	/**
	 * 订单类型-7去广告
	 */
	public final static Integer DICT_ORDERTYPE_NOAD = 7;
	/**
	 * 订单类型-8翻译套餐
	 */
	public final static Integer DICT_ORDERTYPE_TRANSCOMBO = 8;
	/**
	 * 订单类型-9翻译特权
	 */
	public final static Integer DICT_ORDERTYPE_TRANSVIP = 9;
	/**
	 * 订单类型-10VIP会员
	 */
	public final static Integer DICT_ORDERTYPE_USERVIP = 10;
	/**
	 * 订单类型-11机器配音
	 */
	public final static Integer DICT_ORDERTYPE_DUB = 11;
	/**
	 * 订单类型-12真人配音
	 */
	public final static Integer DICT_ORDERTYPE_HUMANDUB = 12;
	/**
	 * 订单类型-13用户调研
	 */
	public final static Integer DICT_ORDERTYPE_RESEARCH = 13;
	/**
	 * 订单类型-14功能测试
	 */
	public final static Integer DICT_ORDERTYPE_TESTING = 14;
	/**
	 * 订单类型-15应用加热
	 */
	public final static Integer DICT_ORDERTYPE_HELPAPP = 15;
	/**
	 * 订单类型-16多多助力
	 */
	public final static Integer DICT_ORDERTYPE_HELPPDD = 16;
	/**
	 * 订单类型-17帮忙投票
	 */
	public final static Integer DICT_ORDERTYPE_HELPVOTE = 17;
	/**
	 * 订单类型-18公众号起量
	 */
	public final static Integer DICT_ORDERTYPE_HELPWX = 18;
	
	
	/**
	 * 订单类型集合-翻译（图片、文本、视频）
	 */
	public static List<Integer> DICT_ORDERTYPES_TRANS = new ArrayList<Integer>();
	static{
		DICT_ORDERTYPES_TRANS.add(DICT_ORDERTYPE_TRANSPIC);
		DICT_ORDERTYPES_TRANS.add(DICT_ORDERTYPE_TRANSTEXT);
		DICT_ORDERTYPES_TRANS.add(DICT_ORDERTYPE_TRANSVIDEO);
	}
	/**
	 * 订单类型集合-订阅（梅出过国、面包英语、去广告、翻译特权、VIP会员）
	 */
	public static List<Integer> DICT_ORDERTYPES_SUB = new ArrayList<Integer>();
	static{
		DICT_ORDERTYPES_SUB.add(DICT_ORDERTYPE_NOAD);
		DICT_ORDERTYPES_SUB.add(DICT_ORDERTYPE_TRANSVIP);
		DICT_ORDERTYPES_SUB.add(DICT_ORDERTYPE_USERVIP);
		DICT_ORDERTYPES_SUB.add(DICT_ORDERTYPE_SUBMCGG);
		DICT_ORDERTYPES_SUB.add(DICT_ORDERTYPE_SUBBREADENGLISH);
	}
	/**
	 * 订单类型集合-套餐（优惠券包、翻译套餐）
	 */
	public static List<Integer> DICT_ORDERTYPES_PACKAGE = new ArrayList<Integer>();
	static{
		DICT_ORDERTYPES_PACKAGE.add(DICT_ORDERTYPE_PACKAGE);
		DICT_ORDERTYPES_PACKAGE.add(DICT_ORDERTYPE_TRANSCOMBO);
	}
	
	/**
	 * 订单类型集合-配音（机器配音、真人配音）
	 */
	public static List<Integer> DICT_ORDERTYPES_DUB = new ArrayList<Integer>();
	static{
		DICT_ORDERTYPES_DUB.add(DICT_ORDERTYPE_DUB);
		DICT_ORDERTYPES_DUB.add(DICT_ORDERTYPE_HUMANDUB);
	}
	
	/**
	 * 订单类型集合-众包任务（用户调研、功能测试、应用加热、多多助力、帮忙投票、公众号起量）
	 */
	public static List<Integer> DICT_ORDERTYPES_CROWDTASK = new ArrayList<Integer>();
	static{
		DICT_ORDERTYPES_CROWDTASK.add(DICT_ORDERTYPE_RESEARCH);
		DICT_ORDERTYPES_CROWDTASK.add(DICT_ORDERTYPE_TESTING);
		DICT_ORDERTYPES_CROWDTASK.add(DICT_ORDERTYPE_HELPAPP);
		DICT_ORDERTYPES_CROWDTASK.add(DICT_ORDERTYPE_HELPPDD);
		DICT_ORDERTYPES_CROWDTASK.add(DICT_ORDERTYPE_HELPVOTE);
		DICT_ORDERTYPES_CROWDTASK.add(DICT_ORDERTYPE_HELPWX);
	}
	
	/**
	 * 根据订单类型获取子业务类型
	 * @param orderType
	 * @return
	 */
	public static String getSubServiceType(Integer orderType){
		if(DICT_ORDERTYPE_TRANSPIC.equals(orderType)){
			return DictConstants.DICT_SUBSERVICETYPE_TRANSPIC;
		}else if(DICT_ORDERTYPE_SUBMCGG.equals(orderType)){
			return DictConstants.DICT_SUBSERVICETYPE_MCGGSUB;
		}else if(DICT_ORDERTYPE_TRANSTEXT.equals(orderType)){
			return DictConstants.DICT_SUBSERVICETYPE_TRANSTEXT;
		}else if(DICT_ORDERTYPE_SUBBREADENGLISH.equals(orderType)){
			return DictConstants.DICT_SUBSERVICETYPE_BREADENGLISH;
		}else if(DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)){
			return DictConstants.DICT_SUBSERVICETYPE_TRANSVIDEO;
		}else if(DICT_ORDERTYPE_PACKAGE.equals(orderType)){
			return DictConstants.DICT_SUBSERVICETYPE_ACTIVITYPACKAGE;
		}else if(DICT_ORDERTYPE_NOAD.equals(orderType)){
			return DictConstants.DICT_SUBSERVICETYPE_NOAD;
		}else if(DICT_ORDERTYPE_TRANSCOMBO.equals(orderType)){
			return DictConstants.DICT_SUBSERVICETYPE_TRANSCOMBO;
		}else if(DICT_ORDERTYPE_TRANSVIP.equals(orderType)){
			return DictConstants.DICT_SUBSERVICETYPE_TRANSVIP;
		}else if(DICT_ORDERTYPE_USERVIP.equals(orderType)){
			return DictConstants.DICT_SUBSERVICETYPE_USERVIP;
		}
				
		return null;
	}
	
	/**
	 * 红包状态-0待支付
	 */
	public static final Integer DICT_REDPACKET_STATUS_WAITPAY = 0;
	/**
	 * 红包状态-1已生效
	 */
	public static final Integer DICT_REDPACKET_STATUS_VALID = 1;
	/**
	 * 红包状态-2已过期
	 */
	public static final Integer DICT_REDPACKET_STATUS_EXPIRED = 2;
	/**
	 * 红包状态-3已取消
	 */
	public static final Integer DICT_REDPACKET_STATUS_CANCEL = 3;
	
	/**
	 * 红包账单类型-1发红包
	 */
	public static final Integer DICT_REDPACKET_BILLTYPE_SEND = 1;
	/**
	 * 红包账单类型-2收红包
	 */
	public static final Integer DICT_REDPACKET_BILLTYPE_RECEIVE = 2;
	/**
	 * 红包账单类型-3未领红包转入
	 */
	public static final Integer DICT_REDPACKET_BILLTYPE_UNRECEIVE = 3;
	/**
	 * 红包账单类型-4提现
	 */
	public static final Integer DICT_REDPACKET_BILLTYPE_CASH = 4;
	/**
	 * 红包账单类型-5驳回提现申请
	 */
	public static final Integer DICT_REDPACKET_BILLTYPE_REJECTCASH = 5;
	
	/**
	 * 红包提现状态-0申请提现
	 */
	public static final Integer DICT_REDPACKETCASH_STATUS_APPLY = 0;
	/**
	 * 红包提现状态-1成功提现
	 */
	public static final Integer DICT_REDPACKETCASH_STATUS_CASHED = 1;
	/**
	 * 红包提现状态-2驳回申请
	 */
	public static final Integer DICT_REDPACKETCASH_STATUS_REJECT = 2;
	
	/**
	 * 翻译修正来源-1翻译日志
	 */
	public static final Integer DICT_TRANS_CORRECTSOURCE_LOG = 1;
	/**
	 * 翻译修正来源-2用户纠错
	 */
	public static final Integer DICT_TRANS_CORRECTSOURCE_USER = 2;
	
	/**
	 * 钱包充值状态-0待支付
	 */
	public static final Integer DICT_WALLET_RECHARGESTATUS_WAITPAY = 0;
	/**
	 * 钱包充值状态-1已支付
	 */
	public static final Integer DICT_WALLET_RECHARGESTATUS_PAID = 1;
	/**
	 * 钱包充值状态-3已取消
	 */
	public static final Integer DICT_WALLET_RECHARGESTATUS_CANCEL = 2;
	/**
	 * 钱包充值状态-3已退款
	 */
	public static final Integer DICT_WALLET_RECHARGESTATUS_REFUND = 3;
	
	/**
	 * 钱包账单类型-1充值
	 */
	public static final Integer DICT_WALLET_BILLTYPE_RECHARGE = 1;
	/**
	 * 钱包账单类型-2消费
	 */
	public static final Integer DICT_WALLET_BILLTYPE_CONSUME = 2;
	/**
	 * 钱包账单类型-3退款
	 */
	public static final Integer DICT_WALLET_BILLTYPE_CONSUMEREFUND = 3;
	/**
	 * 钱包账单类型-4充值退款
	 */
	public static final Integer DICT_WALLET_BILLTYPE_RECHARGEREFUND = 4;
	/**
	 * 钱包账单类型-5赠送
	 */
	public static final Integer DICT_WALLET_BILLTYPE_BESTOW = 5;
	/**
	 * 钱包账单类型-6广告激励
	 */
	public static final Integer DICT_WALLET_BILLTYPE_ENCOURAGE = 6;
	
	public static Integer getIncexpByWalletBillType(Integer billType){
		if(DICT_WALLET_BILLTYPE_RECHARGE.equals(billType)
				|| DICT_WALLET_BILLTYPE_CONSUMEREFUND.equals(billType)
				|| DICT_WALLET_BILLTYPE_BESTOW.equals(billType)
				|| DICT_WALLET_BILLTYPE_ENCOURAGE.equals(billType)){
			return DICT_INCEXP_INCOME;
		}else if(DICT_WALLET_BILLTYPE_CONSUME.equals(billType)
				|| DICT_WALLET_BILLTYPE_RECHARGEREFUND.equals(billType)){
			return DICT_INCEXP_EXPENSES;
		}
		return null;
	}
	
	/**
	 * 钱包激励类型-1视频
	 */
	public static final Integer DICT_WALLET_ENCOURAGETYPE_VIDEO = 1;
	
	
	/**
	 * 奖励账单类型-1奖励
	 */
	public static final Integer DICT_REWARD_BILLTYPE_RECEIVE = 1;
	/**
	 * 奖励账单类型-2提现
	 */
	public static final Integer DICT_REWARD_BILLTYPE_CASH = 2;
	/**
	 * 奖励账单类型-3提现失败
	 */
	public static final Integer DICT_REWARD_BILLTYPE_REJECTCASH = 3;
	/**
	 * 奖励账单类型-4赠送
	 */
	public static final Integer DICT_REWARD_BILLTYPE_BESTOW = 4;
	
	/**
	 * 收支-1收入
	 */
	public static final Integer DICT_INCEXP_INCOME = 1;
	/**
	 * 收支-2支出
	 */
	public static final Integer DICT_INCEXP_EXPENSES = 2;
	
	/**
	 * 翻译套餐单位-1次
	 */
	public static final Integer DICT_TRANSCOMBO_UNIT_TIMES = 1;
	/**
	 * 翻译套餐单位-2日
	 */
	public static final Integer DICT_TRANSCOMBO_UNIT_DAY = 2;
	
	/**
	 * 翻译套餐服务途径-1出国翻译官
	 */
	public static final Integer DICT_TRANSCOMBO_SERVICEWAY_GT = 1;
	/**
	 * 翻译套餐服务途径-2微信号
	 */
	public static final Integer DICT_TRANSCOMBO_SERVICEWAY_WX = 2;
	
	/**
	 * 付款方-1用户
	 */
	public static final Integer DICT_PAYSIDE_USER = 1;
	/**
	 * 付款方-2公司
	 */
	public static final Integer DICT_PAYSIDE_SELF = 2;
	/**
	 * 付款方-3携程
	 */
	public static final Integer DICT_PAYSIDE_CTRIP = 3;
	/**
	 * 评价类型-1翻译订单
	 */
	public static final Integer DICT_EVALTYPE_TRANSORDER = 1;
	
	/**
	 * 有效期类型-1永久
	 */
	public static final Integer DICT_VALIDITYTYPE_FOREVER = 1;
	/**
	 * 有效期类型-2固定
	 */
	public static final Integer DICT_VALIDITYTYPE_FIXED = 2;
	/**
	 * 有效期类型-3赋值
	 */
	public static final Integer DICT_VALIDITYTYPE_ASSIGNED = 3;
	
	/**
	 * 有效期周期类型-1天
	 */
	public static final Integer DICT_VALIDITYCYCLETYPE_DAY = 1;
	/**
	 * 有效期周期类型-2月
	 */
	public static final Integer DICT_VALIDITYCYCLETYPE_MONTH = 2;
	/**
	 * 有效期周期类型-3年
	 */
	public static final Integer DICT_VALIDITYCYCLETYPE_YEAR = 3;
	
	/**
	 * 众包任务频率-1单次
	 */
	public static final Integer DICT_CROWDTASK_TASKFREQ_TIMES = 1;
	/**
	 * 众包任务频率-2每日
	 */
	public static final Integer DICT_CROWDTASK_TASKFREQ_DAILY = 2;
	
	/**
	 * 众包任务类型-1前置任务
	 */
	public static final Integer DICT_CROWDTASK_TASKTYPE_PRETASK = 1;
	/**
	 * 众包任务类型-2任务
	 */
	public static final Integer DICT_CROWDTASK_TASKTYPE_TASK = 2;
	
	/**
	 * 众包任务来源-1测试任务
	 */
	public static final Integer DICT_CROWDTASK_TASKSOURCE_TEST = 1;
	/**
	 * 众包任务来源-2公司任务
	 */
	public static final Integer DICT_CROWDTASK_TASKSOURCE_ENT = 2;
	/**
	 * 众包任务来源-4用户订单
	 */
	public static final Integer DICT_CROWDTASK_TASKSOURCE_ORDER = 4;
	
	/**
	 * 众包任务结算方式-1按任务结算
	 */
	public static final Integer DICT_CROWDTASK_SETTLEMETHOD_TASK = 1;
	/**
	 * 众包任务结算方式-2按题目结算
	 */
	public static final Integer DICT_CROWDTASK_SETTLEMETHOD_SUBJECT = 2;
	
	/**
	 * 众包任务计量-1任务
	 */
	public static final Integer DICT_CROWDTASK_MEASURE_TASK = 1;
	/**
	 * 众包任务计量-2题目
	 */
	public static final Integer DICT_CROWDTASK_MEASURE_SUBJECT = 2;
	
	/**
	 * 众包任务题目分配方式-1系统分配
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTASSIGNMETHOD_SYSTEM = 1;
	/**
	 * 众包任务题目分配方式-2用户领取
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTASSIGNMETHOD_USER = 2;
	
	/**
	 * 众包任务题目分类-1音频采集
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTCAT_AUDIOCOLLECTION = 1;
	/**
	 * 众包任务题目分类-2音频标注
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTCAT_AUDIOMARK = 2;
	/**
	 * 众包任务题目分类-3图片标注
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTCAT_PICMARK = 3;
	/**
	 * 众包任务题目分类-5广告
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTCAT_AD = 5;
	/**
	 * 众包任务题目分类-6视频标注
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTCAT_VIDEOMARK = 6;
	/**
	 * 众包任务题目分类-7文本标注
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTCAT_TXTMARK = 7;
	/**
	 * 众包任务题目分类-8助力
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTCAT_HELP = 8;
	/**
	 * 众包任务题目分类-9添加剂采集
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTCAT_ADDITIVE = 9;
	
	/**
	 * 众包任务题型-1单选
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTTYPE_RADIO = 1;
	/**
	 * 众包任务题型-2多选
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTTYPE_MULTI = 2;
	/**
	 * 众包任务题型-3问答
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTTYPE_QA = 3;
	/**
	 * 众包任务题型-9其他
	 */
	public static final Integer DICT_CROWDTASK_SUBJECTTYPE_OTHER = 9;
	
	/**
	 * 众包任务内容类型-1数字
	 */
	public static final Integer DICT_CROWDTASK_CONTENTTYPE_NUM = 1;
	/**
	 * 众包任务内容类型-2文本
	 */
	public static final Integer DICT_CROWDTASK_CONTENTTYPE_TXT = 2;
	
	/**
	 * 用户众包任务状态-1报名审核中
	 */
	public static final Integer DICT_USERCROWDTASK_STATUS_APPLY = 1;
	/**
	 * 用户众包任务状态-2报名成功
	 */
	public static final Integer DICT_USERCROWDTASK_STATUS_ACCEPTAPPLY = 2;
	/**
	 * 用户众包任务状态-3报名失败
	 */
	public static final Integer DICT_USERCROWDTASK_STATUS_REJECTAPPLY = 3;
	/**
	 * 用户众包任务状态-4待完成
	 */
	public static final Integer DICT_USERCROWDTASK_STATUS_ANSWERING = 4;
	/**
	 * 用户众包任务状态-5已提交，待验收
	 */
	public static final Integer DICT_USERCROWDTASK_STATUS_SUBMIT = 5;
	/**
	 * 用户众包任务状态-6已取消
	 */
	public static final Integer DICT_USERCROWDTASK_STATUS_CANCEL = 6;
	/**
	 * 用户众包任务状态-7已完结
	 */
	public static final Integer DICT_USERCROWDTASK_STATUS_FINISH = 7;
	
	/**
	 * 用户众包任务状态集-报名(扣掉报名失败和已取消)
	 */
	public static List<Integer> DICT_USERCROWDTASK_STATUSES_APPLY = new ArrayList<Integer>();
	static{
		DICT_USERCROWDTASK_STATUSES_APPLY.add(DICT_USERCROWDTASK_STATUS_APPLY);
		DICT_USERCROWDTASK_STATUSES_APPLY.add(DICT_USERCROWDTASK_STATUS_ACCEPTAPPLY);
		DICT_USERCROWDTASK_STATUSES_APPLY.add(DICT_USERCROWDTASK_STATUS_ANSWERING);
		DICT_USERCROWDTASK_STATUSES_APPLY.add(DICT_USERCROWDTASK_STATUS_SUBMIT);
		DICT_USERCROWDTASK_STATUSES_APPLY.add(DICT_USERCROWDTASK_STATUS_FINISH);
	}
	
	/**
	 * 用户众包任务状态集-进行中
	 */
	public static List<Integer> DICT_USERCROWDTASK_STATUSES_ING = new ArrayList<Integer>();
	static{
		DICT_USERCROWDTASK_STATUSES_ING.add(DICT_USERCROWDTASK_STATUS_ACCEPTAPPLY);
		DICT_USERCROWDTASK_STATUSES_ING.add(DICT_USERCROWDTASK_STATUS_ANSWERING);
	}
	
	/**
	 * 用户众包任务状态集-完成
	 */
	public static List<Integer> DICT_USERCROWDTASK_STATUSES_FINISH = new ArrayList<Integer>();
	static{
		DICT_USERCROWDTASK_STATUSES_FINISH.add(DICT_USERCROWDTASK_STATUS_SUBMIT);
		DICT_USERCROWDTASK_STATUSES_FINISH.add(DICT_USERCROWDTASK_STATUS_FINISH);
	}
	
	/**
	 * 用户众包任务审核状态-1待审核
	 */
	public static final Integer DICT_USERCROWDTASK_VERIFYSTATUS_WAITING = 1;
	/**
	 * 用户众包任务审核状态-2审核中
	 */
	public static final Integer DICT_USERCROWDTASK_VERIFYSTATUS_PROCESSING = 2;
	/**
	 * 用户众包任务审核状态-3已审核
	 */
	public static final Integer DICT_USERCROWDTASK_VERIFYSTATUS_DEALT = 3;
	
	/**
	 * 用户众包任务审校状态-1待审校
	 */
	public static final Integer DICT_USERCROWDTASK_QCSTATUS_WAITING = 1;
	/**
	 * 用户众包任务审校状态-2审校中
	 */
	public static final Integer DICT_USERCROWDTASK_QCSTATUS_PROCESSING = 2;
	/**
	 * 用户众包任务审校状态-3已审校
	 */
	public static final Integer DICT_USERCROWDTASK_QCSTATUS_DEALT = 3;
	/**
	 * 用户众包任务审校状态集-进行中
	 */
	public static List<Integer> DICT_USERCROWDTASK_QCSTATUSES_ING = new ArrayList<Integer>();
	static{
		DICT_USERCROWDTASK_QCSTATUSES_ING.add(DICT_USERCROWDTASK_QCSTATUS_WAITING);
		DICT_USERCROWDTASK_QCSTATUSES_ING.add(DICT_USERCROWDTASK_QCSTATUS_PROCESSING);
	}
	
	/**
	 * 用户众包任务奖励发放状态-1待发放
	 */
	public static final Integer DICT_USERCROWDTASK_GRANTSTATUS_WAITING = 1;
	/**
	 * 用户众包任务奖励发放状态-2已到账
	 */
	public static final Integer DICT_USERCROWDTASK_GRANTSTATUS_DEALT = 2;
	
	/**
	 * 用户众包任务奖励分类-1任务奖励
	 */
	public static final Integer DICT_USERCROWDTASK_REWARDCAT_TASK = 1;
	/**
	 * 用户众包任务奖励分类-2审核奖励
	 */
	public static final Integer DICT_USERCROWDTASK_REWARDCAT_VERIFY = 2;
	/**
	 * 用户众包任务奖励分类-3审校奖励
	 */
	public static final Integer DICT_USERCROWDTASK_REWARDCAT_QC = 3;
	
	/**
	 * 图像识别分类-1001植物识别
	 */
	public static final Long DICT_IMAGERECOGNITION_PLANT = 1001L;
	/**
	 * 图像识别分类-1002动物识别
	 */
	public static final Long DICT_IMAGERECOGNITION_ANIMAL = 1002L;
	/**
	 * 图像识别分类-1003品牌logo
	 */
	public static final Long DICT_IMAGERECOGNITION_LOGO = 1003L;
	/**
	 * 图像识别分类-1004通用识别
	 */
	public static final Long DICT_IMAGERECOGNITION_GENERAL = 1004L;
	/**
	 * 图像识别分类-1005菜品识别
	 */
	public static final Long DICT_IMAGERECOGNITION_DISH = 1005L;
	/**
	 * 图像识别分类-1006地标识别
	 */
	public static final Long DICT_IMAGERECOGNITION_LANDMARK = 1006L;
	/**
	 * 图像识别分类-1007红酒识别
	 */
	public static final Long DICT_IMAGERECOGNITION_REDWINE = 1007L;
	/**
	 * 图像识别分类-1008货币识别
	 */
	public static final Long DICT_IMAGERECOGNITION_CURRENCY = 1008L;
	/**
	 * 图像识别分类-1009车型识别
	 */
	public static final Long DICT_IMAGERECOGNITION_CAR = 1009L;
	/**
	 * 图像识别分类-1010公众人物识别
	 */
	public static final Long DICT_IMAGERECOGNITION_STAR = 1010L;
	
	/**
	 * 对话消息方-1用户
	 */
	public static final Integer DICT_DIALOG_MSGSIDE_USER = 1;
	/**
	 * 对话消息方-2智能体
	 */
	public static final Integer DICT_DIALOG_MSGSIDE_AGENT = 2;
	/**
	 * 对话处理状态-1待处理
	 */
	public static final Integer DICT_DIALOG_DEALSTATUS_WAIT = 1;
	/**
	 * 对话处理状态-2已处理
	 */
	public static final Integer DICT_DIALOG_DEALSTATUS_DEALT = 2;
	/**
	 * 同城任务创建途径-1录入
	 */
	public static final Integer DICT_LOCALTASK_CREATEWAY_ENTER = 1;
	/**
	 * 同城任务创建途径-2采集
	 */
	public static final Integer DICT_LOCALTASK_CREATEWAY_SPD = 2;
	/**
	 * 同城任务创建途径-3用工单位
	 */
	public static final Integer DICT_LOCALTASK_CREATEWAY_COMPANY = 3;
	
	/**
	 * 同城任务状态-0已下架
	 */
	public static final Integer DICT_LOCALTASK_STATUS_OFF = 0;
	/**
	 * 同城任务状态-1已发布
	 */
	public static final Integer DICT_LOCALTASK_STATUS_ON = 1;
	/**
	 * 同城任务状态-2编辑中
	 */
	public static final Integer DICT_LOCALTASK_STATUS_EDITING = 2;
	/**
	 * 同城任务状态-3审核中
	 */
	public static final Integer DICT_LOCALTASK_STATUS_VERIFYING = 3;
	/**
	 * 同城任务状态-4未通过审核
	 */
	public static final Integer DICT_LOCALTASK_STATUS_VERIFYREFUSE = 4;
	
	/**
	 * 同城任务工作时间类型-1全职
	 */
	public static final Integer DICT_LOCALTASK_WORKTIMETYPE_FULLTIME = 1;
	/**
	 * 同城任务工作时间类型-2兼职
	 */
	public static final Integer DICT_LOCALTASK_WORKTIMETYPE_PARTTIME = 2;
	
	/**
	 * 同城学历-不限0
	 */
	public static final Integer DICT_LOCALTASK_EDU_UNLIMITED = 0;
	/**
	 * 同城学历-1小学
	 */
	public static final Integer DICT_LOCALTASK_EDU_PRIMARY = 1;
	/**
	 * 同城学历-2初中
	 */
	public static final Integer DICT_LOCALTASK_EDU_JUNIORHIGH = 2;
	/**
	 * 同城学历-3高中职高中专
	 */
	public static final Integer DICT_LOCALTASK_EDU_HIGH = 3;
	/**
	 * 同城学历-4大专junior college
	 */
	public static final Integer DICT_LOCALTASK_EDU_JUNIORCOLLEGE = 4;
	/**
	 * 同城学历-5本科学士
	 */
	public static final Integer DICT_LOCALTASK_EDU_BACHELOR = 5;
	/**
	 * 同城学历-6硕士 
	 */
	public static final Integer DICT_LOCALTASK_EDU_GRADUATE = 6;
	/**
	 * 同城学历-7博士
	 */
	public static final Integer DICT_LOCALTASK_EDU_DOCTORAL = 7;
	/**
	 * 报酬类型-1时薪
	 */
	public static final Integer DICT_LOCALTASK_REWARDTYPE_HOUR = 1;
	/**
	 * 报酬类型-2日薪
	 */
	public static final Integer DICT_LOCALTASK_REWARDTYPE_DATE = 2;
	/**
	 * 报酬类型-3月薪
	 */
	public static final Integer DICT_LOCALTASK_REWARDTYPE_MONTH = 3;
	/**
	 * 报酬类型-4年薪
	 */
	public static final Integer DICT_LOCALTASK_REWARDTYPE_YEAR = 4;
	/**
	 * 报酬类型-5次薪
	 */
	public static final Integer DICT_LOCALTASK_REWARDTYPE_TIME = 5;
	
	/**
	 * 报酬类型集-月薪和年薪
	 */
	public static final List<Integer> DICT_LOCALTASK_REWARDTYPES_MY = new ArrayList<Integer>();
	static{
		DICT_LOCALTASK_REWARDTYPES_MY.add(DICT_LOCALTASK_REWARDTYPE_MONTH);
		DICT_LOCALTASK_REWARDTYPES_MY.add(DICT_LOCALTASK_REWARDTYPE_YEAR);
	}

	/**
	 * 同城公司认证状态-0未认证
	 */
	public static final Integer DICT_LOCACOMPANY_CERTIFYSTATUS_UNCERTIFY = 0;
	/**
	 * 同城公司认证状态-1已认证
	 */
	public static final Integer DICT_LOCACOMPANY_CERTIFYSTATUS_CERTIFIED = 1;
	/**
	 * 同城公司认证状态-2审核中
	 */
	public static final Integer DICT_LOCACOMPANY_CERTIFYSTATUS_VERIFYING = 2;
	/**
	 * 同城公司认证状态-3认证失败
	 */
	public static final Integer DICT_LOCACOMPANY_CERTIFYSTATUS_FAIL = 3;
	
	/**
	 * 同城公司创建途径-1录入
	 */
	public static final Integer DICT_LOCALCOMPANY_CREATEWAY_ENTER = 1;
	/**
	 * 同城公司创建途径-2采集
	 */
	public static final Integer DICT_LOCALCOMPANY_CREATEWAY_SPD = 2;
	/**
	 * 同城公司创建途径-3用工单位
	 */
	public static final Integer DICT_LOCALCOMPANY_CREATEWAY_COMPANY = 3;
}
