package com.qcmz.cmc.dao.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.IApiKeyDao;
import com.qcmz.cmc.entity.CmcApikey;
import com.qcmz.cmc.vo.ApiKeyBean;
import com.qcmz.cmc.vo.ApiKeyCallBean;
import com.qcmz.cmc.vo.ApiKeyTypeCallBean;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.dao.impl.BaseDAO;
import com.qcmz.framework.util.DateUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * @version 1.0
 * 创建日期：Jun 4, 2014 3:07:47 PM
 * 修改历史：
 */
@Repository
public class ApiKeyDao extends BaseDAO implements IApiKeyDao {
	/**
	 * 获取所有有效的key
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 4, 2014 3:03:35 PM
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<ApiKeyBean> findValid(){
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select new com.qcmz.cmc.vo.ApiKeyBean(keyid, apitype, apikey, status)")
			 .append(" from CmcApikey t ")
			 .append(" where t.status=1")
			 .append(" order by t.apitype, t.keyid")
		;
		return (List<ApiKeyBean>) qryList(sbHql.toString());
	}
	
	/**
	 * 获取所有key
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 4, 2014 3:03:35 PM
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public List<ApiKeyBean> findAll(){
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select new com.qcmz.cmc.vo.ApiKeyBean(keyid, apitype, apikey, status)")
			 .append(" from CmcApikey t ")
			 .append(" order by t.apitype, t.keyid")
		;
		return (List<ApiKeyBean>) qryList(sbHql.toString());
	}
	
	/**
	 * 获取配置当天访问量,keyId为key,当天访问量为value
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 10, 2014 11:01:55 AM
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public Map<Long, Long> findTodayCallCount(){
		StringBuilder sbHql = new StringBuilder();
		sbHql.append("select new com.qcmz.cmc.vo.ApiKeyCallBean(t.keyid, count(t.logid))")
			 .append(" from CmcApikeyCalllog t ")
			 .append(" where t.calltime>?")
			 .append(" group by t.keyid")
		;
		
		List<ApiKeyCallBean> timesList = (List<ApiKeyCallBean>) qryList(sbHql.toString(),DateUtil.truncDate(new Date()));
		Map<Long, Long> timesMap = new HashMap<Long, Long>();
		if(timesList!=null && !timesList.isEmpty()){
			for (ApiKeyCallBean bean : timesList) {
				timesMap.put(bean.getKeyid(), bean.getCallTimes());
			}
		}
		return timesMap;
	}
	
	/**
	 * type每天访问量
	 * @return
	 * @author 李炳煜
	 * @version 1.0
	 * 创建日期：Jun 10, 2014 2:19:53 PM
	 * 修改历史：
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Map<String, Long>> findTypeCallCount(Date beginDate, Date endDate){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		sbHql.append("select new com.qcmz.cmc.vo.ApiKeyTypeCallBean(k.apitype, date_format(t.calltime,'%Y-%m-%d'), count(t.logid))")
			 .append(" from CmcApikeyCalllog t, CmcApikey k")
			 .append(" where t.keyid=k.keyid and t.calltime between :beginDate and :endDate")
			 .append(" group by k.apitype,date_format(t.calltime,'%Y-%m-%d')")
			 ;
		params.put("beginDate", beginDate);
		Date ee = DateUtil.addDay(endDate, 1);
		params.put("endDate", ee);
		
		List<ApiKeyTypeCallBean> list = (List<ApiKeyTypeCallBean>) qryListByMap(sbHql.toString(), params);
		Map<String, Map<String, Long>> map = new HashMap<String, Map<String,Long>>();
		for (ApiKeyTypeCallBean bean : list) {
			if(map.get(bean.getKeyType())==null){
				map.put(bean.getKeyType(), new HashMap<String, Long>());
			}
			map.get(bean.getKeyType()).put(bean.getCallDate(), bean.getCallTimes());
		}
		return map;
	}
	
	/**
	 * 获取有效key总数
	 * @param apiType
	 * @return
	 * 修改历史：
	 */
	public Long getValidCount(String apiType){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("apitype", apiType);
		
		return (Long) load("select count(keyid) from CmcApikey where status=1 and apitype=:apitype", params);
	}
	
	/**
	 * 获取key信息
	 * @param apiType
	 * @param apiKey
	 * @return
	 * 修改历史：
	 */
	public CmcApikey loadKey(String apiType, String apiKey){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("apitype", apiType);
		params.put("apikey", apiKey);
		
		return (CmcApikey) load("from CmcApikey where apitype=:apitype and apikey=:apikey", params);
	}

	/**
	 * 停用必应key
	 * @param apiKey
	 * 修改历史：
	 */
	public void stopBing(String apiKey){
		CmcApikey key = loadKey(DictConstant.DICT_APIKEYTYPE_BIYING, apiKey);
		if(key==null) return;
		
		String day = DateUtil.format(key.getRegdate(), "dd");
		String yyyyMM = DateUtil.formatDate3(new Date());
		String restart = yyyyMM + "-" + day;
		Date date = DateUtil.parseDate(restart);
		if(date.getTime()<DateUtil.getSysDateOnly().getTime()){
			date = DateUtil.add(date, DateUtil.MONTH, 1);
		}
		
		key.setStatus(SystemConstants.STATUS_OFF);
		key.setRestartdate(date);
		
		saveOrUpdate(key);
	}
	
	/**
	 * 启用到期的必应
	 * 修改历史：
	 */
	public void restartBing(){
		StringBuilder sbHql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();

		sbHql.append("update CmcApikey set status=1, restartdate=null where status=0 and apitype=:apitype");
		params.put("apitype", DictConstant.DICT_APIKEYTYPE_BIYING);
		
		sbHql.append(" and restartdate<=curdate()");
		
		updateBatch(sbHql.toString(), params);
	}
}
