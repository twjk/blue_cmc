package com.qcmz.cmc.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.constant.BusinessConstant;
import com.qcmz.cmc.entity.CmcProduct;
import com.qcmz.cmc.entity.CmcProductItem;
import com.qcmz.cmc.service.IProductService;
import com.qcmz.cmc.ws.provide.vo.ProductBean;
import com.qcmz.cmc.ws.provide.vo.ProductItemBean;
import com.qcmz.framework.cache.AbstractCacheMap;

@Component
public class ProductMap extends AbstractCacheMap {
	@Autowired
	private IProductService productService;
	
	private Map<Long, ProductBean> productMap;
	private Map<Long, ProductItemBean> itemMap;
	
	@Override @PostConstruct
	protected void init() {
		Map<Long, ProductBean> productMapTemp = new HashMap<Long, ProductBean>();
		Map<Long,  List<ProductItemBean>> productItemsMapTemp = new HashMap<Long, List<ProductItemBean>>();
		Map<Long, ProductItemBean> itemMapTemp = new HashMap<Long, ProductItemBean>();
		
		List<ProductItemBean> itemBeanList = null;
		ProductBean productBean = null;
		ProductItemBean itemBean = null;
		
		List<CmcProductItem> items = productService.findItemValid();
		for (CmcProductItem item : items) {
			itemBeanList = productItemsMapTemp.get(item.getProductid());
			if(itemBeanList==null) itemBeanList = new ArrayList<ProductItemBean>();
			
			itemBean = new ProductItemBean();
			itemBean.setItemId(item.getItemid());
			itemBean.setItemName(item.getItemname());
			itemBean.setIapId(item.getIapid());
			itemBean.setOriPrice(item.getOriprice());
			itemBean.setPrice(item.getPrice());
			itemBean.setPriceType(item.getPricetype());
			itemBean.setMinNum(item.getMinnum());
			itemBean.setMaxNum(item.getMaxnum());			
			itemBean.setPic(item.getPic());
			itemBean.setSlogan(item.getSlogan());
			itemBean.setOverview(item.getOverview());
			
			itemBeanList.add(itemBean);
			itemMapTemp.put(itemBean.getItemId(), itemBean);
			
			productItemsMapTemp.put(item.getProductid(), itemBeanList);
		}
		
		List<CmcProduct> products = productService.findProductValid();
		for (CmcProduct product : products) {
			productBean = new ProductBean();
			productBean.setTitle(product.getTitle());
			productBean.setContent(product.getContent());
			
			itemBeanList = productItemsMapTemp.get(product.getProductid());
			if(itemBeanList!=null){
				productBean.getItems().addAll(itemBeanList);
			}
			
			productMapTemp.put(product.getProductid(), productBean);
		}
		
		productMap = productMapTemp;
		itemMap = itemMapTemp;
	}
	
	/**
	 * 获取产品信息
	 * @param productId
	 * @return
	 */
	public ProductBean getProductInfo(Long productId){
		if(safeInit(productMap)){
			return productMap.get(productId);
		}
		return null;
	}
	public ProductBean getProductInfo4Dub(){
		return getProductInfo(BusinessConstant.PRODUCTID_DUB);
	}

	/**
	 * 获取商品信息
	 * @param itemId
	 * @return
	 */
	public ProductItemBean getItemInfo(Long itemId){
		if(safeInit(itemMap)){
			return itemMap.get(itemId);
		}
		return null;
	}
	
	
	@Override
	public void update(Object obj) {}

	@Override
	public void delete(Object obj) {}

}
