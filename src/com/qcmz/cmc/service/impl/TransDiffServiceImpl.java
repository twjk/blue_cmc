package com.qcmz.cmc.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcmz.bdc.ws.provide.locator.MsgPushWS;
import com.qcmz.cmc.constant.DictConstant;
import com.qcmz.cmc.dao.ITransDiffDao;
import com.qcmz.cmc.entity.CmcTransDiff;
import com.qcmz.cmc.entity.CmcTransCorrect;
import com.qcmz.cmc.service.ITransDiffService;
import com.qcmz.framework.constant.SystemConstants;
import com.qcmz.framework.page.PageBean;
import com.qcmz.framework.util.StringUtil;
import com.qcmz.framework.util.log.Operator;

@Service
public class TransDiffServiceImpl implements ITransDiffService {
	@Autowired
	private ITransDiffDao transDiffDao;
	
	/**
	 * 分页获取列表
	 * @param map
	 * @param pageBean
	 * 修改历史：
	 */
	public void queryByMapTerm(Map<String, String> map, PageBean pageBean){
		transDiffDao.queryByMapTerm(map, pageBean);
	}
	
	/**
	 * 批量保存翻译差异
	 * @param list
	 */
	public void saveOrUpdate(List<CmcTransDiff> list){
		transDiffDao.saveOrUpdateAll(list);
	}
	
	/**
	 * 获取差异信息
	 * @param diffId
	 * @return
	 */
	public CmcTransDiff getDiff(Long diffId){
		return (CmcTransDiff) transDiffDao.load(CmcTransDiff.class, diffId);
	}
	
	/**
	 * 修正译文
	 * @param diffId
	 * @param dst
	 */
	public CmcTransCorrect correctDst(Long diffId, String dst, Operator operator){
		CmcTransDiff entity = getDiff(diffId);

		CmcTransCorrect correct = new CmcTransCorrect();
		correct.setSessionid(entity.getSessionid());
		correct.setUserid(entity.getUserid());
		correct.setUuid(entity.getUuid());
		correct.setPushregid(entity.getPushregid());
		correct.setFromlang(entity.getFromlang());
		correct.setSrc(entity.getSrc());
		correct.setTolang(entity.getTolang());
		correct.setDst(dst);
		correct.setMtdst(entity.getDst());
		correct.setCorrectsource(DictConstant.DICT_TRANS_CORRECTSOURCE_LOG);
		correct.setOpercd(operator.getOperCd());
		correct.setOpername(operator.getOperName());
		correct.setOpertime(new Date());
		
		entity.setDst(dst);
		entity.setStatus(SystemConstants.STATUS_SUCCESS);
		
		//入库
		transDiffDao.update(entity);
		transDiffDao.save(correct);
		
		//推送
		String toType = null;
		String to = null;
		if(StringUtil.notBlankAndNull(entity.getPushregid())){
			toType = MsgPushWS.PUSHTOTYPE_REGID;
			to = entity.getPushregid();
		}else if(entity.getUserid()!=null){
			toType = MsgPushWS.PUSHTOTYPE_ALIAS;
			to = String.valueOf(entity.getUserid());
		}
		MsgPushWS.pushMsg(1023L, toType, to, dst, entity.getSessionid(), null);
		
		return correct;
	}
	
	/**
	 * 清除指定时间之前的翻译差异
	 * @param maxTime
	 */
	public void clearDiff(Date maxTime){
		transDiffDao.clearDiff(maxTime);
	}
}
