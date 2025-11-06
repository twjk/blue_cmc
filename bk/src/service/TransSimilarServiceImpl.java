package com.qcmz.cmc.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.cmc.dao.ITransSimilarDao;
import com.qcmz.cmc.entity.CmcTransSimilar;
import com.qcmz.cmc.service.ITransSimilarService;

/**
 * 类说明：翻译相似度服务
 * 修改历史：
 */
@Service
public class TransSimilarServiceImpl implements ITransSimilarService {
	@Autowired
	private ITransSimilarDao transSimilarDao;
	
	/**
	 * 保存相似度计数
	 * @param from
	 * @param to
	 * @param proxyId
	 * @param similar
	 * @param count
	 * 修改历史：
	 */
	public void save(String from, String to, Long proxyId, Integer similar, Integer count){
		CmcTransSimilar bean = new CmcTransSimilar();
		bean.setSrclang(from);
		bean.setTolang(to);
		bean.setProxyid(proxyId);
		bean.setSimilar(similar);
		bean.setSimilarcount(count);
		bean.setCreatetime(new Date());
		transSimilarDao.save(bean);
	}
}
