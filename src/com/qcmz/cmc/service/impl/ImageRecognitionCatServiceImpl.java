package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IImageRecognitionCatDao;
import com.qcmz.cmc.entity.CmcIrCat;
import com.qcmz.cmc.service.IImageRecognitionCatService;

@Service
public class ImageRecognitionCatServiceImpl implements IImageRecognitionCatService {
	@Autowired
	private IImageRecognitionCatDao imageRecognitionCatDao;
	
	/**
	 * 获取分类列表
	 * @return
	 */
	public List<CmcIrCat> findCat(){
		return imageRecognitionCatDao.findCat();
	}
}
