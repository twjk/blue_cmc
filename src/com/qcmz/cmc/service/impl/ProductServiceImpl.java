package com.qcmz.cmc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.IProductDao;
import com.qcmz.cmc.entity.CmcProduct;
import com.qcmz.cmc.entity.CmcProductItem;
import com.qcmz.cmc.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	private IProductDao productDao;
	
	/**
	 * 获取有效产品列表
	 * @return
	 */
	public List<CmcProduct> findProductValid(){
		return productDao.findProductValid();
	}
	
	/**
	 * 获取有效商品列表
	 * @return
	 */
	public List<CmcProductItem> findItemValid(){
		return productDao.findItemValid();
	}
	
}
