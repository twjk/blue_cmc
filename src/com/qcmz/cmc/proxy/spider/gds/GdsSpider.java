package com.qcmz.cmc.proxy.spider.gds;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;

import com.qcmz.cmc.proxy.spider.Spider;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.HttpsSSLFactoryUtil;
import com.qcmz.framework.util.SystemUtil;

public class GdsSpider extends Spider{
	
	/**
	 * 微信公众平台URL
	 */
	private static final String URL = "https://www.gds.org.cn/";
	/**
	 * 登录cookie存储路径
	 */
	static final String COOKIE_PATH = "c:/test/gdscookie.txt";
	
	public static void getAndSaveAccessToken(){
		getCookie("accessToken", COOKIE_PATH);
	}
	
	public static void main(String[] args) {
		
		
		ChromeDriver driver = null;
		try {
			driver = getChromeDriver();
			
			//1.访问
			driver.get(URL);
			
			//2.手工登录
			SystemUtil.sleep(40000);
			
			//cookie写入文件
			saveCookie(driver.manage().getCookies(), COOKIE_PATH);
			
			//获取token
			Cookie cookie = driver.manage().getCookieNamed("accessToken");
			String authorization = "Bearer " + cookie.getValue();
			
			String url = "https://bff.gds.org.cn/gds/searching-api/productservice/category-product-changeclassify?TypeSourceCode=GPC&ProductCategoryCode=10000104&EntitiesWord=%E9%A3%9F%E5%93%81&PageSize=32&PageIndex=1";

			HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
			conn.setSSLSocketFactory(HttpsSSLFactoryUtil.getSSLFactory());
			conn.setConnectTimeout(HttpUtil.CONN_TIMEOUT_DEFAULT);
			conn.setReadTimeout(300000);
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestProperty("Authorization", authorization);
	        conn.setDoOutput(true);
	    	
	    	if(conn.getResponseCode()!=200){
	        	System.out.println(conn.getResponseCode()+conn.getResponseMessage());
	        }
	        
	        InputStream is = conn.getInputStream();
	        String resultJson = FileUtil.inputStreamToString(is, FileUtil.ENCODING_UTF8);
	        System.out.println(resultJson);
			
		     
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			quitAndClose(driver);
		}
	}
}
