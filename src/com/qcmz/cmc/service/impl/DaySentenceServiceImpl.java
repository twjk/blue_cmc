package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.DaySentenceMap;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.dao.IDaySentenceDao;
import com.qcmz.cmc.entity.CmcPkgDaysentence;
import com.qcmz.cmc.service.IDaySentenceService;
import com.qcmz.cmc.util.CmcUtil;
import com.qcmz.cmc.ws.provide.vo.DaySentenceBean;
import com.qcmz.framework.constant.DictConstants;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.DateUtil;
import com.qcmz.framework.util.StaticUtil;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：对该类的主要功能进行说明
 * 修改历史：
 */
@Service
public class DaySentenceServiceImpl implements IDaySentenceService {
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private IDaySentenceDao daySentenceDao;
	
	@Autowired
	private LangMap langMap;
	@Autowired
	private DaySentenceMap daySentenceMap;
	
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		daySentenceDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 获取所有句子
	 * @return
	 */
	public List<CmcPkgDaysentence> findSentence(){
		return daySentenceDao.findSentence();
	}
	
	/**
	 * 获取单条信息
	 * @return
	 * 修改历史：
	 */
	public CmcPkgDaysentence load(Long id){
		return (CmcPkgDaysentence) daySentenceDao.load(CmcPkgDaysentence.class, id);
	}
	
	/**
	 * 获取有效的句子信息
	 * @param sentId
	 * @return
	 * 修改历史：
	 */
	public DaySentenceBean getSentenceInfo(Long sentId){
		return daySentenceDao.getSentenceInfo(sentId);
	}
	
	/**
	 * 获取下一条句子
	 * @param sentId
	 * @return
	 * 修改历史：
	 */
	public DaySentenceBean getNextSentenceInfo(Long sentId, String from){
		return daySentenceMap.next(sentId, from);
	}
	
	/**
	 * 获取上一条句子
	 * @param sentId
	 * @return
	 * 修改历史：
	 */
	public DaySentenceBean getPreSentenceInfo(Long sentId, String from){
		return daySentenceMap.pre(sentId, from);
	}
	
	/**
	 * 获得待推送的句子
	 * @return
	 * 修改历史：
	 */
	public List<CmcPkgDaysentence> findSentence4Push(){
		return daySentenceDao.findSentence4Push();
	}
	
	/**
	 * 获取每日一句列表
	 * @param from not null
	 * @param start not null
	 * @param end not null
	 * @return
	 */
	public List<CmcPkgDaysentence> findSentence(String from, Date start, Date end){
		return daySentenceDao.findSentence(from, start, end);
	}
	
	/**
	 * 获取某一天的每日一句列表
	 * @param from
	 * @param day MMdd
	 * @return
	 */
	public List<CmcPkgDaysentence> findSentence(String from, String day){
		return daySentenceDao.findSentence(from, day);
	}
	
	/**
	 * 分页获取有效的每日一句
	 * @param from
	 * @param pageBean<CmcPkgDaysentence>
	 * 修改历史：
	 */
	public void findSentence(String from, PageBean pageBean){
		daySentenceDao.findSentence(from, pageBean);
	}
	
	/**
	 * 分页获取每日一句编号
	 * @param pageBean
	 * 修改历史：
	 */
	public void findId(PageBean pageBean){
		daySentenceDao.findId(pageBean);
	}
	
	/**
	 * 保存
	 * @param bean
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public CmcPkgDaysentence saveOrUpdate(CmcPkgDaysentence bean, Operator oper){
		boolean isExist = daySentenceDao.isExist(bean.getSentid(), bean.getReleasetime(), bean.getFromlang());
		if(isExist){
			throw new DataException("信息重复");
		}
		
		//处理不可见符合，以免无法正常发声
		if(DictConstants.DICT_LANG_EN.equals(bean.getFromlang())){
			bean.setSrc(StringUtil.replaceInvisible(bean.getSrc()).trim());
		}
		
		if(bean.getSentid()==null){
			//添加
			if(bean.getSmallpic()!=null){
				bean.setSmallpic(bean.getSmallpic().trim());
			}
			if(bean.getPic()!=null){
				bean.setPic(bean.getPic().trim());
			}
			bean.setPushstatus(SystemConstants.STATUS_UNDEAL);
			bean.setHtmlurl(createHtml(bean));
			daySentenceDao.save(bean);
			OperLogUtil.saveAddLog(bean.getSentid(), oper, bean);
			return bean;
		}else{
			//修改
			CmcPkgDaysentence entity = load(bean.getSentid());
			Map<String, String> oldDescMap = OperLogUtil.getBeanDescMap(entity);
			
			entity.setTitle(bean.getTitle());
			entity.setFromlang(bean.getFromlang());
			entity.setSrc(bean.getSrc());
			entity.setTolang(bean.getTolang());
			entity.setDst(bean.getDst());
			entity.setSmallpic(bean.getSmallpic()==null?null:bean.getSmallpic().trim());
			entity.setPic(bean.getPic()==null?null:bean.getPic().trim());
			entity.setSource(bean.getSource());
			entity.setReleasetime(bean.getReleasetime());
			entity.setStatus(bean.getStatus());
			
			if(SystemConstants.STATUS_ON.equals(bean.getStatus())){
				
				if(entity.getReleasetime()!=null 
						&& DateUtil.afterDate(entity.getReleasetime(), new Date())){
					entity.setPushstatus(SystemConstants.STATUS_UNDEAL);
				}
			}
			entity.setHtmlurl(createHtml(entity));
			
			daySentenceDao.update(entity);
			OperLogUtil.saveUpdateLog(bean.getSentid(), oper, oldDescMap, entity);
			return entity;
		}
	}
	
	/**
	 * 生成静态页面
	 * @param sentId
	 * @return 静态页地址
	 * 修改历史：
	 */
	public String createHtml(Long sentId){
		return createHtml(load(sentId));
	}
	
