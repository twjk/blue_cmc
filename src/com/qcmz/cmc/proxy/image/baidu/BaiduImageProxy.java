package com.qcmz.cmc.proxy.image.baidu;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageAnimalBean;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageAnimalResp;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageCarBean;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageCarResp;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageCurrencyBean;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageCurrencyResp;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageDishBean;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageDishResp;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageGeneralBean;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageGeneralResp;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageLandmarkBean;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageLandmarkResp;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageLogoBean;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageLogoResp;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImagePlantBean;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImagePlantResp;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageRedWineBean;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageRedWineResp;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageStarBean;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageStarResp;
import com.qcmz.cmc.proxy.image.baidu.vo.BaiduImageStarStarBean;
import com.qcmz.cmc.vo.ImageRecognizeResult;
import com.qcmz.framework.util.JsonUtil;
import com.qcmz.framework.util.StringUtil;

public class BaiduImageProxy {
	private static Logger logger = Logger.getLogger(BaiduImageProxy.class);
	
	/**
	 * 通用物体和场景识别
	 * @param imagePath 文件路径或url
	 * @return
	 */
	public static List<ImageRecognizeResult> general(String imagePath){
		List<ImageRecognizeResult> result = new ArrayList<ImageRecognizeResult>();
		ImageRecognizeResult bean = null;
		try {
			String json = BaiduImageUtil.post(imagePath, BaiduImageUtil.URL_GENERAL);
			BaiduImageGeneralResp resp = JsonUtil.string2Object(json, BaiduImageGeneralResp.class);
			for (BaiduImageGeneralBean r : resp.getResult()) {
				if(StringUtil.isBlankOrNull(r.getKeyword())) continue;
				bean = new ImageRecognizeResult();
				bean.setContent(r.getKeyword());
				bean.setScore(r.getScore());
				result.add(bean);
			}
		} catch (Exception e) {
			logger.error("通用物体和场景识别失败："+imagePath, e);
		}
		return result;
	}
	
	/**
	 * 动物识别
	 * @param imagePath 文件路径或url
	 * @return
	 */
	public static List<ImageRecognizeResult> animal(String imagePath){
		List<ImageRecognizeResult> result = new ArrayList<ImageRecognizeResult>();
		ImageRecognizeResult bean = null;
		try {
			String json = BaiduImageUtil.post(imagePath, BaiduImageUtil.URL_ANIMAL);
			BaiduImageAnimalResp resp = JsonUtil.string2Object(json, BaiduImageAnimalResp.class);
			for (BaiduImageAnimalBean r : resp.getResult()) {
				if(StringUtil.isBlankOrNull(r.getName())) continue;
				bean = new ImageRecognizeResult();
				bean.setContent(r.getName());
				bean.setScore(r.getScore());
				result.add(bean);
			}
		} catch (Exception e) {
			logger.error("动物识别失败："+imagePath, e);
		}
		return result;
	}
	
	/**
	 * 植物识别
	 * @param imagePath 文件路径或url
	 * @return
	 */
	public static List<ImageRecognizeResult> plant(String imagePath){
		List<ImageRecognizeResult> result = new ArrayList<ImageRecognizeResult>();
		ImageRecognizeResult bean = null;
		try {
			String json = BaiduImageUtil.post(imagePath, BaiduImageUtil.URL_PLANT);
			BaiduImagePlantResp resp = JsonUtil.string2Object(json, BaiduImagePlantResp.class);
			for (BaiduImagePlantBean r : resp.getResult()) {
				if(StringUtil.isBlankOrNull(r.getName())) continue;
				bean = new ImageRecognizeResult();
				bean.setContent(r.getName());
				bean.setScore(r.getScore());
				result.add(bean);
			}
		} catch (Exception e) {
			logger.error("植物识别失败："+imagePath, e);
		}
		return result;
	}
	
	/**
	 * logo识别
	 * @param imagePath 文件路径或url
	 * @return
	 */
	public static List<ImageRecognizeResult> logo(String imagePath){
		List<ImageRecognizeResult> result = new ArrayList<ImageRecognizeResult>();
		ImageRecognizeResult bean = null;
		try {
			String json = BaiduImageUtil.post(imagePath, BaiduImageUtil.URL_LOGO);
			BaiduImageLogoResp resp = JsonUtil.string2Object(json, BaiduImageLogoResp.class);
			for (BaiduImageLogoBean r : resp.getResult()) {
				if(StringUtil.isBlankOrNull(r.getName())) continue;
				bean = new ImageRecognizeResult();
				bean.setContent(r.getName());
				bean.setScore(r.getProbability());
				result.add(bean);
			}
		} catch (Exception e) {
			logger.error("logo识别失败："+imagePath, e);
		}
		return result;
	}
	
	/**
	 * 菜品识别
	 * @param imagePath 文件路径或url
	 * @return
	 */
	public static List<ImageRecognizeResult> dish(String imagePath){
		List<ImageRecognizeResult> result = new ArrayList<ImageRecognizeResult>();
		ImageRecognizeResult bean = null;
		try {
			String json = BaiduImageUtil.post(imagePath, BaiduImageUtil.URL_DISH);
			BaiduImageDishResp resp = JsonUtil.string2Object(json, BaiduImageDishResp.class);
			for (BaiduImageDishBean r : resp.getResult()) {
				if(StringUtil.isBlankOrNull(r.getName())) continue;
				bean = new ImageRecognizeResult();
				bean.setContent(r.getName());
				bean.setScore(r.getProbability());
				result.add(bean);
			}
		} catch (Exception e) {
			logger.error("菜品识别失败："+imagePath, e);
		}
		return result;
	}
	
