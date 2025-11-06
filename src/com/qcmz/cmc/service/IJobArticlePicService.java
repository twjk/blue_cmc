package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcJobArticlePic;

public interface IJobArticlePicService {
	/**
	 * 获取图片列表
	 * @param articleIds
	 * @return
	 */
	public List<CmcJobArticlePic> findPic(List<Long> articleIds);
	/**
	 * 获取图片列表
	 * @param articleId
	 * @return
	 */
	public List<CmcJobArticlePic> findPic(Long articleId);
	/**
	 * 分页获取需要下载的图片列表
	 * @param pageSize
	 * @param lastPicId
	 * @return
	 */
	public List<CmcJobArticlePic> findPic4Download(int pageSize, Long lastPicId);
	/**
	 * 保存图片
	 * @param articleId
	 * @param pics
	 * @param update
	 */
	public void savePic(Long articleId, List<CmcJobArticlePic> pics, boolean update);
	/**
	 * 更新图片
	 * @param pic
	 */
	public void updatePic(CmcJobArticlePic pic);
	/**
	 * 清除图片
	 * @param articleId
	 */
	public void clearPic(Long articleId);
}
