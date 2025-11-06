package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcLtPic;

public interface ILocalTaskPicService {
	/**
	 * 保存任务图片
	 * @param taskId
	 * @param pics
	 * @param update
	 */
	public void savePic(Long taskId, List<CmcLtPic> pics, boolean update);
	/**
	 * 清除任务图片
	 * @param taskId
	 */
	public void clearPic(Long taskId);
}
