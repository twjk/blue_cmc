package com.qcmz.cmc.proxy.spider;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.StringUtil;

public class Spider {
	/**
	 * WebDriver路径
	 */
	private static final String WEBDRIVER_PATH = "C:/Users/abing/AppData/Local/Google/Chrome/Application/chromedriver.exe";
	
	/**
	 * 登录cookie存储路径
	 */
	protected static String COOKIE_PATH;
	
	protected static String COOKIE_SEPARATOR = "┋";
	
	/**
	 * 
	 * @param chromeDriverPath chromeDriver文件路径
	 * @param headless 是否隐藏浏览器界面
	 * @param disableImage 是否禁止加载图片
	 * @param headers
	 * @return
	 */
	protected static ChromeDriver getChromeDriver(boolean headless, boolean disableImage, Map<String, String> headers){
		System.setProperty("webdriver.chrome.driver", WEBDRIVER_PATH);
		
        ChromeOptions chromeOptions = new ChromeOptions();
        //无浏览器模式
        if (headless) {
            chromeOptions.addArguments("--headless"); 
        }
        
        if(headers!=null && !headers.isEmpty()){
        	for (String key : headers.keySet()) {
        		System.out.println(key+"="+headers.get(key));
        		chromeOptions.addArguments(key+"="+headers.get(key));
			}
        }
       
        // 禁止加载图片
        if (disableImage) {
            chromeOptions.addArguments("blink-settings=imagesEnabled=false"); 
            chromeOptions.addArguments("--disable-images");
        }
        
        chromeOptions.addArguments("--disable-gpu"); // 禁用GPU
        chromeOptions.addArguments("--disable-software-rasterizer"); // 禁用3D软件光栅化器
        chromeOptions.addArguments("--no-sandbox"); // 允许Linux root用户执行
        chromeOptions.addArguments("--disable-dev-shm-usage"); // 解决某些VM环境中Chrome崩溃问题
//      chromeOptions.addArguments("--incognito"); // 无痕模式
        chromeOptions.addArguments("--disable-plugins"); // 禁用插件
        chromeOptions.addArguments("--disable-extensions"); // 禁用扩展
        chromeOptions.addArguments("--disable-popup-blocking"); // 关闭弹窗拦截
        chromeOptions.addArguments("--ignore-certificate-errors"); // 忽略证书错误
        chromeOptions.addArguments("--allow-running-insecure-content"); // 允许加载不安全内容
        chromeOptions.addArguments("--start-maximized");//最大化窗口，防止有些元素被隐藏
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		chromeOptions.addArguments("--disable-blink-features=AutomationControlled");//禁用浏览器正在被自动化程序控制的提示
        
        return new ChromeDriver(chromeOptions); // 实例化ChromeDriver
	}
	
	protected static ChromeDriver getChromeDriver(){
		return getChromeDriver(false, false, null);
	}
	
	/**
	 * 关闭浏览器
	 * @param driver
	 */
	public static void quitAndClose(WebDriver driver){
		if(driver!=null){
			try {
				driver.close();
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * cookie内容保存到文件
	 * @param cookies
	 * @param cookiePath
	 */
	protected static void saveCookie(Set<Cookie> cookies, String cookiePath){
		if(cookies==null || cookies.isEmpty()) return;
		
		List<String> lines = new ArrayList<String>();
		String line = null;
		try {
			for (Cookie cookie : cookies) {
				line = new StringBuilder(cookie.getName())
						.append(COOKIE_SEPARATOR).append(URLDecoder.decode(cookie.getValue(), "UTF-8"))
						.append(COOKIE_SEPARATOR).append(cookie.getDomain())
						.append(COOKIE_SEPARATOR).append(cookie.getPath())
						.append(COOKIE_SEPARATOR).append(DateUtil.formatDateTime(cookie.getExpiry()))
						.append(COOKIE_SEPARATOR).append(cookie.isSecure())
						.toString();
				lines.add(line);
			}
			FileUtil.writeLines(new File(cookiePath), lines, false);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 从文件获取所有cookie
	 * @param driver
	 * @param cookiePath
	 * @return
	 */
	protected static List<Cookie> findCookie(String cookiePath){
		List<Cookie> result = new ArrayList<Cookie>();
		
		List<String> lines = FileUtil.readLine(new File(cookiePath));
		if(lines==null || lines.isEmpty()) return result;
		
		String[] arrLine = null;
		for (String line : lines) {
			arrLine = StringUtil.splitPreserveAllTokens(line, COOKIE_SEPARATOR);
			result.add(new Cookie(arrLine[0], arrLine[1], arrLine[2], arrLine[3], DateUtil.parseDateTime(arrLine[4]), Boolean.valueOf(arrLine[5])));
		}
		return result;
	}
	
	/**
	 * 从文件获取指定cookie
	 * @param cookiePath
	 * @param cookieName
	 * @return
	 */
	protected static Cookie getCookie(String cookiePath, String cookieName){
		List<String> lines = FileUtil.readLine(new File(cookiePath));
		if(lines==null || lines.isEmpty()) return null;
		
		String[] arrLine = null;
		for (String line : lines) {
			arrLine = StringUtil.splitPreserveAllTokens(line, COOKIE_SEPARATOR);
			if(cookieName.equals(arrLine[0])){
				return new Cookie(arrLine[0], arrLine[1], arrLine[2], arrLine[3], DateUtil.parseDateTime(arrLine[4]), Boolean.valueOf(arrLine[5]));
			}
		}
		return null;
	}
	
	/**
	 * 从文件读取Cookies并写入会话
	 * @param driver
	 * @param cookiePath
	 * @return
	 */
	protected static boolean addCookies(WebDriver driver, String cookiePath){
		List<Cookie> cookies = findCookie(cookiePath);
		for (Cookie cookie : cookies) {
			driver.manage().addCookie(cookie);
		}
		return true;
	}

}
