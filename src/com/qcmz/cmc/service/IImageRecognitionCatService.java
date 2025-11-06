package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcIrCat;

public interface IImageRecognitionCatService {
	/**
	 * 获取分类列表
	 * @return
	 */
	public List<CmcIrCat> findCat();
}
