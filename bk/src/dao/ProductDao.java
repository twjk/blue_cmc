package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IProductDao;
import com.qcmz.cmc.entity.CmcFeeProduct;
import com.qcmz.cmc.ws.provide.vo.ProductBean;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Repository
public class ProductDao extends BaseDAO implements IProductDao {
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcFeeProduct t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.productid) from CmcFeeProduct t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			//查询条件
			
			//产品名称
			String productname = map.get("productname");
			if(StringUtil.notBlankAndNull(productname)){
				sbCond.append(" and t.productname=:productname");
				params.put("productname", productname);
			}
			
			//产品代码
			String productcode = map.get("productcode");
			if(StringUtil.notBlankAndNull(productcode)){
				sbCond.append(" and t.productcode=:productcode");
				params.put("productcode", productcode);
			}
			
			//销售状态
			String sellstatus = map.get("sellstatus");
			if(NumberUtil.isNumber(sellstatus)){
				sbCond.append(" and t.sellstatus=:sellstatus");
				params.put("sellstatus", Integer.valueOf(sellstatus));
			}
			
			//状态
			String status = map.get("status");
			if(NumberUtil.isNumber(status)){
				sbCond.append(" and t.status=:status");
				params.put("status", Integer.valueOf(status));
			}
			
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
			
			sbHql.append(" order by t.productid");
		}
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取在售的产品信息
	 * @return
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<ProductBean> findSellProduct() {
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select new com.qcmz.cmc.ws.provide.vo.ProductBean(")
			 .append("productid, productname, productcode, price, description")
			 .append(") from CmcFeeProduct where status=1 and productrange=? order by sortindex, price")
		;
		return (List<ProductBean>) qryList(sbHql.toString(), DictConstant.DICT_PRODUCTRANGE_PUBLIC);
	}

	/**
	 * 根据产品代码获取产品信息
	 * @param productCode
	 * @return
	 * 修改历史：
	 */
	public CmcFeeProduct getProductByCode(String productCode){
		return (CmcFeeProduct) load("from CmcFeeProduct where productcode=?", productCode);
	}
	
	/**
	 * 查重
	 * @param productId
	 * @param productCode
	 * @param productName
	 * @return
	 * 修改历史：
	 */
	public boolean exist(Long productId, String productCode, String productName){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("select count(t.productid) from CmcFeeProduct t")
			 .append(" where (t.productname=:productname or t.productcode=:productcode)")
			;
		params.put("productcode", productCode);
		params.put("productname", productName);
		
		if(productId!=null){
			sbHql.append(" and t.productid!=:productid");
			params.put("productid", productId);
		}
		
		Long count = (Long) load(sbHql.toString(), params);
		return count>0;
	}
	
	/**
	 * 更新状态
	 * @param productId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(Long productId, Integer status){
		update("update CmcFeeProduct set status=? where productid=?", new Object[]{status, productId});
	}
}
