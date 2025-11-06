package com.qcmz.cmc.proxy.spider.weixin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qcmz.cmc.comparator.WeixinArticleListComparator;
import com.qcmz.cmc.proxy.spider.Spider;
import com.qcmz.cmc.proxy.spider.weixin.vo.ArticleListBean;
import com.qcmz.cmc.proxy.spider.weixin.vo.ArticleListResp;
import com.qcmz.cmc.proxy.spider.weixin.vo.OfficialAccountBean;
import com.qcmz.cmc.proxy.spider.weixin.vo.OfficialAccountGetResp;
import com.qcmz.cmc.proxy.spider.weixin.vo.WeixinLoginResult;
import com.qcmz.framework.exception.ProxyException;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.JsonUtil;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.SystemUtil;

/**
 * selenium2.53.1 + chrome104.0.5112.81 + chromedriver_win32_104.0.5112.79
 */
public class WeixinSpider extends Spider{
	private static Logger logger = Logger.getLogger(WeixinSpider.class);
	
	/**
	 * 登录cookie存储路径
	 */
	static final String COOKIE_PATH = "c:/test/wxcookie.txt";
	
	static final String COOKIE_SEPARATOR = "┋";
	
	/**
	 * 微信公众平台URL
	 */
	private static final String WEIXIN_MP_URL = "https://mp.weixin.qq.com";
	
	/**
	 * 打开浏览器并登录
	 * @return
	 * @exception ProxyException
	 */
	public static WeixinLoginResult openAndLogin(){
		WebDriver driver = getChromeDriver();
		String token = login(driver);
		
		WeixinLoginResult result = new WeixinLoginResult();
		result.setDriver(driver);
		result.setToken(token);
		return result;
	}
	
	/**
	 * selenium模拟登录
	 * @param driver
	 * @return token
	 */
	private static String login(WebDriver driver){
		//1.访问微信公众平台
		driver.get(WEIXIN_MP_URL);
		
		//读取并添加cookie(先访问需要登录的地址，再设置cookies登录跳转，否则会出现invalid cookie domain异常)
//		boolean addCookie = addCookies(driver);
		boolean addCookie = false;	//TODO:加载cookie后无法跳过扫码页		
		if(addCookie){
			driver.get(WEIXIN_MP_URL);
		}else{
			//2.使用账号登录(登录后还得扫码^_!)
			/*
			WebElement typeWebElement = driver.findElement(By.xpath("//*[@class='login__type__container login__type__container__scan']/a"));
	        typeWebElement.click();
	
	        WebElement usernameWebElement = driver.findElement(By.name("account"));
	        usernameWebElement.clear();
	        usernameWebElement.sendKeys(username);
	
	        WebElement passwordWebElement = driver.findElement(By.name("password"));
	        passwordWebElement.clear();
	        passwordWebElement.sendKeys(password);
	
	        WebElement helpWebElement = driver.findElement(By.className("icon_checkbox"));
	        helpWebElement.click();
	
	        WebElement btnWebElement = driver.findElement(By.className("btn_login"));
	        btnWebElement.click();
			 */
			
			//2.扫码登录
			SystemUtil.sleep(30000);
			
			//保存cookie
//			saveCookies(driver);
		}
		
		String[] arr = driver.getCurrentUrl().split("token=");
		String token = null;
		if(arr.length>=2){
			token = arr[1];
		}
		
		return token;
	}
	
