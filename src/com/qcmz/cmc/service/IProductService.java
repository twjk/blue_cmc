package com.qcmz.cmc.service;

import java.util.List;

import com.qcmz.cmc.entity.CmcProduct;
import com.qcmz.cmc.entity.CmcProductItem;

public interface IProductService {
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
