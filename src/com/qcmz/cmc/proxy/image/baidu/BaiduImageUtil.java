package com.qcmz.cmc.proxy.image.baidu;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduAccessTokenBean;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduAccessTokenResp;
import com.qcmz.framework.util.FileUtil;
import com.qcmz.framework.util.HttpUtil;
import com.qcmz.framework.util.SecretUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.UrlUtil;

public class BaiduImageUtil {
	private static Logger logger = Logger.getLogger(BaiduImageUtil.class);
	
	/**
	 * Access Token URL
	 */
	public static final String URL_ACCESSTOKEN = "https://aip.baidubce.com/oauth/2.0/token";
	/**
	 * 通用物体和场景识别URL
	 */
	public static final String URL_GENERAL = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general";
	/**
	 * 动物识别URL
	 */
	public static final String URL_ANIMAL = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal";
	/**
	 * 植物识别URL
	 */
	public static final String URL_PLANT = "https://aip.baidubce.com/rest/2.0/image-classify/v1/plant";
	/**
	 * logo识别URL
	 */
	public static final String URL_LOGO = "https://aip.baidubce.com/rest/2.0/image-classify/v2/logo";
	/**
	 * 菜品识别URL
	 */
	public static final String URL_DISH = "https://aip.baidubce.com/rest/2.0/image-classify/v2/dish";
	/**
	 * 红酒识别URL
	 */
	public static final String URL_REDWINE = "https://aip.baidubce.com/rest/2.0/image-classify/v1/redwine";
	/**
	 * 地标识别URL
	 */
	public static final String URL_LANDMARK = "https://aip.baidubce.com/rest/2.0/image-classify/v1/landmark";
	/**
	 * 货币识别URL
	 */
	public static final String URL_CURRENCY = "https://aip.baidubce.com/rest/2.0/image-classify/v1/currency";
	/**
	 * 车型识别URL
	 */
	public static final String URL_CAR = "https://aip.baidubce.com/rest/2.0/image-classify/v1/car";
	/**
	 * 图像审核URL
	 */
	public static final String URL_USER_DEFINED = "https://aip.baidubce.com/rest/2.0/solution/v1/img_censor/v2/user_defined";
	
	/**
	 * 图像识别API Key
	 */
	public static final String API_KEY_IMAGE = "4okv3HFvQX91Nbh0roGmfQeM";
	/**
	 * 图像审核API Key
	 */
	public static final String API_KEY_CENSORING = "nU0zlIQfjeOHq1RsznTn6sKi";
	/**
	 * <apiKey, secretKey>
	 */
	private static Map<String, String> secretKeyMap = new HashMap<String, String>();
	static{
		//图像识别
		secretKeyMap.put(API_KEY_IMAGE, "cz0L6iyYPTN4AX087PqkQr8U8e2uU0y1");
		//图像识别
		secretKeyMap.put(API_KEY_CENSORING, "1PAwkT0fpW831Ly1U11CpIGPgZiSgy7r");
	}
	
	/**
	 * <apiKey, BaiduAccessTokenBean>
	 */
	private static Map<String, BaiduAccessTokenBean> accessTokenMap = new HashMap<String, BaiduAccessTokenBean>();
	//访问量较大时，改为定时校验是否过期，并获取新的token
	public static String getAccessToken(String apiUrl){
		String apiKey = API_KEY_IMAGE;
		if(URL_USER_DEFINED.equals(apiUrl)){
			apiKey = API_KEY_CENSORING;
		}
		
		BaiduAccessTokenBean accessToken = accessTokenMap.get(apiKey);
		if(accessToken==null || !accessToken.isValid()){
			accessToken = getAccessTokenFromBaidu(apiKey);
		}
		return accessToken.getAccessToken();
	}
	
	private static BaiduAccessTokenBean getAccessTokenFromBaidu(String apiKey){
		try {
			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("grant_type", "client_credentials");
			paramsMap.put("client_id", apiKey);
			paramsMap.put("client_secret", secretKeyMap.get(apiKey));
			
			String json = HttpUtil.post(URL_ACCESSTOKEN, HttpUtil.map2Params(paramsMap));
			BaiduAccessTokenResp resp = JSON.parseObject(json, BaiduAccessTokenResp.class);
			if(StringUtil.notBlankAndNull(resp.getAccess_token())){
				BaiduAccessTokenBean result = new BaiduAccessTokenBean();
				result.setAccessToken(resp.getAccess_token());
				result.setExpires(resp.getExpires_in()*1000);
				return result;
			}else{
				logger.error("获取AccessToken失败："+resp.getError()+resp.getError_description());
			}
		} catch (Exception e) {
			logger.error("获取AccessToken失败", e);
		}
		return null;
	}
	
	public static String post(String imagePath, String apiUrl){
		return post(imagePath, apiUrl, null);
	}
	
	public static String post(String imagePath, String apiUrl, Map<String, String> params){
		String url = new StringBuilder(apiUrl).append("?access_token=").append(getAccessToken(apiUrl)).toString();
		
		StringBuilder sbParams = new StringBuilder();
		if(imagePath.startsWith("http")){
			if(BaiduImageUtil.URL_USER_DEFINED.equals(apiUrl)){
				sbParams.append("imgUrl=").append(UrlUtil.encode(imagePath));
			}else{
				sbParams.append("url=").append(UrlUtil.encode(imagePath));
			}
		}else{
			sbParams.append("image=").append(UrlUtil.encode(SecretUtil.encodeByBase64(FileUtil.fileToByteArray(imagePath))));
		}
		
		if(params!=null && !params.isEmpty()){
			for (String key : params.keySet()) {
				sbParams.append("&").append(key).append("=").append(params.get(key));
			}
		}
		
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("Content-Type", "application/x-www-form-urlencoded");
		
		String json = HttpUtil.post(url, sbParams.toString(), properties);
//		System.out.println(json);
		return json;
	}
}
