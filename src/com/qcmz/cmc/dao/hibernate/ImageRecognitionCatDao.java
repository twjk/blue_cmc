package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IImageRecognitionCatDao;
import com.qcmz.cmc.entity.CmcIrCat;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class ImageRecognitionCatDao extends BaseDAO implements IImageRecognitionCatDao {
	/**
	 * 获取分类列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcIrCat> findCat(){
		return (List<CmcIrCat>) qryList("from CmcIrCat order by sortindex");
	}
}
