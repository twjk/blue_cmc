package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.cache.CatMap;
import com.qcmz.cmc.cache.LangMap;
import com.qcmz.cmc.dao.ITransLibDao;
import com.qcmz.cmc.service.IThemeSentenceService;
import com.qcmz.cmc.service.ITransLibService;
import com.qcmz.cmc.util.TransUtil;
import com.qcmz.cmc.vo.TransLibrary4ImportBean;
import com.qcmz.cmc.vo.TransLibraryEntity;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.exception.DataRepeatException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;

/**
 * 类说明：译库服务
 * 修改历史：
 */
@Service
public class TransLibServiceImpl implements ITransLibService {
	@Autowired
	private ITransLibDao transLibDao;
	@Autowired
	private IThemeSentenceService themeSentenceService;
	
	@Autowired
	private CatMap catMap;
	@Autowired
	private LangMap langMap;
	
	/**
	 * 分页获取译库列表
	 * @param map
	 * @param pageBean<TransLibraryEntity>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		transLibDao.queryByMapTerm(map.get("libClass"), map, pageBean);
	}
	
	/**
	 * 分页获取译库列表
	 * @param libClass not null
	 * @param status
	 * @param pageSize
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> findLib(String libClass, Integer status, Long lastId, int pageSize){
		return transLibDao.findLib(libClass, status, lastId, pageSize);
	}
	
	/**
	 * 获取译库列表
	 * @param libClass not null
	 * @param from not null
	 * @param to not null
	 * @param status
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> findLib(String libClass, String from, String to){
		return transLibDao.findLib(libClass, from, to);
	}
	
	/**
	 * 获取译库列表
	 * @param from not null
	 * @param src not null
	 * @param to not null
	 * @param status
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> findLib(String from, String src, String to, Integer status){
		String libClass = TransUtil.getLibClass(from, to);
		return transLibDao.findLib(libClass, from, src, to, status);
	}
	
	/**
	 * 获取可以放入缓存的译库列表
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> findLib4Cache(String libClass){
		return transLibDao.findLib4Cache(libClass);
	}
	
	/**
	 * 获取待导入的译库
	 * @param themeId not null
	 * @param from not null
	 * @param to not null
	 * @param key
	 * @return
	 * 修改历史：
	 */
	public List<TransLibrary4ImportBean> findLib4ImportTheme(Long themeId, String libType, String from, String to, String key){
		String libClass = TransUtil.getLibClass(from, to);
		List<TransLibrary4ImportBean> result = transLibDao.findLib4ImportTheme(libClass, themeId, libType, from, to, key);
		for (TransLibrary4ImportBean lib : result) {
			lib.setFromName(langMap.getLangName4Text(lib.getFrom()));
			lib.setToName(langMap.getLangName4Text(lib.getTo()));
			lib.setCatName(catMap.getCatName(lib.getCat()));			
		}
		return result;
	}
	
	/**
	 * 获取译库源语言代码列表
	 * @return
	 * 修改历史：
	 */
	public List<String> findFromLang(){
		return transLibDao.findFromLang();
	}
	
	/**
	 * 获取译库目标语言代码列表
	 * @return
	 * 修改历史：
	 */
	public List<String> findToLang(){
		return transLibDao.findToLang();
	}
	
	/**
	 * 获取单条记录
	 * @param libClass
	 * @param libId
	 * @return
	 * 修改历史：
	 */
	public TransLibraryEntity getLib(String libClass, Long libId){
		return (TransLibraryEntity) transLibDao.getLib(libClass, libId);
	}
	
	/**
	 * 获取新的译库编号
	 * @return
	 * 修改历史：
	 */
	public Long getNewLibId(){
		return transLibDao.getNewLibId();
	}
	
	/**
	 * 判断是否存在
	 * @param from
	 * @param src
	 * @return
	 * 修改历史：
	 */
	public boolean isExist(String from, String src, String to){
		String libClass = TransUtil.getLibClass(from, to);
		return transLibDao.isExist(libClass, from, src, to);
	}
	
