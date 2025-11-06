package com.qcmz.cmc.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.ITransCorrectDao;
import com.qcmz.cmc.entity.CmcTransCorrect;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;
import com.qcmz.framework.util.StringUtil;

@Repository
public class TransCorrectDao extends BaseDAO implements ITransCorrectDao {

	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcTransCorrect>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		StringBuilder sbHql = new StringBuilder("from CmcTransCorrect t");
		StringBuilder sbCountHql = new StringBuilder("select count(t.correctid) from CmcTransCorrect t");
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			//查询条件
			StringBuilder sbCond = new StringBuilder();
			
			//源语言
			String fromlang = map.get("fromlang");
			if(StringUtil.notBlankAndNull(fromlang)){
				sbCond.append(" and(t.fromlang=:fromlang)");
				params.put("fromlang", fromlang);
			}
			
			//目标语言
			String tolang = map.get("tolang");
			if(StringUtil.notBlankAndNull(tolang)){
				sbCond.append(" and(t.tolang=:tolang)");
				params.put("tolang", tolang);
			}
			
			//原文
			String src = map.get("src");
			if(StringUtil.notBlankAndNull(src)){
				sbCond.append(" and t.src like :src");
				params.put("src", "%"+src+"%");
			}
			
			//译文
			String dst = map.get("dst");
			if(StringUtil.notBlankAndNull(dst)){
				sbCond.append(" and t.dst like :dst");
				params.put("dst", "%"+dst+"%");
			}

			//用户编号
			String userid = map.get("userid");
			if(NumberUtil.isNumber(userid)){
				sbCond.append(" and t.userid = :userid");
				params.put("userid", Long.valueOf(userid));
			}
			
			//pushregid
			String pushregid = map.get("pushregid");
			if(StringUtil.notBlankAndNull(pushregid)){
				sbCond.append(" and t.pushregid = :pushregid");
				params.put("pushregid", pushregid);
			}
			
			//修正来源
			String correctsource = map.get("correctsource");
			if(NumberUtil.isNumber(correctsource)){
				sbCond.append(" and t.correctsource = :correctsource");
				params.put("correctsource", Integer.valueOf(correctsource));
			}
			
			if(sbCond.length()>4){
				sbCond.replace(1, 4, "where");
				
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
			
			sbHql.append(" order by t.correctid desc");
			
		}
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	/**
	 * 获取修正翻译列表
	 * @param uuid
	 * @param minTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcTransCorrect> findCorrectTrans(String uuid, Date minTime){
		String hql = "from CmcTransHuman where uuid=? and transtime>=?";
		return (List<CmcTransCorrect>) qryList(hql, new Object[]{uuid, minTime});
	}

}
