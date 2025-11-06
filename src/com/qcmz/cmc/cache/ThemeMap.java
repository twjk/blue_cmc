package com.qcmz.cmc.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.dao.IThemeDao;
import com.qcmz.cmc.entity.CmcPkgTheme;
import com.qcmz.framework.cache.AbstractCacheMap;
import com.qcmz.framework.util.StringUtil;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Component
public class ThemeMap extends AbstractCacheMap {
	
	@Autowired
	private IThemeDao themeDao;
	
	private Map<Long, CmcPkgTheme> idMap;
	private Map<String, CmcPkgTheme> codeMap;

	/** 
	 * 
	 * 修改历史：
	 */
	@Override @PostConstruct
	protected void init() {
		try {
			Map<Long, CmcPkgTheme> idMapTemp = new HashMap<Long, CmcPkgTheme>();
			Map<String, CmcPkgTheme> codeMapTemp = new HashMap<String, CmcPkgTheme>();
			
			List<CmcPkgTheme> list = themeDao.findValidTheme();
			for (CmcPkgTheme bean : list) {
				idMapTemp.put(bean.getThemeid(), bean);
				codeMapTemp.put(bean.getThemecode(), bean);
			}
			
			idMap = idMapTemp;
			codeMap = codeMapTemp;
			logger.info("完成初始化主题缓存");
		} catch (Exception e) {
			logger.error("初始化主题缓存失败", e);
		}
		
	}

	/**
	 * 获得主题信息
	 * @param themeId
	 * @param themeCode
	 * @return
	 * 修改历史：
	 */
	public CmcPkgTheme getBean(Long themeId, String themeCode){
		if(idMap==null) init();
		if(idMap!=null){
			if(themeId!=null){
				return idMap.get(themeId);
			}else if(StringUtil.notBlankAndNull(themeCode)){
				return codeMap.get(themeCode);
			}
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
