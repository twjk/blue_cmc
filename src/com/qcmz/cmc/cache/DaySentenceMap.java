package com.qcmz.cmc.cache;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.dao.IDaySentenceDao;
import com.qcmz.cmc.ws.provide.vo.DaySentenceBean;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：每日一句缓存
 * 修改历史：
 */
@Component
public class DaySentenceMap extends AbstractCacheMap {
	@Autowired
	private IDaySentenceDao daySentenceDao;
	
	/**
	 * 列表
	 */
	private List<DaySentenceBean> list;

	
	/** 
	 * 初始化
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		list = daySentenceDao.findSentenceInfo(SystemConfig.DAYSENTENCE_MAXRESULT, DateUtil.setMaxTime(new Date()));
	}
	
	/**
	 * 下一条句子
	 * @param sentenceId
	 * @return
	 * 修改历史：
	 */
	public DaySentenceBean next(Long sentenceId, String from){
		if(!safeInit(list) || list.isEmpty()) return null;
		int baseIndex = -1;
		DaySentenceBean baseBean = null;
		if(sentenceId!=null){
			baseIndex = list.indexOf(new DaySentenceBean(sentenceId));
			if(baseIndex>-1){
				baseBean = list.get(baseIndex);
			}
		}
		
		DaySentenceBean bean = null;
		for (int i = baseIndex + 1; i < list.size(); i++) {
			bean = list.get(i);
			if(StringUtil.notBlankAndNull(from)
					&& !bean.getFrom().equals(from)){
				continue;
			}
			if(baseBean!=null
					&& bean.getDate().compareTo(baseBean.getDate())>=0){
				continue;
			}
			return bean;
		}
		return null;
	}
	
	/**
	 * 上一条句子
	 * @param sentenceId
	 * @return
	 * 修改历史：
	 */
	public DaySentenceBean pre(Long sentenceId, String from){
		if(!safeInit(list) || list.isEmpty()) return null;
		
		int baseIndex = -1;
		DaySentenceBean baseBean = null;
		if(sentenceId!=null){
			baseIndex = list.indexOf(new DaySentenceBean(sentenceId));
			if(baseIndex>-1){
				baseBean = list.get(baseIndex);
			}
		}
		if(baseIndex<1) return null;
		
		DaySentenceBean bean = null;
		for (int i = baseIndex - 1; i>=0; i--) {
			bean = list.get(i);
			if(StringUtil.notBlankAndNull(from)
					&& !bean.getFrom().equals(from)){
				continue;
			}
			if(baseBean!=null
					&& bean.getDate().compareTo(baseBean.getDate())<=0){
				continue;
			}
			return bean;
		}
		return null;
	}
	
	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void delete(Object obj) {
	}


	/** 
	 * @param obj
	 * 修改历史：
	 */
	@Override
	public void update(Object obj) {
	}

}
