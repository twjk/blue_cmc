package com.qcmz.cmc.process;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.ImageRecognitionCatMap;
import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.entity.CmcIrCat;
import com.qcmz.cmc.entity.CmcIrImage;
import com.qcmz.cmc.entity.CmcIrRecognition;
import com.qcmz.cmc.proxy.image.baidu.BaiduImageProxy;
import com.qcmz.cmc.service.IImageRecognitionService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.util.UserUtil;
import com.qcmz.cmc.vo.ImageRecognizeResult;
import com.qcmz.cmc.ws.provide.vo.ImageRecognitionImageBean;
import com.qcmz.cmc.ws.provide.vo.ImageRecognizeBean;
import com.qcmz.cms.ws.provide.locator.ArticleRightWS;
import com.qcmz.cms.ws.provide.vo.ArticleCatRightBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.exception.ParamException;
import com.qcmz.framework.exception.SystemException;
import com.qcmz.framework.util.FilePathUtil;
import com.qcmz.framework.util.FileServiceUtil;
import com.qcmz.framework.util.FileTypeUtil;
import com.qcmz.framework.util.ImageUtil;
import com.qcmz.framework.util.StringUtil;

@Component
public class ImageRecognitionProcess {
	@Autowired
	private IImageRecognitionService imageRecognitionService;
	@Autowired
	private ImageRecognitionCatMap imageRecognitionCatMap;
	
	/**
	 * 缩略图最大宽度/高度
	 */
	private static int THUMB_MAX = 300;
		
	/**
	 * 图像识别
	 * @param bean
	 * @exception ParamException,DataException,SystemException
	 */
	public ImageRecognitionImageBean recognize(ImageRecognizeBean bean){
		//数据校验
		//最短边至少15px，最长边最大4096px,支持jpg/png/bmp格式
		String fileType = FileTypeUtil.getImageType(bean.getFile());
		if(!"jpg".equals(fileType) && !"png".equals(fileType) && !"bmp".equals(fileType)){
			throw new ParamException("图像格式有误");
		}
		int[] wh = ImageUtil.getWH(bean.getFile());
		int width = wh[0];
		int height = wh[1];
		if(width<15 || width>4096 || height<15 || height>4096){
			throw new ParamException("图像长宽有误");
		}
		
		//权限校验
		CmcIrCat cat = imageRecognitionCatMap.getCat(bean.getCatId());
		if(cat==null || !SystemConstants.STATUS_ON.equals(cat.getStatus())){
			throw new ParamException("分类编号有误");
		}
		//收费分类，校验是否有权限
		if(SystemConstants.STATUS_ON.equals(cat.getCharge())){
			List<ArticleCatRightBean> list = ArticleRightWS.findCatRight(bean.getUid(), Long.valueOf(BusinessConstant.CATID_USERVIP));
			boolean hasRight = false;
			if(list!=null){
				for (ArticleCatRightBean rightBean : list) {
					if(rightBean.isValid()){
						hasRight = true;
						break;
					}
				}
			}
			if(!hasRight){
				throw new DataException("请购买会员");
			}
		}
		
		//文件上传OSS
		String dirPath = UserUtil.getImageRecognitionBasePath();
		String fileName = FilePathUtil.newFileName(fileType);
		String imagePath = new StringBuilder(dirPath).append("/").append(fileName).toString();
		String imageUrl = FileServiceUtil.saveOrUploadFile(bean.getFile(), dirPath, fileName);
		
		if(StringUtil.isBlankOrNull(imageUrl)){
			throw new SystemException("图像保存失败");
		}
		
		//识别
		List<ImageRecognizeResult> list = null;
		if(DictConstant.DICT_IMAGERECOGNITION_PLANT.equals(bean.getCatId())){
			list = BaiduImageProxy.plant(imageUrl);
		}else if(DictConstant.DICT_IMAGERECOGNITION_ANIMAL.equals(bean.getCatId())){
			list = BaiduImageProxy.animal(imageUrl);
		}else if(DictConstant.DICT_IMAGERECOGNITION_LOGO.equals(bean.getCatId())){
			list = BaiduImageProxy.logo(imageUrl);
		}else if(DictConstant.DICT_IMAGERECOGNITION_GENERAL.equals(bean.getCatId())){
			list = BaiduImageProxy.general(imageUrl);
		}else if(DictConstant.DICT_IMAGERECOGNITION_DISH.equals(bean.getCatId())){
			list = BaiduImageProxy.dish(imageUrl);
		}else if(DictConstant.DICT_IMAGERECOGNITION_LANDMARK.equals(bean.getCatId())){
			list = BaiduImageProxy.landmark(imageUrl);
		}else if(DictConstant.DICT_IMAGERECOGNITION_REDWINE.equals(bean.getCatId())){
			list = BaiduImageProxy.redwine(imageUrl);
		}else if(DictConstant.DICT_IMAGERECOGNITION_CURRENCY.equals(bean.getCatId())){
			list = BaiduImageProxy.currency(imageUrl);
		}else if(DictConstant.DICT_IMAGERECOGNITION_CAR.equals(bean.getCatId())){
			list = BaiduImageProxy.car(imageUrl);
		}else if(DictConstant.DICT_IMAGERECOGNITION_STAR.equals(bean.getCatId())){
			list = BaiduImageProxy.star(imageUrl);
		}
		if(list.isEmpty()){
			throw new DataException("无匹配结果");
		}
		
		//缩略图
		String thumbUrl = imageUrl;
		if(width>THUMB_MAX || height>THUMB_MAX){
			String absThumbPath = UserUtil.getImageRecognitionAbsTempFilePath(FilePathUtil.toThumb(imagePath));
			boolean zoom = ImageUtil.zoomImageScale(bean.getFile(), THUMB_MAX, THUMB_MAX, absThumbPath);
			if(zoom){
				thumbUrl = FileServiceUtil.saveOrUploadFile(new File(absThumbPath), dirPath);
			}
		}
		
		//入库
		CmcIrImage image = new CmcIrImage();
		image.setCatid(bean.getCatId());
		image.setUserid(bean.getUid());
		image.setImageurl(imageUrl);
		image.setThumburl(thumbUrl);
		image.setCreatetime(new Date());
		
		CmcIrRecognition recog = null;
		for (ImageRecognizeResult irr : list) {
			recog = new CmcIrRecognition();
			recog.setContent(irr.getContent());
			recog.setScore(irr.getScore());
			image.getCmcIrRecognitions().add(recog);
		}
		imageRecognitionService.saveImage(image);
		
		//返回
		return BeanConvertUtil.toImageRecognitionImageBean(image, imageRecognitionCatMap.getCatName(image.getCatid()));
	}
}