	/**
	 * 生成静态页面，失败抛出异常
	 * @param bean
	 * @return
	 * 修改历史：
	 */
	private String createHtml(CmcPkgDaysentence bean){
		String htmlUrl = null;
		try {
			//文件名
			String fileName = null;
			if(StringUtil.notBlankAndNull(bean.getHtmlurl())){
				fileName = bean.getHtmlurl().substring(bean.getHtmlurl().lastIndexOf("/")+1);
			}else{
				fileName = System.currentTimeMillis()+".html";
			}
			
			//组装信息
			String date = CmcUtil.formatReleaseTime(bean.getReleasetime());
			String day = null;
			String yearmonth = null;
			if(StringUtil.notBlankAndNull(date)){
				day = date.substring(0,2);
				yearmonth = date.substring(3);
			}else{
				day = "";
				yearmonth = "";
			}
			
			Map<String, Object> datas = new HashMap<String, Object>();
			datas.put("title", bean.getTitle());
			datas.put("pic", bean.getPic());
			datas.put("fromlang", langMap.getLangName4Text(bean.getFromlang()));
			datas.put("src", bean.getSrc());
			datas.put("dst", bean.getDst());
			datas.put("source", bean.getSource());
			datas.put("day", day);
			datas.put("yearmonth", yearmonth);
			
			//创建文件
			htmlUrl = StaticUtil.createHtmlAndUpload(datas, "1001", fileName);
			
			//结果入库
			if(StringUtil.notBlankAndNull(htmlUrl)){
				daySentenceDao.updateHtmlUrl(bean.getSentid(), htmlUrl);
			}
			
		} catch (Exception e) {
			logger.error("每日一句生成静态文件失败："+bean.getTitle(), e);
		}
		if(StringUtil.isBlankOrNull(htmlUrl)){
			throw new DataException("生成静态文件失败");
		}
		return htmlUrl;
	}
	
	/**
	 * 批量更新
	 * @param list
	 * 修改历史：
	 */
	public void saveOrUpdateAll(List<CmcPkgDaysentence> list){
		daySentenceDao.saveOrUpdateAll(list);
	}
	
	/**
	 * 更新状态
	 * @param sentId
	 * @param status
	 * @param oper
	 * 修改历史：
	 */
	public void updateStatus(Long sentId, Integer status, Operator oper){
		CmcPkgDaysentence entity = load(sentId);
		
		entity.setStatus(status);
		if(SystemConstants.STATUS_ON.equals(status)){
			entity.setPushstatus(SystemConstants.STATUS_UNDEAL);
		}
		daySentenceDao.update(entity);
		
		StringBuilder sbLog = new StringBuilder("状态[").append(status).append("]");
		OperLogUtil.saveOperLog(CmcPkgDaysentence.class, sentId, oper, sbLog.toString());
	}
	
	/**
	 * 更新图片
	 * @param sentId
	 * @param picUrl
	 */
	public void updatePic(Long sentId, String picUrl){
		daySentenceDao.updatePic(sentId, picUrl);
	}
	
	/**
	 * 更新小图
	 * @param sentId
	 * @param picUrl
	 */
	public void updateSmallPic(Long sentId, String picUrl){
		daySentenceDao.updateSmallPic(sentId, picUrl);
	}
	
	/**
	 * 删除
	 * @param id
	 * @param oper
	 * 修改历史：
	 */
	public void delete(Long id, Operator oper){
		CmcPkgDaysentence entity = load(id);
		daySentenceDao.delete(entity);
		OperLogUtil.saveDelLog(id, oper, entity);
	}
}
