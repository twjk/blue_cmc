package com.qcmz.cmc.cache;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.entity.CmcTm;
import com.qcmz.cmc.entity.CmcTmVersion;
import com.qcmz.cmc.service.ITmService;
import com.qcmz.cmc.ws.provide.vo.TmVersionBean;
import com.qcmz.framework.cache.AbstractCacheMap;

/**
 * 翻译机缓存
 */
@Component
public class TransMachineMap extends AbstractCacheMap {
	@Autowired
	private ITmService tmService;
	
	/**
	 * 翻译机编号
	 */
	private Set<String> tmcodes;
	/**
	 * 最新版本信息
	 */
	private TmVersionBean lastVersion = null;
	
	@Override @PostConstruct	
	protected void init() {
		Set<String> tmcodesTemp = new HashSet<String>();
		
		//翻译机信息
		List<CmcTm> list = tmService.findTm();
		for (CmcTm entity : list) {
			tmcodesTemp.add(entity.getTmcode());
		}
		
		tmcodes = tmcodesTemp;
		
		//版本
		TmVersionBean lastVersionTemp = null;
		CmcTmVersion cmcTmVersion = tmService.getLastVersion();
		if(cmcTmVersion!=null){
			lastVersionTemp = new TmVersionBean();
			lastVersionTemp.setVersion(cmcTmVersion.getVersioncode());
			lastVersionTemp.setUrl(cmcTmVersion.getUrl());
			lastVersionTemp.setDesc(cmcTmVersion.getDescription());
		}
		lastVersion = lastVersionTemp;
	}

	/**
	 * 校验翻译机编号是否存在
	 * @param code
	 * @return
	 */
	public boolean existCode(String code){
		if(!safeInit(tmcodes)) return false;
		return tmcodes.contains(code);
	}
	
	/**
	 * 获取翻译机最新版本信息
	 * @return
	 */
	public TmVersionBean getLastVersion(){
		return lastVersion;
	}
	
	@Override
	public void update(Object obj) {
		
	}

	@Override
	public void delete(Object obj) {

	}

}
