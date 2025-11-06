package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IDialogDao;
import com.qcmz.cmc.entity.CmcDialog;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;

@Repository
public class DialogDao extends BaseDAO implements IDialogDao {
	/**
	 * 分页获取对话
	 * @param map
	 * @param pageBean<CmcDialog>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean) {
		StringBuilder sbHql = new StringBuilder("from CmcDialog t ");
		StringBuilder sbCountHql = new StringBuilder("select count(t.dialogid) from CmcDialog t");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			//用户编号
			String userId = map.get("userid");
			if(NumberUtil.isNumber(userId)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userId));
			}
			
			//处理状态
			String dealstatus = map.get("dealstatus");
			if(NumberUtil.isNumber(dealstatus)){
				sbCond.append(" and t.dealstatus=:dealstatus");
				params.put("dealstatus", Integer.valueOf(dealstatus));
			}
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}

		sbHql.append(" order by t.msgtime desc, t.dialogid desc ");
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取用户对话
	 * @param userId
	 * @return
	 */
	public CmcDialog getDialog(Long userId){
		String hql = "from CmcDialog t where t.userid=:userid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userId);
		
		return (CmcDialog) load(hql, params);
	}
	
	/**
	 * 更新处理状态
	 * @param dialogId
	 * @param dealStatus
	 */
	public void updateDealStatus(Long dialogId, Integer dealStatus){
		String hql = "update CmcDialog t set dealstatus=:dealstatus where t.dialogid=:dialogid";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dialogid", dialogId);
		params.put("dealstatus", dealStatus);
		
		updateBatch(hql, params);
	}
}
