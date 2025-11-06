package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.ImageRecognitionCatMap;
import com.qcmz.cmc.dao.IImageRecognitionDao;
import com.qcmz.cmc.entity.CmcIrImage;
import com.qcmz.cmc.entity.CmcIrRecognition;
import com.qcmz.cmc.service.IImageRecognitionService;
import com.qcmz.cmc.util.BeanConvertUtil;
import com.qcmz.cmc.ws.provide.vo.ImageRecognitionImageBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.umc.ws.provide.locator.UserMap;
import com.qcmz.umc.ws.provide.vo.UserSimpleBean;

@Service
public class ImageRecognitionServiceImpl implements IImageRecognitionService {
	@Autowired
	private IImageRecognitionDao imageRecognitionDao;
	@Autowired
	private ImageRecognitionCatMap imageRecognitionCatMap;
	
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcIrImage>
	 */
	@SuppressWarnings("unchecked")
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		imageRecognitionDao.queryByMapTerm(map, pageBean);
		
		List<CmcIrImage> images = (List<CmcIrImage>) pageBean.getResultList();
		if(images.isEmpty()) return;
		
		List<Long> imageIds = new ArrayList<Long>();
		Set<Long> userIds = new HashSet<Long>();
		Map<Long, CmcIrImage> imageMap = new HashMap<Long, CmcIrImage>();
		for (CmcIrImage image : images) {
			imageIds.add(image.getImageid());
			userIds.add(image.getUserid());
			imageMap.put(image.getImageid(), image);
		}
		
		//获取识别结果
		List<CmcIrRecognition> recognitions = imageRecognitionDao.findRecognition(imageIds);
		for (CmcIrRecognition recognition : recognitions) {
			imageMap.get(recognition.getImageid()).getCmcIrRecognitions().add(recognition);
		}
		
		//获取用户
		Map<Long, UserSimpleBean> userMap = UserMap.findUser(userIds);
		for (CmcIrImage image : images) {
			image.setUser(userMap.get(image.getUserid()));
		}
	}
	
	/**
	 * 分页获取用户图像列表
	 * @param userId
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<ImageRecognitionImageBean> findImageInfo(Long userId, Integer pageSize, Long lastId){
		List<ImageRecognitionImageBean> result = new ArrayList<ImageRecognitionImageBean>();

		//获取图像列表
		List<CmcIrImage> images = imageRecognitionDao.findImage(userId, pageSize, lastId);
		if(images.isEmpty()) return result;
		
		//获取识别结果
		List<Long> imageIds = new ArrayList<Long>();
		Map<Long, CmcIrImage> imageMap = new HashMap<Long, CmcIrImage>();
		for (CmcIrImage image : images) {
			imageIds.add(image.getImageid());
			imageMap.put(image.getImageid(), image);
		}
		List<CmcIrRecognition> recognitions = imageRecognitionDao.findRecognition(imageIds);
		for (CmcIrRecognition recognition : recognitions) {
			imageMap.get(recognition.getImageid()).getCmcIrRecognitions().add(recognition);
		}
		
		//组装
		for (CmcIrImage image : images) {
			result.add(BeanConvertUtil.toImageRecognitionImageBean(image, imageRecognitionCatMap.getCatName(image.getCatid())));
		}
		
		return result;
	}
	
	/**
	 * 保存图像以及识别结果
	 * @param bean
	 */
	public void saveImage(CmcIrImage bean){
		imageRecognitionDao.save(bean);
		
		for (CmcIrRecognition ir : bean.getCmcIrRecognitions()) {
			ir.setImageid(bean.getImageid());
		}
		imageRecognitionDao.saveOrUpdateAll(bean.getCmcIrRecognitions());
	}
}
