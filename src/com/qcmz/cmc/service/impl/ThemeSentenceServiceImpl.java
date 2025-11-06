package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.CatMap;
import com.qcmz.cmc.dao.IThemeSentenceDao;
import com.qcmz.cmc.dao.ITransLibDao;
import com.qcmz.cmc.entity.CmcPkgSentence;
import com.qcmz.cmc.service.IThemeSentenceService;
import com.qcmz.cmc.util.TransUtil;
import com.qcmz.cmc.vo.TransLibraryEntity;
import com.qcmz.cmc.ws.provide.vo.ThemeSentenceBean;
import com.qcmz.framework.exception.DataAccessException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class ThemeSentenceServiceImpl implements IThemeSentenceService {
	@Autowired
	private IThemeSentenceDao themeSentenceDao;
	@Autowired
	private ITransLibDao transLibDao;
	@Autowired
	private CatMap catMap;
	
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		themeSentenceDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 获取主题语句信息
	 * @param themeId
	 * @param catId
	 * 修改历史：
	 */
	public List<ThemeSentenceBean> findSentenceInfo(Long themeId, String catId){
		//cat处理
		if(StringUtil.notBlankAndNull(catId)){
			catId = catMap.getFullCat(catId);
		}
		
		List<ThemeSentenceBean> result = themeSentenceDao.findSentenceInfo(themeId, catId);
		for (ThemeSentenceBean bean : result) {
			if(StringUtil.notBlankAndNull(bean.getCatId())){
				bean.setCatId(StringUtil.right(bean.getCatId(), 4));
				bean.setCatName(catMap.getCatName(bean.getCatId()));
			}
		}
		
		return result;
	}
	
	/**
	 * 获取明细信息
	 * @param detailId
	 * @return
	 * 修改历史：
	 */
	public CmcPkgSentence load(Long detailId){
		return (CmcPkgSentence) themeSentenceDao.load(CmcPkgSentence.class, detailId);
	}
	
	/**
	 * 获取明细信息，含主题信息
	 * @param detailId
	 * @return
	 * 修改历史：
	 */
	public CmcPkgSentence getJoinTheme(Long detailId){
		return themeSentenceDao.getJoinTheme(detailId);
	}
	
	/**
	 * 保存或修改句子
	 * @param bean
	 * @return
	 * 修改历史：
	 */
	public CmcPkgSentence saveOrUpdate(CmcPkgSentence bean, Operator oper){
		if(themeSentenceDao.isExist(bean)){
			throw new DataAccessException("已经存在");
		}
		
		CmcPkgSentence entity = null;		
		if(bean.getSentid()==null){
			themeSentenceDao.save(bean);
			entity = bean;
		}else{
			entity = load(bean.getSentid());
			entity.setCat(bean.getCat());
			entity.setFromlang(bean.getFromlang());
			entity.setSrc(bean.getSrc());
			entity.setTolang(bean.getTolang());
			entity.setDst(bean.getDst());
			entity.setVoice(bean.getVoice());
			themeSentenceDao.saveOrUpdate(entity);
		}
		
		if(StringUtil.isBlankOrNull(entity.getVoice())){
			entity.setVoice(TransUtil.getThemeSentenceVoice(entity.getThemeid(), entity.getSentid()));
			themeSentenceDao.saveOrUpdate(entity);
		}
		
		return entity;		
	}
	
	
	/**
	 * 从译库导入
	 * @param themeId
	 * @param cat
	 * @param libids
	 * 修改历史：
	 */
	public void importFromLib(Long themeId, String cat, List<Long> libids, Operator oper){
		List<TransLibraryEntity> libs = transLibDao.findLib(libids);
		if(libs.isEmpty()) return;

		List<CmcPkgSentence> list = new ArrayList<CmcPkgSentence>();
		CmcPkgSentence bean = null;
		for (TransLibraryEntity lib : libs) {
			bean = new CmcPkgSentence();
			bean.setCat(cat);
			bean.setThemeid(themeId);
			bean.setFromlang(lib.getFromlang());
			bean.setSrc(lib.getSrc());
			bean.setTolang(lib.getTolang());
			bean.setDst(lib.getDst());
			bean.setVoice(lib.getVoice());
			bean.setLibid(lib.getLibid());
			if(!themeSentenceDao.isExist(bean)){
				list.add(bean);
			}
		}
		if(list.isEmpty()) return;
		
		themeSentenceDao.saveOrUpdateAll(list);
		
		for (CmcPkgSentence entity : list) {
			entity.setVoice(TransUtil.getThemeSentenceVoice(entity.getThemeid(), entity.getSentid()));
		}
		themeSentenceDao.saveOrUpdateAll(list);
		
	}
	
	/**
	 * 删除
	 * @param themeId
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public void delete(Long detailId, Operator oper){
		themeSentenceDao.delete(CmcPkgSentence.class, detailId);
	}
}
