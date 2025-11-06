package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcIrCat;
import com.qcmz.framework.dao.IBaseDAO;

public interface IImageRecognitionCatDao extends IBaseDAO {
	/**
	 * 获取分类列表
	 * @return
	 */
	public List<CmcIrCat> findCat();
}
