package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IJobArticlePicDao;
import com.qcmz.cmc.entity.CmcJobArticlePic;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.util.FileServiceUtil;

@Repository
public class JobArticlePicDao extends BaseDAO implements IJobArticlePicDao {
	
	/**
	 * 清除就业信息图片
	 * @param articleId
	 */
	public void clearPic(Long articleId){
		String hql = "delete from CmcJobArticlePic where artid=:artid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("artid", articleId);
		
		deleteByMap(hql, params);
	}
	
	/**
	 * 获取图片列表
	 * @param articleIds
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcJobArticlePic> findPic(List<Long> articleIds){
		StringBuilder sbHql = new StringBuilder("from CmcJobArticlePic where");
		Map<String, Object> params = new HashMap<String, Object>();

		if(articleIds.size()==1){
			sbHql.append(" artid=:artid");
			params.put("artid", articleIds.get(0));
		}else{
			sbHql.append(" artid in(:artids)");
			params.put("artids", articleIds);
		}
		sbHql.append(" order by sortindex, picid");
		return (List<CmcJobArticlePic>) qryListByMap(sbHql.toString(), params);
	}
	
	/**
	 * 分页获取需要下载的图片列表
	 * @param pageSize
	 * @param lastPicId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcJobArticlePic> findPic4Download(int pageSize, Long lastPicId){
		StringBuilder sbHql = new StringBuilder("from CmcJobArticlePic where picurl not like :picurl");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("picurl", FileServiceUtil.SERVERURL+"%");
		
		if(lastPicId!=null){
			sbHql.append(" and picid>:picid");
			params.put("picid", lastPicId);
		}
		
		return (List<CmcJobArticlePic>) qryListTop(sbHql.toString(), params, pageSize);
	}
}
