package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.bdc.ws.provide.vo.HolidayBean;
import com.qcmz.cmc.cache.HolidayMap;
import com.qcmz.cmc.cache.TransComboMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcCombo;
import com.qcmz.cmc.entity.CmcComboPackage;
import com.qcmz.cmc.entity.CmcROrder;
import com.qcmz.cmc.entity.CmcUCombo;
import com.qcmz.cmc.service.IOrderService;
import com.qcmz.cmc.service.ITranslatorCommissionService;
import com.qcmz.cmc.service.IUserTransComboService;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.DoubleUtil;
import com.qcmz.srm.client.cache.UserCache;
import com.qcmz.srm.ws.provide.vo.UserBaseBean;

/**
 * 订单佣金处理
 */
@Component
public class OrderCommissionProcess {
	@Autowired
	private IUserTransComboService userTransComboService;
	@Autowired
	private ITranslatorCommissionService orderCommissionService;
	@Autowired
	private IOrderService orderService;
	
	@Autowired
	private HolidayMap holidayMap;
	@Autowired
	private TransComboMap transComboMap;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 测试人员用户编号列表，其下的订单不计
	 */
	public static List<Long> testUserIdList = new ArrayList<Long>();
	static{
		testUserIdList.add(10017472L);	//魏赟
		testUserIdList.add(10103107L);	//王东海
		testUserIdList.add(10764032L);	//王东海
		testUserIdList.add(10017388L);	//李炳煜
		testUserIdList.add(11337735L);	//李炳煜
		testUserIdList.add(10007374L);	//薛世一
		testUserIdList.add(10017486L);	//薛世一
		testUserIdList.add(10763445L);	//薛世一
		testUserIdList.add(10017482L);	//薛世一
		testUserIdList.add(10839740L);	//18511654652
		testUserIdList.add(10749521L);	//殷建峰
		testUserIdList.add(11060080L);	//张一涛
		testUserIdList.add(10017537L);	//美丽妹
		testUserIdList.add(11210988L);	//王尧		
	}
	
	//工作时间
	private static final String worktime = "0900-2100";
	//佣金率，
	private static Map<String, Double> rateMap = new HashMap<String, Double>();
	static{
		rateMap.put("en", 0.5);
		rateMap.put("ko", 0.6);
		rateMap.put("ja", 0.6);
	}
	
	/**
	 * 前5天的订单佣金结算
	 */
	public void commission(){
		Date today = DateUtil.getSysDateOnly();
		Date startDate = DateUtil.addDay(today, -5);
		Date endDate = DateUtil.addDay(today, -1);
		commission(startDate, endDate, null);
	}
	
	/**
	 * 指定日期的订单佣金结算
	 * @param date not null yyyy-mm-dd
	 */
	public void commission(Date date){
		Date startDate = date;
		Date endDate = startDate;
		commission(startDate, endDate, null);
	}
	
	/**
	 * 订单佣金结算
	 * @param startDate not null yyyy-mm-dd
	 * @param endDate not null yyyy-mm-dd
	 */
	public void commission(Date startDate, Date endDate, String operCd){
		List<CmcROrder> orderList = orderCommissionService.findDealtOrder(startDate, endDate, SystemConstants.STATUS_OPER_ENABLED, operCd);
		logger.info("译员佣金待结算订单总数："+orderList.size());
		for (CmcROrder order : orderList) {
			try {
				commission(order);
			} catch (Exception e) {
				logger.error("订单佣金结算失败："+order.getOrderid(), e);
			}
		}
		logger.info("完成译员佣金结算");
	}
	
	/**
	 * 订单佣金重新结算
	 * @param startDate not null yyyy-mm-dd
	 * @param endDate not null yyyy-mm-dd
	 */
	public void reCommission(Date startDate, Date endDate, String operCd){
		List<CmcROrder> orderList = orderCommissionService.findDealtOrder(startDate, endDate, null, operCd);
		logger.info("译员佣金重新结算订单总数："+orderList.size());
		for (CmcROrder order : orderList) {
			try {
				commission(order);
			} catch (Exception e) {
				logger.error("订单佣金结算失败："+order.getOrderid(), e);
			}
		}
		logger.info("完成译员佣金重新结算");
	}
	
	/**
	 * 订单佣金结算
	 * @param order
	 */
	public void commission(CmcROrder order){		
		Double commissionAmount = calCommission(order);
		if(commissionAmount!=null){
			Date commissionDate = DateUtil.truncDate(order.getOpertime());
			orderService.updateCommission(order.getOrderid(), SystemConstants.STATUS_OPER_ALREADY, commissionAmount, commissionDate);
		}
	}
	