	/**
	 * 批量保存，重复继续
	 * @param bean 除了libid,libclass,voice其他字段都需要赋值
	 * @param oper
	 * 修改历史：
	 */
	public void saveOrUpdateAll(List<TransLibraryEntity> list, Operator oper){
		if(list==null|| list.isEmpty()) return;
		for (TransLibraryEntity bean : list) {
			try {
				saveOrUpdate(bean, oper);
			} catch (DataRepeatException e) {
			}
		}
	}
	
	/**
	 * 添加或修改
	 * @param bean 除了libid,libclass,voice其他字段都需要赋值
	 * @param oper
	 * @exception DataRepeatException
	 * 修改历史：
	 */
	public List<TransLibraryEntity> saveOrUpdate(TransLibraryEntity bean, Operator oper){
		List<TransLibraryEntity> result = new ArrayList<TransLibraryEntity>();

		TransLibraryEntity oldEntity = null;
		TransLibraryEntity oldReverse = null;
		String oldDst = null;
		String oldSrc = null;
		boolean isUpdate = false;
		
		//1.获取表
		String libClass = bean.getLibClass();
		if(StringUtil.isBlankOrNull(libClass)){
			libClass = TransUtil.getLibClass(bean.getFromlang(), bean.getTolang());
			bean.setLibClass(libClass);
		}
		String reverseLibClass = TransUtil.getLibClass(bean.getTolang(), bean.getFromlang());
		
		//查重
		if(transLibDao.isRepeat(libClass, bean.getFromlang(), bean.getSrc(), bean.getTolang(), bean.getDst(), bean.getLibid())){
			throw new DataRepeatException("已经存在");
		}
		
		//2.入库
		if(bean.getLibid()!=null){
			isUpdate = true;

			oldEntity = transLibDao.getLib(libClass, bean.getLibid());
			
			bean.setHitcount(oldEntity.getHitcount());	//保证计数是最新的
			oldSrc = oldEntity.getSrc();
			oldDst = oldEntity.getDst();
		}else{
			bean.setLibid(transLibDao.getNewLibId());
		}
		
		transLibDao.saveOrUpdate(TransUtil.getLibEntity(bean));
		result.add(bean);
		
		//3.双向处理
		if(SystemConstants.STATUS_ON.equals(bean.getTwoway())){
			TransLibraryEntity reverse = transLibDao.getLib(reverseLibClass, bean.getTolang(), bean.getDst(), bean.getFromlang(), bean.getSrc());
			if(reverse==null){
				reverse = new TransLibraryEntity();
				reverse.setLibid(transLibDao.getNewLibId());
			}
			reverse.setLibClass(reverseLibClass);
			reverse.setLibtype(bean.getLibtype());
			reverse.setFromlang(bean.getTolang());
			reverse.setSrc(bean.getDst());
			reverse.setTolang(bean.getFromlang());
			reverse.setDst(bean.getSrc());
			reverse.setTwoway(bean.getTwoway());
			reverse.setCat(bean.getCat());
			reverse.setStatus(bean.getStatus());
			reverse.setCheckstatus(bean.getCheckstatus());
			reverse.setSourceid(bean.getSourceid());
			transLibDao.saveOrUpdate(TransUtil.getLibEntity(reverse));
			result.add(reverse);
			
			//修改时：双向翻译且原文或译文有了变动，则删除原互译的记录
			if(isUpdate && (!bean.getSrc().equals(oldSrc) || !bean.getDst().equals(oldDst))){
				oldReverse = transLibDao.getLib(reverseLibClass, oldEntity.getTolang(), oldEntity.getDst(), oldEntity.getFromlang(), oldEntity.getSrc());
				if(oldReverse!=null && SystemConstants.STATUS_ON.equals(oldReverse.getTwoway())){
					transLibDao.deleteLib(oldReverse.getLibClass(), oldReverse.getLibid());
				}
			}
		}
		return result;
	}