	/**
	 * 采集指定公众号文章列表
	 * @param officialAccountName
	 * @param lastMsgId
	 * @return
	 */
	public static List<ArticleListBean> spiderArticleList(WeixinLoginResult login, String officialAccountName, Long lastMsgId){
		List<ArticleListBean> articleList = new ArrayList<ArticleListBean>();

		//获取公众号信息
        OfficialAccountBean account = getOfficialAccountInfo(login, officialAccountName);
        if(account==null) return articleList;
        
        //获取公众号文章列表
        int maxDays = 30;
        int pageSize = 10;
        int seq = 0;
        ArticleListResp articleListResp = null;
        boolean hasData = false;
        Long minTime = DateUtil.addDay(DateUtil.getSysDateOnly(), -maxDays).getTime()/1000;
        do{
        	articleListResp = getArticleList(login, account.getFakeid(), seq, pageSize);
        	if(articleListResp==null) break;
        	if(!articleListResp.success()){
        		logger.error("获取文章列表失败："+articleListResp.getBaseResp().getErrMsg());
        		break;
        	}
        	
        	hasData = false;
        	for (ArticleListBean articleListBean : articleListResp.getAppMsgList()) {
        		if(articleListBean.getUpdateTime()>=minTime){
        			if(lastMsgId==null || articleListBean.getAppmsgid()>lastMsgId){
        				articleList.add(articleListBean);
        				hasData = true;
        			}
        		}
        	}
        	if(!hasData) break;
        	
//        	logger.info("【"+officialAccountName+"】获取文章列表，已获取数【"+articleList.size()+"】");
        	SystemUtil.sleep(NumberUtil.random(45000, 20000));
        	seq = seq + pageSize;
        }while(true);       
        
        Collections.sort(articleList, new WeixinArticleListComparator());
        
        return articleList;
	}
	
	/**
	 * 按名称搜索公众号
	 * @param driver
	 * @param token
	 * @param officialAccountName
	 * @return
	 */
	private static OfficialAccountBean getOfficialAccountInfo(WeixinLoginResult login, String officialAccountName){
		Map<String, String> params = new HashMap<>();
        params.put("action", "search_biz");
        params.put("begin", "0");
        params.put("count", "5");
        params.put("query", officialAccountName);
        params.put("token",login.getToken());
        params.put("lang", "zh_CN");
        params.put("f", "json");
        params.put("ajax", "1");
        
        String url = "https://mp.weixin.qq.com/cgi-bin/searchbiz?"+HttpUtil.map2Params(params);
        login.getDriver().get(url);
        
        WebElement we = login.getDriver().findElement(By.tagName("pre"));
        if(we!=null){
//        	FileUtil.stringToFile(we.getText(), "c:/test/searchbiz"+System.currentTimeMillis()+".json");
        	OfficialAccountGetResp resp = JsonUtil.string2Object(we.getText(), OfficialAccountGetResp.class);
        	if(resp.freqControl()){
        		throw new ProxyException(resp.toString());
        	}else if(resp.success()){
        		for (OfficialAccountBean bean : resp.getList()) {
        			if(officialAccountName.equals(bean.getNickname())){
        				return bean;
        			}
        		} 
        	}
        }
        
       return null;
	}
	
	/**
	 * 获取文章列表
	 * @param driver
	 * @param token
	 * @param fakeId
	 * @param begin
	 * @param count
	 * @return
	 * @exception ProxyException 限流抛出异常便于停止采集
	 */
	private static ArticleListResp getArticleList(WeixinLoginResult login, String fakeId, Integer begin, Integer count) {
		Map<String, String> params = new HashMap<>();
		params.put("token", login.getToken());
		params.put("lang", "zh_CN");
		params.put("f", "json");
		params.put("ajax", "1");
		params.put("random", String.valueOf(new Random(1).nextDouble()));
		params.put("action", "list_ex");
		params.put("query", "");
		params.put("type", "9");
		params.put("fakeid", fakeId);
		
		params.put("begin", begin + "");
		params.put("count", count + "");
		
        String url = "https://mp.weixin.qq.com/cgi-bin/appmsg?"+HttpUtil.map2Params(params);
        login.getDriver().get(url);
		
        WebElement we = login.getDriver().findElement(By.tagName("pre"));
        if(we!=null){
//        	FileUtil.stringToFile(we.getText(), "c:/test/appmsg"+System.currentTimeMillis()+".json");
        	ArticleListResp resp = JsonUtil.string2Object(we.getText(), ArticleListResp.class);
        	if(resp.freqControl()){
        		throw new ProxyException(resp.toString());
        	}
        	return resp;
        }else{
        	return null;
        }
	}
	
	/*
	private static void getArticleDetail(WebDriver driver, String articleUrl, String title){
		driver.get(articleUrl);
    	FileUtil.stringToFile(driver.getPageSource(), "c:/test/"+title+".html");
        WebElement element = driver.findElement(By.xpath("//div[@id='js_content']"));
	}*/
	
	
}
