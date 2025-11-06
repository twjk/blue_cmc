package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.proxy.spider.booking.BookingCityBean;
import com.qcmz.cmc.proxy.spider.booking.BookingCountryBean;
import com.qcmz.cmc.proxy.spider.booking.BookingHotelBean;
import com.qcmz.cmc.proxy.spider.booking.BookingPageBean;
import com.qcmz.cmc.proxy.spider.booking.BookingSpiderProxy;
import com.qcmz.cmc.proxy.trans.BaiduLangDetectProxy;
import com.qcmz.cmc.service.ITransLibService;
import com.qcmz.cmc.vo.TransLibraryEntity;
import com.qcmz.cmc.ws.provide.vo.TransResult;
import com.qcmz.framework.config.SharePreferences;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.util.MailUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：从booking抓取酒店名称
 * 修改历史：
 */
@Component
public class SpiderBookingProcess {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private BookingSpiderProxy bookingSpiderProxy;
	@Autowired
	private ITransLibService transLibService;
	@Autowired
	private BaiduLangDetectProxy baiduLangDetectProxy;
	
	@Autowired
	private TransProcess transProcess;
	
	private String[] stars = new String[]{"0","1","2","3","4","5"}; 
	
	private SharePreferences preferences = null;
	private String PREFERENCES_KEY_DESTID = "destId";
	
	/**
	 * 构造函数
	 * 修改历史：
	 */
	public SpiderBookingProcess() {
		try {
			preferences = new SharePreferences("spiderBooking");
		} catch (Exception e) {
			logger.error("初始化个性配置失败", e);
		}
	}
	
	/**
	 * 采集所有国家的酒店名称 
	 * 修改历史：
	 */
	public int spider(){
		int count = 0;

		String lastDestId = preferences.getString(PREFERENCES_KEY_DESTID);
		boolean matchLast = StringUtil.isBlankOrNull(lastDestId);
		for (BookingCountryBean country : BookingSpiderProxy.countries) {
			if(!matchLast && country.getDestId().equals(lastDestId)){
				matchLast = true;
			}
			if(!matchLast) continue;
			
			preferences.saveProperty(PREFERENCES_KEY_DESTID, country.getDestId());
			count = count + spider(country);
		}
		
		preferences.clear(PREFERENCES_KEY_DESTID);
		
		MailUtil.sendSimpleMail(SystemConfig.MAILS, null, "从booking采集所有国家酒店名称完成啦！", null);
		
		return count;
	}
	
	/**
	 * 采集指定国家的酒店名称
	 * @param params
	 * @return
	 * 修改历史：
	 */
	public int spider(Map<String, String> params){
		String destId = params.get("destId");
		if(StringUtil.isBlankOrNull(destId)){
			return spider();
		}else{
			return spider(BookingSpiderProxy.getCountry(destId));
		}
	}
	
	/**
	 * 采集指定国家的酒店名称
	 * 每页记录数rows无效，默认为15，列表只显示67页
	 * 由于列表页数限制，所以增加星级和城市两个条件进行查询
	 * @param params
	 * @return
	 * 修改历史：
	 */
	public int spider(BookingCountryBean country){
		if(country==null){
			return 0;
		}

		String countryName = country.getCountryName();
		String destId = country.getDestId();
		
		int idx = countryName.indexOf("-"); 
		if(idx>-1){
			countryName = countryName.substring(0, idx);
		}
		
		List<BookingCityBean> citys = bookingSpiderProxy.spiderCity(countryName, destId);
		
		int page = 0;
		int pageSize = 15;
		int pageCount = 0;
		int count = 0;
		
		List<BookingHotelBean> hotelIntls = null;
		List<TransLibraryEntity> adds = new ArrayList<TransLibraryEntity>();
		BookingPageBean pageBean = null;
		TransLibraryEntity lib = null;
		StringBuilder sbLog = null;
		String detectLang = null;
		TransResult transBean = null;
		
		Operator oper = OperLogUtil.getSystemOperator();
		
		if(citys.isEmpty()){
			citys.add(new BookingCityBean());	//保证没有数据是可以正常查询
		}
		
		for (String star : stars) {
			for (BookingCityBean city : citys) {
				page = 0;
				do{
					page++;
					sbLog = new StringBuilder("采集：国家[")
						.append(countryName)
						.append("]城市[")
						.append(city.getCityName())
						.append("]星级[")
						.append(star)
						.append("]页数[")
						.append(page).append("/").append(pageCount)
						.append("]")
						;
					logger.info(sbLog.toString());
					pageBean = bookingSpiderProxy.spiderCn(countryName, destId, city.getCityId(), star, page, pageSize);
					if(pageBean==null) continue;	//采集失败，继续下一页
					
					pageCount = pageBean.getPageCount();
					for (BookingHotelBean hotel : pageBean.getResultList()) {
						//检测内容和语言代码是否匹配
						detectLang = baiduLangDetectProxy.detectLang(hotel.getHotelName());
						if(detectLang!=null && !detectLang.equals(hotel.getLangCode())){
							continue;
						}
						
						//查重
						if(transLibService.isExist(DictConstants.DICT_LANG_ZHCN, hotel.getHotelName(), DictConstants.DICT_LANG_EN)){
							logger.info(hotel.getHotelName()+"已经存在");
							continue;
						}
						
						//采集多语言名称
						hotelIntls = bookingSpiderProxy.spiderIntl(hotel);					
						for (BookingHotelBean hotelIntl : hotelIntls) {
							//检测内容和语言代码是否匹配
							detectLang = baiduLangDetectProxy.detectLang(hotelIntl.getHotelName());
							if(detectLang!=null && !detectLang.equals(hotelIntl.getLangCode())){
								continue;
							}
							
							//封装
							lib = new TransLibraryEntity();
							lib.setLibtype(DictConstant.DICT_LIBTYPE_LIB);
							lib.setFromlang(DictConstants.DICT_LANG_ZHCN);
							lib.setSrc(hotel.getHotelName());
							lib.setTolang(hotelIntl.getLangCode());
							lib.setDst(hotelIntl.getHotelName());
							lib.setTwoway(SystemConstants.STATUS_ON);
							lib.setCat(DictConstant.DICT_LIBCAT_HOTEL);
							lib.setHitcount(0L);
							lib.setSourceid(DictConstant.DICT_LIBSOURCE_BOOKING);
							lib.setCheckstatus(SystemConstants.STATUS_OFF);

							//机器翻译
							transBean = transProcess.trans(lib.getFromlang(), lib.getTolang(), lib.getSrc());
							if(transBean!=null && lib.getDst().equalsIgnoreCase(transBean.getDst())){
								lib.setStatus(SystemConstants.STATUS_ON);
								logger.info("采集译文与翻译结果一致："+lib.getSrc());
							}else{
								lib.setStatus(SystemConstants.STATUS_OFF);
							}
							
							adds.add(lib);
						}
						
						if(!adds.isEmpty()){
							transLibService.saveOrUpdateAll(adds, oper);
							adds.clear();
							count++;
						}
						
					}
					
				}while(page<pageCount);
			}
		}
		logger.info("booking采集"+countryName+"酒店名称成功："+count);
		return count;
	}
}