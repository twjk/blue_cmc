package com.qcmz.cmc.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.dao.IDialogMsgDao;
import com.qcmz.cmc.entity.CmcDialogMsg;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.NumberUtil;

@Repository
public class DialogMsgDao extends BaseDAO implements IDialogMsgDao {
	/**
	 * 分页获取对话消息
	 * @param map
	 * @param pageBean<CmcDialogMsg>
	 * @return
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean) {
		StringBuilder sbHql = new StringBuilder("from CmcDialogMsg t left join fetch t.cmcDialog");
		StringBuilder sbCountHql = new StringBuilder("select count(t.msgid) from CmcDialogMsg t left join t.cmcDialog");
		StringBuilder sbCond = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(map!=null && !map.isEmpty()){
			//用户编号
			String userId = map.get("userid");
			if(NumberUtil.isNumber(userId)){
				sbCond.append(" and t.userid=:userid");
				params.put("userid", Long.valueOf(userId));
			}
			//消息方
			String msgside = map.get("msgside");
			if(NumberUtil.isNumber(msgside)){
				sbCond.append(" and t.msgside=:msgside");
				params.put("msgside", Integer.valueOf(msgside));
			}
			//处理状态
			String dealstatus = map.get("dealstatus");
			if(NumberUtil.isNumber(dealstatus)){
				sbCond.append(" and t.cmcDialog.dealstatus=:dealstatus");
				params.put("dealstatus", Integer.valueOf(dealstatus));
			}
			
			if(sbCond.length()>0){
				sbCond.replace(0, 4, " where");
				sbHql.append(sbCond);
				sbCountHql.append(sbCond);
			}
		}

		sbHql.append(" order by t.msgid desc ");
		
		getQueryPageBean(sbHql.toString(), sbCountHql.toString(), params, pageBean);
	}
	
	/**
	 * 获取对话消息列表
	 * @param dialogId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcDialogMsg> findMsg(Long dialogId){
		String hql = "from CmcDialogMsg t where t.dialogid=:dialogid order by msgid desc";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dialogid", dialogId);
		
		return (List<CmcDialogMsg>) qryListByMap(hql, params);
	}
	
	/**
	 * 分页获取对话消息列表
	 * @param userId
	 * @param pageSize
	 * @param sort
	 * @param lastMsgId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CmcDialogMsg> findMsg(Long userId, int pageSize, String sort, Long lastMsgId){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sbHql.append("from CmcDialogMsg t where t.userid=:userid");
		params.put("userid", userId);
		
		if(lastMsgId!=null){
			if(SystemConstants.SORT_DESC.equals(sort)){
				sbHql.append(" and t.msgid<:msgid");
			}else{
				sbHql.append(" and t.msgid>:msgid");
			}
			params.put("msgid", lastMsgId);
		}
		
		sbHql.append(" order by t.msgid ").append(sort);
		
		return (List<CmcDialogMsg>) qryListTop(sbHql.toString(), params, pageSize);
		
	}
}
