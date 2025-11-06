package com.qcmz.cmc.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IProductDao;
import com.qcmz.cmc.entity.CmcProduct;
import com.qcmz.cmc.entity.CmcProductItem;
import com.qcmz.framework.dao.impl.BaseDAO;

@Repository
public class ProductDao extends BaseDAO implements IProductDao {
	/**
	 * 获取有效产品列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcProduct> findProductValid(){
		return (List<CmcProduct>) qryList("from CmcProduct t where t.status=1");
	}
	
	/**
	 * 获取有效商品列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcProductItem> findItemValid(){
		return (List<CmcProductItem>) qryList("from CmcProductItem where status=1 order by sortindex, itemid");
	}
}
