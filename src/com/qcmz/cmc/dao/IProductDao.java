package com.qcmz.cmc.dao;

import java.util.List;

import com.qcmz.cmc.entity.CmcProduct;
import com.qcmz.cmc.entity.CmcProductItem;
import com.qcmz.framework.dao.IBaseDAO;

public interface IProductDao extends IBaseDAO {
	/**
	 * 获取有效产品列表
	 * @return
	 */
	public List<CmcProduct> findProductValid();
	/**
	 * 获取有效商品列表
	 * @return
	 */
	public List<CmcProductItem> findItemValid();
}