	/**
	 * 迁移
	 * @param bean
	 * @param toLibClass
	 * @param oper
	 * 修改历史：
	 */
	public void migration(TransLibraryEntity bean, String toLibClass, Long themeId, Operator oper){
		String fromLibClass = bean.getLibClass();
		if(fromLibClass.equals(toLibClass)) return;
		
		Long libId = bean.getLibid();
		
		try {
			//移入新表
			bean.setLibClass(toLibClass);
			if(TransUtil.LIBCLASS_TEMP.equals(fromLibClass)){
				bean.setLibid(null);	//临时表的编号需要重新生成
			}
			saveOrUpdate(bean, oper);
			if(themeId!=null){
				List<Long> libids = new ArrayList<Long>();
				libids.add(bean.getLibid());
				themeSentenceService.importFromLib(themeId, bean.getCat(), libids, oper);
			}
		} catch (DataRepeatException e) {
			//重复继续
		}
				
		//删除原数据
		deleteNotReverse(fromLibClass, libId, oper);
	}
	
	/**
	 * 更新状态
	 * @param libClass
	 * @param libId
	 * @param status
	 * 修改历史：
	 */
	public void updateStatus(String libClass, Long libId, Integer status, Operator oper){
		transLibDao.updateStatus(libClass, libId, status);
	}
	
	/**
	 * 更新核对状态
	 * @param libClass
	 * @param libId
	 * @param checkStatus
	 * 修改历史：
	 */
	public void updateCheckStatus(String libClass, Long libId, Integer checkStatus, Operator oper){
		transLibDao.updateCheckStatus(libClass, libId, checkStatus);
	}
	
	/**
	 * 增加命中次数
	 * @param libId
	 * @param times
	 * 修改历史：
	 */
	public void increaseHitCount(String libClass, Long libId, int times){
		transLibDao.increaseHitCount(libClass, libId, times);
	}
	
	/**
	 * 保存命中次数
	 * @param libClass
	 * @param libId
	 * @param hitCount
	 * 修改历史：
	 */
	public void updateHitCount(String libClass, Long libId, Long hitCount){
		transLibDao.updateHitCount(libClass, libId, hitCount);
	}
	
	/**
	 * 删除，同时删除反向翻译的结果
	 * @param libClass
	 * @param libId
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public List<TransLibraryEntity> delete(String libClass, Long libId, Operator oper){
		List<TransLibraryEntity> result = new ArrayList<TransLibraryEntity>();
		
		TransLibraryEntity entity = getLib(libClass, libId);
		
		transLibDao.deleteLib(libClass, libId);
		OperLogUtil.saveDelLog(libClass, libId, oper, entity);	//删除日志
		result.add(entity);
		
		//双向处理
		if(SystemConstants.STATUS_ON.equals(entity.getTwoway())){
			String reverseLibClass = TransUtil.getLibClass(entity.getTolang(), entity.getFromlang());
			List<TransLibraryEntity> reverses = transLibDao.findLib(reverseLibClass, entity.getTolang(), entity.getDst(), entity.getFromlang(), null);
			for (TransLibraryEntity reverse : reverses) {
				if(SystemConstants.STATUS_ON.equals(reverse.getTwoway())
						&& reverse.getDst().equals(entity.getSrc())){
					transLibDao.deleteLib(reverse.getLibClass(), reverse.getLibid());
					OperLogUtil.saveDelLog(reverse.getLibClass(), reverse.getLibid(), oper, reverse);
					result.add(reverse);
				}
			}
		}
		
		return result;
	}

	/**
	 * 删除，不删除反向翻译结果
	 * @param libClass
	 * @param libId
	 * @param oper
	 * @return
	 * 修改历史：
	 */
	public void deleteNotReverse(String libClass, Long libId, Operator oper){
		TransLibraryEntity entity = getLib(libClass, libId);
		transLibDao.deleteLib(libClass, libId);
		OperLogUtil.saveDelLog(libClass, libId, oper, entity);	//删除日志
	}
}
