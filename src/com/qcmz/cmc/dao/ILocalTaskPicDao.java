package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcLtPic;
import com.qcmz.framework.dao.IBaseDAO;

public interface ILocalTaskPicDao extends IBaseDAO {
	/**
	 * 获取任务图片列表
	 * @param taskId
	 * @return
	 */
	public List<CmcLtPic> findPic(Long taskId);
	/**
	 * 清除任务图片
	 * @param taskId
	 */
	public void clearPic(Long taskId);
}