	/**
	 * 计算译员佣金
	 * 1 真人口译订单
	 * 1.1  按订单总额100%结算
	 * 1.2  套餐次包：按次均分总价，接一次分一次钱；比如日语1次包25元，译员分¥25*100%；日语3次包70元，接一次分23¥*100%，以此类推
	 * 1.3 套餐日包：按日均分总价，当天所有接单人的的接单时长作为分母，每个接单人的时长各自作分子，算出一个比例，乘以分日金额；总体来说，译员分得的上限是套餐总金额
	 * 2 图文订单
	 * 2.1 英语图文订单按订单总额5折计算
	 * 2.2 日韩图文订单按订单总额6折计算
	 * 2.3 其他图文订单按订单总额7折计算 
	 * 3 公司员工
	 * 3.1 公司员工工作时间内翻译不结算
	 * 3.2 非工作时间：a.周末且非调休 b.法定节假日 c.21:00-09:00
	 * @param userId
	 * @param orderType
	 * @param orderAmount
	 * @param from
	 * @param to
	 * @param userCombo
	 * @param operTime
	 * @param operator
	 * @return null表示待结算
	 */
	public Double calCommission(CmcROrder order){
		//1.佣金为0
		//测试人员订单佣金为0
		if(testUserIdList.contains(order.getUserid())) return 0.0;		
		//非翻译订单，佣金为0
		if(!DictConstant.DICT_ORDERCAT_TRANS.equals(order.getOrdercat())){
			return 0.0;
		}
		//取消、退款订单佣金为0
		if(DictConstant.DICT_ORDER_DEALSTATUS_CANCEL.equals(order.getDealstatus())
				|| DictConstant.DICT_ORDER_DEALSTATUS_REFUNDED.equals(order.getDealstatus())){
			return 0.0;
		}
		
		//2.日套餐，当日不结算
		Date operDate = DateUtil.truncDate(order.getOpertime());
		CmcUCombo userCombo = null;
		if(order.getUcid()!=null){
			userCombo = userTransComboService.getUserCombo(order.getUcid());
		}
		
		if(userCombo!=null 
				&& DictConstant.DICT_TRANSCOMBO_UNIT_DAY.equals(userCombo.getUnit())
				&& DateUtil.getSysDateOnly().getTime()==operDate.getTime()){
			return null;
		}
		
		//3.全职员工，在工作时间内，佣金为0；如果获取操作员信息失败，暂不结算
		UserBaseBean userBean = UserCache.getUser(order.getOpercd());
		if(userBean==null) {
			return null;	
		}
		if(DictConstants.DICT_WORKTIMETYPE_FULLTIME.equals(userBean.getWorktimeType())){
			if(!holidayMap.hasInit()) {
				return null;	//节假日初始化失败，不结算
			}
			if(inWorktime(order.getOpertime())){
				return 0.0;
			}
		}
		
		//4.计算佣金
		Double commissionAmount = null;
		if(userCombo==null){
			//4.1未使用套餐，订单价格*佣金率
			commissionAmount = DoubleUtil.multiplyFloor(order.getAmount(), getRate(order.getOrdertype(), order.getFromlang(), order.getTolang()));
		}else{
			//4.2套餐
			Double unitPrice = null;
			if(userCombo.getPkgid()!=null){
				CmcComboPackage pkg = transComboMap.getPackage(userCombo.getPkgid());
				unitPrice = DoubleUtil.divideFloor(pkg.getPrice(), Double.valueOf(pkg.getNum()));
			}else{
				CmcCombo combo = transComboMap.getCombo(userCombo.getComboid());
				unitPrice = combo.getUnitprice();
			}
			if(DictConstant.DICT_TRANSCOMBO_UNIT_TIMES.equals(userCombo.getUnit())){
				commissionAmount = unitPrice;
			}else if(DictConstant.DICT_TRANSCOMBO_UNIT_DAY.equals(userCombo.getUnit())){
				Long count = userTransComboService.getUserComboUsedCount(userCombo.getUcid(), operDate);
				commissionAmount = DoubleUtil.divideFloor(unitPrice, count.doubleValue());
			}
		}
		return commissionAmount;
	}
	
	/**
	 * 获取佣金率
	 * 真人口译订单按订单总额结算
	 * 英语图文订单按订单总额5折计算
	 * 日韩图文订单按订单总额6折计算
	 * 其他图文订单按订单总额7折计算
	 * @param orderType
	 * @param from
	 * @param to
	 * @return
	 */
	private double getRate(Integer orderType, String from, String to){
		Double rate = 0.0;
		if(DictConstant.DICT_ORDERTYPE_TRANSVIDEO.equals(orderType)){
			rate = 1.0;
		}else if(DictConstant.DICT_ORDERTYPE_TRANSPIC.equals(orderType)
				|| DictConstant.DICT_ORDERTYPE_TRANSTEXT.equals(orderType)){
			rate = rateMap.get(from);
			if(rate==null){
				rate = rateMap.get(to);
			}
			if(rate==null){
				rate = 0.7;
			}
		}
		return rate;
	}

	/**
	 * 是否在工作时间内
	 * @param operTime
	 * @return
	 */
	private boolean inWorktime(Date operTime){
		boolean inWorktime = DateUtil.inTime4(operTime, worktime);
		if(inWorktime){
			HolidayBean holiday = holidayMap.getBean(operTime);
			if(holiday!=null){
				inWorktime = !holiday.isHoliday();	//放假安排
			}else{
				inWorktime = DateUtil.inWeek(operTime, "12345");	//周末 
			}
		}
		return inWorktime;
	}
}
