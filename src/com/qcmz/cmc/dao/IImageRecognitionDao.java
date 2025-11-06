package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcIrImage;
import com.qcmz.cmc.entity.CmcIrRecognition;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

public interface IImageRecognitionDao extends IBaseDAO {
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
	public List<CmcIrImage> findImage(Long userId, int pageSize, Long lastId);
	/**
	 * 获取图像识别结果列表
	 * @param imageIds
	 * @return
	 */
	public List<CmcIrRecognition> findRecognition(List<Long> imageIds);
}
