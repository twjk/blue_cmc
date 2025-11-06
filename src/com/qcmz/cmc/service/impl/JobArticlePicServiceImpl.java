package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IJobArticlePicDao;
import com.qcmz.cmc.entity.CmcJobArticlePic;
import com.qcmz.cmc.service.IJobArticlePicService;

@Service
public class JobArticlePicServiceImpl implements IJobArticlePicService {
	@Autowired
	private IJobArticlePicDao jobArticlePicDao;
	
	/**
	 * 获取图片列表
	 * @param articleIds
	 * @return
	 */
	public List<CmcJobArticlePic> findPic(List<Long> articleIds){
		return jobArticlePicDao.findPic(articleIds);
	}
	
	/**
	 * 获取图片列表
	 * @param articleId
	 * @return
	 */
	public List<CmcJobArticlePic> findPic(Long articleId){
		List<Long> articleIds = new ArrayList<Long>();
		articleIds.add(articleId);
		return findPic(articleIds);
	}
	
	/**
	 * 分页获取需要下载的图片列表
	 * @param pageSize
	 * @param lastPicId
	 * @return
	 */
	public List<CmcJobArticlePic> findPic4Download(int pageSize, Long lastPicId){
		return jobArticlePicDao.findPic4Download(pageSize, lastPicId);
	}
	
	/**
	 * 保存图片
	 * @param articleId
	 * @param pics
	 * @param update
	 */
	public void savePic(Long articleId, List<CmcJobArticlePic> pics, boolean update){
		//清除已有图片
		if(update){
			jobArticlePicDao.clearPic(articleId);
		}
		
		//重新入库
		if(pics==null || pics.isEmpty()) return;
		
		for (CmcJobArticlePic pic : pics) {
			pic.setArtid(articleId);
		}
		jobArticlePicDao.saveOrUpdateAll(pics);
	}
	
	/**
	 * 更新图片
	 * @param pic
	 */
	public void updatePic(CmcJobArticlePic pic){
		jobArticlePicDao.update(pic);
	}
	
	/**
	 * 清除图片
	 * @param articleId
	 */
	public void clearPic(Long articleId){
		jobArticlePicDao.clearPic(articleId);
	}
}
