package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.DictMap;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IProductDao;
import com.qcmz.cmc.dao.ITradeDao;
import com.qcmz.cmc.entity.CmcFeeProduct;
import com.qcmz.cmc.service.IProductService;
import com.qcmz.cmc.ws.provide.vo.ProductBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class ProductServiceImpl implements IProductService {
	@Autowired
	private IProductDao productDao;
	@Autowired
	private ITradeDao tradeDao;
	@Autowired
	private DictMap dictMap;
	
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		productDao.queryByMapTerm(map, pageBean);
	}
	
	/** 
	 * 获取在售的产品列表
	 * @return
	 * 修改历史：
	 */
	@Override
	public List<ProductBean> findSellProduct(Long userId) {
		List<ProductBean> list = productDao.findSellProduct();
		List<Long> rechargeProductIds = new ArrayList<Long>();

		//去掉已经购买的非消耗品
		if(!list.isEmpty() && userId!=null && userId>0){
			rechargeProductIds = tradeDao.findUnconsumableProduct(userId);
			if(!rechargeProductIds.isEmpty()){
				Iterator<ProductBean> it = list.iterator();
				ProductBean bean = null;
				while(it.hasNext()){
					bean = it.next();
					if(bean.getPrice()>0 && rechargeProductIds.contains(bean.getProductId())){
						it.remove();
					}
				}
			}
		}
		
		//设置交易状态和描述
		for (ProductBean bean : list) {
			if(bean.getPrice()>0){
				bean.setTradeStatus(DictConstant.DICT_TRADESTATUS_RECHARGE);
			}else{
				if(rechargeProductIds.contains(bean.getProductId())){
					bean.setTradeStatus(DictConstant.DICT_TRADESTATUS_RECEIVED);
				}else{
					bean.setTradeStatus(DictConstant.DICT_TRADESTATUS_RECEIVE);
				}
			}
			bean.setTradeStatusDesc(dictMap.getTradeStatusMean(bean.getTradeStatus()));
		}
		return list;
	}

	/**
	 * 获取产品信息
	 * @param productId
	 * @return
	 * 修改历史：
	 */
	public CmcFeeProduct loadProduct(Long productId){
		return (CmcFeeProduct) productDao.load(CmcFeeProduct.class, productId);
	}
	
	/**
	 * 获取有效的产品信息
	 * @param productId
	 * @return
	 * 修改历史：
	 */
	public CmcFeeProduct loadValidProduct(Long productId, String productCode){
		CmcFeeProduct entity = null;
		if(productId!=null){
			entity = loadProduct(productId);
		}else if(StringUtil.notBlankAndNull(productCode)){
			entity = productDao.getProductByCode(productCode);
		}
		
		if(entity!=null 
				&& SystemConstants.STATUS_ON.equals(entity.getStatus())){
			return entity;
		}
		return null;
	}
	
	/**
	 * 保存
	 * @param bean
	 * @param oper
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public CmcFeeProduct saveOrUpdate(CmcFeeProduct bean, Operator oper){
		//信息查重
		boolean exist = productDao.exist(bean.getProductid(), bean.getProductcode(), bean.getProductname());
		if(exist){
			throw new DataException("信息重复");
		}
		
		if(bean.getProductid()==null){
			//添加
			productDao.save(bean);
			OperLogUtil.saveAddLog(bean.getProductid(), oper, bean);
			return bean;
		}else{
			//修改
			CmcFeeProduct entity = loadProduct(bean.getProductid());
			Map<String, String> oldDescMap = OperLogUtil.getBeanDescMap(entity);
			
			entity.setProductname(bean.getProductname());
			entity.setProductcode(bean.getProductcode());
			entity.setProducttype(bean.getProducttype());
			entity.setPoint(bean.getPoint());
			entity.setPrice(bean.getPrice());
			entity.setDescription(bean.getDescription());
			entity.setSortindex(bean.getSortindex());
			entity.setProductrange(bean.getProductrange());
			entity.setStatus(bean.getStatus());
			productDao.update(entity);
			
			OperLogUtil.saveUpdateLog(entity.getProductid(), oper, oldDescMap, entity);
			return entity;
		}
	}
	
	/**
	 * 更新状态
	 * @param productId
	 * @param status
	 * @param oper
	 * 修改历史：
	 */
	public void updateStatus(Long productId, Integer status, Operator oper){
		productDao.updateStatus(productId, status);
		
		StringBuilder sbLog = new StringBuilder("状态[").append(status).append("]");
		OperLogUtil.saveOperLog(CmcFeeProduct.class, productId, oper, sbLog.toString());
	}
	
	/**
	 * 删除
	 * @param productId
	 * @param oper
	 * 修改历史：
	 */
	public void delete(Long productId, Operator oper){
		CmcFeeProduct entity = loadProduct(productId);
		
		productDao.delete(entity);
		
		OperLogUtil.saveDelLog(productId, oper, entity);
	}
}
