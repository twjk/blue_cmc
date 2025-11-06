package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcJobArticlePic;
import com.qcmz.framework.dao.IBaseDAO;

public interface IJobArticlePicDao extends IBaseDAO {
	/**
	 * 清除资讯图片
	 * @param articleId
	 */
	public void clearPic(Long articleId);
	/**
	 * 获取图片列表
	 * @param articleIds
	 * @return
	 */
	public List<CmcJobArticlePic> findPic(List<Long> articleIds);
	/**
	 * 分页获取需要下载的图片列表
	 * @param pageSize
	 * @param lastPicId
	 * @return
	 */
	public List<CmcJobArticlePic> findPic4Download(int pageSize, Long lastPicId);
}
