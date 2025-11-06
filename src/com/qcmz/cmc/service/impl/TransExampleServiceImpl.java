package com.qcmz.cmc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ITransExampleDao;
import com.qcmz.cmc.entity.CmcTransExample;
import com.qcmz.cmc.service.ITransExampleService;

/**
 * 类说明：例句服务
 * 修改历史：
 */
@Service
public class TransExampleServiceImpl implements ITransExampleService {
	@Autowired
	private ITransExampleDao transExampleDao;
	
	/**
	 * 批量保存
	 * @param list
	 * 修改历史：
	 */
	public void saveOrUpdateAll(List<CmcTransExample> list){
		if(list==null || list.isEmpty()) return;
		
		List<CmcTransExample> result = new ArrayList<CmcTransExample>();
		
		CmcTransExample entity = null;
		for (CmcTransExample bean : list) {
			entity = transExampleDao.getBean(bean.getFromlang(), bean.getTolang(), bean.getTranssrc(), bean.getSrc());
			if(entity==null){
				result.add(bean);
			}else{
				entity.setSrc(bean.getSrc());
				entity.setDst(bean.getDst());
				result.add(entity);
			}
		}
		transExampleDao.saveOrUpdateAll(result);
	}	
}