	/**
	 * 红酒识别
	 * @param imagePath 文件路径或url
	 * @return
	 */
	public static List<ImageRecognizeResult> redwine(String imagePath){
		List<ImageRecognizeResult> result = new ArrayList<ImageRecognizeResult>();
		ImageRecognizeResult bean = null;
		try {
			String json = BaiduImageUtil.post(imagePath, BaiduImageUtil.URL_REDWINE);
			BaiduImageRedWineResp resp = JsonUtil.string2Object(json, BaiduImageRedWineResp.class);
			for (BaiduImageRedWineBean r : resp.getResult()) {
				if(StringUtil.isBlankOrNull(r.getWineNameCn())) continue;
				bean = new ImageRecognizeResult();
				bean.setContent(r.getWineNameCn());
				result.add(bean);
			}
		} catch (Exception e) {
			logger.error("红酒识别失败："+imagePath, e);
		}
		return result;
	}
	
	/**
	 * 地标识别
	 * @param imagePath 文件路径或url
	 * @return
	 */
	public static List<ImageRecognizeResult> landmark(String imagePath){
		List<ImageRecognizeResult> result = new ArrayList<ImageRecognizeResult>();
		ImageRecognizeResult bean = null;
		try {
			String json = BaiduImageUtil.post(imagePath, BaiduImageUtil.URL_LANDMARK);
			BaiduImageLandmarkResp resp = JsonUtil.string2Object(json, BaiduImageLandmarkResp.class);
			for (BaiduImageLandmarkBean r : resp.getResult()) {
				if(StringUtil.isBlankOrNull(r.getLandmark())) continue;
				bean = new ImageRecognizeResult();
				bean.setContent(r.getLandmark());
				result.add(bean);
			}
		} catch (Exception e) {
			logger.error("地标识别失败："+imagePath, e);
		}
		return result;
	}
	
	/**
	 * 货币识别
	 * @param imagePath 文件路径或url
	 * @return
	 */
	public static List<ImageRecognizeResult> currency(String imagePath){
		List<ImageRecognizeResult> result = new ArrayList<ImageRecognizeResult>();
		ImageRecognizeResult bean = null;
		try {
			String json = BaiduImageUtil.post(imagePath, BaiduImageUtil.URL_CURRENCY);
			BaiduImageCurrencyResp resp = JsonUtil.string2Object(json, BaiduImageCurrencyResp.class);
			for (BaiduImageCurrencyBean r : resp.getResult()) {
				if(StringUtil.isBlankOrNull(r.getCurrencyName())) continue;
				bean = new ImageRecognizeResult();
				bean.setContent(r.getCurrencyName());
				result.add(bean);
			}
		} catch (Exception e) {
			logger.error("货币识别失败："+imagePath, e);
		}
		return result;
	}
	
	/**
	 * 车型识别
	 * @param imagePath 文件路径或url
	 * @return
	 */
	public static List<ImageRecognizeResult> car(String imagePath){
		List<ImageRecognizeResult> result = new ArrayList<ImageRecognizeResult>();
		ImageRecognizeResult bean = null;
		try {
			String json = BaiduImageUtil.post(imagePath, BaiduImageUtil.URL_CAR);
			BaiduImageCarResp resp = JsonUtil.string2Object(json, BaiduImageCarResp.class);
			for (BaiduImageCarBean r : resp.getResult()) {
				if(StringUtil.isBlankOrNull(r.getName())) continue;
				bean = new ImageRecognizeResult();
				bean.setContent(r.getName());
				bean.setScore(r.getScore());
				result.add(bean);
			}
		} catch (Exception e) {
			logger.error("车型识别失败："+imagePath, e);
		}
		return result;
	}
	
	/**
	 * 公众人物识别
	 * @param imagePath 文件路径或url
	 * @return
	 */
	public static List<ImageRecognizeResult> star(String imagePath){
		List<ImageRecognizeResult> result = new ArrayList<ImageRecognizeResult>();
		ImageRecognizeResult bean = null;
		try {
			String json = BaiduImageUtil.post(imagePath, BaiduImageUtil.URL_USER_DEFINED);
			BaiduImageStarResp resp = JsonUtil.string2Object(json, BaiduImageStarResp.class);
			if(StringUtil.notBlankAndNull(resp.getError_code())){
				logger.error(new StringBuilder("公众人物识别失败：【").append(resp.getError_code()).append("】").append(resp.getError_msg()).append("，").append(imagePath));
				return result;
			}
			if(resp.getData()!=null){
				for (BaiduImageStarBean dataBean : resp.getData()) {
					if(dataBean.getStars()!=null){
						for (BaiduImageStarStarBean starsBean : dataBean.getStars()) {
							bean = new ImageRecognizeResult();
							bean.setContent(starsBean.getName());
							bean.setScore(starsBean.getProbability());
							result.add(bean);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("公众人物识别失败："+imagePath, e);
		}
		return result;
	}
	
	public static void main(String[] args) {
		String imagePath = null;
		imagePath = "c:/test/imgr/star_wujing.jpg";
//		imagePath = "https://qcbj.oss-cn-beijing.aliyuncs.com/ir/dish.png";
		List<ImageRecognizeResult> list = star(imagePath);
		for (ImageRecognizeResult r : list) {
			System.out.println(r.getContent()+","+r.getScore());
		}
	}
}
