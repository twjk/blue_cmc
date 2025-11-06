package com.qcmz.cmc.dao;

import java.util.List;
import java.util.Map;

import com.qcmz.cmc.entity.CmcFeeProduct;
import com.qcmz.cmc.ws.provide.vo.ProductBean;
import com.qcmz.framework.dao.IBaseDAO;
import com.qcmz.framework.page.PageBean;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
public interface IProductDao extends IBaseDAO {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean);
	/**
	 * 获取在售的产品信息
	 * @return
	 * 修改历史：
	 */
	public List<ProductBean> findSellProduct();
	/**
	 * 根据产品代码获取产品信息
	 * @param productCode
	 * @return
	 * 修改历史：
	 */
	public CmcFeeProduct getProductByCode(String productCode);
	/**
	 * 查重
	 * @param productId
	 * @param productCode
	 * @param productName
	 * @return
	 * 修改历史：
	 */
	public boolean exist(Long productId, String productCode, String productName);
	/**
	 * 更新状态
	 * @param productId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(Long productId, Integer status);
}