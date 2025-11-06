package com.qcmz.cmc.proxy.spider.booking;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.ArrayUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;


/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class BookingSpiderProxy {
	
	private Logger logger = Logger.getLogger(BookingSpiderProxy.class);
	private String BASEURL = "http://www.booking.com/searchresults.zh-cn.html?dest_type=country&";
	/**
	 * booking与系统语言代码对照表，<booking语言代码, 系统语言代码>
	 */
	private static Map<String, String> langMap = new HashMap<String, String>();
	static{
//		langMap.put("zh-cn", "zh-cn");
		langMap.put("en-gb", "en");	//英语
//		langMap.put("fr", "fr");	//法语
//		langMap.put("de", "de");	//德语
		langMap.put("ko", "ko");	//韩语
		langMap.put("ja", "ja");	//日语
//		langMap.put("es", "es");	//西班牙语
//		langMap.put("pt-pt", "pt");	//葡萄牙语
//		langMap.put("it", "it");	//意大利语
		langMap.put("ar", "ar-eg");	//阿拉伯语
//		langMap.put("ru", "ru");	//俄语
//		langMap.put("th", "th");	//泰语
//		langMap.put("cs", "cs");	//捷克语
//		langMap.put("da", "da");	//丹麦语
//		langMap.put("nl", "nl");	//荷兰语
//		langMap.put("fi", "fi");	//芬兰语
//		langMap.put("el", "el");	//希腊语
//		langMap.put("he", "he");	//希伯来语
//		langMap.put("hu", "hu");	//匈牙利语
//		langMap.put("id", "id");	//印度尼西亚语
//		langMap.put("no", "no");	//挪威语
//		langMap.put("pl", "pl");	//波兰语
//		langMap.put("sv", "sv");	//瑞典语
//		langMap.put("tr", "tr");	//土耳其语
//		langMap.put("vi", "vi");	//越南语
//		langMap.put("uk", "uk");	//乌克兰语
//		langMap.put("ms", "ms");	//马来语
	}
	
	/**
	 * 国家列表
	 */
	public static List<BookingCountryBean> countries = new ArrayList<BookingCountryBean>();
	static{
		countries.add(new BookingCountryBean("44","中国",35200));
		countries.add(new BookingCountryBean("95","香港",587));
		countries.add(new BookingCountryBean("206","台湾",3860));
		countries.add(new BookingCountryBean("124","澳门特别行政区",65));
		countries.add(new BookingCountryBean("104","意大利",109000));
		countries.add(new BookingCountryBean("224","美国",73000));
		countries.add(new BookingCountryBean("73","法国",58000));
		countries.add(new BookingCountryBean("54","克罗地亚",54000));
		countries.add(new BookingCountryBean("197","西班牙",49000));
		countries.add(new BookingCountryBean("80","德国",37000));
		countries.add(new BookingCountryBean("222","英国",34000));
		countries.add(new BookingCountryBean("176","俄罗斯",23200));						
		countries.add(new BookingCountryBean("30","巴西",22640));
		countries.add(new BookingCountryBean("83","希腊",21700));
		countries.add(new BookingCountryBean("14","奥地利",20768));
		countries.add(new BookingCountryBean("57","丹麦",20243));
		countries.add(new BookingCountryBean("170","波兰",17400));
		countries.add(new BookingCountryBean("98","印度",16000));
		countries.add(new BookingCountryBean("171","葡萄牙",14000));
		countries.add(new BookingCountryBean("209","泰国",13000));
		countries.add(new BookingCountryBean("13","澳大利亚",13000));
		countries.add(new BookingCountryBean("215","土耳其",12900));
		countries.add(new BookingCountryBean("96","匈牙利",9737));
		countries.add(new BookingCountryBean("38","加拿大",9200));
		countries.add(new BookingCountryBean("99","印尼",9200));
		countries.add(new BookingCountryBean("56","捷克",9172));
		countries.add(new BookingCountryBean("204","瑞士",8900));
		countries.add(new BookingCountryBean("106","日本",8500));												
		countries.add(new BookingCountryBean("149","荷兰",8286));
		countries.add(new BookingCountryBean("137","墨西哥",8084));
		countries.add(new BookingCountryBean("10","阿根廷",7600));
		countries.add(new BookingCountryBean("195","南非",7121));
		countries.add(new BookingCountryBean("175","罗马尼亚",6800));
		countries.add(new BookingCountryBean("21","比利时",6200));
		countries.add(new BookingCountryBean("203","瑞典",5830));
		countries.add(new BookingCountryBean("198","斯里兰卡",5600));
		countries.add(new BookingCountryBean("230","越南",5500));
		countries.add(new BookingCountryBean("43","智利",5012));
		countries.add(new BookingCountryBean("242","黑山",4200));
		countries.add(new BookingCountryBean("143","摩洛哥",4050));
		countries.add(new BookingCountryBean("128","马来西亚",4000));
		countries.add(new BookingCountryBean("152","新西兰",3800));
		countries.add(new BookingCountryBean("102","爱尔兰",3645));
		countries.add(new BookingCountryBean("159","挪威",3581));
		countries.add(new BookingCountryBean("112","韩国",3300));
		countries.add(new BookingCountryBean("507","克里米亚",3280));
		countries.add(new BookingCountryBean("191","斯洛伐克",3240));
		countries.add(new BookingCountryBean("192","斯洛文尼亚",2600));
		countries.add(new BookingCountryBean("55","塞浦路斯",2600));
		countries.add(new BookingCountryBean("168","菲律宾",2400));
		countries.add(new BookingCountryBean("67","爱沙尼亚",2300));
		countries.add(new BookingCountryBean("52","哥斯达黎加",2232));
		countries.add(new BookingCountryBean("103","以色列",2200));
		countries.add(new BookingCountryBean("72","芬兰",2128));
		countries.add(new BookingCountryBean("36","柬埔寨",2000));
		countries.add(new BookingCountryBean("116","拉托维亚",1900));
		countries.add(new BookingCountryBean("97","冰岛",1300));
		countries.add(new BookingCountryBean("186","沙特阿拉伯",1300));
		countries.add(new BookingCountryBean("63","埃及",1121));
		countries.add(new BookingCountryBean("109","肯尼亚",1100));
		countries.add(new BookingCountryBean("108","哈萨克斯坦",900));
		countries.add(new BookingCountryBean("208","坦桑尼亚",870));						
		countries.add(new BookingCountryBean("131","马耳他",800));
		countries.add(new BookingCountryBean("115","老挝",800));						
		countries.add(new BookingCountryBean("11","亚美尼亚",800));
		countries.add(new BookingCountryBean("145","缅甸",776));
		countries.add(new BookingCountryBean("135","毛里求斯",700));
		countries.add(new BookingCountryBean("148","尼泊尔",673));
		countries.add(new BookingCountryBean("105","牙买加",573));
		countries.add(new BookingCountryBean("153","尼加拉瓜",534));
		countries.add(new BookingCountryBean("129","马尔代夫",500));
		countries.add(new BookingCountryBean("117","黎巴嫩",400));
		countries.add(new BookingCountryBean("107","约旦",400));						
		countries.add(new BookingCountryBean("86","瓜德罗普",400));
		countries.add(new BookingCountryBean("81","加纳",400));
		countries.add(new BookingCountryBean("190","新加坡",400));
		countries.add(new BookingCountryBean("26","玻利维亚",400));
		countries.add(new BookingCountryBean("188","塞舌尔",352));
		countries.add(new BookingCountryBean("155","尼日利亚",340));
		countries.add(new BookingCountryBean("15","阿塞拜疆",300));
		countries.add(new BookingCountryBean("12","阿鲁巴岛",293));
		countries.add(new BookingCountryBean("161","巴基斯坦",240));
		countries.add(new BookingCountryBean("71","斐济",200));	
	}
	
	/**
	 * 获取国家信息
	 * @param destId
	 * @return
	 * 修改历史：
	 */
	public static BookingCountryBean getCountry(String destId){
		if(StringUtil.notBlankAndNull(destId)){
			for (BookingCountryBean country : countries) {
				if(country.getDestId().equals(destId)){
					return country;
				}
			}
		}
		return null;
	}
	
	/**
	 * 是否中国
	 * @param destId
	 * @return
	 * 修改历史：
	 */
	public static boolean isChina(String destId){
		
		return ArrayUtil.contain(new String[]{"44","95","206","124"}, destId);
	}
	
	/**
	 * 获取国家下城市列表
	 * @param countryName
	 * @param destId
	 * @return
	 * 修改历史：
	 */
	public List<BookingCityBean> spiderCity(String countryName, String destId){
		List<BookingCityBean> result = new ArrayList<BookingCityBean>();
		
		Document doc = null;
		Elements elements = null;
		Element element = null;
		String cityId = null;
		String cityName = null;
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("ss_raw", URLEncoder.encode(countryName, "UTF-8"));
			params.put("dest_id", destId);
			params.put("rows", "15");
			params.put("offset", "0");
			
			//获取中文名称及酒店详细页地址
			String url = BASEURL+HttpUtil.map2Params(params);
			logger.info(url);
			doc = Jsoup.connect(url).timeout(5000).get();
			element = doc.getElementById("filter_uf");
			if(element==null) return result;
			
			elements = element.getElementsByClass("filterelement");
			if(!elements.isEmpty()){
				Iterator<Element> it = elements.iterator();
				while(it.hasNext()){
					element = it.next();
					cityId = element.attr("data-value");
					if(StringUtil.isBlankOrNull(cityId)) continue;
					
					element = element.getElementsByClass("filter_label").first();
					if(element!=null){
						cityName = element.text();
					}
					
					result.add(new BookingCityBean(cityId, cityName));
				}
			}

		} catch (Exception e) {
			logger.error("采集城市列表失败", e);
			throw new ProxyException("采集失败");
		}
		return result;
	}
	
	/**
	 * 采集酒店中文名称，失败返回null
	 * http://www.booking.com/searchresults.zh-cn.html?ss_raw=%E4%B8%AD%E5%9B%BD&dest_id=44&nflt=uf%3D4293068755%3Bclass=2&dest_type=country&rows=15&offset=0
	 * 每页记录数rows无效，默认为15，列表只显示67页
	 * @param countryName
	 * @param destId
	 * @param cityId
	 * @param star
	 * @param page
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public BookingPageBean spiderCn(String countryName, String destId, String cityId, String star, int page, int pageSize){
		BookingPageBean result = new BookingPageBean();
		
		String url = null;
		Elements elements = null;
		Element element = null;
		String hotelName = null;
		String detailUrl = null;
		int indexS, indexE;
		String pageCount = null;
		
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("ss_raw", URLEncoder.encode(countryName, "UTF-8"));
			params.put("dest_id", destId);
			params.put("rows", String.valueOf(pageSize));
			params.put("offset", String.valueOf((page-1)*pageSize));
			params.put("nflt", new StringBuilder("uf%3D").append(cityId).append("%3Bclass=").append(star).toString());
			
			boolean isChina = isChina(destId);
			
			//获取中文名称及酒店详细页地址
			url = BASEURL+HttpUtil.map2Params(params);
			logger.info(url);
			Document doc = Jsoup.connect(url).timeout(5000).get();
			elements = doc.select("h3[class=sr-hotel__title]");
			if(!elements.isEmpty()){
				Iterator<Element> it = elements.iterator();
				while(it.hasNext()){
					element = it.next();
					hotelName = element.getElementsByClass("sr-hotel__name").first().text();
					detailUrl = element.getElementsByClass("hotel_name_link").first().attr("abs:href");
					if(StringUtil.isBlankOrNull(hotelName)||StringUtil.isBlankOrNull(detailUrl)) continue;
					
					indexS = hotelName.indexOf("（")+1; 
					indexE = hotelName.indexOf("）");
					if(!isChina && indexS<1) continue;	//非中国的格式为：英文名称（中文名称），故无"（"时抛弃
					
					if(!isChina && indexS>1 && indexE>indexS){
						hotelName = hotelName.substring(indexS, indexE);
					}
					result.getResultList().add(new BookingHotelBean(hotelName, DictConstants.DICT_LANG_ZHCN, detailUrl));
				}
			}
			
			//页码
			element = doc.getElementsByClass("results-paging").first();
			if(element!=null){
				element = element.getElementsByClass("sr_pagination_item").last();
			}
			if(element!=null){
				pageCount = element.text();
			}
			if(NumberUtil.isNumber(pageCount)){
				result.setPageCount(Integer.valueOf(pageCount));
			}else{
				result.setPageCount(1);
			}
		} catch (Exception e) {
			logger.error("采集失败："+url, e);
			return null;
		}
		return result;
	}
	
	/**
	 * 采集酒店各语言名称，失败返回空列表
	 * @param hotel
	 * @return
	 * 修改历史：
	 */
	public List<BookingHotelBean> spiderIntl(BookingHotelBean hotel){
		List<BookingHotelBean> result = new ArrayList<BookingHotelBean>();
		BookingHotelBean bean = null;
		String url = null;
		Document doc = null;
		Element element = null;
		String hotelName = null;
		int index = 0;
		try {
			logger.info("采集酒店各语言名称："+hotel.getHotelName());
			for (String bookingLang : langMap.keySet()) {
				url = hotel.getUrl().replace(".zh-cn.html?", "."+bookingLang+".html?");
				try {
					doc = Jsoup.connect(url).timeout(5000).get();
				} catch (Exception e) {
					//失败再来一次
					doc = Jsoup.connect(url).timeout(5000).get();
				}
				element = doc.getElementById("hp_hotel_name");
				if(element!=null){
					hotelName = element.text();
					if(StringUtil.notBlankAndNull(hotelName)){
						index = hotelName.indexOf("（"); 
						if(index>1){
							hotelName = hotelName.substring(0, index-1);
						}
						bean = new BookingHotelBean();
						bean.setHotelName(hotelName);
						bean.setLangCode(langMap.get(bookingLang));
						result.add(bean);
					}
				}
			}
		} catch (Exception e) {
			logger.error("采集酒店各语言名称失败："+hotel.getHotelName()+hotel.getUrl(), e);
		}
		
		return result;
	}
}