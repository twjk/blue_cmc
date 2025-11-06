package com.qcmz.cmc.service;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcFeeProduct;
import com.qcmz.cmc.ws.provide.vo.ProductBean;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IProductService {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * @author 李炳煜
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取在售的产品列表
	 * @return
	 * 修改历史：
	 */
	public List<ProductBean> findSellProduct(Long userId);
	/**
	 * 获取产品信息
	 * @param productId
	 * @return
	 * 修改历史：
	 */
	public CmcFeeProduct loadProduct(Long productId);
	/**
	 * 获取有效的产品信息
	 * @param productId
	 * @return
	 * 修改历史：
	 */
	public CmcFeeProduct loadValidProduct(Long productId, String productCode);
	/**
	 * 保存
	 * @param bean
	 * @param oper
	 * @return
	 * @author 李炳煜
	 * 修改历史：
	 */
	public CmcFeeProduct saveOrUpdate(CmcFeeProduct bean, Operator oper);
	/**
	 * 更新状态
	 * @param productId
	 * @param status
	 * @param oper
	 * 修改历史：
	 */
	public void updateStatus(Long productId, Integer status, Operator oper);
	/**
	 * 删除
	 * @param productId
	 * @param oper
	 * 修改历史：
	 */
	public void delete(Long productId, Operator oper);
}
