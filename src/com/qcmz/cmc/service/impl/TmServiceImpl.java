package com.qcmz.cmc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ITmDao;
import com.qcmz.cmc.entity.CmcTm;
import com.qcmz.cmc.entity.CmcTmVersion;
import com.qcmz.cmc.service.ITmService;
import com.qcmz.framework.exception.DataRepeatException;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.log.OperLogUtil;
import com.qcmz.framework.util.log.Operator;

@Service
public class TmServiceImpl implements ITmService {
	@Autowired
	private ITmDao tmDao;

	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean<CmcTm>
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String,?> map, PageBean pageBean){
		tmDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 获取所有翻译机信息
	 * @return
	 */
	@Override
	public List<CmcTm> findTm() {
		return tmDao.findTm();
	}
	
	/**
	 * 获取翻译机信息
	 * @param tmId
	 * @return
	 */
	public CmcTm getTm(Long tmId){
		return (CmcTm) tmDao.load(CmcTm.class, tmId);
	} 

	/**
	 * 获取最新版本信息
	 * @return
	 */
	public CmcTmVersion getLastVersion(){
		return tmDao.getLastVersion();
	}
	
	
	/**
	 * 保存翻译机信息
	 * @param bean
	 * @param oper
	 * @exception DataRepeatException
	 */
	public void saveOrUpdate(CmcTm bean, Operator oper){
		CmcTm tm = tmDao.getTm(bean.getTmcode());
		if(tm!=null){
			if(!tm.getTmid().equals(bean.getTmid())){
				throw new DataRepeatException("已经存在");
			}else{
				return;	//没有改动，直接返回
			}
		}
		
		tmDao.saveOrUpdate(bean);
	}
	
	/**
	 * 删除翻译机信息
	 * @param tmId
	 * @param oper
	 */
	public void delete(Long tmId, Operator oper){
		CmcTm entity = getTm(tmId);
		
		//删除
		tmDao.delete(entity);
		
		//日志
		OperLogUtil.saveDelLog(tmId, oper, entity);
	}
}
