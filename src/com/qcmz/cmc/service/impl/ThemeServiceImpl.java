package com.qcmz.cmc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.CatMap;
import com.qcmz.cmc.cache.ThemeMap;
import com.qcmz.cmc.dao.IThemeDao;
import com.qcmz.cmc.dao.IThemeSentenceDao;
import com.qcmz.cmc.entity.CmcPkgTheme;
import com.qcmz.cmc.service.IThemeService;
import com.qcmz.cmc.ws.provide.vo.CatListBean;
import com.qcmz.cmc.ws.provide.vo.ThemeDetailBean;
import com.qcmz.cmc.ws.provide.vo.ThemeDownloadBean;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：对该类的主要功能进行说明
 * @author 李炳煜
 * 修改历史：
 */
@Service
public class ThemeServiceImpl implements IThemeService {
	@Autowired
	private IThemeDao themeDao;
	@Autowired
	private IThemeSentenceDao themeSentenceDao;
	@Autowired
	private CatMap catMap;
	@Autowired
	private ThemeMap themeMap;
	
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String,?> map, PageBean pageBean){
		themeDao.queryByMapTerm(map, pageBean);
	}
	
	/***
	 * 分页获取有效的主题列表
	 * @param pageBean
	 * 修改历史：
	 */
	public void findThemeInfo(PageBean pageBean){
		themeDao.findThemeInfo(pageBean);
	}
	
	/**
	 * 获取主题信息
	 * @param themeId
	 * @return
	 * 修改历史：
	 */
	public CmcPkgTheme load(Long themeId){
		return (CmcPkgTheme) themeDao.load(CmcPkgTheme.class, themeId);
	}
	
	/**
	 * 获取主题详细信息
	 * @param themeId
	 * @return
	 * 修改历史：
	 */
	public ThemeDetailBean getDetailInfo(Long themeId){
		ThemeDetailBean bean = new ThemeDetailBean();
		
		CmcPkgTheme entity = load(themeId);
		if(entity==null){
			throw new DataException("不存在");
		}
		
		//基础信息
		bean.setThemeId(themeId);
		bean.setThemeCode(entity.getThemecode());
		bean.setTitleCn(entity.getTitlecn());
		bean.setTitleLc(entity.getTitlelc());
		bean.setPicUrl(entity.getPic());
		bean.setBgpicUrl(entity.getBgpic());
		bean.setDownloadUrl(entity.getDownloadurl());
		bean.setFileSize(entity.getFilesize());
		
		//分类
		List<String> cats = themeSentenceDao.findCatLev1(themeId);
		if(cats!=null && !cats.isEmpty()){
			CatListBean catListBean = null;
			for (String catId : cats) {
				catListBean = catMap.getCat(catId);
				if(catListBean!=null){
					bean.getCats().add(catListBean);
				}
			}
		}
		
		return bean;
	}
	
	/**
	 * 获取主题下载信息
	 * @param themeId
	 * @return
	 * 修改历史：
	 */
	public ThemeDownloadBean getDownloadInfo(Long themeId){
		return getDownloadInfo(themeId, null);
	}
	
	/**
	 * 获取主题下载信息
	 * @param themeId
	 * @return
	 * 修改历史：
	 */
	public ThemeDownloadBean getDownloadInfo(String themeCode){
		return getDownloadInfo(null, themeCode);
	}
	
	/**
	 * 获取主题下载信息
	 * @param themeId
	 * @return
	 * 修改历史：
	 */
	public ThemeDownloadBean getDownloadInfo(Long themeId, String themeCode){
		CmcPkgTheme entity = themeMap.getBean(themeId, themeCode);
		if(entity==null){
			throw new DataException("不存在");
		}
		
		ThemeDownloadBean bean = new ThemeDownloadBean();
		bean.setThemeId(entity.getThemeid());
		bean.setThemeCode(entity.getThemecode());
		bean.setDownloadUrl(entity.getDownloadurl());
		bean.setFileSize(entity.getFilesize());
		
		return bean;
	}
	
	/**
	 * 保存或修改主题
	 * @param bean
	 * @return
	 * 修改历史：
	 */
	public CmcPkgTheme saveOrUpdate(CmcPkgTheme bean, Operator oper){
		if(themeDao.isExist(bean.getThemeid(), bean.getTitlecn())){
			throw new DataException("已经存在");
		}
		
		if(bean.getThemeid()==null){
			themeDao.save(bean);
			return bean;
		}else{
			CmcPkgTheme entity = load(bean.getThemeid());
			entity.setThemecode(bean.getThemecode());
			entity.setTitlecn(bean.getTitlecn());
			entity.setTitlelc(bean.getTitlelc());
			entity.setPic(bean.getPic());
			entity.setBgpic(bean.getBgpic());
			entity.setDownloadurl(bean.getDownloadurl());
			entity.setFilesize(bean.getFilesize());
			entity.setStatus(bean.getStatus());
			entity.setSortindex(bean.getSortindex());
			
			themeDao.saveOrUpdate(entity);
			return entity;
		}
	}
	
	/**
	 * 删除主题
	 * @param themeId
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public CmcPkgTheme delete(Long themeId, Operator oper){
		CmcPkgTheme entity = load(themeId);
		
		//清除明细
		themeSentenceDao.clearSentence(themeId);
		
		//删除主题
		themeDao.delete(entity);
		
		//日志
		OperLogUtil.saveDelLog(themeId, oper, entity);
		
		return entity;
	}
}
