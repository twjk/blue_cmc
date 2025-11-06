package com.qcmz.cmc.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qcmz.cmc.cache.LocalSourceMap;
import com.qcmz.cmc.cache.RewardActivityMap;
import com.qcmz.cmc.config.SystemConfig;
import com.qcmz.cmc.entity.CmcJobArticle;
import com.qcmz.cmc.entity.CmcJobArticlePic;
import com.qcmz.cmc.service.IJobArticlePicService;
import com.qcmz.cmc.service.IJobArticleService;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.cmc.util.JobArticleUtil;
import com.qcmz.cmc.util.SystemUtil;
import com.qcmz.cmc.ws.provide.vo.JobArticleListBean;
import com.qcmz.cmc.ws.provide.vo.JobArticleQueryBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.geography.GeographyBean;
import com.qcmz.framework.util.FileServiceUtil;
import com.qcmz.framework.util.FileServiceUtil.PicFileBean;
import com.qcmz.framework.util.StringUtil;

@Component
public class JobArticleProcess {
	@Autowired
	private IJobArticleService jobArticleService;
	@Autowired
	private IJobArticlePicService jobArticlePicService;
	@Autowired
	private LocalSourceMap localSourceMap;
	@Autowired
	private RewardActivityMap rewardActivityMap;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 分页获取就业信息
	 * @param query
	 * @param pageSize
	 * @param encrypt 是否加密重要信息
	 * @return
	 */
	public List<JobArticleListBean> findArticle(JobArticleQueryBean query, int pageSize, boolean encrypt){
		List<JobArticleListBean> result = new ArrayList<JobArticleListBean>();
		JobArticleListBean bean = null;
		
		//城市处理
		GeographyBean geo = null;
		Long cityId = null;
		if(StringUtil.notBlankAndNull(query.getCityName())){
			geo = SystemUtil.getGeography(DictConstants.DICT_COUNTRYCODE_CN, query.getCityName());
			if(geo==null){
				return result; 
			}
			cityId = geo.getCityId();
		}
		
		List<Long> articleIds = new ArrayList<Long>();
		Map<Long, JobArticleListBean> articleMap = new HashMap<Long, JobArticleListBean>();		
		List<CmcJobArticle> list = jobArticleService.findArticle(query.getTitle(), query.getCatId(), cityId, pageSize, query.getMoreBaseId());
		for (CmcJobArticle entity : list) {
			bean = new JobArticleListBean();
			bean.setArticleId(entity.getArtid());
			if(encrypt){
				bean.setTitle(CmcUtil.encrypt(entity.getTitle()));
				bean.setLink(CmcUtil.encrypt(entity.getLink()));
			}else{
				bean.setTitle(entity.getTitle());
				bean.setLink(entity.getLink());
			}
			bean.setSourceName(localSourceMap.getSourceName(entity.getSourceid()));
			bean.setCityName(entity.getCityname());
			bean.setPublishTime(entity.getPublishtime().getTime());
			bean.setSortId(entity.getSortquery());
			bean.setMaxInviteRewardAmount(rewardActivityMap.getMaxRewardAmount(entity.getActid()));
			
			result.add(bean);
			articleMap.put(bean.getArticleId(), bean);
			articleIds.add(entity.getArtid());
		}
		
		//图片
		/*
		if(!articleIds.isEmpty()){
			List<CmcJobArticlePic> pics = jobArticlePicService.findPic(articleIds);
			for (CmcJobArticlePic pic : pics) {
				articleMap.get(pic.getArtid()).getPics().add(new JobArticlePicBean(pic.getPicurl(), pic.getThumburl()));
			}
		}*/
		
		return result;
	}
	
	/**
	 * 下载图片
	 * 公众号的图片多重复无用，暂时不下载
	 */
	public void downloadPic(){
		if(!SystemConfig.isRelease()){
			logger.warn("非正式环境不能下载就业信息图片");
			return;
		}
		
		int pageSize = 100;
		Long lastPicId = null;
		PicFileBean picFileBean = null;
		String dirPath = null;
		int count = 0;
		
		int thumbMaxWidth = 500;
		int thumbMaxHeight = 500;	
		
		logger.info("开始下载就业信息图片");
		do{
			List<CmcJobArticlePic> list = jobArticlePicService.findPic4Download(pageSize, lastPicId);
			if(list.isEmpty()) break;
			for (CmcJobArticlePic pic : list) {
				lastPicId = pic.getPicid();
				if(FileServiceUtil.isOuterUrl(pic.getPicurl())){
					dirPath = JobArticleUtil.getPicDirPath();
					picFileBean = FileServiceUtil.catchOuterPic(pic.getPicurl(), dirPath, false, thumbMaxWidth, thumbMaxHeight);
					if(picFileBean==null){
						logger.error("抓取站外图片失败："+pic.getPicid()+","+pic.getPicurl());
						continue;
					}
					
					//更新图片信息
					pic.setPicurl(picFileBean.getPicUrl());
					pic.setThumburl(picFileBean.getThumbUrl());
					jobArticlePicService.updatePic(pic);
					
					count++;
					com.qcmz.framework.util.SystemUtil.sleep(500);
				}
			}
			logger.info("下载就业信息图片，已处理数："+count);
		}while(true);
		logger.info("完成下载就业信息图片，总处理数："+count);
	}
}
