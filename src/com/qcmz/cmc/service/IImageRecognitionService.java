package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcIrImage;
import com.qcmz.cmc.ws.provide.vo.ImageRecognitionImageBean;
import com.qcmz.framework.page.PageBean;

public interface IImageRecognitionService {
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcIrImage>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 分页获取用户图像列表
	 * @param userId
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	public List<ImageRecognitionImageBean> findImageInfo(Long userId, Integer pageSize, Long lastId);
	/**
	 * 保存图像以及识别结果
	 * @param bean
	 */
	public void saveImage(CmcIrImage bean);
}
