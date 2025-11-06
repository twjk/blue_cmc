package com.qcmz.cmc.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IImageRecognitionDao;
import com.qcmz.cmc.entity.CmcIrImage;
import com.qcmz.cmc.entity.CmcIrRecognition;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;

@Repository
public class ImageRecognitionDao extends BaseDAO implements IImageRecognitionDao {
	/**
	 * 分页查询数据
	 * @param map
	 * @param pageBean<CmcIrImage>
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcIrImage t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.imageid) from CmcIrImage t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		//根据Map参数添加查询条件
		if(map!=null && !map.isEmpty()){
			StringBuilder sbCond = new StringBuilder();
			String param = null;
			param = map.get("catid");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.catid=:catid");
				params.put("catid", Long.valueOf(param));	
			}
			param = map.get("userid");
			if(NumberUtil.isNumber(param)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(param));	
			}
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}
		
		sbHql.append(" order by t.imageid desc");
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 分页获取用户图像列表
	 * @param userId
	 * @param pageSize
	 * @param lastId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcIrImage> findImage(Long userId, int pageSize, Long lastId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcIrImage t where t.userid=:userid");
		params.put("userid", userId);
		
		if(lastId!=null){
			sbHql.append(" and t.imageid<:imageid");
			params.put("imageid", lastId);
		}
		
		sbHql.append(" order by t.imageid desc");
		
		return (List<CmcIrImage>) qryListTop(sbHql.toString(), params, pageSize); 
	}
	
	/**
	 * 获取图像识别结果列表
	 * @param imageIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcIrRecognition> findRecognition(List<Long> imageIds){
		if(imageIds==null || imageIds.isEmpty()) return new ArrayList<CmcIrRecognition>();
		
		String hql = null;
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(imageIds.size()==1){
			hql = "from CmcIrRecognition t where t.imageid=:imageid order by t.recogid";
			params.put("imageid", imageIds.get(0));
		}else{
			hql = "from CmcIrRecognition t where t.imageid in(:imageids)";
			params.put("imageids", imageIds);
		}
		
		return (List<CmcIrRecognition>) qryListByMap(hql, params);
	}
	
}
